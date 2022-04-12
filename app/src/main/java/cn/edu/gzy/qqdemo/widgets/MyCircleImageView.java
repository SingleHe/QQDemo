package cn.edu.gzy.qqdemo.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.chapter02.R;

//继承自AppCompatImageView , 而不是ImageView，主要是AppCompatImageView可以支持更早版本的API，比如API-v7
public class MyCircleImageView extends AppCompatImageView {
    private Context mContext;
    private Bitmap mask;
    private Paint paint;
    private int  mBorderWidth = 10;
    private int mBorderColor = Color.parseColor("#f2f2f2");
    public MyCircleImageView(@NonNull Context context) {
        super(context);
    }

    public MyCircleImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿
        //图像在放大绘制的时候，默认使用的是最近邻插值过滤，这种算法简单，但会出现马赛克现象；
        // 而如果开启了双线性过滤，就可以让结果图像显得更加平滑。
        paint.setFilterBitmap(true);
        //指的是 你要绘制的内容 和 canvas 的目标位置的内容应该怎样结合计算出最终的颜色。
        //通俗的讲就是要你以绘制的图形作为源图像，以View中已有的内容做为目标图像，选取一个PorterDuff.Mode 作为绘制内容的颜色处理方案。
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyCircleImageView);
        mBorderColor = typedArray.getColor(R.styleable.MyCircleImageView_border_color,mBorderColor);
        final int defaultSize = (int)(2 * context.getResources().getDisplayMetrics().density + 0.5f);
        mBorderWidth = typedArray.getDimensionPixelOffset(R.styleable.MyCircleImageView_border_width, defaultSize);
        typedArray.recycle();
    }

    public MyCircleImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 生成圆形蒙版
     * @param width
     * @param height
     * @return
     */
    private Bitmap createOvalBitmap(int width, int height){
        //从API 13开始不推荐使用，在android 4.4上面，设置的ARGB_4444会被系统使用ARGB_8888替换
        //ARGB_8888: 默认的选项，每像素占用4字节，ARGB分别占8位，支持1600万种颜色，质量最高，当然内存占用也高。
        Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
        Bitmap localBitmap = Bitmap.createBitmap(width,height,localConfig);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint = new Paint();
        int padding = (mBorderWidth - 2) > 0 ? mBorderWidth -2 : 1;
        //矩形
        RectF localRectF = new RectF(padding, padding, width-padding, height-padding);
        localCanvas.drawOval(localRectF, localPaint);//绘制椭圆形
        return localBitmap;
    }
    private void drawBorder(Canvas canvas, final int width, final int height){
        if(mBorderWidth == 0){
            return ;
        }
        final Paint mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderWidth);
        //同心圆的x、y坐标， 圆的半径和画笔
        canvas.drawCircle(width>>1, height>>1, (width-mBorderWidth)>>1, mBorderPaint);
        canvas = null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //通过getDrawable得到ImageView的drawable的属性
        Drawable drawable = getDrawable();
        if(drawable == null){
            return;
        }
        int width = getWidth();
        int height = getHeight();
        //保存到栈中
        int layer = canvas.saveLayer(0.0F, 0.0F, width, height, null, Canvas.ALL_SAVE_FLAG);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        if((this.mask == null) || (this.mask.isRecycled())){
            this.mask = createOvalBitmap(width,height);
        }
        canvas.drawBitmap(this.mask, 0, 0, paint);
        drawBorder(canvas, width, height);
    }
}

package com.example.chapter02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class WidgetActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
        EditText inputHero = findViewById(R.id.inputHero);
        Button confirm = findViewById(R.id.confirm_hero);
        ImageView heroImage = findViewById(R.id.image_myhero);
        CheckBox CBYase = findViewById(R.id.cb_yase);
        CheckBox CBBingnv = findViewById(R.id.cb_bingnv);
        CheckBox CBHouyi = findViewById(R.id.cb_houyi);
        CheckBox CBLuban = findViewById(R.id.cb_luban);
        Button heroBtn = findViewById(R.id.hero_btn);
        ProgressBar progressBar1 = findViewById(R.id.progress_bar1);
        TextView pbText = findViewById(R.id.pb_text);
        confirm.setOnClickListener(v->{
            String hero = inputHero.getText().toString();//获取用户输入
            Toast.makeText(WidgetActivity.this, "您已选择"+hero+"!", Toast.LENGTH_SHORT).show();
        });
        heroImage.setOnClickListener(v->{
            heroImage.setImageResource(R.drawable.luban);
        });


        Handler handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                progressBar1.setProgress(msg.arg1);  //将进度条进度更新到msg.arg1的值
                pbText.setText("游戏加载中...("   +msg.arg1+  "%)"  ); //更新提示信息
                if(msg.what==1){

                    //msg==100，弹出提示信息“下载完成”
                    Toast.makeText(WidgetActivity.this,"游戏开始，敌军还有三十秒到达战场！", Toast.LENGTH_SHORT).show();
                    //msg==100，将图片设置为显示
                    //imageView.setVisibility(View.VISIBLE);
                }
            }
        };
        heroBtn.setOnClickListener(v->{
            StringBuilder sb = new StringBuilder();
            if(!CBYase.isChecked() && !CBBingnv.isChecked() && !CBHouyi.isChecked() && !CBLuban.isChecked()){
                Toast.makeText(WidgetActivity.this,"您还尚未选择英雄！",Toast.LENGTH_SHORT).show();
            }else{
                if(CBYase.isChecked()){
                    sb.append(CBYase.getText().toString()+",");
                }
                if(CBBingnv.isChecked()){
                    sb.append(CBBingnv.getText().toString()+",");
                }
                if(CBHouyi.isChecked()){
                    sb.append(CBHouyi.getText().toString()+",");
                }
                if(CBLuban.isChecked()){
                    sb.append(CBLuban.getText().toString());
                }
                Toast.makeText(WidgetActivity.this, "您选择的阵容是："+sb.toString()+"!",Toast.LENGTH_SHORT).show();
            }
            new Thread(()->{
                int pValue = 0;
                while(true){
                    pValue += (int)(Math.random()*10);
                    try{
                        Thread.sleep(100);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
//                    Message mes = new Message();
                    Message mes = Message.obtain();
                    if(pValue<100){
                        mes.arg1 = pValue;
                        mes.what = 0;
                        handler.sendMessage(mes);
                    }else{
                        mes.arg1 = 100;
                        mes.what = 1;
                        handler.sendMessage(mes);
                        break;
                    }
                }
            }).start();
        });
        RadioButton radioMan  =  findViewById(R.id.radio_man);
        RadioButton radioFemale  =  findViewById(R.id.radio_female);
        RadioButton radioSecret  =  findViewById(R.id.radio_secret);
        RadioGroup RGGender = findViewById(R.id.gender_group);
        RGGender.setOnCheckedChangeListener((radioView, isChecked)->{
            if(radioMan.isChecked()){
                Toast.makeText(WidgetActivity.this, "您的性别是："+radioMan.getText().toString()+"!",Toast.LENGTH_SHORT).show();
            }
            if(radioFemale.isChecked()){
                Toast.makeText(WidgetActivity.this, "您的性别是："+radioFemale.getText().toString()+"!",Toast.LENGTH_SHORT).show();
            }
            if(radioSecret.isChecked()){
                Toast.makeText(WidgetActivity.this, "您的性别是："+radioSecret.getText().toString()+"!",Toast.LENGTH_SHORT).show();
            }
        });
        SwitchCompat blue_sw = findViewById(R.id.sw_bluebooth);
        ImageView light = findViewById(R.id.img_light);
        LinearLayout myWidget = findViewById(R.id.my_widget);
        blue_sw.setOnCheckedChangeListener((view,isChecked)->{
            String msg;
            if(isChecked){
                msg = "开灯";
                light.setImageResource(R.drawable.on);
                //myWidget.setBackgroundColor(Color.parseColor("#FFF055"));
                //动画效果实现背景色显示
                ValueAnimator colorAnim = ObjectAnimator.ofInt(myWidget,"backgroundColor",
                        Color.BLACK,Color.parseColor("#FFF055"));
                colorAnim.setDuration(2000);
                colorAnim.setEvaluator(new android.animation.ArgbEvaluator());
                //colorAnim.setRepeatCount(ValueAnimator.INFINITE);
                //colorAnim.setRepeatMode(ValueAnimator.REVERSE);
                colorAnim.start();
                blue_sw.setTextColor(Color.parseColor("#000000"));
            }else{
                msg = "关灯";
                light.setImageResource(R.drawable.off);
                myWidget.setBackgroundColor(Color.parseColor("#000000"));
                blue_sw.setTextColor(Color.parseColor("#FFF055"));
            }
            Toast myToast = Toast.makeText(WidgetActivity.this,msg,Toast.LENGTH_SHORT);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            WidgetActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            Log.d("WidgetActivity","获取屏幕高度为:"+height);
            myToast.setGravity(Gravity.TOP,0,height/2);
            myToast.show();
        });
        /*ProgressBar progressBar = findViewById(R.id.progress_bar);
        blue_sw.setOnCheckedChangeListener((view,isChecked)->{
            if(isChecked){
                if(progressBar.getVisibility() == View.GONE){
                    progressBar.setVisibility(View.VISIBLE);
                }
                Toast.makeText(WidgetActivity.this, "蓝牙打开", Toast.LENGTH_SHORT).show();
            }else{
                if(progressBar.getVisibility() != View.GONE){
                    progressBar.setVisibility(View.GONE);
                }
                Toast.makeText(WidgetActivity.this, "蓝牙关闭", Toast.LENGTH_SHORT).show();
            }
        });*/

        Button dialogBtn = findViewById(R.id.dialog_btn);
        dialogBtn.setOnClickListener(v->{
            AlertDialog.Builder dialog = new AlertDialog.Builder(WidgetActivity.this);
            dialog.setTitle("警告！");
            dialog.setMessage("进入公共场合请佩戴口罩！");
            dialog.setCancelable(false);
            dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog.show();
        });
    }
}
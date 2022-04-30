package cn.edu.gzy.qqdemo.dbutils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.chapter02.R;

public class MyDBHelper extends SQLiteOpenHelper {
    public MyDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建登录信息表
        String sql = "CREATE TABLE QQ_Login(qq_num VARCHAR(20) PRIMARY KEY NOT NULL," +
                "qq_name VARCHAR NOT NULL," +
                "qq_pwd VARCHAR NOT NULL," +
                "qq_img INT," +
                "qq_online VARCHAR," +
                "qq_action VARCHAR ," +
                "belong_country VARCHAR);";
        db.execSQL(sql);
        initData(db);
    }

    private void initData(SQLiteDatabase db) {
        String countries[] = new String[]{"蜀","魏","吴"};//所属国家
        //QQ号
        String nums[][] = new String[][]{
                {"1001","1002","1003","1004","1005","1006"},
                {"2001","2002","2003"},
                {"3001","3002","3003"}
        };
        String names[][] = new String[][]{
                {"刘备","关羽","张飞","赵云","黄忠","魏延"},
                {"曹操","许褚","张辽"},
                {"孙权","鲁肃","吕蒙"}
        };//账号名
        //头像
        int icons[][] = {
                {R.drawable.liubei,R.drawable.guanyu,R.drawable.zhangfei,R.drawable.huangzhong,R.drawable.weiyan},
                {R.drawable.caocao,R.drawable.xuchu,R.drawable.zhangliao},
                {R.drawable.sunquan,R.drawable.lusu,R.drawable.lvmeng}
        };
        String[][] pwd = {
                {"123456","213456","123124","121111","323241","654321"},
                {"goodboy","aini1314","4494451"},
                {"234324","234sfe","2342sg"}
        };
        String[][] onLineMode = {
                {"[5G在线]","[5G在线]","[PC在线]","[4G在线]","[Pad在线]","[3G在线]"},
                {"[5G在线]","[5G在线]","[4G在线]"},
                {"[隐身]","[勿打扰]","[在忙]"}
        };
        String[][] qqAction = {
                {"在家躺尸","五一不放假","俺也一样","谁能挡我！","一炮一个小朋友！","要你命三千"},
                {"我负天下人","看我狗头","你看我头像牛逼不"},
                {"老子一统三国","比上不足，比下有余","尚能饭否！"}
        };
        String sql = "insert into QQ_Login(qq_num,qq_name,qq_pwd,qq_img,qq_online,qq_action,belong_country) values(?,?,?,?,?,?,?)";
        for(int i= 0 ; i < countries.length; i++){
            for(int j = 0 ; j < names.length; j++){
                db.execSQL(sql,new Object[]{
                        nums[i][j],
                        names[i][j],
                        pwd[i][j],
                        icons[i][j],
                        onLineMode[i][j],
                        qqAction[i][j],
                        countries[i]});
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

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
        //创建联系人信息表QQ_Contact
        String sql1 = "CREATE TABLE QQ_Contact(contactId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "qq_num VARCHAR NOT NULL," +
                "belong_qq VARCHAR NOT NULL)";
        //创建View_Contact视图,QQ_Contact左连接QQ_Login表
        String sql2 = "CREATE VIEW view_contact as SELECT u.contactId , u.belong_qq , v.* FROM " +
                "QQ_Contact u LEFT JOIN QQ_Login v on u.qq_num = v.qq_num";
        db.execSQL(sql);//执行创建登录信息表
        db.execSQL(sql1);//执行创建联系人信息表
        db.execSQL(sql2);//执行创建视图语句
        initData(db);
    }

    private void initData(SQLiteDatabase db) {
        String countries[] = new String[]{"蜀","魏","吴"};//所属国家
        //QQ号
        String nums[][] = new String[][]{
                {"1001","1002","1003","1004","1005","1006"},
                {"2001","2002","2003" +
                        "" +
                        ""},
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
        //初始化联系人数据
        String sql1 = "insert into QQ_Contact(qq_num, belong_qq) values(?,?)";
        //设置1002的联系人
        for(int i = 0 ; i < nums.length; i++){
            for(int j = 0 ; j < nums[i].length; j++){
                //将除了1002自己以外的人都设置成它的联系人
                if(!"1002".equals(nums[i][j])){
                  db.execSQL(sql1, new Object[]{nums[i][j],"1002"});
                }
                //设置1001的联系人  条件是：每一行偶数列的人才是联系人
                if(!"1001".equals(nums[i][j]) && j % 2 == 0){
                    db.execSQL(sql1, new Object[]{nums[i][j], "1001"});
                }
                //设置2001的联系人， 条件是：每一行中第二列之后的才是联系人
                if(!"2001".equals(nums[i][j]) && j > 0){
                    db.execSQL(sql1, new Object[]{nums[i][j], "2001"});
                }
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //更新数据库  先把已有的数据表删除
        db.execSQL("drop table if exists QQ_Login");
        db.execSQL("drop table if exists QQ_Contact");
        db.execSQL("drop view if exists view_contact");
        onCreate(db);
    }
}

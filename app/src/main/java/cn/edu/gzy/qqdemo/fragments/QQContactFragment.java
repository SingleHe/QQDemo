package cn.edu.gzy.qqdemo.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.example.chapter02.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.gzy.qqdemo.QQMainActivity;
import cn.edu.gzy.qqdemo.adapters.QQContactAdapter;
import cn.edu.gzy.qqdemo.beans.QQContactBean;
import cn.edu.gzy.qqdemo.dbutils.Db_Params;
import cn.edu.gzy.qqdemo.dbutils.MyDBHelper;

public class QQContactFragment extends Fragment {
    private ExpandableListView epListView;
    private QQContactAdapter adapter;
    private ImageView loginedImg;//修改联系人界面对应的登录头像
    private String countries[] = new String[]{"蜀","魏","吴"};
    private String names[][] = new String[][]{
            {"刘备","关羽","张飞","赵云","黄忠","魏延"},
            {"曹操","许褚","张辽"},
            {"孙权","鲁肃","吕蒙"}
    };
    private int icons[][] = new int[][]{
            {R.drawable.liubei,R.drawable.guanyu,R.drawable.zhangfei,R.drawable.zhaoyun,R.drawable.huangzhong,R.drawable.weiyan},
            {R.drawable.caocao,R.drawable.xuchu,R.drawable.zhangliao},
            {R.drawable.sunquan,R.drawable.lusu,R.drawable.lvmeng}
    };
    private List<String> groupData;
    private Map<String, List<QQContactBean>> childData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_qqcontact, container, false);
        //获取显示登录用户头像的图片控件
        loginedImg = view.findViewById(R.id.imgLoginIcon);
        //在登录Activity中，已经将登录账号的数据查询出来设置在了QQMainActivity的静态内部成员loginedUser对象中。
        loginedImg.setImageResource(QQMainActivity.loginedUser.getImg());
        epListView = view.findViewById(R.id.exlv_contact);
        childData = new HashMap<String, List<QQContactBean>>();
        groupData = new ArrayList<String>();
        //initialData();//程序设定的联系人数据
        //这里不要用this.getContenxt()
        adapter = new QQContactAdapter(groupData,childData,view.getContext());
        epListView.setAdapter(adapter);
        return view;
    }

    /**
     * 初始化数据,程序设定
     */
    private void initialData() {
        for (int i = 0; i < countries.length; i++) {
            groupData.add(countries[i]);
            List<QQContactBean> list = new ArrayList<QQContactBean>();
            for (int j = 0; j < names[i].length; j++){
                QQContactBean hero = new QQContactBean(names[i][j],icons[i][j],"[5G在线]","天天向上");
                list.add(hero);
            }
            childData.put(countries[i],list);
        }
    }

    /**
     * 从数据库中查询出登录用户的联系人
     */
    private void getContacts(){
        //注意修改数据库版本，改为比原先版本更大的值
        MyDBHelper helper = new MyDBHelper(getContext(), Db_Params.DB_NAME, null, Db_Params.DB_VER);
        /*（1）getWritableDatabase()方法以读写方式打开数据库。
        一旦数据库的磁盘空间满了，数据库就只能读而不能写，此时用getWritableDatabase()打开数据库就会出错。
        （2）getReadableDatabase()方法先调用getWritableDatabase()先以读写方式打开数据库。
        倘若使用如果数据库的磁盘空间满了，就会打开失败，当打开失败后会继续尝试以只读方式打开数据库。
        一般避免报错都是用getReadableDatabase()方法*/
        SQLiteDatabase db = helper.getReadableDatabase();
        //1. 查询联系人所属的分类
        String sql = "select distinct belong_country from view_contact where belong_qq=?";
        Cursor groupCursor = db.rawQuery(sql,new String[]{QQMainActivity.loginedUser.getNum()});
        while(groupCursor.moveToNext()){
            String countryName = groupCursor.getString(groupCursor.getColumnIndexOrThrow("belong_country"));
            groupData.add(countryName);
            //2.查询登录用户的联系人列表
            sql = "select * from view_contact where belong_qq = ? and belong_country = ?";
            Cursor cursor = db.rawQuery(sql, new String[]{QQMainActivity.loginedUser.getNum(),
                            countryName});
            List<QQContactBean> list = new ArrayList<>();
            while(cursor.moveToNext()){
                QQContactBean bean= new QQContactBean(
                        cursor.getString(cursor.getColumnIndexOrThrow("qq_name")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("qq_img")),
                        cursor.getString(cursor.getColumnIndexOrThrow("qq_online")),
                        cursor.getString(cursor.getColumnIndexOrThrow("qq_action")),
                        cursor.getString(cursor.getColumnIndexOrThrow("qq_num")),
                        cursor.getString(cursor.getColumnIndexOrThrow("belong_country"))
                );
                list.add(bean);
            }
            childData.put(countryName, list);
        }
    }
}
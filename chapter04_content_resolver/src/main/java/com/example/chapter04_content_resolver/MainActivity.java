package com.example.chapter04_content_resolver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ContentResolver contentResolver;
    private RecyclerView lv;
    private Button initBtn;
    private Button queryBtn;
    private Button delBtn;
    private Button insertOneBtn;
    private Button updateOneBtn;
    private Button delOneBtn;
    private MyAdapter adapter;
    private List<UserBean> data;
    private static final String AUTHORITY = "com.example.chapter04_storage.mycontentprovider";
    private static final Uri USERS_ALL_URI = Uri.parse("content://"+AUTHORITY+"/users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentResolver = getContentResolver();
        data = new ArrayList<>();
        adapter = new MyAdapter(data,this);
        lv = findViewById(R.id.lv);
        lv.setAdapter(adapter);
        initBtn = findViewById(R.id.btnInit);
        queryBtn = findViewById(R.id.btnQuery);
        delBtn = findViewById(R.id.btnDelAll);
        insertOneBtn = findViewById(R.id.btnInsertOne);
        updateOneBtn = findViewById(R.id.btnUpdateOne);
        delOneBtn = findViewById(R.id.btnDelOne);
        initBtn.setOnClickListener(v->{
            Toast.makeText(MainActivity.this, "数据库初始化完毕！",Toast.LENGTH_SHORT).show();
        });
        queryBtn.setOnClickListener(v->{
            data.clear();
            Cursor cursor = contentResolver.query(USERS_ALL_URI,null,null,null,null);
            while(cursor.moveToNext()){
                UserBean bean = new UserBean(cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")));
                data.add(bean);
            }
            adapter.notifyDataSetChanged();
            cursor.close();
        });
        delBtn.setOnClickListener(v->{
            contentResolver.delete(USERS_ALL_URI,null,null);
            adapter.notifyDataSetChanged();
        });
        insertOneBtn.setOnClickListener(v->{
            UserBean user = new UserBean(9507,"CoupleHe");
            ContentValues insertValue = new ContentValues();
            insertValue.put("id",user.getId());
            insertValue.put("name",user.getName());
            contentResolver.insert(USERS_ALL_URI,insertValue);
            adapter.notifyDataSetChanged();
        });
        updateOneBtn.setOnClickListener(v->{
            ContentValues updateValue = new ContentValues();
            updateValue.put("name","JoeChen");
            Uri parseUri = ContentUris.withAppendedId(USERS_ALL_URI,9507);
            contentResolver.update(parseUri,updateValue,null,null);
            adapter.notifyDataSetChanged();
        });
        delOneBtn.setOnClickListener(v->{
            Uri delUri = ContentUris.withAppendedId(USERS_ALL_URI,9527);
            contentResolver.delete(delUri,null,null);
            adapter.notifyDataSetChanged();
        });


    }
}
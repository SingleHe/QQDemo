package com.example.chapter06_httptest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tvNoData;
    private EmptyRecyclerView lvCountry;
    private List<String> list;
    private MyRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvNoData = findViewById(R.id.tvNoData);
        lvCountry = findViewById(R.id.lvCountry);
        lvCountry.setEmptyView(tvNoData);//当没有数据时显示
        list = new ArrayList<>();
        adapter = new MyRecyclerAdapter(list);
        /**
         * 注意：以上步骤 还不能显示数据，还是一片显示空白 空白，为何空白 因为都没有去执行MyRecyclerAdpater的代码
         * 需要设置以下代码setLayoutManager，才能显示
         */
        LinearLayoutManager manager =
                new LinearLayoutManager(MainActivity.this, // 参数一：上下文环境
                        LinearLayoutManager.VERTICAL,  // 参数二：显示方向 垂直
                        false);          // 参数三：是否倒序
        lvCountry.setLayoutManager(manager);
        lvCountry.setAdapter(adapter);
        new StrGetTask(list, adapter).execute("http://192.168.0.104/SoccerDataHandler.ashx?action=getTeamStr");
    }
}
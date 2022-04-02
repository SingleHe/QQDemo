package com.example.chapter02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class SecondActivity extends AppCompatActivity {
    private static final String tag = "SecondActivity";
    private static int count = 0;
    ArrayList<Integer> bgList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(tag,"onCreate");
        setContentView(R.layout.activity_second);
        Button login = findViewById(R.id.login_btn);
        Button register = findViewById(R.id.register_btn);
        //初始化背景图片
        bgList= new ArrayList<Integer>();
        bgList.add(R.drawable.bg1);
        bgList.add(R.drawable.bg2);
        bgList.add(R.drawable.bg3);
        bgList.add(R.drawable.bg4);

        login.setOnClickListener(v -> {
            //跳转到第一个Activity
            //Intent intent = new Intent(this,FirstActivity.class);
            //1. 打开网页
            /*Intent intent  = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.baidu.com"));
            startActivity(intent);*/
            Intent intent = new Intent(this,QQLoginActivity.class);
            startActivity(intent);

            //2. 打开拨号界面

        });
        register.setOnClickListener(v->{
            Intent intent = new Intent(this,DialogActivity.class);
            Intent intent1 = new Intent("com.example.chapter02.ACTION_START");
            intent1.addCategory("com.example.chapter02.MY_CATEGORY");
            startActivity(intent1);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                Toast.makeText(this,"您点击了添加按钮！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this,"您点击了删除按钮！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.change_bg:
                count++;
                if(count == bgList.size()){
                    count = 0;
                }
                ConstraintLayout consLayout = findViewById(R.id.login_page);
                //随机切换
                //consLayout.setBackgroundResource(bgList.get(new Random().nextInt(bgList.size())));
                //顺序切换
                consLayout.setBackgroundResource(bgList.get(count));
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(tag,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(tag,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(tag,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(tag,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(tag,"onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(tag,"onRestart");
    }
}
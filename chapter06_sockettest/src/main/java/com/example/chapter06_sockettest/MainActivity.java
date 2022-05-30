package com.example.chapter06_sockettest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button connectBtn = findViewById(R.id.btn_connect);
        EditText etIP = findViewById(R.id.et_ip);
        EditText etPort = findViewById(R.id.et_port);
        EditText etName = findViewById(R.id.et_name);
        connectBtn.setOnClickListener(v->{
            String name = etName.getText().toString();
            if ("".equals(name)) {
                Toast.makeText(this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(MainActivity.this, ChatRoom.class);
                intent.putExtra("name",name);
                intent.putExtra("ip",etIP.getText().toString());
                intent.putExtra("port", etPort.getText().toString());
                startActivity(intent);
            }
        });
    }
}
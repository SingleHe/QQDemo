package com.example.chapter02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class QQLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqlogin);
        TextView forgetPwd = findViewById(R.id.forgetPwd_tv);
        forgetPwd.setOnClickListener(v->{
            Intent intent = new Intent(this,ForgetPwdActivity.class);
            startActivity(intent);
        });
        TextView registerTv = findViewById(R.id.tvRegisterQQ);
        registerTv.setOnClickListener(v->{
            Intent intent = new Intent(this,RegisterActivity.class);
            startActivity(intent);
        });
        Button loginBtn = findViewById(R.id.btn_login);
        EditText QQCount = findViewById(R.id.etQQName);
        EditText QQPassword = findViewById(R.id.etQQPwd);
        loginBtn.setOnClickListener(v->{
            String number = QQCount.getText().toString();
            String password = QQPassword.getText().toString();
            if(number != null && password !=null && "821978332".equals(number) && "123456".equals(password)){
                Intent intent = new Intent(this,QQMessageActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(QQLoginActivity.this,"QQ账号或密码有误！",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
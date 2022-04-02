package com.example.chapter02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button regBtn = findViewById(R.id.reg_btn);
        EditText regAccount = findViewById(R.id.register_count);
        EditText regPassword = findViewById(R.id.register_pwd);
        RadioGroup regGenderRG = findViewById(R.id.gender_rg);
        RadioButton manRB = findViewById(R.id.man_rb);
        RadioButton femaleRB = findViewById(R.id.female_rb);
        EditText regEmail = findViewById(R.id.register_email);

        regGenderRG.setOnCheckedChangeListener((group,isChecked)->{
            switch (group.getId()){
                case R.id.man_rb:
                    RadioButton manRB1 = findViewById(R.id.man_rb);
                    if(manRB.isChecked()){

                    }
                    break;
                case R.id.female_rb:
                    RadioButton femaleRB1 = findViewById(R.id.female_rb);
                    if(femaleRB.isChecked()){

                    }
                    break;
            }
        });
        regBtn.setOnClickListener(v->{
            String gender ;
            if(manRB.isChecked()){
                gender = manRB.getText().toString();
            }else if(femaleRB.isChecked()){
                gender = femaleRB.getText().toString();
            }else{
                gender = "";
            }
            String account = regAccount.getText().toString();
            String password = regPassword.getText().toString();
            String email = regEmail.getText().toString();
            Log.d("RegisterActivity", "获取的账号为："+account);
            Log.d("RegisterActivity","获取的密码为："+password);
            Log.d("RegisterActivity", "获取的邮箱为："+email);
            StringBuilder sb = new StringBuilder();
            if(account != null && !"".equals(account)){
                sb.append("QQ号："+account+"\n");
            }
            if(password != null && !"".equals(password)){
                sb.append("密码："+password+"\n");
            }
            if(email != null && !"".equals(email)){
                sb.append("邮箱："+email+"\n");
            }
            if(gender != null && !"".equals(gender)){
                sb.append("性别："+gender);
            }
            Toast.makeText(RegisterActivity.this,sb.toString(),Toast.LENGTH_LONG).show();
        });

    }
}
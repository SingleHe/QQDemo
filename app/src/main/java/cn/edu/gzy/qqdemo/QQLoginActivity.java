package cn.edu.gzy.qqdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter02.R;

import cn.edu.gzy.qqdemo.QQMainActivity;

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
                Intent intent = new Intent(this, QQMainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(QQLoginActivity.this,"QQ账号或密码有误！",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (item.getItemId()){
            case R.id.menuItem_about:
                builder.setTitle(R.string.dialog_about_title)
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage(R.string.dialog_about_message)
                        .setPositiveButton(R.string.dialog_btn_ok,null)
                        .show();
                break;
            case R.id.menuItem_exit:
                builder.setTitle(R.string.dialog_exit_title)
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage(R.string.dialog_exit_message)
                        .setPositiveButton(R.string.dialog_btn_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(0);
                            }
                        })
                        .setNegativeButton(R.string.dialog_btn_no,null)
                        .show();
        }
        return true;
    }
}
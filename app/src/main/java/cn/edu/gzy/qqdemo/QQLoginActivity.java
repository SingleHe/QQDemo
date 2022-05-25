package cn.edu.gzy.qqdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter02.R;

import cn.edu.gzy.qqdemo.QQMainActivity;
import cn.edu.gzy.qqdemo.beans.QQContactBean;
import cn.edu.gzy.qqdemo.dbutils.Db_Params;
import cn.edu.gzy.qqdemo.dbutils.MyDBHelper;

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
        CheckBox chkRememberPwd = findViewById(R.id.chkRememberPwd);
        loginBtn.setOnClickListener(v->{
            String number = QQCount.getText().toString();
            String password = QQPassword.getText().toString();
            //1.获取数据库帮组类
            MyDBHelper helper = new MyDBHelper(getApplicationContext(), Db_Params.DB_NAME,null,Db_Params.DB_VER);
            //2.获取数据库
            SQLiteDatabase db = helper.getReadableDatabase();
            //3.定义数据库操作语句
            String sql = "select * from QQ_Login where qq_num = ? and qq_pwd = ? ";
            Cursor cursor = db.rawQuery(sql, new String[]{number,password});
            if(cursor.moveToNext()){
                QQMainActivity.loginedUser = new QQContactBean(
                   cursor.getString(cursor.getColumnIndexOrThrow("qq_name")),
                   cursor.getString(cursor.getColumnIndexOrThrow("qq_imgurl")),
                   cursor.getString(cursor.getColumnIndexOrThrow("qq_online")),
                   cursor.getString(cursor.getColumnIndexOrThrow("qq_action")),
                   cursor.getString(cursor.getColumnIndexOrThrow("qq_num")),
                   cursor.getString(cursor.getColumnIndexOrThrow("belong_country"))
                );
                if(chkRememberPwd.isChecked()){
                    //1.获得SharedPreference对象
                    SharedPreferences settings = getSharedPreferences("setting", MODE_PRIVATE);
                    //2. 获取SharedPreferences.Editor对象
                    SharedPreferences.Editor edit = settings.edit();
                    //3. 添加数据
                    edit.putString("qqCount", number);
                    edit.putString("qqPassword", password);
                    edit.putBoolean("isRemembered",true);
                    //4. 提交
                    edit.commit();
                }
                Intent intent  = new Intent(this, QQMainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(QQLoginActivity.this,"QQ账号或密码有误！",Toast.LENGTH_SHORT).show();
            }
        });
        //自动填充数据
        SharedPreferences settings = getSharedPreferences("setting", MODE_PRIVATE);
        QQCount.setText(settings.getString("qqCount",""));
        QQPassword.setText(settings.getString("qqPassword",""));
        chkRememberPwd.setChecked(settings.getBoolean("isRemembered",false));
        //动态授权访问联系人
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},1000);
        }
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

    /**
     * 当系统授无效时，系统会给出相应的提示信息。
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode != 1000){
            Toast.makeText(this, "请授予读取联系人信息权限", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    
}
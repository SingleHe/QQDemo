package cn.edu.gzy.qqdemo.dialogs;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.chapter02.R;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gzy.qqdemo.QQMainActivity;
import cn.edu.gzy.qqdemo.dbutils.Db_Params;
import cn.edu.gzy.qqdemo.dbutils.MyDBHelper;

/**
 * 对话框显示   通过fragment来实现
 */
public class NewContactDialog extends DialogFragment {
    private Spinner spinContact;
    private Button btnOK, btnCancel;
    private OnDialogCompleted onDialogCompleted;//处理新联系人列表自动刷新
    public void setOnDialogCompleted(OnDialogCompleted onDialogCompleted){
        this.onDialogCompleted = onDialogCompleted;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//设置无标题的弹窗
        setCancelable(false);//弹窗不能关闭
        Window window = getDialog().getWindow();//获取窗口
        //去掉dialog默认的padding
        window.getDecorView().setPadding(0,0,0,0);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;//设置弹窗的宽度沾满整个屏幕
        attributes.height = 850;//设置弹窗的高度
        window.setAttributes(attributes);
        //1.加载弹窗视图
        View view = inflater.inflate(R.layout.dialog_newcontact, container);
        //2. 读取控件
        spinContact = view.findViewById(R.id.dlg_spnContact);
        btnOK = view.findViewById(R.id.dlg_btnOK);
        btnCancel = view.findViewById(R.id.dlg_btnCancel);
        //3. 设置下拉列表控件中的数据
        //3-1 创建适配器，使用ArrayAdapter，并指定数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                                        getContactList());
        //3-2 绑定适配器
        spinContact.setAdapter(adapter);
        //点击取消按钮，就推出窗口
        btnCancel.setOnClickListener(v->{
            dismiss();//关闭窗口
        });
        //点击确认按钮，就保存数据到联系人中。
        btnOK.setOnClickListener(v->{
            //1. 获得数据库对象
            Log.d("NewContactDialog","NewContactDialog中通过getContext()获取的上下文环境为："+getContext());
            MyDBHelper helper = new MyDBHelper(getContext(), Db_Params.DB_NAME,null,Db_Params.DB_VER);
            SQLiteDatabase db = helper.getWritableDatabase();
            String sql = "insert into QQ_Contact(qq_num, belong_qq) values(?,?)";
            Log.d("NewContactDialog","从下拉列表中获取的数据为："+spinContact.getSelectedItem().toString());
            String qqNum = spinContact.getSelectedItem().toString().split("\t\t")[0];//取得联系人的QQ号码
            db.execSQL(sql,new Object[]{qqNum,QQMainActivity.loginedUser.getNum()});
            Toast.makeText(view.getContext(),"联系人添加成功！",Toast.LENGTH_SHORT).show();
            onDialogCompleted.dialogCompleted("OK",0);//实现刷新联系人列表
            dismiss();//关闭弹窗
        });
        return view;
    }

    /**
     * 从数据库查询数据，不能包含自己，以及已经在联系人列表中的对象。
     * @return
     */
    private List<String> getContactList() {
        //1. 获取数据库
        MyDBHelper helper = new MyDBHelper(getContext(), Db_Params.DB_NAME,null,Db_Params.DB_VER);
        //2. 获取数据库对象
        SQLiteDatabase db = helper.getReadableDatabase();//查询数据
        //3. 构造SQL语句 : 由于新添加的联系人不能是已在联系人列表中的人，同时也不能是它自己
        String sql = "select * from QQ_Login where qq_num not in (" +
                "select qq_num from QQ_Contact where belong_qq = ?) and qq_num <> ?";
        Cursor cursor = db.rawQuery(sql,new String[]{QQMainActivity.loginedUser.getNum(),QQMainActivity.loginedUser.getNum()});
        //4. 遍历数据，将数据保存到列表中返回
        List<String> list = new ArrayList<>();
        while(cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndexOrThrow("qq_num")) + "\t\t" +
                    cursor.getString(cursor.getColumnIndexOrThrow("qq_name")));//在下拉列表中显示的是联系人号码与联系人名称，中间用tab键隔开
        }
        return list;
    }
}

package cn.edu.gzy.qqdemo.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.chapter02.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import cn.edu.gzy.qqdemo.QQMainActivity;
import cn.edu.gzy.qqdemo.adapters.QQMessageAdapter;
import cn.edu.gzy.qqdemo.beans.QQMessageBean;


public class QQMessageFragment extends Fragment {
    private ArrayList<QQMessageBean> data ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_qqmessage, container, false);
        ImageView loginedImg = view.findViewById(R.id.imgLoginIcon);
        Bitmap bitmap = BitmapFactory.decodeFile(getContext().getExternalFilesDir(null)+"/"+ QQMainActivity.loginedUser.getImgUrl());
        loginedImg.setImageBitmap(bitmap);
        ListView lvMsg = view.findViewById(R.id.listview_qqmsg);
        QQMessageAdapter adapter = new  QQMessageAdapter(getMessageList(),view.getContext());
        lvMsg.setAdapter(adapter);
        registerForContextMenu(lvMsg);
        return view;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_message,menu);
    }

    private List<QQMessageBean> getMessageList(){
        data = new ArrayList<>();
        String[] names = {"刘备","曹操","孙权","张飞","关羽","赵云","诸葛亮","黄忠","魏延"};
        int[] imgs = {R.drawable.liubei,R.drawable.caocao,R.drawable.sunquan,R.drawable.zhangfei,
                R.drawable.guanyu,R.drawable.zhaoyun,R.drawable.zhugeliang,R.drawable.huangzhong,
                R.drawable.weiyan};
        for(int i = 0 ; i < names.length; i++){
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String time = sdf.format(new Date().getTime());
            QQMessageBean msg = new QQMessageBean(names[i],imgs[i],
                    "不好！", time,new Random().nextInt(10));
            data.add(msg);
        }
        return data;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuItem_gotop:
                goTop(item);
                break;
            case R.id.menuItem_deleteMsg:
                deleteMsg(item);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void deleteMsg(MenuItem item) {
        ContextMenu.ContextMenuInfo info = item.getMenuInfo();
        AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterView.AdapterContextMenuInfo) info;
        //获取选中的位置
        int position = contextMenuInfo.position;
        //获取QQ名称
        String qqName = data.get(position).getQqName();
        Toast.makeText(getContext(),item.getTitle()+" "+qqName,Toast.LENGTH_SHORT).show();
    }

    private void goTop(MenuItem item) {
        ContextMenu.ContextMenuInfo info = item.getMenuInfo();
        AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterView.AdapterContextMenuInfo) info;
        //获取选中的位置
        int position = contextMenuInfo.position;
        //获取QQ名称
        String qqName = data.get(position).getQqName();
        Toast.makeText(getContext(),item.getTitle()+" "+qqName,Toast.LENGTH_SHORT).show();
    }
}
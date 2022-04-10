package cn.edu.gzy.qqdemo.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.chapter02.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import cn.edu.gzy.qqdemo.adapters.QQMessageAdapter;
import cn.edu.gzy.qqdemo.beans.QQMessageBean;


public class QQMessageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_qqmessage, container, false);
        ListView lvMsg = view.findViewById(R.id.listview_qqmsg);
        QQMessageAdapter adapter = new  QQMessageAdapter(getMessageList(),view.getContext());
        lvMsg.setAdapter(adapter);
        return view;
    }

    private List<QQMessageBean> getMessageList(){
        ArrayList<QQMessageBean> data = new ArrayList<>();
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
}
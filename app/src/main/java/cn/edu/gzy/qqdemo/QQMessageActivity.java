package cn.edu.gzy.qqdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.chapter02.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import cn.edu.gzy.qqdemo.adapters.QQMessageAdapter;
import cn.edu.gzy.qqdemo.beans.QQMessageBean;

public class QQMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqmessage);
        ListView lvMsg = findViewById(R.id.listview_qqmsg);
        QQMessageAdapter adapter = new  QQMessageAdapter(getMessageList(),this);
        lvMsg.setAdapter(adapter);
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
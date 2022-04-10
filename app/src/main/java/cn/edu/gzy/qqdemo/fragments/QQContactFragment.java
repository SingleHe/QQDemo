package cn.edu.gzy.qqdemo.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.chapter02.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.gzy.qqdemo.adapters.QQContactAdapter;
import cn.edu.gzy.qqdemo.beans.QQContactBean;

public class QQContactFragment extends Fragment {
    private ExpandableListView epListView;
    private QQContactAdapter adapter;
    private String countries[] = new String[]{"蜀","魏","吴"};
    private String names[][] = new String[][]{
            {"刘备","关羽","张飞","赵云","黄忠","魏延"},
            {"曹操","许褚","张辽"},
            {"孙权","鲁肃","吕蒙"}
    };
    private int icons[][] = new int[][]{
            {R.drawable.liubei,R.drawable.guanyu,R.drawable.zhangfei,R.drawable.zhaoyun,R.drawable.huangzhong,R.drawable.weiyan},
            {R.drawable.caocao,R.drawable.xuchu,R.drawable.zhangliao},
            {R.drawable.sunquan,R.drawable.lusu,R.drawable.lvmeng}
    };
    private List<String> groupData;
    private Map<String, List<QQContactBean>> childData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_qqcontact, container, false);
        epListView = view.findViewById(R.id.exlv_contact);
        childData = new HashMap<String, List<QQContactBean>>();
        groupData = new ArrayList<String>();
        initialData();
        //这里不要用this.getContenxt()
        adapter = new QQContactAdapter(groupData,childData,view.getContext());
        epListView.setAdapter(adapter);
        return view;
    }

    /**
     * 初始化数据
     */
    private void initialData() {
        for (int i = 0; i < countries.length; i++) {
            groupData.add(countries[i]);
            List<QQContactBean> list = new ArrayList<QQContactBean>();
            for (int j = 0; j < names[i].length; j++){
                QQContactBean hero = new QQContactBean(names[i][j],icons[i][j],"[5G在线]","天天向上");
                list.add(hero);
            }
            childData.put(countries[i],list);
        }
    }
}
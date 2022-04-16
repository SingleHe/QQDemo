package cn.edu.gzy.qqdemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chapter02.R;

import java.util.List;

import cn.edu.gzy.qqdemo.beans.QQMessageBean;

public class QQMessageAdapter extends BaseAdapter {
    private List<QQMessageBean> data;
    private Context context;
    static class ViewHolder{
        ImageView qqIcon;
        TextView qqName;
        TextView lastTitle;
        TextView lastMsgTime;
        TextView notReadMsgCount;
    }
    public QQMessageAdapter(List<QQMessageBean> data, Context context){
        this.context = context;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_qqmessage,parent,false);
            holder = new ViewHolder();
            holder.qqIcon = convertView.findViewById(R.id.img_qqicon);
            holder.qqName = convertView.findViewById(R.id.tv_qqname);
            holder.lastTitle = convertView.findViewById(R.id.tv_lasttitle);
            holder.lastMsgTime = convertView.findViewById(R.id.tv_lastmsgtime);
            holder.notReadMsgCount =convertView.findViewById(R.id.tv_notreadmsgcount);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        QQMessageBean msg = data.get(position);
        holder.qqIcon.setImageResource(msg.getQqIcon());
        holder.qqName.setText(msg.getQqName());
        holder.lastTitle.setText(msg.getLastTitle());
        holder.lastMsgTime.setText(msg.getLastMsgTime());
        holder.notReadMsgCount.setText(msg.getNotReadMsgCount()+"");
        return convertView;
    }
}

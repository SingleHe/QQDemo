package cn.edu.gzy.qqdemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chapter02.R;

import java.util.List;
import java.util.Map;

import cn.edu.gzy.qqdemo.beans.QQContactBean;

public class QQContactAdapter extends BaseExpandableListAdapter {
    private List<String> groupData;//类别信息
    private Map<String, List<QQContactBean>> childData;//每一类联系人的具体内容
    private Context context;

    public QQContactAdapter() {
    }
    public QQContactAdapter(List<String> groupData, Map<String, List<QQContactBean>> childData, Context context){
        this.groupData = groupData;
        this.childData = childData;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    /**
     * 获取某一类别下联系人的个数
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return childData.get(groupData.get(groupPosition)).size();
    }

    /**
     * 获取某一类别下联系人的具体数据，返回的应该是一个List<QQContactBean>对象
     * @param groupPosition
     * @return
     */
    @Override
    public Object getGroup(int groupPosition) {
        return childData.get(groupData.get(groupPosition));
    }

    /**
     * 根据所点击的分类，以及分类下的点击位置，找到具体的某一个联系人
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(groupData.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contact_group,parent,false);
            holder = new GroupHolder();
            holder.tvGroupTitle = convertView.findViewById(R.id.tv_grouptitle);
            holder.tvGroupCount = convertView.findViewById(R.id.tv_groupcount);
            convertView.setTag(holder);
        }else{
            holder = (GroupHolder) convertView.getTag();
        }
        holder.tvGroupTitle.setText(groupData.get(groupPosition));
        holder.tvGroupCount.setText(getChildrenCount(groupPosition)+"");
        //holder.tvGroupCount.setText(childData.get(groupData.get(groupPosition)).size()+"");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contact_child,parent,false);
            holder = new ChildHolder();
            holder.imgIcon = convertView.findViewById(R.id.imgIcon);
            holder.tvName = convertView.findViewById(R.id.tvName);
            holder.tvOnlineMode = convertView.findViewById(R.id.tv_OnlineMode);
            holder.tvAction  = convertView.findViewById(R.id.tv_Action);
            convertView.setTag(holder);
        }else{
            holder = (ChildHolder) convertView.getTag();
        }
        QQContactBean contactBean = (QQContactBean) getChild(groupPosition,childPosition);
        holder.imgIcon.setImageResource(contactBean.getImg());
        holder.tvName.setText(contactBean.getName());
        holder.tvOnlineMode.setText(contactBean.getOnLineMode());
        holder.tvAction.setText(contactBean.getNewAction());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private static class GroupHolder {
        TextView tvGroupTitle;//类别信息
        TextView tvGroupCount;//成员数量
    }

    private static class ChildHolder {
        ImageView imgIcon;
        TextView tvName;
        TextView tvOnlineMode;
        TextView tvAction;
    }
}

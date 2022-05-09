package com.example.chapter04_content_resolver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<UserBean> userList;
    private Context context;

    public MyAdapter(List<UserBean> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvID;
        private TextView tvName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tvID);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_userlist_layout,parent,false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserBean user = userList.get(position);
        holder.tvID.setText(user.getId());
        holder.tvName.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}

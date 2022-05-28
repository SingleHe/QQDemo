package com.example.chapter06_sockettest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chapter06_sockettest.R;
import com.example.chapter06_sockettest.beans.Msg;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private List<Msg> msgList;
    public MsgAdapter(){}

    public MsgAdapter(List<Msg> msgList) {
        this.msgList = msgList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Msg msg = msgList.get(position);
        if(msg.getType() == Msg.TYPE_RECEIVED){
            //todo
        }else if(msg.getType() == Msg.TYPE_SENT){
            //todo
        }
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //todo
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

package com.example.chapter04_storage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private List<Book> bookList;

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item_layout, parent, false);
        ViewHolder holder = new ViewHolder(v1);
        ImageView bookImg = v1.findViewById(R.id.img_book);
        bookImg.setOnClickListener(v->{
            //todo  点击后图片放大操作
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bookImg.setImageResource(book.getBookImg());
        holder.bookName.setText(book.getBookName());
        holder.bookNo.setText(book.getBookNo()+"");

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView bookImg;
        TextView bookName;
        TextView bookNo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImg = itemView.findViewById(R.id.img_book);
            bookName = itemView.findViewById(R.id.tv_bookName);
            bookNo = itemView.findViewById(R.id.tv_bookNo);
        }
    }
}

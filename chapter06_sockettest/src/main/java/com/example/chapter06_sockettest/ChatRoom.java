package com.example.chapter06_sockettest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter06_sockettest.adapter.MsgAdapter;
import com.example.chapter06_sockettest.beans.Msg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatRoom extends AppCompatActivity {
    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private Button back;
    private RecyclerView msgRecyclerView;
    private String ip = "172.16.68.176";
    private String port = "6666";
    private TextView myName;
    private String recMsg;
    private boolean isSend = false;
    private boolean isRunning = false;
    private String name;
    private MsgAdapter adapter;
    private Socket socketSend;
    DataInputStream dis;
    DataOutputStream dos;
    /**
     * Looper.myLooper()获取当前线程绑定的Looper，如果没有返回null。
     * Looper.getMainLooper()返回主线程的Looper,这样就可以方便的与主线程通信。
     * 注意：在Thread的构造函数中调用Looper.myLooper只会得到主线程的Looper，因为此时新线程还未构造好
     */
    private Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(!recMsg.isEmpty()){
                addNewMessage(recMsg, Msg.TYPE_RECEIVED);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        Intent intent =  getIntent();
        name = intent.getStringExtra("name");
        inputText = findViewById(R.id.et_inputMsg);
        send = findViewById(R.id.btn_send);
        back = findViewById(R.id.btn_back);
        back.setOnClickListener(v->{
            AlertDialog.Builder dialog = new AlertDialog.Builder(ChatRoom.this);
            dialog.setTitle("退出");
            dialog.setMessage("退出登录？");
            dialog.setCancelable(false);
            dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();//结束当前的Activity
                }
            });
            dialog.setNegativeButton("否", null);
            dialog.show();
        });
        send.setOnClickListener(v->{
            String content = inputText.getText().toString();
            String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
            StringBuilder sb = new StringBuilder();
            sb.append(content).append("\n\n"+time);
            content = sb.toString();
            if(!"".equals(content)){
                Msg msg = new Msg(content,Msg.TYPE_SENT);
                msgList.add(msg);
                adapter.notifyItemInserted(msgList.size()-1);
                msgRecyclerView.scrollToPosition(msgList.size()-1);
                isSend = true;
            }
            sb.delete(0,sb.length());
        });
        /**
         * runOnUiThread()是Activity类中的方法，它用于从子线程中切换到主线程来执行一些需要再主线程执行的操作。
         */
        runOnUiThread(() -> {
            LinearLayoutManager layoutManager = new LinearLayoutManager(ChatRoom.this);
            msgRecyclerView = findViewById(R.id.recycler_view_msg);
            msgRecyclerView.setLayoutManager(layoutManager);
            adapter = new MsgAdapter(msgList);
            msgRecyclerView.setAdapter(adapter);
        });
        new Thread(()->{
            try {
                if((socketSend = new Socket(ip, Integer.parseInt(port))) == null){
                    Log.d("CoupleHe","未建立连接！");
                }else{
                    isRunning = true;
                    Log.d("CoupleHe","发送了一条消息2");
                    dis = new DataInputStream(socketSend.getInputStream());
                    dos = new DataOutputStream(socketSend.getOutputStream());
                    new Thread(new Receive(),"接收线程").start();
                    new Thread(new Send(),"发送线程").start();
                }
            } catch (Exception e) {
                e.printStackTrace();
                isRunning = false;
                /**
                 * Looper用于封装了android线程中的消息循环，默认情况下一个线程是不存在消息循环（message loop）的，
                 * 需要调用Looper.prepare()来给线程创建一个消息循环，
                 * 调用Looper.loop()来使消息循环起作用，从消息队列里取消息，处理消息。
                 * 注：写在Looper.loop()之后的代码不会被立即执行，
                 * 当调用后mHandler.getLooper().quit()后，loop才会中止，其后的代码才能得以运行。
                 * Looper对象通过MessageQueue来存放消息和事件。一个线程只能有一个Looper，对应一个MessageQueue。
                 *
                 */
                Looper.prepare();
                Toast.makeText(ChatRoom.this, "连接服务器失败！", Toast.LENGTH_SHORT).show();
                Looper.loop();
                try {
                    socketSend.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                finish();
            }
        }).start();
    }
    public void addNewMessage(String msg, int type){
        Msg message = new Msg(msg, type);
        msgList.add(message);
        adapter.notifyItemInserted(msgList.size()-1);
        msgRecyclerView.scrollToPosition(msgList.size()-1);
    }

    private class Receive implements Runnable {

        @Override
        public void run() {
            recMsg = "";
            while(isRunning){
                try {
                    recMsg = dis.readUTF();
                    Log.d("CoupleHe","收到了一条消息"+"recMsg:"+recMsg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(!TextUtils.isEmpty(recMsg)){
                    Log.d("CoupleHe", "inputStream:"+dis);
                    Message message = new Message();
                    message.obj = recMsg;
                    handler.sendMessage(message);
                }
            }
        }
    }
    private class Send implements Runnable{

        @Override
        public void run() {
            while(isRunning){
                String content = inputText.getText().toString();
                //Log.d("CoupleHe","发送了一条消息！");
                if(!"".equals(content) && isSend){
                    String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
                    StringBuilder sb = new StringBuilder();
                    sb.append(content).append("\n\n来自:").append(name).append("\n"+time);
                    content = sb.toString();
                    try {
                        dos.writeUTF(content);
                        sb.delete(0,sb.length());
                        Log.d("CoupleHe","发送了一条消息");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    isSend = false;
                    inputText.setText("");
                }
            }
        }
    }
}
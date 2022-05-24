package com.example.chapter06_httptest;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.ByteArrayOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * AsyncTask 在Android 11中已经不推荐使用了。 而是用Kotlin中的协程替代
 */
public class StrGetTask extends AsyncTask<String, Void, String> {
    private List<String> list;
    private MyRecyclerAdapter adapter;

    public StrGetTask(List<String> list, MyRecyclerAdapter adapter) {
        this.list = list;
        this.adapter = adapter;
    }

    /**
     * 访问客户端，并返回数据
     * @param params
     * @return
     */
    @Override
    protected String doInBackground(String... params) {
        try{
            HttpURLConnection con = (HttpURLConnection) new URL(params[0]).openConnection();//打开连接
            int code = con.getResponseCode();
            if(code == HttpURLConnection.HTTP_OK){
                InputStream in = con.getInputStream();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int n = 0;
                byte[] buf = new byte[1024];
                //将内容读到buffer中，读到末尾为-1
                while((n = in.read(buf)) != -1){
                    out.write(buf, 0 , n);//将每次读到字节数组(buffer变量)内容写到内存缓冲区中，起到保存每次内容的作用
                }
                String str = out.toString("UTF-8");
                Log.i("Test", str);
                return str;
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将数据显示在ListView中
     * @param s
     */
    @Override
    protected void onPostExecute(String s) {
        if(s != null){
            List<String> strList = Arrays.asList(s.split(","));//s 代表从服务器获取的数据，将其使用，进行分割得到字符串数组
            list.clear();//清空原有数据集
            list.addAll(strList);
            adapter.notifyDataSetChanged();
        }
    }
}

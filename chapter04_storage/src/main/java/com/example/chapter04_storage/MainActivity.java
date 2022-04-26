package com.example.chapter04_storage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnCreateTable;
    private Button btnDropTable;
    private Button btnInsert;
    private Button btnDelete;
    private Button btnQuery;
    private RecyclerView resultList;
    private DatabaseHelper helper;
    private List<Book> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCreateTable = findViewById(R.id.btnCreateTable);
        btnDropTable = findViewById(R.id.btnDropTable);
        btnInsert = findViewById(R.id.btnInsert);
        btnDelete = findViewById(R.id.btnDelete);
        btnQuery = findViewById(R.id.btnQuery);
        resultList = findViewById(R.id.result_list);
        helper = new DatabaseHelper(this,"BookStore.db",null,1);
        btnCreateTable.setOnClickListener(v->{
            createTable();
        });
        btnDropTable.setOnClickListener(v->{
            dropTable();
        });
        btnInsert.setOnClickListener(v->{
            insertData();
        });
        btnDelete.setOnClickListener(v->{
            deleteData();
        });
        btnQuery.setOnClickListener(v->{
            bookList = queryData();
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            resultList.setLayoutManager(layoutManager);
            BookAdapter adapter = new BookAdapter(bookList);
            resultList.setAdapter(adapter);
        });
    }
    private void createTable(){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "CREATE TABLE Books(BookNo integer primary key autoincrement, " +
                "BookName text not null," +
                "BookImg integer);";
        Log.i("chapter04:","CreateDB="+sql);
        db.execSQL(sql);
        Toast.makeText(MainActivity.this,"重建表格成功！",Toast.LENGTH_SHORT).show();
    }
    private void dropTable(){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "DROP TABLE Books";
        Log.i("chapter04:","DropTable="+sql);
        db.execSQL(sql);
        Toast.makeText(MainActivity.this,"删除表格成功！",Toast.LENGTH_SHORT).show();
    }
    private void insertData(){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "Insert into Books(BookNo,BookName,BookImg) values(?,?,?)";
        Log.i("chapter04:","insert="+sql);
        db.execSQL(sql,new Object[]{1001,"微信小程序开发",R.drawable.wechat});
        db.execSQL(sql,new Object[]{1002,"小程序电商",R.drawable.wechat1});
        db.execSQL(sql,new Object[]{1003,"hadoop",R.drawable.hadoop});
        db.execSQL(sql,new Object[]{1004,"大数据",R.drawable.bigdata});
        Toast.makeText(MainActivity.this,"插入4条数据！",Toast.LENGTH_SHORT).show();
    }
    private void deleteData(){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "delete from Books where BookNo = ?";
        Log.i("chapter04:","delete="+sql);
        db.execSQL(sql,new Object[]{1001});
        Toast.makeText(MainActivity.this,"删除一条数据！",Toast.LENGTH_SHORT).show();
    }
    private List<Book> queryData(){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from Books";
        Log.i("chapter04:","query="+sql);
        Cursor cursor = db.rawQuery(sql,null);
        bookList = new ArrayList<>();
        while(cursor.moveToNext()){
            Book book = new Book();
            book.setBookNo(cursor.getInt(cursor.getColumnIndexOrThrow("BookNo")));
            book.setBookName(cursor.getString(cursor.getColumnIndexOrThrow("BookName")));
            book.setBookImg(cursor.getInt(cursor.getColumnIndexOrThrow("BookImg")));
            bookList.add(book);
        }
        return bookList;
    }
}
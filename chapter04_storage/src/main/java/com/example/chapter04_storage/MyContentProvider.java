package com.example.chapter04_storage;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.net.URI;

public class MyContentProvider extends ContentProvider {
    private SQLiteDatabase db = null;
    private DBHelper dbHelper = null;
    //访问权限
    private static final String AUTHORITY = "com.example.chapter04_storage.mycontentprovider";
    //匹配成功后的匹配码
    private static final int MATCH_ALL_CODE = 1;
    private static final int MATCH_ONE_CODE = 2;
    private static final UriMatcher mMatcher;
    //在静态代码块中添加要匹配的URI
    static{
        //匹配不成功
        /**
         * mMatcher.addURI(authority, path, code);
         * authority:主机名，用于唯一标识一个ContentProvider,其需要和清单文件中的authorities属性相同。
         * path:路径，表示用户要操作的数据
         * code 返回值
         */
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(AUTHORITY,"users",MATCH_ALL_CODE);//匹配记录集合
        mMatcher.addURI(AUTHORITY,"users/#",MATCH_ONE_CODE);//匹配单条记录
    }
    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(this.getContext());
        db = dbHelper.getWritableDatabase();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        switch (mMatcher.match(uri)){
            //如果匹配成功，则根据条件查询数据并将查询出的cursor返回
            case MATCH_ALL_CODE:
                Log.i("Test",uri.getPath());
                cursor = db.query("users",projection,null,null,null,null,null);
                break;
            case MATCH_ONE_CODE:
                Log.i("Test",uri.getPath());
                //uri.getLastPathSegment()：String uri = "http://base_path/some_segment/id" 获取其中的id值
                cursor = db.query("users", projection, "id= ?",
                        new String[]{uri.getLastPathSegment()},
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unkonwn Uri:"+uri.toString());
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    /**
     * insert() 方法会使用 ContentValues 参数中的值，向相应表中添加新行。
     * 如果 ContentValues 参数中未包含列名称，您可能希望在提供程序代码或数据库模式中提供其默认值。
     * 此方法应返回新行的内容 URI。
     * 如要构造此方法，请使用 withAppendedId() 向表的内容 URI 追加新行的 _ID（或其他主键）值。
     * @param uri
     * @param contentValues
     * @return
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long rowId;
        int match = mMatcher.match(uri);
        if(match != MATCH_ALL_CODE){
            throw new IllegalArgumentException("Unkonwn Uri : " + uri.toString());
        }
        rowId = db.insert("users",null, contentValues);
        if(rowId > 0){
            //这个方法负责把id和contentUri连接成一个新的Uri。
            Uri insertUri = ContentUris.withAppendedId(uri,rowId);
            return insertUri;
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        switch (mMatcher.match(uri)){
            case MATCH_ALL_CODE:
                count = db.delete("users", null, null);
                break;
            case MATCH_ONE_CODE:
                count = db.delete("user","id = ?", new String[]{uri.getLastPathSegment()});
                break;
            default:
                throw new IllegalArgumentException("Unkonwn Uri:"+uri.toString());
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int count = 0;
        switch (mMatcher.match(uri)){
            case MATCH_ONE_CODE:
                count = db.update("users",contentValues, "id=?",new String[]{uri.getLastPathSegment()});
                break;
            case MATCH_ALL_CODE:
                count = db.update("users", contentValues, null, null);
                break;
            default:
                throw new IllegalArgumentException("Unkonwn Uri:" + uri.toString());
        }
        return count;
    }
}

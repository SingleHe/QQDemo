package cn.edu.gzy.qqdemo.fragments;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chapter02.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.gzy.qqdemo.QQLoginActivity;
import cn.edu.gzy.qqdemo.QQMainActivity;
import cn.edu.gzy.qqdemo.adapters.QQContactAdapter;
import cn.edu.gzy.qqdemo.beans.QQContactBean;
import cn.edu.gzy.qqdemo.dbutils.Db_Params;
import cn.edu.gzy.qqdemo.dbutils.MyDBHelper;
import cn.edu.gzy.qqdemo.dialogs.NewContactDialog;
import cn.edu.gzy.qqdemo.dialogs.OnDialogCompleted;

public class QQContactFragment extends Fragment implements OnDialogCompleted {
    private ExpandableListView epListView;
    private QQContactAdapter adapter;
    private ImageView loginedImg;//修改联系人界面对应的登录头像
    private TextView addContactBtn;//添加联系人按钮
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
        //获取显示登录用户头像的图片控件
        loginedImg = view.findViewById(R.id.imgLoginIcon);
        //在登录Activity中，已经将登录账号的数据查询出来设置在了QQMainActivity的静态内部成员loginedUser对象中。
        //loginedImg.setImageResource(QQMainActivity.loginedUser.getImg());
        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+QQMainActivity.loginedUser.getImgUrl());
        Log.d("QQContactFragment","获取的外部存储路径为："+Environment.getExternalStorageDirectory()+ QQMainActivity.loginedUser.getImgUrl());
        loginedImg.setImageBitmap(bitmap);
        addContactBtn = view.findViewById(R.id.tv_AddBtn);
        //点击添加联系人时，弹窗！
        addContactBtn.setOnClickListener(v->{
            showNewContactDialog();
        });
        epListView = view.findViewById(R.id.exlv_contact);
        childData = new HashMap<String, List<QQContactBean>>();
        groupData = new ArrayList<String>();
        //initialData();//程序设定的联系人数据
        getContacts();//访问数据库获取联系人数据
        //这里不要用this.getContenxt()
        adapter = new QQContactAdapter(groupData,childData,view.getContext());
        epListView.setAdapter(adapter);
        registerForContextMenu(epListView);//在可扩展列表商注册环境上下文菜单。
        return view;
    }

    /**
     * 用户常按组数据项时，不显示上下文菜单
     * 当用户长按子数据项时，显示菜单
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;
        long packedPosition = info.packedPosition;
        //判断是组数据项还是子数据项，0表示组数据项，1表示子数据项
        int packedPositionType = ExpandableListView.getPackedPositionType(packedPosition);
        if (packedPositionType == 1){
            getActivity().getMenuInflater().inflate(R.menu.menu_contact,menu);
        }
    }

    /**
     * 上下文菜单项被选中触发事件
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            //如果是删除联系人
            case R.id.menuitem_delContact:
                deleteContact(item);//删除联系人
                break;
            case R.id.menuitem_newcontact:
                showNewContactDialog();//弹出添加联系人对话框
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * 弹出新建联系窗口
     */
    private void showNewContactDialog() {
        NewContactDialog dialog = new NewContactDialog();
        //getFragmentManager()过期了，改为以下两种，区别如下：
        /**
         * 1.use getChildFragmentManager() Return a private FragmentManager for
         * placing and managing Fragments inside of this Fragment.
         * 2.use getParentFragmentManager() Return the FragmentManager for interacting
         * with fragments associated with this fragment's activity.
         *
         * totally： if you deal with fragments inside a fragment you will use the first one and
         * if you deal with fragments inside an activity you will use the second one.
         */
        dialog.show(getChildFragmentManager(),"NewContact");
        dialog.setOnDialogCompleted(this);
    }

    /**
     * 删除联系人
     * @param item
     */
    private void deleteContact(MenuItem item) {
        //1. 获得要删除的选项
        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();
        //2. 获得选中的外层组项
        int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
        //3. 获得指定组项下的子选项
        int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition);
        //4. 从联系人Map集合中找到对应的数据
        QQContactBean bean = childData.get(groupData.get(groupPos)).get(childPos);
        //5. 获取数据库
        MyDBHelper helper = new MyDBHelper(getContext(),Db_Params.DB_NAME,null,Db_Params.DB_VER);
        SQLiteDatabase db = helper.getWritableDatabase();//获取数据库对象
        String sql = "delete from QQ_Contact where qq_num = ? and belong_qq = ?";
        //6. 执行删除操作
        db.execSQL(sql,new Object[]{bean.getNum(),QQMainActivity.loginedUser.getNum()});
        //7. 从缓存好的数据中childData将该项联系人删除
        //childData.remove(childData.get(groupData.get(groupPos)).get(childPos));
        childData.get(groupData.get(groupPos)).remove(childPos);//删除指定位置的数据就可以了
        //8. 通知视图更新数据
        adapter.notifyDataSetChanged();
    }

    /**
     * 初始化数据,程序设定
     */
    private void initialData() {
        getPhoneContacts();//初始化本机联系人数据
        for (int i = 0; i < countries.length; i++) {
            groupData.add(countries[i]);
            List<QQContactBean> list = new ArrayList<QQContactBean>();
            for (int j = 0; j < names[i].length; j++){
                QQContactBean hero = new QQContactBean(names[i][j],Db_Params.PHOTO_URL+icons[i][j]+".jpg","[5G在线]","天天向上");
                list.add(hero);
            }
            childData.put(countries[i],list);
        }
    }

    /**
     * 从数据库中查询出登录用户的联系人
     */
    private void getContacts(){
        //注意修改数据库版本，改为比原先版本更大的值
        MyDBHelper helper = new MyDBHelper(getContext(), Db_Params.DB_NAME, null, Db_Params.DB_VER);
        /*（1）getWritableDatabase()方法以读写方式打开数据库。
        一旦数据库的磁盘空间满了，数据库就只能读而不能写，此时用getWritableDatabase()打开数据库就会出错。
        （2）getReadableDatabase()方法先调用getWritableDatabase()先以读写方式打开数据库。
        倘若使用如果数据库的磁盘空间满了，就会打开失败，当打开失败后会继续尝试以只读方式打开数据库。
        一般避免报错都是用getReadableDatabase()方法*/
        SQLiteDatabase db = helper.getReadableDatabase();
        //1. 查询联系人所属的分类
        String sql = "select distinct belong_country from view_contact where belong_qq=?";
        Cursor groupCursor = db.rawQuery(sql,new String[]{QQMainActivity.loginedUser.getNum()});
        while(groupCursor.moveToNext()){
            String countryName = groupCursor.getString(groupCursor.getColumnIndexOrThrow("belong_country"));
            Log.d("QQContactFragment","显示联系人列表时获得的国家类别数据："+countryName);
            groupData.add(countryName);
            //2.查询登录用户的联系人列表
            sql = "select * from view_contact where belong_qq = ? and belong_country = ?";
            Cursor cursor = db.rawQuery(sql, new String[]{QQMainActivity.loginedUser.getNum(), countryName});
            List<QQContactBean> list = new ArrayList<>();
            while(cursor.moveToNext()){
                QQContactBean bean= new QQContactBean(
                        cursor.getString(cursor.getColumnIndexOrThrow("qq_name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("qq_imgurl")),
                        cursor.getString(cursor.getColumnIndexOrThrow("qq_online")),
                        cursor.getString(cursor.getColumnIndexOrThrow("qq_action")),
                        cursor.getString(cursor.getColumnIndexOrThrow("qq_num")),
                        cursor.getString(cursor.getColumnIndexOrThrow("belong_country"))
                );
                list.add(bean);
            }
            childData.put(countryName, list);
        }
    }

    /**
     * 实现自动刷新新增联系人列表
     * @param dialogResult
     * @param dialogId
     */
    @Override
    public void dialogCompleted(String dialogResult, int dialogId) {
        //1. 清空原有的groupData 和 childData
        switch (dialogId){
            case 0:
                groupData.clear();
                childData.clear();
                getContacts();//重新获取联系人
                adapter.notifyDataSetChanged();//通知适配器数据更新
                break;
        }
    }

    /**
     * 获取本机联系人
     */
    private void getPhoneContacts(){
        groupData.add("本机联系人");
        List<QQContactBean> list = new ArrayList<>();
        ContentResolver contentResolver = getContext().getContentResolver();
        Cursor cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        while(cursor.moveToNext()){
            //取得联系人名字
            String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
            //取得联系人ID
            String contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
            //根据联系人ID查询对应的电话号码
            Cursor phoneNumbers = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                    null,
                    null);
            //取得第一个电话号码（可能存在多个号码）
            String strPhoneNumber = null;
            if(phoneNumbers.moveToNext()){
                strPhoneNumber = phoneNumbers.getString(phoneNumbers.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            phoneNumbers.close();
            //根据联系人ID查询对应的Email
            Cursor emails = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + contactId,
                    null,
                    null);
            //取得第一个电话号码（可能存在多个号码）
            String strEmail = null;
            if(emails.moveToNext()){
                strEmail = emails.getString(emails.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.DATA));
            }
            emails.close();
            //取得contact_id的URI
            Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,
                    Long.parseLong(contactId));
            QQContactBean qqContactBean = new QQContactBean(strPhoneNumber, name, uri.toString(), "手机", strEmail, "本机联系人");
            list.add(qqContactBean);
        }
        childData.put("本机联系人", list);
        cursor.close();
    }
}
package com.ericwyn.libraryms.dbUtil.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ericwyn.libraryms.dbUtil.DbHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * readerDB读者信息表的帮助类
 * Created by ericwyn on 17-3-26.
 */

public class ReaderDBHelper{
    private static final String TABLE_NAME="readerDB";

    /**
     * 增加用户数据
     * @param context   上下文
     * @param maps  需要增加的用户的集合
     * @return  返回状态代码
     */
    public static int addReaders(Context context,ArrayList<HashMap<String,Object>> maps){
        DbHelper dbHelper=new DbHelper(context,DbHelper.DB_NAME,null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        try {
            for(HashMap<String ,Object> map:maps){
                int readerId=(int)map.get("readerId");
                String readerPw=(String)map.get("readerPw");
                ContentValues values=new ContentValues();
                values.put("readerId",readerId);
                values.put("readerPw",readerPw);
                db.insert(TABLE_NAME,null,values);
                values.clear();
            }
            db.close();
            return 0;
        }catch (Exception e){
            db.close();
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 删除用户的方法
     * @param context   上下文
     * @param deleteReaderIds   需要删除的读者的id
     * @return  返回状态代码
     */
    public static int deleteReaderById(Context context,ArrayList<String> deleteReaderIds){
        DbHelper dbHelper=new DbHelper(context,DbHelper.DB_NAME,null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        for(String id:deleteReaderIds){
            db.delete(TABLE_NAME,"readerId = ?",new String[]{id});
        }
        return 0;
    }

    /**
     * 更新用户密码的方法
     * @param context   上下文
     * @param readerId  需要修改的id
     * @param newPw 新的密码
     * @return  返回的状态
     */
    public static int chancePwByReaderId(Context context,String readerId,String newPw){
        DbHelper dbHelper=new DbHelper(context,DbHelper.DB_NAME,null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("readerPw",newPw);
        db.update(TABLE_NAME,values,"readerId=?",new String[]{readerId});
        values.clear();
        return 0;
    }


    /**
     * 查询所有读者的信息列表(包含读者的id和登录密码)
     * @param context   上下文
     * @return  返回的包含map对象的集合
     */
    public static ArrayList<HashMap<String,Object>> searchAllReader(Context context){
        ArrayList<HashMap<String,Object>> maps=new ArrayList<>();
        DbHelper dbHelper=new DbHelper(context,DbHelper.DB_NAME,null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query(TABLE_NAME,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                HashMap<String ,Object> map=new HashMap<>();
                int readerId=cursor.getInt(cursor.getColumnIndex("readerId"));
                String readerPw=cursor.getString(cursor.getColumnIndex("readerPw"));
                map.put("readerId",readerId);
                map.put("readerPw",readerPw);
                maps.add(map);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return maps;
    }

    /**
     * 验证读者身份的方法
     * @param context   上下文
     * @param readerId  读者id
     * @param readerPw  密码
     * @return  返回的状态码，0代表验证正确，1代表密码错误，2代表用户名不存在,默认返回用户不存在
     */
    public static int checkReaderPw(Context context,int readerId,String readerPw){
        DbHelper dbHelper=new DbHelper(context,DbHelper.DB_NAME,null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query(TABLE_NAME,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                if(cursor.getInt(cursor.getColumnIndex("readerId"))==readerId){
                    if(cursor.getString(cursor.getColumnIndex("readerPw")).equals(readerPw)){
                        return 0;       //代表验证正确
                    }else {
                        return 1;       //代表密码错误
                    }
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return 2;                       //代表用户名不存在
    }

}

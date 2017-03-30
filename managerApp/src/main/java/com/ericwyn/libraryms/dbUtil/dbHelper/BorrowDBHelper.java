package com.ericwyn.libraryms.dbUtil.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ericwyn.libraryms.dbUtil.DbHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * borrowDB借书表的工具类
 * Created by ericwyn on 17-3-27.
 */

public class BorrowDBHelper {
    private static final String TABLE_NAME="borrowDB";


    /**
     * 增加一条借阅记录
     * @param context   上下文
     * @param maps  增加的借阅的信息,以list形式传入
     * @return  返回状态值
     */
    public static int addBorrowRec(Context context, ArrayList<HashMap<String,Object>> maps){
        DbHelper dbHelper=new DbHelper(context,DbHelper.DB_NAME,null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        for(HashMap<String ,Object> map:maps){
            int readerId=(int)map.get("readerId");
            String bookId=(String)map.get("bookId");
            ContentValues values=new ContentValues();
            values.put("readerId",readerId);
            values.put("bookId",bookId);
            db.insert(TABLE_NAME,null,values);
            values.clear();
        }
        return 0;
    }

    /**
     * 删除借书记录的方法
     * @param context   上下文
     * @param maps  列表
     * @return  返回的值
     */
    public static int deleteBorrowRec(Context context,ArrayList<HashMap<String,Object>> maps){
        DbHelper dbHelper=new DbHelper(context,DbHelper.DB_NAME,null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        for(HashMap map:maps){
            int readerId=(int)map.get("readerId");
            String bookId=(String)map.get("bookId");
            db.delete(TABLE_NAME,"readerId = ? AND bookId= ?",new String[]{""+readerId,bookId});
        }
        return 0;
    }

    /**
     * 通过读者id查询这个人借阅的全部书籍
     * @param context   上下文
     * @param readerId  读者id
     * @return  返回状态码
     */
    public static ArrayList<HashMap<String,Object>> searchBorrowByReaderId(Context context,int readerId){
        ArrayList<HashMap<String,Object>> maps=new ArrayList<>();
        DbHelper dbHelper=new DbHelper(context,DbHelper.DB_NAME,null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query(TABLE_NAME,null,null,null,null,null,null);

        if(cursor.moveToFirst()){
            int readerIdFlag=0;
            do{
                if((readerIdFlag=cursor.getInt(cursor.getColumnIndex("readerId")))==readerId){
                    HashMap<String ,Object> map=new HashMap<>();
                    String bookId=cursor.getString(cursor.getColumnIndex("bookId"));
                    map.put("readerId",readerId);
                    map.put("bookId",bookId);
                    maps.add(map);
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return maps;
    }

    /**
     * 查询借阅了某本书的所有人
     * @param context   上下文
     * @param bookId  需要查询的书籍的id
     * @return  返回的值是一个List，和增加数据一样
     */
    public static ArrayList<HashMap<String,Object>> searchBorrowByBookId(Context context,String bookId){
        ArrayList<HashMap<String,Object>> maps=new ArrayList<>();
        DbHelper dbHelper=new DbHelper(context,DbHelper.DB_NAME,null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query(TABLE_NAME,null,null,null,null,null,null);

        if(cursor.moveToFirst()){
            String bookIdFlag="";
            do{
                if((bookIdFlag=cursor.getString(cursor.getColumnIndex("bookId"))).equals(bookId)){
                    HashMap<String ,Object> map=new HashMap<>();
                    String readerId=cursor.getString(cursor.getColumnIndex("readerId"));
                    map.put("readerId",readerId);
                    map.put("bookId",bookId);
                    maps.add(map);
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return maps;
    }


}

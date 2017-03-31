package com.ericwyn.libraryms.dbUtil.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ericwyn.libraryms.dbUtil.DbHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * sortDB图书类别表的帮助类
 * Created by ericwyn on 17-3-26.
 */

public class SortDBHelper{
    private static final String TABLE_NAME="sortDB";

    /**
     * 增加图书类型
     * @param context   上下文
     * @param maps  需要增加图书类型的集合
     * @return  返回状态代码
     */
    public static int addSorts(Context context,ArrayList<HashMap<String,Object>> maps){
        DbHelper dbHelper=new DbHelper(context,DbHelper.DB_NAME,null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        try {
            for(HashMap<String ,Object> map:maps){
                int readerId=(int)map.get("sortId");
                String readerPw=(String)map.get("sortName");
                ContentValues values=new ContentValues();
                values.put("sortId",readerId);
                values.put("sortName",readerPw);
                db.insert(TABLE_NAME,null,values);
                values.clear();
            }
            db.close();
            dbHelper.close();
            return 0;
        }catch (Exception e){
            db.close();
            dbHelper.close();
            e.printStackTrace();
            return -1;
        }

    }

    /**
     * 删除类别的方法
     * @param context   上下文
     * @param deleteSortIds   需要删除的类别的Id
     * @return  返回状态代码
     */

    public static int deleteSortById(Context context,ArrayList<Integer> deleteSortIds){
        DbHelper dbHelper=new DbHelper(context,DbHelper.DB_NAME,null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        for(Integer id:deleteSortIds){
            String idS=""+id;
            db.delete(TABLE_NAME,"sortId = ?",new String[]{idS});
        }
        db.close();
        dbHelper.close();
        return 0;
    }


    /**
     * 删除类别的方法
     * @param context   上下文
     * @param deleteSortNames   需要删除的类别的名称
     * @return  返回状态代码
     */
    public static int deleteSortByName(Context context,ArrayList<String> deleteSortNames){
        DbHelper dbHelper=new DbHelper(context,DbHelper.DB_NAME,null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        for(String name:deleteSortNames){
            db.delete(TABLE_NAME,"sortName = ?",new String[]{name});
        }
        db.close();
        dbHelper.close();
        return 0;
    }

//    感觉类别没什么好更新的，那么就不写了吧
//    /**
//     * 更新用户密码的方法
//     * @param context   上下文
//     * @param readerId  需要修改的id
//     * @param newPw 新的密码
//     * @return  返回的状态
//     */
//    public static int chancePw(Context context,String readerId,String newPw){
//        DbHelper dbHelper=new DbHelper(context,DbHelper.DB_NAME,null,1);
//        SQLiteDatabase db=dbHelper.getWritableDatabase();
//        ContentValues values=new ContentValues();
//        values.put("readerPw",newPw);
//        db.update(TABLE_NAME,values,"readerId=?",new String[]{readerId});
//        values.clear();
//        return 0;
//    }


    /**
     * 查询所有类别
     * @param context   上下文
     * @return  返回的包含map对象的集合
     */
    public static ArrayList<HashMap<String,Object>> searchAllSort(Context context){
        ArrayList<HashMap<String,Object>> maps=new ArrayList<>();
        DbHelper dbHelper=new DbHelper(context,DbHelper.DB_NAME,null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query(TABLE_NAME,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                HashMap<String ,Object> map=new HashMap<>();
                int sortId=cursor.getInt(cursor.getColumnIndex("sortId"));
                String sortName=cursor.getString(cursor.getColumnIndex("sortName"));
                map.put("sortId",sortId);
                map.put("sortName",sortName);
                maps.add(map);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        dbHelper.close();
        return maps;
    }

    /**
     * 返回所有的类别的名单，这个方法用于Spinner的下拉选择列表
     * @param context
     * @return
     */
    public static String[] searchAllSortName(Context context){
        ArrayList<String> maps=new ArrayList<>();
        DbHelper dbHelper=new DbHelper(context,DbHelper.DB_NAME,null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query(TABLE_NAME,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String sortName=cursor.getString(cursor.getColumnIndex("sortName"));
                maps.add(sortName);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        dbHelper.close();
        String[] back=new String[maps.size()];
        maps.toArray(back);
        return back;
    }


    /**
     * 通过sortId，返回类别的名称，使用时候可能需要先判断是否为null
     * @param sortId   类别id
     * @return  返回一个String，可能为null
     */
    public static String getSortNameById(Context context,int sortId){
        DbHelper dbHelper=new DbHelper(context,DbHelper.DB_NAME,null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query(TABLE_NAME,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                if(cursor.getInt(cursor.getColumnIndex("sortId"))==sortId){
                    String back=cursor.getString(cursor.getColumnIndex("sortName"));
                    cursor.close();
                    db.close();
                    dbHelper.close();
                    return back;
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        dbHelper.close();
        return null;
    }

    public static int getSortIdByName(Context context,String sortName){
        DbHelper dbHelper=new DbHelper(context,DbHelper.DB_NAME,null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query(TABLE_NAME,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                if(cursor.getString(cursor.getColumnIndex("sortName")).equals(sortName)){
                    int back=cursor.getInt(cursor.getColumnIndex("sortId"));
                    cursor.close();
                    db.close();
                    dbHelper.close();
                    return back;
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        dbHelper.close();
        return -1;
    }


}

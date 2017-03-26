package com.ericwyn.libraryms.dbUtil.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ericwyn.libraryms.dbUtil.DbHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * bookDB书籍信息表的帮助类
 * Created by ericwyn on 17-3-26.
 */

public class BookDBHelper {
    private static final String TABLE_NAME="bookDB";

    /**
     * 增加图书类型
     * @param context   上下文
     * @param maps  需要增加图书类型的集合，包含bookId，bookName，sortId，bookAllNum，bookOverNum
     * @return  返回状态代码
     */
    public static int addBooks(Context context, ArrayList<HashMap<String,Object>> maps){
        DbHelper dbHelper=new DbHelper(context,DbHelper.DB_NAME,null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        for(HashMap<String ,Object> map:maps){
            int bookId      =(int)map.get("bookId");
            int sortId      =(int)map.get("sortId");
            String bookName =(String)map.get("bookName");
            int bookAllNum  =(int)map.get("bookAllNum");
            int bookOverNum =(int)map.get("bookOverNum");
            ContentValues values=new ContentValues();
            values.put("bookId",bookId);
            values.put("sortId",sortId);
            values.put("bookName",bookName);
            values.put("bookAllNum",bookAllNum);
            values.put("bookOverNum",bookOverNum);
            db.insert(TABLE_NAME,null,values);
            values.clear();
        }
        return 0;
    }

    /**
     * 通过bookId来删除书籍的方法
     * @param context   上下文
     * @param deleteBookIds     需要删除的书籍的bookId的集合
     * @return
     */
    public static int deleteSortByBookId(Context context,ArrayList<Integer> deleteBookIds){
        DbHelper dbHelper=new DbHelper(context,DbHelper.DB_NAME,null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        for(Integer id:deleteBookIds){
            String idS=""+id;
            db.delete(TABLE_NAME,"bookId = ?",new String[]{idS});
        }
        return 0;
    }


    /**
     * 删除bookName来删除书籍的方法
     * @param context   上下文
     * @param deleteBookNames   需要删除的书籍的bookName的集合
     * @return  返回状态代码
     */
    public static int deleteSortByBookName(Context context,ArrayList<String> deleteBookNames){
        DbHelper dbHelper=new DbHelper(context,DbHelper.DB_NAME,null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        for(String name:deleteBookNames){
            db.delete(TABLE_NAME,"bookName = ?",new String[]{name});
        }
        return 0;
    }

    /**
     * 删除sortId来删除书籍的方法
     * @param context   上下文
     * @param deleteSortId   需要删除的书籍的sortId的集合
     * @return  返回状态代码
     */
    public static int deleteSortBySortId(Context context,ArrayList<Integer> deleteSortId){
        DbHelper dbHelper=new DbHelper(context,DbHelper.DB_NAME,null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        for(int sortId:deleteSortId){
            String sortIdS=""+sortId;
            db.delete(TABLE_NAME,"sortId = ?",new String[]{sortIdS});
        }
        return 0;
    }

    /**
     * 通过BookId来修改书籍的信息
     * @param context   上下文
     * @param bookId    书籍的Id
     * @param bookNewMap    书籍新的信息map
     * @return  返回状态代码
     */
    public static int chanceByBookId(Context context,int bookId,HashMap<String,Object> bookNewMap){
        DbHelper dbHelper=new DbHelper(context,DbHelper.DB_NAME,null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
//        int bookId      =(int)bookNewMap.get("bookId");
        int sortId      =(int)bookNewMap.get("sortId");
        String bookName =(String)bookNewMap.get("bookName");
        int bookAllNum  =(int)bookNewMap.get("bookAllNum");
        int bookOverNum =(int)bookNewMap.get("bookOverNum");
        ContentValues values=new ContentValues();
//        values.put("boolId",bookId);
        values.put("sortId",sortId);
        values.put("bookName",bookName);
        values.put("bookAllNum",bookAllNum);
        values.put("bookOverNum",bookOverNum);
        db.update(TABLE_NAME,values,"readerId=?",new String[]{""+bookId});
        values.clear();
        return 0;
    }

    /**
     * 查找并返回所有的书籍信息
     * @param context   上下文
     * @return  返回书籍信息的集合
     */
    public static ArrayList<HashMap<String,Object>> searchAllBook(Context context){
        ArrayList<HashMap<String,Object>> maps=new ArrayList<>();
        DbHelper dbHelper=new DbHelper(context,DbHelper.DB_NAME,null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query(TABLE_NAME,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                HashMap<String ,Object> map=new HashMap<>();
                int bookId      =cursor.getInt(cursor.getColumnIndex("bookId"));
                int sortId      =cursor.getInt(cursor.getColumnIndex("sortId"));
                String bookName =cursor.getString(cursor.getColumnIndex("bookName"));
                int bookAllNum  =cursor.getInt(cursor.getColumnIndex("bookAllNum"));
                int bookOverNum =cursor.getInt(cursor.getColumnIndex("bookOverNum"));

                map.put("bookId",bookId);
                map.put("sortId",sortId);
                map.put("bookName",bookName);
                map.put("bookAllNum",bookAllNum);
                map.put("bookOverNum",bookOverNum);

                maps.add(map);
            }while (cursor.moveToFirst());
        }
        return maps;
    }


    /**
     * 通过SortId来查找并返回所有的书籍信息
     * @param context   上下文
     * @return  返回书籍信息的集合
     */
    public static ArrayList<HashMap<String,Object>> searchBookBySortId(Context context,int searchSortId){
        ArrayList<HashMap<String,Object>> maps=new ArrayList<>();
        DbHelper dbHelper=new DbHelper(context,DbHelper.DB_NAME,null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query(TABLE_NAME,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                if(cursor.getInt(cursor.getColumnIndex("sortId"))==searchSortId){
                    HashMap<String ,Object> map=new HashMap<>();
                    int bookId      =cursor.getInt(cursor.getColumnIndex("bookId"));
                    int sortId      =cursor.getInt(cursor.getColumnIndex("sortId"));
                    String bookName =cursor.getString(cursor.getColumnIndex("bookName"));
                    int bookAllNum  =cursor.getInt(cursor.getColumnIndex("bookAllNum"));
                    int bookOverNum =cursor.getInt(cursor.getColumnIndex("bookOverNum"));

                    map.put("bookId",bookId);
                    map.put("sortId",sortId);
                    map.put("bookName",bookName);
                    map.put("bookAllNum",bookAllNum);
                    map.put("bookOverNum",bookOverNum);

                    maps.add(map);
                }

            }while (cursor.moveToFirst());
        }
        return maps;
    }
}

package com.ericwyn.libraryms.dbUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * bookDB图书信息表的帮助类
 * Created by ericwyn on 17-3-26.
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="userDB";
    private static final String CREATE_bookDB="create table bookDB(" +
            "bookId integer primary key," +
            "bookName text," +
            "sortId integer," +
            "bookAllNum integer," +
            "bookOverNum integer" +
            ")";

    private static final String CREATE_sortDB="create table sortDB(" +
            "sortId integer primary key," +
            "sortName text  NOT NULL" +
            ")";

    private static final String CREATE_readerDB="create table readerDB(" +
            "readerId integer primary key," +
            "readerPw text  NOT NULL" +
            ")";

    private static final String CREATE_borrowDB="create table borrowDB(" +
            "readerId integer NOT NULL," +
            "bookId integer NOT NULL" +
            ")";

    private Context mContext;

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_bookDB);
        Log.i("数据库操作","成功创建bookDB表");
        db.execSQL(CREATE_readerDB);
        Log.i("数据库操作","成功创建readDB表");
        db.execSQL(CREATE_sortDB);
        Log.i("数据库操作","成功创建sortDB表");
        db.execSQL(CREATE_borrowDB);
        Log.i("数据库操作","成功创建borrowDB表");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

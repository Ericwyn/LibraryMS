package com.ericwyn.studentapp.dbUtil.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * readerDB读者信息表的帮助类
 * Created by ericwyn on 17-3-26.
 */

public class ReaderDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="readerDB";
    private static final String CREATE_readerDB="create table readerDB(" +
            "readerId integer primary key," +
            "readerPw text" +
            ")";
    private Context mContext;

    public ReaderDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_readerDB);
        Log.i("数据库操作","成功创建readerDB表");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

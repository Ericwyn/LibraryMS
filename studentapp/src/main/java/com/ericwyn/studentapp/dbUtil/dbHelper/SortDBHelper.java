package com.ericwyn.studentapp.dbUtil.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * sortDB图书类别表的帮助类
 * Created by ericwyn on 17-3-26.
 */

public class SortDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="readerDB";
    private static final String CREATE_sortDB="create table sortDB(" +
            "sortId integer primary key," +
            "sortName text" +
            ")";
    private Context mContext;

    public SortDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_sortDB);
        Log.i("数据库操作","成功创建sortDB表");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

package com.ericwyn.studentapp.dbUtil;

import android.content.Context;

/**
 *
 *数据库操作的工具类
 * Created by ericwyn on 17-3-26.
 */

public class DataBaseUtil {
    /**
     * 数据库初始化函数，创建readerDB、bookDB和sortDB
     */
    public static void dBIni(Context context){
        DbHelper dbHelper=new DbHelper(context,"userDB",null,1);
        dbHelper.getWritableDatabase();
    }
}

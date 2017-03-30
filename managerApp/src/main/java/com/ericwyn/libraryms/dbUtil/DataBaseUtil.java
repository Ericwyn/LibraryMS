package com.ericwyn.libraryms.dbUtil;

import android.content.Context;

import com.ericwyn.libraryms.dbUtil.dbHelper.BookDBHelper;
import com.ericwyn.libraryms.dbUtil.dbHelper.BorrowDBHelper;

import java.util.ArrayList;
import java.util.HashMap;

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


    /**
     * 借阅一本书
     * @param context   上下文
     * @param readerId 读者的id
     * @param bookId    书籍的id
     * @return      返回的状态码,0代表借书完成，1代表书籍无剩余，2代表无书籍
     *              因为默认是登录了的用户才能调用这个方法，所以也就不用考虑那么多用户密码是否正确了
     */
    public static int borrowABook(Context context,int readerId,String bookId){

        //验证书籍是否还剩余，返回0表示剩余，返回1表示无剩余，返回2表示书籍不存在
        int backFlag;
        if((backFlag=BookDBHelper.isBookOver(context,bookId))==1){
            return 1;
        }else if(backFlag==2){
            return 2;
        }
        //对应bookId书籍减少一本库存
        BookDBHelper.borrowABookId(context,bookId);
        //组装borrowDB数据
        ArrayList<HashMap<String ,Object>> maps=new ArrayList<>();
        HashMap<String,Object> map=new HashMap<>();
        map.put("readerId",readerId);
        map.put("bookId",bookId);
        maps.add(map);
        //为borrowDB增加一条数据
        BorrowDBHelper.addBorrowRec(context,maps);
        return 0;
    }




}

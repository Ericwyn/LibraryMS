package com.ericwyn.libraryms.updataDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ericwyn.libraryms.managerApp.BaseDataManagerFragment;
import com.ericwyn.libraryms.R;
import com.ericwyn.libraryms.dbUtil.dbHelper.BookDBHelper;
import com.ericwyn.libraryms.dbUtil.dbHelper.BorrowDBHelper;
import com.ericwyn.libraryms.dbUtil.dbHelper.ReaderDBHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *新增读者的Dialog
 * Created by ericwyn on 17-3-31.
 */

public class UpdataReaderDialogBuilder extends AlertDialog.Builder {
    private Context mContext;
    private TextView readerId;
    private TextView jinggao;

    private int readerIdFlag;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    private HashMap<String,Object> dataMap;

    public UpdataReaderDialogBuilder(@NonNull Context context, HashMap<String,Object> map) {
        super(context);
        mContext=context;
        this.dataMap=map;
        readerIdFlag=(int)dataMap.get("readerId");
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.updata_reader_dialog, null);
        this.setView(view);
        //设置标题
        super.setTitle("读者详情");
        ArrayList<HashMap<String,Object>> data= BorrowDBHelper.searchBorrowByReaderId(mContext,readerIdFlag);

        final ArrayList<String> dataList=getdata(readerIdFlag);
        if(dataList.size()==0){
            jinggao.setText("该用户没有未还书籍");
        }
        arrayAdapter=new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,dataList);

        listView.setAdapter(arrayAdapter);

        //设置积极按钮
        super.setPositiveButton("重置密码", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(ReaderDBHelper.chancePwByReaderId(mContext,""+readerIdFlag,""+readerIdFlag)==0){
                    Toast.makeText(mContext,"重置读者密码成功",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(mContext,"重置读者密码成功",Toast.LENGTH_SHORT).show();
                }

            }
        });

        super.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        //设置消极按钮
        super.setNegativeButton("删除读者", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dataList.size()!=0){
                    Toast.makeText(mContext,"用户仍有未还书籍，无法删除用户",Toast.LENGTH_SHORT).show();
                }else {
                    ArrayList<String> listFlag=new ArrayList<String>();
                    listFlag.add(""+readerIdFlag);
                    if(ReaderDBHelper.deleteReaderById(mContext,listFlag)==0){
                        Toast.makeText(mContext,"删除读者成功",Toast.LENGTH_SHORT).show();
                        BaseDataManagerFragment.updata();
                    }else {
                        Toast.makeText(mContext,"删除读者失败，请检查",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    public ArrayList<String > getdata(int readerId){
        ArrayList<String> list=new ArrayList<>();
        ArrayList<HashMap<String,Object>> maps=BorrowDBHelper.searchBorrowByReaderId(mContext,readerId);
        for(HashMap map:maps){
            String bookIdFlag=(String) map.get("bookId");
            HashMap<String,Object> mapFlag= BookDBHelper.searchBookById(mContext,bookIdFlag);
            list.add((String)mapFlag.get("bookName"));
        }

        return list;
    }

    //载入布局，绑定各种元素
    @Override
    public AlertDialog.Builder setView(View view) {
        readerId=(TextView) view.findViewById(R.id.textView_readerId_updataReaderDialog);
        listView=(ListView)view.findViewById(R.id.listView_readerBooks_updataReaderDialog);
        readerId.setText(""+readerIdFlag);
        jinggao=(TextView)view.findViewById(R.id.textView7);
        return super.setView(view);
    }

}

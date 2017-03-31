package com.ericwyn.libraryms.addDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.ericwyn.libraryms.R;
import com.ericwyn.libraryms.dbUtil.dbHelper.ReaderDBHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *新增读者的Dialog
 * Created by ericwyn on 17-3-31.
 */

public class AddReaderDialogBuilder extends AlertDialog.Builder {
    private Context mContext;
    private TextInputLayout readerId;
    private TextInputLayout readerPw;
    private TextInputLayout readerPwEnsure;

    public AddReaderDialogBuilder(@NonNull Context context) {
        super(context);
        mContext=context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.add_reader_dialog, null);
        this.setView(view);
        //设置标题
        super.setTitle("新增读者");

        //设置积极按钮
        super.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ArrayList<HashMap<String,Object>> maps=new ArrayList<HashMap<String, Object>>();
                HashMap<String,Object> map=new HashMap<String, Object>();
                String readerIdFlag=readerId.getEditText().getText().toString();
                String readerPwFlag=readerPw.getEditText().getText().toString();
                String readerPwEnsureFlag=readerPwEnsure.getEditText().getText().toString();
                if(readerPwFlag.equals(readerPwEnsureFlag)){
                    if(!readerIdFlag.equals("") && !readerPwFlag.equals("")){
                        map.put("readerId",Integer.parseInt(readerIdFlag));
                        map.put("readerPw",readerPwFlag);
                        maps.add(map);
                        if(ReaderDBHelper.addReaders(mContext,maps)!=-1){
                            Toast.makeText(mContext,"新增读者"+readerIdFlag+"数据成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(mContext,"新增读者数据失败，请检查",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(mContext,"请填写读者Id与对应密码",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(mContext,"新增读者数据失败，输入密码不一致",Toast.LENGTH_LONG).show();
                }
            }
        });

        //设置消极按钮
        super.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

    }

    //载入布局，绑定各种元素
    @Override
    public AlertDialog.Builder setView(View view) {
        readerId=(TextInputLayout)view.findViewById(R.id.textInputLayout_readerId_addReaderDialog);
        readerPw=(TextInputLayout)view.findViewById(R.id.textInputLayout_readerPw_addReaderDialog);
        readerPwEnsure=(TextInputLayout)view.findViewById(R.id.textInputLayout_readerPwEnsure_addReaderDialog);
        return super.setView(view);
    }

}

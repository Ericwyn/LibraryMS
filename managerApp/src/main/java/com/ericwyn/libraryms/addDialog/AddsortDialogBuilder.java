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
import com.ericwyn.libraryms.dbUtil.dbHelper.SortDBHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *新增读者的Dialog
 * Created by ericwyn on 17-3-31.
 */

public class AddsortDialogBuilder extends AlertDialog.Builder {
    private Context mContext;
    private TextInputLayout sortId;
    private TextInputLayout sortName;

    public AddsortDialogBuilder(@NonNull Context context) {
        super(context);
        mContext=context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.add_sort_dialog, null);
        this.setView(view);
        //设置标题
        super.setTitle("新增图书类别");

        //设置积极按钮
        super.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ArrayList<HashMap<String,Object>> maps=new ArrayList<HashMap<String, Object>>();
                HashMap<String,Object> map=new HashMap<String, Object>();
                String sortIdFlag=sortId.getEditText().getText().toString();
                String sortNameFlag=sortName.getEditText().getText().toString();
                if(!sortIdFlag.equals("") && !sortNameFlag.equals("")){
                    map.put("sortId",Integer.parseInt(sortIdFlag));
                    map.put("sortName",sortNameFlag);
                    maps.add(map);
                    if(SortDBHelper.addSorts(mContext,maps)==0){
                        Toast.makeText(mContext,"新增图书类别"+sortNameFlag+"成功",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(mContext,"新增图书类别失败，请检查",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(mContext,"请填写好图书类别",Toast.LENGTH_LONG).show();
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
        sortId=(TextInputLayout)view.findViewById(R.id.textInputLayout_sortId_addSortDialog);
        sortName=(TextInputLayout)view.findViewById(R.id.textInputLayout_sortName_addSortDialog);
        //.size代表的编号当前是还没使用的，因为编号从0开始
        sortId.getEditText().setText(""+(SortDBHelper.searchAllSort(mContext).size()));
        return super.setView(view);
    }

}

package com.ericwyn.libraryms.updataDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ericwyn.libraryms.BaseDataManagerFragment;
import com.ericwyn.libraryms.ChildFragment.baseDataM.SortManagerFragment;
import com.ericwyn.libraryms.R;
import com.ericwyn.libraryms.dbUtil.dbHelper.BookDBHelper;
import com.ericwyn.libraryms.dbUtil.dbHelper.SortDBHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *新增读者的Dialog
 * Created by ericwyn on 17-3-31.
 */

public class UpdataSortDialogBuilder extends AlertDialog.Builder {
    private Context mContext;
    private TextView sortId;
    private TextInputLayout sortName;
    private SortManagerFragment fragment;

    private HashMap<String,Object> dataMap;

    public UpdataSortDialogBuilder(@NonNull Context context, HashMap<String,Object> map,final SortManagerFragment fragment) {
        super(context);
        mContext=context;
        this.dataMap=map;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.updata_sort_dialog, null);
        this.setView(view);
        //设置标题
        super.setTitle("类别详情");

        //设置积极按钮
        super.setPositiveButton("更新数据", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ArrayList<HashMap<String,Object>> maps=new ArrayList<HashMap<String, Object>>();
                HashMap<String,Object> map=new HashMap<String, Object>();
                String sortIdFlag=sortId.getText().toString();
                String sortNameFlag=sortName.getEditText().getText().toString();
                if(!sortIdFlag.equals("") && !sortNameFlag.equals("")){
                    map.put("sortId",Integer.parseInt(sortIdFlag));
                    map.put("sortName",sortNameFlag);
                    maps.add(map);
                    if(SortDBHelper.addSorts(mContext,maps)==0){
                        Toast.makeText(mContext,"更新类别"+sortNameFlag+"成功",Toast.LENGTH_LONG).show();
                        fragment.updata();
                    }else {
                        Toast.makeText(mContext,"更新类别失败，请检查",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(mContext,"请填写好图书类别",Toast.LENGTH_LONG).show();
                }
            }
        });

        super.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        //设置消极按钮
        super.setNegativeButton("删除类别", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int sortIdFlag=Integer.parseInt(sortId.getText().toString());
                ArrayList<Integer> deletList=new ArrayList<Integer>();
                deletList.add(sortIdFlag);
                if(SortDBHelper.deleteSortById(mContext,deletList)==0
                        && BookDBHelper.deleteSortBySortId(mContext,deletList)==0){
                    Toast.makeText(mContext,"删除类别和书籍成功",Toast.LENGTH_SHORT).show();
                    BaseDataManagerFragment.updata();
                }else {
                    Toast.makeText(mContext,"删除类别失败",Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    //载入布局，绑定各种元素
    @Override
    public AlertDialog.Builder setView(View view) {
        sortId=(TextView) view.findViewById(R.id.textView_sortId_updataSortDialog);
        sortName=(TextInputLayout)view.findViewById(R.id.textInputLayout_sortName_updataSortDialog);
        //.size代表的编号当前是还没使用的，因为编号从0开始
        sortId.setText(dataMap.get("sortId").toString());
        sortName.getEditText().setText((String)dataMap.get("sortName"));
        return super.setView(view);
    }

}

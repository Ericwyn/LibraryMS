package com.ericwyn.libraryms.addDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.ericwyn.libraryms.managerApp.BookOrderFragment;
import com.ericwyn.libraryms.R;
import com.ericwyn.libraryms.dbUtil.dbHelper.OrderDBHelper;
import com.ericwyn.libraryms.dbUtil.dbHelper.SortDBHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *新增读者的Dialog
 * Created by ericwyn on 17-3-31.
 */

public class AddOrderDialogBuilder extends AlertDialog.Builder {
    private Context mContext;
    private TextInputLayout bookId;
    private TextInputLayout bookName;
    private TextInputLayout bookAllNum;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private int sortId=-10086;

    private BookOrderFragment fragment;

    public AddOrderDialogBuilder(@NonNull Context context, final BookOrderFragment fragment) {
        super(context);
        mContext=context;
        this.fragment=fragment;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.add_book_dialog, null);
        this.setView(view);
        //设置标题
        super.setTitle("新增书籍订购");

        //设置积极按钮
        super.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ArrayList<HashMap<String,Object>> maps=new ArrayList<HashMap<String, Object>>();
                HashMap<String,Object> map=new HashMap<String, Object>();
                String bookNameFlag=bookName.getEditText().getText().toString();
                String bookIdFlag=bookId.getEditText().getText().toString();
                String bookAllNameFlag=bookAllNum.getEditText().getText().toString();
                if(sortId==-10086){
                    Toast.makeText(mContext,"请选择图书类别",Toast.LENGTH_SHORT).show();
                }

                if(!bookNameFlag.equals("") && !bookIdFlag.equals("") && !bookAllNameFlag.equals("")){
                    map.put("bookName",bookNameFlag);
                    map.put("bookId",bookIdFlag);
                    map.put("bookAllNum",Integer.parseInt(bookAllNameFlag));
                    map.put("bookOutNum",0);
                    map.put("sortId",sortId);
                    maps.add(map);
                    if(OrderDBHelper.addBooks(mContext,maps)!=-1){
                        Toast.makeText(mContext,"新增图书"+bookNameFlag+"数据成功",Toast.LENGTH_SHORT).show();
                        fragment.updata();
                    }else {
                        Toast.makeText(mContext,"新增图书数据失败，请检查",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(mContext,"请填写完整图书数据",Toast.LENGTH_LONG).show();
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
        bookId=(TextInputLayout)view.findViewById(R.id.textInputLayout_bookId_addBookDialog);
        bookName=(TextInputLayout)view.findViewById(R.id.textInputLayout_bookName_addBookDialog);
        bookAllNum=(TextInputLayout)view.findViewById(R.id.textInputLayout_bookAllNum_addBookDialog);
        spinner=(Spinner)view.findViewById(R.id.spinner_sortId_addBookDialog);
        adapter=new ArrayAdapter<String>(mContext,
                android.R.layout.simple_spinner_item,
                SortDBHelper.searchAllSortName(mContext));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Adapter adapter=parent.getAdapter();
                String sortNameFlag=(String) adapter.getItem(position);
                sortId=SortDBHelper.getSortIdByName(mContext,sortNameFlag);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sortId=-10086;
            }
        });

        return super.setView(view);
    }

}

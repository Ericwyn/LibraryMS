package com.ericwyn.libraryms.updataDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ericwyn.libraryms.R;
import com.ericwyn.libraryms.dbUtil.dbHelper.BookDBHelper;
import com.ericwyn.libraryms.dbUtil.dbHelper.SortDBHelper;

import java.util.HashMap;

/**
 *新增读者的Dialog
 * Created by ericwyn on 17-3-31.
 */

public class UpdataBookDialogBuilder extends AlertDialog.Builder {
    private Context mContext;
    private TextView bookId;
    private TextInputLayout bookName;
    private TextInputLayout bookAllNum;
    private TextInputLayout bookOutNum;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private int sortId=-10086;

    private HashMap<String,Object> dataMap=new HashMap<>();


    public UpdataBookDialogBuilder(@NonNull Context context,String bookid) {
        super(context);
        mContext=context;
        dataMap= BookDBHelper.searchBookById(mContext,bookid);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.update_book_dialog, null);
        this.setView(view);
        //设置标题
        super.setTitle("新增书籍");

        //设置积极按钮
        super.setPositiveButton("更新数据", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HashMap<String,Object> map=new HashMap<String, Object>();
                String bookNameFlag=bookName.getEditText().getText().toString();
                String bookIdFlag=bookId.getText().toString();
                String bookAllNameFlag=bookAllNum.getEditText().getText().toString();
//                String bookOverNumFla

                if(!bookNameFlag.equals("") && !bookIdFlag.equals("") && !bookAllNameFlag.equals("")){
                    map.put("bookName",bookNameFlag);
                    map.put("bookId",bookIdFlag);
                    map.put("bookAllNum",Integer.parseInt(bookAllNameFlag));
                    map.put("bookOverNum",Integer.parseInt(bookAllNameFlag));
                    map.put("bookOutNum",BookDBHelper.bookOutNum(mContext,bookIdFlag));
                    map.put("sortId",sortId);
                    if(BookDBHelper.chanceByBookId(mContext,bookIdFlag,map)!=-1){
                        Toast.makeText(mContext,"更新图书"+bookNameFlag+"数据成功",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(mContext,"更新图书数据失败，请检查",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(mContext,"请填写完整图书数据",Toast.LENGTH_LONG).show();
                }

            }
        });

        //中性按钮，在最左边
        super.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //设置消极按钮，在中间
        super.setNegativeButton("删除书籍", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

    }

    //载入布局，绑定各种元素
    @Override
    public AlertDialog.Builder setView(View view) {
        bookId=(TextView) view.findViewById(R.id.textView_bookid_updatabookDialog);
        bookName=(TextInputLayout)view.findViewById(R.id.textInputLayout_bookName_updatabookDialog);
        bookAllNum=(TextInputLayout)view.findViewById(R.id.textInputLayout_bookAllNum_updatabookDialog);
        bookOutNum=(TextInputLayout)view.findViewById(R.id.textInputLayout_bookOverNum_updatabookDialog);


        bookId.setText((String)dataMap.get("bookId"));
        bookName.getEditText().setText((String)dataMap.get("bookName"));
        bookAllNum.getEditText().setText(dataMap.get("bookAllNum").toString());
        bookOutNum.getEditText().setText(dataMap.get("bookOutNum").toString());
        bookOutNum.getEditText().setInputType(InputType.TYPE_NULL);

        spinner=(Spinner)view.findViewById(R.id.spinner_sortId_updatabookDialog);
        String[] allSortName=SortDBHelper.searchAllSortName(mContext);
        adapter=new ArrayAdapter<String>(mContext,
                android.R.layout.simple_spinner_item,
                allSortName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        //让spinner跳到指定的项
        sortId=(Integer) dataMap.get("sortId");
        String sortName=SortDBHelper.getSortNameById(mContext,sortId);
        int selectId=0;
        int i=0;
        for(String str:allSortName){
            if(str.equals(sortName)){
                selectId=i;
                break;
            }
            i++;
        }
        spinner.setSelection(i);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Adapter adapter=parent.getAdapter();
                String sortNameFlag=(String) adapter.getItem(position);
                sortId=SortDBHelper.getSortIdByName(mContext,sortNameFlag);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return super.setView(view);
    }

}

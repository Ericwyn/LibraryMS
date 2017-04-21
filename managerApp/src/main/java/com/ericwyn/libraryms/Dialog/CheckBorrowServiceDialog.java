package com.ericwyn.libraryms.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ericwyn.libraryms.ChildFragment.borrowM.BorrowActivity;
import com.ericwyn.libraryms.R;
import com.ericwyn.libraryms.dbUtil.DataBaseUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * 告知借书服务成功与否的Dialog
 * Created by ericwyn on 17-4-16.
 */

public class CheckBorrowServiceDialog extends AlertDialog.Builder{
    private final int BORROW_SERVICE_CODE=1;
    private final int RETURN_SERVICE_CODE=2;
    private TextView successTital;
    private TextView failTital;
    private TextView successText;
    private TextView failText;
    private Context mContext;
    private Text text;
    private int sortId=-10086;

    private String success="";
    private String fail="";

    public CheckBorrowServiceDialog(@NonNull final Context context, final BorrowActivity activity, int serviceCode) {
        super(context);
        mContext=context;
        String readerId=activity.readerIdText;
        ArrayList<String> bookIds=activity.bookidList;
        int[] code=new int[bookIds.size()];
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.check_borrow_service_dialog, null);
        this.setView(view);

        super.setTitle("新增书籍");

        if(serviceCode==BORROW_SERVICE_CODE){
            for(int i=0;i<bookIds.size();i++){
                code[i]=DataBaseUtil.borrowABook(context,Integer.parseInt(readerId),bookIds.get(i));
            }
            for(int i=0;i<code.length;i++){
                if(code[i]==0){
                    success+="书籍"+bookIds.get(i)+"借阅成功"+"\n";
                }else if(code[i]==1){
                    fail+="书籍"+bookIds.get(i)+"无剩余"+"\n";
                }else if(code[i]==2){
                    fail+="书籍"+bookIds.get(i)+"不存在"+"\n";
                }else if(code[i]==3){
                    fail+="书籍"+bookIds.get(i)+"用户不存在"+"\n";
                }
            }

        }else if(serviceCode==RETURN_SERVICE_CODE){
            for(int i=0;i<bookIds.size();i++){
                code[i]=DataBaseUtil.returnABook(context,Integer.parseInt(readerId),bookIds.get(i));
            }
            for(int i=0;i<code.length;i++){
                if(code[i]==0){
                    success+="书籍"+bookIds.get(i)+"借阅成功"+"\n";
                }else if(code[i]==1){
                    fail+="书籍"+bookIds.get(i)+"无剩余"+"\n";
                }else if(code[i]==2){
                    fail+="书籍"+bookIds.get(i)+"不存在"+"\n";
                }else if(code[i]==3){
                    fail+="书籍"+bookIds.get(i)+"用户不存在"+"\n";
                }
            }
        }
        //设置标题
        successText.setText(success);
        failText.setText(fail);

        //设置积极按钮
        super.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("点击","点几点几点几");
            }
        });

//        //设置消极按钮
//        super.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });

    }

    //载入布局，绑定各种元素
    @Override
    public AlertDialog.Builder setView(View view) {

        successTital=(TextView)view.findViewById(R.id.tital_success_checkDialog);
        successText=(TextView)view.findViewById(R.id.text_success_checkDialog);
        failTital=(TextView)view.findViewById(R.id.tital_fail_checkDialog);
        failText=(TextView)view.findViewById(R.id.text_fial_checkDialog);

        successTital.setText("成功信息");
        failTital.setText("失败信息");


        return super.setView(view);
    }
}

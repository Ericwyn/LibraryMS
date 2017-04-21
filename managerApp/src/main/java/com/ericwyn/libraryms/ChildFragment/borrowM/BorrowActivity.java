package com.ericwyn.libraryms.ChildFragment.borrowM;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ericwyn.libraryms.R;
import com.ericwyn.libraryms.dbUtil.DataBaseUtil;
import com.znq.zbarcode.CaptureActivity;

import java.util.ArrayList;

import static com.znq.zbarcode.CaptureActivity.EXTRA_STRING;

public class BorrowActivity extends AppCompatActivity {
    private ImageButton scanReaderId;
    private ImageButton scanBookId;
    private EditText readerId;
    private EditText[] bookIds=new EditText[4];
    private Button[] clearButtons=new Button[4];
    private Toolbar toolbar;

    private Button finish;

    private String service;

    public ArrayList<String> bookidList=new ArrayList<>();
    public String readerIdText="";

    private final int BORROW_SERVICE_CODE=1;
    private final int RETURN_SERVICE_CODE=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);
        if(getIntent()!=null){
            service=getIntent().getStringExtra("service");
        }

        toolbar=(Toolbar)findViewById(R.id.toolbar_borrowActivity);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BorrowActivity.this.finish();
            }
        });

        scanReaderId=(ImageButton)findViewById(R.id.btn_scanReaderId_borrowActivity);
        scanBookId=(ImageButton)findViewById(R.id.btn_scanBookId_borrowActivity);
        readerId=(EditText)findViewById(R.id.et_readerId_borrowActivity);

        bookIds[0]=(EditText) findViewById(R.id.et_bookId_1_borrowActivity);
        bookIds[1]=(EditText) findViewById(R.id.et_bookId_2_borrowActivity);
        bookIds[2]=(EditText) findViewById(R.id.et_bookId_3_borrowActivity);
        bookIds[3]=(EditText) findViewById(R.id.et_bookId_4_borrowActivity);

        clearButtons[0]=(Button)findViewById(R.id.btn_clear_1_borrowActivity);
        clearButtons[1]=(Button)findViewById(R.id.btn_clear_2_borrowActivity);
        clearButtons[2]=(Button)findViewById(R.id.btn_clear_3_borrowActivity);
        clearButtons[3]=(Button)findViewById(R.id.btn_clear_4_borrowActivity);

        finish=(Button)findViewById(R.id.btn_finishBorrowActivity);

        clearButtons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookIds[0].setText("");
            }
        });
        clearButtons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookIds[1].setText("");
            }
        });
        clearButtons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookIds[2].setText("");
            }
        });
        clearButtons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookIds[3].setText("");
            }
        });

        scanReaderId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString(EXTRA_STRING, "readerId");
                Intent intent=new Intent(BorrowActivity.this, CaptureActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
//                startActivity(intent);
            }
        });

        scanBookId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString(EXTRA_STRING, "bookId");
                Intent intent=new Intent(BorrowActivity.this, CaptureActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });

        if(service.equals("borrow")){
            finish.setText("确认借书");
        }else if(service.equals("return")){
            finish.setText("确认还书");
        }

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tianchongshuju();
                if(service.equals("borrow")){
                    showToast(getActivity(),1);

//                    CheckBorrowServiceDialog checkBorrowServiceDialog=
//                            new CheckBorrowServiceDialog(getApplicationContext(),getActivity(),1);
//                    checkBorrowServiceDialog.show();
                    //点击后清楚数据缓存
                    readerIdText="";
                    bookidList.clear();
                    finish();
                }else if(service.equals("return")){
                    showToast(getActivity(),2);
//                    CheckBorrowServiceDialog checkBorrowServiceDialog=
//                            new CheckBorrowServiceDialog(getApplicationContext(),getActivity(),2);
//                    checkBorrowServiceDialog.show();
                    //点击后清楚数据缓存
                    readerIdText="";
                    bookidList.clear();
                    finish();
                }
            }
        });


    }

    /**
     * 将数据塞好以便dialog那里调用
     */
    private void tianchongshuju(){
        readerIdText=readerId.getText().toString();
        for(EditText editText:bookIds){
            if(!editText.getText().toString().equals("")){
                bookidList.add(editText.getText().toString());
            }
        }
    }

    private void tianchong(String bookId){
        boolean empty=true;
        for(int i=0;i<4;i++){
            Log.i("test",bookIds[i].getText().toString());
            if(bookIds[i].getText().toString().equals("")){
                empty=false;
                bookIds[i].setText(bookId);
                break;
            }
        }
        if(empty){
            Toast.makeText(this,"一次最多只能借阅4本书",Toast.LENGTH_SHORT).show();
        }
    }


    public void showToast(final BorrowActivity activity, int serviceCode) {
        String success="";
        String fail="";
        String readerId=activity.readerIdText;
        ArrayList<String> bookIds=activity.bookidList;

        int[] code=new int[bookIds.size()];

        super.setTitle("新增书籍");

        if(serviceCode==BORROW_SERVICE_CODE){
            for(int i=0;i<bookIds.size();i++){
                code[i]= DataBaseUtil.borrowABook(this,Integer.parseInt(readerId),bookIds.get(i));
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
                code[i]=DataBaseUtil.returnABook(this,Integer.parseInt(readerId),bookIds.get(i));
            }
            for(int i=0;i<code.length;i++){
                if(code[i]==0){
                    success+="书籍"+bookIds.get(i)+"还书成功"+"\n";
                }else if(code[i]==1){
                    fail+="用户没有借阅书籍"+bookIds.get(i)+"\n";
                }else if(code[i]==3){
                    fail+="代表用户不存在"+"\n";
                }
            }
        }

        Toast.makeText(this,success+fail,Toast.LENGTH_LONG).show();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 111:
                Bundle bundle = data.getExtras();
                String info = bundle.getString("readerId");
                readerId.setText(info);
                Toast.makeText(this,info,Toast.LENGTH_SHORT).show();
                break;

            case 222:
                Bundle bundle1 = data.getExtras();
                String info1 = bundle1.getString("bookId");
                tianchong(info1);
//                readerId.setText(info1);
                Toast.makeText(this,info1,Toast.LENGTH_SHORT).show();
//                ArrayList<String> list=CaptureActivity.bookIdList;
//                for (int i=0;i<list.size();i++){
//                    bookIds[i].setText(list.get(i));
//                }
//                list.clear();
                break;
            default:
                break;
        }
    }


    private BorrowActivity getActivity(){
        return this;
    }
}

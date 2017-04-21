package com.ericwyn.libraryms;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ericwyn.libraryms.managerApp.MainActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText account;
    private EditText password;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        loadAccount();
    }

    private void initView(){
        account=(EditText)findViewById(R.id.editText_loginAccount);
        password=(EditText)findViewById(R.id.editText_loginPassword);
        btn=(Button)findViewById(R.id.login_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(account.getText().toString().equals("admin")){
                    if(password.getText().toString().equals("admin")){
                        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this,"用户名错误",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //记住用户名
    public void remaberAccount(String account){
        SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
        SharedPreferences.Editor ed=sp.edit();
        ed.putString("account",account);
        ed.apply();
    }

    //载入用户名
    public void loadAccount(){
        SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
        String accountSave=sp.getString("account","");
        account.setText(accountSave);
    }

    @Override
    public void finish() {
        remaberAccount(account.getText().toString());
        super.finish();
    }
}

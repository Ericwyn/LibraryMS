package com.ericwyn.libraryms;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ericwyn.libraryms.dbUtil.DataBaseUtil;
import com.ericwyn.libraryms.dbUtil.dbHelper.BookDBHelper;
import com.ericwyn.libraryms.dbUtil.dbHelper.ReaderDBHelper;
import com.ericwyn.libraryms.dbUtil.dbHelper.SortDBHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment mContent;
    private FragmentManager fragmentManager=getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("系统概括");
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initView();
    }

    private void initView(){
        DataBaseUtil.dBIni(MainActivity.this);

        MainFragment mainFragment=new MainFragment();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.content_main_layout,mainFragment);
        transaction.commit();
        mContent=mainFragment;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            setTitle("系统概括");
            MainFragment fragment=new MainFragment();
            switchFragment(mContent,fragment);
        } else if (id == R.id.nav_dataMaintain) {
            setTitle("基础数据维护");
            BaseDataManagerFragment fragment=new BaseDataManagerFragment();
            switchFragment(mContent,fragment);
        } else if (id == R.id.nav_borrowManager) {
            setTitle("图书借阅管理");

        } else if (id == R.id.nav_orderManager) {
            setTitle("新书订购管理");

        } else if (id == R.id.nav_introduction) {
            dbini();
        } else if (id == R.id.nav_authod) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void dbini(){

        ArrayList<HashMap<String ,Object>> maps=new ArrayList<>();
        for(int i=0;i<7;i++){
            HashMap<String ,Object> map=new HashMap<>();
            map.put("readerId",1500502100+i*2);
            map.put("readerPw","pw"+(1500502100+i*2));
            maps.add(map);
        }
        ReaderDBHelper.addReaders(this,maps);

        maps=new ArrayList<>();
        for(int i=0;i<10;i++){
            HashMap<String ,Object> map=new HashMap<>();
            map.put("sortId",i);
            map.put("sortName","类别i");
            maps.add(map);
        }
        SortDBHelper.addSorts(this,maps);

        maps=new ArrayList<>();
        for(int i=0;i<25;i++){
            HashMap<String ,Object> map=new HashMap<>();
            map.put("sortId",i%9);
            map.put("bookId",(20000000+i));
            map.put("bookName","测试书籍"+(i+1));
            map.put("bookAllNum",22);
            map.put("bookOverNum",22);
            maps.add(map);
        }
        BookDBHelper.addBooks(this,maps);

        Toast.makeText(MainActivity.this,"测试数据已经生成",Toast.LENGTH_SHORT).show();
    }

    public void switchFragment(Fragment from,Fragment to){
        if (mContent != to) {
            mContent = to;
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!to.isAdded()) {	// 先判断是否被add过
                transaction.hide(from).add(R.id.content_main_layout, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }
}

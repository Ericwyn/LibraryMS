package com.ericwyn.libraryms;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ericwyn.libraryms.ChildFragment.baseDataM.BookManagerFragment;
import com.ericwyn.libraryms.ChildFragment.baseDataM.ReaderManagerFragment;
import com.ericwyn.libraryms.ChildFragment.baseDataM.SortManagerFragment;
import com.ericwyn.libraryms.dbUtil.dbHelper.ReaderDBHelper;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 基础数据管理的Fragment
 * Created by ericwyn on 17-3-27.
 */

public class BaseDataManagerFragment extends Fragment{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionsMenu floatingActionsMenu;
    private FloatingActionButton addABook;
    private FloatingActionButton addASort;
    private FloatingActionButton addAReader;
    private FloatingActionButton addByScanner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view=inflater.inflate(R.layout.fragment_base_data, container, false);
        //初始化Tablayout
        tabLayout=(TabLayout)view.findViewById(R.id.tabLayout_baseDataFragment);
        //初始化ViewPager
        viewPager=(ViewPager)view.findViewById(R.id.viewPager_baseDataManagerFragment);
        //新建TabAdapter，在Adapter里面绑定相应的Fragment试图
        final TabAdapter tabAdapter=new TabAdapter(getChildFragmentManager());
        //然后TabAdapter再和viewPager绑定
        viewPager.setAdapter(tabAdapter);
        viewPager.setOffscreenPageLimit(2);
        //最后让tabLayout来绑定这个TabAdapter，就搞定了
        tabLayout.setupWithViewPager(viewPager);

        floatingActionsMenu=(FloatingActionsMenu)view.findViewById(R.id.fab_menu_basedataFragment);
//        floatingActionsMenu
        addABook=(FloatingActionButton)view.findViewById(R.id.fab_newBook_basedataFragment);
        addAReader=(FloatingActionButton)view.findViewById(R.id.fab_newReader_basedataFragment);
        addASort=(FloatingActionButton)view.findViewById(R.id.fab_newSort_basedataFragment);
        addByScanner=(FloatingActionButton)view.findViewById(R.id.fab_sanner_basedataFragent);
        addAReader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionsMenu.toggle();
                LayoutInflater inflater=getActivity().getLayoutInflater();
                View dialog=inflater.inflate(R.layout.add_reader_dialog,(ViewGroup)view.findViewById(R.id.reader_dialog_datebaseManager));
                final TextInputLayout readerId=(TextInputLayout)dialog.findViewById(R.id.textInputLayout_readerId_addReaderDialog);
                final TextInputLayout readerPw=(TextInputLayout)dialog.findViewById(R.id.textInputLayout_readerPw_addReaderDialog);
                final TextInputLayout readerPwEnsure=(TextInputLayout)dialog.findViewById(R.id.textInputLayout_readerPwEnsure_addReaderDialog);
                final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("新增读者");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
                                if(ReaderDBHelper.addReaders(getActivity(),maps)!=-1){
                                    Toast.makeText(getActivity(),"新增读者"+readerIdFlag+"数据成功",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getActivity(),"新增读者数据失败，请检查",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(getActivity(),"请填写读者Id与对应密码",Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getActivity(),"新增读者数据失败，输入密码不一致",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setView(dialog);
                builder.show();

            }
        });



        return view;
    }
}

class TabAdapter extends FragmentPagerAdapter{
    List<Fragment> fragmentList=new ArrayList<>();
    String[] titles={"读者管理","图书管理","类别管理"};
    public TabAdapter(FragmentManager fm){
        super(fm);
        fragmentList.add(new ReaderManagerFragment());
        fragmentList.add(new BookManagerFragment());
        fragmentList.add(new SortManagerFragment());
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
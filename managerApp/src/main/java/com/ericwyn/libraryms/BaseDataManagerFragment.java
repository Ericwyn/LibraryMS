package com.ericwyn.libraryms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ericwyn.libraryms.addDialog.AddBookDialogBuilder;
import com.ericwyn.libraryms.addDialog.AddReaderDialogBuilder;
import com.ericwyn.libraryms.addDialog.AddsortDialogBuilder;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

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
    private static TabAdapter tabAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view=inflater.inflate(R.layout.fragment_base_data, container, false);
        //初始化Tablayout
        tabLayout=(TabLayout)view.findViewById(R.id.tabLayout_baseDataFragment);
        //初始化ViewPager
        viewPager=(ViewPager)view.findViewById(R.id.viewPager_baseDataManagerFragment);
        //新建TabAdapter，在Adapter里面绑定相应的Fragment试图
        tabAdapter=new TabAdapter(getChildFragmentManager());
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
                AddReaderDialogBuilder dialogBuilder=new AddReaderDialogBuilder(getActivity());
                dialogBuilder.show();
            }
        });
        addASort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionsMenu.toggle();
                AddsortDialogBuilder dialogBuilder=new AddsortDialogBuilder(getActivity());
                dialogBuilder.show();
            }
        });
        addABook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionsMenu.toggle();
                AddBookDialogBuilder dialogBuilder=new AddBookDialogBuilder(getActivity());
                dialogBuilder.show();
            }
        });


        return view;
    }
    public static void updata(){
        tabAdapter.updata();
    }

}



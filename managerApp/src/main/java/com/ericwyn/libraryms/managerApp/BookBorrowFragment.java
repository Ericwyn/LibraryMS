package com.ericwyn.libraryms.managerApp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ericwyn.libraryms.R;
import com.ericwyn.libraryms.managerApp.tabAdapter.BorrowFragmentTabAdapter;

/**
 * 图书借阅管理的Fragment
 * Created by ericwyn on 17-4-9.
 */

public class BookBorrowFragment extends Fragment{
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.borrow_fragment,container,false);
        tabLayout=(TabLayout)view.findViewById(R.id.tabLayout_borrowFragment);

        viewPager=(ViewPager)view.findViewById(R.id.viewPager_borrowFragment);

        BorrowFragmentTabAdapter borrowFragmentTabAdapter=new BorrowFragmentTabAdapter(getChildFragmentManager());

        viewPager.setAdapter(borrowFragmentTabAdapter);

        tabLayout.setupWithViewPager(viewPager);

        return view;
    }


}

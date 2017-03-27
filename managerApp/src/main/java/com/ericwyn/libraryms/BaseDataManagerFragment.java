package com.ericwyn.libraryms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ericwyn.libraryms.ChildFragment.baseDataM.BookManagerFragment;
import com.ericwyn.libraryms.ChildFragment.baseDataM.ReaderManagerFragment;
import com.ericwyn.libraryms.ChildFragment.baseDataM.SortManagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础数据管理的Fragment
 * Created by ericwyn on 17-3-27.
 */

public class BaseDataManagerFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_base_data, container, false);
        //初始化Tablayout
        tabLayout=(TabLayout)view.findViewById(R.id.tabLayout_baseDataFragment);
        //初始化ViewPager
        viewPager=(ViewPager)view.findViewById(R.id.viewPager_baseDataManagerFragment);
        //新建TabAdapter，在Adapter里面绑定相应的Fragment试图
        TabAdapter tabAdapter=new TabAdapter(getChildFragmentManager());
        //然后TabAdapter再和viewPager绑定
        viewPager.setAdapter(tabAdapter);
        //最后让tabLayout来绑定这个TabAdapter，就搞定了
        tabLayout.setupWithViewPager(viewPager);
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
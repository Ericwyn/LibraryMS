package com.ericwyn.libraryms.managerApp.tabAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ericwyn.libraryms.ChildFragment.baseDataM.BookManagerFragment;
import com.ericwyn.libraryms.ChildFragment.baseDataM.ReaderManagerFragment;
import com.ericwyn.libraryms.ChildFragment.baseDataM.SortManagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * TabLayout的适配器
 * Created by ericwyn on 17-4-2.
 */

public class BaseDataMFragmentTabAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList=new ArrayList<>();
    String[] titles={"读者管理","图书管理","类别管理"};
    ReaderManagerFragment readerManagerFragment=new ReaderManagerFragment();
    BookManagerFragment bookManagerFragment=new BookManagerFragment();
    SortManagerFragment sortManagerFragment=new SortManagerFragment();
    public BaseDataMFragmentTabAdapter(FragmentManager fm){
        super(fm);
        fragmentList.add(readerManagerFragment);
        fragmentList.add(bookManagerFragment);
        fragmentList.add(sortManagerFragment);
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

    public BaseDataMFragmentTabAdapter getTabAdapter(){
        return this;
    }

    public void updata(){
        readerManagerFragment.updata();
        bookManagerFragment.updata();
        sortManagerFragment.updata();
    }

}
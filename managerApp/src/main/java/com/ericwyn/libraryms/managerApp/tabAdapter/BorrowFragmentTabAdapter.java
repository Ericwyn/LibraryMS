package com.ericwyn.libraryms.managerApp.tabAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ericwyn.libraryms.ChildFragment.baseDataM.ReaderManagerFragment;
import com.ericwyn.libraryms.ChildFragment.borrowM.borrowServiceFragment;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by ericwyn on 17-4-9.
 */

public class BorrowFragmentTabAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList=new ArrayList<>();
    String[] titles={"借还服务","借书列表"};
    public BorrowFragmentTabAdapter(FragmentManager fm){
        super(fm);
        fragmentList.add(new borrowServiceFragment());
        fragmentList.add(new ReaderManagerFragment());
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
        return 2;
    }

}

package com.ericwyn.libraryms.ChildFragment.baseDataM;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ericwyn.libraryms.R;

/**
 * 图书管理的的childFragment
 * Created by ericwyn on 17-3-27.
 */

public class BookManagerFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.lv_child_fragment,container,false);

        return view;
    }
}

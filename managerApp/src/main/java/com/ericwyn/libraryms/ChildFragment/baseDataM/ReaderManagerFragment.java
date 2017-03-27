package com.ericwyn.libraryms.ChildFragment.baseDataM;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ericwyn.libraryms.R;

/**
 * 读者管理的childFragment
 * Created by ericwyn on 17-3-27.
 */

public class ReaderManagerFragment extends Fragment {
    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.lv_child_fragment,container,false);
        listView=(ListView)view.findViewById(R.id.listView_child_fragment);


        return view;
    }
}

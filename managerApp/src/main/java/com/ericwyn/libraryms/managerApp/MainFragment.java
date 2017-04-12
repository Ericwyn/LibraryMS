package com.ericwyn.libraryms.managerApp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ericwyn.libraryms.R;
import com.ericwyn.libraryms.dbUtil.dbHelper.BookDBHelper;
import com.ericwyn.libraryms.dbUtil.dbHelper.BorrowDBHelper;
import com.ericwyn.libraryms.dbUtil.dbHelper.ReaderDBHelper;

/**
 * 概述页面的Fragment
 * Created by ericwyn on 17-3-27.
 */
public class MainFragment extends Fragment {
    private TextView allBookNum;
    private TextView allReaderNum;
    private TextView borrowBookNum;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_main, container, false);

        allBookNum=(TextView)view.findViewById(R.id.tv_allBookNum);
        allReaderNum=(TextView)view.findViewById(R.id.tv_allReaderNum);
        borrowBookNum=(TextView)view.findViewById(R.id.tv_borrowBookNum);

        allBookNum.setText(""+ BookDBHelper.searchAllBook(getActivity()).size());
        allReaderNum.setText(""+ ReaderDBHelper.searchAllReader(getActivity()).size());
        //这里是所有的书籍的数量
        borrowBookNum.setText(""+ BorrowDBHelper.searchArrBorrow(getActivity()).size());

        return view;
    }

}

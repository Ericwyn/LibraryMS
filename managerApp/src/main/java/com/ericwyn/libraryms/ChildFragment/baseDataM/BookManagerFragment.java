package com.ericwyn.libraryms.ChildFragment.baseDataM;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ericwyn.libraryms.R;
import com.ericwyn.libraryms.dbUtil.dbHelper.BookDBHelper;
import com.ericwyn.libraryms.dbUtil.dbHelper.SortDBHelper;
import com.ericwyn.libraryms.updataDialog.UpdataBookDialogBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 图书管理的的childFragment
 * Created by ericwyn on 17-3-27.
 */

public class BookManagerFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<HashMap<String,Object>> dataList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.childlayout_recyclerview_fragment,container,false);
        recyclerView=(RecyclerView) view.findViewById(R.id.rv_child_fragment);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        dataList=getData();
        adapter=new BookManagerAdapter(getActivity(),dataList,this);
        recyclerView.setAdapter(adapter);

        return view;
    }
    private ArrayList<HashMap<String,Object>> getData(){
        ArrayList<HashMap<String,Object>> allBook=BookDBHelper.searchAllBook(getActivity());
        ArrayList<HashMap<String,Object>> list=new ArrayList<>();
        for(HashMap mapFlag:allBook){
            HashMap<String,Object> map=new HashMap<>();
            String bookName=(String) mapFlag.get("bookName");
            String bookId=(String)mapFlag.get("bookId");
            int sortId=(int)mapFlag.get("sortId");
            String sortName= SortDBHelper.getSortNameById(getActivity(),sortId);
            map.put("bookId",bookId);
            map.put("bookName",bookName);
            map.put("sortName",sortName);
            list.add(map);
        }
        return list;
    }

    class BookManagerAdapter extends RecyclerView.Adapter<BookManagerAdapter.VH> {

        private List<HashMap<String,Object>> dataList;
        private LayoutInflater layoutInflater;
        private BookManagerFragment fragmentManager;

        public BookManagerAdapter(Context context, ArrayList<HashMap<String,Object>> datas,BookManagerFragment fragmentManager) {
            this.layoutInflater=LayoutInflater.from(context);
            this.dataList = datas;
            this.fragmentManager=fragmentManager;
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(layoutInflater.inflate(R.layout.lv_item_book,parent,false));
        }

        @Override
        public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
            super.onBindViewHolder(holder, position, payloads);
        }

        @Override
        public void onBindViewHolder(VH holder, final int position) {
            String bookName=(String) dataList.get(position).get("bookName");
            String sortName=(String) dataList.get(position).get("sortName");
            final String bookId=(String)dataList.get(position).get("bookId");
            holder.bookName.setText(bookName);
            holder.sortName.setText(sortName);
            holder.bookId.setText(bookId);
            holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdataBookDialogBuilder dialogBuilder=
                            new UpdataBookDialogBuilder(getActivity(),bookId,fragmentManager);
                    dialogBuilder.show();
                }
            });


        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        class VH extends RecyclerView.ViewHolder {
            private TextView bookId;
            private TextView bookName;
            private TextView sortName;
            private ConstraintLayout constraintLayout;
            VH(View itemView) {
                super(itemView);
                bookId=(TextView)itemView.findViewById(R.id.bookId_reader_item);
                bookName=(TextView)itemView.findViewById(R.id.bookName_reader_item);
                sortName=(TextView)itemView.findViewById(R.id.sortName_reader_item);
                constraintLayout=(ConstraintLayout)itemView.findViewById(R.id.conslayout_book_item);
            }
        }
    }

    public void updataData(){
        dataList.clear();
        dataList.addAll(getData());
        adapter.notifyDataSetChanged();
    }




}

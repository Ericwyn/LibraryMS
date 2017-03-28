package com.ericwyn.libraryms.ChildFragment.baseDataM;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ericwyn.libraryms.R;
import com.ericwyn.libraryms.dbUtil.dbHelper.BookDBHelper;
import com.ericwyn.libraryms.dbUtil.dbHelper.SortDBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 图书管理的的childFragment
 * Created by ericwyn on 17-3-27.
 */

public class BookManagerFragment extends Fragment {
    private RecyclerView recyclerView;
    private SimpleAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.childlayout_recyclerview_fragment,container,false);
        recyclerView=(RecyclerView) view.findViewById(R.id.rv_child_fragment);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        recyclerView.setAdapter(new BookManagerAdapter(getActivity(),getData()));
        return view;
    }
    private ArrayList<HashMap<String,Object>> getData(){
        ArrayList<HashMap<String,Object>> allBook=BookDBHelper.searchAllBook(getActivity());
        ArrayList<HashMap<String,Object>> list=new ArrayList<>();
        for(HashMap mapFlag:allBook){
            HashMap<String,Object> map=new HashMap<>();
            String bookName=(String) mapFlag.get("bookName");
            int bookId=(int)mapFlag.get("bookId");
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

        public BookManagerAdapter(Context context, ArrayList<HashMap<String,Object>> datas) {
            this.layoutInflater=LayoutInflater.from(context);
            this.dataList = datas;
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(layoutInflater.inflate(R.layout.lv_item_book,parent,false));
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            String bookName=(String) dataList.get(position).get("bookName");
            String sortName=(String) dataList.get(position).get("sortName");
            int bookId=(int)dataList.get(position).get("bookId");
            holder.bookName.setText(bookName);
            holder.sortName.setText(sortName);
            holder.bookId.setText(""+bookId);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        class VH extends RecyclerView.ViewHolder {
            private TextView bookId;
            private TextView bookName;
            private TextView sortName;
            VH(View itemView) {
                super(itemView);
                bookId=(TextView)itemView.findViewById(R.id.bookId_reader_item);
                bookName=(TextView)itemView.findViewById(R.id.bookName_reader_item);
                sortName=(TextView)itemView.findViewById(R.id.sortName_reader_item);
            }
        }
    }

}

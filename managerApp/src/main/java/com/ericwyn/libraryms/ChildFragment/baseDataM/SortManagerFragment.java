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
import android.widget.TextView;

import com.ericwyn.libraryms.R;
import com.ericwyn.libraryms.dbUtil.dbHelper.SortDBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 图书类别管理的childFragment
 * Created by ericwyn on 17-3-27.
 */

public class SortManagerFragment extends Fragment {
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.childlayout_recyclerview_fragment,container,false);
        recyclerView=(RecyclerView) view.findViewById(R.id.rv_child_fragment);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new SortManagerAdapter(getActivity(),getData()));

        return view;
    }
    private ArrayList<HashMap<String,Object>> getData(){
        ArrayList<HashMap<String,Object>> maps=SortDBHelper.searchAllSort(getActivity());
        ArrayList<HashMap<String,Object>> list=new ArrayList<>();
        for(HashMap mapFlag:maps){
            HashMap<String,Object> map=new HashMap<>();
            String sortName=(String) mapFlag.get("sortName");
            int sortId=(int)mapFlag.get("sortId");
            map.put("sortId",sortId);
            map.put("sortName",sortName);
            list.add(map);
        }
        return list;
    }
    class SortManagerAdapter extends RecyclerView.Adapter<SortManagerAdapter.VH> {

        private List<HashMap<String,Object>> dataList;
        private LayoutInflater layoutInflater;

        public SortManagerAdapter(Context context, ArrayList<HashMap<String,Object>> datas) {
            this.layoutInflater=LayoutInflater.from(context);
            this.dataList = datas;
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(layoutInflater.inflate(R.layout.lv_item_sort,parent,false));
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            String sortName=(String) dataList.get(position).get("sortName");
            int sortId=(int)dataList.get(position).get("sortId");
            holder.sortId.setText(""+sortId);
            holder.sortName.setText(sortName);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        class VH extends RecyclerView.ViewHolder {
            private TextView sortId;
            private TextView sortName;
            VH(View itemView) {
                super(itemView);
                sortId=(TextView)itemView.findViewById(R.id.sortId_sort_item);
                sortName=(TextView)itemView.findViewById(R.id.sortName_sort_item);
            }
        }
    }
}

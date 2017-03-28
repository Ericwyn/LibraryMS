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
import com.ericwyn.libraryms.dbUtil.dbHelper.BorrowDBHelper;
import com.ericwyn.libraryms.dbUtil.dbHelper.ReaderDBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 读者管理的childFragment
 * Created by ericwyn on 17-3-27.
 */

public class ReaderManagerFragment extends Fragment {
    private RecyclerView recyclerView;
    private SimpleAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.childlayout_recyclerview_fragment,container,false);
        recyclerView=(RecyclerView) view.findViewById(R.id.rv_child_fragment);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        recyclerView.setAdapter(new ReaderManagerAdapter(getActivity(),getData()));
        return view;
    }
    private ArrayList<HashMap<String,Object>> getData(){
        ArrayList<HashMap<String,Object>> allReader= ReaderDBHelper.searchAllReader(getActivity());
        ArrayList<HashMap<String,Object>> list=new ArrayList<>();
        for(HashMap mapFlag:allReader){
            HashMap<String,Object> map=new HashMap<>();
            int readerId=(int) mapFlag.get("readerId");
            map.put("readerId",readerId);
            map.put("borrowNum",BorrowDBHelper.searchBorrowByReaderId(getActivity(),readerId).size());
            list.add(map);
        }
        return list;
    }

    class ReaderManagerAdapter extends RecyclerView.Adapter<ReaderManagerAdapter.VH> {

        private List<HashMap<String,Object>> dataList;
        private LayoutInflater layoutInflater;
        private Context context;

        public ReaderManagerAdapter(Context context, ArrayList<HashMap<String,Object>> datas) {
            this.layoutInflater=LayoutInflater.from(context);
            this.dataList = datas;
            this.context=context;
        }


        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(layoutInflater.inflate(R.layout.lv_item_reader,parent,false));
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            int readerId=(int)dataList.get(position).get("readerId");
            int borrowNum=(int)dataList.get(position).get("borrowNum");
            holder.readerId.setText(""+readerId);
            holder.borrowNum.setText(""+borrowNum);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        class VH extends RecyclerView.ViewHolder {
            private TextView readerId;
            private TextView borrowNum;
            VH(View itemView) {
                super(itemView);
                readerId=(TextView)itemView.findViewById(R.id.readerId_reader_item);
                borrowNum=(TextView)itemView.findViewById(R.id.borrowBookNum_reader_item);
            }
        }
    }
}

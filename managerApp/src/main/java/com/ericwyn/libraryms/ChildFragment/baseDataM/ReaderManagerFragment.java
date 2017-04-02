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
import com.ericwyn.libraryms.dbUtil.dbHelper.BorrowDBHelper;
import com.ericwyn.libraryms.dbUtil.dbHelper.ReaderDBHelper;
import com.ericwyn.libraryms.updataDialog.UpdataReaderDialogBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 读者管理的childFragment
 * Created by ericwyn on 17-3-27.
 */

public class ReaderManagerFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<HashMap<String,Object>> dataList;
    private ReaderManagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.childlayout_recyclerview_fragment,container,false);
        recyclerView=(RecyclerView) view.findViewById(R.id.rv_child_fragment);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        dataList=getData();
        adapter=new ReaderManagerAdapter(getActivity(),dataList);
        recyclerView.setAdapter(adapter);
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

        public ReaderManagerAdapter(Context context, ArrayList<HashMap<String,Object>> datas) {
            this.layoutInflater=LayoutInflater.from(context);
            this.dataList = datas;
        }


        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(layoutInflater.inflate(R.layout.lv_item_reader,parent,false));
        }

        @Override
        public void onBindViewHolder(VH holder, final int position) {
            final int readerId=(int)dataList.get(position).get("readerId");
            int borrowNum=(int)dataList.get(position).get("borrowNum");
            holder.readerId.setText(""+readerId);
            holder.borrowNum.setText(""+borrowNum);
            holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdataReaderDialogBuilder dialogBuilder=
                            new UpdataReaderDialogBuilder(getActivity(),dataList.get(position));
                    dialogBuilder.show();

                }
            });
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        class VH extends RecyclerView.ViewHolder {
//            private CardView cardView;
            private TextView readerId;
            private TextView borrowNum;
            private ConstraintLayout constraintLayout;
            VH(View itemView) {
                super(itemView);
                readerId=(TextView)itemView.findViewById(R.id.readerId_reader_item);
                borrowNum=(TextView)itemView.findViewById(R.id.borrowBookNum_reader_item);
//                cardView=(CardView)itemView.findViewById(R.id.baseCV_reader_item);
                constraintLayout=(ConstraintLayout)itemView.findViewById(R.id.conslayout_reader_item);
            }
        }
    }
    public void updata(){
        dataList.clear();
        dataList.addAll(getData());
        adapter.notifyDataSetChanged();
    }

}

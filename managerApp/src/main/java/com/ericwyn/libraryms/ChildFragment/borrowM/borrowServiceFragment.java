package com.ericwyn.libraryms.ChildFragment.borrowM;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ericwyn.libraryms.R;
import com.znq.zbarcode.CaptureActivity;

/**
 * 借还书服务的Fragment
 * Created by ericwyn on 17-4-11.
 */

public class borrowServiceFragment extends Fragment {
    private CardView borrowService;
    private CardView returnService;
    private static final int QR_CODE=10086;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.borrowservice,container,false);

        borrowService=(CardView)view.findViewById(R.id.cv_borrowService_borrowServiceFragmeng);
        returnService=(CardView)view.findViewById(R.id.cv_returnService_borrowServiceFragmeng);

        borrowService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent1, QR_CODE);
            }
        });

        returnService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

}

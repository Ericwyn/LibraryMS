package com.ericwyn.libraryms;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

/**
 * Created by ericwyn on 17-3-30.
 */

public class AddReaderDialog extends Dialog {
    private Context mContext;


    public AddReaderDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext=context;
    }

    public AddReaderDialog(@NonNull Context context) {
        super(context);
        mContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_reader_dialog);

    }
}

package com.android.p2pflowernet.project.view.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.android.p2pflowernet.project.R;

/**
 * Created by heguozhong on 2018/1/30/030.
 * 打烊dialog
 */

public class DaYangDialog extends Dialog{
    public DaYangDialog(Context context) {
        super(context);
        this.show();
    }

    public DaYangDialog(Context context, int theme) {
        super(context, theme);
        this.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.da_yang_layout);
    }

}

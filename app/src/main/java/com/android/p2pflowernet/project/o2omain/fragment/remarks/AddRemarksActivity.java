package com.android.p2pflowernet.project.o2omain.fragment.remarks;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by heguozhong on 2018/1/12/012.
 * 添加备注主界面
 */

public class AddRemarksActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        Intent intent = getIntent();
        int isInvoice = intent.getIntExtra("isInvoice", 0);
        return AddRemarksFragment.newInstance(isInvoice);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            removeFragment();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
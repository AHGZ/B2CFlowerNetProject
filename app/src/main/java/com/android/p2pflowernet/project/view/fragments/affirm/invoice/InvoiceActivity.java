package com.android.p2pflowernet.project.view.fragments.affirm.invoice;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/7.
 * by--选择发票页面
 */

public class InvoiceActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {

        Intent intent = getIntent();
        String invoice = intent.getStringExtra("invoice");
        String tax_num = intent.getStringExtra("tax_num");
        String userType = intent.getStringExtra("userType");
        return InvoiceFragment.newIntence(invoice, tax_num, userType);
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

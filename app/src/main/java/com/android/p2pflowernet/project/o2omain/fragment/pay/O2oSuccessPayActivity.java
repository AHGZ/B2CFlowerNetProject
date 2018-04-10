package com.android.p2pflowernet.project.o2omain.fragment.pay;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/12/29.
 * by--支付是否完成的回调页面
 */

public class O2oSuccessPayActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        boolean isok = intent.getBooleanExtra("isok", false);
        String payMoney = intent.getStringExtra("payMoney");

        return O2oSuccessPayFragment.newIntence(type, isok, payMoney);
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

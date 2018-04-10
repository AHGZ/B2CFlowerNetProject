package com.android.p2pflowernet.project.view.fragments.affirm.pay;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/2.
 * by--支付密码设置的页面
 */

public class PayPwdActivity extends KActivity {


    @Override
    protected KFragment getFirstFragment() {
        return PayPwdFragment.newIntence();
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

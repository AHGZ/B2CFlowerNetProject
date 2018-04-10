package com.android.p2pflowernet.project.view.fragments.affirm;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.check.SetPayPwdFragment;

/**
 * Created by caishen on 2017/12/6.
 * by--设置支付密码
 */

public class SetPayPwdActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {

        Intent intent = getIntent();
        String tag = intent.getStringExtra("tag");
        return SetPayPwdFragment.newIntence(tag);
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

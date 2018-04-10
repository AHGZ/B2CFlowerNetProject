package com.android.p2pflowernet.project.view.fragments.mine.setting.pay.forget;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/12/7.
 * by--忘记支付密码
 */

public class ForgetPayPwdActivity extends KActivity{
    @Override
    protected KFragment getFirstFragment() {

        return ForgetPayPwdFragment.newIntence();
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

package com.android.p2pflowernet.project.view.fragments.mine.setting.pay;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/12/7.
 * by--支付设置
 */

public class SettingPayActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {

        return SettingPayFragment.newIntence();
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

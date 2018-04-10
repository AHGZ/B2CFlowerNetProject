package com.android.p2pflowernet.project.view.fragments.mine.setting.login;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/18.
 * by--更换手机号
 */

public class ChangePhoneActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        return ChangePhoneFragment.newIntence();
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

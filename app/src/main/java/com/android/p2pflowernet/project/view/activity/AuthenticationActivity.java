package com.android.p2pflowernet.project.view.activity;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.fragments.authentication.AuthenticationFragment;

/**
 * Created by zhangkun on 2018/3/12.
 * 实名认证
 */

public class AuthenticationActivity extends KActivity{
    @Override
    protected KFragment getFirstFragment() {
        return AuthenticationFragment.newIntence();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            removeFragment();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

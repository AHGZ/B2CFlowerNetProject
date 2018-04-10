package com.android.p2pflowernet.project.view.activity;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.fragments.login.LoginFragment;

/**
 * author: zhangpeisen
 * created on: 2017/10/11 上午9:26
 * description: 登录页面
 */
public class LoginActivity extends KActivity {

    @Override
    protected KFragment getFirstFragment() {
        return LoginFragment.newInstance();
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

package com.android.p2pflowernet.project.view.activity;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.fragments.updatepwd.UpdatePwdFragment;


/**
 * Created by caishen on 2017/10/18.
 * by--忘记登录密码的页面
 */

public class UpdatePasswordActivity extends KActivity {

    @Override
    protected KFragment getFirstFragment() {
        return UpdatePwdFragment.newInstance();
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

package com.android.p2pflowernet.project.view.fragments.mine.setting.personal;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/15.
 * by--二维码邀请页面
 */

public class QRInviteActivity extends KActivity {


    @Override
    protected KFragment getFirstFragment() {
        return QrInviteFragment.newIntence();
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

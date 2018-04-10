package com.android.p2pflowernet.project.view.fragments.mine.setting;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/4.
 * by--设置页面
 */

public class SettingActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        return SettingFragment.newIntence();
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

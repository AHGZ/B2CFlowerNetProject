package com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/20.
 * by--完善个人信息
 */

public class ImproveInfoActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        return ImproveInfoFragment.newIntence();
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

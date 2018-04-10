package com.android.p2pflowernet.project.view.activity;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.fragments.platformdata.regiondata.RegionRoleDataFragment;

/**
 * Created by zhangkun on 2018/1/31.
 */

public class RegionRoleDataActivity extends KActivity{
    @Override
    protected KFragment getFirstFragment() {
        return RegionRoleDataFragment.newInstance();
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

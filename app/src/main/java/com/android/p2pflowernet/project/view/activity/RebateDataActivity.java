package com.android.p2pflowernet.project.view.activity;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.fragments.platformdata.rebatedata.RebateDataFragment;

/**
 * Created by zhangkun on 2018/1/30.
 * 返润数据
 */

public class RebateDataActivity extends KActivity{
    @Override
    protected KFragment getFirstFragment() {
        return RebateDataFragment.newInstance();
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

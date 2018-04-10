package com.android.p2pflowernet.project.view.fragments.mine.waitevaluated;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/12/14.
 * by--待评价
 */

public class WaitEvaluatedActivity extends KActivity{
    @Override
    protected KFragment getFirstFragment() {
        return WaitEvaluatedFragment.newIntence();
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

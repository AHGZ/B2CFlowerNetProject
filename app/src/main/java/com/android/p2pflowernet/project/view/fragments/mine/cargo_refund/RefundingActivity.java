package com.android.p2pflowernet.project.view.fragments.mine.cargo_refund;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/17.
 * by--退换货
 */

public class RefundingActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        return RefundingFragment.newIntence();
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

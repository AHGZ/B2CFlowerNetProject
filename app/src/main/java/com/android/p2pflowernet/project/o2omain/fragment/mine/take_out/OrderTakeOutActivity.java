package com.android.p2pflowernet.project.o2omain.fragment.mine.take_out;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2018/1/5.
 * by--外卖订单
 */

public class OrderTakeOutActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {

        return OrderTakeOutFragment.newIntence();
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

package com.android.p2pflowernet.project.view.fragments.mine.orderflow.refund_cargio;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/18.
 * by--选择快递公司的页面
 */

public class ExpressageActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        return ExpressageFragment.newIntence();
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

package com.android.p2pflowernet.project.view.fragments.mine.applyfor.cloud;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/22.
 * by--申请云工成功的页面
 */

public class SuccessCloudActivity extends KActivity{
    @Override
    protected KFragment getFirstFragment() {

        return SuccessCloudFragment.newIntence();
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

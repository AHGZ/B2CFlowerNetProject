package com.android.p2pflowernet.project.view.activity;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.fragments.branddetail.BrandDetailFrangment;

/**
 * Created by caishen on 2017/10/19.
 * by--品牌详情页面
 */

public class BrandDetailActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        return BrandDetailFrangment.newInstance();
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

package com.android.p2pflowernet.project.view.fragments.index.bigdata;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2018/1/31.
 * by--大数据展示页面
 */

public class BigDataShowActivity extends KActivity{
    @Override
    protected KFragment getFirstFragment() {
        return  BigDataShowFragment.newIntence();
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

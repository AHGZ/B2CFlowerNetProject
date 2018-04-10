package com.android.p2pflowernet.project.view.activity;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.fragments.citylist.CityListFragment;

/**
 * Created by caishen on 2017/10/19.
 * by--城市定位列表
 */

public class CityListActivity extends KActivity {

    @Override
    protected KFragment getFirstFragment() {
        return CityListFragment.newInstance();
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

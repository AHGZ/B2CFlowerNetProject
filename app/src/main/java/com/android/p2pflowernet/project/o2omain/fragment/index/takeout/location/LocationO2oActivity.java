package com.android.p2pflowernet.project.o2omain.fragment.index.takeout.location;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2018/1/3.
 * by--选择收货地址
 */

public class LocationO2oActivity extends KActivity{
    @Override
    protected KFragment getFirstFragment() {
        String flag = getIntent().getStringExtra("flag");
        return LocationO2oFragment.newIntence(flag) ;
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

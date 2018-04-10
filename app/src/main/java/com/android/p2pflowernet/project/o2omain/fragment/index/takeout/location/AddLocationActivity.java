package com.android.p2pflowernet.project.o2omain.fragment.index.takeout.location;

import android.os.Bundle;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2018/1/3.
 * by--添加地址
 */

public class AddLocationActivity extends KActivity{
    @Override
    protected KFragment getFirstFragment() {
        Bundle data = getIntent().getExtras();
        String tag = getIntent().getStringExtra("tag");
        return  AddLocationFragment.newIntence(data,tag);
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

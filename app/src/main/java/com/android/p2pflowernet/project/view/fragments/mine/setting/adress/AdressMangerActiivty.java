package com.android.p2pflowernet.project.view.fragments.mine.setting.adress;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/23.
 * by--地址管理
 */

public class AdressMangerActiivty extends KActivity{
    @Override
    protected KFragment getFirstFragment() {
        Intent intent = getIntent();
        String state = intent.getStringExtra("state");
        return AdressMangerFragment.newIntence(state);
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

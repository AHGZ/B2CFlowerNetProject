package com.android.p2pflowernet.project.view.fragments.affirm.partnerapttitude;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/9.
 * by--购买合伙人资质
 */

public class BuyPartnerAptitudeActiivty extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        Intent intent = getIntent();
        boolean isAdd = intent.getBooleanExtra("isAdd", false);
        String type = intent.getStringExtra("type");
        return BuyPartnerAptitudeFragment.newIntence(type, isAdd);
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

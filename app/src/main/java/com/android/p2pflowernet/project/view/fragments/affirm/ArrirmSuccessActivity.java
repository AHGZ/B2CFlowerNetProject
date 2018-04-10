package com.android.p2pflowernet.project.view.fragments.affirm;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/12/6.
 * by--支付成功页面
 */

public class ArrirmSuccessActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {

        Intent intent = getIntent();
        String message = intent.getStringExtra("message");
        String type = intent.getStringExtra("type");
        String money = intent.getStringExtra("money");
        boolean isOK = intent.getBooleanExtra("isOK", false);
        String tag = intent.getStringExtra("tag");
        return AffirmSuccessFragment.newIntence(message, type, isOK,money,tag);
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

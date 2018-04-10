package com.android.p2pflowernet.project.view.fragments.mine.cargo_refund;

import android.text.TextUtils;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/20.
 * by--申请仲裁
 */

public class ApplyForArbitrationActivity extends KActivity{
    @Override
    protected KFragment getFirstFragment() {
        String refundid = getIntent().getStringExtra("refundid");
        return ApplyForArbitrationFragment.newIntence(TextUtils.isEmpty(refundid) ? "" : refundid);
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

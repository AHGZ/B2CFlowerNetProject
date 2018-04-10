package com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/20.
 * by--完善保单信息
 */

public class ImproveGuaranteeActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        Intent intent = getIntent();
        String record_id = intent.getStringExtra("record_id");
        return ImproveGuaranteeFragment.newIntence(record_id);
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

package com.android.p2pflowernet.project.view.fragments.mine.orderflow.evaluate;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/12/5.
 * by--评价
 */

public class EvaluateActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        Intent intent = getIntent();
        String order_id = intent.getStringExtra("order_id");
        String mesuaName = intent.getStringExtra("mesuaName");
        return EvaluateFragment.newIntence(order_id,mesuaName);
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

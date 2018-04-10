package com.android.p2pflowernet.project.view.fragments.mine.orderflow.logistics;

import android.text.TextUtils;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/20.
 * by--查看物流信息
 */

public class LogisticsDetailActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        String state = getIntent().getStringExtra("state");
        String ordernum = getIntent().getStringExtra("ordernum");
        String waybill_num = getIntent().getStringExtra("waybill_num");
        String express_id = getIntent().getStringExtra("express_id");
        return LogiSticsDetailFragment.newIntence(
                TextUtils.isEmpty(state) ? "" : state
                , TextUtils.isEmpty(ordernum) ? "" : ordernum,
                TextUtils.isEmpty(waybill_num) ? "" : waybill_num
                ,TextUtils.isEmpty(express_id) ? "" : express_id);
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

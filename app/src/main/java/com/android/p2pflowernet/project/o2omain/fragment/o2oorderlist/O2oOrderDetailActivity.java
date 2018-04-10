package com.android.p2pflowernet.project.o2omain.fragment.o2oorderlist;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2018/1/23.
 * by--外卖商品商品详情
 */

public class O2oOrderDetailActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {

        String merch_id = getIntent().getStringExtra("merch_id");
        String goods_id = getIntent().getStringExtra("goods_id");
        return O2oOrderDetailFragment.newIntence(merch_id, goods_id);
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

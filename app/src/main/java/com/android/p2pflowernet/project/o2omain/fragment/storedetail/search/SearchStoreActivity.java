package com.android.p2pflowernet.project.o2omain.fragment.storedetail.search;

import android.os.Bundle;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2018/2/3.
 * by--搜索店铺商品页面
 */

public class SearchStoreActivity extends KActivity{
    @Override
    protected KFragment getFirstFragment() {
        Bundle extras = getIntent().getExtras();
        String merch_id = getIntent().getStringExtra("merch_id");
        return SearchStoreFragment.newIntence(extras,merch_id);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            removeFragment();
        }
        return super.onKeyDown(keyCode, event);
    }
}

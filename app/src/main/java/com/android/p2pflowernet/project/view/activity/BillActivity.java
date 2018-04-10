package com.android.p2pflowernet.project.view.activity;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.fragments.mine.wallet.bill.BillFragment;

/**
 * author: zhangpeisen
 * created on: 2017/10/11 上午9:26
 * description: 账单页面
 */
public class BillActivity extends KActivity {

    @Override
    protected KFragment getFirstFragment() {
        return BillFragment.newIntence();
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

package com.android.p2pflowernet.project.view.fragments.mine.wallet.bankcard;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2018/1/12.
 * by--绑定银行卡
 */

public class BankCardInfoActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        String tag = getIntent().getStringExtra("tag");
        return BankcardInfoFragment.newIntence(tag);
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

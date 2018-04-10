package com.android.p2pflowernet.project.view.activity;

import android.text.TextUtils;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.fragments.mine.accumincome.AccoumIncomeFragment;

/**
 * author: zhangpeisen
 * created on: 2017/10/11 上午9:26
 * description: 累计收益页面
 */
public class AccoumIncomeActivity extends KActivity {

    @Override
    protected KFragment getFirstFragment() {
        String is_agent = getIntent().getStringExtra("is_agent");
        String is_partner = getIntent().getStringExtra("is_partner");
        return AccoumIncomeFragment.newIntence(TextUtils.isEmpty(is_agent) ? "" : is_agent, TextUtils.isEmpty(is_partner) ? "" : is_partner);
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

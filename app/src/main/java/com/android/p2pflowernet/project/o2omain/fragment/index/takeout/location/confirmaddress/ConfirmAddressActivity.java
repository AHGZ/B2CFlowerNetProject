package com.android.p2pflowernet.project.o2omain.fragment.index.takeout.location.confirmaddress;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by zhangkun on 2018/2/2.
 * 确认收货地址
 */

public class ConfirmAddressActivity extends KActivity{
    @Override
    protected KFragment getFirstFragment() {
        String flag = getIntent().getStringExtra("flag");
        return ConfirmAddressFragment.newIntence(flag);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            removeFragment();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

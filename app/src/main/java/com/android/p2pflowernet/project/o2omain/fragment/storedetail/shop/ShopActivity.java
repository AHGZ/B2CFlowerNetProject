package com.android.p2pflowernet.project.o2omain.fragment.storedetail.shop;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by heguozhong on 2018/1/12/012.
 * 店铺商家主界面
 */

public class ShopActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        return ShopFragment.newInstance("");
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

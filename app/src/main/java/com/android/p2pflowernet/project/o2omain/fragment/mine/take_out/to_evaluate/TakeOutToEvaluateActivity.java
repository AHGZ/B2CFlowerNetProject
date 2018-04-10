package com.android.p2pflowernet.project.o2omain.fragment.mine.take_out.to_evaluate;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by zhangkun on 2018/1/20.
 */

public class TakeOutToEvaluateActivity extends KActivity {

    @Override
    protected KFragment getFirstFragment() {
        String orderNumber = getIntent().getStringExtra("order_num");

        return TakeOutToEvaluateFragment.newIntence(orderNumber);
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

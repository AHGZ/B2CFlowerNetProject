package com.android.p2pflowernet.project.o2omain.fragment.pay;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/12/29.
 * by--确认付款
 */

public class AffirmPayActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {

        String resultString = getIntent().getStringExtra("resultString");
        return AffirmPayFragment.newIntence(resultString);
    }

    /**
     * 退出主界面
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            removeFragment();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

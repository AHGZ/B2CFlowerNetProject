package com.android.p2pflowernet.project.view.fragments.goods.info.compare;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2018/1/29.
 * by--查看更多比价页面
 */

public class CompareListActivity extends KActivity {

    @Override
    protected KFragment getFirstFragment() {
        String spec_id = getIntent().getStringExtra("spec_id");
        return CompareListFragment.NewIntence(spec_id);
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

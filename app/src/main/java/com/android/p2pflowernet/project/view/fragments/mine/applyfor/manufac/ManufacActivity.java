package com.android.p2pflowernet.project.view.fragments.mine.applyfor.manufac;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.entity.IdEntityBean;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2018/1/29.
 * by--供应商申请页面
 */

public class ManufacActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        String state = getIntent().getStringExtra("state");
        IdEntityBean bean = (IdEntityBean) getIntent().getSerializableExtra("idEntityBean");
        return ManufacFragment.NewIntence(state,bean);
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

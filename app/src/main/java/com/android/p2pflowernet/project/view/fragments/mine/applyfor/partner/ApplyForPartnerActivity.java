package com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner;

import android.os.Bundle;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.entity.IdEntityBean;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/20.
 * by--申请合伙人
 */

public class ApplyForPartnerActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        Bundle bundle = getIntent().getExtras();
        IdEntityBean bean = (IdEntityBean) bundle.getSerializable("idEntityBean");
        return ApplyForPartnerFragment.newIntence(bean);
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

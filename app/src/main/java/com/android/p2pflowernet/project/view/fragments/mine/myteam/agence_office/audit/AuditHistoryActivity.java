package com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office.audit;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/12/1.
 * by--审核历史
 */

public class AuditHistoryActivity extends KActivity{
    @Override
    protected KFragment getFirstFragment() {

        return AuditHistoryFragment.newIntence();
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

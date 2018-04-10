package com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office.strongplan;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/30.
 * by--成长计划
 */

public class StrongPlanActivity extends KActivity{
    @Override
    protected KFragment getFirstFragment() {

        return StrongPlanFragment.newIntence();
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

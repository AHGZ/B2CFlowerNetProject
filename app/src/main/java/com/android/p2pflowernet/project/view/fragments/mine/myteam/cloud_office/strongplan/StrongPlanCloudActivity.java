package com.android.p2pflowernet.project.view.fragments.mine.myteam.cloud_office.strongplan;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/30.
 * by--云工成长计划
 */

public class StrongPlanCloudActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {

        return StrongPlanCloudFragment.newIntence();
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

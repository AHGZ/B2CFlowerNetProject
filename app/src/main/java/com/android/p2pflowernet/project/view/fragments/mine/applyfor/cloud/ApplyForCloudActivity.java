package com.android.p2pflowernet.project.view.fragments.mine.applyfor.cloud;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.entity.IdEntityBean;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/22.
 * by--申请云工
 */

public class ApplyForCloudActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {

        Intent intent = getIntent();
        String cloudId = intent.getStringExtra("cloudId");
        String isUpdata = intent.getStringExtra("isUpdata");
        String state = intent.getStringExtra("state");
        IdEntityBean bean = (IdEntityBean) intent.getSerializableExtra("idEntityBean");
        return ApplyForCloudFragment.newIntence(isUpdata, cloudId,state,bean);
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

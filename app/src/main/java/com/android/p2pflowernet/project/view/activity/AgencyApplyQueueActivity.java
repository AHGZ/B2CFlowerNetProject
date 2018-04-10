package com.android.p2pflowernet.project.view.activity;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.agency.applyqueque.ApplyQueueFragment;

/**
 * @描述: 申请排行主页
 * @创建人：zhangpeisen
 * @创建时间：2017/11/23 下午6:05
 * @修改人：zhangpeisen
 * @修改时间：2017/11/23 下午6:05
 * @修改备注：
 * @throws
 */

public class AgencyApplyQueueActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        Intent intent = getIntent();
        String state = intent.getStringExtra("state");
        String id = getIntent().getStringExtra("id");
        return ApplyQueueFragment.newIntence(state,id);
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

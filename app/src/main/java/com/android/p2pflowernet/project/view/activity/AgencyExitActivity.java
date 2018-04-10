package com.android.p2pflowernet.project.view.activity;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.agency.AgencyExitFragment;

/**
 * @描述: 代理人退出主页
 * @创建人：zhangpeisen
 * @创建时间：2017/11/23 下午6:05
 * @修改人：zhangpeisen
 * @修改时间：2017/11/23 下午6:05
 * @修改备注：
 * @throws
 */

public class AgencyExitActivity extends KActivity {

    @Override
    protected KFragment getFirstFragment() {
        if (getIntent().getExtras() == null) {
            return null;
        }
        String state = getIntent().getStringExtra("state");
        return AgencyExitFragment.newIntence(state);
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

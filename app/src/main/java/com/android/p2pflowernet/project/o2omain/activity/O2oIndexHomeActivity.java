package com.android.p2pflowernet.project.o2omain.activity;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.fragment.index.O2oIndexFragment;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by caishen on 2018/2/3.
 * by--o2o首页
 */

public class O2oIndexHomeActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        String searchName = getIntent().getStringExtra("searchName");
        return O2oIndexFragment.newInstance(searchName);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            removeFragment();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /***
     * 友盟统计
     */
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


}

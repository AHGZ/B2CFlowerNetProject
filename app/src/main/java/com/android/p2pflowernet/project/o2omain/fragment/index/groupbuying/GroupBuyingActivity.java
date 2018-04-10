package com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by heguozhong on 2017/12/28/028.
 * 团购优惠主页面
 */

public class GroupBuyingActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        String city = getIntent().getStringExtra("city");
        return GroupBuyingFragment.newInstance(city);
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

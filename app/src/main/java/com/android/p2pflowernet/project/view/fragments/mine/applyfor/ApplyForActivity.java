package com.android.p2pflowernet.project.view.fragments.mine.applyfor;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.event.UpdateUserInfoEvent;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by caishen on 2017/11/11.
 * by--申请更多身份的页面
 */

public class ApplyForActivity extends KActivity {


    @Override
    protected KFragment getFirstFragment() {
        return ApplyForFragment.newIntence();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            //发送刷新我的页面数据
            EventBus.getDefault().post(new UpdateUserInfoEvent());

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

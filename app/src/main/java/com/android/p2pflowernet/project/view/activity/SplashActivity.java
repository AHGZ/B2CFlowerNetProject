package com.android.p2pflowernet.project.view.activity;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.fragments.advertising.SplashFragment;


/**
 * author: zhangpeisen
 * created on: 2018/1/16 上午9:41
 * description: 花返网启动页
 */
public class SplashActivity extends KActivity {

    @Override
    protected KFragment getFirstFragment() {
        return SplashFragment.newIntence();
    }

    /**
     * 屏蔽物理返回按钮
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

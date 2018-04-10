package com.android.p2pflowernet.project.view.fragments.mine.setting.feedback;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/24.
 * by--意见反馈
 */

public class FeedBackActiivty extends KActivity {
    @Override
    protected KFragment getFirstFragment() {

        return FeedBackFragment.newIntence();
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

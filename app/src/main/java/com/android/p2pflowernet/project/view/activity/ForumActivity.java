package com.android.p2pflowernet.project.view.activity;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.fragments.forum.ForumFragment;

/**
 * Created by zhangkun on 2018/1/18.
 * 花返讲堂主页
 */

public class ForumActivity extends KActivity {

    @Override
    protected KFragment getFirstFragment() {
        return ForumFragment.newInstance();
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

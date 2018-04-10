package com.android.p2pflowernet.project.view.fragments.mine.message;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/4.
 * by--我的--消息类型列表的页面
 */

public class MessageActivity extends KActivity{
    @Override
    protected KFragment getFirstFragment() {
        return MessageFragment.newIntence();
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

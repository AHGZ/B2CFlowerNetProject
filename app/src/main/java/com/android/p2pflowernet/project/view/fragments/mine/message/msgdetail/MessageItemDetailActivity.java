package com.android.p2pflowernet.project.view.fragments.mine.message.msgdetail;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/12/28.
 * by--查看系统通知，申请通知，返润通知的详情
 */

public class MessageItemDetailActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        String notice_id = intent.getStringExtra("notice_id");
        return MessageItemDetailFragment.newItence(type, notice_id);
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

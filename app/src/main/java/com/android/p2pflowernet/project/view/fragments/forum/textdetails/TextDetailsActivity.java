package com.android.p2pflowernet.project.view.fragments.forum.textdetails;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by zhangkun on 2018/1/23.
 * 花返文章内容详情
 */

public class TextDetailsActivity extends KActivity {

    @Override
    protected KFragment getFirstFragment() {
        String id = getIntent().getStringExtra("id");
        return TextDetailsFragment.newIntence(id);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            removeFragment();
        }
        return super.onKeyDown(keyCode, event);
    }
}

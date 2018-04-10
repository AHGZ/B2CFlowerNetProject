package com.android.p2pflowernet.project.view.activity;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.fragments.knowh5.KnowMoreFragment;

/**
 * Created by caishen on 2017/12/1.
 * by--了解更多的h5页面
 */

public class KnowMoreActivity extends KActivity{
    @Override
    protected KFragment getFirstFragment() {

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        return  KnowMoreFragment.newIntence(url);
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

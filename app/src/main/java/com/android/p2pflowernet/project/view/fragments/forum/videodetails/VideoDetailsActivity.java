package com.android.p2pflowernet.project.view.fragments.forum.videodetails;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by zhangkun on 2018/1/22.
 */

public class VideoDetailsActivity extends KActivity {

    @Override
    protected KFragment getFirstFragment() {

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        return VideoDetailsFragment.newInstance(id);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            removeFragment();
        }
        return super.onKeyDown(keyCode, event);
    }

}

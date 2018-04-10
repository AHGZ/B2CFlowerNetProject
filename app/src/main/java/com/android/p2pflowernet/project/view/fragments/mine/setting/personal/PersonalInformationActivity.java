package com.android.p2pflowernet.project.view.fragments.mine.setting.personal;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.event.UpdateUserInfoEvent;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by caishen on 2017/11/15.
 * by--个人信息
 */

public class PersonalInformationActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        return PersonalInformationFragment.newIntence();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            EventBus.getDefault().post(new UpdateUserInfoEvent());
            removeFragment();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

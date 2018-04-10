package com.android.p2pflowernet.project.view.fragments.affirm.pay.check;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.event.OrderDeatailRefreshEvent;
import com.android.p2pflowernet.project.event.PayCancleEvent;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by caishen on 2017/11/22.
 * by--检验支付密码
 */

public class CheckPayPwdActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        Intent intent = getIntent();
        String tag = intent.getStringExtra("tag");
        return CheckPayPwdFragment.newIntence(tag);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            EventBus.getDefault().post(new PayCancleEvent());//发送消息判断是否取消支付
            EventBus.getDefault().post(new OrderDeatailRefreshEvent());
            removeFragment();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

package com.android.p2pflowernet.project.view.fragments.affirm.partnerapttitude;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.event.AgenceOfficeEvent;
import com.android.p2pflowernet.project.event.PaySuccessEvent;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by caishen on 2017/11/21.
 * by--支付成功回调页面
 */

public class SuccessPayActivity extends KActivity {


    private String ordernum = "";

    @Override
    protected KFragment getFirstFragment() {

        Intent intent = getIntent();
        String message = intent.getStringExtra("message");
        String type = intent.getStringExtra("type");
        boolean isOK = intent.getBooleanExtra("isOK", false);
        String money = intent.getStringExtra("money");
        String type1 = intent.getStringExtra("type1");
        ordernum = intent.getStringExtra("ordernum");
        boolean isadd = intent.getBooleanExtra("isadd", false);
        return SuccessPayFragment.newIntence(message, type, isOK, money, type1, isadd, ordernum);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            EventBus.getDefault().post(new PaySuccessEvent(ordernum));
            //发送刷新代理办公页面数据
            EventBus.getDefault().post(new AgenceOfficeEvent());
            removeFragment();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

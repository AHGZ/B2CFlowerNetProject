package com.android.p2pflowernet.project.view.fragments.affirm;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.entity.OrderDetailBean;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by caishen on 2017/10/28.
 * by--确认订单的页面
 */

public class AffirmIndentActivity extends KActivity {
//
    private FragmentBackListener backListener;
    private boolean isInterception = false;

    @Override
    protected KFragment getFirstFragment() {
        Intent intent = getIntent();
        OrderDetailBean data = (OrderDetailBean) intent.getSerializableExtra("data");
        return AffirmIndentFragment.newIntence(data);
    }


    public void setBackListener(FragmentBackListener backListener) {
        this.backListener = backListener;
    }

    //是否拦截
    public boolean isInterception() {
        return isInterception;
    }

    public void setInterception(boolean isInterception) {
        this.isInterception = isInterception;
    }


    //返回键监听实现
    public interface FragmentBackListener {
        void onBackForward();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isInterception()) {
                if (backListener != null) {
                    backListener.onBackForward();
                    return false;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /***
     * 友盟统计
     */
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}

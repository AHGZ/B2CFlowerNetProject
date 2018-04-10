package com.android.p2pflowernet.project.o2omain.activity;

import android.os.Bundle;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.fragment.storedetail.StoreDetailFragment;
import com.umeng.analytics.MobclickAgent;

/**
 * author: zhangpeisen
 * created on: 2017/10/11 上午9:26
 * description: 店铺详情
 */
public class StoreDetailActivity extends KActivity {

    private FragmentBackListener backListener;
    private boolean isInterception = false;

    @Override
    protected KFragment getFirstFragment() {
        Bundle bundle = getIntent().getExtras();
        String tag = getIntent().getStringExtra("flag");//是否是再来一单
        String merch_id = getIntent().getStringExtra("merch_id");
        bundle.putString("flag",tag);
        bundle.putString("merch_id",merch_id);
        return StoreDetailFragment.newInstance(bundle);
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

package com.android.p2pflowernet.project.o2omain.fragment.storedetail.confirmorder;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.entity.GoPayBean;
import com.android.p2pflowernet.project.entity.O2oIndexBean;
import com.android.p2pflowernet.project.entity.ShopCart;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by heguozhong on 2018/1/10/010.
 * 确认订单主页面
 */

public class ConfirmOrderActivity extends KActivity {

    private FragmentBackListener backListener;
    private boolean isInterception = false;

    @Override
    protected KFragment getFirstFragment() {
        ShopCart shopCart = (ShopCart) getIntent().getSerializableExtra("shopcart");
        String goodslist = getIntent().getStringExtra("goodslist");
        String merch_id = getIntent().getStringExtra("merch_id");
        GoPayBean gopaybean = (GoPayBean) getIntent().getSerializableExtra("gopaybean");
        O2oIndexBean o2oIndexBean1 = (O2oIndexBean) getIntent().getSerializableExtra("o2oIndexBean1");
        return ConfirmOrderFragment.newInstance(goodslist, shopCart, gopaybean, merch_id, o2oIndexBean1);
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

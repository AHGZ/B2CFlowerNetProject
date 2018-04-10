package com.android.p2pflowernet.project.view.activity;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.entity.ShopCarBean;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.fragments.goods.detail.GoodsDetailHomeFragment;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;

/**
 * author: zhangpeisen
 * created on: 2017/10/18 下午2:59
 * description: 商品详情主页面
 */
public class GoodsDetailActivity extends KActivity {

    private FragmentBackListener backListener;
    private ShopCarBean.ListBean.ShopBean mShopBean;
    private boolean isInterception = false;

    @Override
    protected KFragment getFirstFragment() {

        Intent intent = getIntent();
        String goodsId = intent.getStringExtra("goodsId");
        if(intent.hasExtra("shopBean")) {
            mShopBean = (ShopCarBean.ListBean.ShopBean) intent.getSerializableExtra("shopBean");
        }
        return GoodsDetailHomeFragment.newInstance(goodsId, mShopBean);
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


    //分享的回调页面
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
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
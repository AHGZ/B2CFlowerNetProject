package com.android.p2pflowernet.project.view.fragments.goods.goodslist;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.event.GoodListSearchEvent;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by caishen on 2017/10/23.
 * by--商品列表筛选页面
 */

public class GoodsListActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        String id = getIntent().getExtras().getString("id");
        String tag = getIntent().getStringExtra("tag");
        String searchName = getIntent().getStringExtra("searchName");
        return GoodsListFragment.newInstance(id, tag, searchName);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            removeFragment();
            return true;
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        String id = getIntent().getExtras().getString("id");
        String tag = getIntent().getStringExtra("tag");
        String searchName = getIntent().getStringExtra("searchName");
        EventBus.getDefault().post(new GoodListSearchEvent(id,tag,searchName));

    }

}

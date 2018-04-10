package com.android.p2pflowernet.project.view.fragments.search;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.event.MainEvent;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.O2oMainActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by caishen on 2017/10/26.
 * by--搜索页面
 */

public class SearchActivity extends KActivity {

    private String tag = "0";//0-b2c 1-o2o 2-外卖 3商品分类 4分类

    @Override
    protected KFragment getFirstFragment() {
        tag = getIntent().getStringExtra("tag");
        int cate_id = getIntent().getIntExtra("cate_id", 0);
        int zcate_id = getIntent().getIntExtra("zcate_id", 0);
        return SearchFragment.newInstance(tag, zcate_id, cate_id);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            removeFragment();
            if (tag.equals("0")) {//b2c首页
                //发送消息跳转到b2c首页
                EventBus.getDefault().post(new MainEvent(0));
            } else if (tag.equals("1")) {//o2o首页
                //发送消息跳转到o2o首页
                Intent intent = new Intent(this, O2oMainActivity.class);
                intent.putExtra("searchName", "").addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("index", 0);
                startActivity(intent);
            } else if (tag.equals("4")) {
                //发送消息跳转到分类页面
                EventBus.getDefault().post(new MainEvent(1));
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

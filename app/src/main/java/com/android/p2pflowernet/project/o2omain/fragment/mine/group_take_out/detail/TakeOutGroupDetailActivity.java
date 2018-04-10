package com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out.detail;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.entity.TakeOutOrderGroupBean;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2018/1/6.
 * by--团购订单详情
 */

public class TakeOutGroupDetailActivity extends KActivity{
    @Override
    protected KFragment getFirstFragment() {
        TakeOutOrderGroupBean.ListBean listBean = (TakeOutOrderGroupBean.ListBean) getIntent().getSerializableExtra("good");
        return TakeOutGroupDetailFragment.newIntence(listBean);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            removeFragment();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

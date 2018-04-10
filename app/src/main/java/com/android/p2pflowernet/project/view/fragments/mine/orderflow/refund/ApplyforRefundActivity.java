package com.android.p2pflowernet.project.view.fragments.mine.orderflow.refund;

import android.text.TextUtils;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.entity.OrderDetailItemBean;
import com.android.p2pflowernet.project.entity.OrderListBean;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/17.
 * by--申请退款
 */

public class ApplyforRefundActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        OrderDetailItemBean orderDetailItemBean = (OrderDetailItemBean) getIntent().getSerializableExtra("ordergooddetail");
        OrderListBean.ListsBean orderlists = (OrderListBean.ListsBean) getIntent().getSerializableExtra("orderlists");
        String ogid = getIntent().getStringExtra("ogid");
        String ordernum = getIntent().getStringExtra("ordernum");
        return ApplyforRefundFragment.newIntence(TextUtils.isEmpty(ogid) ? "" : ogid, TextUtils.isEmpty(ordernum) ? "" : ordernum,
                orderlists,orderDetailItemBean);
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

package com.android.p2pflowernet.project.view.activity;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.entity.OrderDetailItemBean;
import com.android.p2pflowernet.project.entity.RefundBean;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.refund.refunddetail.RefundDetailFragment;

/**
 * author: zhangpeisen
 * created on: 2017/10/11 上午9:26
 * description: 退换货详情
 */
public class RefundDetailActivity extends KActivity {

    @Override
    protected KFragment getFirstFragment() {
        String refundid = getIntent().getStringExtra("refundid");
        RefundBean.ListsBean ListsBean = (RefundBean.ListsBean) getIntent().getSerializableExtra("listsBean");
        OrderDetailItemBean orderDetailItemBean = (OrderDetailItemBean) getIntent().getSerializableExtra("ordergooddetail");
        return RefundDetailFragment.newIntence(refundid, ListsBean, orderDetailItemBean);
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

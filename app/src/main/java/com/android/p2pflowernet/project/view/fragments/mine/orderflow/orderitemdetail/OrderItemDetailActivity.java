package com.android.p2pflowernet.project.view.fragments.mine.orderflow.orderitemdetail;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.entity.OrderListBean;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/10.
 * by--所有订单的点击详情页
 */

public class OrderItemDetailActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        Intent intent = getIntent();
        String order_id = intent.getStringExtra("order_id");
        String state = intent.getStringExtra("state");
        String isreturn = intent.getStringExtra("isreturn");
        OrderListBean.ListsBean orderlists = (OrderListBean.ListsBean) intent.getSerializableExtra("orderlists");
        return OrderItemDetailFragment.newIntence(order_id, state,isreturn,orderlists);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            //发消息判断是否取消支付
//            EventBus.getDefault().post(new PayCancleEvent());
            removeFragment();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}



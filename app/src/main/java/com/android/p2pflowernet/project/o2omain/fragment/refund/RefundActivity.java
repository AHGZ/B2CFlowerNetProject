package com.android.p2pflowernet.project.o2omain.fragment.refund;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.entity.TakeOutBean;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

import java.util.List;

/**
 * Created by zhangkun on 2018/1/24.
 * 申请退款页面
 */

public class RefundActivity extends KActivity{
    @Override
    protected KFragment getFirstFragment() {
        Intent intent = getIntent();
        String pay_channel = intent.getStringExtra("pay_channel");
        String manger_name = intent.getStringExtra("manger_name");
        String manger_img = intent.getStringExtra("manger_img");
        String order_num = intent.getStringExtra("order_num");
        String pay_amount = intent.getStringExtra("pay_amount");
        List<TakeOutBean.ListBean.GoodsBean> goodsBeans = (List<TakeOutBean.ListBean.GoodsBean>) intent.getSerializableExtra("goods");
        return RefundFragment.newIntence(pay_channel,order_num,pay_amount,manger_name,manger_img,goodsBeans);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            removeFragment();
        }
        return super.onKeyDown(keyCode, event);
    }
}

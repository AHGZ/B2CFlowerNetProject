package com.android.p2pflowernet.project.o2omain.fragment.mine.take_out.takefulldetail;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.entity.TakeOutBean;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

import java.util.List;

/**
 * Created by heguozhong on 2018/1/15/015.
 * 外卖明细主页面
 */

public class TakeFullDetailActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        Intent intent = getIntent();
        String order_num = intent.getStringExtra("order_num");
        String countdown_time = intent.getStringExtra("countdown_time");
        String refund_id = intent.getStringExtra("refund_id");
        int id = intent.getIntExtra("id",0);
        List<TakeOutBean.ListBean.GoodsBean> goodsBeans = (List<TakeOutBean.ListBean.GoodsBean>) intent.getSerializableExtra("goods");
        return TakeFullDetailFragment.newIntence(order_num,countdown_time,refund_id,id,goodsBeans);
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

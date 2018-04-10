package com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out.fulldetail;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.entity.TakeOutOrderGroupBean;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by heguozhong on 2018/1/15/015.
 * 团购明细主页面
 */

public class GroupFullDetailActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        Intent intent = getIntent();
        String order_num = intent.getStringExtra("order_num");
        TakeOutOrderGroupBean.ListBean group_listBean = (TakeOutOrderGroupBean.ListBean) intent.getSerializableExtra("group_listBean");
        return GroupFullDetailFragment.newIntence(order_num,group_listBean);
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

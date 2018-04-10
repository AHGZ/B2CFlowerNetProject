package com.android.p2pflowernet.project.view.activity;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.fragments.mine.orderdetail.OrderDetailFragment;

/**
 * @描述:订单详情页面Activity
 * @创建人：zhangpeisen
 * @创建时间：2017/10/30 下午4:54
 * @修改人：zhangpeisen
 * @修改时间：2017/10/30 下午4:54
 * @修改备注：
 * @throws
 */
public class OrderDetailActivity extends KActivity {


    @Override
    protected KFragment getFirstFragment() {
        int index = getIntent().getExtras().getInt("index");
        return OrderDetailFragment.newInstance(index);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
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

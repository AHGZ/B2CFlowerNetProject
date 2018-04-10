package com.android.p2pflowernet.project.view.fragments.mine.orderflow.logistics.info;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.entity.OrderDetailItemBean;
import com.android.p2pflowernet.project.entity.RefundBean;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * @描述: 完善物流
 * @创建人：zhangpeisen
 * @创建时间：2017/12/26 下午4:39
 * @修改人：zhangpeisen
 * @修改时间：2017/12/26 下午4:39
 * @修改备注：
 * @throws
 */

public class LogisticsinfoActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        OrderDetailItemBean orderDetailItemBean = (OrderDetailItemBean) getIntent().getSerializableExtra("refundgooddetail");
        RefundBean.ListsBean ListsBean = (RefundBean.ListsBean) getIntent().getSerializableExtra("refundlists");
        return LogisticsinfoFragment.newIntence(ListsBean, orderDetailItemBean);
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

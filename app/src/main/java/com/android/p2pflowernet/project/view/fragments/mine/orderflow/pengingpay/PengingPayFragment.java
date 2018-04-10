package com.android.p2pflowernet.project.view.fragments.mine.orderflow.pengingpay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * @描述:待付款主类
 * @创建人：zhangpeisen
 * @创建时间：2017/10/30 下午5:40
 * @修改人：zhangpeisen
 * @修改时间：2017/10/30 下午5:40
 * @修改备注：
 * @throws
 */
public class PengingPayFragment extends KFragment<IPengingPaymentView, IPendingPaymentPrenter> {
    public static PengingPayFragment newInstance() {
        Bundle args = new Bundle();
        PengingPayFragment fragment = new PengingPayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public IPendingPaymentPrenter createPresenter() {
        return new IPendingPaymentPrenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.mine_orderdetail_pengingpay_fragment;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {


    }

}

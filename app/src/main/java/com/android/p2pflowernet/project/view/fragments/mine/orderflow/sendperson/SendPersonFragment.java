package com.android.p2pflowernet.project.view.fragments.mine.orderflow.sendperson;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * @描述:待收货主页
 * @创建人：zhangpeisen
 * @创建时间：2017/10/30 下午5:40
 * @修改人：zhangpeisen
 * @修改时间：2017/10/30 下午5:40
 * @修改备注：
 * @throws
 */
public class SendPersonFragment extends KFragment<ISendPersonView, ISendPersonPrenter> {
    public static SendPersonFragment newInstance() {
        Bundle args = new Bundle();
        SendPersonFragment fragment = new SendPersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ISendPersonPrenter createPresenter() {
        return new ISendPersonPrenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.mine_orderdetail_sendperson_fragment;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {


    }

}

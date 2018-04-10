package com.android.p2pflowernet.project.view.fragments.mine.orderflow.personafter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * @描述:退换货
 * @创建人：zhangpeisen
 * @创建时间：2017/10/30 下午5:40
 * @修改人：zhangpeisen
 * @修改时间：2017/10/30 下午5:40
 * @修改备注：
 * @throws
 */
public class PersonAftermarketFragment extends KFragment<IPersonAftermarketView, IPersonAftermarketPrenter> implements IPersonAftermarketView {


    public static PersonAftermarketFragment newInstance() {
        Bundle args = new Bundle();
        PersonAftermarketFragment fragment = new PersonAftermarketFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public IPersonAftermarketPrenter createPresenter() {
        return new IPersonAftermarketPrenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.mine_orderdetail_aftermarket_fragment;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

    }


}

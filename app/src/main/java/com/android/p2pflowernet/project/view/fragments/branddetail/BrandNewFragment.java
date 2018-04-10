package com.android.p2pflowernet.project.view.fragments.branddetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/10/19.
 * by--商品 --最新
 */

public class BrandNewFragment extends KFragment<IBrandNewView, IBranNewPrenter> {
    @Override
    public IBranNewPrenter createPresenter() {
        return new IBranNewPrenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_brand_home;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

    }

    public static Fragment newInstance() {
        return new BrandNewFragment();
    }
}

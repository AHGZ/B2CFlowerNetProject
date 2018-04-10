package com.android.p2pflowernet.project.view.fragments.mine.myteam.cloud_office.strongplan.diamod;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/30.
 * by--云工钻石页面
 */

public class DiamondFragment extends KFragment<IDiamondView, IDiamondPrenter> {
    @Override
    public IDiamondPrenter createPresenter() {
        return new IDiamondPrenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_cloud_diamond;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

    }

    public static Fragment newIntence() {

        DiamondFragment diamondFragment = new DiamondFragment();
        Bundle bundle = new Bundle();
        diamondFragment.setArguments(bundle);

        return diamondFragment;
    }
}

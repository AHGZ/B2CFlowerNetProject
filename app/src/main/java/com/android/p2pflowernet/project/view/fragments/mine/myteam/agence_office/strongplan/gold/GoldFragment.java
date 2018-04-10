package com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office.strongplan.gold;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/30.
 * by--代理金牌页面
 */

public class GoldFragment extends KFragment<IGoldView, IGoldPrenter> {
    @Override
    public IGoldPrenter createPresenter() {

        return new IGoldPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_agent_gold;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

    }

    public static Fragment newIntence() {

        GoldFragment goldFragment = new GoldFragment();
        Bundle bundle = new Bundle();
        goldFragment.setArguments(bundle);
        return goldFragment;
    }
}

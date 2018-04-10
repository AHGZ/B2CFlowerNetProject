package com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office.strongplan.silver;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/30.
 * by--代理银牌
 */

public class SilverFragment extends KFragment<ISilverView,ISilverPrenter>{
    @Override
    public ISilverPrenter createPresenter() {

        return new ISilverPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_agent_silver;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {




    }

    public static Fragment newIntence() {

        SilverFragment silverFragment = new SilverFragment();
        Bundle bundle = new Bundle();
        silverFragment.setArguments(bundle);
        return silverFragment;
    }
}

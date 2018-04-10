package com.android.p2pflowernet.project.view.fragments.life;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/10/16.
 * by--生活模块
 */

public class LifeFragment extends KFragment<ILifeView, ILifeViewPresenter> {
    public static LifeFragment newInstance() {

        Bundle args = new Bundle();
        LifeFragment fragment = new LifeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ILifeViewPresenter createPresenter() {
        return new ILifeViewPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.o2obase_fragment;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

    }

}

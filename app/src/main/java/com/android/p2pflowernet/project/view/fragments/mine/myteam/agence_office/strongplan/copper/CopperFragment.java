package com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office.strongplan.copper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.mvp.KFragment;

import butterknife.BindView;

/**
 * Created by caishen on 2017/11/30.
 * by--代理铜牌页面
 */

public class CopperFragment extends KFragment<ICopperView, ICopperPrenter> {
    @BindView(R.id.tv_pthy)
    TextView tvPthy;
    @BindView(R.id.tv_hhrzz)
    TextView tvHhrzz;
    @BindView(R.id.tv_jl)
    TextView tvJl;
    @BindView(R.id.tv_jpo)
    TextView tvJpo;

    @Override
    public ICopperPrenter createPresenter() {

        return new ICopperPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_agent_copper;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

    }

    public static Fragment newIntence() {

        CopperFragment copperFragment = new CopperFragment();
        Bundle bundle = new Bundle();
        copperFragment.setArguments(bundle);
        return copperFragment;
    }
}

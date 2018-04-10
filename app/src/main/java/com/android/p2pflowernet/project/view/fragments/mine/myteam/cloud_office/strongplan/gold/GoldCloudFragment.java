package com.android.p2pflowernet.project.view.fragments.mine.myteam.cloud_office.strongplan.gold;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.mvp.KFragment;

import butterknife.BindView;

/**
 * Created by caishen on 2017/11/30.
 * by--云工金牌页面
 */

public class GoldCloudFragment extends KFragment<IGoldCloudView, IGoldCloudPrenter> {
    @BindView(R.id.tv_pthy)
    TextView tvPthy;
    @BindView(R.id.tv_hhrzz)
    TextView tvHhrzz;
    @BindView(R.id.ticket_one_leftvalue)
    TextView ticketOneLeftvalue;
    @BindView(R.id.ticket_one_rightvalue)
    TextView ticketOneRightvalue;
    @BindView(R.id.ticket_one_ly)
    LinearLayout ticketOneLy;
    @BindView(R.id.ticket_two_leftvalue)
    TextView ticketTwoLeftvalue;
    @BindView(R.id.ticket_two_rightvalue)
    TextView ticketTwoRightvalue;
    @BindView(R.id.ticket_two_ly)
    LinearLayout ticketTwoLy;

    @Override
    public IGoldCloudPrenter createPresenter() {
        return new IGoldCloudPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_cloud_silver;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        tvPthy.setText("普通会员/50人");
        tvHhrzz.setText("合伙人资质/10份");
        ticketOneLeftvalue.setText("¥1000");//奖励
        ticketTwoLeftvalue.setText("¥2500");//底薪
    }

    public static Fragment newIntence() {

        GoldCloudFragment goldCloudFragment = new GoldCloudFragment();
        Bundle bundle = new Bundle();
        goldCloudFragment.setArguments(bundle);
        return goldCloudFragment;
    }
}

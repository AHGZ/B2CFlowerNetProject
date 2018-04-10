package com.android.p2pflowernet.project.view.fragments.mine.myteam.cloud_office.strongplan.copper;

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
 * by--云工铜牌
 */

public class CopperCloudFragment extends KFragment<ICopperCloudView, ICopperCloudPrenter> {
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
    public ICopperCloudPrenter createPresenter() {

        return new ICopperCloudPrenter();
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

        tvPthy.setText("普通会员/10人");
        tvHhrzz.setText("合伙人资质/2份");
        ticketOneLeftvalue.setText("¥300");//奖励
        ticketTwoLeftvalue.setText("¥1200");//底薪

    }

    public static Fragment newIntence() {

        CopperCloudFragment copperCloudFragment = new CopperCloudFragment();
        Bundle bundle = new Bundle();
        copperCloudFragment.setArguments(bundle);
        return copperCloudFragment;
    }
}

package com.android.p2pflowernet.project.view.fragments.mine.myteam.team_earnings.contribution;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.MyTeamContriQueueAdapter;
import com.android.p2pflowernet.project.entity.ContriRankBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;

import butterknife.BindView;

/**
 * Created by caishen on 2017/12/19.
 * by--贡献排行榜
 */

public class ContributionRankFragment extends KFragment<IContributionRankView, IContributionRankPrenter>
        implements IContributionRankView, NormalTopBar.normalTopClickListener {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.applyqueue_recyclerview)
    RecyclerView applyqueueRecyclerview;
    private String state;
    private MyTeamContriQueueAdapter myTeamContriQueueAdapter;
    private ShapeLoadingDialog shapeLoadingDialog;

    @Override
    public IContributionRankPrenter createPresenter() {
        return new IContributionRankPrenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        state = arguments.getString("state");
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_contributionrank;
    }

    @Override
    public void initData() {

        mPresenter.contrirank();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        Utils.setStatusBar(getActivity(), 0, false);
        normalTop.setTitleText("贡献排行榜");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        initData();
    }

    public static KFragment newIntence(String state) {
        ContributionRankFragment contributionRankFragment = new ContributionRankFragment();
        Bundle bundle = new Bundle();
        bundle.putString("state", state);
        contributionRankFragment.setArguments(bundle);
        return contributionRankFragment;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public void onError(String s) {

        showShortToast(s);
    }

    @Override
    public void showDialog() {

        shapeLoadingDialog.show();
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void successRanks(ContriRankBean data) {

        if (data != null) {

            //设置贡献排行榜的适配器
            applyqueueRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
            myTeamContriQueueAdapter = new MyTeamContriQueueAdapter();
            myTeamContriQueueAdapter.attachRecyclerView(applyqueueRecyclerview);
            myTeamContriQueueAdapter.setList(data.getList());
        }
    }

    @Override
    public void onLeftClick(View view) {

        removeFragment();
    }

    @Override
    public void onRightClick(View view) {

    }

    @Override
    public void onTitleClick(View view) {

    }
}

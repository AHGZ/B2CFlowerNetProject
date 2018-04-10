package com.android.p2pflowernet.project.view.fragments.mine.myteam;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.HomeFragmentAdapter;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office.AgenceOfficeFragment;
import com.android.p2pflowernet.project.view.fragments.mine.myteam.cloud_office.CloudOfficeFragment;
import com.android.p2pflowernet.project.view.fragments.mine.myteam.team_earnings.TeamEarningsFragment;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by caishen on 2017/11/28.
 * by--我的团队
 */

public class MyTeamFragment extends KFragment<IMyTeamView, IMyTeamPrenter>
        implements NormalTopBar.normalTopClickListener {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.tab_hometitle)
    TabLayout tabHometitle;
    @BindView(R.id.vp_homepager)
    ViewPager vpHomepager;

    private ArrayList<Fragment> list_fragment;
    private ArrayList<String> list_title;

    @Override
    public IMyTeamPrenter createPresenter() {

        return new IMyTeamPrenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_myteam;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setTitleText("我的团队");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        Utils.setStatusBar(getActivity(), 0, false);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);


        //将fragment装进列表中
        list_fragment = new ArrayList<>();
        list_fragment.add(TeamEarningsFragment.newIntence());
        list_fragment.add(CloudOfficeFragment.newIntence());
        list_fragment.add(AgenceOfficeFragment.newIntence());

        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<>();
        list_title.add("团队收益");
        list_title.add("云工办公");
        list_title.add("代理办公");

        //设置TabLayout的模式
        tabHometitle.setTabMode(TabLayout.MODE_FIXED);
        vpHomepager.setOffscreenPageLimit(0);

        //为TabLayout添加tab名称
        tabHometitle.addTab(tabHometitle.newTab().setText(list_title.get(0)));
        tabHometitle.addTab(tabHometitle.newTab().setText(list_title.get(1)));
        tabHometitle.addTab(tabHometitle.newTab().setText(list_title.get(2)));

        HomeFragmentAdapter fAdapter = new HomeFragmentAdapter(getActivity().getSupportFragmentManager(),
                list_fragment, list_title);

        //viewpager加载adapter
        vpHomepager.setAdapter(fAdapter);

        tabHometitle.setupWithViewPager(vpHomepager);

        LinearLayout linearLayout = (LinearLayout) tabHometitle.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.tablayout_center_line));
        linearLayout.setDividerPadding(6);

        //默认选中第二个页面
        tabHometitle.getTabAt(0).select();
    }

    public static KFragment newIntence() {

        MyTeamFragment myTeamFragment = new MyTeamFragment();
        Bundle bundle = new Bundle();
        myTeamFragment.setArguments(bundle);

        return myTeamFragment;
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

    /***
     * 友盟统计
     */
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MyTeamPage"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MyTeamPage");
    }
}

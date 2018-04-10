package com.android.p2pflowernet.project.view.fragments.mine.setting.feedback;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.HomeFragmentAdapter;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by caishen on 2017/11/24.
 * by--意见反馈
 */

public class FeedBackFragment extends KFragment<IFeedBackView, IFeedBackPrenter> implements
        NormalTopBar.normalTopClickListener {
    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.tab_hometitle)
    TabLayout tabHometitle;
    @BindView(R.id.vp_homepager)
    ViewPager vpHomepager;
    private ArrayList<Fragment> list_fragment;
    private ArrayList<String> list_title;

    @Override
    public IFeedBackPrenter createPresenter() {

        return new IFeedBackPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_feed_back;
    }

    @Override
    public void initData() {

        //将fragment装进列表中
        list_fragment = new ArrayList<>();
        list_fragment.add(FeedBacksFragment.newIntence());
        list_fragment.add(FeedBacksHistoryFragment.newIntence());

        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<>();
        list_title.add("意见反馈");
        list_title.add("反馈历史");

        //设置TabLayout的模式
        tabHometitle.setTabMode(TabLayout.MODE_FIXED);

        //为TabLayout添加tab名称
        tabHometitle.addTab(tabHometitle.newTab().setText(list_title.get(0)));
        tabHometitle.addTab(tabHometitle.newTab().setText(list_title.get(1)));

        HomeFragmentAdapter fAdapter = new HomeFragmentAdapter(getActivity().getSupportFragmentManager(),
                list_fragment, list_title);

        //viewpager加载adapter
        vpHomepager.setAdapter(fAdapter);

        tabHometitle.setupWithViewPager(vpHomepager);

        //默认选中第二个页面
        tabHometitle.getTabAt(0).select();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setTitleText("意见反馈");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        Utils.setStatusBar(getActivity(), 0, false);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);
        initData();
    }

    public static KFragment newIntence() {
        FeedBackFragment feedBackFragment = new FeedBackFragment();
        Bundle bundle = new Bundle();
        feedBackFragment.setArguments(bundle);
        return feedBackFragment;
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

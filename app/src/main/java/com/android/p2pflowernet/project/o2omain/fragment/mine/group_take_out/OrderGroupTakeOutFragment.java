package com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out;

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
 * Created by caishen on 2018/1/5.
 * by--团购订单
 */

public class OrderGroupTakeOutFragment extends KFragment
        <IOrderGroupTakeOutView, IOrderGroupTakeOutPrenter> implements NormalTopBar.normalTopClickListener {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.tabLayout)
    TabLayout tabHometitle;
    @BindView(R.id.vp_homepager)
    ViewPager vpHomepager;
    private ArrayList<Fragment> list_fragment;
    private ArrayList<String> list_title;

    @Override
    public IOrderGroupTakeOutPrenter createPresenter() {
        return new IOrderGroupTakeOutPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_order_take_out_group;
    }

    @Override
    public void initData() {


    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸栏
        Utils.setStatusBar(getActivity(), 3, false);
        normalTop.setTitleText("团购订单");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.color.o2o_red);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);

        //将fragment装进列表中
        list_fragment = new ArrayList<>();
        list_fragment.add(TakeOutOrderGroupFragment.newIntence(1));
        list_fragment.add(TakeOutOrderGroupFragment.newIntence(0));
        list_fragment.add(TakeOutOrderGroupFragment.newIntence(2));
        list_fragment.add(TakeOutOrderGroupFragment.newIntence(3));
        list_fragment.add(TakeOutOrderGroupFragment.newIntence(4));

        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<>();
        list_title.add("全部");
        list_title.add("待付款");
        list_title.add("待使用");
        list_title.add("已使用");
        list_title.add("已退款");

        //设置TabLayout的模式
        tabHometitle.setTabMode(TabLayout.MODE_FIXED);

        //为TabLayout添加tab名称
        tabHometitle.addTab(tabHometitle.newTab().setText(list_title.get(0)));
        tabHometitle.addTab(tabHometitle.newTab().setText(list_title.get(1)));
        tabHometitle.addTab(tabHometitle.newTab().setText(list_title.get(2)));
        tabHometitle.addTab(tabHometitle.newTab().setText(list_title.get(3)));
        tabHometitle.addTab(tabHometitle.newTab().setText(list_title.get(4)));

        HomeFragmentAdapter fAdapter = new HomeFragmentAdapter(getActivity().getSupportFragmentManager(),
                list_fragment, list_title);

        //viewpager加载adapter
        vpHomepager.setAdapter(fAdapter);

        tabHometitle.setupWithViewPager(vpHomepager);

        //默认选中第二个页面
        tabHometitle.getTabAt(0).select();
    }


    public static KFragment newIntence() {

        OrderGroupTakeOutFragment orderGroupTakeOutFragment = new OrderGroupTakeOutFragment();
        Bundle bundle = new Bundle();
        orderGroupTakeOutFragment.setArguments(bundle);
        return orderGroupTakeOutFragment;
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

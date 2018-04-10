package com.android.p2pflowernet.project.o2omain.fragment.mine.take_out;

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
import com.android.p2pflowernet.project.o2omain.fragment.mine.take_out.all_order.TakeOutOrderFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by caishen on 2018/1/5.
 * by--外卖订单
 */

public class OrderTakeOutFragment extends KFragment<IOrderTakeOutView, IOrderTakeOutPrenter>
        implements NormalTopBar.normalTopClickListener{
    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.tabLayout)
    TabLayout tabHometitle;
    @BindView(R.id.vp_homepager)
    ViewPager vpHomepager;
    private ArrayList<Fragment> list_fragment;
    private ArrayList<String> list_title;

    @Override
    public IOrderTakeOutPrenter createPresenter() {
        return new IOrderTakeOutPrenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_order_take_out;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸栏
        Utils.setStatusBar(getActivity(), 3, false);
        normalTop.setTitleText("外卖订单");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.color.o2o_red);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);

        //将fragment装进列表中
        list_fragment = new ArrayList<>();
        list_fragment.add(TakeOutOrderFragment.newIntence(1));//全部
        list_fragment.add(TakeOutOrderFragment.newIntence(2));//待付款
        list_fragment.add(TakeOutOrderFragment.newIntence(3));//待使用
        list_fragment.add(TakeOutOrderFragment.newIntence(4));//待评价
        list_fragment.add(TakeOutOrderFragment.newIntence(5));//退款

        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<>();
        list_title.add("全部");
        list_title.add("待付款");
        list_title.add("待收货");
        list_title.add("待评价");
        list_title.add("退款申请");

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

        OrderTakeOutFragment orderTakeOutFragment = new OrderTakeOutFragment();
        Bundle bundle = new Bundle();
        orderTakeOutFragment.setArguments(bundle);
        return orderTakeOutFragment;
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

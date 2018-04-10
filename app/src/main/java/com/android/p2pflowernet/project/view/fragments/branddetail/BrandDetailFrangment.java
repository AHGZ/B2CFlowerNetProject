package com.android.p2pflowernet.project.view.fragments.branddetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.HomeFragmentAdapter;
import com.android.p2pflowernet.project.mvp.KFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by caishen on 2017/10/19.
 * by--品牌详情页面
 */

public class BrandDetailFrangment extends KFragment<IBrandDetailView, IBrandDetailPrenter> {


    @BindView(R.id.toolbar)
    LinearLayout toolbar;
    @BindView(R.id.tab)
    TabLayout mTabLayout;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.viewpager)
    ViewPager mVpBody;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    Unbinder unbinder;
    private ArrayList<String> titles;
    private ArrayList<Fragment> fragmentsList;

    @Override
    public IBrandDetailPrenter createPresenter() {
        return new IBrandDetailPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_brand_detail;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        titles = new ArrayList<>();
        titles.add("首页");
        titles.add("商品");
        titles.add("最新");

        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));

        fragmentsList = new ArrayList<Fragment>();
        Bundle bundle = new Bundle();
        Fragment exchangeFragment = BrandHomeFragment.newInstance();
        Fragment activityFragment = BrandsFragment.newInstance();
        Fragment meFragment = BrandNewFragment.newInstance();


        //设置TabLayout的模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        fragmentsList.add(exchangeFragment);
        fragmentsList.add(activityFragment);
        fragmentsList.add(meFragment);
        mVpBody.setAdapter(new HomeFragmentAdapter(getActivity().getSupportFragmentManager(), fragmentsList, titles));
        mVpBody.setCurrentItem(0);
        mTabLayout.setupWithViewPager(mVpBody);

        //默认选中第二个页面
        mTabLayout.getTabAt(0).select();
        mTabLayout.setupWithViewPager(mVpBody);
    }

    public static KFragment newInstance() {
        return new BrandDetailFrangment();
    }


//    @OnClick({R.id.back, R.id.share})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.back://返回
//
//                removeFragment();
//
//                break;
//            case R.id.share://分享
//
//
//                break;
//        }
//    }

}

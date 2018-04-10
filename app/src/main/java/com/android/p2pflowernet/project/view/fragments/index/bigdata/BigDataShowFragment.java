package com.android.p2pflowernet.project.view.fragments.index.bigdata;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.BigdataPagerAdapter;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.fragments.platformdata.awarddata.AwardDataFragment;
import com.android.p2pflowernet.project.view.fragments.platformdata.rebatedata.RebateDataFragment;
import com.android.p2pflowernet.project.view.fragments.platformdata.regiondata.RegionRoleDataFragment;
import com.android.p2pflowernet.project.view.fragments.platformdata.roleformdata.RoleFormDataFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2018/1/31.
 * by--大数据首页展示
 */

public class BigDataShowFragment extends KFragment<IBigDataShowView, IBigDataShowPrenter>
        implements NormalTopBar.normalTopClickListener {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.iv_last)
    ImageView ivLast;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    private ArrayList<Fragment> list_fragment;
    private int index = 0;

    @Override
    public IBigDataShowPrenter createPresenter() {
        return new IBigDataShowPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_bigdata;
    }

    @Override
    public void initData() {

        //将fragment装进列表中
        list_fragment = new ArrayList<>();
        list_fragment.add(NationwideFragment.newIntence());
        list_fragment.add(RebateDataFragment.newInstance());
        list_fragment.add(RoleFormDataFragment.newInstance());
        list_fragment.add(RegionRoleDataFragment.newInstance());
        list_fragment.add(AwardDataFragment.newInstance());

        //设置适配器
        BigdataPagerAdapter fAdapter = new
                BigdataPagerAdapter(getActivity().getSupportFragmentManager(), list_fragment);

        //viewpager加载adapter
        viewPager.setAdapter(fAdapter);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //当前选中的页面
                if (position == 0) {
                    ivLast.setVisibility(View.GONE);
                } else {
                    ivLast.setVisibility(View.VISIBLE);
                }

                if (position == list_fragment.size() - 1) {
                    ivNext.setVisibility(View.GONE);
                } else {
                    ivNext.setVisibility(View.VISIBLE);
                }

                switch (position) {
                    case 0:
                        normalTop.setTitleText("花返网全国发展展示");
                        break;
                    case 1:
                        normalTop.setTitleText("花返网返润数据");
                        break;
                    case 2:
                        normalTop.setTitleText("花返网角色组成数据");
                        break;
                    case 3:
                        normalTop.setTitleText("花返网地区角色组成数据");
                        break;
                    case 4:
                        normalTop.setTitleText("花返网业务奖励发放数据");
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {
                //:当前页面，及你点击滑动的页面
                index = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setTitleText("花返网大数据展示");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.getLeftImage().setImageResource(R.mipmap.icon_back_white);
        normalTop.setTopClickListener(this);
        normalTop.setBackgroundColor(getResources().getColor(R.color.drop_down_unselected));
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.drop_down_unselected));
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);

        initData();
    }

    public static KFragment newIntence() {

        BigDataShowFragment bigDataShowFragment = new BigDataShowFragment();
        Bundle bundle = new Bundle();
        bigDataShowFragment.setArguments(bundle);
        return bigDataShowFragment;
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

    @OnClick({R.id.iv_last, R.id.iv_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_last://上一个

                viewPager.setCurrentItem(index - 1);
                break;
            case R.id.iv_next://下一个

                viewPager.setCurrentItem(index + 1);

                break;
        }
    }
}

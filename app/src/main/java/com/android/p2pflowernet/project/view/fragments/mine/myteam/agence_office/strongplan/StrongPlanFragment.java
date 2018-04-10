package com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office.strongplan;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.AgentGrowBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office.strongplan.copper.CopperFragment;
import com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office.strongplan.gold.GoldFragment;
import com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office.strongplan.silver.SilverFragment;

import butterknife.BindView;

/**
 * Created by caishen on 2017/11/30.
 * by--代理成长计划
 */

public class StrongPlanFragment extends KFragment<IStrongPlanView, IStrongPlanPrenter>
        implements NormalTopBar.normalTopClickListener, IStrongPlanView {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private TabLayout.Tab copper;
    private TabLayout.Tab silver;
    private TabLayout.Tab gold;
    private ShapeLoadingDialog shapeLoadingDialog;

    @Override
    public IStrongPlanPrenter createPresenter() {

        return new IStrongPlanPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_strong_plan_agent;
    }

    @Override
    public void initData() {

        mPresenter.getagentGrowPlan();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setTitleText("成长计划");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        Utils.setStatusBar(getActivity(), 0, false);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        //初始化tablayout加载页面
        initTab();

        initData();
    }

    //初始化tablayout加载页面
    private void initTab() {

        viewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {

            private String[] mTitles = new String[]{"铜牌", "银牌", "金牌"};

            @Override
            public Fragment getItem(int position) {

                if (position == 1) {

                    return SilverFragment.newIntence();

                } else if (position == 2) {

                    return GoldFragment.newIntence();
                }

                return CopperFragment.newIntence();
            }

            @Override
            public int getCount() {

                return mTitles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });

        tabLayout.setupWithViewPager(viewPager);

        //设置图标
        copper = tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.icon_tp));
        silver = tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.icon_yp));
        gold = tabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.icon_jp));
    }

    public static KFragment newIntence() {

        StrongPlanFragment strongPlanFragment = new StrongPlanFragment();
        Bundle bundle = new Bundle();
        strongPlanFragment.setArguments(bundle);
        return strongPlanFragment;
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
    public void SuccessAgent(AgentGrowBean data) {

        if (data != null) {

            //设置数值
            tvNum.setText("已有" + data.getGold() + "位");
        }
    }
}

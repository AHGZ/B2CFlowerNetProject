package com.android.p2pflowernet.project.view.fragments.mine.myteam.cloud_office.strongplan;

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
import com.android.p2pflowernet.project.entity.CloudGrowBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.mine.myteam.cloud_office.strongplan.copper.CopperCloudFragment;
import com.android.p2pflowernet.project.view.fragments.mine.myteam.cloud_office.strongplan.diamod.DiamondFragment;
import com.android.p2pflowernet.project.view.fragments.mine.myteam.cloud_office.strongplan.gold.GoldCloudFragment;
import com.android.p2pflowernet.project.view.fragments.mine.myteam.cloud_office.strongplan.silver.SilverCloudFragment;

import butterknife.BindView;

/**
 * Created by caishen on 2017/11/30.
 * by--云工成长计划
 */

public class StrongPlanCloudFragment extends KFragment<IStrongPlanCloudView, IStrongPlanCloudPrenter>
        implements NormalTopBar.normalTopClickListener, IStrongPlanCloudView {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private ShapeLoadingDialog shapeLoadingDialog;

    @Override
    public IStrongPlanCloudPrenter createPresenter() {

        return new IStrongPlanCloudPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_strong_plan_cloud;
    }

    @Override
    public void initData() {

        mPresenter.getCloudGrow();
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

    public static KFragment newIntence() {

        StrongPlanCloudFragment strongPlanCloudFragment = new StrongPlanCloudFragment();
        Bundle bundle = new Bundle();
        strongPlanCloudFragment.setArguments(bundle);

        return strongPlanCloudFragment;
    }

    //初始化tablayout加载页面
    private void initTab() {

        viewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {

            private String[] mTitles = new String[]{"铜牌", "银牌", "金牌", "钻石"};

            @Override
            public Fragment getItem(int position) {

                if (position == 1) {

                    return SilverCloudFragment.newIntence();

                } else if (position == 2) {

                    return GoldCloudFragment.newIntence();

                } else if (position == 3) {

                    return DiamondFragment.newIntence();
                }

                return CopperCloudFragment.newIntence();
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
        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.icon_tp));
        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.icon_yp));
        tabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.icon_jp));
        tabLayout.getTabAt(3).setIcon(getResources().getDrawable(R.drawable.icon_zs));
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
    public void SuccessCloud(CloudGrowBean data) {

        if (data != null) {

            tvNum.setText("已有" + data.getDiamonds() + "位");
        }
    }
}

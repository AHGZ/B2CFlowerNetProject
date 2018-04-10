package com.android.p2pflowernet.project.view.fragments.mine.applyfor;

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
import com.android.p2pflowernet.project.event.ApplyforEventTralate;
import com.android.p2pflowernet.project.event.UpdateUserInfoEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.applyforhistory.AppForHistoryFragment;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.applyforwait.ApplyForWaitFragment;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.stake.StakeDetailFragment;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by caishen on 2017/11/11.
 * by--申请更多身份的页面
 */

public class ApplyForFragment extends KFragment<IApplyForView, IApplyPrenter> implements NormalTopBar.normalTopClickListener {


    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.tab_hometitle)
    TabLayout tabHometitle;
    @BindView(R.id.vp_homepager)
    ViewPager vpHomepager;
    private ApplyForWaitFragment applyForWaitFragment;
    private AppForHistoryFragment appForHistoryFragment;
    private ArrayList<Fragment> list_fragment;
    private ArrayList<String> list_title;

    @Override
    public IApplyPrenter createPresenter() {
        return new IApplyPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_applyfor;
    }

    @Override
    public void initData() {


    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setTitleText("更多申请");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        Utils.setStatusBar(getActivity(), 0, false);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);

        //将fragment装进列表中
        list_fragment = new ArrayList<>();
        list_fragment.add(ApplyForWaitFragment.newIntence());
        list_fragment.add(AppForHistoryFragment.newIntence());
        list_fragment.add(StakeDetailFragment.newIntence());

        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<>();
        list_title.add("待申请");
        list_title.add("申请历史");
        list_title.add("合伙明细");

        //设置TabLayout的模式
        tabHometitle.setTabMode(TabLayout.MODE_FIXED);

        //为TabLayout添加tab名称
        tabHometitle.addTab(tabHometitle.newTab().setText(list_title.get(0)));
        tabHometitle.addTab(tabHometitle.newTab().setText(list_title.get(1)));
        tabHometitle.addTab(tabHometitle.newTab().setText(list_title.get(2)));

        HomeFragmentAdapter fAdapter = new HomeFragmentAdapter(getActivity().getSupportFragmentManager(),
                list_fragment, list_title);

        //添加分割线
        LinearLayout linearLayout = (LinearLayout) tabHometitle.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.tablayout_center_line));
        linearLayout.setDividerPadding(6);

        //viewpager加载adapter
        vpHomepager.setAdapter(fAdapter);

        tabHometitle.setupWithViewPager(vpHomepager);

        //默认选中第二个页面
        tabHometitle.getTabAt(0).select();
    }

    /**
     * 接收平移消息
     *
     * @param str
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ApplyforEventTralate str) {

        int str1 = str.getStr();
        if (str1 != -1) {
            tabHometitle.getTabAt(str1).select();
        }
    }

    public static KFragment newIntence() {

        Bundle bundle = new Bundle();
        ApplyForFragment applyForFragment = new ApplyForFragment();
        applyForFragment.setArguments(bundle);
        return applyForFragment;
    }

    @Override
    public void onLeftClick(View view) {

        //发送刷新我的页面数据
        EventBus.getDefault().post(new UpdateUserInfoEvent());
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
        MobclickAgent.onPageStart("applyForPage"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("applyForPage");
    }
}

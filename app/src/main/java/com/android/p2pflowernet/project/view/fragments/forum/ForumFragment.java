package com.android.p2pflowernet.project.view.fragments.forum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.ForumAdapter;
import com.android.p2pflowernet.project.entity.ForumChannelBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.forum.member.ForumMemberFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/18.
 */

public class ForumFragment extends KFragment<IForumView,IForumPresenter> implements IForumView,NormalTopBar.normalTopClickListener{

    @BindView(R.id.normal_top)
    NormalTopBar topBar;

    @BindView(R.id.fragment_forum_tab)
    TabLayout mTabLayout;

    @BindView(R.id.fragment_forum_viewPager)
    ViewPager mViewPager;

    private List<Fragment> list;
    private List<String> list_title;
    private ShapeLoadingDialog shapeLoadingDialog;
    private List<ForumChannelBean.ChannelBean> channelBeans;

    public static ForumFragment newInstance(){
        return new ForumFragment();
    }

    @Override
    public IForumPresenter createPresenter() {
        return new IForumPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_forum;
    }

    @Override
    public void initData() {
        mPresenter.getForumChannel();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        topBar.setBackgroundColor(getResources().getColor(R.color.forum_titleBar_bg));
        topBar.setTitleText("花返讲堂");
        topBar.setLeftImageId(R.mipmap.icon_back_white);
        topBar.setTitleTextColor(getResources().getColor(R.color.white));
        topBar.setTopClickListener(this);
        //初始化沉浸式
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.forum_titleBar_bg));

        //初始化加载窗口
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(2000)
                .build();

        channelBeans = new ArrayList<>();
        list = new ArrayList<>();
        list_title = new ArrayList<>();

        initData();
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
    public void onError(String string) {
        showShortToast(string);
    }

    @Override
    public void onSuccess(ForumChannelBean data) {
        if (null != data) {
            channelBeans.addAll(data.getChannel());
            for (int i = 0; i < channelBeans.size(); i++) {
                ForumChannelBean.ChannelBean bean = channelBeans.get(i);
                list_title.add(bean.getTitle());
                list.add(ForumMemberFragment.newIntence(String.valueOf(bean.getId())));
            }

            //给viewpager设置适配器
            ForumAdapter adapter = new ForumAdapter(getActivity().getSupportFragmentManager(),list,list_title);
            mViewPager.setAdapter(adapter);
            //设置tabLayout与viewPager关联
            mTabLayout.setupWithViewPager(mViewPager);
            //默认选中第一个页面
            mTabLayout.getTabAt(0).select();
        }
    }

    @Override
    public void showDialog() {
        shapeLoadingDialog.show();
    }

    @Override
    public void hideDialog() {
        if (shapeLoadingDialog.isShowing()) {
            shapeLoadingDialog.dismiss();
        }
    }
}

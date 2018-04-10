package com.android.p2pflowernet.project.o2omain.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.event.MainEvent;
import com.android.p2pflowernet.project.event.OutO2oIndexHomeEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.fragment.index.O2oIndexFragment;
import com.android.p2pflowernet.project.o2omain.fragment.mine.O2oMineFragment;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.bottombar.BottomBarItem;
import com.android.p2pflowernet.project.view.bottombar.BottomBarLayout;
import com.android.p2pflowernet.project.view.customview.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * author: zhangpeisen
 * created on: 2017/10/10 上午11:25
 * description: O2o花返网 主类
 */
public class O2oMainFragment extends KFragment<O2oMainView, IO2oMainPrenter> {
    @BindView(R.id.vp_content)
    NoScrollViewPager mVpContent;
    @BindView(R.id.bbl)
    BottomBarLayout mBottomBarLayout;

    private List<KFragment> mFragmentList = new ArrayList<>();
    private String searchName = "";

    public static O2oMainFragment newInstance(String searchName) {
        Bundle args = new Bundle();
        args.putString("searchName", searchName);
        O2oMainFragment fragment = new O2oMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchName = getArguments().getString("searchName");
    }


    @Override
    public IO2oMainPrenter createPresenter() {
        return new IO2oMainPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.o2o_activity_main;
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        initData();
        initListener();
    }

    public void initData() {

        mFragmentList.add(O2oIndexFragment.newInstance(searchName));//首页
        mFragmentList.add(O2oIndexFragment.newInstance(""));//商城
        mFragmentList.add(O2oMineFragment.newInstance());//我的
        mVpContent.setAdapter(new MyAdapter(getChildFragmentManager()));
        mBottomBarLayout.setViewPager(mVpContent);

        //设置商城的点击事件
        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int position) {
                //  O2O商城
                if (position == 1) {
                    removeFragment();
                    //发送消息跳转到b2c的首页
                    EventBus.getDefault().post(new MainEvent(0));
                } else if (position == 0) {
                    Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.coloro2o));
                } else if (position == 2) {
                    Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.coloro2o));
                }
            }
        });
    }

    /***
     * 接收结束当前页面的广播
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OutO2oIndexHomeEvent event) {

        removeFragment();
    }


    public void initListener() {
//        mBottomBarLayout.setUnread(0, 20);//设置第一个页签的未读数为20
//        mBottomBarLayout.setUnread(1, 101);//设置第二个页签的未读书
//        mBottomBarLayout.showNotify(0);//设置第三个页签显示提示的小红点0
//        mBottomBarLayout.showNotify(1);//设置第三个页签显示提示的小红点0
//        mBottomBarLayout.showNotify(3);//设置第三个页签显示提示的小红点0
//        mBottomBarLayout.showNotify(4);//设置第三个页签显示提示的小红点0
//        mBottomBarLayout.setMsg(3, "NEW");//设置第四个页签显示NEW提示文字

    }


    class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
        }
    }


}

package com.android.p2pflowernet.project.view.fragments.mine.orderdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.event.OrderDeatailRefreshEvent;
import com.android.p2pflowernet.project.event.UpdateUserInfoEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.FastSrcollViewpagerView;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.pendingship.PengingShipFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @描述:订单详情页面Fragment
 * @创建人：zhangpeisen
 * @创建时间：2017/10/30 下午4:55
 * @修改人：zhangpeisen
 * @修改时间：2017/10/30 下午4:55
 * @修改备注：
 * @throws
 */
public class OrderDetailFragment extends KFragment<IOrderDetailView, IOrderDetailPresenter>
        implements NormalTopBar.normalTopClickListener {
    @BindView(R.id.normal_top)
    //自定义通用标题
            NormalTopBar normalTopBar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    FastSrcollViewpagerView viewPager;
    int CurrentTabItem = -1;
    private List<KFragment> mFragmentList = new ArrayList<>();

    public static OrderDetailFragment newInstance(int index) {
        Bundle args = new Bundle();
        OrderDetailFragment fragment = new OrderDetailFragment();
        args.putInt("index", index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CurrentTabItem = getArguments().getInt("index");
    }

    @Override
    public IOrderDetailPresenter createPresenter() {
        return new IOrderDetailPresenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.mine_orderlistdetail_fragment;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        //初始化沉浸式
        Utils.setStatusBar(getActivity(), 0, false);
        // 扩大事件的点击范围
        UIUtils.setTouchDelegate(normalTopBar.getLeftImage(), 60);
        UIUtils.setTouchDelegate(normalTopBar.getRightImage(), 60);
        normalTopBar.getRightImage().setVisibility(View.GONE);
        normalTopBar.setTopClickListener(this);
        //type代表页签，1：全部订单 2：待付款 3：待发货 4：待收货 5：已完成
        mFragmentList.add(PengingShipFragment.newInstance(1));
        mFragmentList.add(PengingShipFragment.newInstance(2));
        mFragmentList.add(PengingShipFragment.newInstance(3));
        mFragmentList.add(PengingShipFragment.newInstance(4));
        mFragmentList.add(PengingShipFragment.newInstance(5));

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        //关联ViewPager
        tabLayout.setupWithViewPager(viewPager);
        if (CurrentTabItem == -1) {
            return;
        }
        switch (CurrentTabItem) {
            case 1:

                // 全部订单
                tabLayout.getTabAt(0).select();
                viewPager.setCurrentItem(0);
                normalTopBar.setTitleText("全部订单");

                break;
            case 2:
                // 待付款
                tabLayout.getTabAt(1).select();
                viewPager.setCurrentItem(1);
                normalTopBar.setTitleText("待付款");
                break;
            case 3:
                // 待发货
                tabLayout.getTabAt(2).select();
                viewPager.setCurrentItem(2);
                normalTopBar.setTitleText("待发货");
                break;
            case 4:
                // 待收货
                tabLayout.getTabAt(3).select();
                viewPager.setCurrentItem(3);
                normalTopBar.setTitleText("待收货");
                break;
            case 5:
                // 已完成
                tabLayout.getTabAt(4).select();
                viewPager.setCurrentItem(4);
                normalTopBar.setTitleText("已完成");
                break;
        }
        //设置滑动的。
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //设置ViewPager联动
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        normalTopBar.setTitleText("全部订单");
                        EventBus.getDefault().post(new OrderDeatailRefreshEvent());
                        break;
                    case 1:
                        normalTopBar.setTitleText("待付款");
                        EventBus.getDefault().post(new OrderDeatailRefreshEvent());
                        break;
                    case 2:
                        normalTopBar.setTitleText("待发货");
                        EventBus.getDefault().post(new OrderDeatailRefreshEvent());
                        break;
                    case 3:
                        normalTopBar.setTitleText("待收货");
                        EventBus.getDefault().post(new OrderDeatailRefreshEvent());
                        break;
                    case 4:
                        normalTopBar.setTitleText("已完成");
                        EventBus.getDefault().post(new OrderDeatailRefreshEvent());
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void onLeftClick(View view) {
        EventBus.getDefault().post(new UpdateUserInfoEvent());
        removeFragment();
    }

    @Override
    public void onRightClick(View view) {

    }

    @Override
    public void onTitleClick(View view) {

    }


    class MyAdapter extends FragmentStatePagerAdapter {
        List<String> names = new ArrayList<>();

        public MyAdapter(FragmentManager fm) {
            super(fm);
            names.add("全部订单");
            names.add("待付款");
            names.add("待发货");
            names.add("待收货");
            names.add("已完成");
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
        public CharSequence getPageTitle(int position) {
            return names.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
        }
    }
}

package com.android.p2pflowernet.project.view.fragments.branddetail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.BrandHomeadApter;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.activity.GoodsDetailActivity;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import butterknife.BindView;

/**
 * Created by caishen on 2017/10/19.
 * by--品牌的首页
 */

public class BrandHomeFragment extends KFragment<IBrandHomeView, IBrandHomePrenter> {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.pull_refresh)
    PullToRefreshLayout pullRefresh;


    @Override
    public IBrandHomePrenter createPresenter() {
        return new IBrandHomePrenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_brand_home;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //设置上拉刷新下拉加载更多
        pullRefresh.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 结束刷新
                        pullRefresh.finishRefresh();
                    }
                }, 0);
            }

            @Override
            public void loadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        // 结束加载更多
                        pullRefresh.finishLoadMore();
                    }
                }, 0);
            }
        });

        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        BrandHomeadApter brandHomeadApter = new BrandHomeadApter(getActivity());

        //设置点击事件
        brandHomeadApter.setOnRecyclerViewListener(new BrandHomeadApter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        //设置适配器
        recyclerview.setAdapter(brandHomeadApter);
    }

    public static Fragment newInstance() {

        return new BrandHomeFragment();
    }

}

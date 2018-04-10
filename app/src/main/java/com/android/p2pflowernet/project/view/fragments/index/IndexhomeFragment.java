package com.android.p2pflowernet.project.view.fragments.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.IndexHomeBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.activity.PublicWebActivity;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.index.bigdata.BigDataShowActivity;
import com.android.p2pflowernet.project.view.fragments.mine.message.MessageActivity;
import com.android.p2pflowernet.project.view.fragments.search.SearchActivity;
import com.android.p2pflowernet.project.zxing.activity.CaptureActivity;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by caishen on 2017/10/21.
 * by--首页数据
 */

public class IndexhomeFragment extends KFragment<IIndexHomeView, IIndexHomePresenter>
        implements IIndexHomeView {

    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.refreshLayout)
    PullToRefreshLayout refreshLayout;
    @BindView(R.id.scan_tv)
    TextView scanTv;
    @BindView(R.id.scan_linear)
    LinearLayout scanLinear;
    @BindView(R.id.tosearch_framelayout)
    FrameLayout tosearch_framelayout;
    @BindView(R.id.message_linear)
    LinearLayout messageLinear;
    @BindView(R.id.toolbar)
    LinearLayout toolbar;
    @BindView(R.id.layout)
    ImageView layout;
    private IndexAdapter mOneAdapter;
    private List<String> mItemList = new ArrayList<>();
    private ShapeLoadingDialog shapeLoadingDialog;

    public static IndexhomeFragment newInstance(String content) {
        Bundle args = new Bundle();
        IndexhomeFragment fragment = new IndexhomeFragment();
        args.putString("content", content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public IIndexHomePresenter createPresenter() {
        return new IIndexHomePresenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.index_fagment;
    }

    @Override
    public void initData() {

        mPresenter.getHomeData();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸式
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.colorstart));
        for (int i = 1; i <= 20; i++) {
            mItemList.add("item" + i);
        }

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity()).loadText("加载中...")
                .delay(5000)
                .build();

        //大数据展示页面
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BigDataShowActivity.class);
                startActivity(intent);
            }
        });

        //初始化数据
        initData();

        initClick();
    }

    private void initClick() {

        refreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {

                mPresenter.getHomeData();
                // 结束刷新
                refreshLayout.finishRefresh();
            }

            @Override
            public void loadMore() {

                // 结束加载更多
                refreshLayout.finishLoadMore();
            }
        });
    }


    @OnClick({R.id.scan_linear, R.id.message_linear, R.id.tosearch_framelayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scan_linear:
                //扫一扫
                startActivity(new Intent(getActivity(), CaptureActivity.class));
                break;
            case R.id.message_linear:

                //消息模块
                startActivity(new Intent(getActivity(), MessageActivity.class));

                break;
            case R.id.tosearch_framelayout:

                //搜索模块
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("tag", "0");//0-b2c 1-o2o
                startActivity(intent);

                break;
        }
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
    public void onSuccessIndex(IndexHomeBean data) {

        if (data != null) {

            //设置布局管理
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false);
            mRv.setLayoutManager(linearLayoutManager);
            UIUtils.setTouchDelegate(messageLinear, 50);
            mOneAdapter = new IndexAdapter(getActivity(), data);
            mRv.setAdapter(mOneAdapter);

            //设置轮播图的点击事件
            mOneAdapter.setBannerListener(new IndexAdapter.BannerOnclickListener() {
                @Override
                public void bannerOnclickListener(View view, int position, String url) {

                    Intent intent = new Intent(getActivity(), PublicWebActivity.class);
                    intent.putExtra("publicurl", url);
                    intent.putExtra("tag", "1");//返回时 0-由广告页进入首页 1不进入首页
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onSuccess(String message) {

        showShortToast(message);
    }

    /***
     * 友盟统计
     */
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MainPage"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainPage");
    }
}

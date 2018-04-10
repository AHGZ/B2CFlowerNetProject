package com.android.p2pflowernet.project.view.fragments.mine.waitevaluated;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.WaitEvaluatedAdapter;
import com.android.p2pflowernet.project.entity.WaitEvaluatedBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.evaluate.EvaluateActivity;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.List;

import butterknife.BindView;

/**
 * Created by caishen on 2017/12/14.
 * by--待评价
 */

public class WaitEvaluatedFragment extends KFragment<IWaitEvaluateView, IWaitEvaluatePrenter>
        implements NormalTopBar.normalTopClickListener, IWaitEvaluateView {
    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.pull_refresh)
    PullToRefreshLayout pullRefresh;
    @BindView(R.id.layout_empty)
    LinearLayout layout_empty;
    @BindView(R.id.tv_empty)
    TextView tv_empty;
    private ShapeLoadingDialog shapeLoadingDialog;
    private List<WaitEvaluatedBean.ListsBean> datas;
    private boolean isLoad = false;
    private WaitEvaluatedAdapter mAdapter;
    private int page = 1;

    @Override
    public IWaitEvaluatePrenter createPresenter() {

        return new IWaitEvaluatePrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_evualuate_wait;
    }

    @Override
    public void initData() {

        mPresenter.getWaitComment();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(2000)
                .build();

        //初始化状态栏渐变色
        Utils.setStatusBar(getActivity(), 0, false);
        //扩大可点击事件
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setBackground(getResources().getDrawable(R.drawable.app_statusbar_bg));
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setTitleText("待评价");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setTopClickListener(this);

//        initData();

        //初始化刷新事件
        initRefresh();
    }

    private void initRefresh() {

        pullRefresh.setRefreshListener(new BaseRefreshListener() {

            @Override
            public void refresh() {

                page = 1;
                mPresenter.getWaitComment();
                pullRefresh.finishRefresh();
            }

            @Override
            public void loadMore() {

                if (datas.size() > mAdapter.getCount()) {

                    isLoad = true;
                    page += 1;
                    mPresenter.getWaitComment();

                } else {

                    showShortToast("没有更多数据了！");
                }

                pullRefresh.finishLoadMore();
            }
        });
    }

    public static KFragment newIntence() {

        WaitEvaluatedFragment waitEvaluatedFragment = new WaitEvaluatedFragment();
        Bundle bundle = new Bundle();
        waitEvaluatedFragment.setArguments(bundle);
        return waitEvaluatedFragment;
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
    public int getPage() {
        return page;
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.getWaitComment();
    }

    @Override
    public void SuccessWaitEva(WaitEvaluatedBean data) {

        if (data != null) {

            final List<WaitEvaluatedBean.ListsBean> lists = data.getLists();

            if (!isLoad) {

                datas = lists;
                //设置适配器
                mAdapter = new WaitEvaluatedAdapter(getActivity(), datas);
                listView.setAdapter(mAdapter);

                //设置去评价的点击事件
                mAdapter.setOnEvualuatedClickLintener(new WaitEvaluatedAdapter.OnEvualuatedClickLintener() {
                    @Override
                    public void OnEvualuatedClickLintener(View view, int position) {

                        Intent intent = new Intent(getActivity(), EvaluateActivity.class);
                        intent.putExtra("order_id", lists.get(position).getId());
                        intent.putExtra("mesuaName", lists.get(position).getManufac_name());
                        startActivity(intent);
                    }
                });

            } else {

                isLoad = false;
                datas.addAll(data.getLists());
                mAdapter.notifyDataSetChanged();
            }

            if (datas == null || datas.size() == 0) {
                layout_empty.setVisibility(View.VISIBLE);
            }else{
                layout_empty.setVisibility(View.GONE);
            }
        }
    }
}

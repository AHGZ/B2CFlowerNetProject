package com.android.p2pflowernet.project.view.fragments.mine.applyfor.stake;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.StakeAdapter;
import com.android.p2pflowernet.project.entity.StakeBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.customview.DragIndicatorView;
import com.android.p2pflowernet.project.view.customview.MyListView;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner.ApplyForPartnerActivity;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2017/11/23.
 * by--入股明细
 */

public class StakeDetailFragment extends KFragment<IStakeDetailView, IStakeDetailPrenter>
        implements IStakeDetailView {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_else)
    TextView tvElse;
    @BindView(R.id.listview)
    MyListView listview;
    @BindView(R.id.tv_notic)
    DragIndicatorView tvNotic;
    @BindView(R.id.fl_notic)
    FrameLayout flNotic;
    @BindView(R.id.apply_btn)
    Button applyBtn;
    @BindView(R.id.ll_apply_for_stake)
    LinearLayout llApplyForStake;
    @BindView(R.id.ll_empt)
    LinearLayout llEmpt;
    @BindView(R.id.view_gray)
    View viewGray;
    @BindView(R.id.pull_refresh)
    PullToRefreshLayout pullToRefreshLayout;
    private ShapeLoadingDialog shapeLoadingDialog;
    private boolean isLoad = false;
    private StakeAdapter mAdapter;
    private int page = 1;
    private int count = 0;
    private List<StakeBean.ListsBean> lists1;
    private List<StakeBean.ListsBean> lists;

    @Override
    public IStakeDetailPrenter createPresenter() {

        return new IStakeDetailPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_applyfor_stake;
    }

    @Override
    public void initData() {

//        mPresenter.getStake();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

//        initData();

        pullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {

                page = 1;
                count = 0;
                mPresenter.getStake();
                pullToRefreshLayout.finishRefresh();
            }

            @Override
            public void loadMore() {

                if (mAdapter != null) {

                    if (count >= mAdapter.getCount()) {

                        isLoad = true;
                        page += 1;
                        mPresenter.getStake();

                    } else {

                        showShortToast("没有更多数据了！");
                    }
                }
                pullToRefreshLayout.finishLoadMore();
            }
        });
    }

    public static Fragment newIntence() {

        StakeDetailFragment stakeDetailFragment = new StakeDetailFragment();
        Bundle bundle = new Bundle();
        stakeDetailFragment.setArguments(bundle);
        return stakeDetailFragment;
    }

    @OnClick(R.id.apply_btn)
    public void onClick() {//去申请

        Intent intent = new Intent(getActivity(), ApplyForPartnerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onError(String s) {

        showShortToast(s);
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        mPresenter.getStake();
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
    public void setSuccessStake(StakeBean data) {

        if (data != null) {

            lists1 = data.getLists();

            count = lists1.size();

            if (!isLoad) {

                lists = lists1;

                if (lists != null && lists.size() > 0) {

                    listview.setVisibility(View.VISIBLE);
                    llEmpt.setVisibility(View.GONE);
                    llApplyForStake.setVisibility(View.VISIBLE);
                    viewGray.setVisibility(View.VISIBLE);

                    //设置适配器
                    mAdapter = new StakeAdapter(getActivity(), lists);
                    listview.setAdapter(mAdapter);

                } else {

                    //空数据
                    listview.setVisibility(View.GONE);
                    llEmpt.setVisibility(View.VISIBLE);
                    llApplyForStake.setVisibility(View.GONE);
                    viewGray.setVisibility(View.GONE);
                }

            } else {

                isLoad = false;
                if (lists1.size() > 0) {

                    lists.addAll(lists1);
                    mAdapter.notifyDataSetChanged();

                } else {
                    showShortToast("没有更多数据了！");
                }
            }
        }
    }

    @Override
    public int getPage() {
        return page;
    }
}

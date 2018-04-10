package com.android.p2pflowernet.project.view.fragments.goods.info.compare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ExpandableListView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.CompareListAdapter;
import com.android.p2pflowernet.project.entity.CompareListBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.List;

import butterknife.BindView;


/**
 * Created by caishen on 2018/1/29.
 * by--比价页面
 */

public class CompareListFragment extends KFragment<ICompareListView, ICompareListPrenter>
        implements NormalTopBar.normalTopClickListener, ICompareListView {
    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.ex_listView)
    ExpandableListView exListView;
    @BindView(R.id.pull_refresh)
    PullToRefreshLayout pullRefresh;
    private String spec_id = "";
    private ShapeLoadingDialog shapeLoadingDialog;

    @Override
    public ICompareListPrenter createPresenter() {

        return new ICompareListPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_compare_more;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spec_id = getArguments().getString("spec_id");
    }

    @Override
    public void initData() {

        mPresenter.compares();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setTitleText("比一比");
        normalTop.setTopClickListener(this);
        Utils.setStatusBar(getActivity(), 1, false);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .delay(5000)
                .loadText("加载中...")
                .build();

        initData();

        pullRefresh.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {

                pullRefresh.finishRefresh();
            }

            @Override
            public void loadMore() {

                pullRefresh.finishLoadMore();
            }
        });
    }

    public static KFragment NewIntence(String spec_id) {

        CompareListFragment compareListFragment = new CompareListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("spec_id", spec_id);
        compareListFragment.setArguments(bundle);
        return compareListFragment;
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
    public String getSpecId() {
        return spec_id;
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
    public void successGsInfo(CompareListBean data) {

        if (data != null) {

            List<CompareListBean.ListBean> list = data.getList();

            if (list != null && list.size() > 0) {

                exListView.setVisibility(View.VISIBLE);
                //设置比一比的适配器
                CompareListAdapter mAdapter = new CompareListAdapter(getActivity(), list);
                exListView.setAdapter(mAdapter);
                exListView.setGroupIndicator(null); //设置属性 GroupIndicator 去掉向下箭头

                for (int i = 0; i < mAdapter.getGroupCount(); i++) {
                    exListView.expandGroup(i); //关键步骤4:初始化，将ExpandableListView以展开的方式显示
                }

            }else {

                exListView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onSuccess(String message) {

    }
}

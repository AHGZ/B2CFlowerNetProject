package com.android.p2pflowernet.project.view.fragments.goods.comment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.EveluateAdapter;
import com.android.p2pflowernet.project.callback.AdapterLoader;
import com.android.p2pflowernet.project.entity.EveluateBean;
import com.android.p2pflowernet.project.entity.ListsBean;
import com.android.p2pflowernet.project.event.GoodEvaNumEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

import static com.android.p2pflowernet.project.R.id.comment_recycleview;

/**
 * Created by caishen on 2018/3/12.
 * by--评价分类页面
 */

public class GoodsEvaFragment extends KFragment<IGoodsEvaView, IGoodsEvaPrenter> implements
        IGoodsEvaView, AdapterLoader.OnItemClickListener<ListsBean> {

    @BindView(comment_recycleview)
    RecyclerView commentRecycleview;
    @BindView(R.id.pull_refresh)
    PullToRefreshLayout pullRefresh;
    private int page = 1;
    private int count = 0;
    private boolean isLoad = false;
    private EveluateAdapter eveluateAdapter;
    private int tag = 0;//分类
    private ShapeLoadingDialog shapeLoadingDialog;
    private String goods_id = "";

    @Override
    public IGoodsEvaPrenter createPresenter() {
        return new IGoodsEvaPrenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_goods_eva;
    }

    @Override
    public void initData() {

        //获取商品评价列表数据
        mPresenter.getEveluate();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = getArguments().getInt("tag");
        goods_id = getArguments().getString("goods_id");
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity()).loadText("加载中...")
                .delay(5000)
                .build();

        initData();

        //设置下拉加载更多数据
        pullRefresh.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {

                page = 1;
                count = 0;
                mPresenter.getEveluate();
                pullRefresh.finishRefresh();
            }

            @Override
            public void loadMore() {

                if (eveluateAdapter != null) {

                    if (count >= eveluateAdapter.getItemCount()) {

                        isLoad = true;
                        page += 1;
                        mPresenter.getEveluate();

                    } else {

                        showShortToast("没有更多数据了！");
                    }
                }

                pullRefresh.finishLoadMore();
            }
        });
    }

    public static Fragment newIntence(int tag, String goodId) {

        Bundle bundle = new Bundle();
        GoodsEvaFragment goodsEvaFragment = new GoodsEvaFragment();
        goodsEvaFragment.setArguments(bundle);
        bundle.putInt("tag", tag);
        bundle.putString("goods_id", goodId);
        return goodsEvaFragment;
    }

    @Override
    public void onError(String message) {
        showShortToast(message);
    }

    @Override
    public void showDialog() {

        shapeLoadingDialog.show();
    }

    @Override
    public String goodsId() {
        return goods_id;
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
    public void successEveluate(EveluateBean data) {

        //设置适配器
        if (data != null) {

            List<ListsBean> lists1 = data.getLists();
            //赋值加载数据
            count += lists1.size();

            //发送待评价数的广播
            EventBus.getDefault().post(new GoodEvaNumEvent(data));

            if (!isLoad) {

                List<ListsBean> lists = lists1;

                if (lists != null && lists.size() > 0) {

                    //设置评价的适配器
                    LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
                    commentRecycleview.setLayoutManager(mLinearLayoutManager);
                    eveluateAdapter = new EveluateAdapter(getActivity());
                    eveluateAdapter.attachRecyclerView(commentRecycleview);
                    eveluateAdapter.setList(lists);
                    commentRecycleview.setAdapter(eveluateAdapter);
                    eveluateAdapter.setOnItemClickListener(this);

                } else {

                }

            } else {

                isLoad = false;
                if (lists1.size() > 0) {

                    eveluateAdapter.appendList(lists1);
                    eveluateAdapter.notifyDataSetChanged();

                } else {

                    showShortToast("没有更多数据了！");
                }
            }
        }
    }

    @Override
    public void onSuccess(String message) {
        showShortToast(message);
    }

    @Override
    public int type() {
        return tag;
    }

    @Override
    public void onItemClick(View itemView, int position, ListsBean item) {

    }
}

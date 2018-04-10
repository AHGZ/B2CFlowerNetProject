package com.android.p2pflowernet.project.o2omain.fragment.storedetail.evaluationdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.EvaluationRecyclerAdapter;
import com.android.p2pflowernet.project.entity.ShopEvaluationBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;

import java.util.List;

import butterknife.BindView;

/**
 * Created by heguozhong on 2018/1/5/005.
 * 评论详情页面
 */

public class EvaluationDetailsFragment extends
        KFragment<IEvaluationDetailsView, IEvaluationDetailsPresenter> implements IEvaluationDetailsView {
    //绑定显示评价内容的recyclerview
    @BindView(R.id.group_buying_evaluation_recyclerview)
    RecyclerView groupBuyingEvaluationRecyclerview;
    private ShapeLoadingDialog shapeLoadingDialog;

    public static EvaluationDetailsFragment newInstance() {
        Bundle args = new Bundle();
        EvaluationDetailsFragment fragment = new EvaluationDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public IEvaluationDetailsPresenter createPresenter() {
        return new IEvaluationDetailsPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.groupbuying_evaluation_details_fragment;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .delay(5000)
                .loadText("加载中...")
                .build();
//        mPresenter.getShopEvaluation();
    }

    @Override
    public void onError(String errorMsg) {

    }

    @Override
    public void onSuccess(ShopEvaluationBean shopEvaluationBean) {
        List<ShopEvaluationBean.ListsBean> lists = shopEvaluationBean.getLists();
        //获取布局管理者
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //设置为垂直排列格式
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //recyclerview应用垂直排列格式
        groupBuyingEvaluationRecyclerview.setLayoutManager(linearLayoutManager);
        //设置适配器
        EvaluationRecyclerAdapter evaluationRecyclerAdapter = new EvaluationRecyclerAdapter(getActivity(),lists);
        groupBuyingEvaluationRecyclerview.setAdapter(evaluationRecyclerAdapter);
    }

    @Override
    public void onSuccess(String message) {

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
}

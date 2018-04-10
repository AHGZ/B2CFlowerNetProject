package com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying.evaluation.groupevaluationdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.GroupEvaluationRecyclerAdapter;
import com.android.p2pflowernet.project.entity.GroupEvaluationBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;

import java.util.List;

import butterknife.BindView;

/**
 * Created by heguozhong on 2018/1/5/005.
 * 评论详情有图页面
 */

public class GroupEvaluationDetailsFragment2 extends KFragment<IGroupEvaluationDetailsView, IGroupEvaluationDetailsPresenter> implements IGroupEvaluationDetailsView {
    //绑定显示评价内容的recyclerview
    @BindView(R.id.group_buying_evaluation_recyclerview)
    RecyclerView groupBuyingEvaluationRecyclerview;
    private ShapeLoadingDialog shapeLoadingDialog;
    private int type;

    public static GroupEvaluationDetailsFragment2 newInstance() {
        Bundle args = new Bundle();
        GroupEvaluationDetailsFragment2 fragment = new GroupEvaluationDetailsFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public IGroupEvaluationDetailsPresenter createPresenter() {
        return new IGroupEvaluationDetailsPresenter();
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
            mPresenter.getShopEvaluation(1,5,3,1);

    }

    @Override
    public void onError(String errorMsg) {
        showShortToast(errorMsg);
    }

    @Override
    public void onSuccess(GroupEvaluationBean groupEvaluationBean) {
        List<GroupEvaluationBean.EvalListBean> eval_list = groupEvaluationBean.getEval_list();
        //获取布局管理者
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //设置为垂直排列格式
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //recyclerview应用垂直排列格式
        groupBuyingEvaluationRecyclerview.setLayoutManager(linearLayoutManager);
        //设置适配器
        GroupEvaluationRecyclerAdapter groupEvaluationRecyclerAdapter = new GroupEvaluationRecyclerAdapter(getActivity(),eval_list);
        groupBuyingEvaluationRecyclerview.setAdapter(groupEvaluationRecyclerAdapter);
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

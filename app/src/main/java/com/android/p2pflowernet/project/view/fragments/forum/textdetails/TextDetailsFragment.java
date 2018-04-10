package com.android.p2pflowernet.project.view.fragments.forum.textdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.ForumTextAdapter;
import com.android.p2pflowernet.project.entity.ForumDetailsBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhangkun on 2018/1/23.
 */

public class TextDetailsFragment extends KFragment<ITextDetailsView,ITextDetailsPresenter> implements NormalTopBar.normalTopClickListener,ITextDetailsView {

    @BindView(R.id.normal_top)
    NormalTopBar topBar;
    @BindView(R.id.fragment_text_details_recyclerView)
    RecyclerView mRecyclerView;

    private String id;
    private int page = 1;
    private ShapeLoadingDialog shapeLoadingDialog;
    private List<ForumDetailsBean> detailsBean;
    private ForumTextAdapter adapter;

    @Override
    public ITextDetailsPresenter createPresenter() {
        return new ITextDetailsPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_text_details;
    }

    public static KFragment newIntence(String id){
        TextDetailsFragment fragment = new TextDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getString("id");
    }

    @Override
    public void initData() {
        mPresenter.getForumContent();

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        topBar.setBackgroundColor(getResources().getColor(R.color.forum_titleBar_bg));
        topBar.setTitleText("详情");
        topBar.setLeftImageId(R.mipmap.icon_back_white);
        topBar.setTitleTextColor(getResources().getColor(R.color.white));
        topBar.setTopClickListener(this);
        //初始化沉浸式
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.forum_titleBar_bg));

        detailsBean = new ArrayList<>();

        //初始化加载窗口
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(2000)
                .build();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new ForumTextAdapter(detailsBean,getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
        initData();
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
    public void onError(String string) {
        showShortToast(string);
    }

    @Override
    public void onSuccess(ForumDetailsBean data) {
        if (null != data) {
            detailsBean.add(data);
            for (int i = 1; i < detailsBean.get(0).getPage_count(); i++) {
                page++;
                mPresenter.getForumContent();
            }

            if (page == detailsBean.get(0).getPage_count()) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void showDialog() {
        shapeLoadingDialog.show();
    }

    @Override
    public void hideDialog() {
        if (shapeLoadingDialog.isShowing()) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public String getCourseId() {
        return id;
    }

    @Override
    public int getPage() {
        return page;
    }
}

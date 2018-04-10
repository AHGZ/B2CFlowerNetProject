package com.android.p2pflowernet.project.view.fragments.forum.videodetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.ForumTextAdapter;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.ForumDetailsBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by zhangkun on 2018/1/22.
 */

public class VideoDetailsFragment extends KFragment<IVideoDetailsView,IVideoDetailsPresenter> implements IVideoDetailsView{

    @BindView(R.id.fragment_video_details_JZVideoPlayer)
    JZVideoPlayerStandard videoPlayer;
    @BindView(R.id.fragment_video_details_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.fragment_video_details_img)
    ImageView mImageView;

    private String id;
    private int page = 1;
    private ShapeLoadingDialog shapeLoadingDialog;
    private List<ForumDetailsBean> detailsBean;
    private ForumTextAdapter adapter;


    public static VideoDetailsFragment newInstance(String id){
        VideoDetailsFragment fragment = new VideoDetailsFragment();
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
    public IVideoDetailsPresenter createPresenter() {
        return new IVideoDetailsPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_video_details;
    }

    @Override
    public void initData() {
        mPresenter.getForumContent();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        //初始化沉浸式
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.forum_titleBar_bg));

        detailsBean = new ArrayList<>();

        //初始化加载窗口
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(2000)
                .build();

        //初始化recyclerView设置
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new ForumTextAdapter(detailsBean,getActivity());
        mRecyclerView.setAdapter(adapter);

        initData();
    }

    @OnClick(R.id.fragment_video_details_img)
    public void onClick(){
        removeFragment();
    }

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onError(String string) {
        showShortToast(string);
    }

    @Override
    public void onSuccess(ForumDetailsBean data) {
        if (null != data) {
            detailsBean.add(data);
            //设置视频
            if (page == 1) {
                videoPlayer.setUp(ApiUrlConstant.API_BASE_URL + detailsBean.get(0).getVideo_url(),JZVideoPlayer.SCREEN_WINDOW_NORMAL,"");
            }

            //获取文章内容
            for (int i = 1; i < detailsBean.get(0).getPage_count(); i++) {
                page ++;
                mPresenter.getForumContent();
            }

            //更新适配器
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

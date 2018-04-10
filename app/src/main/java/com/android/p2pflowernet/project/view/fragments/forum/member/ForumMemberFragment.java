package com.android.p2pflowernet.project.view.fragments.forum.member;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.ForumMemberAdapter;
import com.android.p2pflowernet.project.entity.ForumListBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerManager;


/**
 * 花返会员
 * Created by zhangkun on 2018/1/18.
 */

public class ForumMemberFragment extends KFragment<IForumMemberView,IForumMemberPresenter> implements IForumMemberView{

    @BindView(R.id.fragment_forumMember_rl)
    RecyclerView mRecyclerView;
    @BindView(R.id.fragment_forum_pullToRefreshLayout)
    PullToRefreshLayout pullToRefreshLayout;

    private ForumMemberAdapter adapter;
    private String id;
    private ShapeLoadingDialog shapeLoadingDialog;
    private List<ForumListBean.ListsBean> listsBeans;
    private int page = 1;
    private boolean isLoadMore = false;

    @Override
    public IForumMemberPresenter createPresenter() {
        return new IForumMemberPresenter();
    }

    public static KFragment newIntence(String id){
        ForumMemberFragment fragment = new ForumMemberFragment();
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
    protected int getLayout() {
        return R.layout.fragment_forum_member;
    }

    @Override
    public void initData() {
        mPresenter.getForumList();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化加载窗口
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(2000)
                .build();

        listsBeans = new ArrayList<>();

        pullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                pullToRefreshLayout.finishRefresh();
            }

            @Override
            public void loadMore() {
                page++;
                isLoadMore = true;
                mPresenter.getForumList();
            }
        });

        //获取布局管理者
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //设置为垂直排列格式
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ForumMemberAdapter(getActivity(),listsBeans);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
               int type =  mRecyclerView.getChildViewHolder(view).getItemViewType();
                if (type == 1 || type == 2) {
                    if (JZVideoPlayerManager.getCurrentJzvd() != null && JZVideoPlayerManager.getCurrentJzvd().currentScreen != JZVideoPlayer.SCREEN_WINDOW_FULLSCREEN) {
                        JZVideoPlayer.releaseAllVideos();
                    }
                }
            }
        });

        initData();
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
    public void onSuccess(ForumListBean data) {
        if (null != data && data.getLists().size() > 0) {
            listsBeans.addAll(data.getLists());
            adapter.notifyDataSetChanged();
        }

        //如果在加载更多内容则完成加载更多
        if (isLoadMore) {
            showShortToast("无更多数据");
            pullToRefreshLayout.finishLoadMore();
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

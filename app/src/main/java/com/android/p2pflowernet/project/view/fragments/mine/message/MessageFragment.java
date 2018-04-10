package com.android.p2pflowernet.project.view.fragments.mine.message;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.MessageListAdapter;
import com.android.p2pflowernet.project.entity.MessagesBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.mine.message.msgdetail.MessageDetailActivity;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.List;

import butterknife.BindView;

/**
 * Created by caishen on 2017/11/4.
 * by--消息类型的列表数据页面
 */

public class MessageFragment extends KFragment<IMessageView, IMessagePrenter>
        implements NormalTopBar.normalTopClickListener, IMessageView {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.pull_refresh)
    PullToRefreshLayout pullRefresh;
    private ShapeLoadingDialog shapeLoadingDialog;
    private List<MessagesBean.ListsBean> lists;

    @Override
    public IMessagePrenter createPresenter() {
        return new IMessagePrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_message;
    }

    @Override
    public void initData() {

        mPresenter.getMessages();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {


        normalTop.setTitleText("消息中心");
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setTopClickListener(this);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        Utils.setStatusBar(getActivity(), 0, false);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        initData();

        initClick();
    }

    private void initClick() {

        //设置消息类型的点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (lists != null && lists.size() > 0) {

                    Intent intent = new Intent(getActivity(), MessageDetailActivity.class);
                    intent.putExtra("type", lists.get(position).getType());
                    startActivity(intent);
                }
            }
        });

        //设置下拉刷新，上拉加载更多
        pullRefresh.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {

                mPresenter.getMessages();
                pullRefresh.finishRefresh();
            }

            @Override
            public void loadMore() {

                pullRefresh.finishLoadMore();
            }
        });
    }

    public static KFragment newIntence() {

        MessageFragment messageFragment = new MessageFragment();
        Bundle bundle = new Bundle();
        messageFragment.setArguments(bundle);

        return messageFragment;
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
    public void hideDialog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void onSuccessMessages(MessagesBean data) {

        if (data != null) {

            //设置消息类型数据的适配器
            lists = data.getLists();
            MessageListAdapter mAdapter = new MessageListAdapter(getActivity(), data.getLists());
            listview.setAdapter(mAdapter);
        }
    }
}

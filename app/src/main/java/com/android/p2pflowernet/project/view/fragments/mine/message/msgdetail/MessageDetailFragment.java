package com.android.p2pflowernet.project.view.fragments.mine.message.msgdetail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.p2pflowernet.project.MainActivity;
import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.HdDeAdapter;
import com.android.p2pflowernet.project.adapter.MessageDeAdapter;
import com.android.p2pflowernet.project.adapter.WlDeAdapter;
import com.android.p2pflowernet.project.entity.ActionItem;
import com.android.p2pflowernet.project.entity.MessaTypeBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.TitlePopup;
import com.android.p2pflowernet.project.view.fragments.search.SearchActivity;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by caishen on 2017/11/8.
 * by--信息类型详情页面
 */

public class MessageDetailFragment extends KFragment<IMessageDetailView, IMessageDetailPrenter>
        implements NormalTopBar.normalTopClickListener, TitlePopup.OnItemOnClickListener, IMessageDetailView {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.pull_refresh)
    PullToRefreshLayout pullRefresh;
    private TitlePopup titlePopup;
    private String type = "";
    private int page = 1;
    private ShapeLoadingDialog shapeLoadingDialog;
    private boolean isRefresh = false;//是否加载更多
    private List<MessaTypeBean.ListsBean> messagesBeen;
    private MessageDeAdapter mAdapter;
    private WlDeAdapter mAdapter1;
    private HdDeAdapter mAdapter2;
    private String receiver = "1,2,3,4,5";//-会员 2-代理 3-合伙人 4-云工 5-生活商家 6-厂家 11-买家 12-卖家,如果多个用英文逗号(,)隔开
    //b2c, o2o取值1,2,3,4,5
    //店铺app取值12

    public static MessageDetailFragment newIntence(String type) {

        MessageDetailFragment messageDetailFragment = new MessageDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        messageDetailFragment.setArguments(bundle);
        return messageDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        type = arguments.getString("type");
    }

    @Override
    public IMessageDetailPrenter createPresenter() {

        return new IMessageDetailPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_message_detail;
    }


    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {


        //1-反润 2-物流 3-申请 4-互动 5-系统通知
        if (type.equals("1")) {
            normalTop.setTitleText("反润通知");
        } else if (type.equals("3")) {
            normalTop.setTitleText("申请通知");
        } else if (type.equals("2")) {
            normalTop.setTitleText("物流通知");
        } else if (type.equals("4")) {
            normalTop.setTitleText("互动通知");
        } else {
            normalTop.setTitleText("系统通知");
        }

        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setTopClickListener(this);
        normalTop.getRightImage().setVisibility(View.VISIBLE);
        normalTop.getRightImage().setImageResource(R.drawable.fr_icon_gd);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        Utils.setStatusBar(getActivity(), 0, false);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        UIUtils.setTouchDelegate(normalTop.getRightImage(), 50);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        //初始化右上角弹窗数据
        initTitlePou();

        initData();

        initClick();
    }

    private void initClick() {

        //设置下拉刷新，上拉加载更多
        pullRefresh.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {

                page = 1;
                mPresenter.getNoticeList();
                pullRefresh.finishRefresh();
            }

            @Override
            public void loadMore() {

                page++;
                mPresenter.getNoticeList();
                pullRefresh.finishLoadMore();
            }
        });
    }

    private void initTitlePou() {

        //初始化右上角弹窗
        titlePopup = new TitlePopup(getActivity(), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 给标题栏弹窗添加子类
        titlePopup.addAction(new ActionItem(getActivity(), "首页", R.drawable.fr_icon_sy));
        titlePopup.addAction(new ActionItem(getActivity(), "搜索", R.drawable.fr_icon_ss));
        titlePopup.setItemOnClickListener(this);
    }

    @Override
    public void initData() {


        mPresenter.getNoticeList();

    }


    @Override
    public void onLeftClick(View view) {

        removeFragment();
    }

    @Override
    public void onRightClick(View view) {

        titlePopup.show(view);

        //全部已读
        mPresenter.getNoticeList();
    }

    @Override
    public void onTitleClick(View view) {

    }

    @Override
    public void onItemClick(ActionItem item, int position) {
        switch (position) {
            case 0://首页

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                removeFragment();

                break;
            case 1://搜索

                Intent intent1 = new Intent(getActivity(), SearchActivity.class);
                intent1.putExtra("tag", "0");//0-b2c 1-o2o
                startActivity(intent1);
                removeFragment();

                break;
        }
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public int getPage() {
        return page;
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
    public void onsuccessType(MessaTypeBean data) {

        if (data != null) {

            messagesBeen = new ArrayList<>();

            if (!isRefresh) {//首次加载数据

                messagesBeen = data.getLists();
                //1-反润 2-物流 3-申请 4-互动 5-系统通知
                if (type.equals("1") || type.equals("3") || type.equals("5")) {

                    //设置返润通知的消息适配器
                    mAdapter = new MessageDeAdapter(getActivity(), messagesBeen, type);
                    listView.setAdapter(mAdapter);

                } else if (type.equals("2")) {

                    //设置物流通知的消息适配器
                    mAdapter1 = new WlDeAdapter(getActivity(), messagesBeen);
                    listView.setAdapter(mAdapter1);

                } else if (type.equals("4")) {

                    //设置互动通知的消息适配器
                    mAdapter2 = new HdDeAdapter(getActivity(), messagesBeen);
                    listView.setAdapter(mAdapter2);
                }

            } else {

                isRefresh = false;
                messagesBeen.addAll(data.getLists());

                if (type.equals("1") || type.equals("3") || type.equals("5")) {
                    mAdapter.notifyDataSetChanged();
                } else if (type.equals("2")) {
                    mAdapter1.notifyDataSetChanged();
                } else if (type.equals("4")) {
                    mAdapter2.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public String getreceiver() {
        return receiver;
    }
}

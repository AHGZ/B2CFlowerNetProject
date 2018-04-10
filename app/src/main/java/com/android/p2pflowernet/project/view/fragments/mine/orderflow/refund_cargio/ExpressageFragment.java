package com.android.p2pflowernet.project.view.fragments.mine.orderflow.refund_cargio;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.ExpressAdapter;
import com.android.p2pflowernet.project.adapter.HeaderRecyclerAndFooterWrapperAdapter;
import com.android.p2pflowernet.project.entity.BaseIndexPinyinBean;
import com.android.p2pflowernet.project.entity.ExpresListBean;
import com.android.p2pflowernet.project.event.ExpressEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.DividerItemDecoration;
import com.android.p2pflowernet.project.utils.OnItemClickListener;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.IndexBar;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.SuspensionDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by caishen on 2017/11/18.
 * by--显示所有快递公司的页面
 */

public class ExpressageFragment extends KFragment<IExpressageView, IExpressagePrenter>
        implements NormalTopBar.normalTopClickListener, IExpressageView {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.indexBar)
    IndexBar indexBar;
    @BindView(R.id.tvSideBarHint)
    TextView tvSideBarHint;
    private LinearLayoutManager mManager;
    //主体部分数据源（快递公司）
    private List<ExpresListBean.ListsBean> mBodyDatas;
    //悬停吸顶
    private SuspensionDecoration mDecoration;
    private ExpressAdapter mAdapter;
    private HeaderRecyclerAndFooterWrapperAdapter mHeaderAdapter;
    //设置给InexBar、ItemDecoration的完整数据集
    private List<BaseIndexPinyinBean> mSourceDatas;
    private ShapeLoadingDialog shapeLoadingDialog;

    @Override
    public IExpressagePrenter createPresenter() {

        return new IExpressagePrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_expressageprenter;
    }

    @Override
    public void initData() {

        mPresenter.expressList();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸栏
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        Utils.setStatusBar(getActivity(), 0, false);
        normalTop.setTitleText("快递公司");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setRightText("取消");
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setRightTextColor(Color.WHITE);
        normalTop.setTopClickListener(this);
        normalTop.getRightImage().setImageResource(R.drawable.icon_shop_car);
        normalTop.getRightImage().setVisibility(View.VISIBLE);
        normalTop.getRightTextView().setVisibility(View.GONE);
        // 扩大事件的点击范围
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        //放大视图的点击事件
        UIUtils.setTouchDelegate(normalTop, 50);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity()).loadText("加载中...").delay(5000).build();

        initData();
    }

    /**
     * 组织数据源
     *
     * @param data
     * @return
     */
    private void initDatas(final List<ExpresListBean.ListsBean> data) {

        mBodyDatas = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            ExpresListBean.ListsBean listsBean = new ExpresListBean.ListsBean();
            listsBean.setExpress_name(data.get(i).getExpress_name());//设置城市名称
            mBodyDatas.add(listsBean);
        }

        //先排序
        indexBar.getDataHelper().sortSourceDatas(mBodyDatas);
        mAdapter.setDatas(mBodyDatas);
        mAdapter.notifyDataSetChanged();
        mSourceDatas.addAll(mBodyDatas);
        indexBar.setmSourceDatas(mSourceDatas)//设置数据
                .invalidate();
        mDecoration.setmDatas(mSourceDatas);
    }


    public static KFragment newIntence() {

        Bundle bundle = new Bundle();
        ExpressageFragment expressageFragment = new ExpressageFragment();
        expressageFragment.setArguments(bundle);
        return expressageFragment;
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
    public void showDialog() {

        shapeLoadingDialog.show();
    }

    @Override
    public void onError(String s) {

        showShortToast(s);
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    /**
     * 获取快递公司列表成功
     *
     * @param data
     */
    @Override
    public void successExpress(ExpresListBean data) {

        if (data != null) {

            mRv.setLayoutManager(mManager = new LinearLayoutManager(getActivity()));
            mSourceDatas = new ArrayList<>();
            mAdapter = new ExpressAdapter(getActivity(), R.layout.meituan_item_select_city, mBodyDatas);
            mRv.setAdapter(mAdapter);

            //吸顶效果实现
            mRv.addItemDecoration(mDecoration = new SuspensionDecoration(getActivity(), mSourceDatas)
                    .setmTitleHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, getResources().getDisplayMetrics()))
                    .setColorTitleBg(0xffefefef)
                    .setTitleFontSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()))
                    .setColorTitleFont(getActivity().getResources().getColor(android.R.color.black))
                    .setHeaderViewCount(mAdapter.getItemCount()));
            mRv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

            indexBar.setmPressedShowTextView(tvSideBarHint)//设置HintTextView
                    .setNeedRealIndex(true)//设置需要真实的索引
                    .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                    .setHeaderViewCount(mAdapter.getItemCount());

            initDatas(data.getLists());

            //设置点击事件
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                    ExpresListBean.ListsBean expressBean = mBodyDatas.get(position);
                    if (expressBean == null) {
                        return;
                    }

                    //发送消息
                    EventBus.getDefault().post(new ExpressEvent(expressBean));
                    removeFragment();
                }

                @Override
                public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                    return false;
                }
            });
        }
    }
}

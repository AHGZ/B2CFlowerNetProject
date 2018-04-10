package com.android.p2pflowernet.project.view.fragments.mine.orderflow.logistics;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.TraceAdapter;
import com.android.p2pflowernet.project.entity.LogisticsDetailBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * Created by caishen on 2017/11/20.
 * by--查看物流信息
 */

public class LogiSticsDetailFragment extends KFragment<ILogisticsDetailView,
        ILogisticsDetailPrenter> implements NormalTopBar.normalTopClickListener, ILogisticsDetailView {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_order)
    TextView tvOrder;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.lv_trace)
    ListView lvTrace;
    // 0-在途 1-已揽件 2-货物寄送过程出了问题 3-签收，收件人已签收 4：退签，发件人已经签收 5-同城派件中 6-退回途中
    String mStatus;
    // 订单编号
    String ordernum;
    // 物流编号
    private String waybill_num;
    // 物流公司id
    private String express_id;
    // 加载进度条
    private ShapeLoadingDialog shapeLoadingDialog;

    public static LogiSticsDetailFragment newIntence(String state, String ordernum, String waybill_num, String express_id) {
        Bundle bundle = new Bundle();
        LogiSticsDetailFragment logiSticsDetailFragment = new LogiSticsDetailFragment();
        bundle.putString("state", state);
        bundle.putString("ordernum", ordernum);
        bundle.putString("waybill_num", waybill_num);
        bundle.putString("express_id", express_id);
        logiSticsDetailFragment.setArguments(bundle);
        return logiSticsDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStatus = getArguments().getString("state");
        ordernum = getArguments().getString("ordernum");
        waybill_num = getArguments().getString("waybill_num");
        express_id = getArguments().getString("express_id");
    }

    @Override
    public ILogisticsDetailPrenter createPresenter() {

        return new ILogisticsDetailPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_logisticsdetail;
    }

    @Override
    public void initData() {
        mPresenter.querydelivery();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setTitleText("订单跟踪");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        Utils.setStatusBar(getActivity(), 0, false);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();
        //初始化订单详细数据
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
    public String getOrderNum() {
        return TextUtils.isEmpty(ordernum) ? "" : ordernum;
    }

    @Override
    public String getWaybillnum() {
        return TextUtils.isEmpty(waybill_num) ? "" : waybill_num;
    }

    @Override
    public String getExpressId() {
        return TextUtils.isEmpty(express_id) ? "" : express_id;
    }

    @Override
    public String getStatus() {
        return TextUtils.isEmpty(mStatus) ? "" : mStatus;
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
    public void onError(String errorMsg) {
        showShortToast(errorMsg);
    }

    @Override
    public void onSuccess(String message) {
        showShortToast(message);
    }

    @Override
    public void onSuccess(LogisticsDetailBean logisticsDetailBean) {
        if (logisticsDetailBean == null) {
            return;
        }
        ArrayList<LogisticsDetailBean.ListsBean> logisticsDetailBeanLists = logisticsDetailBean.getLists();
        if (logisticsDetailBeanLists.isEmpty() && logisticsDetailBeanLists == null) {
            return;
        }
        //设置适配器
        TraceAdapter mAdapter = new TraceAdapter(logisticsDetailBeanLists, getActivity());
        lvTrace.setAdapter(mAdapter);
    }

}

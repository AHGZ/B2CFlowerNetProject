package com.android.p2pflowernet.project.view.fragments.mine.orderflow.refund.refunddetail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.RefundGsListAdapter;
import com.android.p2pflowernet.project.entity.OrderDetailItemBean;
import com.android.p2pflowernet.project.entity.RefundBean;
import com.android.p2pflowernet.project.entity.RefundDetailBean;
import com.android.p2pflowernet.project.event.RefundEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.mine.cargo_refund.ApplyForArbitrationActivity;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.logistics.info.LogisticsinfoActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @描述:各种订单详情主类
 * @创建人：zhangpeisen
 * @创建时间：2017/12/16 上午9:35
 * @修改人：zhangpeisen
 * @修改时间：2017/12/16 上午9:35
 * @修改备注：
 * @throws
 */
public class RefundDetailFragment extends KFragment<IRefundDetailView,
        IRefundDetailPrenter> implements NormalTopBar.normalTopClickListener, IRefundDetailView {
    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.refund_state)
    // 退款状态
            TextView refundState;
    @BindView(R.id.tv_allmoney)
    // 退款金额
            TextView tvAllmoney;
    @BindView(R.id.Reasonsforrejection_ly)
    // 拒绝原因
            LinearLayout Reasonsforrejection_ly;
    @BindView(R.id.apply_resonsetitle)
    // 不同状态标题
            TextView applyResonsetitle;
    @BindView(R.id.apply_resonsevalue)
    // 不同状态描述
            TextView applyResonsevalue;
    @BindView(R.id.refund_adress)
    // 退货地址
            TextView refundAdress;
    @BindView(R.id.ll_location)
    LinearLayout llLocation;
    @BindView(R.id.refund_reason_title)
    //退款原因
            TextView refundReasontitle;
    @BindView(R.id.shop_name)
    //店铺名称
            TextView shopName;
    @BindView(R.id.refund_reasonvalue)
    //退款原因
            TextView refundReasonvalue;
    @BindView(R.id.refund_amountvalue)
    //退款金额
            TextView refundAmountvalue;
    @BindView(R.id.refund_applydate)
    // 申请日期
            TextView refundApplydate;
    @BindView(R.id.refund_servicenum)
    // 服务单号
            TextView refundServicenum;
    @BindView(R.id.cunstomer_ly)
    // 联系客服
            LinearLayout cunstomerLy;
    @BindView(R.id.callphone_ly)
    // 拨打电话
            LinearLayout callphoneLy;
    @BindView(R.id.tv_statu_center)
    // 状态1
            TextView tvStatuCenter;
    @BindView(R.id.tv_statu_right)
    // 状态2
            TextView tvStatuRight;
    @BindView(R.id.goodslist_RecyclerView)
    // 商品列表
            RecyclerView goodslist_RecyclerView;
    // 商品列表适配器
    RefundGsListAdapter refundGsListAdapter;
    // 加载进度条
    private ShapeLoadingDialog shapeLoadingDialog;
    // 来自订单列表
    RefundBean.ListsBean mRefundLists;
    // 来自订单详情
    OrderDetailItemBean mRefundDetail;
    String mRefundid;

    public static RefundDetailFragment newIntence(String Refundid, RefundBean.ListsBean listsBean, OrderDetailItemBean orderDetailItemBean) {
        Bundle bundle = new Bundle();
        RefundDetailFragment applyforRefundFragment = new RefundDetailFragment();
        bundle.putSerializable("refundlists", listsBean);
        bundle.putSerializable("refundgooddetail", orderDetailItemBean);
        bundle.putString("refundid", Refundid);
        applyforRefundFragment.setArguments(bundle);
        return applyforRefundFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRefundLists = (RefundBean.ListsBean) getArguments().getSerializable("refundlists");
        mRefundDetail = (OrderDetailItemBean) getArguments().getSerializable("refundgooddetail");
        mRefundid = getArguments().getString("refundid");
    }

    @Override
    public IRefundDetailPrenter createPresenter() {

        return new IRefundDetailPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.order_refunddetail_fragment;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setTitleText("退换货详情");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        normalTop.setRightTextColor(Color.WHITE);
        Utils.setStatusBar(getActivity(), 0, false);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();
        // 获取退换货详情
        mPresenter.refundrecorddetail();

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
    public String getRefundid() {
        return TextUtils.isEmpty(mRefundid) ? "" : mRefundid;
    }

    @Override
    public void showDialog() {
        shapeLoadingDialog.show();
    }

    @Override
    public void hideDialog() {
        if (shapeLoadingDialog.isShowing())
            shapeLoadingDialog.dismiss();
    }


    @Override
    public void onError(String errorMsg) {
        showShortToast(errorMsg);
    }


    @Override
    public void onSuccess(RefundDetailBean refundDetailBean) {
        if (refundDetailBean == null) {
            return;
        }
        goodslist_RecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refundGsListAdapter = new RefundGsListAdapter();
        refundGsListAdapter.attachRecyclerView(goodslist_RecyclerView);
        // 服务流水号
        refundServicenum.setText(TextUtils.isEmpty(refundDetailBean.getId()) ? "" : refundDetailBean.getId());
        // 退款金额
        refundAmountvalue.setText(TextUtils.isEmpty(refundDetailBean.getRefund_money()) ? "" : refundDetailBean.getRefund_money());
        // 退款总金额
        tvAllmoney.setText(TextUtils.isEmpty(refundDetailBean.getRefund_money()) ? "" : "¥"+refundDetailBean.getRefund_money());
        List<RefundDetailBean.GoodsListBean> goods_list = refundDetailBean.getGoods_list() == null ? null : refundDetailBean.getGoods_list();
        refundGsListAdapter.setList(goods_list);
        // 快递信息
        refundAdress.setText(TextUtils.isEmpty(refundDetailBean.getExpress_name()) ? "" : refundDetailBean.getExpress_name());
        // 退款理由
        refundReasonvalue.setText(TextUtils.isEmpty(refundDetailBean.getReason()) ? "" : refundDetailBean.getReason());
        // 申请日期
        refundApplydate.setText(TextUtils.isEmpty(refundDetailBean.getCreated()) ? "" : refundDetailBean.getCreated());
        //店铺名称
        shopName.setText(TextUtils.isEmpty(refundDetailBean.getManufac_name()) ? "" : refundDetailBean.getManufac_name());

        if (TextUtils.isEmpty(refundDetailBean.getRefund_state())) {
            return;
        }
        String text = "";
        int color = Color.parseColor("#4B4B4B");
        // 0-未退款 1-申请退款 2-待提交物流信息 3-待退款 9-取消退款 10-退款成功(厂家确认收货) 11-拒绝退款
        switch (refundDetailBean.getRefund_state()) {
            case "0":
                // 未退款
                text = "等待商家同意";
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
                tvStatuRight.setVisibility(View.VISIBLE);
                tvStatuCenter.setVisibility(View.GONE);
                llLocation.setVisibility(View.GONE);
                tvStatuRight.setText("取消退款");
                tvStatuRight.setBackgroundResource(R.drawable.tv_order_pay_selector);
                break;
            case "1":
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
                text = "如商家6天23小时59分内未确认将自动退款给用户";
                tvStatuRight.setText("取消退款");
                tvStatuRight.setVisibility(View.VISIBLE);
                llLocation.setVisibility(View.GONE);
                break;
            case "2":
                // 待提交物流信息
                text = "等待买家退货";
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
                tvStatuRight.setText("完善物流信息");
                tvStatuCenter.setText("取消退货");
                tvStatuRight.setVisibility(View.VISIBLE);
                tvStatuCenter.setVisibility(View.VISIBLE);
                tvStatuCenter.setBackgroundResource(R.drawable.tv_order_pay_selector);
                tvStatuRight.setBackgroundResource(R.drawable.tv_order_pay_selector);
                break;
            case "3":
                // 待退款
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
                if (TextUtils.isEmpty(refundDetailBean.getWaybill_num()) && refundDetailBean.getWaybill_num().equals("")) {
                    text = "等待商家退款";
                    llLocation.setVisibility(View.GONE);
                    //没有物流信息
                    tvStatuRight.setText("完善物流信息");
                    tvStatuRight.setVisibility(View.VISIBLE);
                    llLocation.setVisibility(View.VISIBLE);
                    tvStatuRight.setBackgroundResource(R.drawable.tv_order_pay_selector);
                    refundState.setText(text);
                } else {
                    tvStatuRight.setVisibility(View.GONE);
                    text = "等待商家退货";
                    llLocation.setVisibility(View.VISIBLE);
                    refundReasontitle.setText("拒绝原因");
                    refundReasonvalue.setText(TextUtils.isEmpty(refundDetailBean.getExplain()) ? "" : refundDetailBean.getExplain());
                }
                break;
            case "9":
                // 取消退款
                // 退款关闭
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
                // 地址
                llLocation.setVisibility(View.GONE);
                text = "退款关闭";
                refundState.setText(text);
                break;
            case "10":
                // 退款完成
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
                // 地址
                llLocation.setVisibility(View.GONE);
                text = "退款成功";
                refundState.setText(text);
                break;
            case "11":
                // 拒绝退款
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
                // 地址
                llLocation.setVisibility(View.GONE);
                if (refundDetailBean.getArbit_id().equals("0")) {
                    //去申请仲裁
                    text = "商家拒绝";
                    tvStatuRight.setText("申请仲裁");
                    tvStatuRight.setVisibility(View.VISIBLE);
                    Reasonsforrejection_ly.setVisibility(View.VISIBLE);
                    applyResonsetitle.setText("拒绝原因");
                    applyResonsevalue.setText(TextUtils.isEmpty(refundDetailBean.getRefuse_reason()) ? "" : refundDetailBean.getRefuse_reason());
                } else {
                    text = "已申请仲裁";
                    tvStatuRight.setText("取消仲裁");
                    tvStatuRight.setVisibility(View.VISIBLE);
                    applyResonsetitle.setText("申请描述");
                    applyResonsevalue.setText(TextUtils.isEmpty(refundDetailBean.getArbit_content()) ? "" : refundDetailBean.getArbit_content());
                    Reasonsforrejection_ly.setVisibility(View.VISIBLE);
                }
                refundState.setText(text);
                break;
        }
        refundState.setText(text);
        refundState.setTextColor(color);
    }


    @OnClick({R.id.cunstomer_ly, R.id.callphone_ly, R.id.tv_statu_center, R.id.tv_statu_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cunstomer_ly:
                // 联系客服
                break;
            case R.id.callphone_ly:
                // 拨打电话
                break;
            case R.id.tv_statu_center:
                String trim = tvStatuCenter.getText().toString().trim();
                switch (trim) {
                    case "取消退货":
                        // 取消退货
                        mPresenter.cancelrefund();
                        break;
                }
                break;
            case R.id.tv_statu_right:
                String trim1 = tvStatuRight.getText().toString().trim();
                switch (trim1) {
                    case "完善物流信息":
                        Bundle bundle = new Bundle();
                        Intent refundintent = new Intent(getActivity(), LogisticsinfoActivity.class);
                        bundle.putSerializable("refundlists", mRefundLists);
                        bundle.putSerializable("refundgooddetail", mRefundDetail);
                        refundintent.putExtras(bundle);
                        startActivity(refundintent);
                        break;
                    case "取消退款":
                        //  取消退款
                        mPresenter.cancelrefund();
                        break;
                    case "申请仲裁":
                        //  申请仲裁
                        Intent intent = new Intent(getActivity(), ApplyForArbitrationActivity.class);
                        intent.putExtra("refundid", mRefundid);
                        startActivity(intent);
                        break;
                    case "取消仲裁":
                        //  取消仲裁
                        mPresenter.cancelarbitr();
                        break;
                }
                break;

        }
    }

    @Override
    public void onSuccess(String message) {
        showShortToast(message);
    }

    @Override
    public void onCancelSuccess(String message) {
        EventBus.getDefault().post(new RefundEvent());
        showShortToast(message);
        removeFragment();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefundEvent event) {
        // 获取退换货详情
        mPresenter.refundrecorddetail();
    }
}

package com.android.p2pflowernet.project.view.fragments.mine.orderflow.pendingship;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.alipay.sdk.app.PayTask;
import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.OrderDetailAdapter;
import com.android.p2pflowernet.project.callback.AdapterLoader;
import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.OrderListBean;
import com.android.p2pflowernet.project.entity.PayAlResultBean;
import com.android.p2pflowernet.project.entity.UnionPayEntity;
import com.android.p2pflowernet.project.event.CheckPayEvent;
import com.android.p2pflowernet.project.event.OrderDeatailRefreshEvent;
import com.android.p2pflowernet.project.event.PayCancleEvent;
import com.android.p2pflowernet.project.event.SetPayPwdEvent;
import com.android.p2pflowernet.project.event.UPpayEvent;
import com.android.p2pflowernet.project.event.UserBanclanceBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.OrderDetailRecyclerView;
import com.android.p2pflowernet.project.view.customview.PayPopupWindow;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.affirm.ArrirmSuccessActivity;
import com.android.p2pflowernet.project.view.fragments.affirm.SetPayPwdActivity;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.check.CheckPayPwdActivity;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.logistics.LogisticsDetailActivity;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.orderitemdetail.OrderItemDetailActivity;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.refund.ApplyforRefundActivity;
import com.android.p2pflowernet.project.view.fragments.trade.pay.PayResult;
import com.google.gson.Gson;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.unionpay.UPPayAssistEx;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.android.p2pflowernet.project.constant.Constants.PARTNER;
import static com.android.p2pflowernet.project.constant.Constants.RSA_PRIVATE;
import static com.android.p2pflowernet.project.constant.Constants.SELLER;


/**
 * @描述:待发货主页
 * @创建人：zhangpeisen
 * @创建时间：2017/10/30 下午5:40
 * @修改人：zhangpeisen
 * @修改时间：2017/10/30 下午5:40
 * @修改备注：
 * @throws
 */
public class PengingShipFragment extends KFragment<IPengingShipmentView, IPendingShipmentPrenter>
        implements IPengingShipmentView, PopupWindow.OnDismissListener {
    @BindView(R.id.orderdetail_recyclerview)
    // 订单详情列表
            OrderDetailRecyclerView orderdetail_recyclerview;
    // 订单详情适配器

    OrderDetailAdapter orderDetailAdapter;

    @BindView(R.id.fragment_pengingShip_pullToRefreshLayout)
    PullToRefreshLayout pullToRefreshLayout;
    @BindView(R.id.layout_empty)
    LinearLayout layout_empty;
    int type = -1;
    // 加载进度条
    private ShapeLoadingDialog shapeLoadingDialog;
    private String orderId = "";
    private WindowManager.LayoutParams params;
    private PayPopupWindow mPop;
    private String paySource = "";
    private String payPrice = "";
    private String body = "";
    private String order_num = "";
    private static final int SDK_PAY_FLAG = 1;
    private Runnable loadMoreAction;
    private boolean isLoad = false;//是否加载更多数据
    private int page = 1;
    private List<OrderListBean.ListsBean> listsBeans;
    private int count = 0;

    /**
     * 支付宝支付异步回调
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    PayAlResultBean payAlResultBean = new Gson().fromJson(resultInfo, PayAlResultBean.class);
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {

                        payPrice = payAlResultBean.getAlipay_trade_app_pay_response().getTotal_amount();
                        //跳转到查看订单详情界面
                        removeFragment();
                        Intent intent = new Intent(getActivity(), ArrirmSuccessActivity.class);
                        intent.putExtra("tag", "b2c");
                        intent.putExtra("message", "支付成功");
                        intent.putExtra("type", "1");
                        intent.putExtra("isOK", true);
                        intent.putExtra("money", payPrice);
                        startActivity(intent);

                    } else {

                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        removeFragment();
                        Intent intent = new Intent(getActivity(), ArrirmSuccessActivity.class);
                        intent.putExtra("tag", "b2c");
                        intent.putExtra("message", "支付失败");
                        intent.putExtra("type", "1");
                        intent.putExtra("isOK", false);
                        intent.putExtra("money", payPrice);
                        startActivity(intent);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    private String banlanceMoney = "";//余额

    public static PengingShipFragment newInstance(int type) {

        //type代表页签，1：全部订单 2：待付款 3：待发货 4：待收货 5：已完成
        Bundle args = new Bundle();
        PengingShipFragment fragment = new PengingShipFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");
    }

    /**
     * 判断是否取消支付
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(PayCancleEvent event) {

        if (mPop != null) {

            //发消息刷新订单列表数据
            EventBus.getDefault().post(new OrderDeatailRefreshEvent());
            showShortToast("支付取消");
        }
    }

    @Override
    public IPendingShipmentPrenter createPresenter() {
        return new IPendingShipmentPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.mine_orderdetail_pengingship_fragment;
    }

    @Override
    public void initData() {

        mPresenter.OrderFlowList();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        initRecycler();

        pullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                isLoad = false;
                page = 1;
                mPresenter.OrderFlowList();
                pullToRefreshLayout.finishRefresh();
            }

            @Override
            public void loadMore() {
                if (orderDetailAdapter != null) {
                    isLoad = true;
                    page += 1;
                    mPresenter.OrderFlowList();
                }
                pullToRefreshLayout.finishLoadMore();
            }
        });
    }

    private void initRecycler() {

        listsBeans = new ArrayList<>();
        orderDetailAdapter = new OrderDetailAdapter();
        orderdetail_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderDetailAdapter.attachRecyclerView(orderdetail_recyclerview);
        orderdetail_recyclerview.setHasFixedSize(false);

        orderDetailAdapter.setOnItemClickListener(new AdapterLoader.OnItemClickListener<OrderListBean.ListsBean>() {
            @Override
            public void onItemClick(View itemView, int position, OrderListBean.ListsBean item) {

                Intent intent = new Intent(getActivity(), OrderItemDetailActivity.class);
                intent.putExtra("order_id", listsBeans.get(position).getId());
                intent.putExtra("state", listsBeans.get(position).getState());
                intent.putExtra("isreturn", listsBeans.get(position).getIs_return());
                intent.putExtra("orderlists", item);
                startActivity(intent);
            }
        });
        orderDetailAdapter.setButtonLeftClickListener(new OrderDetailAdapter.ButtonLeftClickListener() {
            @Override
            public void onClick(int position, OrderListBean.ListsBean data) {
                String text = null;
                switch (data.getState()) {
                    case "1":
                        text = "";
                        break;
                }
                showShortToast("按钮" + text);
            }
        });

        //订单状态0-待付款 1-待发货 2-待收货 3-已收货  10-交易完成 11-交易关闭（会员取消）
        //12-交易关闭（客服取消） 13-交易关闭（支付超时自动关闭）
        orderDetailAdapter.setButtonCenterClickListener(new OrderDetailAdapter.ButtonCenterClickListener() {
            @Override
            public void onClick(int position, OrderListBean.ListsBean data) {
                String text = null;
                switch (data.getState()) {
                    case "2":
                        text = "查看物流";

                        Intent intent = new Intent(getActivity(), LogisticsDetailActivity.class);
                        startActivity(intent);

                        break;
                    case "11":
                        text = "删除订单";
                        orderId = data.getId();
                        mPresenter.delOrder();

                        break;
                    case "12":

                        text = "删除订单";
                        orderId = data.getId();
                        mPresenter.delOrder();

                        break;
                    case "13":
                        text = "删除订单";
                        orderId = data.getId();
                        mPresenter.delOrder();
                        break;
                    case "0"://代付款取消订单

                        text = "取消订单";
                        orderId = data.getId();
                        mPresenter.cancleOrder();

                        break;
                }
            }
        });
        orderDetailAdapter.setButtonRightClickListener(new OrderDetailAdapter.ButtonRightClickListener() {
            @Override
            public void onClick(int position, OrderListBean.ListsBean data) {
                String text = null;
                switch (data.getState()) {
                    case "0":

                        text = "付款";
                        payPrice = data.getPay_amount();
                        order_num = data.getOrder_num();
                        body = data.getOrder_num() + "," + "2";
                        mPresenter.getBanlce();


                        break;
                    case "2":
                        text = "确认收货";
                        orderId = data.getId();
                        mPresenter.AffirmOrder();

                        break;
                    case "3":

                        text = "查看详情";
                        Intent intent = new Intent(getActivity(), OrderItemDetailActivity.class);
                        intent.putExtra("order_id", data.getId());
                        intent.putExtra("state", data.getState());
                        intent.putExtra("isreturn", data.getIs_return());
                        intent.putExtra("orderlists", data);
                        startActivity(intent);

                        break;
                    case "4"://删除订单
                        text = "删除订单";
                        break;
                    case "11":
                        text = "删除订单";
                        orderId = data.getId();
                        mPresenter.delOrder();
                        break;
                    case "12":
                        text = "删除订单";
                        orderId = data.getId();
                        mPresenter.delOrder();

                        break;
                    case "13":
                        text = "删除订单";
                        orderId = data.getId();
                        mPresenter.delOrder();

                        break;
                    case "1"://申请退款

                        String is_return = data.getIs_return();
                        if (is_return.equals("0")) {

                            text = "申请退款";
                            // 待退款
                            Intent refundintent = new Intent(getActivity(), ApplyforRefundActivity.class);
                            Bundle bundle = new Bundle();
                            // 订单商品表id
                            bundle.putString("ogid", "0");
                            bundle.putString("ordernum", data.getOrder_num());
                            bundle.putSerializable("orderlists", data);
                            refundintent.putExtras(bundle);
                            startActivity(refundintent);
                        }

                        break;
                    case "10"://查看详情

                        text = "查看详情";
                        intent = new Intent(getActivity(), OrderItemDetailActivity.class);
                        intent.putExtra("order_id", data.getId());
                        intent.putExtra("state", data.getState());
                        intent.putExtra("isreturn", data.getIs_return());
                        intent.putExtra("orderlists", data);
                        startActivity(intent);

                        break;
                }
            }
        });
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public int getSeltype() {

        return type;
    }

    @Override
    public int getOrderId() {

        return Integer.parseInt(orderId);
    }

    @Override
    public void onError(String errorMsg) {

        showShortToast(errorMsg);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OrderDeatailRefreshEvent userInfoEvent) {

        mPresenter.OrderFlowList();
    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onSuccess(OrderListBean orderListBean) {
        if (orderListBean == null) {
            return;
        }

        List<OrderListBean.ListsBean> lists = orderListBean.getLists();
        if (isLoad) {
            //加载更多
            if (null != lists && lists.size() > 0) {
                listsBeans.addAll(lists);
            } else {
                showShortToast("没有更多数据了！");
            }
        } else {
            //刷新
            if (null != lists ) {
                listsBeans.clear();
                listsBeans.addAll(lists);
            }
        }

        if (listsBeans.isEmpty()) {

            layout_empty.setVisibility(View.VISIBLE);

        } else {

            layout_empty.setVisibility(View.GONE);
        }

        orderDetailAdapter.setList(listsBeans);
        count = listsBeans.size();
        orderDetailAdapter.setTotalCount(listsBeans.size());
    }

    @Override
    public void showDialog() {

        if (shapeLoadingDialog != null) {

            shapeLoadingDialog.show();
        }
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog != null)
            shapeLoadingDialog.dismiss();
    }

    @Override
    public void onSuccessCancleOrder(String message) {

        showShortToast("取消成功");
        mPresenter.OrderFlowList();
    }

    @Override
    public void onSuccessAffirm(String message) {

        showShortToast("确认收货成功");
        mPresenter.OrderFlowList();

        if (orderDetailAdapter != null) {
            orderDetailAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取余额成功
     *
     * @param data
     */
    @Override
    public void SuccessgetBanlance(UserBanclanceBean data) {

        if (data != null) {

            banlanceMoney = data.getMoney();

            //微信支付的点击事件
            View.OnClickListener wxOnclickListner = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Utils.isFastDoubleClick()) {

                        return;
                    }

                    removeFragment();
                    onDismiss();
                }
            };

            //支付宝的点击事件
            View.OnClickListener zfbxOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Utils.isFastDoubleClick()) {

                        return;
                    }
                    aliPay();
                }
            };

            //余额支付的点击事件
            View.OnClickListener yeOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Utils.isFastDoubleClick()) {

                        return;
                    }

                    mPresenter.checkPayPwd();
                    onDismiss();
                }
            };

            mPop = new PayPopupWindow(getActivity(), payPrice, banlanceMoney, wxOnclickListner,
                    zfbxOnClickListener, yeOnClickListener, new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Utils.isFastDoubleClick()) {

                        return;
                    }

                    // 调用银联支付接口
                    paySource = "2";
                    mPresenter.AlipayPresenter();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDismiss();
                    showShortToast("支付取消");
                }
            });

            mPop.showAtLocation(orderdetail_recyclerview, Gravity.BOTTOM | Gravity.CENTER, 0, 0);
            params = getActivity().getWindow().getAttributes();
            //当弹出Popupwindow时，背景变半透明
            params.alpha = 0.6f;
            getActivity().getWindow().setAttributes(params);
            //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
            mPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {

                    params = getActivity().getWindow().getAttributes();
                    params.alpha = 1f;
                    getActivity().getWindow().setAttributes(params);
                }
            });
        }
    }

    //支付宝支付
    private void aliPay() {

        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {

            new AlertDialog.Builder(getActivity()).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {

                        }

                    }).show();
            return;
        }

        if (TextUtils.isEmpty(order_num) && order_num.equals("")) {
            return;
        }

        paySource = "1";
        mPresenter.AlipayPresenter();
        onDismiss();
    }

    /**
     * 检测密码支付成功
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(CheckPayEvent event) {
        String invoice = event.getStr();

        if (invoice.equals("4")) {

            if (TextUtils.isEmpty(order_num) && order_num.equals("")) {
                return;
            }

            mPresenter.yEpay();
        }
    }


    /**
     * 设置支付密码成功的回调
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(SetPayPwdEvent event) {
        String invoice = event.getStr();

        if (invoice.equals("4")) {

            //检测输入的支付密码
            Intent intent = new Intent(getActivity(), CheckPayPwdActivity.class);
            intent.putExtra("tag", "4");
            startActivity(intent);
        }
    }

    @Override
    public void onSuccessed(String message) {

        showShortToast(message);
    }

    /**
     * 检测是否设置过支付密码
     *
     * @param data
     */
    @Override
    public void onSuccessCheck(CheckPwdBean data) {
        if (data != null) {

            //0: 否，1： 是
            int is_set = data.getIs_set();
            if (is_set == 0) {

                // 去设置支付密码
                Intent intent = new Intent(getActivity(), SetPayPwdActivity.class);
                intent.putExtra("tag", "4");
                startActivity(intent);

            } else {

                //检测输入的支付密码
                Intent intent = new Intent(getActivity(), CheckPayPwdActivity.class);
                intent.putExtra("tag", "4");
                startActivity(intent);

            }
        }
    }

    /**
     * 支付宝支付成功
     *
     * @param data
     */
    @Override
    public void onSuccessAli(final AppTradeBean data) {

        if (data != null) {

            if (paySource.equals("1")) {//支付宝支付

                //异步请求支付宝付款
                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {

                        PayTask alipay = new PayTask(getActivity());
                        Map<String, String> result = alipay.payV2(data.getList().toString(), true);

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

                Thread payThread = new Thread(payRunnable);
                payThread.start();

            } else {//银联支付

                UPPayAssistEx.startPay(getActivity(), null, null, data.getList(), Constants.UNIONPAYMODE);
                onDismiss();
            }
        }
    }

    @Override
    public String getPaySource() {
        return paySource;
    }

    @Override
    public String getOrderNum() {
        return order_num;
    }

    @Override
    public String getBody() {
        return body;
    }

    /***
     * 银联支付成功
     * @param unionPayResult
     */
    @Override
    public void UnionPaySuccess(UnionPayEntity unionPayResult) {

    }

    /**
     * 银联支付回调
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(UPpayEvent event) {
        Intent data = event.getData();
        int requestCode = event.getRequestCode();
        int resultCode = event.getResultCode();

        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/
        if (data == null) {
            return;
        }
        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            msg = "支付成功！";
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
        }

        //跳转到查看订单详情界面
        removeFragment();
        Intent intent = new Intent(getActivity(), ArrirmSuccessActivity.class);
        intent.putExtra("tag", "b2c");
        intent.putExtra("type", "2");//支付方式 0余额 1支付宝 2银联
        if (msg.equals("支付成功！")) {
            intent.putExtra("isOK", true);
        } else if (msg.equals("支付失败！")) {
            intent.putExtra("isOK", false);
        } else if (msg.equals("用户取消了支付")) {
            intent.putExtra("isOK", false);
        }
        intent.putExtra("message", "支付成功");
        intent.putExtra("money", payPrice);
        startActivity(intent);
    }

    /**
     * 余额支付成功
     *
     * @param data
     */
    @Override
    public void onSuccessYe(BanlanceBean data) {

        if (data != null) {

            removeFragment();
            Intent intent = new Intent(getActivity(), ArrirmSuccessActivity.class);
            intent.putExtra("tag", "b2c");
            intent.putExtra("message", "支付成功");
            intent.putExtra("type", "0");
            intent.putExtra("isOK", true);
            intent.putExtra("money", payPrice);
            startActivity(intent);
        }

    }

    @Override
    public String getSalePrice() {
        return payPrice;
    }

    @Override
    public void onDelOrderSuccess(String message) {

        showShortToast("删除成功");
        EventBus.getDefault().post(new OrderDeatailRefreshEvent());
    }


    /**
     * 支付类型 1，O2O  2，B2c 3,购买合伙人 4，代理人追加，5 代理人购买资质
     *
     * @return
     */
    @Override
    public String getType() {
        return "2";
    }

    /**
     * 1为父2为否（在type为1,2时必填）
     * 提交订单1 我的订单2
     *
     * @return
     */
    @Override
    public String is_father_order() {
        return "2";
    }

    /***
     * 余额支付失败回调
     * @param message
     */
    @Override
    public void onPayYeError(String message) {

        removeFragment();
        Intent intent = new Intent(getActivity(), ArrirmSuccessActivity.class);
        intent.putExtra("tag", "b2c");
        intent.putExtra("message", message);
        intent.putExtra("type", "0");
        intent.putExtra("isOK", false);
        intent.putExtra("money", payPrice);
        startActivity(intent);

    }


    @Override
    public void onDismiss() {

        if (mPop != null) {
            mPop.dismiss();
        }
    }
}

package com.android.p2pflowernet.project.o2omain.fragment.mine.take_out.all_order;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.alipay.sdk.app.PayTask;
import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.OrderTakeOutAdapter;
import com.android.p2pflowernet.project.callback.AdapterLoader;
import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.EncoreBean;
import com.android.p2pflowernet.project.entity.O2oIndexBean;
import com.android.p2pflowernet.project.entity.PayAlResultBean;
import com.android.p2pflowernet.project.entity.ShopCart;
import com.android.p2pflowernet.project.entity.TakeOutBean;
import com.android.p2pflowernet.project.entity.UnionPayEntity;
import com.android.p2pflowernet.project.event.CheckPayEvent;
import com.android.p2pflowernet.project.event.RefreshOrderListEvent;
import com.android.p2pflowernet.project.event.SetPayPwdEvent;
import com.android.p2pflowernet.project.event.UPpayEvent;
import com.android.p2pflowernet.project.event.UserBanclanceBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.activity.StoreDetailActivity;
import com.android.p2pflowernet.project.o2omain.fragment.mine.take_out.takefulldetail.TakeFullDetailActivity;
import com.android.p2pflowernet.project.o2omain.fragment.mine.take_out.to_evaluate.TakeOutToEvaluateActivity;
import com.android.p2pflowernet.project.o2omain.fragment.refund.RefundActivity;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.PayPopupWindow;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.affirm.ArrirmSuccessActivity;
import com.android.p2pflowernet.project.view.fragments.affirm.SetPayPwdActivity;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.check.CheckPayPwdActivity;
import com.android.p2pflowernet.project.view.fragments.trade.pay.PayResult;
import com.google.gson.Gson;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.unionpay.UPPayAssistEx;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.android.p2pflowernet.project.constant.Constants.PARTNER;
import static com.android.p2pflowernet.project.constant.Constants.RSA_PRIVATE;
import static com.android.p2pflowernet.project.constant.Constants.SELLER;

/**
 * Created by caishen on 2018/1/5.
 * by--外卖订单列表数据
 */

public class TakeOutOrderFragment extends KFragment<ITakeOutOrderView, ITakeOutOrderPrenter> implements ITakeOutOrderView,
        PopupWindow.OnDismissListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.pull_refresh)
    PullToRefreshLayout pullRefresh;
    @BindView(R.id.layout_empty)
    LinearLayout layout_empty;
    private int id = 1;
    private int page = 1;
    private ShapeLoadingDialog shapeLoadingDialog;
    private boolean isLoad = false;//是否加载更多数据
    private OrderTakeOutAdapter orderTakeOutAdapter;
    private int count = 0;
    private String payPrice;
    private String order_num;
    private String body;
    private String banlanceMoney;
    private String merch_id;
    private PopupWindow mPop;
    private WindowManager.LayoutParams params;
    private String paySource;
    private static final int SDK_PAY_FLAG = 1;
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
                        intent.putExtra("tag", "o2o");
                        intent.putExtra("message", "支付成功");
                        intent.putExtra("type", "1");
                        intent.putExtra("isOK", true);
                        intent.putExtra("money", payPrice);
                        startActivity(intent);

                    } else {

                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        removeFragment();
                        Intent intent = new Intent(getActivity(), ArrirmSuccessActivity.class);
                        intent.putExtra("tag", "o2o");
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

    @Override
    public ITakeOutOrderPrenter createPresenter() {
        return new ITakeOutOrderPrenter();
    }

    public static TakeOutOrderFragment newIntence(int id) {

        TakeOutOrderFragment takeOutOrderFragment = new TakeOutOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        takeOutOrderFragment.setArguments(bundle);
        return takeOutOrderFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        id = arguments.getInt("id");
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_order_take_out_all;
    }

    @Override
    public void initData() {

        mPresenter.getTakeOut();
    }

    private int convertDpToPixel(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return (int) (dp * displayMetrics.density);
    }


    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .delay(5000)
                .loadText("加载中...")
                .build();

        //添加分割线
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = 0;
                // top bottom left right 对应的值应该是dpi 而不是dp  dpi根据不同手机而不同
                int i = parent.getChildLayoutPosition(view) % 1;//每行1个
                switch (i) {
                    case 0://第一个
                        outRect.left = convertDpToPixel(0);
                        outRect.right = convertDpToPixel(0);
                        outRect.bottom = convertDpToPixel(12);
                        break;
                }
            }
        });

        initData();

        pullRefresh.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {

                isLoad = true;
                page = 1;
                mPresenter.getTakeOut();
                pullRefresh.finishRefresh();
            }

            @Override
            public void loadMore() {

                if (orderTakeOutAdapter != null) {

                    if (count >= orderTakeOutAdapter.getItemCount()) {

                        isLoad = true;
                        page += 1;
                        mPresenter.getTakeOut();

                    } else {

                        showShortToast("没有更多数据了！");
                    }
                }
                pullRefresh.finishLoadMore();
            }
        });
    }

    @Override
    public String getSate() {

        return String.valueOf(id);
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
    public int getPage() {
        return page;
    }

    @Override
    public void hideDialog() {
        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void onSuccessData(TakeOutBean data) {

        if (data != null) {

            final List<TakeOutBean.ListBean> list = data.getList();

            count = list.size();

            if (!isLoad) {

                if (list != null && list.size() > 0) {

                    layout_empty.setVisibility(View.GONE);

                    //设置适配器
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    orderTakeOutAdapter = new OrderTakeOutAdapter(getActivity(),id);
                    orderTakeOutAdapter.attachRecyclerView(recyclerView);
                    orderTakeOutAdapter.setList(list);

                    //设置按钮的点击状态
                    orderTakeOutAdapter.setOnLeftButtonStateClickLitener(new OrderTakeOutAdapter.OnLeftButtonStateClickLitener() {
                        @Override
                        public void leftButtonStateLitener(View view, int position) {

                            //tag值 0-取消订单 1-去支付 2再来一单 3-确认收货 4-去评价 5-取消退款
                            switch (view.getTag().toString()) {
                                case "0"://取消订单

                                    order_num = list.get(position).getOrder_num();
                                    mPresenter.cancelOrder();

                                    break;
                                case "1"://去支付

                                    payPrice = list.get(position).getPay_amount();
                                    order_num = list.get(position).getOrder_num();
                                    body = list.get(position).getOrder_num() + "," + "2";
                                    pay(payPrice, order_num, body);

                                    break;
                                case "2"://再来一单
                                    //1.根据当前订单号请求数据
                                    merch_id = list.get(position).getMerch_id();
                                    order_num = list.get(position).getOrder_num();
                                    mPresenter.againOneOrder();

                                    break;
                                case "3"://确认收货
                                    order_num = list.get(position).getOrder_num();
                                    mPresenter.confirmGoods();

                                    break;
                                case "4"://去评价
                                    order_num = list.get(position).getOrder_num();
                                    Intent evaluateIntent = new Intent(getActivity(), TakeOutToEvaluateActivity.class);
                                    evaluateIntent.putExtra("order_num",order_num);
                                    startActivity(evaluateIntent);

                                    break;
                                case "5"://取消退款
                                    order_num =list.get(position).getOrder_num();
                                    mPresenter.cancelRefund();

                                    break;
                                case "6"://申请退款
                                    order_num =list.get(position).getOrder_num();
                                    Intent refundIntent = new Intent(getActivity(), RefundActivity.class);
                                    Bundle bundle = new Bundle();
                                    List<TakeOutBean.ListBean.GoodsBean> goodsBeans = list.get(position).getGoods();
                                    bundle.putSerializable("goods", (Serializable) goodsBeans);
                                    refundIntent.putExtras(bundle);
                                    refundIntent.putExtra("pay_channel",list.get(position).getPay_channel());
                                    refundIntent.putExtra("manger_name",list.get(position).getManager_name());
                                    refundIntent.putExtra("manger_img",list.get(position).getFile_path());
                                    refundIntent.putExtra("order_num",order_num);
                                    refundIntent.putExtra("pay_amount",list.get(position).getPay_amount());
                                    Log.e("TakeOutOrder=====", list.get(position).getPay_channel() + "" );
                                    startActivity(refundIntent);
                                    break;
                            }
                        }
                    });


                    orderTakeOutAdapter.setOnRightButtonStateClickLitener(new OrderTakeOutAdapter.OnRightButtonStateClickLitener() {
                        @Override
                        public void rightButtonStateLitener(View view, int position) {
                            //tag值 0-取消订单 1-去支付 2再来一单 3-确认收货 4-去评价 5-取消退款
                            switch (view.getTag().toString()) {
                                case "0"://取消订单

                                    order_num = list.get(position).getOrder_num();
                                    mPresenter.cancelOrder();

                                    break;
                                case "1"://去支付

                                    payPrice = list.get(position).getPay_amount();
                                    order_num = list.get(position).getOrder_num();
                                    body = list.get(position).getOrder_num() + "," + "2";
                                    pay(payPrice, order_num, body);

                                    break;
                                case "2"://再来一单

                                    //1.根据当前订单号请求数据
                                    merch_id = list.get(position).getMerch_id();
                                    order_num = list.get(position).getOrder_num();
                                    mPresenter.againOneOrder();

                                    break;
                                case "3"://确认收货
                                    order_num = list.get(position).getOrder_num();
                                    mPresenter.confirmGoods();

                                    break;
                                case "4"://去评价
                                    order_num = list.get(position).getOrder_num();
                                    Intent evaluateIntent = new Intent(getActivity(), TakeOutToEvaluateActivity.class);
                                    evaluateIntent.putExtra("order_num",order_num);
                                    startActivity(evaluateIntent);

                                    break;
                                case "5"://取消退款
                                    order_num =list.get(position).getOrder_num();
                                    mPresenter.cancelRefund();
                                    break;
                                case "6"://申请退款
                                    order_num =list.get(position).getOrder_num();
                                    Intent refundIntent = new Intent(getActivity(), RefundActivity.class);
                                    Bundle bundle = new Bundle();
                                    List<TakeOutBean.ListBean.GoodsBean> goodsBeans = list.get(position).getGoods();
                                    bundle.putSerializable("goods", (Serializable) goodsBeans);
                                    refundIntent.putExtras(bundle);
                                    refundIntent.putExtra("order_num",order_num);
                                    refundIntent.putExtra("pay_channel",list.get(position).getPay_channel());
                                    refundIntent.putExtra("manger_name",list.get(position).getManager_name());
                                    refundIntent.putExtra("manger_img",list.get(position).getFile_path());
                                    refundIntent.putExtra("pay_amount",list.get(position).getPay_amount());
                                    Log.e("TakeOutOrder=====", list.get(position).getPay_channel() + "" );
                                    startActivity(refundIntent);
                                    break;
                            }
                        }
                    });

                    orderTakeOutAdapter.setOnItemClickListener(new AdapterLoader.OnItemClickListener<TakeOutBean.ListBean>() {
                        @Override
                        public void onItemClick(View itemView, int position, TakeOutBean.ListBean item) {

                            Intent intent = new Intent(getActivity(), TakeFullDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("order_num", list.get(position).getOrder_num());
                            bundle.putString("countdown_time",list.get(position).getCountdown_time());
                            bundle.putString("refund_id",list.get(position).getRefund_id());
                            bundle.putInt("id",id);
                            List<TakeOutBean.ListBean.GoodsBean> goodsBeans = list.get(position).getGoods();
                            bundle.putSerializable("goods", (Serializable) goodsBeans);
                            intent.putExtras(bundle);
                            startActivity(intent);
//                            TextView tvState = (TextView) itemView.findViewById(R.id.tv_state);
//                            switch (tvState.getText().toString()) {
//                                case "待付款":
//                                    Intent dfkIntent = new Intent(getActivity(), TakeFullDetailActivity.class);
//                                    Bundle dfkbundle = new Bundle();
//                                    dfkbundle.putString("state", "待付款");
//                                    dfkIntent.putExtras(dfkbundle);
//                                    startActivity(dfkIntent);
//                                    break;
//                            }
                        }
                    });

                } else {

                    layout_empty.setVisibility(View.VISIBLE);
                }

            } else {

                isLoad = false;
                if (list.size() > 0) {

                    list.addAll(list);
                    orderTakeOutAdapter.notifyDataSetChanged();

                } else {

                    showShortToast("没有更多数据了！");
                }
            }
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

            mPop.showAtLocation(recyclerView, Gravity.BOTTOM | Gravity.CENTER, 0, 0);
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

    @Override
    public void UnionPaySuccess(UnionPayEntity unionPayResult) {

    }

    @Override
    public String getOrderNum() {
        return order_num;
    }

    @Override
    public String getPaySource() {
        return paySource;
    }

    /**
     * 支付类型 1，O2O  2，B2c 3,购买合伙人 4，代理人追加，5 代理人购买资质
     *
     * @return
     */
    @Override
    public String getType() {
        return "1";
    }

    /**
     * 1为父2为否（在type为1,2时必填）
     * 提交订单1 我的订单2
     *
     * @return
     */
    @Override
    public String is_father_order() {
        return "1";
    }

    /**
     * 支付宝支付请求回调
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


    @Override
    public void onSuccessed(String message) {

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
            intent.putExtra("tag", "o2o");
            intent.putExtra("message", "支付成功");
            intent.putExtra("type", "0");
            intent.putExtra("isOK", true);
            intent.putExtra("money", payPrice);
            startActivity(intent);
        }

    }

    /**
     * 余额支付失败
     *
     * @param message
     */
    @Override
    public void onPayYeError(String message) {

        removeFragment();
        Intent intent = new Intent(getActivity(), ArrirmSuccessActivity.class);
        intent.putExtra("tag", "o2o");
        intent.putExtra("message", message);
        intent.putExtra("type", "0");
        intent.putExtra("isOK", false);
        intent.putExtra("money", payPrice);
        startActivity(intent);
    }

    @Override
    public String getSalePrice() {
        return payPrice;
    }

    @Override
    public void onSuccessCancelOrder(String message) {
        //取消订单成功刷新页面
        page = 1;
        mPresenter.getTakeOut();
    }

    @Override
    public void onSuccessEncore(EncoreBean data) {
        if (data != null) {
            //再来一单
            String str ="";
            ShopCart shopCart = new ShopCart();
            List<EncoreBean.ListsBean> listsBeans = data.getLists();

            for (int i = 0; i < listsBeans.size(); i++) {
                EncoreBean.ListsBean listsBean = listsBeans.get(i);
                int is_exis = listsBean.getIs_exis();
                if (is_exis == 1) {
                    O2oIndexBean.ListsBean.GoodsListBean bean = new O2oIndexBean.ListsBean.GoodsListBean();
                    bean.setId(listsBean.getId() + "");
                    bean.setPrice(listsBean.getPrice());
                    bean.setName(listsBean.getName());
                    bean.setStock(listsBean.getStock() + "");
                    for (int j = 0; j < listsBean.getNum(); j++) {
                        shopCart.addShoppingSingle(bean);
                    }
                }else{
                    str += listsBean.getName() + "、";
                }
            }

            Intent intent = new Intent(getActivity(),StoreDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("flag","1");
            bundle.putString("merch_id",merch_id);
            bundle.putSerializable("shopCart",shopCart);
            intent.putExtras(bundle);
//            EventBus.getDefault().post(new MessageEvent(shopCart.getShoppingAccount(), shopCart.getShoppingTotalPrice(), shopCart));
            startActivity(intent);
            if (!str.equals("")) {
                showShortToast(str.substring(0,str.length()-2) + "已下架");
            }

//            EventBus.getDefault().post(new TakeOutOrderEvent("1"));
        }
    }

    @Override
    public void onSuccessCancelRefund(String data) {
        //取消退款成功刷新页面
        page = 1;
        mPresenter.getTakeOut();
    }

    @Override
    public void onSuccessConfirmGoods(String data) {
        //确认收货成功回调
        page = 1;
        mPresenter.getTakeOut();
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
        intent.putExtra("tag", "o2o");
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

    //去支付
    private void pay(String payPrice, String order_num, String body) {

        mPresenter.getBanlce();
    }

    @Override
    public void onDismiss() {

        if (mPop != null) {
            mPop.dismiss();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(RefreshOrderListEvent event){
        //评价完成刷新页面
        page = 1;
        mPresenter.getTakeOut();
    }
}

package com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.alipay.sdk.app.PayTask;
import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.callback.AdapterLoader;
import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.PayAlResultBean;
import com.android.p2pflowernet.project.entity.TakeOutOrderGroupBean;
import com.android.p2pflowernet.project.event.CheckPayEvent;
import com.android.p2pflowernet.project.event.EvaluateGroupEvent;
import com.android.p2pflowernet.project.event.SetPayPwdEvent;
import com.android.p2pflowernet.project.event.UPpayEvent;
import com.android.p2pflowernet.project.event.UserBanclanceBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out.detail.TakeOutGroupDetailActivity;
import com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out.fulldetail.GroupFullDetailActivity;
import com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out.to_evaluate.GroupTakeOutToEvaluateActivity;
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

import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.android.p2pflowernet.project.constant.Constants.PARTNER;
import static com.android.p2pflowernet.project.constant.Constants.RSA_PRIVATE;
import static com.android.p2pflowernet.project.constant.Constants.SELLER;

/**
 * Created by caishen on 2018/1/6.
 * by--团购订单
 */

public class TakeOutOrderGroupFragment extends KFragment<ITakeOutOrderGroupView, ITakeOutOrderGroupPrenter> implements ITakeOutOrderGroupView, PopupWindow.OnDismissListener {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.pull_refresh)
    PullToRefreshLayout pullRefresh;
    @BindView(R.id.layout_empty)
    LinearLayout layout_empty;
    private boolean isLoad = false;
    private List<TakeOutOrderGroupBean.ListBean> list;
    private OrderTakeOutGroupAdapter orderTakeOutAdapter;
    private int page = 1;
    private String state = "1";//1-全部 0待付款 2-待使用 3-已使用 4-已退款
    private ShapeLoadingDialog shapeLoadingDialog;
    private int count = 10;
    private String order_num;//订单号
    private String payPrice;//价格
    private String body;
    private String paySource;
    private String banlanceMoney;
    private PopupWindow mPop;
    private static final int SDK_PAY_FLAG = 1;
    private WindowManager.LayoutParams params;

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
    public ITakeOutOrderGroupPrenter createPresenter() {
        return new ITakeOutOrderGroupPrenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_order_take_out_g;
    }

    @Override
    public void initData() {

        mPresenter.getTakeOutGroup();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state = getArguments().getString("state");
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .delay(5000)
                .loadText("加载中...")
                .build();

        initData();

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

        pullRefresh.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {

                isLoad = true;
                page = 1;
                mPresenter.getTakeOutGroup();
                pullRefresh.finishRefresh();
            }

            @Override
            public void loadMore() {

                if (orderTakeOutAdapter != null) {

                    if (count >= orderTakeOutAdapter.getItemCount()) {

                        isLoad = true;
                        page += 1;
                        mPresenter.getTakeOutGroup();

                    } else {

                        showShortToast("没有更多数据了！");
                    }
                }
                pullRefresh.finishLoadMore();
            }
        });

    }

    private int convertDpToPixel(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return (int) (dp * displayMetrics.density);
    }

    public static Fragment newIntence(int i) {

        TakeOutOrderGroupFragment takeOutOrderGroupFragment = new TakeOutOrderGroupFragment();
        Bundle bundle = new Bundle();
        bundle.putString("state", String.valueOf(i));
        takeOutOrderGroupFragment.setArguments(bundle);
        return takeOutOrderGroupFragment;
    }

    @Override
    public void onError(String s) {

        showShortToast(s);
    }

    @Override
    public String getstate() {
        return state;
    }

    @Override
    public int getpages() {
        return page;
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
    public void onSuccessData(final TakeOutOrderGroupBean data) {

        if (data != null) {

            list = data.getList();
            count = list.size();

            if (!isLoad) {

                if (list != null && list.size() > 0) {

                    layout_empty.setVisibility(View.GONE);

                    //设置适配器
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    orderTakeOutAdapter = new OrderTakeOutGroupAdapter(getActivity());
                    orderTakeOutAdapter.attachRecyclerView(recyclerView);
                    orderTakeOutAdapter.setList(list);


                    //设置点击事件
                    orderTakeOutAdapter.setOnItemClickListener(new AdapterLoader.OnItemClickListener<TakeOutOrderGroupBean.ListBean>() {
                        @Override
                        public void onItemClick(View itemView, int position, TakeOutOrderGroupBean.ListBean item) {
                            Intent dfkIntent = new Intent(getActivity(), GroupFullDetailActivity.class);
                            Bundle dfkbundle = new Bundle();
                            dfkbundle.putString("order_num",list.get(position).getOrder_num());
                            dfkbundle.putSerializable("group_listBean",list.get(position));
                            dfkIntent.putExtras(dfkbundle);
                            startActivity(dfkIntent);
                        }
                    });


                    //设置按钮状态的点击事件
                    orderTakeOutAdapter.setOnLeftButtonStateClickLitener(new OrderTakeOutGroupAdapter.OnLeftButtonStateClickLitener() {
                        @Override
                        public void leftButtonStateLitener(View view, int position) {

                            switch (view.getTag().toString()) {
                                case "1"://申请退款

                                    order_num = list.get(position).getOrder_num();
                                    mPresenter.refundGroupOrder();

                                    break;
                                case "2"://查看劵码
                                    Intent intent = new Intent(getActivity(), TakeOutGroupDetailActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("good", list.get(position));
                                    intent.putExtras(bundle);
                                    startActivity(intent);

                                    break;
                                case "3"://取消订单

                                    order_num = list.get(position).getOrder_num();
                                    mPresenter.cancelGroupOrder();

                                    break;
                                case "4"://去支付
                                    payPrice = list.get(position).getOrder_amount();
                                    order_num = list.get(position).getOrder_num();
                                    body = list.get(position).getOrder_num() + "," + "2";
                                    pay(payPrice, order_num, body);

                                    break;
                                case "5"://去评价
                                    order_num = list.get(position).getOrder_num();
                                    Intent evaluateIntent = new Intent(getActivity(), GroupTakeOutToEvaluateActivity.class);
                                    Bundle evaluateBundle = new Bundle();
                                    evaluateBundle.putSerializable("good",list.get(position));
                                    evaluateIntent.putExtras(evaluateBundle);
                                    startActivity(evaluateIntent);
                                    break;
                            }
                        }
                    });

                    orderTakeOutAdapter.setOnRightButtonStateClickLitener(new OrderTakeOutGroupAdapter.OnRightButtonStateClickLitener() {
                        @Override
                        public void rightButtonStateLitener(View view, int position) {

                            switch (view.getTag().toString()) {
                                case "1"://申请退款

                                    order_num = list.get(position).getOrder_num();
                                    mPresenter.refundGroupOrder();

                                    break;
                                case "2"://查看劵码

                                    Intent intent = new Intent(getActivity(), TakeOutGroupDetailActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("good", list.get(position));
                                    intent.putExtras(bundle);
                                    startActivity(intent);

                                    break;
                                case "3"://取消订单

                                    order_num = list.get(position).getOrder_num();
                                    mPresenter.cancelGroupOrder();

                                    break;
                                case "4"://去支付
                                    payPrice = list.get(position).getPrice();
                                    order_num = list.get(position).getOrder_num();
                                    body = list.get(position).getOrder_num() + "," + "2";
                                    pay(payPrice, order_num, body);

                                    break;
                                case "5"://去评价

                                    order_num = list.get(position).getOrder_num();
                                    Intent evaluateIntent = new Intent(getActivity(), GroupTakeOutToEvaluateActivity.class);
                                    Bundle evaluateBundle = new Bundle();
                                    evaluateBundle.putSerializable("good",list.get(position));
                                    evaluateIntent.putExtras(evaluateBundle);
                                    startActivity(evaluateIntent);

                                    break;

                            }
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

    //去支付
    private void pay(String payPrice, String order_num, String body) {
        mPresenter.getBanlce();
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
    public String getSalePrice() {
        return payPrice;
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

    /**
     * 支付类型 1，O2O  2，B2c 3,购买合伙人 4，代理人追加，5 代理人购买资质 6 团购
     *
     * @return
     */
    @Override
    public String getType() {
        return "6";
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
    public String getPaySource() {
        return paySource;
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
     * 1为父2为否（在type为1,2时必填）
     * 提交订单1 我的订单2
     *
     * @return
     */
    @Override
    public String is_father_order() {
        return "2";
    }

    @Override
    public String getOrderNumber() {
        return order_num;
    }

    @Override
    public void onSuccessCancelGroupOrder(String str) {
        //取消团购订单成功
        showShortToast(str);
        //刷新页面
        page = 1;
        mPresenter.cancelGroupOrder();
    }

    @Override
    public void onSuccessRefundGroupOrder(String string) {
        //团购退订单成功
        showShortToast(string);
        //刷新页面
        page = 1;
        mPresenter.cancelGroupOrder();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EvaluateGroupEvent event) {
        //刷新页面
        page = 1;
        mPresenter.cancelGroupOrder();
    }

    @Override
    public void onDismiss() {
        if (mPop != null) {
            mPop.dismiss();
        }
    }
}

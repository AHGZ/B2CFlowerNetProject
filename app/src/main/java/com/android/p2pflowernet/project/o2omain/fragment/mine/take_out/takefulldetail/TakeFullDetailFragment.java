package com.android.p2pflowernet.project.o2omain.fragment.mine.take_out.takefulldetail;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.TakeFullDetailsAdapter;
import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.EncoreBean;
import com.android.p2pflowernet.project.entity.O2oIndexBean;
import com.android.p2pflowernet.project.entity.OrderDetailsBean;
import com.android.p2pflowernet.project.entity.PayAlResultBean;
import com.android.p2pflowernet.project.entity.ShopCart;
import com.android.p2pflowernet.project.entity.TakeOutBean;
import com.android.p2pflowernet.project.event.CheckPayEvent;
import com.android.p2pflowernet.project.event.RefreshOrderListEvent;
import com.android.p2pflowernet.project.event.SetPayPwdEvent;
import com.android.p2pflowernet.project.event.TakeOutOrderEvent;
import com.android.p2pflowernet.project.event.UPpayEvent;
import com.android.p2pflowernet.project.event.UserBanclanceBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.activity.StoreDetailActivity;
import com.android.p2pflowernet.project.o2omain.fragment.mine.take_out.to_evaluate.TakeOutToEvaluateActivity;
import com.android.p2pflowernet.project.o2omain.fragment.refund.RefundActivity;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.PayPopupWindow;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.AppPartnerAlertDialog;
import com.android.p2pflowernet.project.view.fragments.affirm.ArrirmSuccessActivity;
import com.android.p2pflowernet.project.view.fragments.affirm.SetPayPwdActivity;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.check.CheckPayPwdActivity;
import com.android.p2pflowernet.project.view.fragments.trade.pay.PayResult;
import com.google.gson.Gson;
import com.unionpay.UPPayAssistEx;

import org.greenrobot.eventbus.EventBus;
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
 * Created by heguozhong on 2018/1/15/015.
 * 外卖明细主界面
 */

public class TakeFullDetailFragment extends KFragment<ITakeFullDetailView, ITakeFullDetailPresenter> implements ITakeFullDetailView, PopupWindow.OnDismissListener {

    //装载外卖订单完整详情的recyclerview
    @BindView(R.id.group_full_detail_recyclerview)
    RecyclerView groupFullDetailRecyclerview;
    private TakeFullDetailsAdapter takeFullDetailsAdapter;
    private ShapeLoadingDialog shapeLoadingDialog;
    private String order_num;
    private String payPrice;//价格
    private static final int SDK_PAY_FLAG = 1;
    private String paySource;
    private PopupWindow mPop;
    private String banlanceMoney;
    private WindowManager.LayoutParams params;
    private String pay_amount;
    private String body;
    private OrderDetailsBean orderDetailsBean;


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
    private String countdown_time;
    private String refund_id;
    private List<TakeOutBean.ListBean.GoodsBean> goodsBeans;
    private int id;

    public static KFragment newIntence(String order_num, String countdown_time, String refund_id, int id, List<TakeOutBean.ListBean.GoodsBean> goodsBeans) {
        TakeFullDetailFragment takeFullDetailFragment = new TakeFullDetailFragment();
        Bundle bundle = new Bundle();
        takeFullDetailFragment.setArguments(bundle);
        return takeFullDetailFragment;
    }

    @Override
    public ITakeFullDetailPresenter createPresenter() {
        return new ITakeFullDetailPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.group_full_detail;
    }

    @Override
    public void initData() {
        Bundle arguments = getArguments();
        //订单号
        order_num = arguments.getString("order_num");
        countdown_time = arguments.getString("countdown_time");
        refund_id = arguments.getString("refund_id");
        id = arguments.getInt("id");
        goodsBeans = (List<TakeOutBean.ListBean.GoodsBean>) arguments.getSerializable("goods");

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        initData();
        //初始化沉浸式
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.coloro2o));
        //初始化加载进度条
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .delay(5000)
                .loadText("加载中...")
                .build();

        if (id == 5) {//id==5位退款状态
            //查看退款订单详情接口
            mPresenter.getRefundOrder(order_num, refund_id);
        } else {
            //查看订单详情接口
            mPresenter.o2oorderdetail(order_num);
        }

    }


    @Override
    public void onError(String errorMsg) {
        showShortToast(errorMsg);
    }

    @Override
    public void onSuccess(OrderDetailsBean orderDetailsBean) {
        this.orderDetailsBean = orderDetailsBean;
        pay_amount = orderDetailsBean.getPay_amount();
        //获取布局管理者
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //设置为垂直排列格式
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //recyclerview应用垂直排列格式
        groupFullDetailRecyclerview.setLayoutManager(linearLayoutManager);
        //设置适配器
        takeFullDetailsAdapter = new TakeFullDetailsAdapter(getActivity(), orderDetailsBean, order_num, countdown_time);
        groupFullDetailRecyclerview.setAdapter(takeFullDetailsAdapter);

        onCallBacks();

}

//    @Override
//    public void onSuccessRefund(ReFundOrderBean reFundOrderBean) {
//        this.reFundOrderBean = reFundOrderBean;
//
//    }

    @Override
    public void onSuccess(String message) {
        showShortToast(message);
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

            mPop.showAtLocation(groupFullDetailRecyclerview, Gravity.BOTTOM | Gravity.CENTER, 0, 0);
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

    @Override
    public String getType() {
        return "1";
    }

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
        showShortToast(message);
    }

    @Override
    public String is_father_order() {
        return "1";
    }

    @Override
    public String getOrderNumber() {
        return order_num;
    }

    @Override
    public void onSuccessCancelOrder(String message) {
        EventBus.getDefault().post(new RefreshOrderListEvent("RefreshOrder"));
        showAlertMsgDialog2("取消订单成功。。。", "取消订单成功", "确定", "返回");
    }

    @Override
    public void onSuccessEncore(EncoreBean data) {
        if (data != null) {
            //再来一单
            String str = "";
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
                } else {
                    str += listsBean.getName() + "、";
                }
            }
            Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
            intent.putExtra("merch_id", orderDetailsBean.getMerch_id());
            intent.putExtra("flag", "1");
//            EventBus.getDefault().post(new MessageEvent(shopCart.getShoppingAccount(), shopCart.getShoppingTotalPrice(), shopCart));
            startActivity(intent);
            if (!str.equals("")) {
                showShortToast(str.substring(0, str.length() - 2) + "已下架");
            }

            EventBus.getDefault().post(new TakeOutOrderEvent("1"));
        }
    }

    @Override
    public void onSuccessCancelRefund(String data) {
        EventBus.getDefault().post(new RefreshOrderListEvent("RefreshOrder"));
        showAlertMsgDialog2("取消退款成功。。。", "取消退款成功", "确定", "返回");
    }

    @Override
    public void onSuccessConfirmGoods(String data) {
        EventBus.getDefault().post(new RefreshOrderListEvent("RefreshOrder"));
        showAlertMsgDialog2("确认收货成功。。。", "确认收货成功", "确定", "返回");
    }

    //回调接口点击事件
    public void onCallBacks() {
        //设置订单号复制按钮点击事件
        takeFullDetailsAdapter.setOnCopyClickListener(new TakeFullDetailsAdapter.OnCopyClickListener() {
            @Override
            public void onCopyClick(View v, TextView text) {
                onClickCopy(v, text);
            }

        });
        //设置返回按钮点击事件
        takeFullDetailsAdapter.setOnBackClickListener(new TakeFullDetailsAdapter.OnBackClickListener() {
            @Override
            public void onBackClick(View v) {
                removeFragment();
            }
        });
        //设置服务按钮点击事件
        takeFullDetailsAdapter.setOnServiceClickListener(new TakeFullDetailsAdapter.OnServiceClickListener() {
            @Override
            public void onServiceClick(View v) {
                showShortToast("服务...............");
            }
        });
        //设置去评价点击事件
        takeFullDetailsAdapter.setOnQpjClickListener(new TakeFullDetailsAdapter.OnQpjClickListener() {
            @Override
            public void onQpjClick(View v) {
                Intent evaluateIntent = new Intent(getActivity(), TakeOutToEvaluateActivity.class);
                evaluateIntent.putExtra("order_num", order_num);
                startActivity(evaluateIntent);
                removeFragment();
            }
        });
        //设置再来一单点击事件
        takeFullDetailsAdapter.setOnAgainClickListener(new TakeFullDetailsAdapter.OnAgainClickListener() {
            @Override
            public void onAgainClick(View v) {
                mPresenter.againOneOrder(order_num);
            }
        });
        //设置申请退款点击事件
        takeFullDetailsAdapter.setOnSqtkClickListener(new TakeFullDetailsAdapter.OnSqtkClickListener() {
            @Override
            public void onSqtkClick(View v) {
                Intent refundIntent = new Intent(getActivity(), RefundActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("goods", (Serializable) goodsBeans);
                bundle.putString("pay_channel", orderDetailsBean.getPay_channel() + "");
                bundle.putString("manger_name", orderDetailsBean.getMerch_name());
                bundle.putString("manger_img", orderDetailsBean.getLogo_url());
                bundle.putString("pay_amount", orderDetailsBean.getPay_amount());
                bundle.putString("order_num", order_num);
                refundIntent.putExtras(bundle);
                startActivity(refundIntent);
                removeFragment();
            }
        });
        //设置取消退款点击事件
        takeFullDetailsAdapter.setOnQxtkClickListener(new TakeFullDetailsAdapter.OnQxtkClickListener() {
            @Override
            public void onQxtkClick(View v) {
                mPresenter.cancelRefund(order_num);
            }
        });
        //设置取消订单点击事件
        takeFullDetailsAdapter.setOnQxddClickListener(new TakeFullDetailsAdapter.OnQxddClickListener() {
            @Override
            public void onQxddClick(View v) {
                showAlertMsgDialog("退款将退回您原订单支付的账户，详细情况请查看退款/售后。", "取消订单并退款", "取消订单", "先等等");
            }
        });
        //设置确认收货点击事件
        takeFullDetailsAdapter.setOnQrshClickListener(new TakeFullDetailsAdapter.OnQrshClickListener() {
            @Override
            public void onQrshClick(View v) {
                mPresenter.confirmGoods(order_num);
            }
        });
        //设置去付款点击事件
        takeFullDetailsAdapter.setOnQfkClickListener(new TakeFullDetailsAdapter.OnQfkClickListener() {
            @Override
            public void onQfkClick(View view) {
                payPrice = pay_amount;
                body = order_num + "," + "2";
                pay(payPrice, order_num, body);
            }
        });
        //设置联系送餐员点击事件
        takeFullDetailsAdapter.setOnCallPeopleClickListener(new TakeFullDetailsAdapter.OnCallPeopleClickListener() {
            @Override
            public void onCallPeopleClick(View v, String tel) {
                call(tel);
            }
        });
        //设置联系商家点击事件
        takeFullDetailsAdapter.setOnCallShopClickListener(new TakeFullDetailsAdapter.OnCallShopClickListener() {
            @Override
            public void onCallShopClick(View v, String tel) {
                call(tel);
            }
        });
    }

    //复制文本方法
    public void onClickCopy(View v, TextView textView) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(textView.getText());
        showShortToast("复制成功");
    }

    //拨打手机号
    public void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //弹出取消订单对话框
    public void showAlertMsgDialog(String msg, String title, String positive, String negative) {
        AppPartnerAlertDialog alertDialog = new AppPartnerAlertDialog(getActivity()).builder().setTitle(title).setMsg(msg)
                .setPositiveButton(positive, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.cancelOrder(order_num);
                    }
                })
                .setNegativeButton(negative, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
        alertDialog.show();
    }

    //弹出取消订单对话框
    public void showAlertMsgDialog2(String msg, String title, String positive, String negative) {
        AppPartnerAlertDialog alertDialog = new AppPartnerAlertDialog(getActivity()).builder().setTitle(title).setMsg(msg)
                .setPositiveButton(positive, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeFragment();
                    }
                })
                .setNegativeButton(negative, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeFragment();
                    }
                });
        alertDialog.show();
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
        EventBus.getDefault().post(new RefreshOrderListEvent("RefreshOrder"));
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

}

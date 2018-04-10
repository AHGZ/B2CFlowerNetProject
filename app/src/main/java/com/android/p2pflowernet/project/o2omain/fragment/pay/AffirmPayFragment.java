package com.android.p2pflowernet.project.o2omain.fragment.pay;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.MerchXqBean;
import com.android.p2pflowernet.project.entity.PayAlResultBean;
import com.android.p2pflowernet.project.entity.ScanOrderBean;
import com.android.p2pflowernet.project.event.CheckPayEvent;
import com.android.p2pflowernet.project.event.SetPayPwdEvent;
import com.android.p2pflowernet.project.event.UPpayEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.PayPopupWindow;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.affirm.SetPayPwdActivity;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.check.CheckPayPwdActivity;
import com.android.p2pflowernet.project.view.fragments.trade.pay.PayResult;
import com.google.gson.Gson;
import com.unionpay.UPPayAssistEx;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.android.p2pflowernet.project.constant.Constants.PARTNER;
import static com.android.p2pflowernet.project.constant.Constants.RSA_PRIVATE;
import static com.android.p2pflowernet.project.constant.Constants.SELLER;

/**
 * Created by caishen on 2017/12/29.
 * by--确认付款
 */

public class AffirmPayFragment extends KFragment<IAffirmPayView, IAffirmPayPrenter>
        implements NormalTopBar.normalTopClickListener, IAffirmPayView, PopupWindow.OnDismissListener {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_shop_nick)
    TextView tvShopNick;
    @BindView(R.id.btn_pay)
    Button btnPay;
    @BindView(R.id.et_paymoney)
    CustomEditText etPaymoney;
    private ShapeLoadingDialog shapeLoadingDialog;
    private String payMoneys = "";//支付金额
    private String resultString = "";//店铺付款码
    private String order_num;
    private PayPopupWindow mPop;
    private String paySource;
    private WindowManager.LayoutParams params;
    private static final int SDK_PAY_FLAG = 1;
    private String salemoney;//支付宝确认收到的金额

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

                        salemoney = payAlResultBean.getAlipay_trade_app_pay_response().getTotal_amount();

                        //跳转到查看订单详情界面
                        removeFragment();
                        Intent intent = new Intent(getActivity(), O2oSuccessPayActivity.class);
                        intent.putExtra("type", "1");//0-余额 1-支付宝 2-银联 3-微信
                        intent.putExtra("isok", true);
                        intent.putExtra("payMoney", payMoneys);
                        startActivity(intent);

                    } else {

                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。

                        removeFragment();
                        Intent intent = new Intent(getActivity(), O2oSuccessPayActivity.class);
                        intent.putExtra("type", "1");//0-余额 1-支付宝 2-银联 3-微信
                        intent.putExtra("isok", false);
                        intent.putExtra("payMoney", payMoneys);
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
    public IAffirmPayPrenter createPresenter() {
        return new IAffirmPayPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_o2o_affirm_pay;
    }

    @Override
    public void initData() {

        if (!TextUtils.isEmpty(resultString) || !resultString.equals("")) {

            mPresenter.getMerceInfo();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultString = getArguments().getString("resultString");

        if (!TextUtils.isEmpty(resultString) || !resultString.equals("")) {

            //截取商家信息
            resultString = resultString.toString().substring(resultString.lastIndexOf("P0"));
        }
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸式
        Utils.setStatusBar(getActivity(), 2, false);
        normalTop.setTitleText("确认付款");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);

        //设置输入数字与小数点
        etPaymoney.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity()).loadText("加载中...")
                .delay(5000)
                .build();

        //初始化数据
        initData();
    }

    public static KFragment newIntence(String resultString) {

        AffirmPayFragment affirmPayFragment = new AffirmPayFragment();
        Bundle bundle = new Bundle();
        bundle.putString("resultString", resultString);
        affirmPayFragment.setArguments(bundle);
        return affirmPayFragment;
    }

    @OnClick(R.id.btn_pay)
    public void onClick() {//去支付

        //提交订单去支付
        mPresenter.payscanOrder();
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
    public String merch_id() {
        return resultString;
    }

    @Override
    public void successData(MerchXqBean data) {

        if (data != null) {


        }
    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public String getMoney() {

        payMoneys = etPaymoney.getText().toString().trim();
        return etPaymoney.getText().toString().trim();
    }

    /**
     * 提交订单
     *
     * @param data
     */
    @Override
    public void successCommit(ScanOrderBean data) {

        if (data != null) {

            order_num = data.getOrder_num();
            String balance = data.getBalance();

            //微信支付的点击事件
            View.OnClickListener wxOnclickListner = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Utils.isFastDoubleClick()) {

                        return;
                    }

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

            mPop = new PayPopupWindow(getActivity(), balance, etPaymoney.getText().toString().trim(), wxOnclickListner,
                    zfbxOnClickListener, yeOnClickListener, new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Utils.isFastDoubleClick()) {

                        return;
                    }

                    // 调用银联支付接口请求签名
                    paySource = "2";
                    mPresenter.AlipayPresenter();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDismiss();

                    showShortToast("支付取消");
                    removeFragment();
                }
            });

            mPop.setFocusable(false);

            mPop.showAtLocation(btnPay, Gravity.BOTTOM | Gravity.CENTER, 0, 0);
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
    public String getOrderNum() {
        return order_num;
    }

    @Override
    public String getPaySource() {
        return paySource;
    }

    /**
     * 支付类型 1，O2O  2，B2c 3,购买合伙人 4，代理人追加，5 代理人购买资质6 团购 7线下扫码支付
     *
     * @return
     */
    @Override
    public String getType() {
        return "7";
    }

    @Override
    public String is_father_order() {
        return "1";
    }

    /**
     * 银联 ，支付宝请求签名回调成功的
     *
     * @param data
     */
    @Override
    public void onSuccessAli(AppTradeBean data) {

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
     * 检测密码支付成功
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(CheckPayEvent event) {
        String invoice = event.getStr();

        if (invoice.equals("10")) {

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

        if (invoice.equals("10")) {

            mPresenter.yEpay();
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
                intent.putExtra("tag", "10");
                startActivity(intent);

            } else {

                //检测输入的支付密码
                Intent intent = new Intent(getActivity(), CheckPayPwdActivity.class);
                intent.putExtra("tag", "10");
                startActivity(intent);
            }
        }
    }

    @Override
    public void onSuccessed(String message) {

    }

    /***
     * 余额支付成功回调
     * @param data
     */
    @Override
    public void onSuccessYe(BanlanceBean data) {

        removeFragment();
        Intent intent = new Intent(getActivity(), O2oSuccessPayActivity.class);
        intent.putExtra("type", "0");//0-余额 1-支付宝 2-银联 3-微信
        intent.putExtra("isok", true);
        intent.putExtra("payMoney", payMoneys);
        startActivity(intent);
    }

    /**
     * 余额支付失败回调
     *
     * @param message
     */
    @Override
    public void onPayYeError(String message) {

        removeFragment();
        Intent intent = new Intent(getActivity(), O2oSuccessPayActivity.class);
        intent.putExtra("type", "0");//0-余额 1-支付宝 2-银联 3-微信
        intent.putExtra("isok", false);
        intent.putExtra("payMoney", payMoneys);
        startActivity(intent);

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
        Intent intent = new Intent(getActivity(), O2oSuccessPayActivity.class);
        intent.putExtra("type", "2");//0-余额 1-支付宝 2-银联 3-微信
        if (msg.equals("支付成功！")) {
            intent.putExtra("isOK", true);
        } else if (msg.equals("支付失败！")) {
            intent.putExtra("isOK", false);
        } else if (msg.equals("用户取消了支付")) {
            intent.putExtra("isOK", false);
        }
        intent.putExtra("money", payMoneys);
        startActivity(intent);
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

        paySource = "1";
        mPresenter.AlipayPresenter();
        onDismiss();
    }

    @Override
    public void onDismiss() {

        if (mPop != null) {
            mPop.dismiss();
        }
    }
}

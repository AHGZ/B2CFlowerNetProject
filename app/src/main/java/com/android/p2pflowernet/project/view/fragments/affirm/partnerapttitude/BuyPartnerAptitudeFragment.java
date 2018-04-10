package com.android.p2pflowernet.project.view.fragments.affirm.partnerapttitude;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.ApplyFroPayBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.PayAlResultBean;
import com.android.p2pflowernet.project.entity.UnionPayEntity;
import com.android.p2pflowernet.project.event.CheckPayEvent;
import com.android.p2pflowernet.project.event.SetPayPwdEvent;
import com.android.p2pflowernet.project.event.UPpayEvent;
import com.android.p2pflowernet.project.event.UserBanclanceBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.NumberAddSubView;
import com.android.p2pflowernet.project.view.customview.PayPopupWindow;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.check.CheckPayPwdActivity;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.check.CheckPayPwdFragment;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.check.SetPayPwdFragment;
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
 * Created by caishen on 2017/11/9.
 * by--购买合伙人资质
 */

public class BuyPartnerAptitudeFragment extends KFragment<IBuyPartnerView, IBuyPartnerViewPrenter> implements
        IBuyPartnerView, PopupWindow.OnDismissListener, NormalTopBar.normalTopClickListener,
        NumberAddSubView.OnNumberChangeListener {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.tv_pledge)
    TextView tvPledge;
    @BindView(R.id.num_subview)
    NumberAddSubView numSubview;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.btn_buy)
    Button btnBuy;
    @BindView(R.id.tv_type_nick)
    TextView tvTypeNick;
    @BindView(R.id.tv_hint_type)
    TextView tvHintType;
    private PayPopupWindow mPop;
    private WindowManager.LayoutParams params;
    private ShapeLoadingDialog shapeLoadingDialog;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private String money;//支付成功金额
    private String salePrice;//支付成功金额

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

                        money = payAlResultBean.getAlipay_trade_app_pay_response().getTotal_amount();
                        //跳转到查看订单详情界面
                        removeFragment();
                        Intent intent = new Intent(getActivity(), SuccessPayActivity.class);
                        intent.putExtra("message", "支付成功");
                        intent.putExtra("type", "1");
                        intent.putExtra("isOK", true);
                        intent.putExtra("money", money);
                        intent.putExtra("type1", type1);
                        intent.putExtra("isadd", isAdd);
                        intent.putExtra("ordernum", getOrderNum());
                        startActivity(intent);

                    } else {

                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        removeFragment();
                        Intent intent = new Intent(getActivity(), SuccessPayActivity.class);
                        intent.putExtra("message", "支付失败");
                        intent.putExtra("type", "1");
                        intent.putExtra("isOK", false);
                        intent.putExtra("money", money);
                        intent.putExtra("type1", type1);
                        intent.putExtra("isadd", isAdd);
                        intent.putExtra("ordernum", getOrderNum());
                        startActivity(intent);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    private String paySource;
    private String type = "";//支付类型
    private String type1 = "";//（0.合伙人，1.代理人）
    private boolean isAdd = false;//是否追加金额
    private String orderNum = "";

    @Override
    public IBuyPartnerViewPrenter createPresenter() {
        return new IBuyPartnerViewPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_buy_partner;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        type1 = arguments.getString("type");
        isAdd = arguments.getBoolean("isAdd");
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        normalTop.setTitleText("");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setTopClickListener(this);

        //判断来源
        if (type1.equals("") | type1 == null) {
            return;
        }
        if (type1.equals("0") && isAdd == false) {//0合伙人
            tvTypeNick.setText("*合伙人资质（份）");
            tvHintType.setText("合伙人资质1份10000元，最低1份起。");
            tvTotalMoney.setText("¥" + 10000);
            normalTop.setTitleText("购买合伙人资质");
            numSubview.setValue(1);
        } else if (type1.equals("1") && isAdd == false) {//1代理人
            tvTypeNick.setText("*购买合伙人审批资质（份）");
            tvHintType.setText("合伙人审批资质1份10000元，最低5份起。");
            tvTotalMoney.setText("¥" + 10000);
            normalTop.setTitleText("购买合伙人审批资质");
            numSubview.setValue(5);
        } else if (type1.equals("1") && isAdd == true) {//追加代理人
            tvTypeNick.setText("*追加合伙人审批资质（份）");
            tvHintType.setText("追加合伙人审批资质1份10000元，最低1份起。");
            tvTotalMoney.setText("¥" + 10000);
            normalTop.setTitleText("追加合伙人审批资质");
            numSubview.setValue(1);
        } else if (type1.equals("0") && isAdd == true) {
            tvTypeNick.setText("*追加合伙人资质（份）");
            tvHintType.setText("追加合伙人资质1份10000元，最低1份起。");
            tvTotalMoney.setText("¥" + 10000);
            normalTop.setTitleText("追加合伙人资质");
            numSubview.setValue(1);
        }

        tvTotalMoney.setText("¥" + numSubview.getValue() * 10000);
        //设置加减控件的点击事件
        numSubview.setOnNumberChangeListener(this);
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();
    }

    public static KFragment newIntence(String type, boolean isAdd) {

        BuyPartnerAptitudeFragment buyPartnerAptitudeFragment = new BuyPartnerAptitudeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", type);
        bundle.putBoolean("isAdd", isAdd);
        buyPartnerAptitudeFragment.setArguments(bundle);

        return buyPartnerAptitudeFragment;
    }

    @OnClick(R.id.btn_buy)
    public void onClick() {//去支付

        //获取订单号
        mPresenter.applyPay();
    }

    /**
     * call alipay sdk pay. 调用支付宝SDK支付
     */
    public void aliPay() {

        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {

            new AlertDialog.Builder(getActivity()).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            return;
                        }
                    }).show();
            return;
        }
        paySource = "1";
        mPresenter.AlipayPresenter();
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

    @Override
    public void UnionPaySuccess(UnionPayEntity unionPayResult) {

    }

    @Override
    public void showDialog() {

        shapeLoadingDialog.show();
    }

    /**
     * 验证是否设置过支付密码成功的回调
     *
     * @param data
     */
    @Override
    public void onSuccessCheck(CheckPwdBean data) {

        if (data != null) {
            //0: 否，1： 是
            int is_set = data.getIs_set();
            onDismiss();
            if (is_set == 0) {

                // 去设置支付密码
                addFragment(SetPayPwdFragment.newIntence("6"));

            } else {

                //检测输入的支付密码
                addFragment(CheckPayPwdFragment.newIntence("6"));
            }
        }
    }


    /**
     * 检测输入密码成功
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CheckPayEvent event) {
        String invoice = event.getStr();
        if (invoice.equals("6")) {

            mPresenter.yEpay();
        }
    }

    /**
     * 设置密码成功
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SetPayPwdEvent event) {
        String invoice = event.getStr();

        if (invoice.equals("6")) {
            //设置成功

            //检测输入的支付密码
            Intent intent = new Intent(getActivity(), CheckPayPwdActivity.class);
            intent.putExtra("tag", "6");
            startActivity(intent);
        }
    }

    @Override
    public void onSuccessed(String message) {

        showShortToast(message);
    }

    @Override
    public String getBody() {
        return null;
    }

    @Override
    public String getOrderNum() {
        return orderNum;
    }

    @Override
    public String getPaySource() {
        return paySource;
    }

    /**
     * 支付宝请求签名成功
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
     * 支付类型 1，O2O  2，B2c 3,购买合伙人 4，代理人追加，5 代理人购买资质
     *
     * @return
     */
    @Override
    public String getType() {

        if (type1.equals("0")) {//0合伙人
            type = "3";
        } else if (type1.equals("1") && isAdd == false) {//1代理人
            type = "5";
        } else if (type1.equals("1") && isAdd == true) {//代理人追加
            type = "4";
        }

        return type;
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

    @Override
    public String getnum() {
        return numSubview.getValue() + "";
    }

    /**
     * 余额支付成功
     *
     * @param data
     */
    @Override
    public void onSuccessYe(BanlanceBean data) {

        removeFragment();
        Intent intent = new Intent(getActivity(), SuccessPayActivity.class);
        intent.putExtra("message", "支付成功");
        intent.putExtra("type", "0");
        intent.putExtra("isOK", true);
        intent.putExtra("money", salePrice);
        intent.putExtra("type1", type1);
        intent.putExtra("isadd", isAdd);
        intent.putExtra("ordernum", orderNum);
        startActivity(intent);
    }

    /**
     * 余额支付失败回调
     *
     * @param message
     */
    @Override
    public void onPayYeError(String message) {

        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
        removeFragment();
        Intent intent = new Intent(getActivity(), SuccessPayActivity.class);
        intent.putExtra("message", message);
        intent.putExtra("type", "0");
        intent.putExtra("isOK", false);
        intent.putExtra("money", salePrice);
        intent.putExtra("type1", type1);
        intent.putExtra("isadd", isAdd);
        intent.putExtra("ordernum", orderNum);
        startActivity(intent);
    }

    /***
     * 获取余额成功
     * @param data
     */
    @Override
    public void SuccessgetBanlance(UserBanclanceBean data) {

        String money = data.getMoney();
        int payMoney = showPayMoney();

    }

    /**
     * 身份支付请求成功
     *
     * @param data
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onsuccessApplyFor(ApplyFroPayBean data) {

        if (data != null) {

            String yu_e = data.getYu_e();//是否可余额支付
            salePrice = data.getSale_price();
            orderNum = data.getOrder_num();
            int balance = data.getBalance();

//            BigDecimal msalePriceDecimal = new BigDecimal(salePrice.replace(",", ""));
            // 用户余额
//            BigDecimal mMaxDecimal = new BigDecimal("50000.00");

            if (type1.equals("1") && numSubview.getValue() < 5 && isAdd == false) {

                showShortToast("代理人资质不能少于5份");
                numSubview.setValue(5);
                showPayMoney();

            } else {

                //微信支付的点击事件
                View.OnClickListener wxOnclickListner = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (Utils.isFastDoubleClick()) {

                            return;
                        }
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
                        onDismiss();
                    }
                };

                //余额支付的点击事件
                View.OnClickListener yeOnClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (Utils.isFastDoubleClick()) {

                            return;
                        }

                        if (TextUtils.isEmpty(yu_e)) {
                            return;
                        }
                        if (TextUtils.isEmpty(salePrice)) {
                            return;
                        }

                        //是否可用余额支付
                        if (balance == 0) {
                            showShortToast("余额不足~");
                        } else {
                            mPresenter.checkIsSetPayPwd();
                        }
                    }
                };

                mPop = new PayPopupWindow(getActivity(), salePrice, yu_e, wxOnclickListner,
                        zfbxOnClickListener, yeOnClickListener, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (Utils.isFastDoubleClick()) {
                            return;
                        }
                        // 调用银联支付接口
                        paySource = "2";
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onDismiss();
                        showShortToast("支付取消");
                        removeFragment();
                    }
                });

                mPop.showAtLocation(btnBuy, Gravity.BOTTOM | Gravity.CENTER, 0, 0);
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
    }

    //计算购买金额
    private int showPayMoney() {
        int payMoney = 0;
        if (type1.equals("0")) {//购买合伙人，追加合伙人
            payMoney = numSubview.getValue() * 10000;
        } else if (type1.equals("1")) {//购买代理人
            payMoney = numSubview.getValue() * 10000;
        } else if (type1.equals("1") && isAdd == true) {//追加代理人
            payMoney = numSubview.getValue() * 10000;
        }

        return payMoney;
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
        Intent intent = new Intent(getActivity(), SuccessPayActivity.class);
        intent.putExtra("type", "2");//支付方式 0余额 1支付宝 2银联
        if (msg.equals("支付成功！")) {
            intent.putExtra("isOK", true);
        } else if (msg.equals("支付失败！")) {
            intent.putExtra("isOK", false);
        } else if (msg.equals("用户取消了支付")) {
            intent.putExtra("isOK", false);
        }
        intent.putExtra("message", msg);
        intent.putExtra("money", salePrice);
        intent.putExtra("type1", type1);
        intent.putExtra("isadd", isAdd);
        intent.putExtra("ordernum", orderNum);
        startActivity(intent);
    }

    @Override
    public void onDismiss() {

        mPop.dismiss();
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

    /**
     * 加
     *
     * @param view
     * @param value
     */
    @Override
    public void addNumber(View view, int value) {
        if (value > 5) {
            if (type1.equals("0") && isAdd == false) {//0合伙人
                showShortToast("合伙人资质最多5份");
            } else if (type1.equals("1") && isAdd == false) {//1代理人
                showShortToast("合伙人审批资质最多5份");
            } else if (type1.equals("1") && isAdd == true) {//追加代理人
                showShortToast("追加合伙人审批资质最多5份");
            } else if (type1.equals("0") && isAdd == true) {//追加合伙人
                showShortToast("追加合伙人最多5份");
            }
            numSubview.setValue(5);
        } else {
            tvTotalMoney.setText("¥" + numSubview.getValue() * 10000);
        }
    }

    /**
     * 减
     *
     * @param view
     * @param value
     */
    @Override
    public void subNumner(View view, int value) {
        if (value <= 1) {
            if (type1.equals("0") && isAdd == false) {//0合伙人
                showShortToast("合伙人资质不能少于1份");
                numSubview.setValue(1);
            } else if (type1.equals("1") && isAdd == false) {//1代理人
                showShortToast("合伙人审批资质不能少于5份");
                numSubview.setValue(5);
            } else if (type1.equals("1") && isAdd == true) {//追加代理人
                showShortToast("追加合伙人审批资质不能少于1份");
                numSubview.setValue(1);
            } else if (type1.equals("0") && isAdd == true) {//追加合伙人
                showShortToast("追加合伙人资质不能少于1份");
                numSubview.setValue(1);
            }
        }
        tvTotalMoney.setText("¥" + numSubview.getValue() * 10000);
    }
}

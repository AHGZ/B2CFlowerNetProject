package com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying.shopdetails.submitorder;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
import com.android.p2pflowernet.project.entity.GroupHomeBean;
import com.android.p2pflowernet.project.entity.GroupPutOrderBean;
import com.android.p2pflowernet.project.entity.PayAlResultBean;
import com.android.p2pflowernet.project.event.CheckPayEvent;
import com.android.p2pflowernet.project.event.SetPayPwdEvent;
import com.android.p2pflowernet.project.event.UPpayEvent;
import com.android.p2pflowernet.project.event.UserBanclanceBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.PayPopupWindow;
import com.android.p2pflowernet.project.view.fragments.affirm.ArrirmSuccessActivity;
import com.android.p2pflowernet.project.view.fragments.affirm.SetPayPwdActivity;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.check.CheckPayPwdActivity;
import com.android.p2pflowernet.project.view.fragments.trade.pay.PayResult;
import com.google.gson.Gson;
import com.rxy.netlib.http.ApiResponse;
import com.umeng.analytics.MobclickAgent;
import com.unionpay.UPPayAssistEx;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.android.p2pflowernet.project.constant.Constants.PARTNER;
import static com.android.p2pflowernet.project.constant.Constants.RSA_PRIVATE;
import static com.android.p2pflowernet.project.constant.Constants.SELLER;

/**
 * Created by heguozhong on 2018/1/5/005.
 * 提交订单主界面
 */

public class SubmitOrderFragment extends KFragment<ISubmitOrderView, ISubmitOrderPresenter> implements ISubmitOrderView, PopupWindow.OnDismissListener {

    //订单图片
    @BindView(R.id.group_buying_submitorder_shop_iv)
    ImageView groupBuyingSubmitorderShopIv;
    //订单商品名称
    @BindView(R.id.group_buying_submitorder_shop_tv1)
    TextView groupBuyingSubmitorderShopTv1;
    //订单商家名称
    @BindView(R.id.group_buying_submitorder_shop_tv2)
    TextView groupBuyingSubmitorderShopTv2;
    //订单金额
    @BindView(R.id.group_buying_submitorder_shop_money)
    TextView groupBuyingSubmitorderShopMoney;
    //购买数量名字
    @BindView(R.id.group_buying_submitorder_num_name)
    TextView groupBuyingSubmitorderNumName;
    //购买数量减按钮
    @BindView(R.id.group_buying_submitorder_num_down)
    ImageView groupBuyingSubmitorderNumDown;
    //购买数量具体个数
    @BindView(R.id.group_buying_submitorder_num_tv)
    TextView groupBuyingSubmitorderNumTv;
    //购买数量加按钮
    @BindView(R.id.group_buying_submitorder_num_add)
    ImageView groupBuyingSubmitorderNumAdd;
    //花返名字
    @BindView(R.id.group_buying_submitorder_huafan_name)
    TextView groupBuyingSubmitorderHuafanName;
    //花返具体金额
    @BindView(R.id.group_buying_submitorder_huafan_tv)
    TextView groupBuyingSubmitorderHuafanTv;
    //小计名字
    @BindView(R.id.group_buying_submitorder_xiaoji_name)
    TextView groupBuyingSubmitorderXiaojiName;
    //小计具体金额
    @BindView(R.id.group_buying_submitorder_xiaoji_tv)
    TextView groupBuyingSubmitorderXiaojiTv;
    //实付金额名字
    @BindView(R.id.group_buying_submitorder_sfje_name)
    TextView groupBuyingSubmitorderSfjeName;
    //实付金额具体金额
    @BindView(R.id.group_buying_submitorder_sfje_tv)
    TextView groupBuyingSubmitorderSfjeTv;
    //手机号
    @BindView(R.id.group_buying_submitorder_phone_name)
    TextView groupBuyingSubmitorderPhoneName;
    //手机具体号码
    @BindView(R.id.group_buying_submitorder_phone_tv)
    TextView groupBuyingSubmitorderPhoneTv;
    //提交订单按钮
    @BindView(R.id.group_buying_submitorder_button)
    Button groupBuyingSubmitorderButton;
    //标题栏返回按钮
    @BindView(R.id.cate_title_left_iv)
    ImageView cateTitleLeftIv;
    //标题栏文本内容
    @BindView(R.id.cate_title_text)
    TextView cateTitleText;
    //标题栏右侧按钮
    @BindView(R.id.cate_title_right_iv)
    ImageView cateTitleRightIv;
    private int shopNum;
    private String tel;//联系电话
    private String huafan_price;//花返价格

    private PopupWindow mPop;
    private String banlanceMoney;
    private String body;
    private String payPrice;//价格
    private String paySource;
    private WindowManager.LayoutParams params;
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
    private String order_num;
    private GroupHomeBean.ListBean info;
    private int group_id;
    private int merchant_id;

    public static SubmitOrderFragment newInstance(String tel, String huafan_price, GroupHomeBean.ListBean listBean) {
        Bundle args = new Bundle();
        SubmitOrderFragment fragment = new SubmitOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ISubmitOrderPresenter createPresenter() {
        return new ISubmitOrderPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.groupbuying_submit_order;
    }

    @Override
    public void initData() {
        Bundle arguments = getArguments();
        tel = arguments.getString("tel");
        huafan_price = arguments.getString("huafan_price");
        info = (GroupHomeBean.ListBean) arguments.getSerializable("listBean");
        merchant_id = Integer.parseInt(info.getMerchant_id());
        group_id = Integer.parseInt(info.getId());

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        //初始化沉浸式
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.coloro2o));
        //设置标题栏基本信息
        cateTitleText.setText("提交订单");
        cateTitleRightIv.setVisibility(View.INVISIBLE);

        initData();
        //赋值花返价格
        groupBuyingSubmitorderHuafanTv.setText("¥" + huafan_price);
        //赋值手机号
        groupBuyingSubmitorderPhoneTv.setText(tel);
        //获取团购商品数量
        mPresenter.getGroupNum(merchant_id, group_id);
        //赋值商品图片
        new GlideImageLoader().displayImage(getActivity(), info.getImgs(), groupBuyingSubmitorderShopIv);
        //赋值商品名称
        groupBuyingSubmitorderShopTv1.setText(info.getTitle());
        //赋值商家名称
        groupBuyingSubmitorderShopTv2.setText(info.getMerch_name());
        //赋值商品价格
        groupBuyingSubmitorderShopMoney.setText("¥" + info.getPrice());
        //赋值小计金额
        groupBuyingSubmitorderXiaojiTv.setText("¥" + info.getPrice());
        //赋值实付金额
        groupBuyingSubmitorderSfjeTv.setText("¥" + info.getPrice());
    }

    //加按钮，减按钮，提交按钮点击事件
    @OnClick({R.id.cate_title_left_iv, R.id.group_buying_submitorder_num_down, R.id.group_buying_submitorder_num_add, R.id.group_buying_submitorder_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //设置标题栏左侧返回按钮点击事件
            case R.id.cate_title_left_iv:
                removeFragment();
                break;
            //购买数量加按钮
            case R.id.group_buying_submitorder_num_down:
                int num1 = Integer.parseInt(groupBuyingSubmitorderNumTv.getText().toString().trim());
                num1--;
                if (num1 < 1) {
                    showShortToast("购买数量不能小于一份");
                } else {
                    groupBuyingSubmitorderNumTv.setText(num1 + "");
                }

                break;
            //购买数量减按钮
            case R.id.group_buying_submitorder_num_add:
                int num2 = Integer.parseInt(groupBuyingSubmitorderNumTv.getText().toString().trim());
                num2++;
                if (num2 <= shopNum) {
                    groupBuyingSubmitorderNumTv.setText(num2 + "");
                } else {
                    showShortToast("现在现存量只有" + shopNum + "份");
                }

                break;
            //提交订单按钮
            case R.id.group_buying_submitorder_button:
                if (shopNum == 0) {
                    showShortToast("库存为0，请选择其他的商品");
                } else {
                    int num = Integer.parseInt(groupBuyingSubmitorderNumTv.getText().toString().trim());
                    mPresenter.submitOrder(merchant_id, group_id, num);
                }

                //友盟统计
                MobclickAgent.onEvent(getActivity(), "commitGroup");

                break;
        }
    }

    @Override
    public void onError(String errorMsg) {
        showShortToast(errorMsg);
    }

    @Override
    public void onSuccess(String groupResidueNumBean) {
        shopNum = Integer.valueOf(groupResidueNumBean.toString().replace("\"", "")).intValue();
        if (shopNum == 0) {
            groupBuyingSubmitorderNumTv.setText(shopNum + "");
        }

    }


    @Override
    public void showDialog() {

    }

    @Override
    public void hideDialog() {

    }

    @Override
    public void onSuccessData(ArrayList<ApiResponse<String>> data) {

    }

    @Override
    public void onSuccesses(GroupPutOrderBean groupPutOrderBean) {
        order_num = groupPutOrderBean.getOrder_num();
        payPrice = groupPutOrderBean.getSale_price();
        body = order_num + "," + "1";
        pay(payPrice, order_num, body);

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


            //友盟统计
            MobclickAgent.onEvent(getActivity(), "GroupPay");

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
                    //友盟统计
                    MobclickAgent.onEvent(getActivity(), "GroupAliPay");
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
                    //友盟统计
                    MobclickAgent.onEvent(getActivity(), "GroupBanlancePay");
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

                    //友盟统计
                    MobclickAgent.onEvent(getActivity(), "GroupBankPay");
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDismiss();
                    showShortToast("支付取消");
                }
            });

            mPop.showAtLocation(cateTitleLeftIv, Gravity.BOTTOM | Gravity.CENTER, 0, 0);
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

    //"11483002"
    @Override
    public String getOrderNumber() {
        return order_num;
    }

    @Override
    public void onDismiss() {
        if (mPop != null) {
            mPop.dismiss();
        }
    }

    /***
     * 友盟统计
     */
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("groupSubmitPage"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("groupSubmitPage");
    }
}

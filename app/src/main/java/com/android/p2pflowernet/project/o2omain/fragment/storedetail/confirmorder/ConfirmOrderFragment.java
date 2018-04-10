package com.android.p2pflowernet.project.o2omain.fragment.storedetail.confirmorder;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.ConfirmOrderAdapter;
import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.GoPayBean;
import com.android.p2pflowernet.project.entity.O2oAddressBean;
import com.android.p2pflowernet.project.entity.O2oIndexBean;
import com.android.p2pflowernet.project.entity.O2oorderCommitBean;
import com.android.p2pflowernet.project.entity.PayAlResultBean;
import com.android.p2pflowernet.project.entity.ShopCart;
import com.android.p2pflowernet.project.event.CheckPayEvent;
import com.android.p2pflowernet.project.event.RemarksEditTextEvent;
import com.android.p2pflowernet.project.event.SetPayPwdEvent;
import com.android.p2pflowernet.project.event.UPpayEvent;
import com.android.p2pflowernet.project.event.UpdateAddressEvent;
import com.android.p2pflowernet.project.event.UserBanclanceBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.fragment.index.takeout.location.LocationO2oActivity;
import com.android.p2pflowernet.project.o2omain.fragment.remarks.AddRemarksActivity;
import com.android.p2pflowernet.project.utils.TimeTools;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.PayPopupWindow;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.SingleCheckAlertDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.OptionPicker;
import com.android.p2pflowernet.project.view.customview.actionsheet.TimePicker;
import com.android.p2pflowernet.project.view.fragments.affirm.ArrirmSuccessActivity;
import com.android.p2pflowernet.project.view.fragments.affirm.SetPayPwdActivity;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.AuthResult;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.check.CheckPayPwdActivity;
import com.android.p2pflowernet.project.view.fragments.trade.pay.PayResult;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.unionpay.UPPayAssistEx;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.android.p2pflowernet.project.constant.Constants.PARTNER;
import static com.android.p2pflowernet.project.constant.Constants.RSA_PRIVATE;
import static com.android.p2pflowernet.project.constant.Constants.SELLER;

/**
 * Created by heguozhong on 2018/1/10/010.
 * 确认订单主界面
 */

public class ConfirmOrderFragment extends KFragment<IConfirmOrderView, IConfirmOrderPresenter>
        implements IConfirmOrderView, PopupWindow.OnDismissListener, ConfirmOrderActivity.FragmentBackListener {
    //标题栏左侧返回按钮
    @BindView(R.id.cate_title_left_iv)
    ImageView cateTitleLeftIv;
    //标题栏文字说明
    @BindView(R.id.cate_title_text)
    TextView cateTitleText;
    //标题栏右边图标
    @BindView(R.id.cate_title_right_iv)
    ImageView cateTitleRightIv;
    //展示订单具体内容的recyclerview
    @BindView(R.id.confirm_order_recyclerview)
    RecyclerView confirmOrderRecyclerview;
    //花返优惠金额
    @BindView(R.id.confirm_order_tv_hfyh_price)
    TextView confirmOrderTvHfyhPrice;
    //合计
    @BindView(R.id.confirm_order_tv_sum_price)
    TextView confirmOrderTvSumPrice;
    //提交订单
    @BindView(R.id.confirm_order_submit)
    Button confirmOrderSubmit;
    // 加载view
    private ShapeLoadingDialog shapeLoadingDialog;
    private int peiSong = 200;
    ShopCart mShopCart;
    String mGoodslist;
    private O2oIndexBean o2oIndexBean1;
    // 配送时间
    private TextView ljscTime;

    private ConfirmOrderAdapter confirmOrderAdapter;
    // 添加地址id
    private String o2oaddresslistId = "";

    private String reachTime = "";
    private String latitude = "39.9300112";//纬度
    private String longitude = "116.60586";//经度
    private String banlanceMoney = "";//余额
    private List<GoPayBean.ListsBean> goPayBeanLists;//可选的送达时间
    private String merchId = "";//商家ID
    private String trim;
    private GoPayBean gopaybean;
    private String is_self = "1";//1 配送 2 自取 默认1
    private String phone = "";
    private String body;
    private String order_num;
    private String sale_price;
    private PayPopupWindow mPop;
    private String paySource;
    private WindowManager.LayoutParams params;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private TextView beizhuText;

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

                        sale_price = payAlResultBean.getAlipay_trade_app_pay_response().getTotal_amount();
                        //跳转到查看订单详情界面
                        removeFragment();
                        Intent intent = new Intent(getActivity(), ArrirmSuccessActivity.class);
                        intent.putExtra("tag", "o2o");
                        intent.putExtra("message", "支付成功");
                        intent.putExtra("type", "1");
                        intent.putExtra("isOK", true);
                        intent.putExtra("money", sale_price);
                        startActivity(intent);

                    } else {

                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        removeFragment();
                        Intent intent = new Intent(getActivity(), ArrirmSuccessActivity.class);
                        intent.putExtra("tag", "o2o");
                        intent.putExtra("message", "支付失败");
                        intent.putExtra("type", "1");
                        intent.putExtra("isOK", false);
                        intent.putExtra("money", sale_price);
                        startActivity(intent);
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {

                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(getActivity(),
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(getActivity(),
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    private String distrib_money = "0";//配送费
    private String remarksText;

    public static ConfirmOrderFragment newInstance(String goodslist, ShopCart shopCart,
                                                   GoPayBean mMerChantBean, String merch_id, O2oIndexBean o2oIndexBean1) {
        Bundle args = new Bundle();
        ConfirmOrderFragment fragment = new ConfirmOrderFragment();
        args.putString("goodslist", goodslist);
        args.putSerializable("shopcart", shopCart);
        args.putSerializable("gopaybean", mMerChantBean);
        args.putString("merch_id", merch_id);
        args.putSerializable("o2oIndexBean1", o2oIndexBean1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mShopCart = (ShopCart) getArguments().getSerializable("shopcart");
        gopaybean = (GoPayBean) getArguments().getSerializable("gopaybean");

//        //取出经纬度
//        latitude = SPUtils.get(getActivity(), "latitude", "").toString();
//        longitude = SPUtils.get(getActivity(), "longitude", "").toString();

        mGoodslist = getArguments().getString("goodslist");
        merchId = getArguments().getString("merch_id");
        o2oIndexBean1 = (O2oIndexBean) getArguments().getSerializable("o2oIndexBean1");
        distrib_money = o2oIndexBean1.getDistrib_money();

        Log.e("TAG", "o2oIndexBean1==" + o2oIndexBean1);
        Log.e("TAG", "distrib_money==" + distrib_money);
    }

    @Override
    public IConfirmOrderPresenter createPresenter() {
        return new IConfirmOrderPresenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.confirm_order;
    }

    @Override
    public void initData() {

        if (gopaybean == null) {
            return;
        }

        goPayBeanLists = gopaybean.getLists();

        if (mShopCart == null) {
            return;
        }

        //计算商品价格
        showTotalPrice(mShopCart);

        //如果是立即送出，则为空，否则按小时,分钟，例（12,30）传值
        reachTime = TimeTools.getCountTimeByLongHour(gopaybean.getReach_time() == 0 ? 0 : gopaybean.getReach_time());

        GoPayBean.AddInfoBean add_info = gopaybean.getAdd_info();
        o2oaddresslistId = add_info.getId();

        //设置商品列表的适配器
        confirmOrderAdapter = new ConfirmOrderAdapter(getActivity(), mShopCart, add_info, reachTime, o2oIndexBean1, gopaybean);
        confirmOrderRecyclerview.setAdapter(confirmOrderAdapter);

        //设置自取时间监听
        confirmOrderAdapter.setOnZqTimeClickListener(new ConfirmOrderAdapter.OnZqTimeClickListener() {
            @Override
            public void onZqTimeClick(View v) {

                TextView zqTime = (TextView) v.findViewById(R.id.confirm_order_address_zq_time);
                //弹出自取时间对话框
                showTimePicker(v, zqTime);
            }
        });

        //设置餐具选择监听
        confirmOrderAdapter.setOnCjSelectClickListener(new ConfirmOrderAdapter.OnCjSelectClickListener() {
            @Override
            public void onCjSelectClick(View v) {
                TextView cjSum = (TextView) v.findViewById(R.id.confirm_order_cj_sum);
                //弹出选择餐具对话框
                showTableWare(v, cjSum);
            }
        });

        //设置立即送出监听
        confirmOrderAdapter.setOnLjscClickListener(new ConfirmOrderAdapter.OnLjscClickListener() {
            @Override
            public void onLjscClick(View v) {

                ljscTime = (TextView) v.findViewById(R.id.confirm_order_address_ljsctime);
                showLjsc(ljscTime, goPayBeanLists);
            }
        });

        //设置备注/发票监听
        confirmOrderAdapter.setOnBzFpClickListener(new ConfirmOrderAdapter.OnBzFpClickListener() {
            @Override
            public void onBzFpClick(View v) {
                //is_invoice: 0-不支持 1-支持
                //跳转添加备注页面
                Intent intent = new Intent(getActivity(), AddRemarksActivity.class);
                intent.putExtra("isInvoice", gopaybean.getIs_invoice());
                startActivity(intent);
                beizhuText = (TextView) v.findViewById(R.id.confirm_order_beizhu);
            }
        });

        //选择地址点击事件
        confirmOrderAdapter.setOnAddressClickListener(new ConfirmOrderAdapter.OnAddressClickListener() {
            @Override
            public void onAddress(View v) {
                //跳转添加收货地址
                Intent intent = new Intent(getActivity(), LocationO2oActivity.class);
                intent.putExtra("flag", "3");
                startActivity(intent);
            }
        });

        //选择到店自提
        confirmOrderAdapter.setOnZQCheckClickListener(new ConfirmOrderAdapter.OnZQCheckClickListener() {
            @Override
            public void onZQCheckClick(View v, CheckBox checkBox) {
                if (checkBox.isChecked()) {
                    //设置自取
                    is_self = 2 + "";
                } else {
                    is_self = 1 + "";
                }

            }
        });
    }

    //商品总价格
    private void showTotalPrice(ShopCart shops) {

        double mtotalPrice = 0.00;
        List<O2oIndexBean.ListsBean.GoodsListBean> dishList = new ArrayList<>();
        dishList.addAll(shops.getShoppingSingleMap().keySet());
        for (int i = 0; i < dishList.size(); i++) {

            double replace = Double.parseDouble(dishList.get(i).getHuafan().replace(",", ""));
            // 商品数量
            int num = shops.getShoppingSingleMap().get(dishList.get(i));
            mtotalPrice += replace * num;
            BigDecimal bg = new BigDecimal(mtotalPrice);
            mtotalPrice = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }

        confirmOrderTvHfyhPrice.setText("¥" + mtotalPrice);

        //包装费
        double mtotalPrice1 = 0.00;
        int num = 0;
        for (int i = 0; i < dishList.size(); i++) {

            double replace = Double.parseDouble(dishList.get(i).getBox_price().replace(",", ""));
            mtotalPrice1 += replace * Integer.parseInt(dishList.get(i).getBox_num());
            BigDecimal bg = new BigDecimal(mtotalPrice);
            mtotalPrice = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            num += shops.getShoppingSingleMap().get(dishList.get(i));
        }

        double v = mtotalPrice1 * num;
        BigDecimal bg = new BigDecimal(v);
        double v1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        double v2 = shops.getShoppingTotalPrice() + Double.parseDouble(distrib_money) + v1;
        confirmOrderTvSumPrice.setText("¥" + v2);
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        //初始化沉浸式
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.coloro2o));

        //设置标题内容
        cateTitleText.setText("确认订单");
        cateTitleRightIv.setVisibility(View.INVISIBLE);
        //扩大返回按钮点击范围
        UIUtils.setTouchDelegate(cateTitleLeftIv, 50);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        //获取布局管理者
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //设置为垂直排列格式
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //recyclerview应用垂直排列格式
        confirmOrderRecyclerview.setLayoutManager(linearLayoutManager);

        initData();

    }

    //监听事件
    @OnClick({R.id.cate_title_left_iv, R.id.confirm_order_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //标题栏左侧按钮
            case R.id.cate_title_left_iv:

                removeFragment();

                break;
            case R.id.confirm_order_submit:

                mPresenter.o2ocreorder();

                break;
        }
    }

    //显示失败对话框
    public void showAlertMsgDialog(String msg, String title, String positive) {

        SingleCheckAlertDialog alertDialog = new SingleCheckAlertDialog(getActivity()).builder().setTitle(title).setMsg(msg)
                .setPositiveButton(positive, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        alertDialog.show();
    }

    //显示自取时间对话框
    public void showTimePicker(View view, final TextView textView) {

        //默认选中当前时间
        TimePicker picker = new TimePicker(getActivity());
        picker.setTopLineVisible(false);
        picker.setTitleText("自取时间");
        picker.setSubmitTextColor(Color.parseColor("#FF2E00"));//确定
        picker.setCancelTextColor(Color.parseColor("#7B838D"));//取消
        picker.setTitleTextColor(Color.parseColor("#656565"));
        picker.setTextColor(Color.parseColor("#333333"));
        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {
                textView.setText("自取时间 （" + hour + " : " + minute + "）");
            }
        });
        picker.show();
    }

    //显示选择餐具对话框
    public void showTableWare(View view, final TextView textView) {
        final String[] options = new String[11];
        options[0] = "不需要餐具";
        options[1] = "1";
        options[2] = "2";
        options[3] = "3";
        options[4] = "4";
        options[5] = "5";
        options[6] = "6";
        options[7] = "7";
        options[8] = "8";
        options[9] = "9";
        options[10] = "10";
        OptionPicker optionPicker = new OptionPicker(getActivity(), options);
        optionPicker.setTitleText("选择餐具");
        optionPicker.setSubmitTextColor(Color.parseColor("#FF2E00"));
        optionPicker.show();
        optionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(String option, int selectedIndex) {
                textView.setText(options[selectedIndex] + "人");
                if (confirmOrderAdapter == null) {
                    return;
                }
                confirmOrderAdapter.getConfirmOrderCjSum().setText(options[selectedIndex]);
            }
        });
    }


    //获取手机星期
    public String getWeek() {
        Calendar calendar = Calendar.getInstance();
        String mWay = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "周日";
        } else if ("2".equals(mWay)) {
            mWay = "周一";
        } else if ("3".equals(mWay)) {
            mWay = "周二";
        } else if ("4".equals(mWay)) {
            mWay = "周三";
        } else if ("5".equals(mWay)) {
            mWay = "周四";
        } else if ("6".equals(mWay)) {
            mWay = "周五";
        } else if ("7".equals(mWay)) {
            mWay = "周六";
        }
        return mWay;
    }


    @Override
    public void onError(String s) {

//        if (peiSong > 100) {
//
//            //弹出失败对话框
//            showAlertMsgDialog("很遗憾，您当前订单的（收货 地址）不在商家配送范围内。", "温馨提示", "我知道了");
//
//        } else {

        showShortToast(s);
//        }
    }

    @Override
    public void onSuccess(String message) {

    }

    /**
     * 更改添加收货地址回调
     *
     * @param updateaddressevent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpdateAddressEvent updateaddressevent) {
        if (updateaddressevent == null) {
            return;
        }
        if (confirmOrderAdapter == null) {
            return;
        }
        TextView confirmOrderTvAddress = confirmOrderAdapter.getConfirmOrderTvAddress();
        TextView confirmOrderTvPeople = confirmOrderAdapter.getConfirmOrderTvPeople();
        TextView confirmOrderTvPhone = confirmOrderAdapter.getConfirmOrderTvPhone();
        O2oAddressBean.ListsBean o2oaddresslist = updateaddressevent.listsBean;
        if (o2oaddresslist == null) {
            return;
        }
        o2oaddresslistId = o2oaddresslist.getId();
        latitude = o2oaddresslist.getLatitude();
        longitude = o2oaddresslist.getLongitude();
        confirmOrderTvPeople.setText(TextUtils.isEmpty(o2oaddresslist.getName()) ? "" : o2oaddresslist.getName());
        confirmOrderTvAddress.setText(TextUtils.isEmpty(o2oaddresslist.getAddress()) ? "" : o2oaddresslist.getAddress());
        confirmOrderTvPhone.setText(TextUtils.isEmpty(o2oaddresslist.getTelephone()) ? "" : o2oaddresslist.getTelephone());
        phone = o2oaddresslist.getTelephone();
    }

    /**
     * 去结算成功回调
     *
     * @param goPayBean
     */
    @Override
    public void onSuccess(GoPayBean goPayBean) {

    }

    //显示立即送出对话框
    public void showLjsc(final TextView textView, final List<GoPayBean.ListsBean> goPayBeanLists) {
        if (goPayBeanLists == null) {
            return;
        }

        final String[] options = new String[goPayBeanLists.size()];
        for (int i = 0; i < goPayBeanLists.size(); i++) {
            int time = goPayBeanLists.get(i).getTime();
            String countTimeByLongHour = TimeTools.getCountTimeByLongHour(time);
            options[i] = countTimeByLongHour + "(" + goPayBeanLists.get(i).getDistrib_money() + "元配送费" + ")";
        }

        OptionPicker optionPicker = new OptionPicker(getActivity(), options);
        String week = getWeek();
        optionPicker.setTitleText("今天（" + week + "）");
        optionPicker.setSubmitTextColor(Color.parseColor("#FF2E00"));
        optionPicker.show();
        optionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(String option, int selectedIndex) {
                textView.setText("（" + options[selectedIndex] + "）");

                for (int i = 0; i < goPayBeanLists.size(); i++) {

                    if (i == selectedIndex) {

                        String times = TimeTools.getCountTimeByLongHour(goPayBeanLists.get(i).getTime());
                        reachTime = times.replace(":", ",");
                    }
                }
            }
        });
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

    /**
     * 提交订单成功
     *
     * @param data
     */
    @Override
    public void onSuccessAffirm(O2oorderCommitBean data) {

        if (data != null) {

            //友盟统计
            MobclickAgent.onEvent(getActivity(), "o2oPay");

            body = data.getOrder_num() + "," + 1;//1-立即购买，购物车支付2-我的订单
            order_num = data.getOrder_num();
            sale_price = data.getSale_price();
            banlanceMoney = data.getBalance();

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
                    MobclickAgent.onEvent(getActivity(), "alio2oPay");
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
                    MobclickAgent.onEvent(getActivity(), "banlanceo2oPay");
                }
            };

            mPop = new PayPopupWindow(getActivity(), data.getSale_price(), banlanceMoney, wxOnclickListner,
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
                    MobclickAgent.onEvent(getActivity(), "bank2oPay");
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDismiss();
                    showShortToast("支付取消");
                    removeFragment();

                    //发消息刷新购物车数据

                }
            });

            mPop.setFocusable(false);

            mPop.showAtLocation(confirmOrderSubmit, Gravity.BOTTOM | Gravity.CENTER, 0, 0);
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


    /**
     * call alipay sdk pay. 调用支付宝SDK支付
     */
    public void aliPay() {

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

    /**
     * 获取余额成功
     *
     * @param data
     */
    @Override
    public void SuccessgetBanlance(UserBanclanceBean data) {
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
        intent.putExtra("money", sale_price);
        startActivity(intent);
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

    /***
     * 验证是否设置过支付密码
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
                intent.putExtra("tag", "3");
                startActivity(intent);

            } else {

                //检测输入的支付密码
                Intent intent = new Intent(getActivity(), CheckPayPwdActivity.class);
                intent.putExtra("tag", "3");
                startActivity(intent);
            }
        }
    }

    @Override
    public String getSalePrice() {
        return sale_price;
    }

    /***
     * 余额支付成功
     * @param data
     */
    @Override
    public void onSuccessYe(BanlanceBean data) {

        removeFragment();
        Intent intent = new Intent(getActivity(), ArrirmSuccessActivity.class);
        intent.putExtra("tag", "o2o");
        intent.putExtra("message", "支付成功");
        intent.putExtra("type", "0");
        intent.putExtra("isOK", true);
        intent.putExtra("money", sale_price);
        startActivity(intent);
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
        intent.putExtra("money", sale_price);
        startActivity(intent);
    }


    /**
     * 设置支付密码成功的回调
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(SetPayPwdEvent event) {
        String invoice = event.getStr();

        if (invoice.equals("3")) {

            //检测输入的支付密码
            Intent intent = new Intent(getActivity(), CheckPayPwdActivity.class);
            intent.putExtra("tag", "3");
            startActivity(intent);
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

        if (invoice.equals("3")) {

            mPresenter.yEpay();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //注册监听
        ((ConfirmOrderActivity) getActivity()).setBackListener(this);
        ((ConfirmOrderActivity) getActivity()).setInterception(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //取消监听
        ((ConfirmOrderActivity) getActivity()).setBackListener(null);
        ((ConfirmOrderActivity) getActivity()).setInterception(false);
    }

    /**
     * 虚拟键返回监听回调
     */
    @Override
    public void onBackForward() {

        if (mPop != null && mPop.isShowing()) {

//            EventBus.getDefault().post(new ShopCarRefreshEvent());
//            EventBus.getDefault().post(new OrderDeatailRefreshEvent());
            showShortToast("支付取消");
            onDismiss();
        }
        removeFragment();
    }


    @Override
    public void onDismiss() {

        if (mPop != null) {
            mPop.dismiss();
        }
    }

    //商家ID
    @Override
    public String merchId() {
        return merchId;
    }

    //商品ID
    @Override
    public String goodslist() {

        String replace = "";
        if (mGoodslist != null && mGoodslist.length() > 0) {
            replace = mGoodslist.replace("\\", "");
        }
        return replace;
    }

    //是否到店自提
    @Override
    public String getis_self() {
        return is_self;
    }

    //是否开票
    @Override
    public String getis_invoice() {
        return null;
    }

    //开票类型
    @Override
    public String getinvoice_type() {
        return null;
    }

    //税号
    @Override
    public String getTaxNum() {
        return null;
    }

    //发票抬头
    @Override
    public String getTitle() {
        return null;
    }

    //自取电话
    @Override
    public String getphone() {
        return phone;
    }

    //收货ID
    @Override
    public String addressid() {

        return TextUtils.isEmpty(o2oaddresslistId) ? "" : o2oaddresslistId;
    }

    //用餐人数
    @Override
    public String dinnersetcount() {

        String trim = "";

        if (confirmOrderAdapter != null) {
            trim = confirmOrderAdapter.getConfirmOrderCjSum().getText().toString().trim();
        }

        return TextUtils.isEmpty(trim) ? "" : trim;
    }

    //预约时间
    @Override
    public String reachtime() {

        if (reachTime.equals(gopaybean.getReach_time())) {
            reachTime = "";
        }

        return reachTime;
    }

    //备注
    @Override
    public String getNote() {
        if (remarksText==null) {
            return "";
        } else {
            return remarksText;
        }
    }

    //收货地址经度
    @Override
    public String longitude() {
        return longitude;
    }

    //收货地址纬度
    @Override
    public String latitude() {
        return latitude;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RemarksEditTextEvent event) {
        remarksText = event.getEditText();
        if (!TextUtils.isEmpty(remarksText)) {
            beizhuText.setText(remarksText);
        }

    }

}
package com.android.p2pflowernet.project.view.fragments.affirm;

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
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.AffirmIndentAdapter;
import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.AdressMangerBean;
import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.CommitAffirmBean;
import com.android.p2pflowernet.project.entity.OrderDetailBean;
import com.android.p2pflowernet.project.entity.PayAlResultBean;
import com.android.p2pflowernet.project.entity.UnionPayEntity;
import com.android.p2pflowernet.project.event.AdressMangerEvent;
import com.android.p2pflowernet.project.event.AffirmEvent;
import com.android.p2pflowernet.project.event.CheckPayEvent;
import com.android.p2pflowernet.project.event.InvoiceEvent;
import com.android.p2pflowernet.project.event.OrderDeatailRefreshEvent;
import com.android.p2pflowernet.project.event.PayCancleEvent;
import com.android.p2pflowernet.project.event.SetPayPwdEvent;
import com.android.p2pflowernet.project.event.ShopCarRefreshEvent;
import com.android.p2pflowernet.project.event.UPpayEvent;
import com.android.p2pflowernet.project.event.UserBanclanceBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.PayPopupWindow;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.AppPartnerAlertDialog;
import com.android.p2pflowernet.project.view.fragments.affirm.invoice.InvoiceActivity;
import com.android.p2pflowernet.project.view.fragments.affirm.partnerapttitude.BuyPartnerAptitudeActiivty;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.AuthResult;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.check.CheckPayPwdActivity;
import com.android.p2pflowernet.project.view.fragments.mine.setting.adress.AdressMangerActiivty;
import com.android.p2pflowernet.project.view.fragments.trade.pay.PayResult;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.unionpay.UPPayAssistEx;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.android.p2pflowernet.project.constant.Constants.PARTNER;
import static com.android.p2pflowernet.project.constant.Constants.RSA_PRIVATE;
import static com.android.p2pflowernet.project.constant.Constants.SELLER;

/**
 * Created by caishen on 2017/10/28.
 * by--确认订单的页面
 */

public class AffirmIndentFragment extends KFragment<IArrirmIndentView, IArrirmIndentPrenter>
        implements IArrirmIndentView, NormalTopBar.normalTopClickListener, PopupWindow.OnDismissListener,
        AffirmIndentActivity.FragmentBackListener {

    private static final String TARGET_ID = "";
    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.ex_listView)
    ExpandableListView exListView;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_rabate)
    TextView tvRabate;
    @BindView(R.id.tv_rabate1)
    TextView tvRabate1;
    @BindView(R.id.tv_pay_money)
    TextView tvPayMoney;
    @BindView(R.id.tv_pay_money1)
    TextView tvPayMoney1;
    @BindView(R.id.btn_indent)
    Button btnIndent;
    private TextView tv_person;
    private TextView tv_phone;
    private TextView tv_adress;
    private PayPopupWindow mPop;
    private WindowManager.LayoutParams params;
    final IWXAPI msgApi = WXAPIFactory.createWXAPI(getActivity(), null);
    private TextView invoices;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private String money;//支付成功金额
    private String adressId = "";//管理地址id

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
                        Intent intent = new Intent(getActivity(), ArrirmSuccessActivity.class);
                        intent.putExtra("tag", "b2c");
                        intent.putExtra("message", "支付成功");
                        intent.putExtra("type", "1");
                        intent.putExtra("isOK", true);
                        intent.putExtra("money", money);
                        startActivity(intent);

                    } else {

                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        removeFragment();
                        Intent intent = new Intent(getActivity(), ArrirmSuccessActivity.class);
                        intent.putExtra("tag", "b2c");
                        intent.putExtra("message", "支付失败");
                        intent.putExtra("type", "1");
                        intent.putExtra("isOK", false);
                        intent.putExtra("money", money);
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
    private RelativeLayout rl_add_adress;
    private List<OrderDetailBean.ListBean> list;
    private OrderDetailBean data;
    private ShapeLoadingDialog shapeLoadingDialog;
    private String spec_id = "";
    private String count = "";
    private String select = "";
    private String invoice_state = "";
    private String tax_num = "";
    private String userType = "";
    private String invoice = "";
    private String source = "";
    private String body;
    private String sale_price = "";
    private String order_num;
    private String paySource = "";//1为支付宝2为银联
    private String subject = "";

    @Override
    public IArrirmIndentPrenter createPresenter() {

        return new IArrirmIndentPrenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        data = (OrderDetailBean) arguments.getSerializable("data");
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_affirm;
    }


    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(2000)
                .build();

        //初始化合伙人弹窗
//        showAlertMsgDialog("申请成为合伙人才能享受最高 花返利率哦！", "温馨提示", "现在去申请", "取消");

        //初始化状态栏渐变色
        Utils.setStatusBar(getActivity(), 0, false);
        //扩大可点击事件
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setBackground(getResources().getDrawable(R.drawable.app_statusbar_bg));
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setTitleText("确认订单");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setTopClickListener(this);

        shapeLoadingDialog.show();

        //添加显示管理地址的头部
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.affirm_head_layout, null);
        LinearLayout ll_location = (LinearLayout) headView.findViewById(R.id.ll_location);
        tv_person = (TextView) headView.findViewById(R.id.tv_person);
        tv_phone = (TextView) headView.findViewById(R.id.tv_phone);
        tv_adress = (TextView) headView.findViewById(R.id.tv_adress);
        rl_add_adress = (RelativeLayout) headView.findViewById(R.id.rl_add_adress);

        //设置去地址管理界面
        rl_add_adress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AdressMangerActiivty.class);
                intent.putExtra("state", "1");
                startActivity(intent);
            }
        });

        //设置添加地址的点击事件
        ll_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AdressMangerActiivty.class);
                intent.putExtra("state", "1");
                startActivity(intent);
            }
        });

        //添加一个可添加地址的头部
        exListView.addHeaderView(headView);

        initData();
    }

    /**
     * 填写发票的回调
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(InvoiceEvent event) {

        invoice = event.getTitle();
        tax_num = event.getTax_num();
        userType = event.getUserType();
        invoices.setText(invoice);
    }

    /**
     * 移除fragment，并刷新购物车数据
     *
     * @param event 是否移除fragment
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AffirmEvent event) {
        removeFragment();
        //发消息刷新购物车数据
        EventBus.getDefault().post(new ShopCarRefreshEvent());
    }

    /**
     * 地址选择的回调
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AdressMangerEvent event) {

        AdressMangerBean data = event.getData();
        List<AdressMangerBean.UalBean> ual = data.getUal();
        AdressMangerBean.UalBean ualBean = ual.get(event.getPosition());
        String id = ualBean.getId();
        String adress = ualBean.getLocation() + ualBean.getAddress();
        String name = ualBean.getName();
        String telephone = ualBean.getTelephone();
        tv_adress.setText(adress == null ? "" : adress);
        tv_phone.setText(telephone == null ? "" : telephone);
        tv_person.setText(name == null ? "" : name);
        adressId = id;

        //重新确认订单信息
        mPresenter.affirmOrder();

    }

    //初始化数据
    private void initAffirm(OrderDetailBean event) {


    }

    //初始化数据
    @Override
    public void initData() {

        if (data != null) {

            spec_id = "";
            count = "";
            select = "";
            count = data.getNum();
            select = data.getSelect();
            invoice_state = data.getInvoice_state();
            source = data.getSource();

            List<OrderDetailBean.ListBean> list = data.getList();
            for (int i = 0; i < list.size(); i++) {
                List<OrderDetailBean.ListBean.GoodsBean> goods = list.get(i).getGoods();
                for (int i1 = 0; i1 < goods.size(); i1++) {
                    spec_id += goods.get(i1).getSpec_id() + ",";
                }
            }
            spec_id = spec_id.toString().substring(0, spec_id.toString().lastIndexOf(","));

            list = data.getList();

            if (list == null) {

                if (shapeLoadingDialog != null) {
                    shapeLoadingDialog.dismiss();
                }
                return;
            }

            OrderDetailBean.UserBean user = data.getUser();
            if (TextUtils.isEmpty(user.getAddress()) || TextUtils.isEmpty(user.getAddress_id())) {
                rl_add_adress.setVisibility(View.VISIBLE);
            } else {
                rl_add_adress.setVisibility(View.GONE);
            }

            //设置数据
            tvNumber.setText("共" + data.getNum() + "件");
            tvRabate1.setText(data.getHuafan() == null ? "" : data.getHuafan());
            tvPayMoney1.setText(data.getSale_price() == null ? "" : data.getSale_price());
            tv_person.setText(user.getName() == null ? "" : user.getName());
            tv_phone.setText(user.getTelephone() == null ? "" : user.getTelephone());
            tv_adress.setText(user.getAddress() == null ? "" : user.getLocation() + user.getAddress());
            adressId = user.getAddress_id();

            //设置适配器
            AffirmIndentAdapter mAdapter = new AffirmIndentAdapter(getActivity(), data);

            //设置属性 GroupIndicator 去掉向下箭头
            exListView.setGroupIndicator(null);
            exListView.setAdapter(mAdapter);

            if (shapeLoadingDialog != null) {

                shapeLoadingDialog.dismiss();
            }

            //设置 发票的点击事件
            mAdapter.setInvoiceOnClickLintener(new AffirmIndentAdapter.InvoiceOnClickLintener() {
                @Override
                public void onItemClick(View view, TextView invoice1) {
                    invoices = invoice1;

                    if (invoice_state.equals("0")) {//不支持开发票

                        showShortToast("暂不支持开发票");

                    } else {

                        Intent intent = new Intent(getActivity(), InvoiceActivity.class);
                        intent.putExtra("invoice", invoice);
                        intent.putExtra("tax_num", tax_num);
                        intent.putExtra("userType", userType);
                        startActivity(intent);
                    }
                }
            });

            //关键步骤4:初始化，将ExpandableListView以展开的方式显示
            for (int i = 0; i < mAdapter.getGroupCount(); i++) {
                exListView.expandGroup(i);
            }
        }
    }

    public static KFragment newIntence(OrderDetailBean data) {

        Bundle bundle = new Bundle();
        AffirmIndentFragment affirmIndentFragment = new AffirmIndentFragment();
        bundle.putSerializable("data", data);
        affirmIndentFragment.setArguments(bundle);
        return affirmIndentFragment;
    }

    //提交订单去收钱
    @OnClick(R.id.btn_indent)
    public void onClick() {

        mPresenter.getBalance();

        //友盟统计
        MobclickAgent.onEvent(getActivity(), "commitIndent");
    }

    @Override
    public void onLeftClick(View view) {

        //结束页面，返回上一个页面
        removeFragment();
    }

    @Override
    public void onRightClick(View view) {

    }

    @Override
    public void onTitleClick(View view) {

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

        Alipay();
        onDismiss();
    }

    @Override
    public void onError(String errorMsg) {

        showShortToast(errorMsg);
    }

    @Override
    public void AlipaySuccess() {

    }

    @Override
    public void UnionPaySuccess(UnionPayEntity unionPayResult) {
        // 银联支付返回值
    }

    @Override
    public void WeChatPaySuccess() {

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
    public void onSuccessed(String message) {

        showShortToast(message);
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

    /**
     * 判断是否取消支付
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(PayCancleEvent event) {

        if (mPop != null) {

            //发消息刷新购物车列表数据
            EventBus.getDefault().post(new ShopCarRefreshEvent());
            showShortToast("支付取消");
        }
        removeFragment();
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

    /**
     * 余额支付成功
     *
     * @param data
     */
    @Override
    public void onSuccessYe(BanlanceBean data) {

        removeFragment();
        Intent intent = new Intent(getActivity(), ArrirmSuccessActivity.class);
        intent.putExtra("tag", "b2c");
        intent.putExtra("message", "支付成功");
        intent.putExtra("type", "0");
        intent.putExtra("isOK", true);
        intent.putExtra("money", sale_price);
        startActivity(intent);
    }

    @Override
    public String getSpecId() {

        return spec_id;
    }

    @Override
    public String getCount() {

        return count;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public String getSelect() {
        return select;
    }

    @Override
    public String getInvoice() {

        if (!invoice.isEmpty() && invoice_state.equals("1")) {//支持开发票并且填写了发票信息
            return "1";
        } else {
            return "0";
        }
    }

    @Override
    public String getTitle() {
        return invoice;
    }

    @Override
    public String getTaxNum() {
        return tax_num;
    }

    @Override
    public String getTexture() {
        return "1";
    }

    @Override
    public String getUserType() {
        return userType;
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
        intent.putExtra("message", msg);
        intent.putExtra("money", sale_price);
        startActivity(intent);
    }

    @Override
    public void SuccessComit(CommitAffirmBean data) {

        if (data != null) {

            body = data.getOrder_num() + "," + 1;//1-立即购买，购物车支付2-我的订单
            order_num = data.getOrder_num();
            sale_price = data.getSale_price();

            //友盟统计
            MobclickAgent.onEvent(getActivity(), "payPage");

            //微信支付的点击事件
            View.OnClickListener wxOnclickListner = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Utils.isFastDoubleClick()) {

                        return;
                    }

                    mPresenter.WeChatPayPresenter(getActivity());
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
                    MobclickAgent.onEvent(getActivity(), "aliPayPage");
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
                    MobclickAgent.onEvent(getActivity(), "banlancePayPage");
                }
            };

            mPop = new PayPopupWindow(getActivity(), data.getSale_price(), money, wxOnclickListner,
                    zfbxOnClickListener, yeOnClickListener, new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Utils.isFastDoubleClick()) {

                        return;
                    }

                    // 调用银联支付接口请求签名
                    paySource = "2";
                    mPresenter.AlipayPresenter();

                    //友盟统计
                    MobclickAgent.onEvent(getActivity(), "bankPayPage");
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDismiss();
                    showShortToast("支付取消");
                    removeFragment();
                    //发消息刷新购物车数据
                    EventBus.getDefault().post(new ShopCarRefreshEvent());
                }
            });

            mPop.setFocusable(false);

            mPop.showAtLocation(btnIndent, Gravity.BOTTOM | Gravity.CENTER, 0, 0);
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
    public String getAddressId() {
        return adressId;
    }

    @Override
    public String getSalePrice() {
        return sale_price;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public String getOrderNum() {
        return order_num;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    /***
     * 支付宝，银联请求签名成功回调
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

    /**
     * 获取余额成功
     *
     * @param data
     */
    @Override
    public void SuccessgetBanlance(UserBanclanceBean data) {

        if (data != null) {

            money = data.getMoney();
            mPresenter.commitSel();
        }
    }

    /**
     * 支付类型 1，O2O  2，B2c 3,购买合伙人 4，代理人追加，5 代理人购买资质6 团购 7线下扫码支付
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
        return "1";
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
        intent.putExtra("money", money);
        startActivity(intent);
    }

    /***
     * 重新确认订单信息
     * @param data
     */
    @Override
    public void SuccessOrder(OrderDetailBean data) {

        if (data != null) {

            spec_id = "";
            count = "";
            select = "";
            count = data.getNum();
            select = data.getSelect();
            invoice_state = data.getInvoice_state();
            source = data.getSource();

            List<OrderDetailBean.ListBean> list = data.getList();
            for (int i = 0; i < list.size(); i++) {
                List<OrderDetailBean.ListBean.GoodsBean> goods = list.get(i).getGoods();
                for (int i1 = 0; i1 < goods.size(); i1++) {
                    spec_id += goods.get(i1).getSpec_id() + ",";
                }
            }
            spec_id = spec_id.toString().substring(0, spec_id.toString().lastIndexOf(","));

            OrderDetailBean.UserBean user = data.getUser();
            //设置数据
            tvNumber.setText("共" + data.getNum() + "件");
            tvRabate1.setText(data.getHuafan() == null ? "" : data.getHuafan());
            tvPayMoney1.setText(data.getSale_price() == null ? "" : data.getSale_price());

            //设置适配器
            AffirmIndentAdapter mAdapter = new AffirmIndentAdapter(getActivity(), data);

            //设置属性 GroupIndicator 去掉向下箭头
            exListView.setGroupIndicator(null);
            exListView.setAdapter(mAdapter);

            if (shapeLoadingDialog != null) {

                shapeLoadingDialog.dismiss();
            }

            //设置 发票的点击事件
            mAdapter.setInvoiceOnClickLintener(new AffirmIndentAdapter.InvoiceOnClickLintener() {
                @Override
                public void onItemClick(View view, TextView invoice) {
                    invoices = invoice;

                    if (invoice_state.equals("0")) {//不支持开发票

                        showShortToast("暂不支持开发票");

                    } else {

                        Intent intent = new Intent(getActivity(), InvoiceActivity.class);
                        startActivity(intent);
                    }
                }
            });

            //关键步骤4:初始化，将ExpandableListView以展开的方式显示
            for (int i = 0; i < mAdapter.getGroupCount(); i++) {
                exListView.expandGroup(i);
            }
        }
    }

    /**
     * @Title: showAlertMsgDialog @Description: TODO 弹出消息框 @param @param
     * msg @param @param title @param @param positive @param @param
     * negative @return_ticket void 返回类型 @throws
     */
    public void showAlertMsgDialog(String msg, String title, String positive, String negative) {
        AppPartnerAlertDialog alertDialog = new AppPartnerAlertDialog(getActivity()).builder().setTitle(title).setMsg(msg)
                .setPositiveButton(positive, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), BuyPartnerAptitudeActiivty.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(negative, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        alertDialog.show();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //注册监听
        ((AffirmIndentActivity) getActivity()).setBackListener(this);
        ((AffirmIndentActivity) getActivity()).setInterception(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //取消监听
        ((AffirmIndentActivity) getActivity()).setBackListener(null);
        ((AffirmIndentActivity) getActivity()).setInterception(false);
    }

    /**
     * 虚拟键返回监听回调
     */
    @Override
    public void onBackForward() {

        if (mPop != null && mPop.isShowing()) {

            EventBus.getDefault().post(new ShopCarRefreshEvent());
            EventBus.getDefault().post(new OrderDeatailRefreshEvent());
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


    // 支付宝
    public void Alipay() {

        paySource = "1";
        mPresenter.AlipayPresenter();
    }


    /***
     * 友盟统计
     */
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("AffirmIndentPage"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("AffirmIndentPage");
    }
}

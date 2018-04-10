package com.android.p2pflowernet.project.view.fragments.mine.orderflow.orderitemdetail;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
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
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.OrderIndentAdapter;
import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.OrderDetailItemBean;
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
import com.android.p2pflowernet.project.utils.DateUtils;
import com.android.p2pflowernet.project.utils.DatesUtils;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.PayPopupWindow;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.affirm.ArrirmSuccessActivity;
import com.android.p2pflowernet.project.view.fragments.affirm.SetPayPwdActivity;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.check.CheckPayPwdActivity;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.evaluate.EvaluateActivity;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.logistics.LogisticsDetailActivity;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.logistics.info.LogisticsinfoActivity;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.refund.ApplyforRefundActivity;
import com.android.p2pflowernet.project.view.fragments.trade.pay.PayResult;
import com.google.gson.Gson;
import com.unionpay.UPPayAssistEx;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

import static com.android.p2pflowernet.project.constant.Constants.PARTNER;
import static com.android.p2pflowernet.project.constant.Constants.RSA_PRIVATE;
import static com.android.p2pflowernet.project.constant.Constants.SELLER;

/**
 * Created by caishen on 2017/11/10.
 * by--所有订单的点击页面详情
 */

public class OrderItemDetailFragment extends KFragment<IOrderDetailItemView, IOrderDetailItemPresenter>
        implements NormalTopBar.normalTopClickListener, IOrderDetailItemView, PopupWindow.OnDismissListener {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.ex_listView)
    ExpandableListView exListView;
    @BindView(R.id.tv_statu_left)
    TextView tvStatuLeft;
    @BindView(R.id.tv_statu_center)
    TextView tvStatuCenter;
    @BindView(R.id.tv_statu_right)
    TextView tvStatuRight;
    @BindView(R.id.ll_state)
    LinearLayout llState;
    private ImageView ivStatue;
    private TextView tvSatue;
    private TextView tvClose;
    private LinearLayout llWlInfo;
    private LinearLayout llTime;
    private TextView tvSerial;
    private TextView tvCopy;
    private TextView tvTime;
    private int minute = 0;//这是分钟
    private int second = 0;//这是分钟后面的秒数。这里是以30分钟为例的，所以，minute是30，second是0
    private Timer timer;
    private TimerTask timerTask;
    private static final int SDK_PAY_FLAG = 1;
    //这是接收回来处理的消息
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (minute == 0) {
                if (second == 0) {

                    //写业务接口
                    tv_count_time.setText("30分钟到");

                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                    if (timerTask != null) {
                        timerTask = null;
                    }
                } else {
                    second--;
                    if (second >= 10) {
                        tv_count_time.setText("剩" + "0" + minute + "分" + second + "秒");
                    } else {
                        tv_count_time.setText("剩" + "0" + minute + "分" + "0" + second + "秒");
                    }
                }
            } else {
                if (second == 0) {
                    second = 59;
                    minute--;
                    if (minute >= 10) {
                        tv_count_time.setText("剩" + minute + "分" + second + "秒");
                    } else {
                        tv_count_time.setText("剩" + "0" + minute + "分" + second + "秒");
                    }
                } else {
                    second--;
                    if (second >= 10) {
                        if (minute >= 10) {
                            tv_count_time.setText("剩" + minute + "分" + second + "秒");
                        } else {
                            tv_count_time.setText("剩" + "0" + minute + "分" + second + "秒");
                        }
                    } else {
                        if (minute >= 10) {
                            tv_count_time.setText("剩" + minute + "分" + "0" + second + "秒");
                        } else {
                            tv_count_time.setText("剩" + "0" + minute + "分" + "0" + second + "秒");
                        }
                    }
                }
            }
        }
    };

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

    private TextView tv_count_time;
    private String order_id = "";
    private String state;
    private ShapeLoadingDialog shapeLoadingDialog;
    private TextView tv_person1;
    private TextView tv_phone;
    private TextView tv_adress;
    private ImageView iv_wl;
    private TextView tv_time;//创建时间
    private OrderIndentAdapter mAdapter;
    private TextView tv_sh_time;
    private TextView tv_fh_time;
    private TextView tv_fk_time;
    private TextView tv_jy;
    private PayPopupWindow mPop;
    private WindowManager.LayoutParams params;
    private String paySource = "";
    private String payPrice = "";
    private String body = "";
    private String orderNum = "";
    private String isreturn = "";
    private OrderDetailItemBean data;
    private OrderListBean.ListsBean orderlists;

    @Override
    public IOrderDetailItemPresenter createPresenter() {

        return new IOrderDetailItemPresenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_orderitem_detail;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        order_id = arguments.getString("order_id");
        state = arguments.getString("state");
        isreturn = arguments.getString("isreturn");
        orderlists = (OrderListBean.ListsBean) arguments.getSerializable("orderlists");
    }

    @Override
    public void initData() {

        mPresenter.getOrderDetail();
    }

    //根据状态显示按钮的状态
    private void showButtonStatue(String statue) {

        //0-待付款 1-待发货 2-待收货 3-已完成  10-交易完成 11-交易关闭（会员取消）
        // 12-交易关闭（客服取消） 13-交易关闭（支付超时自动关闭）

        if (statue.equals("2")) {//待收货

            llTime.setVisibility(View.VISIBLE);
            ivStatue.setImageResource(R.drawable.image1);

            tvClose.setText("自动确认收货");
            tvSatue.setText("等待买家收货");
            tvSatue.setGravity(Gravity.BOTTOM);
            llState.setVisibility(View.VISIBLE);

            tvStatuRight.setText("确认收货");
            tvStatuRight.setVisibility(View.VISIBLE);

            tvStatuCenter.setText("再次购买");
            tvStatuCenter.setVisibility(View.GONE);

            tvStatuLeft.setText("申请退款");
            tvStatuLeft.setVisibility(View.GONE);

        } else if (statue.equals("1")) {//待发货

            llTime.setVisibility(View.VISIBLE);
            ivStatue.setImageResource(R.drawable.image4);
            tvClose.setText("自动确认收货");
            tvSatue.setText("等待商家发货");
            tvSatue.setGravity(Gravity.BOTTOM);
            llState.setVisibility(View.VISIBLE);

            if (isreturn.equals("0")) {
                tvStatuRight.setText("申请退款");
            } else {
                tvStatuRight.setText("退款中");
            }

            tvStatuRight.setVisibility(View.VISIBLE);
            tvStatuCenter.setText("");
            tvStatuCenter.setVisibility(View.GONE);
            tvStatuLeft.setVisibility(View.GONE);

        } else if (statue.equals("2")) {//已完成

            ivStatue.setImageResource(R.drawable.image2);
            llTime.setVisibility(View.GONE);
            tvSatue.setText("交易完成");
            tvSatue.setGravity(Gravity.CENTER_VERTICAL);

            llState.setVisibility(View.GONE);

            tvStatuRight.setText("去评价");
            tvStatuRight.setVisibility(View.GONE);

            tvStatuCenter.setText("再次购买");
            tvStatuCenter.setVisibility(View.GONE);

            tvStatuLeft.setText("申请退货");
            tvStatuLeft.setVisibility(View.GONE);

        } else if (statue.equals("11") || statue.equals("12") || statue.equals("13")) {//交易关闭

            ivStatue.setImageResource(R.drawable.image3);
            tvSatue.setText("交易关闭");
            llTime.setVisibility(View.GONE);
            tvSatue.setGravity(Gravity.CENTER_VERTICAL);

            llState.setVisibility(View.GONE);

            tvStatuRight.setText("再次购买");
            tvStatuRight.setVisibility(View.GONE);

            tvStatuCenter.setVisibility(View.GONE);

            tvStatuLeft.setVisibility(View.GONE);

        } else if (statue.equals("0")) {//待付款

            ivStatue.setImageResource(R.drawable.icon_order_image);
            tvSatue.setText("等待买家付款");
            llTime.setVisibility(View.VISIBLE);
            tvSatue.setGravity(Gravity.BOTTOM);
            timerTask = new TimerTask() {

                @Override
                public void run() {
                    Message msg = new Message();
                    msg.what = 0;
                    handler.sendMessage(msg);
                }
            };

            llState.setVisibility(View.VISIBLE);
            timer = new Timer();
            timer.schedule(timerTask, 0, 1000);
            tv_count_time.setText("剩" + minute + "分" + second + "秒");
            tvStatuRight.setText("付款");
            tvStatuRight.setVisibility(View.VISIBLE);
            tvStatuCenter.setText("取消订单");
            tvStatuCenter.setVisibility(View.VISIBLE);
            tvStatuLeft.setVisibility(View.GONE);


        } else if (statue.equals("10")) {//交易完成

            ivStatue.setImageResource(R.drawable.image2);
            tvSatue.setText("交易完成");
            tvSatue.setGravity(Gravity.CENTER_VERTICAL);
            llTime.setVisibility(View.GONE);

            llState.setVisibility(View.GONE);
            tvStatuRight.setVisibility(View.GONE);
            tvStatuCenter.setVisibility(View.GONE);
            tvStatuLeft.setVisibility(View.GONE);

        } else if (statue.equals("6")) {//待评价

            ivStatue.setImageResource(R.drawable.image2);
            tvSatue.setText("等待卖家评价");
            tvSatue.setGravity(Gravity.CENTER_VERTICAL);
            llTime.setVisibility(View.GONE);

            llState.setVisibility(View.GONE);
            tvStatuRight.setVisibility(View.GONE);
            tvStatuCenter.setVisibility(View.GONE);
            tvStatuLeft.setVisibility(View.GONE);

        } else if (state.equals("3")) {//已完成

            ivStatue.setImageResource(R.drawable.image2);
            tvSatue.setText("买家已收货");
            tvSatue.setGravity(Gravity.CENTER_VERTICAL);
            llTime.setVisibility(View.GONE);

            llState.setVisibility(View.GONE);
            tvStatuRight.setVisibility(View.GONE);
            tvStatuCenter.setVisibility(View.GONE);
            tvStatuLeft.setVisibility(View.GONE);


        } else {

            llState.setVisibility(View.GONE);
            tvStatuRight.setVisibility(View.GONE);
            tvStatuCenter.setVisibility(View.GONE);
            tvStatuLeft.setVisibility(View.GONE);
        }
    }


    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setTitleText("订单详情");
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setTopClickListener(this);
        Utils.setStatusBar(getActivity(), 0, false);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();
        //添加头部
        addHead();
        //添加尾部
        addFoot();
        //初始化数据
        initData();
    }

    //添加尾部
    private void addFoot() {

        View footView = View.inflate(getActivity(), R.layout.order_detail_foot_layout, null);
        //订单编号
        tvSerial = (TextView) footView.findViewById(R.id.tv_serial);
        //订单时间
        tvTime = (TextView) footView.findViewById(R.id.tv_time);
        //复制
        tvCopy = (TextView) footView.findViewById(R.id.tv_copy);
        //收货时间
        tv_sh_time = (TextView) footView.findViewById(R.id.tv_sh_time);
        //付款时间
        tv_fk_time = (TextView) footView.findViewById(R.id.tv_fk_time);
        //发货时间
        tv_fh_time = (TextView) footView.findViewById(R.id.tv_fh_time);
        //交易编号
        tv_jy = (TextView) footView.findViewById(R.id.tv_jy);

        //添加订单尾部
        exListView.addFooterView(footView);
    }

    //添加显示管理地址的头部
    private void addHead() {
        //添加显示管理地址的头部
        View headView = View.inflate(getActivity(), R.layout.order_detail_head_layout, null);
        tv_count_time = (TextView) headView.findViewById(R.id.tv_count_time);
        ivStatue = (ImageView) headView.findViewById(R.id.iv_statu);
        tvSatue = (TextView) headView.findViewById(R.id.tv_statu);
        tvClose = (TextView) headView.findViewById(R.id.tv_close);
        llWlInfo = (LinearLayout) headView.findViewById(R.id.ll_wl_info);
        llTime = (LinearLayout) headView.findViewById(R.id.ll_time_statue);
        tv_person1 = (TextView) headView.findViewById(R.id.tv_person1);
        tv_phone = (TextView) headView.findViewById(R.id.tv_phone);
        tv_adress = (TextView) headView.findViewById(R.id.tv_adress);
        iv_wl = (ImageView) headView.findViewById(R.id.iv_wl);
        tv_time = (TextView) headView.findViewById(R.id.tv_time);

        //添加一个可添加地址的头部
        exListView.addHeaderView(headView);
    }

    public static OrderItemDetailFragment newIntence(String order_id, String state, String isreturn,OrderListBean.ListsBean orderlists) {
        Bundle bundle = new Bundle();
        OrderItemDetailFragment orderItemDetailFragment = new OrderItemDetailFragment();
        bundle.putString("state", state);
        bundle.putString("order_id", order_id);
        bundle.putString("isreturn", isreturn);
        bundle.putSerializable("orderlists",orderlists);
        orderItemDetailFragment.setArguments(bundle);
        return orderItemDetailFragment;
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OrderDeatailRefreshEvent userInfoEvent) {
        mPresenter.getOrderDetail();
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
    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask = null;
        }
        minute = -1;
        second = -1;
        super.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public String getOrderId() {

        return order_id;
    }

    @Override
    public void onError(String s) {

        showShortToast(s);
    }

    @Override
    public void showDialog() {

        if (shapeLoadingDialog != null) {

            shapeLoadingDialog.show();
        }
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void SuccessDetail(final OrderDetailItemBean data) {

        if (data != null) {
            this.data=data;
            //设置适配器
            mAdapter = new OrderIndentAdapter(getActivity(), data);
            //设置属性 GroupIndicator 去掉向下箭头
            exListView.setGroupIndicator(null);
            exListView.setAdapter(mAdapter);

            //关键步骤4:初始化，将ExpandableListView以展开的方式显示
            for (int i = 0; i < mAdapter.getGroupCount(); i++) {
                exListView.expandGroup(i);
            }

            payPrice = data.getPay_amount();
            orderNum = data.getOrder_num();
            body = orderNum + "," + "2";//1-立即购买，购物车支付2-我的订单

            //显示订单详情下部的订单编号等
            showFootDetail(data);

            //设置物流的点击事件
            llWlInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), LogisticsDetailActivity.class);

                    startActivity(intent);
                }
            });

            //设置即时通讯的点击事件
            mAdapter.setOnImClickListener(new OrderIndentAdapter.OnImClickListener() {
                @Override
                public void OnImClickListener(View view, int position) {
                    showShortToast("客服正在上线中...");
                }
            });

            //设置电话客服的点击事件
            mAdapter.setOnTelClickListener(new OrderIndentAdapter.OnTelClickListener() {
                @Override
                public void OnTelClickListener(View view, int position) {
                    showShortToast("电话客服正在上线中...");
                }
            });


            //设置复制订单编号的点击事件
            tvCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ClipboardManager myClipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    String text = data.getOrder_num();
                    ClipData text1 = ClipData.newPlainText("text", text);
                    myClipboard.setPrimaryClip(text1);
                    showShortToast("已复制到剪切板");
                }
            });

            //左边按钮状态值监听回调
            mAdapter.setOnLeftItemClickListener(new OrderIndentAdapter.OnLeftItemClickListener() {
                @Override
                public void OnLeftItemClickListener(TextView view, int position) {
                    switch (view.getText().toString()) {
                        case "查看物流"://
                            orderNum = data.getOrder_num();
                            String state = data.getState();
                            OrderDetailItemBean.ListsBean listsBean = data.getLists().get(position);
                            if (listsBean == null) {
                                return;
                            }
                            String waybill_num = listsBean.getWaybill_num();
                            String express_id = listsBean.getExpress_id();
                            Intent intent = new Intent(getActivity(), LogisticsDetailActivity.class);
                            // 订单商品表id
                            intent.putExtra("state", state);
                            intent.putExtra("ordernum", orderNum);
                            intent.putExtra("waybill_num", waybill_num);
                            intent.putExtra("express_id", express_id);
                            startActivity(intent);
                            break;
                        case "退款/退货"://
                            orderNum = data.getOrder_num();
                            // 待退款
                            Intent Refundintent = new Intent(getActivity(), ApplyforRefundActivity.class);
                            Bundle bundle = new Bundle();
                            // 订单商品表id
                            bundle.putString("ogid", "0");
//                            bundle.putString("ogid", data.getId());
                            bundle.putString("ordernum", orderNum);
                            bundle.putSerializable("ordergooddetail", data);
                            bundle.putSerializable("orderlists", orderlists);
                            Refundintent.putExtras(bundle);
                            startActivity(Refundintent);
                            break;
                        case "退款详情":
                            showShortToast("退款详情");
                            break;
                    }
                }
            });

            //右边按钮状态值得监听回调
            mAdapter.setOnRightItemClickListener(new OrderIndentAdapter.OnRightItemClickListener() {
                @Override
                public void OnRightItemClickListener(TextView view, int position) {

                    switch (view.getText().toString()) {
                        case "去评价"://

                            removeFragment();
                            Intent intent = new Intent(getActivity(), EvaluateActivity.class);
                            intent.putExtra("order_id", data.getLists().get(position).getId());
                            intent.putExtra("mesuaName", data.getManufac_name());
                            startActivity(intent);

                            break;
                        case "退款/退货"://
                            // 待退款
                            OrderDetailItemBean.ListsBean listsBean = data.getLists().get(position);
                            if (listsBean == null) {
                                return;
                            }
                            Intent refundintent = new Intent(getActivity(), ApplyforRefundActivity.class);
                            Bundle bundle = new Bundle();
                            // 订单商品表id
//                            bundle.putString("ogid", listsBean.getId());
                            bundle.putString("ogid", "0");
                            bundle.putString("ordernum", orderNum);
                            bundle.putSerializable("ordergooddetail", data);
                            bundle.putSerializable("orderlists", orderlists);
                            refundintent.putExtras(bundle);
                            startActivity(refundintent);
                            break;
                        case "退款详情":

                            showShortToast("退款详情");

                            break;
                    }
                }
            });

            //初始化按钮状态值
            showButtonStatue(state);

            //设置基本数据信息
            tv_person1.setText(data.getCustomer_name() == null ? "" : data.getCustomer_name());//收货人
            tv_phone.setText(data.getCustomer_tel() == null ? "" : data.getCustomer_tel());//电话
            tv_adress.setText(data.getAddress() == null ? "" : data.getAddress());//收货地址
            tv_time.setText(data.getCreated() == null ? "" : data.getCreated());//物流时间

            //计算时间差用于倒计时
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());
            DatesUtils datesUtils = new DatesUtils();
            String str = formatter.format(curDate);
            try {

                //初始化倒计时
                String minutes = datesUtils.getTimeDifferenceMinute(str, data.getDeadline());
                int integer = Integer.parseInt(minutes);

                if (integer > 0) {

                    minute = integer;

                } else {

                    minute = 0;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick({R.id.tv_statu_left, R.id.tv_statu_center, R.id.tv_statu_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_statu_left:

                if (tvStatuLeft.getText().toString().equals("付款")) {
                } else if (tvStatuCenter.getText().toString().equals("取消订单")) {

                    mPresenter.cancleOrder();

                } else if (tvStatuLeft.getText().toString().equals("申请退款")) {
                    if (mAdapter != null) {
                        Intent intent = new Intent(getActivity(), ApplyforRefundActivity.class);
//                        OrderDetailItemBean orderDetailItemBean = mAdapter.getData();
//                        if (orderDetailItemBean == null) {
//                            return;
//                        }
                        orderNum = data.getOrder_num();
                        Bundle bundle = new Bundle();
                        // 订单商品表id
//                        bundle.putString("ogid", data.getId());
                        bundle.putString("ogid", "0");
                        bundle.putString("ordernum", orderNum);
                        bundle.putSerializable("ordergooddetail", data);
                        bundle.putSerializable("orderlists", orderlists);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                } else if (tvStatuLeft.getText().toString().equals("申请退款")) {

                    if (mAdapter != null) {

                        Intent intent = new Intent(getActivity(), ApplyforRefundActivity.class);
//                        OrderDetailItemBean orderDetailItemBean = mAdapter.getData();
//                        if (orderDetailItemBean == null) {
//                            return;
//                        }
                        orderNum = data.getOrder_num();
                        Bundle bundle = new Bundle();
                        // 订单商品表id
//                        bundle.putString("ogid", data.getId());
                        bundle.putString("ogid", "0");
                        bundle.putString("ordernum", orderNum);
                        bundle.putSerializable("ordergooddetail", data);
                        bundle.putSerializable("orderlists", orderlists);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                } else if (tvStatuLeft.getText().toString().equals("申请退货")) {

                    if (mAdapter != null) {
                        Intent intent = new Intent(getActivity(), LogisticsinfoActivity.class);
                        OrderDetailItemBean orderDetailItemBean = mAdapter.getData();
                        if (orderDetailItemBean == null) {
                            return;
                        }
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("refundgooddetail", orderDetailItemBean);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                } else if (tvStatuLeft.getText().toString().equals("确认收货")) {

                    mPresenter.AffirmOrder();
                }

                break;
            case R.id.tv_statu_center:

                if (tvStatuCenter.getText().toString().equals("付款")) {

                } else if (tvStatuCenter.getText().toString().equals("取消订单")) {

                    mPresenter.cancleOrder();

                } else if (tvStatuCenter.getText().toString().equals("申请退款")) {

                    if (mAdapter != null) {
                        Intent intent = new Intent(getActivity(), ApplyforRefundActivity.class);
//                        OrderDetailItemBean orderDetailItemBean = mAdapter.getData();
//                        if (orderDetailItemBean == null) {
//                            return;
//                        }
                        orderNum = data.getOrder_num();
                        Bundle bundle = new Bundle();
                        // 订单商品表id
//                        bundle.putString("ogid", data.getId());
                        bundle.putString("ogid", "0");
                        bundle.putString("ordernum", orderNum);
                        bundle.putSerializable("ordergooddetail", data);
                        bundle.putSerializable("orderlists", orderlists);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                } else if (tvStatuCenter.getText().toString().equals("申请退货")) {
                    if (mAdapter != null) {
                        Intent intent = new Intent(getActivity(), LogisticsinfoActivity.class);
                        OrderDetailItemBean orderDetailItemBean = mAdapter.getData();
                        if (orderDetailItemBean == null) {
                            return;
                        }
                        Bundle bundle = new Bundle();
                        // 订单商品表id
                        bundle.putSerializable("refundgooddetail", orderDetailItemBean);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }


                } else if (tvStatuCenter.getText().toString().equals("确认收货")) {

                    mPresenter.AffirmOrder();
                }


                break;
            case R.id.tv_statu_right:

                if (tvStatuRight.getText().toString().equals("付款")) {

                    mPresenter.getBanlce();

                } else if (tvStatuRight.getText().toString().equals("取消订单")) {

                    mPresenter.cancleOrder();

                } else if (tvStatuRight.getText().toString().equals("申请退款")) {

                    if (isreturn.equals("0")) {//申请退款

                        if (mAdapter != null) {
                            Intent intent = new Intent(getActivity(), ApplyforRefundActivity.class);
//                            OrderDetailItemBean orderDetailItemBean = mAdapter.getData();
//                            if (orderDetailItemBean == null) {
//                                return;
//                            }
                            orderNum = data.getOrder_num();
                            Bundle bundle = new Bundle();
                            // 订单商品表id
//                            bundle.putString("ogid", data.getId());
                            bundle.putString("ogid", "0");
                            bundle.putString("ordernum", orderNum);
                            bundle.putSerializable("ordergooddetail", data);
                            bundle.putSerializable("orderlists", orderlists);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    } else {//退款中

                    }

                } else if (tvStatuRight.getText().toString().equals("申请退货")) {

                    if (mAdapter != null) {
                        Intent intent = new Intent(getActivity(), LogisticsinfoActivity.class);
                        OrderDetailItemBean orderDetailItemBean = mAdapter.getData();
                        if (orderDetailItemBean == null) {
                            return;
                        }
                        // 订单商品表id
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("refundgooddetail", orderDetailItemBean);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                } else if (tvStatuRight.getText().toString().equals("确认收货")) {

                    mPresenter.AffirmOrder();
                }

                break;
        }
    }

    //显示订单详情下部的订单编号等
    private void showFootDetail(OrderDetailItemBean data) {
        //0-待付款 1-待发货 2-待收货 3-已完成  10-交易完成 11-交易关闭（会员取消）
        // 12-交易关闭（客服取消） 13-交易关闭（支付超时自动关闭）
        switch (state) {
            case "0"://代付款

                tvSerial.setVisibility(View.VISIBLE);
                tvSerial.setText("订单编号：" + data.getOrder_num());
                tvTime.setVisibility(View.VISIBLE);
                tvTime.setText("创建时间：" + data.getCreated());
                tv_jy.setVisibility(View.GONE);
                tv_sh_time.setVisibility(View.GONE);
                tv_fh_time.setVisibility(View.GONE);
                tv_fk_time.setVisibility(View.GONE);

                break;
            case "1"://待发货

                //时间戳转date
                String count_down = data.getCount_down();
                if (!TextUtils.isEmpty(count_down)) {
                    DateUtils dateUtils = new DateUtils();
                    String dateToString = DateUtils.timesdate(count_down);
                    DatesUtils datesUtils = new DatesUtils();
                    Date date = datesUtils.string2DateTime(dateToString, "yyyy-MM-dd HH:mm");
                    int day = date.getDay();
                    int minutes = date.getMinutes();
                    int hours = date.getHours();
                    tv_count_time.setText("还剩" + day + "天" + hours + "小时" + minutes + "分");
                } else {
                    tv_count_time.setText("还剩" + 0 + "天" + 0 + "小时" + 0 + "分");
                }

                tvSerial.setVisibility(View.VISIBLE);
                tvSerial.setText("订单编号：" + data.getOrder_num());
                tvTime.setVisibility(View.VISIBLE);
                tvTime.setText("创建时间：" + data.getCreated());
                tv_jy.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(data.getTrade_no())||data.getTrade_no().equals("")){
                    tv_jy.setVisibility(View.GONE);
                }
                tv_jy.setText("交易编号：" + data.getTrade_no());
                tv_sh_time.setVisibility(View.GONE);
                tv_fh_time.setVisibility(View.GONE);
                tv_fk_time.setVisibility(View.VISIBLE);

                DateUtils dateUtils1 = new DateUtils();
                tv_fk_time.setText("付款时间：" + DateUtils.timetodate(data.getPay_time()));

                break;
            case "2"://待收货

                //待收货时间显示
                //时间戳转date
                count_down = data.getCount_down();
                if (!TextUtils.isEmpty(count_down)) {
                    DateUtils dateUtils = new DateUtils();
                    String dateToString = DateUtils.timesdate(count_down);
                    DatesUtils datesUtils = new DatesUtils();
                    Date date = datesUtils.string2DateTime(dateToString, "yyyy-MM-dd HH:mm");
                    int day = date.getDay();
                    int minutes = date.getMinutes();
                    int hours = date.getHours();
                    tv_count_time.setText("还剩" + day + "天" + hours + "小时" + minutes + "分");
                } else {
                    tv_count_time.setText("还剩" + 0 + "天" + 0 + "小时" + 0 + "分");
                }
                tvSerial.setVisibility(View.VISIBLE);
                tvSerial.setText("订单编号：" + data.getOrder_num());
                tvTime.setVisibility(View.VISIBLE);
                tvTime.setText("创建时间：" + data.getCreated());
                tv_jy.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(data.getTrade_no())||data.getTrade_no().equals("")){
                    tv_jy.setVisibility(View.GONE);
                }
                tv_jy.setText("交易编号：" + data.getTrade_no());
                tv_sh_time.setVisibility(View.GONE);
                tv_fh_time.setVisibility(View.VISIBLE);
                tv_fh_time.setText("发货时间：" + data.getDeliv_time());
                tv_fk_time.setVisibility(View.VISIBLE);
                dateUtils1 = new DateUtils();
                tv_fk_time.setText("付款时间：" + DateUtils.timetodate(data.getPay_time()));

                break;
            case "3"://已完成

                tvSerial.setVisibility(View.VISIBLE);
                tvSerial.setText("订单编号：" + data.getOrder_num());
                tvTime.setVisibility(View.VISIBLE);
                tvTime.setText("创建时间：" + data.getCreated());
                tv_jy.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(data.getTrade_no())||data.getTrade_no().equals("")){
                    tv_jy.setVisibility(View.GONE);
                }
                tv_jy.setText("交易编号：" + data.getTrade_no());
                tv_sh_time.setVisibility(View.VISIBLE);
                tv_sh_time.setText("收货时间：" + data.getConfirm_time());
                tv_fh_time.setVisibility(View.GONE);
                tv_fh_time.setText("发货时间：" + data.getDeliv_time());
                tv_fk_time.setVisibility(View.VISIBLE);
                dateUtils1 = new DateUtils();
                tv_fk_time.setText("付款时间：" + DateUtils.timetodate(data.getPay_time()));

                break;
            case "10"://交易完成

                dateUtils1 = new DateUtils();
                tvSerial.setVisibility(View.VISIBLE);
                tvSerial.setText("订单编号：" + data.getOrder_num());
                tvTime.setVisibility(View.VISIBLE);
                tvTime.setText("创建时间：" + data.getCreated());
                tv_jy.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(data.getTrade_no())||data.getTrade_no().equals("")){
                    tv_jy.setVisibility(View.GONE);
                }
                tv_jy.setText("交易编号：" + data.getTrade_no());
                tv_sh_time.setVisibility(View.VISIBLE);
                tv_sh_time.setText("收货时间：" + data.getConfirm_time());
                tv_fh_time.setVisibility(View.GONE);
                tv_fh_time.setText("发货时间：" + data.getDeliv_time());
                tv_fk_time.setVisibility(View.VISIBLE);
                tv_fk_time.setText("付款时间：" + DateUtils.timetodate(data.getPay_time()));

                break;
            case "11"://交易关闭
                dateUtils1 = new DateUtils();
                tvSerial.setVisibility(View.VISIBLE);
                tvSerial.setText("订单编号：" + data.getOrder_num());
                tvTime.setVisibility(View.VISIBLE);
                tvTime.setText("创建时间：" + data.getCreated());
                tv_jy.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(data.getTrade_no())||data.getTrade_no().equals("")){
                    tv_jy.setVisibility(View.GONE);
                }
                tv_jy.setText("交易编号：" + data.getTrade_no());
                tv_sh_time.setVisibility(View.GONE);
                tv_fh_time.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(data.getEnded())) {
                    tv_fh_time.setText("关闭时间：" + data.getEnded());
                } else {
                    tv_fh_time.setText("关闭时间：");
                }
                tv_fk_time.setVisibility(View.GONE);
//                tv_fk_time.setText("付款时间：" + DateUtils.timetodate(data.getPay_time()));

                break;
            case "12"://交易关闭

                dateUtils1 = new DateUtils();
                tvSerial.setVisibility(View.VISIBLE);
                tvSerial.setText("订单编号：" + data.getOrder_num());
                tvTime.setVisibility(View.VISIBLE);
                tvTime.setText("创建时间：" + data.getCreated());
                tv_jy.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(data.getTrade_no())||data.getTrade_no().equals("")){
                    tv_jy.setVisibility(View.GONE);
                }
                tv_jy.setText("交易编号：" + data.getTrade_no());
                tv_sh_time.setVisibility(View.GONE);
                tv_fh_time.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(data.getEnded())) {
                    tv_fh_time.setText("关闭时间：" + data.getEnded());
                } else {
                    tv_fh_time.setText("关闭时间：");
                }
                tv_fk_time.setVisibility(View.GONE);
//                tv_fk_time.setText("付款时间：" + DateUtils.timetodate(data.getPay_time()));

                break;
            case "13"://交易关闭

                dateUtils1 = new DateUtils();
                tvSerial.setVisibility(View.VISIBLE);
                tvSerial.setText("订单编号：" + data.getOrder_num());
                tvTime.setVisibility(View.VISIBLE);
                tvTime.setText("创建时间：" + data.getCreated());
                tv_jy.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(data.getTrade_no())||data.getTrade_no().equals("")){
                    tv_jy.setVisibility(View.GONE);
                }
                tv_jy.setText("交易编号：" + data.getTrade_no());
                tv_sh_time.setVisibility(View.GONE);
                tv_fh_time.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(data.getEnded())) {
                    tv_fh_time.setText("关闭时间：" + data.getEnded());
                } else {
                    tv_fh_time.setText("关闭时间：");
                }
                tv_fk_time.setVisibility(View.GONE);
//                tv_fk_time.setText("付款时间：" + data.getPay_time());

                break;
        }
    }

    /**
     * 取消成功
     *
     * @param message
     */
    @Override
    public void onSuccessCancleOrder(String message) {

        showShortToast("取消成功");
        EventBus.getDefault().post(new OrderDeatailRefreshEvent());
        removeFragment();
    }

    /**
     * 确认收货成功
     *
     * @param message
     */
    @Override
    public void onSuccessAffirm(String message) {

        showShortToast("确认收货成功");
        EventBus.getDefault().post(new OrderDeatailRefreshEvent());
        removeFragment();
    }

    /**
     * 获取余额成功
     *
     * @param data
     */
    @Override
    public void SuccessgetBanlance(UserBanclanceBean data) {

        if (data != null) {

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

            mPop = new PayPopupWindow(getActivity(), payPrice, data.getMoney(), wxOnclickListner,
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

            mPop.showAtLocation(tvStatuRight, Gravity.BOTTOM | Gravity.CENTER, 0, 0);
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
     * 银联支付成功回调
     *
     * @param unionPayResult
     */
    @Override
    public void UnionPaySuccess(UnionPayEntity unionPayResult) {

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
    public String getBody() {
        return body;
    }

    @Override
    public String getOrderNum() {
        return orderNum;
    }

    @Override
    public String getPaySource() {
        return paySource;
    }

    /***
     * 支付宝支付成功
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

    @Override
    public void onSuccessed(String message) {
        showShortToast(message);
    }

    @Override
    public String getSalePrice() {
        return payPrice;
    }

    /***
     * 余额支付成功
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

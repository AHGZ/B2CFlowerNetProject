package com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out.fulldetail;

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
import com.android.p2pflowernet.project.adapter.GroupFullDetailsAdapter;
import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.GroupFullDetailBean;
import com.android.p2pflowernet.project.entity.PayAlResultBean;
import com.android.p2pflowernet.project.entity.TakeOutOrderGroupBean;
import com.android.p2pflowernet.project.event.CheckPayEvent;
import com.android.p2pflowernet.project.event.SetPayPwdEvent;
import com.android.p2pflowernet.project.event.UPpayEvent;
import com.android.p2pflowernet.project.event.UserBanclanceBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out.to_evaluate.GroupTakeOutToEvaluateActivity;
import com.android.p2pflowernet.project.utils.SPUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.PayPopupWindow;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.affirm.ArrirmSuccessActivity;
import com.android.p2pflowernet.project.view.fragments.affirm.SetPayPwdActivity;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.check.CheckPayPwdActivity;
import com.android.p2pflowernet.project.view.fragments.trade.pay.PayResult;
import com.google.gson.Gson;
import com.unionpay.UPPayAssistEx;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import butterknife.BindView;

import static com.android.p2pflowernet.project.constant.Constants.PARTNER;
import static com.android.p2pflowernet.project.constant.Constants.RSA_PRIVATE;
import static com.android.p2pflowernet.project.constant.Constants.SELLER;


/**
 * Created by heguozhong on 2018/1/15/015.
 * 团购明细主界面
 */

public class GroupFullDetailFragment extends KFragment<IGroupFullDetailView, IGroupFullDetailPresenter> implements IGroupFullDetailView,PopupWindow.OnDismissListener{

    //装载团购订单完整详情的recyclerview
    @BindView(R.id.group_full_detail_recyclerview)
    RecyclerView groupFullDetailRecyclerview;
    private String order_num;//订单号
    private ShapeLoadingDialog shapeLoadingDialog;
    private GroupFullDetailsAdapter groupFullDetailsAdapter;
    private PopupWindow mPop;
    private String banlanceMoney;
    private String body;
    private String payPrice;//价格
    private String paySource;
    private WindowManager.LayoutParams params;
    private static final int SDK_PAY_FLAG = 1;
    private TakeOutOrderGroupBean.ListBean group_listBean;
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


    public static KFragment newIntence(String order_num,TakeOutOrderGroupBean.ListBean group_listBean) {
        GroupFullDetailFragment groupFullDetailFragment = new GroupFullDetailFragment();
        Bundle bundle = new Bundle();
        groupFullDetailFragment.setArguments(bundle);
        return groupFullDetailFragment;
    }

    @Override
    public IGroupFullDetailPresenter createPresenter() {
        return new IGroupFullDetailPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.group_full_detail;
    }

    @Override
    public void initData() {
        Bundle arguments = getArguments();
        order_num = arguments.getString("order_num");
        group_listBean = (TakeOutOrderGroupBean.ListBean) arguments.getSerializable("group_listBean");

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

        mPresenter.getGroupOrderDetail();
    }



    @Override
    public void onError(String errorMsg) {
        showShortToast(errorMsg);
    }

    @Override
    public void onSuccess(GroupFullDetailBean groupFullDetailBean) {
        //获取布局管理者
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //设置为垂直排列格式
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //recyclerview应用垂直排列格式
        groupFullDetailRecyclerview.setLayoutManager(linearLayoutManager);
        //设置适配器
        groupFullDetailsAdapter = new GroupFullDetailsAdapter(getActivity(),groupFullDetailBean,order_num,group_listBean);
        groupFullDetailRecyclerview.setAdapter(groupFullDetailsAdapter);

        //调用各种点击事件
        onClicks();
    }

    @Override
    public void onSuccess(String message) {

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
    public String longitude() {
        return String.valueOf(SPUtils.get(getActivity(), "longitude", ""));
    }

    @Override
    public String latitude() {
        return String.valueOf(SPUtils.get(getActivity(), "latitude", ""));
    }

    //各种点击事件
    public void onClicks(){
        //设置订单号复制按钮点击事件
        groupFullDetailsAdapter.setOnCopyClickListener(new GroupFullDetailsAdapter.OnCopyClickListener() {
            @Override
            public void onCopyClick(View v, TextView text) {
                onClickCopy(v,text);
            }

        });
        //设置返回按钮点击事件
        groupFullDetailsAdapter.setOnBackClickListener(new GroupFullDetailsAdapter.OnBackClickListener() {
            @Override
            public void onBackClick(View v) {
                removeFragment();
            }
        });
        //设置图文详情点击事件
        groupFullDetailsAdapter.setOnPhoteDetailsClickListener(new GroupFullDetailsAdapter.OnPhoteDetailsClickListener() {
            @Override
            public void onPhoteDetailsClick(View v) {
                showShortToast("图文详情被点击了");
            }
        });
        //联系店铺点击事件
        groupFullDetailsAdapter.setOnCallClickListener(new GroupFullDetailsAdapter.OnCallClickListener() {
            @Override
            public void onCallClick(View v, String tel) {
                call(tel);
            }
        });

        //去评价点击事件
        groupFullDetailsAdapter.setOnQpjClickListener(new GroupFullDetailsAdapter.OnQpjClickListener() {
            @Override
            public void onQpjClick(View view) {
                Intent intent = new Intent(getActivity(), GroupTakeOutToEvaluateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("good",group_listBean);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //去付款点击事件
        groupFullDetailsAdapter.setOnQfkClickListener(new GroupFullDetailsAdapter.OnQfkClickListener() {
            @Override
            public void onQfkClick(View view) {
                payPrice = group_listBean.getOrder_amount();
                body = order_num + "," + "1";
                pay(payPrice, order_num, body);
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
}

package com.android.p2pflowernet.project.view.fragments.affirm.partnerapttitude;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.event.AgenceOfficeEvent;
import com.android.p2pflowernet.project.event.ApplyforEventTralate;
import com.android.p2pflowernet.project.event.PaySuccessEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner.ImproveInfoActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2017/11/21.
 * by--支付成功页面
 */

public class SuccessPayFragment extends KFragment<ISuccessPayView, ISuccessPayPrenter>
        implements NormalTopBar.normalTopClickListener {
    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.iv_pay_statue)
    ImageView ivPayStatue;
    @BindView(R.id.pay_type)
    TextView payType;
    @BindView(R.id.pay_money)
    TextView payMoney;
    @BindView(R.id.btn_improve)
    Button btnImprove;
    @BindView(R.id.tv_hint1)
    TextView tvHint1;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    private String message;
    private String type;
    private boolean isOK;
    private String money;
    private boolean isadd;//是否是追加金额
    private String type1;//（0.合伙人，1.代理人）
    private String ordernum;

    public static SuccessPayFragment newIntence(String message, String type,
                                                boolean isOK, String money, String type1, boolean isadd, String ordernum) {

        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        bundle.putString("type", type);
        bundle.putBoolean("isOK", isOK);
        bundle.putString("money", money);
        bundle.putString("type1", type1);
        bundle.putBoolean("isadd", isadd);
        bundle.putString("ordernum", ordernum);
        SuccessPayFragment successPayFragment = new SuccessPayFragment();
        successPayFragment.setArguments(bundle);
        return successPayFragment;
    }

    @Override
    public ISuccessPayPrenter createPresenter() {

        return new ISuccessPayPrenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        message = arguments.getString("message");
        type = arguments.getString("type");
        isOK = arguments.getBoolean("isOK");
        money = arguments.getString("money");
        isadd = arguments.getBoolean("isadd");
        type1 = arguments.getString("type1");
        ordernum = arguments.getString("ordernum");
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_successpay;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        if (isOK) {//是支付成功

            if (isadd == true) {//是否是追加金额

                if (type1.equals("0")) {//合伙人

                    tvHint.setVisibility(View.VISIBLE);
                    tvHint1.setVisibility(View.VISIBLE);
                    tvHint1.setText("恭喜您追加合伙人成功");
                    btnImprove.setVisibility(View.VISIBLE);
                    btnImprove.setTag("1");//0,完善个人信息1，填写保单信息
                    btnImprove.setText("填写保单信息");

                } else {//代理人

                    //隐藏保单
                    tvHint.setVisibility(View.GONE);
                    tvHint1.setVisibility(View.VISIBLE);
                    tvHint1.setText("恭喜您追加代理人成功");
                    btnImprove.setVisibility(View.GONE);
                }

            } else {//首次购买

                if (type1.equals("0")) {//合伙人

                    //提示赠送保单
                    tvHint.setVisibility(View.VISIBLE);
                    tvHint1.setVisibility(View.VISIBLE);
                    tvHint1.setText("恭喜您购买合伙人成功");
                    tvHint.setText("花返网将赠送您一份百年人寿保单");
                    btnImprove.setVisibility(View.VISIBLE);
                    btnImprove.setTag("0");//0,完善个人信息1，填写保单信息
                    btnImprove.setText("完善个人信息");

                } else {//代理人

                    //隐藏保单
                    tvHint.setVisibility(View.GONE);
                    tvHint1.setVisibility(View.VISIBLE);
                    tvHint1.setText("恭喜您购买代理人成功");
                    btnImprove.setVisibility(View.GONE);
                    //发送提交代理人信息的接口
                    EventBus.getDefault().post(new PaySuccessEvent(ordernum));
                }
            }

            ivPayStatue.setImageResource(R.drawable.wancheng);
            if (!TextUtils.isEmpty(message)) {
                normalTop.setTitleText("订单" + message);
            }

            if (!TextUtils.isEmpty(type)) {//支付方式
                if (type.equals("0")) {
                    payType.setText("支付方式：余额支付");
                } else if (type.equals("1")) {
                    payType.setText("支付方式：支付宝支付");
                } else if (type.equals("2")) {
                    payType.setText("支付方式：银联支付");
                }

            } else {

                payType.setText("支付方式：");
            }

            if (!TextUtils.isEmpty(money)) {

                payMoney.setText("支付金额:¥" + money);
            }

        } else {//否

            tvHint.setVisibility(View.GONE);
            tvHint1.setVisibility(View.GONE);
            normalTop.setTitleText("支付失败");
            payType.setText("很遗憾，支付失败！");
            payMoney.setText("");
            ivPayStatue.setImageResource(R.drawable.shibai);
            btnImprove.setVisibility(View.GONE);
        }

        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        normalTop.setTopClickListener(this);
        Utils.setStatusBar(getActivity(), 0, false);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
    }


    @OnClick(R.id.btn_improve)
    public void onClick() {//去完善个人信息

        removeFragment();
        if (btnImprove.getTag().equals("0")) {//完善个人信息

            Intent intent = new Intent(getActivity(), ImproveInfoActivity.class);
            startActivity(intent);

        } else if (btnImprove.getTag().equals("1")) {//填写保单信息

            //发消息到入股明细的界面去填写保单信息
            EventBus.getDefault().post(new ApplyforEventTralate(2));
        }
    }

    @Override
    public void onLeftClick(View view) {

        EventBus.getDefault().post(new PaySuccessEvent(ordernum));
        //发送刷新代理办公页面数据
        EventBus.getDefault().post(new AgenceOfficeEvent());
        removeFragment();
    }

    @Override
    public void onRightClick(View view) {

    }

    @Override
    public void onTitleClick(View view) {

    }
}

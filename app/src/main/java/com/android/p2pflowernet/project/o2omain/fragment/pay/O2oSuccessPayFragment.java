package com.android.p2pflowernet.project.o2omain.fragment.pay;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2017/12/29.
 * by--支付是否成功的回调
 */

public class O2oSuccessPayFragment extends KFragment<IO2oSuccessPayView, IO2oSuccessPayPrenter> implements NormalTopBar.normalTopClickListener {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_shop_nick)
    TextView tvShopNick;
    @BindView(R.id.pay_style)
    TextView payStyle;
    @BindView(R.id.ll_style)
    LinearLayout llStyle;
    @BindView(R.id.pay_money)
    TextView payMoney;
    @BindView(R.id.ll_pay_money)
    LinearLayout llPayMoney;
    @BindView(R.id.ll_statue)
    LinearLayout llStatue;
    @BindView(R.id.btn_compile)
    Button btnCompile;
    private String type = "";//0-余额 1-支付宝 2-银联 3-微信
    private String paySales = "";//支付金额
    private boolean isok = false;//是否支付成功


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        type = arguments.getString("type");//0-余额 1-支付宝 2-银联 3-微信
        paySales = arguments.getString("payMoney");
        isok = arguments.getBoolean("isok");
    }

    public static KFragment newIntence(String type, boolean isok, String payMoney) {

        O2oSuccessPayFragment o2oSuccessPayFragment = new O2oSuccessPayFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putBoolean("isok", isok);
        bundle.putString("payMoney", payMoney);
        o2oSuccessPayFragment.setArguments(bundle);
        return o2oSuccessPayFragment;
    }

    @Override
    public IO2oSuccessPayPrenter createPresenter() {

        return new IO2oSuccessPayPrenter();
    }


    @Override
    protected int getLayout() {

        return R.layout.fragment_o2o_affirm_pay_success;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        Utils.setStatusBar(getActivity(), 2, false);
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);

        if (isok) {

            llPayMoney.setVisibility(View.VISIBLE);
            llStyle.setVisibility(View.VISIBLE);
            llStatue.setVisibility(View.GONE);
            normalTop.setTitleText("支付完成");

            switch (type) {//0-余额 1-支付宝 2-银联 3-微信
                case "0":
                    payStyle.setText("余额支付");
                    break;
                case "1":
                    payStyle.setText("支付宝支付");
                    break;
                case "2":
                    payStyle.setText("银联支付");
                    break;
                case "3":
                    payStyle.setText("余微信支付");
                    break;
            }

            //设置支付金额
            payMoney.setText(paySales);

        } else {

            normalTop.setTitleText("支付失败");
            llPayMoney.setVisibility(View.GONE);
            llStyle.setVisibility(View.GONE);
            llStatue.setVisibility(View.VISIBLE);
        }

        initData();
    }


    @OnClick(R.id.btn_compile)
    public void onClick() {//完成
        removeFragment();
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
}

package com.android.p2pflowernet.project.view.fragments.affirm;

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
import com.android.p2pflowernet.project.event.OrderDeatailRefreshEvent;
import com.android.p2pflowernet.project.event.ShopCarRefreshEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.fragment.mine.take_out.OrderTakeOutActivity;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.activity.OrderDetailActivity;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by caishen on 2017/12/6.
 * by--支付成功页面
 */

public class AffirmSuccessFragment extends KFragment<IAffirmSuccessView, IAffirmSuccessPrenter>
        implements NormalTopBar.normalTopClickListener {
    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.iv_pay_statue)
    ImageView ivPayStatue;
    @BindView(R.id.pay_type)
    TextView payType;
    @BindView(R.id.pay_money)
    TextView payMoney;
    @BindView(R.id.btn_detail)
    Button btnDetail;
    private String message = "";//支付回调信息
    private String type = "";//支付类型
    private boolean isOK = false;//支付是否成功
    private String money = "";
    private String tag = "b2c";//区别b2c o2o

    @Override
    public IAffirmSuccessPrenter createPresenter() {
        return new IAffirmSuccessPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_affirm_success;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        message = arguments.getString("message");
        type = arguments.getString("type");
        isOK = arguments.getBoolean("isOK");
        money = arguments.getString("money");
        tag = arguments.getString("tag");
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        Utils.setStatusBar(getActivity(), 0, false);

        //发消息刷新购物车
        EventBus.getDefault().post(new ShopCarRefreshEvent());

        if (isOK) {//是支付成功

            ivPayStatue.setImageResource(R.drawable.wancheng);
            if (!TextUtils.isEmpty(message)) {
                normalTop.setTitleText("订单" + message);
            }
            if (!TextUtils.isEmpty(type)) {
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

            normalTop.setTitleText("订单支付失败");
            payType.setText("很遗憾，支付失败！");
            payMoney.setText("");
            ivPayStatue.setImageResource(R.drawable.shibai);
        }

        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);
    }

    public static KFragment newIntence(String message, String type, boolean isOK, String money, String tag) {

        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        bundle.putString("type", type);
        bundle.putString("money", money);
        bundle.putBoolean("isOK", isOK);
        bundle.putString("tag", tag);
        AffirmSuccessFragment affirmSuccessFragment = new AffirmSuccessFragment();
        affirmSuccessFragment.setArguments(bundle);
        return affirmSuccessFragment;

    }

    @OnClick(R.id.btn_detail)
    public void onClick() {//查看商品详情

        removeFragment();
        if (tag.equals("b2c")) {

            //发消息刷新我的订单列表数据
            EventBus.getDefault().post(new OrderDeatailRefreshEvent());
            startActivity(new Intent(getActivity(), OrderDetailActivity.class).putExtra("index", 1));

        } else {

            //外卖订单列表数据
            startActivity(new Intent(getActivity(), OrderTakeOutActivity.class));
        }
    }

    @Override
    public void onLeftClick(View view) {

        //发消息刷新我的订单列表数据
        EventBus.getDefault().post(new OrderDeatailRefreshEvent());
        removeFragment();
    }

    @Override
    public void onRightClick(View view) {

    }

    @Override
    public void onTitleClick(View view) {

    }
}

package com.android.p2pflowernet.project.view.fragments.affirm.pay.check;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.callback.OnPayStatusListener;
import com.android.p2pflowernet.project.entity.PayPwdBean;
import com.android.p2pflowernet.project.event.CheckPayEvent;
import com.android.p2pflowernet.project.event.SetPayEvent;
import com.android.p2pflowernet.project.event.SetPayPwdEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.MD5Utils;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.PayPwdEditText;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.keyborad.PayBoardDialog;
import com.android.p2pflowernet.project.view.fragments.mine.wallet.bankcard.BankCardInfoActivity;
import com.android.p2pflowernet.project.view.fragments.mine.wallet.bankcard.BankcardInfoFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2017/12/4.
 * by--设置支付密码
 */

public class SetPayPwdFragment extends KFragment<ISetPayPwdView, ISetPayPwdPrenter>
        implements ISetPayPwdView, OnPayStatusListener {

    @BindView(R.id.im_back)
    ImageView imBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_click)
    TextView tvClick;
    @BindView(R.id.checkpaypwd)
    PayPwdEditText checkpaypwd;
    @BindView(R.id.prompt_tv)
    TextView promptTv;
    private PayBoardDialog payBoardDialog;
    private String tag = "";
    private String firstStr = "";//第一次输入的密码
    private String lastStr = "";//第二次输入的密码
    private ShapeLoadingDialog shapeLoadingDialog;

    @Override
    public ISetPayPwdPrenter createPresenter() {
        return new ISetPayPwdPrenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        tag = arguments.getString("tag");
    }

    @Override
    protected int getLayout() {

        return R.layout.mine_checkpaypwd_fragment;
    }

    @Override
    public void initData() {

        //初始化沉浸式
        Utils.setStatusBar(getActivity(), 2, false);
        //增加控件点击区域
        UIUtils.setTouchDelegate(imBack, 50);
        UIUtils.setTouchDelegate(tvClick, 50);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SetPayEvent event) {

        firstStr = event.getFirstStr();
        lastStr = event.getLastStr();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        if (tag.equals("0")) {//0,解绑银行卡
            tvTitle.setText("设置支付密码");
        } else if (tag.equals("1")) {//1,添加银行卡
            tvTitle.setText("设置支付密码");
        } else if (tag.equals("2")) {//设置-修改支付密码
            tvTitle.setText("修改支付密码");
        } else if (tag.equals("3")) {//3,购物车订单
            tvTitle.setText("设置支付密码");
        } else {
            tvTitle.setText("设置支付密码");
        }

        payBoardDialog = new PayBoardDialog(getActivity(), 0).builder();
        payBoardDialog.setPayEdittext(checkpaypwd);
        payBoardDialog.setPrompTv(promptTv);
        payBoardDialog.showborad();

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        payBoardDialog.setOnPayStatusListener(this);
        payBoardDialog.show();
    }

    @OnClick({R.id.im_back, R.id.checkpaypwd})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.im_back:

                // 返回按钮
                removeFragment();

                break;
            case R.id.checkpaypwd:

                if (payBoardDialog != null) {

                    payBoardDialog.cancel();
                }

                checkPay();

                break;
        }
    }

    private void checkPay() {

        payBoardDialog = new PayBoardDialog(getActivity(), 0).builder();
        payBoardDialog.setPayEdittext(checkpaypwd);
        payBoardDialog.setPrompTv(promptTv);
        payBoardDialog.showborad();
        payBoardDialog.setOnPayStatusListener(this);
        payBoardDialog.show();
    }

    public static KFragment newIntence(String tag) {

        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        SetPayPwdFragment setPayPwdFragment = new SetPayPwdFragment();
        setPayPwdFragment.setArguments(bundle);
        return setPayPwdFragment;
    }

    @Override
    public String getFirstStr() {

        return MD5Utils.MD5To32(firstStr);
    }

    @Override
    public String getLastStr() {

        return MD5Utils.MD5To32(lastStr);
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
    public void onSetSuccess(PayPwdBean data) {

        if (tag.equals("0")) {
            removeFragment();
            EventBus.getDefault().post(new SetPayPwdEvent("0"));//0,解绑银行卡

        } else if (tag.equals("1")) {

            removeFragment();
            addFragment(BankcardInfoFragment.newIntence("1"));//1,添加银行卡

        } else if (tag.equals("2")) {//设置-修改支付密码

            removeFragment();
            EventBus.getDefault().post(new SetPayPwdEvent("2"));//设置-修改支付密码

        } else if (tag.equals("3")) {//3,提交订单

            removeFragment();
            EventBus.getDefault().post(new SetPayPwdEvent("3"));//3,提交订单

        } else if (tag.equals("4")) {

            removeFragment();
            EventBus.getDefault().post(new SetPayPwdEvent("4"));//3,购物车订单

        } else if (tag.equals("5")) {//设置-忘记支付密码

            removeFragment();
            EventBus.getDefault().post(new SetPayPwdEvent("5"));//5设置-忘记密码

        } else if (tag.equals("6")) {//购买合伙人资质（代理人资质）

            removeFragment();
            EventBus.getDefault().post(new SetPayPwdEvent("6"));//购买合伙人资质（代理人资质）

        } else if (tag.equals("7")) {// 提现

            removeFragment();
            EventBus.getDefault().post(new SetPayPwdEvent("7"));//提现

        } else if (tag.equals("8")) {//申请云工是否绑定银行卡

            removeFragment();
            Intent intent = new Intent(getActivity(), BankCardInfoActivity.class);
            intent.putExtra("tag", "8");
            startActivity(intent);

        }else if(tag.equals("9")) {

            removeFragment();
            EventBus.getDefault().post(new SetPayPwdEvent("9"));//提现
        }
    }

    @Override
    public void onSuccess(String message) {

        showShortToast(message);

        if (tag.equals("0")) {

            removeFragment();
            EventBus.getDefault().post(new CheckPayEvent("0"));//0,解绑银行卡

        } else if (tag.equals("1")) {

            removeFragment();
            addFragment(BankcardInfoFragment.newIntence("1"));//1,添加银行卡

        } else if (tag.equals("3")) {

            removeFragment();
            EventBus.getDefault().post(new CheckPayEvent("3"));//3,购物车订单

        } else if (tag.equals("2")) {

            removeFragment();
            EventBus.getDefault().post(new SetPayPwdEvent("2"));//设置-修改支付密码

        } else if (tag.equals("4")) {//设置-设置支付密码

            removeFragment();
            EventBus.getDefault().post(new SetPayPwdEvent("4"));//设置-设置支付密码

        } else if (tag.equals("5")) {//设置-忘记支付密码

            removeFragment();
            EventBus.getDefault().post(new SetPayPwdEvent("5"));//5设置-忘记密码

        } else if (tag.equals("6")) {//我的订单

            removeFragment();
            EventBus.getDefault().post(new SetPayPwdEvent("5"));//我的订单

        } else if (tag.equals("7")) {//提现

            removeFragment();
            EventBus.getDefault().post(new SetPayPwdEvent("7"));//提现

        } else if (tag.equals("8")) {//申请云工是否绑定银行卡

            removeFragment();
            Intent intent = new Intent(getActivity(), BankCardInfoActivity.class);
            intent.putExtra("tag", "8");
            startActivity(intent);
        }else if(tag.equals("9")) {

            removeFragment();
            EventBus.getDefault().post(new SetPayPwdEvent("9"));//提现
        }
    }

    @Override
    public void onPayStatus(String right, boolean isPass) {

        if (isPass) {

            // 验证通过事件处理
            payBoardDialog.cancel();

            if (tag.equals("2") || tag.equals("5")) {//修改,,忘记支付密码

                mPresenter.updataPayPwd();

            } else {//设置

                mPresenter.setPayPwd();
            }
        }
    }
}

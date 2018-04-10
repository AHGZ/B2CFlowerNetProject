package com.android.p2pflowernet.project.view.fragments.mine.setting.pay;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.event.CheckPayEvent;
import com.android.p2pflowernet.project.event.SetPayPwdEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.check.CheckPayPwdFragment;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.check.SetPayPwdFragment;
import com.android.p2pflowernet.project.view.fragments.mine.setting.pay.forget.ForgetPayPwdActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2017/12/7.
 * by--设置支付密码
 */

public class SettingPayFragment extends KFragment<ISettingPayView, ISettingPayPrenter>
        implements NormalTopBar.normalTopClickListener, ISettingPayView {
    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.ll_set_pay_pwd)
    LinearLayout llSetPayPwd;
    @BindView(R.id.ll_updata_pay_pwd)
    LinearLayout llUpdataPayPwd;
    @BindView(R.id.ll_forget_pay_pwd)
    LinearLayout llForgetPayPwd;
    private ShapeLoadingDialog shapeLoadingDialog;

    @Override
    public ISettingPayPrenter createPresenter() {

        return new ISettingPayPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_setting_pay;
    }

    @Override
    public void initData() {

        mPresenter.checkPayPwd();

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        Utils.setStatusBar(getActivity(), 0, false);
        normalTop.setTitleText("支付设置");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        initData();
    }

    public static KFragment newIntence() {

        Bundle bundle = new Bundle();
        SettingPayFragment settingPayFragment = new SettingPayFragment();
        settingPayFragment.setArguments(bundle);
        return settingPayFragment;
    }

    @OnClick({R.id.ll_set_pay_pwd, R.id.ll_updata_pay_pwd, R.id.ll_forget_pay_pwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_set_pay_pwd://设置支付密码

                // 去设置支付密码
                addFragment(SetPayPwdFragment.newIntence("4"));

                break;
            case R.id.ll_updata_pay_pwd://修改支付密码

                showShortToast("请输入旧密码");

                //检测输入的支付密码
                addFragment(CheckPayPwdFragment.newIntence("2"));

                break;
            case R.id.ll_forget_pay_pwd://忘记支付密码

                startActivity(new Intent(getActivity(), ForgetPayPwdActivity.class));

                break;
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
        if (invoice.equals("2")) {

            showShortToast("校验成功，请输入新密码");
            addFragment(SetPayPwdFragment.newIntence("2"));
        }
    }

    /**
     * 修改支付密码成功
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SetPayPwdEvent event) {
        String invoice = event.getStr();
        if (invoice.equals("2")) {
            //修改成功
        }
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

    @Override
    public void onError(String s) {

        showShortToast(s);
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
    public void onSuccessCheck(CheckPwdBean data) {
        if (data != null) {

            //0: 否，1： 是
            int is_set = data.getIs_set();
            if (is_set == 0) {

                llSetPayPwd.setVisibility(View.VISIBLE);
                llForgetPayPwd.setVisibility(View.GONE);
                llUpdataPayPwd.setVisibility(View.GONE);

            } else {

                llSetPayPwd.setVisibility(View.GONE);
                llForgetPayPwd.setVisibility(View.VISIBLE);
                llUpdataPayPwd.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onSuccessed(String message) {

        showShortToast(message);
    }
}

package com.android.p2pflowernet.project.view.fragments.mine.setting.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.base.BaseApplication;
import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.UdateUserLoginBean;
import com.android.p2pflowernet.project.event.RevisePasswordEvent;
import com.android.p2pflowernet.project.event.UserInfoEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.SPUtils;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.activity.LoginActivity;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2017/11/18.
 * by--修改登录密码
 */

public class UpdateLoginPwdFragment extends KFragment<IUpdateLoginPwdView, IUpdateLoginPwdPrenter>
        implements NormalTopBar.normalTopClickListener, TextWatcher, IUpdateLoginPwdView {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.ed_a_pwd)
    CustomEditText edAPwd;
    @BindView(R.id.ed_b_pwd)
    CustomEditText edBPwd;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ed_c_pwd)
    CustomEditText edCPwd;
    @BindView(R.id.btn_affirm)
    Button btnAffirm;
    private ShapeLoadingDialog shapeLoadingDialog;

    @Override
    public IUpdateLoginPwdPrenter createPresenter() {
        return new IUpdateLoginPwdPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_update_login_pwd;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        Utils.setStatusBar(getActivity(), 0, false);
        normalTop.setTitleText("修改登录密码");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);
        edAPwd.addTextChangedListener(this);
        edBPwd.addTextChangedListener(this);
        edCPwd.addTextChangedListener(this);

        //初始化加载中进度条
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();
    }

    public static KFragment newIntence() {

        Bundle bundle = new Bundle();
        UpdateLoginPwdFragment updateLoginPwdFragment = new UpdateLoginPwdFragment();
        updateLoginPwdFragment.setArguments(bundle);
        return updateLoginPwdFragment;
    }


    /**
     * @param
     * @return void    返回类型
     * @throws
     * @Title: setButtonBackground
     * @Description: TODO 设置登录按钮的背景颜色
     */
    private void setButtonBackground() {

        String username = edAPwd.getText().toString().trim();
        String userpwd = edBPwd.getText().toString().trim();
        String edc = edCPwd.getText().toString().trim();
        if (username.length() > 0 && userpwd.length() > 0 && edc.length() > 0) {
            btnAffirm.setClickable(true);
            btnAffirm.setBackgroundResource(R.drawable.invoice_pressed_shape);
        } else {
            btnAffirm.setClickable(false);
            btnAffirm.setBackgroundResource(R.drawable.btn_norml);
        }
    }

    @OnClick(R.id.btn_affirm)
    public void onClick() {//确认修改密码

        mPresenter.updateUserPwd();
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (s.length() > 0) {
            setButtonBackground();
        }
    }

    @Override
    public void onError(String s) {

        showShortToast(s);
    }

    @Override
    public String getAPwd() {
        return edAPwd.getText().toString().trim();
    }

    @Override
    public String getBPwd() {
        return edBPwd.getText().toString().trim();
    }

    @Override
    public String getCPwd() {
        return edCPwd.getText().toString().trim();
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void sendCodeSuccess(UdateUserLoginBean sendCodeBean) {

        showShortToast("修改成功");
        EventBus.getDefault().post(new RevisePasswordEvent("RevisePassword"));
        EventBus.getDefault().post(new UserInfoEvent(null));
        Constants.TOKEN = "";
        removeFragment();
        getActivity().finish();

        if (Constants.TOKEN.equals("")) {

            startActivity(new Intent(getActivity(), LoginActivity.class));
            removeFragment();

        } else {

            EventBus.getDefault().post(new UserInfoEvent(null));
            Constants.TOKEN = "";
            SPUtils.put(getActivity(), "token", "");
            SPUtils.put(BaseApplication.getContext(), Constants.ISLOGIN, "");
            SPUtils.put(BaseApplication.getContext(), "region", "");
            removeFragment();
            getActivity().finish();
        }

    }

    @Override
    public void showDialog() {

        shapeLoadingDialog.show();
    }

    @Override
    public void onSuccess(String message) {
        showShortToast("修改成功");
        removeFragment();
    }
}

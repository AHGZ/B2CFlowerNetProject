package com.android.p2pflowernet.project.view.fragments.mine.setting.login;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.ChangePhoneBean;
import com.android.p2pflowernet.project.entity.SendCodeBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.TimeCount;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2017/11/18.
 * by--
 */

public class ChangePhoneFragment extends KFragment<IChangePhoneView, IChangePhonePrenter>
        implements NormalTopBar.normalTopClickListener, TextWatcher, IChangePhoneView {
    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.ed_a_phone)
    CustomEditText edAPhone;
    @BindView(R.id.ed_smscode)
    CustomEditText edSmscode;
    @BindView(R.id.tv_sendcode)
    TextView tvSendcode;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_code)
    TextView tvCode;
    private TimeCount timeCount;
    private ShapeLoadingDialog shapeLoadingDialog;
    private int code = -1;//验证码

    @Override
    public IChangePhonePrenter createPresenter() {
        return new IChangePhonePrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_change_phone;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        Utils.setStatusBar(getActivity(), 0, false);
        normalTop.setTitleText("更换手机号");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);
        edAPhone.addTextChangedListener(this);
        edSmscode.addTextChangedListener(this);

        //初始化倒计时
        timeCount = new TimeCount(60000, 1000);
        timeCount.setCountListener(new TimeCount.CountListener() {
            @Override
            public void onTick(Long time) {

                if (tvSendcode != null) {

                    tvSendcode.setText(String.valueOf(time / 1000).concat("s"));
                    tvSendcode.setEnabled(false);//设置不可点击
                }
            }

            @Override
            public void onFinish() {

                if (tvSendcode != null) {

                    tvSendcode.setText("重新发送");
                    tvSendcode.setEnabled(true);
                }
            }
        });

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();
    }

    public static KFragment newIntence() {

        ChangePhoneFragment changePhoneFragment = new ChangePhoneFragment();
        Bundle bundle = new Bundle();
        changePhoneFragment.setArguments(bundle);
        return changePhoneFragment;
    }

    @OnClick({R.id.tv_sendcode, R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sendcode://发送验证码

                mPresenter.sendCode();

                break;
            case R.id.btn_next://下一步

                if (btnNext.getText().toString().trim().equals("下一步")) {

                    if (edSmscode.getText().toString().trim().equals("")) {
                        showShortToast("请先获取验证码");
                    } else if (edAPhone.getText().toString().equals("")) {
                        showShortToast("请输入手机号");
                    } else if (code != Integer.parseInt(edSmscode.getText().toString().trim())
                            && !TextUtils.isEmpty(edSmscode.getText().toString().trim())) {
                        showShortToast("请输入正确的验证码");

                    } else {

                        showShortToast("请输入新手机号");
                        tvPhone.setText("新手机号");
                        edSmscode.setText("");
                        code = -1;
                        edAPhone.setText("");
                        if (timeCount != null) {
                            timeCount.cancel();
                        }
                        tvSendcode.setEnabled(true);//设置可点击
                        tvSendcode.setText("获取验证码");
                        btnNext.setText("提交");
                    }

                } else {//提交

                    if (edSmscode.getText().toString().trim().equals("")) {
                        showShortToast("请先获取验证码");
                    } else if (edAPhone.getText().toString().equals("")) {
                        showShortToast("请输入手机号");
                    } else if (code != Integer.parseInt(edSmscode.getText().toString().trim())
                            && !TextUtils.isEmpty(edSmscode.getText().toString().trim())) {
                        showShortToast("请输入正确的验证码");
                    } else {
                        mPresenter.changePhone();
                    }
                }
                break;
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

    /**
     * @param
     * @return void    返回类型
     * @throws
     * @Title: setButtonBackground
     * @Description: TODO 设置登录按钮的背景颜色
     */
    private void setButtonBackground() {

        String username = edAPhone.getText().toString().trim();
        String userpwd = edSmscode.getText().toString().trim();
        if (username.length() > 0 && userpwd.length() > 0) {
            btnNext.setClickable(true);
            btnNext.setBackgroundResource(R.drawable.invoice_pressed_shape);
        } else {
            btnNext.setClickable(false);
            btnNext.setBackgroundResource(R.drawable.btn_norml);
        }
    }

    @Override
    public void onError(String s) {

        showShortToast(s);
    }

    @Override
    public String getPhone() {
        return edAPhone.getText().toString().trim();
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

    @Override
    public void sendCodeSuccess(SendCodeBean sendCodeBean) {

        if (sendCodeBean != null) {

            code = sendCodeBean.getCode();
            showShortToast("验证码发送成功");
            timeCount.start();
        }
    }

    @Override
    public void changePhoneSuccess(ChangePhoneBean changePhoneBean) {

    }

    @Override
    public String getCode() {
        return edSmscode.getText().toString().trim();
    }

    @Override
    public void onSuccess(String message) {

        showShortToast(message);
        removeFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timeCount.onFinish();
    }
}

package com.android.p2pflowernet.project.view.fragments.mine.setting.pay.forget;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.SendCodeBean;
import com.android.p2pflowernet.project.entity.ShowPersonInfo;
import com.android.p2pflowernet.project.event.SetPayPwdEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.TimeCount;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.check.SetPayPwdFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2017/12/7.
 * by--忘记支付密码
 */

public class ForgetPayPwdFragment extends KFragment<IForgetPayPwdView, IForgetPayPwdPrenter>
        implements NormalTopBar.normalTopClickListener, TextWatcher, IForgetPayPwdView {

    @BindView(R.id.normal_top)
    NormalTopBar normalTopBar;
    @BindView(R.id.iv_smscode)
    ImageView ivSmscode;
    @BindView(R.id.edittext_smscode)
    CustomEditText edittextSmscode;
    @BindView(R.id.Sendsmsicon_tv)
    TextView SendsmsiconTv;
    @BindView(R.id.nextto_btn)
    Button nexttoBtn;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    private ShapeLoadingDialog shapeLoadingDialog;
    private TimeCount timeCount;
    private String code;
    private String phone = "";

    @Override
    public IForgetPayPwdPrenter createPresenter() {

        return new IForgetPayPwdPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_froget_pay_pwd;
    }

    @Override
    public void initData() {

        mPresenter.getPersonInfo();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸式
        Utils.setStatusBar(getActivity(), 0, false);
        normalTopBar.setTitleText("忘记支付密码");
        normalTopBar.setBackgroundResource(R.drawable.app_statusbar_bg);
        normalTopBar.setTitleTextColor(Color.WHITE);
        normalTopBar.getRightImage().setVisibility(View.GONE);
        normalTopBar.setLeftImageId(R.mipmap.icon_back_white);

        // 扩大事件的点击范围
        UIUtils.setTouchDelegate(normalTopBar.getLeftImage(), 50);
        normalTopBar.setTopClickListener(this);

        edittextSmscode.addTextChangedListener(this);
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        //获取个人信息的手机号
        initData();

        //初始化倒计时
        timeCount = new TimeCount(60000, 1000);
        timeCount.setCountListener(new TimeCount.CountListener() {
            @Override
            public void onTick(Long time) {

                if (SendsmsiconTv != null) {

                    SendsmsiconTv.setText(String.valueOf(time / 1000).concat("s"));
                    SendsmsiconTv.setEnabled(false);//设置不可点击
                }
            }

            @Override
            public void onFinish() {

                if (SendsmsiconTv != null) {

                    SendsmsiconTv.setText("重新发送");
                    SendsmsiconTv.setEnabled(true);
                }
            }
        });
    }

    public static KFragment newIntence() {

        ForgetPayPwdFragment forgetPayPwdFragment = new ForgetPayPwdFragment();
        Bundle bundle = new Bundle();
        forgetPayPwdFragment.setArguments(bundle);
        return forgetPayPwdFragment;
    }

    @OnClick({R.id.Sendsmsicon_tv, R.id.nextto_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Sendsmsicon_tv://发送验证码

                mPresenter.sendCode();

                break;
            case R.id.nextto_btn://下一步

                if (code.equals("")) {

                    showShortToast("请先获取验证码");

                } else if (!edittextSmscode.getText().toString().trim().equals(code)) {

                    showShortToast("请输入正确的验证码");

                } else {

                    //设置支付密码
                    addFragment(new SetPayPwdFragment().newIntence("5"));
                }

                break;
        }
    }


    /**
     * 忘记支付密码设置支付密码成功
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SetPayPwdEvent event) {
        String invoice = event.getStr();
        if (invoice.equals("5")) {

            //修改成功
            timeCount.onFinish();
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
        String username = edittextSmscode.getText().toString().trim();
        if (username.length() > 0) {

            nexttoBtn.setClickable(true);
            nexttoBtn.setBackgroundResource(R.drawable.pay_bg);

        } else {

            nexttoBtn.setClickable(false);
            nexttoBtn.setBackgroundResource(R.drawable.btn_norml);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timeCount.onFinish();
    }

    @Override
    public void onError(String s) {
        showShortToast(s);
    }

    @Override
    public String getPhone() {
        return phone;
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
    public void sendCodeSuccess(SendCodeBean sendCodeBean) {

        showShortToast("验证码发送成功");
        timeCount.start();
        code = sendCodeBean.getCode() + "";
    }

    /**
     * 获取个人信息成功
     *
     * @param data
     */
    @Override
    public void setShowPersonInfo(ShowPersonInfo data) {

        if (data != null) {

            phone = data.getPhone();
            tvHint.setText("本次操作需要短信确认,验证码已发送至手机:" + phone + " ,请按提示操作");
        }
    }
}

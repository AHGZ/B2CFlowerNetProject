package com.android.p2pflowernet.project.view.fragments.register;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.CheckMobileBean;
import com.android.p2pflowernet.project.entity.SendCodeBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.TimeCount;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.activity.PublicWebActivity;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.AppPartnerAlertDialog;
import com.android.p2pflowernet.project.view.fragments.register.setpwd.SetPwdFragment;

import butterknife.BindView;
import butterknife.OnClick;

import static com.android.p2pflowernet.project.utils.Utils.isFastDoubleClick;

/**
 * Created by caishen on 2017/10/16.
 * by--手机注册页面
 */

public class RegistFragment extends KFragment<IRegistView, IRegistPresenter> implements IRegistView,
        NormalTopBar.normalTopClickListener, CustomEditText.IMyRightDrawableClick, TextWatcher {

    @BindView(R.id.normal_top)
    NormalTopBar normalTopBar;
    @BindView(R.id.edittext_phonevalue)
    CustomEditText edittextPhonevalue;
    @BindView(R.id.edittext_smscode)
    CustomEditText edittextSmscode;
    @BindView(R.id.Sendsmsicon_tv)
    TextView sendsmsiconTv;
    @BindView(R.id.checkbox_im)
    CheckBox checkboxIm;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.register_btn)
    Button register_btn;
    private TimeCount timeCount;
    // 加载进度条
    private ShapeLoadingDialog shapeLoadingDialog;
    private String code1 = "";//验证码
    private String textView_content = "同意《花返网用户注册协议》";
    private String phone = "";

    public static RegistFragment newInstance() {

        Bundle args = new Bundle();
        RegistFragment fragment = new RegistFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public IRegistPresenter createPresenter() {

        return new IRegistPresenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.register_fragment;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸式
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.colorstart));
        normalTopBar.setTitleText("手机快速注册");
        normalTopBar.getRightImage().setVisibility(View.GONE);
        normalTopBar.setTitleTextColor(getResources().getColor(R.color.white));
        normalTopBar.setLeftImageId(R.mipmap.icon_back_white);
        normalTopBar.setBackground(getResources().getDrawable(R.drawable.app_statusbar_bg));

        // 扩大事件的点击范围
        UIUtils.setTouchDelegate(normalTopBar.getLeftImage(), 50);
        normalTopBar.setTopClickListener(this);

        edittextPhonevalue.addTextChangedListener(this);
        edittextSmscode.addTextChangedListener(this);
        edittextPhonevalue.setDrawableClick(this);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        //初始化倒计时
        timeCount = new TimeCount(60000, 1000);
        timeCount.setCountListener(new TimeCount.CountListener() {
            @Override
            public void onTick(Long time) {

                if (sendsmsiconTv != null) {

                    sendsmsiconTv.setText(String.valueOf(time / 1000).concat("s"));
                    sendsmsiconTv.setEnabled(false);//设置不可点击
                }
            }

            @Override
            public void onFinish() {

                if (sendsmsiconTv != null) {

                    sendsmsiconTv.setText("重新发送");
                    sendsmsiconTv.setEnabled(true);
                }
            }
        });
        SpannableString spannableString = new SpannableString(textView_content);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.bgColor = Color.parseColor("#EEEEEE");
                ds.setColor(Color.parseColor("#0ac446"));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }

            @Override
            public void onClick(View widget) {
                if (isFastDoubleClick()) {
                    return;
                }
                Intent intent = new Intent(getActivity(), PublicWebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("publicurl", ApiUrlConstant.HFW_REGISTER_AGREEMENT);
                intent.putExtra("tag", "1");//区别是否进入首页,0-由广告页进入首页 1-不进入首页
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }, textView_content.lastIndexOf("《"), textView_content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvDetail.setText(spannableString);
        tvDetail.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onLeftClick(View view) {

        //返回到上一页
        removeFragment();
    }

    @Override
    public void onRightClick(View view) {

    }

    @Override
    public void onTitleClick(View view) {

    }


    @OnClick({R.id.Sendsmsicon_tv, R.id.register_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Sendsmsicon_tv://发送验证码

                mPresenter.checkMoblie();

                break;
            case R.id.register_btn://下一步

                if (edittextPhonevalue.getText().toString().trim().equals("")) {
                    showShortToast("请输入手机号");
                } else if (edittextSmscode.getText().toString().trim().equals("")) {
                    showShortToast("请输入验证码");
                } else if (!checkboxIm.isChecked()) {
                    showShortToast("没有勾选花返网协议哦！");
                } else if (!edittextSmscode.getText().toString().trim().equals(code1)) {
                    showShortToast("请输入正确的验证码");
                } else if (code1.equals("")) {
                    showShortToast("请先获取验证码");
                } else if (!phone.equals(edittextPhonevalue.getText().toString().trim())) {
                    showShortToast("不是通过该手机号获得到的验证码");
                } else {
                    addFragment(SetPwdFragment.newInstance(getPhone()));
                }
                break;
        }
    }

    @Override
    public void rightDrawableClick(View view) {
        switch (view.getId()) {
            case R.id.edittext_phonevalue:

                // 账户清除
                edittextPhonevalue.setText("");

                break;
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
        String userpwd = edittextPhonevalue.getText().toString().trim();
        if (username.length() > 0 && userpwd.length() > 0) {

            register_btn.setClickable(true);
            register_btn.setBackgroundResource(R.drawable.btn_pressed);

        } else {

            register_btn.setClickable(false);
            register_btn.setBackgroundResource(R.drawable.btn_norml);
        }
    }

    @Override
    public String getPhone() {
        phone = edittextPhonevalue.getText().toString().trim();
        return phone;
    }

    @Override
    public void sendCheckMobileSuccess(CheckMobileBean checkMobileBean) {

        if (checkMobileBean == null) {
            return;
        }
        if (checkMobileBean.getIs_used() == 0) {

            mPresenter.sendCode();

        } else {

            showShortToast("手机号已注册");
        }
    }

    @Override
    public void sendCodeSuccess(SendCodeBean sendCodeBean) {

        showShortToast("验证码发送成功");
        timeCount.start();
        code1 = sendCodeBean.getCode() + "";
        showAlertMsgDialog("验证码发送成功", "您的验证码为" + sendCodeBean.getCode(), "确定");
    }

    /**
     * @Title: showAlertMsgDialog @Description: TODO 弹出消息框 @param @param
     * msg @param @param title @param @param positive @param @param
     * negative @return_ticket void 返回类型 @throws
     */
    public void showAlertMsgDialog(String msg, String title, String positive) {
        AppPartnerAlertDialog alertDialog = new AppPartnerAlertDialog(getActivity()).builder().setTitle(title)
                .setMsg(msg).setPositiveButton(positive, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                    }
                });
        alertDialog.show();
    }

    @Override
    public void onError(String errorMsg) {

        showShortToast(errorMsg);
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
    public void onDestroy() {
        super.onDestroy();
        timeCount.onFinish();
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
}

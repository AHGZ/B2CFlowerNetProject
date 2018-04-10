package com.android.p2pflowernet.project.view.fragments.updatepwd;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.CheckMobileBean;
import com.android.p2pflowernet.project.entity.SendCodeBean;
import com.android.p2pflowernet.project.entity.UpdatePwd;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.TimeCount;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.ElastticityScrollView;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;

import static com.android.p2pflowernet.project.R.id.edittext_pwd;

/**
 * Created by caishen on 2017/10/18.
 * by--修改登录密码的页面
 */

public class UpdatePwdFragment extends KFragment<IUpdatePwdView, IUpdatePwdPresenter> implements
        NormalTopBar.normalTopClickListener, IUpdatePwdView, CustomEditText.IMyRightDrawableClick, TextWatcher {


    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.iv_phonetitle)
    ImageView ivPhonetitle;
    @BindView(R.id.edittext_phonevalue)
    CustomEditText edittextPhonevalue;
    @BindView(R.id.register_phone)
    RelativeLayout registerPhone;
    @BindView(R.id.iv_smscode)
    ImageView ivSmscode;
    @BindView(R.id.edittext_smscode)
    CustomEditText edittextSmscode;
    @BindView(R.id.sendsmsicon_tv)
    TextView sendsmsiconTv;
    @BindView(R.id.register_smscode)
    RelativeLayout registerSmscode;
    @BindView(R.id.iv_pwd)
    ImageView ivPwd;
    @BindView(edittext_pwd)
    CustomEditText edittextPwd;
    @BindView(R.id.update_pwd)
    RelativeLayout updatePwd;
    @BindView(R.id.register_layout)
    RelativeLayout registerLayout;
    @BindView(R.id.commit_btn)
    Button commitBtn;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.srcollview)
    ElastticityScrollView srcollview;
    private boolean isHidden = false;
    private PopupWindow popupWindow;
    private ShapeLoadingDialog shapeLoadingDialog;
    private TimeCount timeCount;

    @Override
    public IUpdatePwdPresenter createPresenter() {
        return new IUpdatePwdPresenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.actiivty_updatepassword;
    }

    @Override
    public void initData() {

    }


    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸式
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.colorstart));
        // 初始化标题及相关事件监听
        normalTop.setTitleText("修改密码");
        normalTop.setTitleTextColor(getResources().getColor(R.color.white));
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackground(getResources().getDrawable(R.drawable.app_statusbar_bg));
        normalTop.getRightImage().setVisibility(View.GONE);
        // 扩大事件的点击范围
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);
        // 账户输入框设置右图标
        edittextPhonevalue.setRightDrawable(getResources().getDrawable(R.drawable.edittext_clear_close));
        edittextPwd.setRightDrawable(getResources().getDrawable(R.drawable.icon_close_eye));

        // 新密码输入框设置右图标
        edittextPhonevalue.setDrawableClick(this);
        edittextPwd.setDrawableClick(this);

        edittextPwd.addTextChangedListener(this);
        edittextPhonevalue.addTextChangedListener(this);
        edittextSmscode.addTextChangedListener(this);

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

//        //设置显示密码的选择框
//        cbShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if (isChecked) {//选中查看密码的选项
//
//                    //设置EditText文本为可见的
//                    edittextPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                    edittextPwd1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//
//                } else {
//
//                    //设置EditText文本为隐藏的
//                    edittextPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                    edittextPwd1.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                }
//            }
//        });
    }


    public static KFragment newInstance() {
        return new UpdatePwdFragment();
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
    public void rightDrawableClick(View view) {
        switch (view.getId()) {
            case R.id.edittext_phonevalue://账户删除信息

                //手机号清除
                edittextPhonevalue.setText("");

                break;
            case R.id.edittext_pwd:
                // 密码切换
                if (isHidden) {
                    //设置EditText文本为可见的
                    edittextPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edittextPwd.setRightDrawable(getResources().getDrawable(R.drawable.icon_open_eye));
                } else {
                    //设置EditText文本为隐藏的
                    edittextPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edittextPwd.setRightDrawable(getResources().getDrawable(R.drawable.icon_close_eye));
                }
                isHidden = !isHidden;
                edittextPwd.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = edittextPwd.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
        }
    }

    @Override
    public String getPwd() {

        return edittextPwd.getText().toString().trim();
    }

    @Override
    public String getaffirmPwd() {

        return null;
    }

    @Override
    public void onError(String errorMsg) {

        showShortToast(errorMsg);
    }

    @Override
    public void onLoginSuccess(UpdatePwd info) {

    }

    @Override
    public void onSendCodeSuccess(String message) {
        showShortToast(message);
        removeFragment();
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void hideDialog() {

    }

    @Override
    public String getPhone() {

        return edittextPhonevalue.getText().toString().trim();
    }

    @Override
    public void sendCheckMobileSuccess(CheckMobileBean checkMobileBean) {

    }

    @Override
    public void sendCodeSuccess(SendCodeBean sendCodeBean) {

        showShortToast("发送验证码成功");
        timeCount.start();
    }

    @Override
    public String getSendCode() {

        return edittextSmscode.getText().toString().trim();
    }

    /**
     * @param
     * @return void    返回类型
     * @throws
     * @Title: setButtonBackground
     * @Description: TODO 设置登录按钮的背景颜色
     */
    private void setButtonBackground() {

        String phonevalue = edittextPhonevalue.getText().toString().trim();
        String smscode = edittextSmscode.getText().toString().trim();
        String userpwd = edittextPwd.getText().toString().trim();

        if (phonevalue.length() > 0 && smscode.length() > 0 && userpwd.length() > 0) {
            commitBtn.setClickable(true);
            commitBtn.setBackgroundResource(R.drawable.btn_pressed);
        } else {
            commitBtn.setClickable(false);
            commitBtn.setBackgroundResource(R.drawable.btn_norml);
        }
    }

    @OnClick({R.id.commit_btn, R.id.tv_hint, R.id.sendsmsicon_tv})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.commit_btn://提交修改密码按钮

                mPresenter.commit();

                break;
            case R.id.tv_hint://手机号不可用

                showAlertMsgDialog();
                popupWindow.showAtLocation(tvHint, Gravity.CENTER, 0, 0);

                break;
            case R.id.sendsmsicon_tv://发送验证码

                mPresenter.sendCode();

                break;
        }
    }

    //弹窗联系客服
    private void showAlertMsgDialog() {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view1 = inflater.inflate(R.layout.linkman, null);

        // 自适配长、框设置
        popupWindow = new PopupWindow(view1, getActivity().getWindowManager()
                .getDefaultDisplay().getWidth() * 3 / 4, ViewPager.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow.update();
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        backgroundAlpha(0.5f);
        popupWindow.setOnDismissListener(new poponDismissListener());
        TextView link = (TextView) view1.findViewById(R.id.link);//联系人
        TextView cancle = (TextView) view1.findViewById(R.id.cancle);//取消

        cancle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
            }
        });

        link.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "10000"));
                startActivity(intent);
                popupWindow.dismiss();
            }
        });
    }

    private void backgroundAlpha(float bgAlpha) {

        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        getActivity().getWindow().setAttributes(lp);
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

    //PopupWindow消失的回调
    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {

            backgroundAlpha(1f);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timeCount.onFinish();
    }
}

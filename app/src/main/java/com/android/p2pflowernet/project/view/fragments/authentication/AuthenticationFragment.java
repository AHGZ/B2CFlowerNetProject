package com.android.p2pflowernet.project.view.fragments.authentication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.event.AuthenticationEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.OptionPicker;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangkun on 2018/3/12.
 * 实名认证
 */

public class AuthenticationFragment extends KFragment<IAuthenticationView,IAuthenticationPrenter>implements NormalTopBar.normalTopClickListener,IAuthenticationView{
    @BindView(R.id.normal_top)
    NormalTopBar topBar;
    @BindView(R.id.edt_name)
    EditText edt_name;
    @BindView(R.id.edt_idNumber)
    EditText edt_idNumber;
    @BindView(R.id.tv_bankName)
    TextView tv_bankName;
    @BindView(R.id.tv_bankChoose)
    TextView tv_bankChoose;
    @BindView(R.id.edt_bankNumber)
    EditText edt_bankNumber;
    @BindView(R.id.tv_sure)
    TextView tv_sure;

    private ShapeLoadingDialog shapeLoadingDialog;
    private String [] banks;

    @Override
    public IAuthenticationPrenter createPresenter() {
        return new IAuthenticationPrenter();
    }

    public static KFragment newIntence(){
        AuthenticationFragment fragment = new AuthenticationFragment();
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_authentication;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        //初始化沉浸式
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.mineState));
        topBar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.mineState));
        topBar.setTitleTextColor(getResources().getColor(R.color.white));
        topBar.setLeftImageId(R.mipmap.icon_back_white);
        topBar.setTitleText("提交认证");
        topBar.setTopClickListener(this);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        banks = getResources().getStringArray(R.array.bank);

        edt_bankNumber.addTextChangedListener(new TextWatcher() {
            int beforeTextLength = 0;
            int onTextLength = 0;
            boolean isChanged = false;

            int location = 0;// 记录光标的位置
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();
            int konggeNumberB = 0;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3 || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                konggeNumberB = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        konggeNumberB++;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChanged) {
                    location = edt_bankNumber.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == ' ') {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }

                    index = 0;
                    int konggeNumberC = 0;
                    while (index < buffer.length()) {
                        // if (index % 5 == 4) {
                        //      buffer.insert(index, ' ');
                        //      konggeNumberC++;
                        // }
                        if (index == 4 || index == 9 || index == 14 || index == 19) {
                            buffer.insert(index, ' ');
                            konggeNumberC++;
                        }
                        index++;
                    }

                    if (konggeNumberC > konggeNumberB) {
                        location += (konggeNumberC - konggeNumberB);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }

                    edt_bankNumber.setText(str);
                    Editable etable = edt_bankNumber.getText();
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
            }
        });
    }

    @OnClick({R.id.tv_bankChoose,R.id.tv_sure})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_bankChoose://选择银行
                initCause();
                break;
            case R.id.tv_sure://确认提交
                mPresenter.userAuthentication();
                break;
        }
    }

    private void initCause() {
        OptionPicker optionPicker = new OptionPicker(getActivity(), banks);
        optionPicker.setTitleText("");
        optionPicker.setSubmitTextColor(Color.parseColor("#FF2E00"));
        optionPicker.show();
        optionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(String option, int selectedIndex) {

                tv_bankName.setText(banks[selectedIndex]);
            }
        });
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
    public void onError(String errorMsg) {
        showShortToast(errorMsg);
    }

    @Override
    public void onSuccess(String str) {
        showShortToast("认证成功");
        EventBus.getDefault().post(new AuthenticationEvent("认证成功"));
        removeFragment();
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
    public String getUserName() {
        return edt_name.getText().toString().trim();
    }

    @Override
    public String getIDNumber() {
        return edt_idNumber.getText().toString().trim();
    }

    @Override
    public String getBankName() {
        return tv_bankName.getText().toString().trim();
    }

    @Override
    public String getBankNumber() {
        return edt_bankNumber.getText().toString().trim();
    }


}

package com.android.p2pflowernet.project.view.fragments.mine.setting.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.event.RevisePasswordEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2017/11/18.
 * by--登录设置
 */

public class SettingLoginFragment extends KFragment<IsettingLoginView, IsettingLoginPrenter>
        implements NormalTopBar.normalTopClickListener {
    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.ll_change_phone)
    LinearLayout llChangePhone;
    @BindView(R.id.ll_update_login_pwd)
    LinearLayout llUpdateLoginPwd;

    @Override
    public IsettingLoginPrenter createPresenter() {
        return new IsettingLoginPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_setting_login;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        Utils.setStatusBar(getActivity(), 0, false);
        normalTop.setTitleText("登录设置");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);

    }

    public static KFragment newIntence() {

        SettingLoginFragment settingLoginFragment = new SettingLoginFragment();
        Bundle bundle = new Bundle();
        settingLoginFragment.setArguments(bundle);

        return settingLoginFragment;
    }

    @OnClick({R.id.ll_change_phone, R.id.ll_update_login_pwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_change_phone://更换手机号

                Intent intent = new Intent(getActivity(), ChangePhoneActivity.class);
                startActivity(intent);

                break;
            case R.id.ll_update_login_pwd://修改登录密码

                intent = new Intent(getActivity(), UpdateLoginPwdActivity.class);
                startActivity(intent);

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

    //修改密码成功后删除当前页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RevisePasswordEvent event){
        removeFragment();
    }
}

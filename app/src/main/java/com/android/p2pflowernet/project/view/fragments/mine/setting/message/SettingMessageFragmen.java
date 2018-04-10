package com.android.p2pflowernet.project.view.fragments.mine.setting.message;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;

import butterknife.BindView;

/**
 * Created by caishen on 2017/11/22.
 * by--设置-消息设置
 */

public class SettingMessageFragmen extends KFragment<ISettingMessageView, SettingMessagePrenter>
        implements NormalTopBar.normalTopClickListener, CompoundButton.OnCheckedChangeListener
        ,View.OnClickListener{
    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.tb_push)
    ToggleButton tbPush;
    @BindView(R.id.tb_wl)
    ToggleButton tbWl;
    @BindView(R.id.tb_fr)
    ToggleButton tbFr;
    @BindView(R.id.tb_hd)
    ToggleButton tbHd;
    @BindView(R.id.tb_apply)
    ToggleButton tbApply;
    @BindView(R.id.tb_system)
    ToggleButton tbSystem;
    @BindView(R.id.fragment_tb_push)
    LinearLayout mLinearLayout;

    @Override
    public SettingMessagePrenter createPresenter() {

        return new SettingMessagePrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_setting_message;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        Utils.setStatusBar(getActivity(), 0, false);
        normalTop.setTitleText("消息设置");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);

        //设置按钮的点击监听
        tbPush.setOnCheckedChangeListener(this);
        tbApply.setOnCheckedChangeListener(this);
        tbFr.setOnCheckedChangeListener(this);
        tbHd.setOnCheckedChangeListener(this);
        tbSystem.setOnCheckedChangeListener(this);
        tbWl.setOnCheckedChangeListener(this);

        mLinearLayout.setOnClickListener(this);
        initView();

    }

    private void initView() {
        NotificationManagerCompat manager = NotificationManagerCompat.from(getContext());
        boolean isOpened = manager.areNotificationsEnabled();
        if (isOpened) {
            tbPush.setChecked(true);
        }else{
            tbPush.setChecked(false);
        }
    }

    public static KFragment newIntence() {

        SettingMessageFragmen settingMessageFragmen = new SettingMessageFragmen();
        Bundle bundle = new Bundle();
        settingMessageFragmen.setArguments(bundle);
        return settingMessageFragmen;
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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {
//            case R.id.tb_push://推送
//
//                break;
            case R.id.tb_fr://返润

                if (isChecked) {
                    showShortToast("开启");
                } else {
                    showShortToast("关闭");
                }
                break;
            case R.id.tb_apply://申请/审批
                if (isChecked) {
                    showShortToast("开启");
                } else {
                    showShortToast("关闭");
                }

                break;
            case R.id.tb_hd://互动通知
                if (isChecked) {
                    showShortToast("开启");
                } else {
                    showShortToast("关闭");
                }

                break;
            case R.id.tb_wl://物流
                if (isChecked) {
                    showShortToast("开启");
                } else {
                    showShortToast("关闭");
                }

                break;
            case R.id.tb_system://系统通知
                if (isChecked) {
                    showShortToast("开启");
                } else {
                    showShortToast("关闭");
                }

                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_tb_push:
                new AlertDialog.Builder(getContext())
                        .setTitle("提示")
                        .setMessage("是否前往设置中心设置")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                initView();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getActivity().getApplication().getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent,00000);
                            }
                        })
                        .create()
                        .show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 00000) {
            initView();
        }
    }
}

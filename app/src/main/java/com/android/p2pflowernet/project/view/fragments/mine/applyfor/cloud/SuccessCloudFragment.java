package com.android.p2pflowernet.project.view.fragments.mine.applyfor.cloud;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2017/11/22.
 * by--申请云工成功的页面
 */

public class SuccessCloudFragment extends KFragment<ISuccessCloudView, ISuccessCloudPrenter>
        implements NormalTopBar.normalTopClickListener {
    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.btn_officing)
    Button btnOfficing;
    @BindView(R.id.tv_success_statue)
    TextView tvSuccessStatue;
    private ShapeLoadingDialog shapeLoadingDialog;

    @Override
    public ISuccessCloudPrenter createPresenter() {

        return new ISuccessCloudPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_applyfor_cloud_success;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setTitleText("申请成功");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        normalTop.setTopClickListener(this);
        Utils.setStatusBar(getActivity(), 0, false);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();
    }

    public static KFragment newIntence() {

        SuccessCloudFragment successCloudFragment = new SuccessCloudFragment();
        Bundle bundle = new Bundle();
        successCloudFragment.setArguments(bundle);
        return successCloudFragment;
    }

    @OnClick(R.id.btn_officing)
    public void onClick() {//查看移动办公


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
}

package com.android.p2pflowernet.project.view.fragments.mine.cargo_refund;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.event.RefundEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by caishen on 2017/11/20.
 * by--申请仲裁
 */

public class ApplyForArbitrationFragment extends KFragment<IApplyForArbitrationView, IApplyForArbitrationPrenter>
        implements NormalTopBar.normalTopClickListener, IApplyForArbitrationView {
    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.iv_user)
    ImageView ivUser;
    @BindView(R.id.ed_applyfor)
    CustomEditText edApplyfor;
    @BindView(R.id.id_editor_detail_count)
    TextView idEditorDetailCount;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    private boolean islMaxCount;

    String mRefundid;
    // 加载进度条
    private ShapeLoadingDialog shapeLoadingDialog;

    public static ApplyForArbitrationFragment newIntence(String Refundid) {

        Bundle bundle = new Bundle();
        ApplyForArbitrationFragment applyForArbitrationFragment = new ApplyForArbitrationFragment();
        bundle.putString("refundid", Refundid);
        applyForArbitrationFragment.setArguments(bundle);
        return applyForArbitrationFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRefundid = getArguments().getString("refundid");
    }

    @Override
    public IApplyForArbitrationPrenter createPresenter() {
        return new IApplyForArbitrationPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_applyfor_bitration;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setTitleText("申请仲裁");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setRightText("取消");
        normalTop.setRightTextColor(Color.WHITE);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        Utils.setStatusBar(getActivity(), 0, false);
        normalTop.setTopClickListener(this);
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();
    }


    /**
     * 设置editetxt的字数输入限制
     *
     * @param editable
     */
    @OnTextChanged(value = R.id.ed_applyfor, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void editTextDetailChange(Editable editable) {
        int detailLength = editable.length();
        idEditorDetailCount.setText(detailLength + "/50");
        if (detailLength == 49) {
            islMaxCount = true;
        }
    }


    @Override
    public void onLeftClick(View view) {
        EventBus.getDefault().post(new RefundEvent());
        removeFragment();
    }

    @Override
    public void onRightClick(View view) {

    }

    @Override
    public void onTitleClick(View view) {

    }


    @Override
    public String getRefundId() {
        return mRefundid;
    }

    @Override
    public String getContent() {
        return edApplyfor.getText().toString().trim();
    }

    @Override
    public void showDialog() {
        shapeLoadingDialog.show();
    }

    @Override
    public void hideDialog() {
        if (shapeLoadingDialog.isShowing())
            shapeLoadingDialog.dismiss();
    }


    @OnClick(R.id.btn_commit)
    public void onClick(View view) {//提交
        switch (view.getId()) {
            case R.id.btn_commit:
                mPresenter.applyarbit();
                break;
        }

    }

    @Override
    public void onError(String errorMsg) {
        showShortToast(errorMsg);
    }

    @Override
    public void onSuccess(String message) {
        showShortToast(message);
        EventBus.getDefault().post(new RefundEvent());
        removeFragment();

    }
}

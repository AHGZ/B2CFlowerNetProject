package com.android.p2pflowernet.project.view.fragments.mine.applyfor.manufac;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.IdEntityBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2018/1/29.
 * by--供应商申请页面
 */

public class ManufacFragment extends KFragment<IManufacView, IManufacPrenter>
        implements NormalTopBar.normalTopClickListener, TextWatcher, IManufacView {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.et_name)
    CustomEditText etName;
    @BindView(R.id.et_phone)
    CustomEditText etPhone;
    @BindView(R.id.et_emil)
    CustomEditText etEmil;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    private String state = "";
    private ShapeLoadingDialog shapeLoadingDialog;
    private IdEntityBean idEntityBean;

    @Override
    public IManufacPrenter createPresenter() {

        return new IManufacPrenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        state = arguments.getString("state");
        idEntityBean = (IdEntityBean) arguments.getSerializable("idEntityBean");
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_manufac_applyfor;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setTitleText("填写供货商信息");
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

        etName.addTextChangedListener(this);
        etPhone.addTextChangedListener(this);
        etEmil.addTextChangedListener(this);

        if (idEntityBean != null) {
            etName.setText(TextUtils.isEmpty(idEntityBean.getRealname()) ? "" : idEntityBean.getRealname());
            if (TextUtils.isEmpty(idEntityBean.getRealname())) {
                etName.setEnabled(true);
            }else{
                etName.setEnabled(false);
            }
        }
    }

    public static KFragment NewIntence(String state,IdEntityBean bean) {

        Bundle bundle = new Bundle();
        ManufacFragment manufacFragment = new ManufacFragment();
        bundle.putString("state", state);
        bundle.putSerializable("idEntityBean",bean);
        manufacFragment.setArguments(bundle);
        return manufacFragment;
    }

    @OnClick(R.id.btn_commit)
    public void onClick() {//提交

        mPresenter.add_manufac_apply();

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
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String emil = etEmil.getText().toString().trim();
        if (name.length() > 0 && phone.length() > 0 && emil.length() > 0) {
            btnCommit.setClickable(true);
            btnCommit.setBackgroundResource(R.drawable.pay_bg);
        } else {
            btnCommit.setClickable(false);
            btnCommit.setBackgroundResource(R.drawable.btn_norml);
        }
    }

    @Override
    public void onError(String s) {

        showShortToast(s);
    }

    @Override
    public String getapply_name() {
        return etName.getText().toString().trim();
    }

    @Override
    public String getapply_phone() {
        return etPhone.getText().toString().trim();
    }

    @Override
    public String apply_email() {
        return etEmil.getText().toString().trim();
    }

    @Override
    public void hideDiaglog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void successData(String data) {

        if (data != null) {

            showShortToast("申请成功！");
            removeFragment();
        }
    }

    @Override
    public void showDialog() {

        shapeLoadingDialog.show();
    }
}

package com.android.p2pflowernet.project.view.fragments.authentication.result;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.IdEntityBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;

import butterknife.BindView;

/**
 * Created by zhangkun on 2018/3/12.
 */

public class AuthenticationResultFragment extends KFragment<IAuthenticationResultView,AuthenticationResultPresenter> implements NormalTopBar.normalTopClickListener
    ,IAuthenticationResultView{

    @BindView(R.id.normal_top)
    NormalTopBar topBar;
    @BindView(R.id.img_result)
    ImageView img_result;
    @BindView(R.id.tv_result)
    TextView tv_result;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_idNumber)
    TextView tv_idNumber;
    @BindView(R.id.tv_bankNumber)
    TextView tv_bankNumber;

    private ShapeLoadingDialog shapeLoadingDialog;
    private int is_checked;
    private String userName;
    private String userIDNumber;
    private String bankName;
    private String bankNumber;

    @Override
    public AuthenticationResultPresenter createPresenter() {
        return new AuthenticationResultPresenter();
    }

    public static AuthenticationResultFragment newIntence(){
        AuthenticationResultFragment fragment = new AuthenticationResultFragment();
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_authentication_result;
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
        topBar.setTitleText("认证结果");
        topBar.setTopClickListener(this);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        mPresenter.getUserInfo();
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
    public void onSuccess(IdEntityBean data) {
        if (data != null) {
            is_checked = data.getIs_checked();
            userName = data.getRealname();
            userIDNumber = data.getId_number();
            bankName = data.getBank_name();
            bankNumber = data.getCard_num();
            if (is_checked == 1) {
                tv_result.setText("认证成功");
            }else{
                tv_result.setText("未认证");
            }
            img_result.setImageResource(R.mipmap.wancheng);
        }

        tv_name.setText(userName);
        tv_idNumber.setText(userIDNumber);
        //银行卡格式化
        if (!TextUtils.isEmpty(bankNumber)) {
            String str = bankNumber.replaceAll("\\d{4}(?!$)", "$0 ");
            tv_bankNumber.setText(str);
        }
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
}

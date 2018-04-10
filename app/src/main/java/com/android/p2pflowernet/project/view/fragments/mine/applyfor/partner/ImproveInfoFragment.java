package com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.AddAuthInfoBean;
import com.android.p2pflowernet.project.entity.AllPlaceDataBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.SelectsCityPicker;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2017/11/20.
 * by--完善个人信息
 */

public class ImproveInfoFragment extends KFragment<IImproveInfoView, ImproveInfoFPrenter>
        implements NormalTopBar.normalTopClickListener, TextWatcher, IImproveInfoView, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.et_name)
    CustomEditText etName;
    @BindView(R.id.et_id_card)
    CustomEditText etIdCard;
    @BindView(R.id.rb_married)
    RadioButton rbMarried;
    @BindView(R.id.rb_spinsterhood)
    RadioButton rbSpinsterhood;
    @BindView(R.id.et_huji)
    CustomEditText etHuji;
    @BindView(R.id.tv_place)
    TextView tvPlace;
    @BindView(R.id.iv_place)
    ImageView ivPlace;
    @BindView(R.id.et_place_detail)
    CustomEditText etPlaceDetail;
    @BindView(R.id.ll_place)
    LinearLayout llPlace;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.tv_married)
    TextView tvMarried;
    @BindView(R.id.tv_spinsterhood)
    TextView tvSpinsterhood;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    private ShapeLoadingDialog shapeLoadingDialog;
    private String marital_state = "2";
    private String cityId1 = "", provinceid1 = "", countyId1 = "";

    public static KFragment newIntence() {

        Bundle bundle = new Bundle();
        ImproveInfoFragment improveInfoFragment = new ImproveInfoFragment();
        improveInfoFragment.setArguments(bundle);

        return improveInfoFragment;
    }

    @Override
    public ImproveInfoFPrenter createPresenter() {
        return new ImproveInfoFPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_improve_info;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setTitleText("完善个人信息");
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
        etIdCard.addTextChangedListener(this);
        etName.addTextChangedListener(this);
        etHuji.addTextChangedListener(this);
        etPlaceDetail.addTextChangedListener(this);
        radioGroup.setOnCheckedChangeListener(this);


        //完善个人信息
        mPresenter.addAuthInfo();
    }

    @OnClick({R.id.iv_place, R.id.ll_place, R.id.btn_commit, R.id.tv_place})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_place://选择现居地址

                //选择城市
                mPresenter.getcitydata();

                break;
            case R.id.tv_place:////选择现居地址

                //选择城市
                mPresenter.getcitydata();

                break;
            case R.id.btn_commit://提交完善

                mPresenter.addInsuranceInfo();

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
        String username = etIdCard.getText().toString().trim();
        String userpwd = etName.getText().toString().trim();
        String etplace = etPlaceDetail.getText().toString().trim();
        String ethuji = etHuji.getText().toString().trim();
        String trim = tvPlace.getText().toString().trim();
        if (username.length() > 0 && userpwd.length() > 0 && etplace.length() > 0 && ethuji.length() > 0) {
            btnCommit.setClickable(true);
            btnCommit.setBackgroundResource(R.drawable.pay_bg);
        } else {
            btnCommit.setClickable(false);
            btnCommit.setBackgroundResource(R.drawable.btn_norml);
        }
    }

    @Override
    public String getUserId() {

        return "1";
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void onError(String message) {

        showShortToast(message);
    }

    @Override
    public void setAddAuthInfoSuccess(AddAuthInfoBean data) {

        if (data != null) {

            etName.setText(data.getRealname());
            etIdCard.setText(data.getId_number());
        }
    }

    @Override
    public void showDialog() {

        shapeLoadingDialog.show();
    }

    @Override
    public void setVestAddressSuccess(AllPlaceDataBean data) {

        List<AllPlaceDataBean.GrBean> dataBeanRegion = data.getGr();
        List<AllPlaceDataBean.GrBean> gr = dataBeanRegion;

        if (gr.isEmpty()) {
            showShortToast("获取城市数据失败");
            return;
        }
        getSelectCity(gr);
    }

    @Override
    public void onSucceessAllData(ArrayList<ApiResponse<AllPlaceDataBean>> data) {

    }

    @Override
    public String getmaritalState() {

        return marital_state;
    }

    @Override
    public String getAddress() {

        return tvPlace.getText().toString().trim();
    }

    @Override
    public String getprovinceIid() {

        return provinceid1;
    }

    @Override
    public String getcityId() {

        return cityId1;
    }

    @Override
    public String getdistictIid() {
        return countyId1;
    }

    @Override
    public String getlocationAddress() {

        return etPlaceDetail.getText().toString().trim();
    }

    @Override
    public void onSucessAddInsuranceInfo(String message) {

        showShortToast(message);
        removeFragment();

        //填写保单
        Intent intent = new Intent(getActivity(), ImproveGuaranteeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSuccess(String message) {

        showShortToast(message);
        removeFragment();

        //填写保单
        Intent intent = new Intent(getActivity(), ImproveGuaranteeActivity.class);
        startActivity(intent);
    }

    //选择城市列表
    private void getSelectCity(List<AllPlaceDataBean.GrBean> gr) {

        SelectsCityPicker selectCityPicker = new SelectsCityPicker(getActivity(), gr);
        selectCityPicker.setHalfScreen(true);
        selectCityPicker.setOnCitySelectListener(new SelectsCityPicker.OnCitySelectListener() {
            @Override
            public void onCitySelect(String province, String city, int provinceid, int cityid, String countyName, int countyId) {
                if (!province.equals("") && city.equals("") && countyName.equals("")) {
                    tvPlace.setText(province);
                } else {
                    tvPlace.setText(province + "-" + city + "-" + countyName);
                }

                provinceid1 = provinceid + "";
                cityId1 = cityid + "";
                countyId1 = countyId + "";
            }
        });
        selectCityPicker.show();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_married://已婚
                marital_state = "2";
                break;
            case R.id.rb_spinsterhood://未婚
                marital_state = "1";
                break;
            default:
                break;
        }
    }
}

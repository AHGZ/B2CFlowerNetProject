package com.android.p2pflowernet.project.view.fragments.affirm.address;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.AllPlaceDataBean;
import com.android.p2pflowernet.project.event.RefreshEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.SelectsCityPicker;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2017/11/7.
 * by--添加地址页面
 */

public class AdressFragment extends KFragment<IAdressView, IAdressPrenter> implements
        NormalTopBar.normalTopClickListener, IAdressView, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.et_name)
    CustomEditText etName;
    @BindView(R.id.et_phone)
    CustomEditText etPhone;
    @BindView(R.id.tv_place)
    TextView tvPlace;
    @BindView(R.id.iv_place)
    ImageView ivPlace;
    @BindView(R.id.et_place_detail)
    CustomEditText etPlaceDetail;
    @BindView(R.id.ll_place)
    LinearLayout llPlace;
    @BindView(R.id.cb_default)
    CheckBox cbDefault;
    @BindView(R.id.rl_defult)
    RelativeLayout rlDefult;
    private ShapeLoadingDialog shapeLoadingDialog;
    private String isDefault = "1";//0--默认地址 1--不是
    private String provinceid1 = "";
    private String cityId1 = "";
    private String countyId1 = "";
    private String isUpdate = "0";//0--添加地址 1--修改地址
    private String adressId = "";
    private String address = "";
    private String location = "";
    private String telephone = "";
    private String name = "";

    public static KFragment newIntence(String update, String id, String name, String telephone,
                                       String location, String province_id, String city_id,
                                       String district_id, String address, String isUpdate) {

        AdressFragment adressFragment = new AdressFragment();
        Bundle bundle = new Bundle();
        bundle.putString("isUpdate", update);
        bundle.putString("id", id);
        bundle.putString("name", name);
        bundle.putString("telephone", telephone);
        bundle.putString("province_id", province_id);
        bundle.putString("location", location);
        bundle.putString("city_id", city_id);
        bundle.putString("district_id", district_id);
        bundle.putString("address", address);
        bundle.putString("is_default", isUpdate);
        adressFragment.setArguments(bundle);

        return new AdressFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        isUpdate = arguments.getString("isUpdate");

        if (isUpdate.equals("0")) {

        } else {

            name = arguments.getString("name");
            telephone = arguments.getString("telephone");
            provinceid1 = arguments.getString("province_id");
            cityId1 = arguments.getString("city_id");
            countyId1 = arguments.getString("district_id");
            address = arguments.getString("address");
            isDefault = arguments.getString("is_default");
            location = arguments.getString("location");
            adressId = arguments.getString("id");
        }
    }

    @Override
    public IAdressPrenter createPresenter() {

        return new IAdressPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_adress;
    }


    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        Utils.setStatusBar(getActivity(), 0, false);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTitleText("添加地址");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setRightText("保存");
        normalTop.setRightTextColor(Color.WHITE);
        normalTop.setBackground(getResources().getDrawable(R.drawable.app_statusbar_bg));
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        //设置标题栏的点击事件
        normalTop.setTopClickListener(this);
        cbDefault.setOnCheckedChangeListener(this);


        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        initData();
    }

    @Override
    public void initData() {

        if (isUpdate.equals("0")) {//添加地址

        } else {

            if (!address.isEmpty()) {
                etPlaceDetail.setText(address);
            }
            if (!telephone.isEmpty()) {
                etPhone.setText(telephone);
            }
            if (!name.isEmpty()) {
                etName.setText(name);
            }

            if (!location.isEmpty()) {
                tvPlace.setText(location);
            }

            //0:否，1：是
            if (isDefault.equals("0")) {
                cbDefault.setChecked(false);
                //是否显示默认地址设置
                rlDefult.setVisibility(View.VISIBLE);
            } else {
                rlDefult.setVisibility(View.GONE);
                cbDefault.setChecked(true);
            }
        }
    }

    @Override
    public void onLeftClick(View view) {

        removeFragment();
    }

    @Override
    public void onRightClick(View view) {//保存

        mPresenter.addUpdateAdress();
    }

    @Override
    public void onTitleClick(View view) {

    }

    @Override
    public String getName() {

        return etName.getText().toString().trim();
    }

    @Override
    public String gettelephone() {

        return etPhone.getText().toString().trim();
    }

    @Override
    public String getprovinceId() {
        return provinceid1;
    }

    @Override
    public String getcityId() {
        return cityId1;
    }

    @Override
    public String getdistrictId() {
        return countyId1;
    }

    @Override
    public String getaddress() {

        return etPlaceDetail.getText().toString().trim();
    }

    @Override
    public String getisDefault() {
        return isDefault;
    }

    @Override
    public String getAdressId() {

        return adressId;
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void showDialog() {

        shapeLoadingDialog.show();
    }

    @Override
    public void onError(String message) {

        showShortToast(message);
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
    public void onSuccess(String message) {

        showShortToast("操作成功！");
        EventBus.getDefault().post(new RefreshEvent("refresh"));
        removeFragment();
    }

    @Override
    public String getLocation() {

        return tvPlace.getText().toString().trim();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (isChecked) {

            isDefault = "1";

        } else {

            isDefault = "0";
        }
    }

    @OnClick({R.id.tv_place, R.id.iv_place})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_place://选择地区

                //选择城市
                mPresenter.getcitydata();

                break;
            case R.id.iv_place:

                //选择城市
                mPresenter.getcitydata();

                break;
        }
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
}

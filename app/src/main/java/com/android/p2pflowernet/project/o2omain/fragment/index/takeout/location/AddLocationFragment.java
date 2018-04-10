package com.android.p2pflowernet.project.o2omain.fragment.index.takeout.location;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.AllPlaceDataBean;
import com.android.p2pflowernet.project.entity.O2oAddressBean;
import com.android.p2pflowernet.project.event.AddLocationEvent;
import com.android.p2pflowernet.project.event.O2oAddressEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.fragment.index.takeout.location.confirmaddress.ConfirmAddressActivity;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.SelectsCityPicker;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2018/1/3.
 * by--添加地址
 */

public class AddLocationFragment extends KFragment<IAddLocationView, IAddLocationPrenter>
        implements NormalTopBar.normalTopClickListener, IAddLocationView {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.et_name)
    CustomEditText etName;
    @BindView(R.id.tv_002)
    TextView tv002;
    @BindView(R.id.rb_men)
    RadioButton rbMen;
    @BindView(R.id.rb_women)
    RadioButton rbWomen;
    @BindView(R.id.rb_sex)
    RadioGroup rbSex;
    @BindView(R.id.rl_sex)
    RelativeLayout rlSex;
    @BindView(R.id.et_phone)
    CustomEditText etPhone;
    @BindView(R.id.tv_003)
    TextView tv003;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.rl_date)
    RelativeLayout rlDate;
    @BindView(R.id.et_detail_adress)
    CustomEditText etDetailAdress;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    private ShapeLoadingDialog shapeLoadingDialog;
    private String province_id;
    private String city_id;
    private String district_id;
    private String tag = "";////0-添加收货地址 1-编辑收货地址
    private O2oAddressBean.ListsBean adressData;//地址信息
    private String longitude;
    private String latitude;
    private PoiInfo poiInfo;
    private String str;//省市县
    private String id;//收货地址ID

    @Override
    public IAddLocationPrenter createPresenter() {
        return new IAddLocationPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_add_location;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = getArguments().getString("tag");
        adressData = (O2oAddressBean.ListsBean) getArguments().getSerializable("data");

        poiInfo = getArguments().getParcelable("PoiInfo");
    }

    @Override
    public void initData() {

        if (adressData != null) {

            etName.setText(adressData.getName() == null ? "" : adressData.getName());
            etPhone.setText(adressData.getTelephone() == null ? "" : adressData.getTelephone());
            etDetailAdress.setText(adressData.getAddress() == null ? "" : adressData.getAddress());
            tvDate.setText(adressData.getLocation() == null ? "" : adressData.getLocation());
            longitude = adressData.getLongitude();
            latitude = adressData.getLatitude();
            id = adressData.getId();

            int is_beyond = adressData.getIs_beyond();//1-男 2-女
            if (is_beyond == 1) {
                rbMen.setChecked(true);
            } else {
                rbWomen.setChecked(true);
            }

            //根据经纬度获得省市区
            LatLng latLng = new LatLng(Double.parseDouble(longitude), Double.parseDouble(latitude));
            GeoCoder coder = GeoCoder.newInstance();
            coder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                @Override
                public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                }

                @Override
                public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                    ReverseGeoCodeResult.AddressComponent addressDetail = reverseGeoCodeResult.getAddressDetail();
                    //countryName：国家、province：省、city：市、district：区、street：街、streetNumber：街号
                    str = addressDetail.province + "-" + addressDetail.city + "-" + addressDetail.district;
                }
            });
            coder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
        }
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸式
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.coloro2o));
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity()).loadText("加载中...")
                .delay(5000)
                .build();

        normalTop.setTitleText("添加地址");
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setBackgroundColor(Color.parseColor("#FF1E00"));
        normalTop.setTopClickListener(this);

        if (null != poiInfo) {
            tvDate.setText(poiInfo.name);
//            etDetailAdress.setText(poiInfo.address);
            LatLng ll_oc = poiInfo.location;
            latitude = String.valueOf(ll_oc.latitude);
            longitude = String.valueOf(ll_oc.longitude);
            GeoCoder coder = GeoCoder.newInstance();
            coder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                @Override
                public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                }

                @Override
                public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                    ReverseGeoCodeResult.AddressComponent addressDetail = reverseGeoCodeResult.getAddressDetail();
                    //countryName：国家、province：省、city：市、district：区、street：街、streetNumber：街号
                    str = addressDetail.province + "-" + addressDetail.city + "-" + addressDetail.district;
                }
            });

            coder.reverseGeoCode(new ReverseGeoCodeOption().location(ll_oc));
        }

        if (tag != null && tag.equals("1")) {//修改

            initData();
        }
    }


    public static KFragment newIntence(Bundle data, String tag) {

        O2oAddressBean.ListsBean datas = (O2oAddressBean.ListsBean) data.getSerializable("data");
        PoiInfo poiInfos = data.getParcelable("PoiInfo");
        AddLocationFragment addLocationFragment = new AddLocationFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", datas);
        bundle.putString("tag", tag);
        bundle.putParcelable("PoiInfo",poiInfos);
        addLocationFragment.setArguments(bundle);
        return addLocationFragment;
    }

    @OnClick({R.id.btn_commit, R.id.rl_date})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_commit://提交
                if (tag != null && tag.equals("1")) {
                    //修改地址
                    mPresenter.upuseradd();
                } else {
                    //添加地址
                    mPresenter.addAdderss();
                }
                break;

            case R.id.rl_date://选择城市
//                mPresenter.getcitydata();
                Intent intent = new Intent(getActivity(), ConfirmAddressActivity.class);
                intent.putExtra("flag","1");
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

    @Override
    public String getName() {

        return etName.getText().toString().trim();
    }

    @Override
    public String getSex() {
        String sex = "";
        if (rbMen.isChecked()) {
            sex = "1";
        } else if (rbWomen.isChecked()) {
            sex = "2";
        }
        return sex;
    }

    @Override
    public String getTelephone() {
        return etPhone.getText().toString().trim();
    }

    @Override
    public String getProvinceId() {
        return province_id;
    }

    @Override
    public String getCityId() {
        return city_id;
    }

    @Override
    public String getDistrictId() {
        return district_id;
    }

    @Override
    public String getLocation() {
        return str;
    }

    @Override
    public String getAddress() {
        return tvDate.getText().toString().trim() + etDetailAdress.getText().toString().trim();
    }

    @Override
    public String getAddressId() {
        return id;
    }

    @Override
    public String getSiteName() {
        return tvDate.getText().toString().trim();
    }

    @Override
    public String getLatitude() {
        return latitude;
    }

    @Override
    public String getLongitude() {
        return longitude;
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
    public void onSuccess(String data) {
        showShortToast("添加成功！");
        EventBus.getDefault().post(new O2oAddressEvent("02oAddress"));
        removeFragment();
    }

    @Override
    public void onError(String message) {
        showShortToast(message);
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

    //选择城市列表
    private void getSelectCity(List<AllPlaceDataBean.GrBean> gr) {

        SelectsCityPicker selectCityPicker = new SelectsCityPicker(getActivity(), gr);
        selectCityPicker.setHalfScreen(true);
        selectCityPicker.setOnCitySelectListener(new SelectsCityPicker.OnCitySelectListener() {

            @Override
            public void onCitySelect(String province, String city, int provinceid, int cityid, String countyName, int countyId) {
                if (!province.equals("") && city.equals("") && countyName.equals("")) {
                    tvDate.setText(province);
                } else {
                    tvDate.setText(province + "-" + city + "-" + countyName);
                }

                province_id = provinceid + "";
                city_id = cityid + "";
                district_id = countyId + "";
            }
        });
        selectCityPicker.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddLocationEvent event) {
        poiInfo = event.getMesage();

        if (null != poiInfo) {
            tvDate.setText(poiInfo.name);
//            etDetailAdress.setText(poiInfo.address);
            LatLng ll_oc = poiInfo.location;
            latitude = String.valueOf(ll_oc.latitude);
            longitude = String.valueOf(ll_oc.longitude);
            GeoCoder coder = GeoCoder.newInstance();
            coder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                @Override
                public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                }

                @Override
                public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                    ReverseGeoCodeResult.AddressComponent addressDetail = reverseGeoCodeResult.getAddressDetail();
                    //countryName：国家、province：省、city：市、district：区、street：街、streetNumber：街号
                    str = addressDetail.province + "-" + addressDetail.city + "-" + addressDetail.district;
                }
            });

            coder.reverseGeoCode(new ReverseGeoCodeOption().location(ll_oc));
        }
    }
}

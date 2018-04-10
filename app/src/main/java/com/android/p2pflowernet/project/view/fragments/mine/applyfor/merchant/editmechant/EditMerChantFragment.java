package com.android.p2pflowernet.project.view.fragments.mine.applyfor.merchant.editmechant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.AllPlaceDataBean;
import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.android.p2pflowernet.project.entity.MerInfoBean;
import com.android.p2pflowernet.project.entity.MerchTypeBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.ParamMatchUtils;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.SelectsCityPicker;
import com.android.p2pflowernet.project.view.customview.SelectsMerchTypePicker;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.merchant.IMerChantView;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.merchant.uploadmerchant.IUploadMerChantPrenter;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.merchant.uploadmerchant.UploadMerChantFragment;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @描述:申请商家页面
 * @创建人：zhangpeisen
 * @创建时间：2017/11/15 下午4:03
 * @修改人：zhangpeisen
 * @修改时间：2017/11/15 下午4:03
 * @修改备注：
 * @throws
 */
public class EditMerChantFragment extends KFragment<IMerChantView, IUploadMerChantPrenter>
        implements IMerChantView, TextWatcher, CustomEditText.IMyRightDrawableClick {


    @BindView(R.id.im_back)
    // 返回键
            ImageView imBack;
    @BindView(R.id.storename_ed)
    // 商铺名称
            CustomEditText storenameEd;
    @BindView(R.id.business_licensename_ed)
    // 营业执照名称
            CustomEditText businessLicensenameEd;
    @BindView(R.id.unifiedsocialcreditcode_ed)
    // 统一社会信用代码
            CustomEditText unifiedsocialcreditcodeEd;
    @BindView(R.id.shopownername_ed)
    // 店铺负责人姓名
            CustomEditText shopownername_ed;
    @BindView(R.id.shopownerphone_ed)
    // 店铺负责人电话
            CustomEditText shopownerphone_ed;
    @BindView(R.id.selectaddress_ed)
    // 选择店铺所属省市
            TextView selectaddressEd;
    @BindView(R.id.detailaddress_ed)
    // 店铺详细地址
            CustomEditText detailaddressEd;
    @BindView(R.id.submit_btn)
    // 下一步按钮
            Button submitBtn;

    // 姓名
    String realname;
    // 身份证号
    String IdcardNum;
    //
    int Islegal;
    @BindView(R.id.shop_type)
    TextView shopType;

    private int provinceId;
    private int cityId;
    private int countyId;
    private String state = "";
    private String id = "";
    private ShapeLoadingDialog shapeLoadingDialog;
    private int shopType1 = -1;//店铺类别分类
    private int shopType2 = -1;
    private int shopType3 = -1;

    public static EditMerChantFragment newIntence(String realname, String IdcardNum, int Islegal, String state, String id) {

        Bundle bundle = new Bundle();
        EditMerChantFragment editMerChantFragment = new EditMerChantFragment();
        bundle.putString("realname", realname);
        bundle.putString("idcardnum", IdcardNum);
        bundle.putInt("islegal", Islegal);
        bundle.putString("state", state);
        bundle.putString("id", id);
        editMerChantFragment.setArguments(bundle);
        return editMerChantFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realname = getArguments().getString("realname");
        IdcardNum = getArguments().getString("idcardnum");
        Islegal = getArguments().getInt("islegal");
        state = getArguments().getString("state");
        id = getArguments().getString("id");
    }

    @Override
    public IUploadMerChantPrenter createPresenter() {
        return new IUploadMerChantPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.mine_editmerchant_fragment;
    }

    @Override
    public void initData() {

        mPresenter.getMerInfo();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸式
        Utils.setStatusBar(getActivity(), 2, false);

        //增加控件点击区域
        UIUtils.setTouchDelegate(imBack, 50);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        storenameEd.addTextChangedListener(this);
        businessLicensenameEd.addTextChangedListener(this);
        unifiedsocialcreditcodeEd.addTextChangedListener(this);
        shopownername_ed.addTextChangedListener(this);
        shopownerphone_ed.addTextChangedListener(this);
        selectaddressEd.addTextChangedListener(this);
        detailaddressEd.addTextChangedListener(this);

        storenameEd.setRightDrawable(getResources().getDrawable(R.drawable.edittext_clear_close));
        businessLicensenameEd.setRightDrawable(getResources().getDrawable(R.drawable.edittext_clear_close));
        unifiedsocialcreditcodeEd.setRightDrawable(getResources().getDrawable(R.drawable.edittext_clear_close));
        shopownername_ed.setRightDrawable(getResources().getDrawable(R.drawable.edittext_clear_close));
        shopownerphone_ed.setRightDrawable(getResources().getDrawable(R.drawable.edittext_clear_close));
        detailaddressEd.setRightDrawable(getResources().getDrawable(R.drawable.edittext_clear_close));

        storenameEd.setDrawableClick(this);
        businessLicensenameEd.setDrawableClick(this);
        unifiedsocialcreditcodeEd.setDrawableClick(this);
        shopownername_ed.setDrawableClick(this);
        shopownerphone_ed.setDrawableClick(this);
        detailaddressEd.setDrawableClick(this);

        //修改数据
        if (state.equals("2")) {

            initData();
        }
    }


    @Override
    public void onError(String errorMsg) {
        showShortToast(errorMsg);
    }


    @Override
    public void showDialog() {

        shapeLoadingDialog.show();
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public String getMerId() {
        return id;
    }

    @Override
    public void onMerInfoSucc(MerInfoBean data) {

        if (data != null) {

            List<MerInfoBean.AplistBean> aplist = data.getAplist();
            if (aplist != null && aplist.size() > 0) {

                MerInfoBean.AplistBean aplistBean = aplist.get(0);

                provinceId = aplistBean.getShop_province_id();
                cityId = aplistBean.getShop_city_id();
                countyId = aplistBean.getShop_district_id();
                storenameEd.setText(aplistBean.getShop_name());
                businessLicensenameEd.setText(aplistBean.getLicense_name());
                unifiedsocialcreditcodeEd.setText(aplistBean.getUniform_social_credit_code());
                shopownername_ed.setText(aplistBean.getLegal_name());
                shopownerphone_ed.setText(aplistBean.manager_tel());
                selectaddressEd.setText(aplistBean.getArea_name());
                detailaddressEd.setText(aplistBean.getShop_detail_address());
                shopType1 = aplistBean.getFirst_cate_id();
                shopType2 = aplistBean.getSecond_cate_id();
                shopType3 = aplistBean.getThird_cate_id();
                shopType.setText(aplistBean.getMerch_type());

            }
        }
    }

    @Override
    public void onSuccess(String message) {

        showShortToast(message);
    }

    /**
     * 获取店铺类别
     *
     * @param data
     */
    @Override
    public void setMerchTypeSuccess(MerchTypeBean data) {

        if (data != null) {

            List<MerchTypeBean.CateBean> cate = data.getCate();

            if (cate != null && cate.size() > 0) {
                SelectsMerchTypePicker selectCityPicker = new SelectsMerchTypePicker(getActivity(), cate);
                selectCityPicker.setHalfScreen(true);
                selectCityPicker.setOnCitySelectListener(new SelectsMerchTypePicker.OnCitySelectListener() {

                    @Override
                    public void onCitySelect(String province, String city, int provinceid, int cityid, String countyName, int countyid) {
                        if (!province.equals("") && city.equals("") && countyName.equals("")) {
                            shopType.setText(province);
                        } else {
                            shopType.setText(province + "-" + city + "-" + countyName);
                        }

                        shopType1 = provinceid;
                        shopType2 = cityid;
                        shopType3 = countyid;
                    }
                });
                selectCityPicker.show();
            }
        }
    }

    @Override
    public String getfirst_cate_id() {
        return String.valueOf(shopType1);
    }

    @Override
    public String getsecond_cate_id() {
        return String.valueOf(shopType2);
    }

    @Override
    public String getthird_cate_id() {
        return String.valueOf(shopType3);
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
    public void setMerchantSuccess(String message) {

    }

    //选择城市列表
    private void getSelectCity(List<AllPlaceDataBean.GrBean> gr) {

        SelectsCityPicker selectCityPicker = new SelectsCityPicker(getActivity(), gr);
        selectCityPicker.setHalfScreen(true);
        selectCityPicker.setOnCitySelectListener(new SelectsCityPicker.OnCitySelectListener() {

            @Override
            public void onCitySelect(String province, String city, int provinceid, int cityid, String countyName, int countyid) {
                if (!province.equals("") && city.equals("") && countyName.equals("")) {
                    selectaddressEd.setText(province);
                } else {
                    selectaddressEd.setText(province + "-" + city + "-" + countyName);
                }

                provinceId = provinceid;
                cityId = cityid;
                countyId = countyid;
            }
        });
        selectCityPicker.show();
    }


    @Override
    public String getRealname() {
        return realname;
    }

    @Override
    public String getIdnumber() {
        return IdcardNum;
    }

    @Override
    public int getIslegal() {
        return Islegal;
    }

    @Override
    public boolean isCheck() {
        return false;
    }

    @Override
    public String getShopname() {
        return storenameEd.getText().toString().trim();
    }

    @Override
    public String getBusinessLicensename() {
        return businessLicensenameEd.getText().toString().trim();
    }


    @Override
    public String getUscc() {
        return unifiedsocialcreditcodeEd.getText().toString().trim();
    }

    @Override
    public String getShopOwnerName() {
        return shopownername_ed.getText().toString().trim();
    }


    @Override
    public String getShopOwnerPhone() {
        return shopownerphone_ed.getText().toString().trim();
    }

    @Override
    public int getShopProvinceid() {
        return provinceId;
    }

    @Override
    public int getShopCityid() {
        return cityId;
    }

    @Override
    public int getShopDistrictid() {
        return countyId;
    }

    @Override
    public String getAreaname() {
        return selectaddressEd.getText().toString().trim();
    }

    @Override
    public String getShopDetailAddress() {
        return detailaddressEd.getText().toString().trim();
    }

    @Override
    public String getCfipath() {
        return null;
    }

    @Override
    public String getCbipath() {
        return null;
    }

    @Override
    public String getSpipath() {
        return null;
    }

    @Override
    public String getSiipaths() {
        return null;
    }

    @Override
    public String getBlipath() {
        return null;
    }

    @Override
    public String getCslipath() {
        return null;
    }

    @Override
    public String getTipath() {
        return null;
    }

    @Override
    public List<File> getUserImgList() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public void setUploadImgSuccess(ImgDataBean data) {

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > 0 && Islegal != -1) {
            setButtonBackground();
        }
    }

    private void setButtonBackground() {
        String storenameStr = storenameEd.getText().toString().trim();
        String businessLicensenameStr = businessLicensenameEd.getText().toString().trim();
        String unifiedsocialcreditcodeStr = unifiedsocialcreditcodeEd.getText().toString().trim();
        String shopownernameStr = shopownername_ed.getText().toString().trim();
        String shopownerphoneStr = shopownerphone_ed.getText().toString().trim();
        String selectaddressStr = selectaddressEd.getText().toString().trim();
        String detailaddressStr = detailaddressEd.getText().toString().trim();
        if (storenameStr.length() > 0 && businessLicensenameStr.length() > 0 &&
                unifiedsocialcreditcodeStr.length() > 0 && shopownernameStr.length() > 0 && shopownerphoneStr.length() > 0
                && selectaddressStr.length() > 0 && detailaddressStr.length() > 0) {
            submitBtn.setClickable(true);
            submitBtn.setBackgroundResource(R.drawable.pay_bg);

        } else {
            submitBtn.setClickable(false);
            submitBtn.setBackgroundResource(R.drawable.btn_norml);
        }
    }


    @OnClick({R.id.im_back, R.id.selectaddress_ed, R.id.submit_btn, R.id.shop_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                removeFragment();
                break;
            case R.id.selectaddress_ed:
                //城市选择
                hideSoftInput();
                mPresenter.getcitydata();
                break;

            case R.id.shop_type://店铺类别

                mPresenter.getShopType();

                break;
            case R.id.submit_btn:

                if (TextUtils.isEmpty(getShopname()) || getShopname().equals("")) {
                    showShortToast("请填写真实的店铺名称");
                    return;
                }
                if (TextUtils.isEmpty(getBusinessLicensename()) || getBusinessLicensename().equals("")) {
                    showShortToast("请填写真实的营业执照名称");
                    return;
                }
                if (TextUtils.isEmpty(getUscc()) || getUscc().equals("")) {
                    showShortToast("请填写统一社会信用代码");
                    return;
                }
                if (TextUtils.isEmpty(getShopOwnerName()) || getShopOwnerName().equals("")) {
                    showShortToast("请填写负责人姓名");
                    return;
                }
                if (TextUtils.isEmpty(getShopOwnerPhone()) || getShopOwnerPhone().equals("")) {
                    showShortToast("请填写负责人电话");
                    return;
                }

                if (!check(getShopOwnerPhone())) {
                    return;
                }

                if (TextUtils.isEmpty(shopType.getText().toString().trim()) || shopType.getText().toString().trim().equals("")) {
                    showShortToast("请选择店铺类别");
                    return;
                }

                if (TextUtils.isEmpty(getAreaname()) || getAreaname().equals("")) {
                    showShortToast("请选择店铺所属省市");
                    return;
                }
                if (TextUtils.isEmpty(getShopDetailAddress()) || getShopDetailAddress().equals("")) {
                    showShortToast("请填写店铺详细地址");
                    return;
                }

                addFragment(UploadMerChantFragment.newIntence(getRealname(), getIdnumber(), getIslegal()
                        , getShopname(), getBusinessLicensename(), getUscc(), getShopOwnerName(),
                        getShopOwnerPhone(), provinceId, cityId, countyId, getAreaname(), getShopDetailAddress(), state, id,
                        String.valueOf(shopType1), String.valueOf(shopType2), String.valueOf(shopType3)));
                break;
        }
    }

    @Override
    public void rightDrawableClick(View view) {
        switch (view.getId()) {
            case R.id.storename_ed:
                storenameEd.setText("");
                break;
            case R.id.business_licensename_ed:
                businessLicensenameEd.setText("");
                break;
            case R.id.unifiedsocialcreditcode_ed:
                unifiedsocialcreditcodeEd.setText("");
                break;
            case R.id.shopownername_ed:
                shopownername_ed.setText("");
                break;
            case R.id.shopownerphone_ed:
                shopownerphone_ed.setText("");
                break;
            case R.id.detailaddress_ed:
                detailaddressEd.setText("");
                break;
        }
    }

    //判断手机号码是否正确
    private boolean check(String phone) {
        if (TextUtils.isEmpty(phone)) {
            showShortToast("请填写手机号");
            return false;
        }

        if (!ParamMatchUtils.isPhoneAvailable(phone)) {
            showShortToast("请填写正确的手机号");
            return false;
        }
        return true;
    }
}

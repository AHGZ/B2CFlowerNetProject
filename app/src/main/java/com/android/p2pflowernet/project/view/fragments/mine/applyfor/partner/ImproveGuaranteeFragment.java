package com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.AllPlaceDataBean;
import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.android.p2pflowernet.project.event.SigntureEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.ElastticityScrollView;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.SelectsCityPicker;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.ActionSheet;
import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.bean.ImageItem;
import com.ghnor.flora.Flora;
import com.ghnor.flora.callback.Callback;
import com.ghnor.flora.spec.calculation.Calculation;
import com.rxy.netlib.http.ApiResponse;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;


/**
 * Created by caishen on 2017/11/20.
 * by--完善保单信息
 */

public class ImproveGuaranteeFragment extends KFragment<IImproveGuaranteeView, IImproveGuaranteePrenter>
        implements NormalTopBar.normalTopClickListener, TextWatcher, ActionSheet.ActionSheetListener,
        IImproveGuaranteeView, RadioGroup.OnCheckedChangeListener {

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
    @BindView(R.id.iv_id_z)
    ImageView ivIdZ;
    @BindView(R.id.iv_id_f)
    ImageView ivIdF;
    @BindView(R.id.tv_signature)
    TextView tvSignature;
    @BindView(R.id.iv_signature)
    ImageView ivSignature;
    @BindView(R.id.tv_date)
    TextView etDate;
    @BindView(R.id.ll_date)
    LinearLayout llDate;
    @BindView(R.id.checkbox_im)
    CheckBox checkboxIm;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.btn_yl)
    Button btnYl;
    @BindView(R.id.et_phone)
    CustomEditText etPhone;
    @BindView(R.id.et_zhanghu)
    CustomEditText etZhanghu;
    @BindView(R.id.et_bank_name)
    CustomEditText etBankName;
    @BindView(R.id.et_bank_id)
    CustomEditText etBankId;
    @BindView(R.id.tv_married)
    TextView tvMarried;
    @BindView(R.id.tv_spinsterhood)
    TextView tvSpinsterhood;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.srcollview)
    ElastticityScrollView srcollview;
    private ShapeLoadingDialog shapeLoadingDialog;
    private String signturepath = "";
    private String marital_state = "2";//默认已婚状态
    private String countyId1 = "", cityId1 = "", provinceid1 = "";
    private List<File> fileList = new ArrayList<>();
    private String tag = "";//1--正面 2--反面 3--签名
    private List<String> filPath = new ArrayList<>();
    private String frontPhotoPath = "", signUrlPath = "", backPhotoPath = "";
    private String type = "";
    private String record_id = "";//对应资质购买ID

    public static KFragment newIntence(String record_id) {
        Bundle bundle = new Bundle();
        bundle.putString("record_id", record_id);
        ImproveGuaranteeFragment improveGuaranteeFragment = new ImproveGuaranteeFragment();
        improveGuaranteeFragment.setArguments(bundle);
        return improveGuaranteeFragment;
    }

    @Override
    public IImproveGuaranteePrenter createPresenter() {
        return new IImproveGuaranteePrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_improve_guarantee;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        record_id = arguments.getString("record_id");
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setTitleText("填写保险信息");
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

        //获取系统当前日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        etDate.setText(simpleDateFormat.format(date));
    }

    //返回签名的图片
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SigntureEvent event) {

        signturepath = event.getPath();
        if (!signturepath.isEmpty()) {

            File file1 = new File(signturepath);
            fileList.clear();
            fileList.add(file1);
            tag = "3";
            //上传图片返回地址
            type = "3";
            mPresenter.uploadImg();

        } else {

            tvSignature.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.iv_place, R.id.ll_place, R.id.iv_id_z, R.id.iv_id_f, R.id.tv_signature, R.id.btn_commit,
            R.id.btn_yl, R.id.iv_signature})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_place://选择地区

                //选择城市
                mPresenter.getcitydata();

                break;
            case R.id.iv_id_z://身份证正面

                //每次添加之前先清理
                // 添加图片
                getActivity().setTheme(R.style.ActionSheetStyleiOS7);
                showActionSheet(ivIdZ.getId());

                break;
            case R.id.iv_id_f://身份证反面

                //每次添加之前先清理
                // 添加图片
                getActivity().setTheme(R.style.ActionSheetStyleiOS7);
                showActionSheet1(ivIdF.getId());

                break;
            case R.id.tv_signature://添加电子签名

                Intent intent = new Intent(getActivity(), SignatureActivity.class);
                startActivity(intent);

                break;
            case R.id.btn_commit://提交

                mPresenter.addInsuranceInfo();

                break;
            case R.id.btn_yl://预览

                srcollview.smoothScrollTo(0, 10);

                break;
            case R.id.iv_signature://电子签名

                intent = new Intent(getActivity(), SignatureActivity.class);
                startActivity(intent);

                break;
        }
    }

    private void showActionSheet1(int viewid) {

        ActionSheet.createBuilder(getActivity(), getFragmentManager(), viewid)
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles("选择图片")
                .setCancelableOnTouchOutside(true).setListener(new ActionSheet.ActionSheetListener() {
            @Override
            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

            }

            @Override
            public void onOtherButtonClick(ActionSheet actionSheet, int index, int viewId) {
                switch (index + 1) {
                    case 1:
                        // 选择图片
                        RxPicker.of().single(true).start(getActivity()).subscribe(new Consumer<List<ImageItem>>() {

                            @Override
                            public void accept(@NonNull List<ImageItem> imageItems) throws Exception {

                                if (!fileList.isEmpty() && fileList.size() > 0) {
                                    fileList.clear();
                                }
                                for (ImageItem imageItem : imageItems) {
                                    type = "2";
                                    compressByUs(imageItem.getPath(), "2");
                                }
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        }).show();
    }

    /**
     * @throws
     * @描述: 压缩图片
     * @创建人：zhangpeisen
     * @创建时间：2017/12/29 下午3:14
     * @修改人：zhangpeisen
     * @修改时间：2017/12/29 下午3:14
     * @修改备注：
     */
    private void compressByUs(String path, String tags) {
        Flora.with(this).calculation(new Calculation() {
            @Override
            public int calculateInSampleSize(int srcWidth, int srcHeight) {
                return super.calculateInSampleSize(srcWidth, srcHeight);
            }

            @Override
            public int calculateQuality(int srcWidth, int srcHeight, int targetWidth, int targetHeight) {
                return super.calculateQuality(srcWidth, srcHeight, targetWidth, targetHeight);
            }
        }).load(path).compress(new Callback<String>() {
            @Override
            public void callback(String compressResults) {
                fileList.add(new File(compressResults));
                tag = tags;
                mPresenter.uploadImg();
            }
        });
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
        if (username.length() > 0 && userpwd.length() > 0 && etplace.length() > 0 && ethuji.length() >
                0 && trim.length() > 0) {

            btnCommit.setClickable(true);
            btnCommit.setBackgroundResource(R.drawable.pay_bg);

        } else {

            btnCommit.setClickable(false);
            btnCommit.setBackgroundResource(R.drawable.btn_norml);
        }
    }

    /**
     * 显示更换头像
     *
     * @param viewid
     */
    public void showActionSheet(int viewid) {

        ActionSheet.createBuilder(getActivity(), getFragmentManager(), viewid)
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles("选择图片")
                .setCancelableOnTouchOutside(true).setListener(this).show();
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

    }

    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index, int viewId) {

        switch (index + 1) {
            case 1:
                // 选择图片
                RxPicker.of().single(true).start(getActivity()).subscribe(new Consumer<List<ImageItem>>() {

                    @Override
                    public void accept(@NonNull List<ImageItem> imageItems) throws Exception {

                        if (!fileList.isEmpty() && fileList.size() > 0) {
                            fileList.clear();
                        }

                        for (ImageItem imageItem : imageItems) {
                            type = "2";
                            compressByUs(imageItem.getPath(), "1");
                        }
                    }
                });
                break;
            default:
                break;
        }
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
    public void setVestAddressSuccess(AllPlaceDataBean data) {

        List<AllPlaceDataBean.GrBean> dataBeanRegion = data.getGr();
        List<AllPlaceDataBean.GrBean> gr = dataBeanRegion;

        if (gr.isEmpty()) {
            showShortToast("获取城市数据失败");
            return;
        }
        getSelectCity(gr);
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
    public void onError(String message) {

        showShortToast(message);
    }

    @Override
    public void onSucceess(ArrayList<ApiResponse<AllPlaceDataBean>> data) {

    }

    @Override
    public String getmaritalState() {

        return marital_state;
    }

    /**
     * 户籍地址
     *
     * @return
     */
    @Override
    public String getAddress() {

        return etHuji.getText().toString().trim();
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
    public String getBackPhoto() {
        return backPhotoPath;
    }

    @Override
    public String getFrontPhoto() {
        return frontPhotoPath;
    }

    @Override
    public String getSignUrl() {

        return signUrlPath;
    }

    @Override
    public String getinsurantRealname() {

        return etName.getText().toString().trim();
    }

    @Override
    public String getidNumber() {

        return etIdCard.getText().toString().trim();
    }

    @Override
    public String getphone() {

        return etPhone.getText().toString().trim();
    }

    @Override
    public String getcardNum() {

        return etBankId.getText().toString().trim();
    }

    @Override
    public String getUserId() {
        return "1";
    }

    @Override
    public List<File> getfileList() {
        return fileList;
    }

    @Override
    public void setUploadImgSuccess(ImgDataBean data) {

        if (data.getFile_path().isEmpty()) {
            return;
        }
        String imgpath = ApiUrlConstant.API_IMG_URL + data.getFile_path().get(0);
        GlideImageLoader glideImageLoader = new GlideImageLoader();
        filPath = data.getFile_path();
        if (tag.equals("1")) {
            frontPhotoPath = data.getFile_path().get(0);
            glideImageLoader.displayImage(getActivity(), imgpath, ivIdZ);
        } else if (tag.equals("2")) {
            backPhotoPath = data.getFile_path().get(0);
            glideImageLoader.displayImage(getActivity(), imgpath, ivIdF);
        } else if (tag.equals("3")) {
            signUrlPath = data.getFile_path().get(0);
            glideImageLoader.displayImage(getActivity(), imgpath, ivSignature);
            tvSignature.setVisibility(View.GONE);
        }
        showShortToast("上传成功！");
    }

    @Override
    public void onSuccess(String message) {

        showShortToast(message);
        //保存成功
        removeFragment();
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getRecordId() {
        return record_id;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        int id = group.getCheckedRadioButtonId();
        switch (id) {
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

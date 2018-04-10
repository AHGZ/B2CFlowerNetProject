package com.android.p2pflowernet.project.view.fragments.mine.applyfor.cloud;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.CloudInfoBean;
import com.android.p2pflowernet.project.entity.IdEntityBean;
import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.activity.PublicWebActivity;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.ActionSheet;
import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.bean.ImageItem;
import com.ghnor.flora.Flora;
import com.ghnor.flora.callback.Callback;
import com.ghnor.flora.spec.calculation.Calculation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

import static com.android.p2pflowernet.project.R.id.rb_man;
import static com.android.p2pflowernet.project.utils.Utils.isFastDoubleClick;

/**
 * Created by caishen on 2017/11/22.
 * by--申请云工
 */

public class ApplyForCloudFragment extends KFragment<IApplyForCloudView, IApplyForCloudPrenter>
        implements NormalTopBar.normalTopClickListener, TextWatcher, IApplyForCloudView,
        RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.et_name)
    CustomEditText etName;
    @BindView(rb_man)
    RadioButton rbMan;
    @BindView(R.id.rb_women)
    RadioButton rbWomen;
    @BindView(R.id.et_id_card)
    CustomEditText etIdCard;
    @BindView(R.id.iv_id_z)
    ImageView ivIdZ;
    @BindView(R.id.iv_id_f)
    ImageView ivIdF;
    @BindView(R.id.checkbox_im)
    CheckBox checkboxIm;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    Unbinder unbinder;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    private ShapeLoadingDialog shapeLoadingDialog;
    private String tag = "";//0--正面1--反面
    private List<File> fileList = new ArrayList<>();
    private String marital_state = "0";//1--男 2--女
    private String backPhotoPath = "", frontPhotoPath = "";
    private String isUpdata = "0";//0申请云工 1修改云工信息
    private String cloudId = "";
    private String state = "";
    private String type = "";
    private String textView_content = "我已阅读并同意《花返网云工协议》";
    private IdEntityBean idEntityBean;

    @Override
    public IApplyForCloudPrenter createPresenter() {
        return new IApplyForCloudPrenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_applyfor_cloud;
    }

    @Override
    public void initData() {

        mPresenter.getCloudInfo();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        isUpdata = arguments.getString("isUpdata");
        cloudId = arguments.getString("cloudId");
        state = arguments.getString("state");
        idEntityBean = (IdEntityBean) arguments.getSerializable("idEntityBean");

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setTitleText("填写云工资料");
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
        radioGroup.setOnCheckedChangeListener(this);


        //判断是否实名认证过。
//        mPresenter.checkIdentity();

        if (isUpdata.equals("1")) {//修改云工身份信息

            initData();
        }

        SpannableString spannableString = new SpannableString(textView_content);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.bgColor = Color.parseColor("#EEEEEE");
                ds.setColor(Color.parseColor("#019B43"));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }

            @Override
            public void onClick(View widget) {
                if (isFastDoubleClick()) {
                    return;
                }
                Intent intent = new Intent(getActivity(), PublicWebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("publicurl", ApiUrlConstant.HFW_STAFF_GREEMENT);
                intent.putExtra("tag", "1");//区别是否进入首页,0-由广告页进入首页 1-不进入首页
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }, textView_content.lastIndexOf("《"), textView_content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvDetail.setText(spannableString);
        tvDetail.setMovementMethod(LinkMovementMethod.getInstance());

        //实名认证
        if (idEntityBean != null) {
            etIdCard.setText(TextUtils.isEmpty(idEntityBean.getId_number()) ? "" : idEntityBean.getId_number());
            etName.setText(TextUtils.isEmpty(idEntityBean.getRealname()) ? "" : idEntityBean.getRealname());
            etIdCard.setEnabled(false);
            etName.setEnabled(false);
        }
    }

    public static KFragment newIntence(String isUpdata, String cloudId, String statue,IdEntityBean bean) {

        Bundle bundle = new Bundle();
        bundle.putString("isUpdata", isUpdata);
        bundle.putString("cloudId", cloudId);
        bundle.putString("state", statue);
        bundle.putSerializable("idEntityBean",bean);
        ApplyForCloudFragment applyForCloudFragment = new ApplyForCloudFragment();
        applyForCloudFragment.setArguments(bundle);
        return applyForCloudFragment;
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
        if (username.length() > 0 && userpwd.length() > 0) {
            btnCommit.setClickable(true);
            btnCommit.setBackgroundResource(R.drawable.pay_bg);
        } else {
            btnCommit.setClickable(false);
            btnCommit.setBackgroundResource(R.drawable.btn_norml);
        }
    }

    @OnClick({R.id.iv_id_z, R.id.iv_id_f, R.id.btn_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_id_z://身份证正面

                tag = "0";
                // 添加图片
                getActivity().setTheme(R.style.ActionSheetStyleiOS7);
                showActionSheet1(ivIdZ.getId());

                break;
            case R.id.iv_id_f://身份证反面

                tag = "1";
                // 添加图片
                getActivity().setTheme(R.style.ActionSheetStyleiOS7);
                showActionSheet1(ivIdF.getId());

                break;

            case R.id.btn_commit://提交
                if (!checkboxIm.isChecked()) {
                    showShortToast("请勾选花返网代理人服务协议");
                    return;
                }
                if (isUpdata.equals("0")) {//申请云工

                    mPresenter.addCloud();

                } else {//修改云工

                    mPresenter.updataCloud();
                }

                break;
        }
    }

    /**
     * 选择图片
     *
     * @param viewid
     */
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
                        RxPicker.of().single(true).limit(1, 1).start(getActivity()).subscribe(new Consumer<List<ImageItem>>() {

                            @Override
                            public void accept(@NonNull List<ImageItem> imageItems) throws Exception {

                                if (!fileList.isEmpty() && fileList.size() > 0) {
                                    fileList.clear();
                                }
                                for (ImageItem imageItem : imageItems) {
                                    compressByUs(imageItem.getPath());
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
    private void compressByUs(String path) {
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
                type = "2";
                mPresenter.uploadImg();
            }
        });
    }

    @Override
    public void showDialog() {

        shapeLoadingDialog.show();
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
    public void hideDialog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void setUploadImgSuccess(ImgDataBean data) {

        if (data != null) {

            String imgpath = ApiUrlConstant.API_IMG_URL + data.getFile_path().get(0);
            GlideImageLoader glideImageLoader = new GlideImageLoader();

            if (tag.equals("0")) {//正面
                frontPhotoPath = data.getFile_path().get(0);
                glideImageLoader.displayImage(getActivity(), imgpath, ivIdZ);
            } else if (tag.equals("1")) {//反面
                backPhotoPath = data.getFile_path().get(0);
                glideImageLoader.displayImage(getActivity(), imgpath, ivIdF);
            }

            showShortToast("上传成功");

        } else {
        }
    }

    @Override
    public void onError(String message) {

        showShortToast(message);
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
    public String getRealName() {

        return etName.getText().toString().trim();
    }

    @Override
    public String getSex() {
        return marital_state;
    }

    @Override
    public String getIdNumber() {
        return etIdCard.getText().toString().trim();
    }

    @Override
    public void successed(String message) {

        showShortToast(message);
        Intent intent = new Intent(getActivity(), SuccessCloudActivity.class);
        startActivity(intent);
        removeFragment();
    }

    @Override
    public String getCloudId() {

        return cloudId;
    }

    @Override
    public String getStatue() {
        return state;
    }

    @Override
    public void setGetCloudSuccessInfo(CloudInfoBean data) {

        if (data != null) {

            List<CloudInfoBean.StBean> st = data.getSt();
            if (st != null && st.size() > 0) {
                CloudInfoBean.StBean stBean = st.get(0);

                cloudId = stBean.getId();
                etIdCard.setText(stBean.getId_number());
                etName.setText(stBean.getRealname());
                List<CloudInfoBean.StBean.ImgsBean> imgs = stBean.getImgs();
                GlideImageLoader glideImageLoader = new GlideImageLoader();
                for (int i = 0; i < imgs.size(); i++) {

                    if (imgs.get(i).getImg_type().equals("1")) {

                        frontPhotoPath = imgs.get(i).getFile_path();
                        String imgpath = ApiUrlConstant.API_IMG_URL + imgs.get(i).getFile_path();
                        glideImageLoader.displayImage(getActivity(), imgpath, ivIdZ);

                    } else if (imgs.get(i).getImg_type().equals("2")) {

                        backPhotoPath = imgs.get(i).getFile_path();
                        String imgpath = ApiUrlConstant.API_IMG_URL + imgs.get(i).getFile_path();
                        glideImageLoader.displayImage(getActivity(), imgpath, ivIdF);
                    }
                }
                marital_state = stBean.getSex();
                if (marital_state.equals("1")) {//男
                    rbMan.setChecked(true);
                } else {//女
                    rbWomen.setChecked(true);
                }
            }
        }
    }

    @Override
    public void onSuccess(String message) {

        showShortToast(message);
        removeFragment();
    }

    @Override
    public String getType() {

        return type;
    }

    /**
     * 检测是否实名认证
     *
     * @param data
     */
    @Override
    public void setGetIdentitySuccess(IdEntityBean data) {
        etIdCard.setText(data.getId_number());
        etName.setText(data.getRealname());

        int is_checked = data.getIs_checked();

        if (is_checked == 1) {

            showShortToast("已经验证过身份信息");
            etIdCard.setEnabled(false);
            etName.setEnabled(false);

        } else {

            etIdCard.setEnabled(true);
            etName.setEnabled(true);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        int id = group.getCheckedRadioButtonId();
        switch (group.getCheckedRadioButtonId()) {

            case rb_man://男

                marital_state = "1";

                break;
            case R.id.rb_women://女

                marital_state = "2";

                break;
            default:
                break;
        }
    }
}

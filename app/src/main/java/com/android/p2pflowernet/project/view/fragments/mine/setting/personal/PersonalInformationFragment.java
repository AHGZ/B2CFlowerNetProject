package com.android.p2pflowernet.project.view.fragments.mine.setting.personal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.android.p2pflowernet.project.entity.PersonInfo;
import com.android.p2pflowernet.project.entity.ShowPersonInfo;
import com.android.p2pflowernet.project.event.UpdateUserInfoEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.RxImageLoader;
import com.android.p2pflowernet.project.utils.SPUtils;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.CircleImageView;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.ActionSheet;
import com.android.p2pflowernet.project.view.customview.actionsheet.DatePicker;
import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.bean.ImageItem;
import com.caimuhao.rxpicker.utils.DensityUtil;
import com.ghnor.flora.Flora;
import com.ghnor.flora.callback.Callback;
import com.ghnor.flora.spec.calculation.Calculation;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;


/**
 * Created by caishen on 2017/11/15.
 * by--个人信息页面
 */

public class PersonalInformationFragment extends KFragment<IpersonalView, IpersonalPerenter>
        implements ActionSheet.ActionSheetListener, IpersonalView, NormalTopBar.normalTopClickListener,
        RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.iv_user)
    CircleImageView ivUser;
    @BindView(R.id.rl_user_img)
    RelativeLayout rlUserImg;
    @BindView(R.id.tv_001)
    TextView tv001;
    @BindView(R.id.tv_user_name)
    CustomEditText edUserName;
    @BindView(R.id.rl_user_name)
    RelativeLayout rlUserName;
    @BindView(R.id.tv_002)
    TextView tv002;
    @BindView(R.id.rl_sex)
    RelativeLayout rlSex;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.rl_phone)
    RelativeLayout rlPhone;
    @BindView(R.id.rl_invite)
    RelativeLayout rlInvite;
    @BindView(R.id.tv_003)
    TextView tv003;
    @BindView(R.id.rl_date)
    RelativeLayout rlDate;
    @BindView(R.id.tv_place)
    TextView tvPlace;
    @BindView(R.id.rl_place)
    RelativeLayout rlPlace;
    @BindView(R.id.rb_men)
    RadioButton rbMen;
    @BindView(R.id.rb_women)
    RadioButton rbWomen;
    @BindView(R.id.rb_bm)
    RadioButton rbBm;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.rb_sex)
    RadioGroup rbSex;
    @BindView(R.id.tv_dlsjh)
    TextView tvDlsjh;
    @BindView(R.id.tv_yqewm)
    TextView tvYqewm;
    @BindView(R.id.tv_zhgsd)
    TextView tvZhgsd;

    private String token;
    private int imageSize;
    private int sex = 0;//性别0：保密，1：男，2：女
    private ShapeLoadingDialog shapeLoadingDialog;

    private List<File> fileList = null;
    private String file_path;

    @Override
    public IpersonalPerenter createPresenter() {
        return new IpersonalPerenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_personal_information;
    }

    @Override
    public void initData() {

        //获取个人信息
        mPresenter.showPersonalInfo();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setTitleText("个人信息");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setRightText("编辑");
        normalTop.getRightTextView().setTextColor(Color.WHITE);
        edUserName.setEnabled(false);
        rlSex.setEnabled(false);
        rlSex.setFocusableInTouchMode(false);
        rlDate.setEnabled(false);
        rbMen.setEnabled(false);
        rbWomen.setEnabled(false);
        rbBm.setEnabled(false);
        rlDate.setFocusableInTouchMode(false);
        Utils.setStatusBar(getActivity(), 0, false);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);

        //初始化加载进度条
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .delay(5000)
                .loadText("加载中...")
                .build();

        token = String.valueOf(SPUtils.get(getActivity(), "token", null));
        imageSize = DensityUtil.getDeviceWidth(ivUser.getContext()) / 3;
        normalTop.setTopClickListener(this);
        fileList = new ArrayList<>();
        rbSex.setOnCheckedChangeListener(this);

        SpannableStringBuilder builder = new SpannableStringBuilder(tvUser.getText().toString());
        SpannableStringBuilder builder1 = new SpannableStringBuilder(tv001.getText().toString());
        SpannableStringBuilder builder2 = new SpannableStringBuilder(tvDlsjh.getText().toString());
        SpannableStringBuilder builder3 = new SpannableStringBuilder(tvYqewm.getText().toString());
        SpannableStringBuilder builder4 = new SpannableStringBuilder(tv003.getText().toString());
        SpannableStringBuilder builder5 = new SpannableStringBuilder(tvZhgsd.getText().toString());
        SpannableStringBuilder builder6 = new SpannableStringBuilder(tv002.getText().toString());

        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
        builder.setSpan(redSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder1.setSpan(redSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder2.setSpan(redSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder3.setSpan(redSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder4.setSpan(redSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder5.setSpan(redSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder6.setSpan(redSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvUser.setText(builder);
        tv001.setText(builder1);
        tvDlsjh.setText(builder2);
        tvYqewm.setText(builder3);
        tv003.setText(builder4);
        tvZhgsd.setText(builder5);
        tv002.setText(builder6);

        //初始化数据
        initData();
    }

    public static KFragment newIntence() {

        PersonalInformationFragment perFragment = new PersonalInformationFragment();
        Bundle bundle = new Bundle();
        perFragment.setArguments(bundle);
        return perFragment;
    }

    @OnClick({R.id.rl_user_img, R.id.rl_user_name, R.id.rl_phone, R.id.rl_invite, R.id.rl_date, R.id.rl_place})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_user_img://用户头像

                // 更换头像
                getActivity().setTheme(R.style.ActionSheetStyleiOS7);
                showActionSheet(ivUser.getId());

                break;
            case R.id.rl_user_name://用户昵称


                break;
            case R.id.rl_phone://登录手机


                break;
            case R.id.rl_invite://邀请二维码

                //去查看二维码邀请
                startActivity(new Intent(getActivity(), QRInviteActivity.class));

                break;
            case R.id.rl_date://出生日期

                SelectProjectDate();

                break;
            case R.id.rl_place://归属地


                break;
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
                .setOtherButtonTitles("选择头像")
                .setCancelableOnTouchOutside(true).setListener(this).show();
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

    }

    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index, int viewId) {

        switch (index + 1) {
            case 1:
                if (fileList != null && fileList.size() > 0)
                    fileList.clear();
                // 选择头像
                RxPicker.of().single(true).limit(1, 1).start(getActivity()).subscribe(new Consumer<List<ImageItem>>() {

                    @Override
                    public void accept(@NonNull List<ImageItem> imageItems) throws Exception {

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
                mPresenter.mofityPhoto();
            }
        });
    }


    /**
     * 日期弹窗控件
     */
    public void SelectProjectDate() {

        DatePicker picker = new DatePicker(getActivity());
        picker.setTitleText("选择出生日期");
        picker.setSubmitTextColor(Color.parseColor("#FF2E00"));//确定
        picker.setCancelTextColor(Color.parseColor("#7B838D"));//取消
        picker.setTitleTextColor(Color.parseColor("#656565"));
        picker.setTextColor(Color.parseColor("#333333"));

        Calendar nowCalendar = Calendar.getInstance();
        int nowyear = nowCalendar.get(GregorianCalendar.YEAR);
        int nowmonth = nowCalendar.get(GregorianCalendar.MONTH);
        int nowday = nowCalendar.get(GregorianCalendar.DATE);
        picker.setSelectedItem(nowyear, nowmonth + 1, nowday + 1);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                tvDate.setText(year + "-" + month + "-" + day);
            }
        });
        picker.show();
    }

    @Override
    public void onError(String s) {

        showShortToast(s);
    }

    @Override
    public int getSex() {
        return sex;
    }

    @Override
    public String getUserName() {

        return edUserName.getText().toString().trim();
    }

    @Override
    public int getUserId() {

        return 1;
    }

    @Override
    public String getBrithDay() {

        return tvDate.getText().toString();
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

    /**
     * 修改个人信息成功回调
     *
     * @param personInfo
     */
    @Override
    public void setPersonInfo(PersonInfo personInfo) {

        showShortToast("修改成功");
    }

    @Override
    public void mofityPhoto(ImgDataBean imgDataBean) {
        if (imgDataBean.getFile_path().isEmpty()) {
            return;
        }

        String imgpath = ApiUrlConstant.API_IMG_URL + imgDataBean.getFile_path().get(0);
        file_path = imgDataBean.getFile_path().get(0);
        new RxImageLoader().display(ivUser, imgpath, imageSize, imageSize);

        //修改个人信息
        mPresenter.updatePersonal();
    }

    /**
     * 获取个人信息成功的回调
     *
     * @param data
     */
    @Override
    public void setShowPersonInfo(ShowPersonInfo data) {
        edUserName.setText(data.getNickname());
        tvDate.setText(data.getBirthday() == null ? "" : String.valueOf(data.getBirthday()));
        tvPhone.setText(data.getPhone());
        int sex = data.getSex();

        if (sex == 0) {//保密
            rbBm.setChecked(true);
        } else if (sex == 1) {//男
            rbMen.setChecked(true);
        } else if (sex == 2) {//女
            rbWomen.setChecked(true);
        } else {
            rbBm.setChecked(true);
        }

        //设置头像信息
        String imgPath = ApiUrlConstant.API_IMG_URL + data.getFile_path();
        new RxImageLoader().display(ivUser, imgPath, imageSize, imageSize);
        tvPlace.setText(data.getCity() + "" + data.getRegion());

        if (data.getFile_path() == null || TextUtils.isEmpty(data.getFile_path())) {
            return;
        }
    }

    @Override
    public File getUserImg() {
        return fileList.get(0);
    }

    @Override
    public List<File> getUserImgList() {
        return fileList;
    }

    @Override
    public String getType() {
        return "1";
    }

    @Override
    public String getFilePath() {

        return file_path;
    }

    @Override
    public void onLeftClick(View view) {

        EventBus.getDefault().post(new UpdateUserInfoEvent());
        removeFragment();
    }

    @Override
    public void onRightClick(View view) {

        if (normalTop.getRightTextView().getText().toString().equals("编辑")) {//点击编辑的状态

            normalTop.setRightText("完成");
            edUserName.setEnabled(true);
            rlDate.setEnabled(true);
            rlSex.setFocusableInTouchMode(true);
            rbMen.setEnabled(true);
            rbWomen.setEnabled(true);
            rbBm.setEnabled(true);
            rlDate.setEnabled(true);
            ivUser.setEnabled(true);
            rlDate.setFocusableInTouchMode(true);

        } else {//点击完成

            normalTop.setRightText("编辑");
            edUserName.setEnabled(false);
            rlSex.setEnabled(false);
            rlSex.setFocusableInTouchMode(false);
            rlDate.setEnabled(false);
            rbMen.setEnabled(false);
            rbWomen.setEnabled(false);
            rbBm.setEnabled(false);
            ivUser.setEnabled(false);
            rlDate.setFocusableInTouchMode(false);

            //请求接口
            mPresenter.updatePersonal();
        }
    }

    @Override
    public void onTitleClick(View view) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        int checkedRadioButtonId = group.getCheckedRadioButtonId();
        switch (checkedRadioButtonId) {
            case R.id.rb_men://男
                sex = 1;
                break;
            case R.id.rb_women://女
                sex = 2;
                break;
            case R.id.rb_bm://保密
                sex = 0;
                break;
        }
    }
}

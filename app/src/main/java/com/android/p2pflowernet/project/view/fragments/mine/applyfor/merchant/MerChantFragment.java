package com.android.p2pflowernet.project.view.fragments.mine.applyfor.merchant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
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
import com.android.p2pflowernet.project.entity.AllPlaceDataBean;
import com.android.p2pflowernet.project.entity.IdEntityBean;
import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.android.p2pflowernet.project.entity.MerInfoBean;
import com.android.p2pflowernet.project.entity.MerchTypeBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.IDCardValidate;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.activity.PublicWebActivity;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.merchant.editmechant.EditMerChantFragment;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.merchant.uploadmerchant.IUploadMerChantPrenter;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.android.p2pflowernet.project.utils.Utils.isFastDoubleClick;

/**
 * @描述:申请商家页面
 * @创建人：zhangpeisen
 * @创建时间：2017/11/15 下午4:03
 * @修改人：zhangpeisen
 * @修改时间：2017/11/15 下午4:03
 * @修改备注：
 * @throws
 */
public class MerChantFragment extends KFragment<IMerChantView, IUploadMerChantPrenter>
        implements IMerChantView, TextWatcher, CustomEditText.IMyRightDrawableClick {

    @BindView(R.id.im_back)
    // 返回按钮
            ImageView imBack;
    @BindView(R.id.merchant_idcard)
    // 商家身份证
            CustomEditText merchantIdcard;
    @BindView(R.id.realname_ed)
    //商家姓名
            CustomEditText realnameEd;
    @BindView(R.id.rgp_faren)
    // 单选数组
            RadioGroup rgpfaren;
    @BindView(R.id.rb_yes)
    // 是
            RadioButton rbYes;
    @BindView(R.id.rb_no)
    // 否
            RadioButton rbNo;
    @BindView(R.id.checkbox_im)
    // 是否同意
            CheckBox checkboxIm;
    @BindView(R.id.tv_detail)
    // 协议内容
            TextView tvDetail;
    @BindView(R.id.submit_btn)
    // 提交按钮
            Button submitBtn;
    // 是否商家法人  int 0-不是 1-是
    int Islegal = 1;
    private String state = "";
    private String id = "";
    private ShapeLoadingDialog shapeLoadingDialog;
    private String textView_content = "我已阅读并同意《花返网生活商家服务协议》按照本协议约\n定履行权利义务。";
    private IdEntityBean entityBean;

    public static MerChantFragment newIntence(String state, String id,IdEntityBean idEntityBean) {

        Bundle bundle = new Bundle();
        MerChantFragment walletFragment = new MerChantFragment();
        bundle.putString("state", state);
        bundle.putString("id", id);
        bundle.putSerializable("idEntityBean",idEntityBean);
        walletFragment.setArguments(bundle);
        return walletFragment;
    }

    @Override
    public IUploadMerChantPrenter createPresenter() {
        return new IUploadMerChantPrenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        state = arguments.getString("state");
        id = arguments.getString("id");
        entityBean = (IdEntityBean) arguments.getSerializable("idEntityBean");

    }

    @Override
    protected int getLayout() {
        return R.layout.mine_applymerchant_fragment;
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

        rgpfaren.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                if (checkedId == R.id.rb_yes) {
//                    Islegal = 1;
//                } else {
//                    Islegal = 0;
//                }
            }
        });

        merchantIdcard.addTextChangedListener(this);
        realnameEd.addTextChangedListener(this);
//        merchantIdcard.setRightDrawable(getResources().getDrawable(R.drawable.edittext_clear_close));
//        realnameEd.setRightDrawable(getResources().getDrawable(R.drawable.edittext_clear_close));
        merchantIdcard.setDrawableClick(this);
        realnameEd.setDrawableClick(this);

        //修改数据
        if (state.equals("2")) {

            initData();
        }


        SpannableString spannableString = new SpannableString(textView_content);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.bgColor = Color.parseColor("#EEEEEE");
                ds.setColor(Color.parseColor("#0ac446"));
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
                bundle.putString("publicurl", ApiUrlConstant.HFW_MERCHANT_GREEMENT);
                intent.putExtra("tag", "1");//区别是否进入首页,0-由广告页进入首页 1-不进入首页
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }, textView_content.lastIndexOf("《"), textView_content.lastIndexOf("》") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvDetail.setText(spannableString);
        tvDetail.setMovementMethod(LinkMovementMethod.getInstance());

        if (entityBean != null) {
            realnameEd.setText(TextUtils.isEmpty(entityBean.getRealname())? "" : entityBean.getRealname());
            merchantIdcard.setText(TextUtils.isEmpty(entityBean.getId_number())? "" : entityBean.getId_number());
            realnameEd.setEnabled(false);
            merchantIdcard.setEnabled(false);
        }
    }


    @Override
    public String getRealname() {
        return realnameEd.getText().toString().trim();
    }

    @Override
    public String getIdnumber() {
        return merchantIdcard.getText().toString().trim();
    }

    @Override
    public int getIslegal() {
        return 1;
    }

    @Override
    public boolean isCheck() {
        return checkboxIm.isChecked();
    }

    @Override
    public String getShopname() {
        return null;
    }

    @Override
    public String getBusinessLicensename() {
        return null;
    }


    @Override
    public String getUscc() {
        return null;
    }

    @Override
    public String getShopOwnerName() {
        return null;
    }

    @Override
    public String getShopOwnerPhone() {
        return null;
    }


    @Override
    public int getShopProvinceid() {
        return 0;
    }

    @Override
    public int getShopCityid() {
        return 0;
    }

    @Override
    public int getShopDistrictid() {
        return 0;
    }

    @Override
    public String getAreaname() {
        return null;
    }

    @Override
    public String getShopDetailAddress() {
        return null;
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
    public void onError(String errorMsg) {

        showShortToast(errorMsg);
    }

    @Override
    public void setVestAddressSuccess(AllPlaceDataBean data) {

    }

    @Override
    public void setMerchantSuccess(String message) {

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

                realnameEd.setText(aplist.get(0).getLegal_name());
                merchantIdcard.setText(aplist.get(0).getLegal_idnum());
                int is_legal = aplist.get(0).getIs_legal();
                Islegal = 1;
//                //0-不是 1-是
//                if (is_legal == 0) {
//                    rbNo.setChecked(true);
//                } else {
//                    rbYes.setChecked(true);
//                }
            }
        }
    }

    @Override
    public void onSuccess(String message) {
        showShortToast(message);
    }

    @Override
    public void setMerchTypeSuccess(MerchTypeBean data) {

    }

    @Override
    public String getfirst_cate_id() {
        return null;
    }

    @Override
    public String getsecond_cate_id() {
        return null;
    }

    @Override
    public String getthird_cate_id() {
        return null;
    }

    @OnClick({R.id.im_back, R.id.submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                // 返回按钮
                removeFragment();
                break;
            case R.id.submit_btn:
                if (!isCheck()) {
                    showShortToast("请勾选花返网生活商家服务协议");
                    return;
                }
                if (TextUtils.isEmpty(getRealname()) || getRealname().equals("")) {
                    showShortToast("请输入本人姓名");
                    return;
                }
                if (TextUtils.isEmpty(getIdnumber()) || getIdnumber().equals("")) {
                    showShortToast("请输入身份证号");
                    return;
                }

                // 判断是否符合身份证号码的规范
                boolean checkIDCard = IDCardValidate.checkIDCard(getIdnumber());
                if (!checkIDCard) {//有效返回""，无效返回错误信息
                    showShortToast("请填写正确的身份证号");
                    return;
                }
                if (getIslegal() == -1) {
                    showShortToast("请选择是否为法定代表人");
                    return;
                }
                // 传递数据并跳转下一页
                addFragment(EditMerChantFragment.newIntence(getRealname(), getIdnumber(), getIslegal(), state, id));
                break;
        }
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

    private void setButtonBackground() {

        String merchantIdcardStr = merchantIdcard.getText().toString().trim();
        String realnameStr = realnameEd.getText().toString().trim();
        if (merchantIdcardStr.length() > 0 && realnameStr.length() > 0) {
            submitBtn.setClickable(true);
            submitBtn.setBackgroundResource(R.drawable.pay_bg);
        } else {
            submitBtn.setClickable(false);
            submitBtn.setBackgroundResource(R.drawable.btn_norml);
        }
    }

    @Override
    public void rightDrawableClick(View view) {
        switch (view.getId()) {
            case R.id.realname_ed:
                realnameEd.setText("");
                break;
            case R.id.merchant_idcard:
                merchantIdcard.setText("");
                break;
        }
    }
}

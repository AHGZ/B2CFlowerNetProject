package com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.IdEntityBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.ConvertUtils;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.activity.PublicWebActivity;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ProtocoMsglDialog;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.affirm.partnerapttitude.BuyPartnerAptitudeActiivty;

import butterknife.BindView;
import butterknife.OnClick;

import static com.android.p2pflowernet.project.utils.Utils.isFastDoubleClick;

/**
 * Created by caishen on 2017/11/20.
 * by--申请合伙人
 */

public class ApplyForPartnerFragment extends KFragment<IApplyForPartnerView, IApplyForPartnerPrenter>
        implements NormalTopBar.normalTopClickListener, TextWatcher, IApplyForPartnerView {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.et_name)
    CustomEditText etName;
    @BindView(R.id.et_id_card)
    CustomEditText etIdCard;
    @BindView(R.id.checkbox_im)
    CheckBox checkboxIm;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    private ShapeLoadingDialog shapeLoadingDialog;
    private String realname;
    private int is_checked = -1;
    private String textView_content = "我已阅读并同意《花返网合伙人协议》";
    private IdEntityBean idEntityBean;

    @Override
    public IApplyForPartnerPrenter createPresenter() {
        return new IApplyForPartnerPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_applyfor_partner;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setTitleText("填写合伙人信息");
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

        //判断是否实名认证过。
//        mPresenter.checkIdentity();
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
                bundle.putString("publicurl", ApiUrlConstant.HFW_PARTNER_GREEMENT);
                intent.putExtra("tag", "1");//区别是否进入首页,0-由广告页进入首页 1-不进入首页
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }, textView_content.lastIndexOf("《"), textView_content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvDetail.setText(spannableString);
        tvDetail.setMovementMethod(LinkMovementMethod.getInstance());

        //根据实名认证信息设置
        if (idEntityBean != null) {
            etName.setText(TextUtils.isEmpty(idEntityBean.getRealname())? "" : idEntityBean.getRealname());
            etIdCard.setText(TextUtils.isEmpty(idEntityBean.getId_number())? "" : idEntityBean.getId_number());
            etName.setEnabled(false);
            etIdCard.setEnabled(false);
        }
    }

    public static KFragment newIntence(IdEntityBean bean) {

        Bundle bundle = new Bundle();
        ApplyForPartnerFragment applyForPartnerFragment = new ApplyForPartnerFragment();
        bundle.putSerializable("idEntityBean",bean);
        applyForPartnerFragment.setArguments(bundle);
        return applyForPartnerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idEntityBean = (IdEntityBean) getArguments().getSerializable("idEntityBean");
    }

    @OnClick({R.id.tv_detail, R.id.btn_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_detail://花返网合伙人协议

                //花返网合伙人协议
                ProtocoMsglDialog protocoMsglDialog = new ProtocoMsglDialog(getActivity()).builder().setTitle("花返网合伙人协议")
                        .setMsg(ConvertUtils.getString(R.string.protocotext)).setNegativeButton("我知道了", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });

                protocoMsglDialog.show();

                break;
            case R.id.btn_commit://提交

                if (!checkboxIm.isChecked()) {

                    showShortToast("请勾选花返网合伙人服务协议");

                } else {

                    mPresenter.commit();
                }

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
        if (username.length() > 0 && userpwd.length() > 0 && checkboxIm.isChecked()) {
            btnCommit.setClickable(true);
            btnCommit.setBackgroundResource(R.drawable.pay_bg);
        } else {
            btnCommit.setClickable(false);
            btnCommit.setBackgroundResource(R.drawable.btn_norml);
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

    @Override
    public String getIdCard() {
        return etIdCard.getText().toString().trim();
    }

    @Override
    public String getName() {
        return etName.getText().toString().trim();
    }

    @Override
    public String getuserId() {

        return "1";
    }

    @Override
    public void onError(String s) {

        showShortToast(s);
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void setCheckIdentitySuccess(String data) {

    }

    @Override
    public String getIschecked() {

        return is_checked + "";
    }

    @Override
    public void setGetIdentitySuccess(IdEntityBean data) {

        etIdCard.setText(data.getId_number());
        etName.setText(data.getRealname());

        realname = data.getRealname();
        is_checked = data.getIs_checked();

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
    public void showDialog() {

        shapeLoadingDialog.show();
    }

    @Override
    public void onSuccess(String s) {

        removeFragment();
        //跳转到购买合伙人资质的页面
        Intent intent = new Intent(getActivity(), BuyPartnerAptitudeActiivty.class);
        intent.putExtra("isAdd", false);//是否追加
        intent.putExtra("type", "0");//购买类型（0.合伙人，1.代理人）
        startActivity(intent);
    }
}

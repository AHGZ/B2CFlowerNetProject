package com.android.p2pflowernet.project.view.fragments.mine.wallet.bankcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.cloud.ApplyForCloudActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @描述:银行卡信息页面
 * @创建人：zhangpeisen
 * @创建时间：2017/11/15 下午4:03
 * @修改人：zhangpeisen
 * @修改时间：2017/11/15 下午4:03
 * @修改备注：
 * @throws
 */
public class BankcardInfoFragment extends KFragment<IBankcardInfoView, IBankcardInfoPrenter>
        implements IBankcardInfoView, CustomEditText.IMyRightDrawableClick, TextWatcher {

    @BindView(R.id.im_back)
    // 返回按钮
            ImageView im_back;
    @BindView(R.id.bankcardnum_ed)
    // 银行卡号
            CustomEditText bankcardnumEd;
    @BindView(R.id.realname_ed)
    // 填写姓名
            CustomEditText realnameEd;
    @BindView(R.id.idcard_ed)
    // 填写身份号
            CustomEditText idcardEd;
    @BindView(R.id.checkbox_im)
    //勾选协议
            CheckBox checkboxIm;
    @BindView(R.id.tv_detail)
    // 协议文本框
            TextView tvDetail;
    @BindView(R.id.submit_btn)
    // 提交信息按钮
            Button submitBtn;
    // 加载进度条
    private ShapeLoadingDialog shapeLoadingDialog;
    private String tag = "1";//1添加银行卡 8云工申请添加银行卡

    public static BankcardInfoFragment newIntence(String tag) {

        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        BankcardInfoFragment walletFragment = new BankcardInfoFragment();
        walletFragment.setArguments(bundle);
        return walletFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        tag = arguments.getString("tag");
    }

    @Override
    public IBankcardInfoPrenter createPresenter() {
        return new IBankcardInfoPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.mine_editbankcardinfo_fragment;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸式
        Utils.setStatusBar(getActivity(), 2, false);
        //增加控件点击区域
        UIUtils.setTouchDelegate(im_back, 50);

        bankcardnumEd.setDrawableClick(this);
        idcardEd.setDrawableClick(this);
        realnameEd.setDrawableClick(this);

        idcardEd.addTextChangedListener(this);
        realnameEd.addTextChangedListener(this);

        // 进度条初始化
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        bankcardnumEd.addTextChangedListener(watcher);
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String str = s.toString().trim().replace(" ", "");
            String result = "";
            if (str.length() >= 4) {
                bankcardnumEd.removeTextChangedListener(watcher);
                for (int i = 0; i < str.length(); i++) {
                    result += str.charAt(i);
                    if ((i + 1) % 4 == 0) {
                        result += " ";
                    }
                }
                if (result.endsWith(" ")) {
                    result = result.substring(0, result.length() - 1);
                }
                bankcardnumEd.setText(result);
                bankcardnumEd.addTextChangedListener(watcher);
                bankcardnumEd.setSelection(bankcardnumEd.getText().toString().length());//焦点到输入框最后位置
            }
        }
    };

    @Override
    public void rightDrawableClick(View view) {
        switch (view.getId()) {
            case R.id.bankcardnum_ed:
                bankcardnumEd.setText("");
                break;
            case R.id.realname_ed:
                realnameEd.setText("");
                break;
            case R.id.idcard_ed:
                idcardEd.setText("");
                break;
        }
    }

    @OnClick({R.id.im_back, R.id.submit_btn})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.im_back:

                // 返回按钮
                removeFragment();

                break;
            case R.id.submit_btn:

                String str = bankcardnumEd.getText().toString();
                // 信息提交按钮
                mPresenter.bancardadd();

                break;

        }
    }

    @Override
    public String getBankcardNo() {

        // 银行卡号
//        return bankcardnumEd.getText().toString().trim();
        String str = bankcardnumEd.getText().toString();
        return str.replace(" ", "");
    }


    @Override
    public String getRealName() {

        // 姓名
        return realnameEd.getText().toString().trim();
    }

    @Override
    public String getIdCard() {

        // 身份证号
        return idcardEd.getText().toString().trim();
    }

    @Override
    public void showDialog() {

        shapeLoadingDialog.show();
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog.isShowing())
            shapeLoadingDialog.dismiss();
    }

    @Override
    public boolean getProfile() {

        return checkboxIm.isChecked();
    }

    @Override
    public void onError(String message) {

        showShortToast(message);
    }

    @Override
    public void onSuccess(String message) {
        showShortToast(message);

        if (tag.equals("1")) {//绑定银行卡成功

            removeFragment();

        } else {//云工绑定银行卡成功

            removeFragment();
            Intent intent = new Intent(getActivity(), ApplyForCloudActivity.class);
            intent.putExtra("cloudId", "");
            intent.putExtra("isUpdata", "0");
            intent.putExtra("state", "");
            startActivity(intent);
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

        String bankcardnumStr = bankcardnumEd.getText().toString().trim();
        String realnameStr = realnameEd.getText().toString().trim();
        String idcardStr = idcardEd.getText().toString().trim();
        if (bankcardnumStr.length() > 0 && realnameStr.length() > 0 && idcardStr.length() > 0) {
            submitBtn.setClickable(true);
            submitBtn.setBackgroundResource(R.drawable.pay_bg);
        } else {
            submitBtn.setClickable(false);
            submitBtn.setBackgroundResource(R.drawable.btn_norml);
        }
    }
}

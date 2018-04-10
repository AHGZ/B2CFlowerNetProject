package com.android.p2pflowernet.project.view.fragments.register.setpwd;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.base.BaseApplication;
import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.DataBean;
import com.android.p2pflowernet.project.entity.SelectCityEnity;
import com.android.p2pflowernet.project.entity.UserInfo;
import com.android.p2pflowernet.project.event.RegestEvent;
import com.android.p2pflowernet.project.event.UserInfoEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.HFWDataManager;
import com.android.p2pflowernet.project.utils.SPUtils;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.SelectCityPicker;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.zxing.activity.CaptureActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @描述:设置密码
 * @创建人：zhangpeisen
 * @创建时间：2017/11/4 上午9:37
 * @修改人：zhangpeisen
 * @修改时间：2017/11/4 上午9:37
 * @修改备注：
 * @throws
 */
public class SetPwdFragment extends KFragment<SetPwdView, SetPwdPrenter> implements
        NormalTopBar.normalTopClickListener, SetPwdView, CustomEditText.IMyRightDrawableClick, TextWatcher {


    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.edittext_passwordvalue)
    // 设置密码
            CustomEditText edittext_passwordvalue;
    @BindView(R.id.edittext_invidtecode)
    // 设置邀请码
            CustomEditText edittext_invidtecode;
    @BindView(R.id.edittext_vestaddress)
    //归属地
            CustomEditText edittext_vestaddress;
    @BindView(R.id.commit_btn)
    // 完成按钮
            Button commitBtn;
    @BindView(R.id.iv_invidtecode)
    ImageView ivInvidtecode;
    private boolean isHidden = false;
    private int CityId;
    // 获取归属省市的集合类
    private ArrayList<SelectCityEnity> mSelectProviceEnities;
    // 加载进度条
    private ShapeLoadingDialog shapeLoadingDialog;
    // 手机号
    private String mobile;

    public static SetPwdFragment newInstance(String mobile) {
        Bundle args = new Bundle();
        SetPwdFragment fragment = new SetPwdFragment();
        args.putString("mobile", mobile);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mobile = getArguments().getString("mobile");
    }

    @Override
    public SetPwdPrenter createPresenter() {
        return new SetPwdPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fast_register_fragment;
    }

    @Override
    public void initData() {

    }


    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸式
        Utils.setStatusBar(getActivity(), 1, false);
        // 初始化标题及相关事件监听
        normalTop.setTitleText("快速注册");
        normalTop.getRightImage().setVisibility(View.GONE);
        // 扩大事件的点击范围
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        // 扩大事件的点击范围
        normalTop.setTopClickListener(this);
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .delay(5000)
                .loadText("加载中...")
                .build();

        // 选择归属地
        edittext_vestaddress.setInputType(InputType.TYPE_NULL);
        edittext_vestaddress.setFocusableInTouchMode(false);
        edittext_vestaddress.setFocusable(false);

        // 账户输入框设置右图标
        edittext_passwordvalue.setRightDrawable(getResources().getDrawable(R.drawable.icon_close_eye));
        edittext_vestaddress.setRightDrawable(getResources().getDrawable(R.mipmap.address_icon));

        // 新密码输入框设置右图标
        edittext_passwordvalue.setDrawableClick(this);
        edittext_vestaddress.setDrawableClick(this);

        edittext_vestaddress.addTextChangedListener(this);
        edittext_passwordvalue.addTextChangedListener(this);
        edittext_invidtecode.addTextChangedListener(this);

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
    public void rightDrawableClick(View view) {
        switch (view.getId()) {
            case R.id.edittext_passwordvalue:
                // 密码切换
                if (isHidden) {
                    //设置EditText文本为可见的
                    edittext_passwordvalue.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edittext_passwordvalue.setRightDrawable(getResources().getDrawable(R.drawable.icon_open_eye));
                } else {
                    //设置EditText文本为隐藏的
                    edittext_passwordvalue.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edittext_passwordvalue.setRightDrawable(getResources().getDrawable(R.drawable.icon_close_eye));
                }
                isHidden = !isHidden;
                edittext_passwordvalue.postInvalidate();

                //切换后将EditText光标置于末尾
                CharSequence charSequence = edittext_passwordvalue.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
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

        String phonevalue = edittext_passwordvalue.getText().toString().trim();
        String invidtecode = edittext_invidtecode.getText().toString().trim();
        String vestaddressvalue = edittext_vestaddress.getText().toString().trim();

        if (phonevalue.length() > 0 && invidtecode.length() > 0 && vestaddressvalue.length() > 0) {
            commitBtn.setClickable(true);
            commitBtn.setBackgroundResource(R.drawable.btn_pressed);
        } else if (phonevalue.length() > 0 && vestaddressvalue.length() > 0) {
            commitBtn.setClickable(true);
            commitBtn.setBackgroundResource(R.drawable.btn_pressed);
        } else if (vestaddressvalue.length() > 0 && phonevalue.length() > 0) {
            commitBtn.setClickable(true);
            commitBtn.setBackgroundResource(R.drawable.btn_pressed);
        } else if (vestaddressvalue.length() > 0 && phonevalue.length() > 0 && invidtecode.length() > 0) {
            commitBtn.setClickable(true);
            commitBtn.setBackgroundResource(R.drawable.btn_pressed);
        } else if (invidtecode.length() > 0 && vestaddressvalue.length() > 0 && phonevalue.length() > 0) {
            commitBtn.setClickable(true);
            commitBtn.setBackgroundResource(R.drawable.btn_pressed);
        } else {
            commitBtn.setClickable(false);
            commitBtn.setBackgroundResource(R.drawable.btn_norml);
        }
    }

    @OnClick({R.id.commit_btn, R.id.edittext_vestaddress, R.id.iv_invidtecode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.commit_btn://提交完成按钮

                if (edittext_passwordvalue.getText().toString().trim().equals("")) {
                    showShortToast("请输入密码");
                } else if (edittext_vestaddress.getText().toString().trim().equals("")) {
                    showShortToast("请选择账户归属地");
                } else {
                    mPresenter.register();
                }

                break;
            case R.id.edittext_vestaddress:

                //选择城市
                mPresenter.getcitydata();

                break;
            case R.id.iv_invidtecode://二维码扫描

                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                intent.putExtra("tag", "0");//tag --0-注册邀请码 1-线下扫码
                startActivity(intent);

                break;
        }
    }

    /**
     * 接收扫码邀请的二维码回调
     *
     * @param regest
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RegestEvent regest) {

        if (regest.getResult() != null) {//

            edittext_invidtecode.setText(regest.getResult());
        }
    }

    @Override
    public String getPhone() {

        return TextUtils.isEmpty(mobile) ? "" : mobile;
    }

    @Override
    public String getPwd() {

        return edittext_passwordvalue.getText().toString().trim();
    }

    @Override
    public String getInvidcode() {

        return edittext_invidtecode.getText().toString().trim();
    }


    @Override
    public void setVestAddressSuccess(DataBean dataBean) {
        HFWDataManager.getInstance().saveData(dataBean);

        List<DataBean.RegionBean> dataBeanRegion = dataBean.getRegion();
        SPUtils.put(getActivity(), "rts", dataBean.getRts());

        if (dataBeanRegion == null) {
            showShortToast("获取归属省市失败");
            return;
        }
        getSelectCity(dataBeanRegion);
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

    @Override
    public void onError(String errorMsg) {

        showShortToast(errorMsg);
    }


    private void getSelectCity(List<DataBean.RegionBean> regionBeanArrayList) {
        SelectCityPicker selectCityPicker = new SelectCityPicker(getActivity(), regionBeanArrayList);
        selectCityPicker.setHalfScreen(true);
        selectCityPicker.setOnCitySelectListener(new SelectCityPicker.OnCitySelectListener() {
            @Override
            public void onCitySelect(String province, String city, int provinceId, int cityId) {
                if (!province.equals("") && city.equals("")) {
                    edittext_vestaddress.setText(province);
                } else {
                    edittext_vestaddress.setText(province + "-" + city);
                }
                CityId = cityId == 0 ? provinceId : cityId;
            }
        });
        selectCityPicker.show();
    }

    @Override
    public int region() {
        return CityId;
    }

    @Override
    public void setPwdSuccess(UserInfo userinfo) {
        // 注册成功
        if (userinfo == null) {
            return;
        }
        showShortToast("注册成功");
        SPUtils.put(BaseApplication.getContext(), Constants.ISLOGIN, "isLogin");
        EventBus.getDefault().post(new UserInfoEvent(userinfo));
        removeFragment();
        getActivity().finish();
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
}

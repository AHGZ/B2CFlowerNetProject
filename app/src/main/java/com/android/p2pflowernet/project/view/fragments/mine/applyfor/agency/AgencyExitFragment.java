package com.android.p2pflowernet.project.view.fragments.mine.applyfor.agency;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.CustomEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @描述:退出代理人页面
 * @创建人：zhangpeisen
 * @创建时间：2017/11/15 下午4:03
 * @修改人：zhangpeisen
 * @修改时间：2017/11/15 下午4:03
 * @修改备注：
 * @throws
 */
public class AgencyExitFragment extends KFragment<IAgencyExitView, IAgencyExitPrenter>
        implements IAgencyExitView,CustomEditText.IMyRightDrawableClick, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.im_back)
    // 返回按钮
            ImageView imBack;
    @BindView(R.id.editagencyname_ed)
    // 姓名
            CustomEditText editagencynameEd;
    @BindView(R.id.editagencyrb_men)
    // 男
            RadioButton editagencyrbMen;
    @BindView(R.id.editagencyrb_women)
    // 女
            RadioButton editagencyrbWomen;
    @BindView(R.id.ditagency_sex_rp)
    // 单选按钮组合
            RadioGroup ditagencySexRp;
    @BindView(R.id.editagency_idcard)
    // 身份证号
            CustomEditText editagencyIdcard;
    @BindView(R.id.hfw_mobile_ed)
    // 手机号
            CustomEditText hfwMobileEd;
    @BindView(R.id.submit_btn)
    // 提交信息按钮
            Button submitBtn;
    // 状态
    private String mState;


    public static AgencyExitFragment newIntence(String state) {

        Bundle bundle = new Bundle();
        AgencyExitFragment agencyExitFragment = new AgencyExitFragment();
        bundle.putString("state", state);
        agencyExitFragment.setArguments(bundle);
        return agencyExitFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mState = getArguments().getString("state");
    }
    @Override
    public IAgencyExitPrenter createPresenter() {
        return new IAgencyExitPrenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.mine_applyexitagency_fragment;
    }

    @Override
    public void initData() {
        //初始化沉浸式
        Utils.setStatusBar(getActivity(), 2, false);
        //增加控件点击区域
        UIUtils.setTouchDelegate(imBack, 50);
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        editagencynameEd.setRightDrawable(getResources().getDrawable(R.drawable.edittext_clear_close));
        editagencyIdcard.setRightDrawable(getResources().getDrawable(R.drawable.edittext_clear_close));
        hfwMobileEd.setRightDrawable(getResources().getDrawable(R.drawable.edittext_clear_close));

        editagencynameEd.setDrawableClick(this);
        editagencyIdcard.setDrawableClick(this);
        hfwMobileEd.setDrawableClick(this);
        ditagencySexRp.setOnCheckedChangeListener(this);
    }

    @Override
    public void rightDrawableClick(View view) {
        switch (view.getId()) {
            case R.id.editagencyname_ed:
                // 姓名
                editagencynameEd.setText("");
                break;
            case R.id.editagency_idcard:
                // 身份证
                editagencyIdcard.setText("");
                break;
            case R.id.hfw_mobile_ed:
                // 手机号
                hfwMobileEd.setText("");
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        if (checkedId == R.id.editagencyrb_men) {

        } else {

        }
    }

    @OnClick({R.id.im_back, R.id.submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                // 返回按钮
                removeFragment();
                break;
            case R.id.submit_btn:
                removeFragment();
                break;
        }
    }


    @Override
    public String getState() {
        return mState;
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void hideDialog() {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onSuccess(String message) {

    }
}

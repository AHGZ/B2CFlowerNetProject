package com.android.p2pflowernet.project.view.fragments.mine.wallet.withdraw;

import android.content.Intent;
import android.icu.math.BigDecimal;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.BankInfoBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.WithDrawInfoBean;
import com.android.p2pflowernet.project.event.RefreshWithrDrawEvent;
import com.android.p2pflowernet.project.event.SetPayPwdEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.utils.MD5Utils;
import com.android.p2pflowernet.project.utils.SPUtils;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.activity.BankcardActivity;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.keyborad.PayBoardDialog;
import com.android.p2pflowernet.project.view.fragments.affirm.SetPayPwdActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @描述:提现页面
 * @创建人：zhangpeisen
 * @创建时间：2017/11/15 下午4:03
 * @修改人：zhangpeisen
 * @修改时间：2017/11/15 下午4:03
 * @修改备注：
 * @throws
 */
public class WithDrawFragment extends KFragment<IWithDrawView, IWithDrawPrenter>
        implements IWithDrawView, CustomEditText.IMyRightDrawableClick {
    @BindView(R.id.im_back)
    // 返回按钮
            ImageView im_back;
    @BindView(R.id.tv_cancel)
    // 取消按钮
            TextView tvCancel;
    @BindView(R.id.bankcardname_tv)
    // 银行卡名字
            TextView bankcardname_tv;
    @BindView(R.id.bankcardnum_tv)
    // 银行卡卡号
            TextView bankcardnum_tv;
    @BindView(R.id.bankcardicon_iv)
    // 银行卡图标
            ImageView bankcardicon_iv;
    @BindView(R.id.userbalance_tv)
    // 账户余额
            TextView userbalance_tv;
    @BindView(R.id.allwithdraw_value)
    // 全部提现按钮
            TextView allwithdrawValue;
    @BindView(R.id.withdrawamunt_ed)
    // 提现金额
            CustomEditText withdrawamuntEd;
    @BindView(R.id.withdrawtime_tv)
    // 提现到账时间
            TextView withdrawtimeTv;
    @BindView(R.id.withdraw_showhint)
    // 提现状态提示
            TextView withdrawShowhint;
    @BindView(R.id.finished_btn)
    // 完成按钮
            Button finishedBtn;
    @BindView(R.id.container_ly)
    // 完成按钮
            LinearLayout container_ly;
    @BindView(R.id.bankcard_linear)
    LinearLayout bankcard_linear;
    // 加载进度条
    private ShapeLoadingDialog shapeLoadingDialog;

    private String mPayPwdStr;

    boolean mIsPass;
    // 今天已用金额
    private String Withdrawals;
    // 余额
    private String WithdrawMoneny;


    public static WithDrawFragment newIntence() {

        Bundle bundle = new Bundle();
        WithDrawFragment walletFragment = new WithDrawFragment();
        walletFragment.setArguments(bundle);
        return walletFragment;
    }

    @Override
    public IWithDrawPrenter createPresenter() {
        return new IWithDrawPrenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.mine_withdraw_fragment;
    }

    @Override
    public void initData() {
        //初始化沉浸式
        Utils.setStatusBar(getActivity(), 2, false);
        //增加控件点击区域
        UIUtils.setTouchDelegate(im_back, 50);
        UIUtils.setTouchDelegate(allwithdrawValue, 50);
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        initData();
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        mPresenter.getBankInfo();
        withdrawamuntEd.setRightDrawable(getResources().getDrawable(R.drawable.edittext_clear_close));
        withdrawamuntEd.setDrawableClick(this);
    }

    @Override
    public void rightDrawableClick(View view) {
        switch (view.getId()) {
            case R.id.withdrawamunt_ed:
                withdrawamuntEd.setText("");
                break;
        }
    }


    @Override
    public String getPwd() {
        return TextUtils.isEmpty(mPayPwdStr) ? "" : MD5Utils.MD5To32(mPayPwdStr);
    }

    @Override
    public String getBalance() {

        return withdrawamuntEd.getText().toString().trim();
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
    public void onError(String errorMsg) {
        showShortToast(errorMsg);
    }


    @Override
    public void onSuccess(WithDrawInfoBean withDrawInfoBean) {
        if (withDrawInfoBean == null) {
            return;
        }
        // 银行卡名字
        bankcardname_tv.setText(TextUtils.isEmpty(withDrawInfoBean.getBank_name())
                && withDrawInfoBean.getBank_name().equals("") ? "" : withDrawInfoBean.getBank_name());
        // 银行卡卡号
        bankcardnum_tv.setText(TextUtils.isEmpty(withDrawInfoBean.getCard_num())
                && withDrawInfoBean.getCard_num().equals("") ? "" : withDrawInfoBean.getCard_num()
                .substring(withDrawInfoBean.getCard_num().length() - 4, withDrawInfoBean.getCard_num().length()));
        // 可用余额
        userbalance_tv.setText(TextUtils.isEmpty(withDrawInfoBean.getMoney())
                && withDrawInfoBean.getMoney().equals("") ? "" : withDrawInfoBean.getMoney());
        // 今天已用金额
        Withdrawals = TextUtils.isEmpty(withDrawInfoBean.getWithdrawals())
                && withDrawInfoBean.getWithdrawals().equals("") ? "" : withDrawInfoBean.getWithdrawals();
        // 用户金额
        WithdrawMoneny = TextUtils.isEmpty(withDrawInfoBean.getMoney())
                && withDrawInfoBean.getMoney().equals("") ? "" : withDrawInfoBean.getMoney();
        if (TextUtils.isEmpty(withDrawInfoBean.getBankimg())) {
            return;
        }
        new GlideImageLoader().displayImage(getActivity(), ApiUrlConstant.API_IMG_URL
                + withDrawInfoBean.getBankimg(), bankcardicon_iv);
    }


    public void PayDialog() {

        final PayBoardDialog payBoardDialog = new PayBoardDialog(getActivity(), 1).builder();
        payBoardDialog.setBackKey(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment();
            }
        }).show();
        payBoardDialog.setOnPayStatusListener(new PayBoardDialog.CheckPayPwdFinish() {
            @Override
            public void onPayStatus(String right, boolean isPass) {
                mIsPass = isPass;
            }

            @Override
            public void CheckPayPwdFinish(String pwd) {
                if (mIsPass) {
                    // 检测通过
                    payBoardDialog.cancel();
                    if (TextUtils.isEmpty(pwd) && pwd.equals("")) {
                        return;
                    }
                    mPayPwdStr = pwd;
                    mPresenter.checkPayPwd();
                }
            }
        });
    }

    @Override
    public void onCheckPayPwdSuccess(String message) {
        SPUtils.put(getActivity(), "checksucess", "checksucess");
        mPresenter.withdrawals();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick({R.id.im_back, R.id.tv_cancel, R.id.allwithdraw_value})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                // 返回按钮
                removeFragment();
                break;
            case R.id.tv_cancel:
                // 取消按钮
                removeFragment();
                break;
            case R.id.allwithdraw_value:
                // 设置全部用户余额
                withdrawamuntEd.setText(userbalance_tv.getText().toString().trim());
                break;
        }
    }


    @Override
    public void onSuccessCheck(CheckPwdBean data) {
        if (data != null) {
            //0: 否，1： 是
            int is_set = data.getIs_set();
            if (is_set == 0) {
                // 去设置支付密码
                Intent intent = new Intent(getActivity(), SetPayPwdActivity.class);
                intent.putExtra("tag", "7");
                startActivity(intent);
            } else {
                //检测输入的支付密码
                PayDialog();
            }
        }
    }

    @Override
    public void onSuccessBankList(BankInfoBean data, String message) {
        if (data.getList().size() == 0) {
            showShortToast("请绑定银行卡");
            bankcardicon_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), BankcardActivity.class));
                }
            });
            finishedBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showShortToast("请绑定银行卡");
                }
            });
        } else {
            mPresenter.selwithdrawals();
            finishedBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 初始化弹框
                    String checksucess = String.valueOf(SPUtils.get(getActivity(), "checksucess", ""));
                    if (TextUtils.isEmpty(getBalance()) && getBalance().equals("")) {
                        showShortToast("请输入提现金额");
                        return;

                    }else if (getBalance().equals(".")) {
                        showShortToast("请输入正确的金额，不能只输入字符");
                        return;
                    }

                    if (!checksucess.equals("")) {

                        BigDecimal mWithdrawMonenyDecimal = new BigDecimal(WithdrawMoneny);
                        // 用户余额
                        BigDecimal mDrawalsDecimal = new BigDecimal(getBalance());
                        // 今天已用金额
                        BigDecimal mWithDrawalsDecimal = new BigDecimal(Withdrawals);
                        // 最大提现金额
                        BigDecimal mMaxWithDrawDecimal = new BigDecimal("50000");
                        if (mDrawalsDecimal.compareTo(mWithdrawMonenyDecimal) == 1) {
                            // 提现金额 大于 用户余额
                            showShortToast("可提现余额不足,请及时充值");
                            return;
                        }
                        if (getBalance().equals("0") || getBalance().equals("0.00")) {
                            showShortToast("用户余额不足,请及时充值");
                            return;
                        }
                        if (mWithDrawalsDecimal.compareTo(mMaxWithDrawDecimal) == 1) {
                            // 提现金额 大于 最大提现金额
                            showShortToast("可提金额已超限");
                            return;
                        }
                        mPresenter.withdrawals();
                    } else {
                        mPresenter.HasPayPwd();
                    }
                }
            });
        }
    }

    @Override
    public void onSuccess(String message) {
        showShortToast(message);
        EventBus.getDefault().post(new RefreshWithrDrawEvent());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshWithrDrawEvent refreshWithrDrawEvent) {
        mPresenter.selwithdrawals();
    }

    /**
     * 设置支付密码成功的回调
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(SetPayPwdEvent event) {
        String invoice = event.getStr();
        if (invoice.equals("7")) {
            //检测输入的支付密码
            PayDialog();
        }
    }
}

package com.android.p2pflowernet.project.view.fragments.mine.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.IdEntityBean;
import com.android.p2pflowernet.project.entity.SelectorDialogBean;
import com.android.p2pflowernet.project.entity.UserAcountBean;
import com.android.p2pflowernet.project.event.AuthenticationEvent;
import com.android.p2pflowernet.project.event.CheckPayEvent;
import com.android.p2pflowernet.project.event.SetPayPwdEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.ParamMatchUtils;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.activity.AuthenticationActivity;
import com.android.p2pflowernet.project.view.activity.BankcardActivity;
import com.android.p2pflowernet.project.view.activity.BillActivity;
import com.android.p2pflowernet.project.view.activity.PublicWebActivity;
import com.android.p2pflowernet.project.view.customview.AppEditextAlertDialog;
import com.android.p2pflowernet.project.view.customview.SelectorAlertDialog;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.check.CheckPayPwdFragment;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.check.SetPayPwdFragment;
import com.android.p2pflowernet.project.view.fragments.mine.wallet.withdraw.WithDrawFragment;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @描述:钱包页面
 * @创建人：zhangpeisen
 * @创建时间：2017/11/15 下午4:03
 * @修改人：zhangpeisen
 * @修改时间：2017/11/15 下午4:03
 * @修改备注：
 * @throws
 */
public class WalletFragment extends KFragment<IWalletView, IWalletPrenter> implements IWalletView {
    @BindView(R.id.im_back)
    // 返回按钮
            ImageView im_back;
    @BindView(R.id.tv_bill)
    // 账单
            TextView tvBill;
    @BindView(R.id.allmoney_meun_value)
    // 总资产
            TextView allmoneyMeunValue;
    @BindView(R.id.account_amount)
    // 待返润金额
            TextView accountAmount;
    @BindView(R.id.more_linear)
    LinearLayout more_linear;

    @BindView(R.id.bankcard_linear)
    // 银行卡模块
     LinearLayout bankcardLinear;
    @BindView(R.id.im_bg)
    ImageView imBg;
    @BindView(R.id.tv_commen)
    TextView tvCommen;
    @BindView(R.id.tv_withdraw_money)
    TextView tvWithdrawMoney;
    private ShapeLoadingDialog shapeLoadingDialog;
    private String alipayAccount = "";//支付宝账号
    private String withMoey = "";//支付宝金额
    private int is_checked;
    private IdEntityBean idEntityBean;

    public static WalletFragment newIntence() {

        Bundle bundle = new Bundle();
        WalletFragment walletFragment = new WalletFragment();
        walletFragment.setArguments(bundle);
        return walletFragment;
    }

    @Override
    public IWalletPrenter createPresenter() {
        return new IWalletPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.mine_wallet_fragment;
    }

    @Override
    public void initData() {

        mPresenter.getUserAccount();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸式
        Utils.setStatusBar(getActivity(), 2, false);
        //增加控件点击区域
        UIUtils.setTouchDelegate(im_back, 50);
        UIUtils.setTouchDelegate(tvBill, 50);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        initData();

        //判断是否实名认证过。
        mPresenter.checkIdentity();
    }

    @OnClick({R.id.im_back, R.id.tv_bill, R.id.more_linear, R.id.bankcard_linear, R.id.tv_commen})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.im_back:

                // 返回按钮
                removeFragment();

                break;
            case R.id.tv_bill:

                // 账单
                startActivity(new Intent(getActivity(), BillActivity.class));

                break;
            case R.id.more_linear:
                if (is_checked == 1) {
                    showSelectorDiog("请选择提现方式");
                }else{
                    //实名认证
                    initAuthentication();
                }

                //友盟统计
                MobclickAgent.onEvent(getActivity(), "withdraw");

                break;
            case R.id.bankcard_linear:
                if (is_checked == 1) {
                    //（银行卡）更多
                    startActivity(new Intent(getActivity(), BankcardActivity.class));
                }else{
                    //实名认证
                    initAuthentication();
                }

                break;
            case R.id.tv_commen://常见问题

                Intent intent = new Intent(getActivity(), PublicWebActivity.class);
                intent.putExtra("publicurl", ApiUrlConstant.API_BASE_URL + "/home/static_html/problem");
                intent.putExtra("tag", "1");//0-由广告页进入首页 1不进入首页
                startActivity(intent);

                break;
        }
    }

    private void initAuthentication() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_authentication,null);
        TextView cancelView = (TextView) dialogView.findViewById(R.id.tv_cancel);
        TextView sureView = (TextView) dialogView.findViewById(R.id.tv_sure);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.show();
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

        sureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                startActivity(new Intent(getActivity(), AuthenticationActivity.class));
            }
        });
    }

    //提现方式选择弹窗
    private void showSelectorDiog(String title) {

        ArrayList<SelectorDialogBean> selectors = new ArrayList<>();

        for (int i = 0; i < 2; i++) {

            SelectorDialogBean selectorDialogBean = new SelectorDialogBean();
            selectorDialogBean.setId(i);
            if (i == 0) {
                selectorDialogBean.setName("提现到银行卡");
            } else {
                selectorDialogBean.setName("提现到支付宝");
            }
            selectors.add(selectorDialogBean);
        }

        SelectorAlertDialog alertDialog = new SelectorAlertDialog(getActivity(), selectors).builder().setTitle(title)
                .setOnItemClickLitener(new SelectorAlertDialog.OnItemClickLitener() {
                    @Override
                    public void onItemClickLitener(int position, View view) {

                        if (position == 0) {//提现到银行卡

                            //（待返润）更多
                            addFragment(WithDrawFragment.newIntence());

                        } else {//提现到支付宝

                            showAiPay();
                        }
                    }
                });

        alertDialog.show();
    }

    //体现到支付宝
    private void showAiPay() {

        AppEditextAlertDialog alertDialog = new AppEditextAlertDialog(getActivity()).builder().setTitle("请输入支付宝账号")
                .setPositiveButton("确定", new AppEditextAlertDialog.OnposClickLitener() {
                    @Override
                    public void onPosEditClick(String trim, String trim1, String trim2, String trim3) {

                        if (TextUtils.isEmpty(trim)) {

                            showShortToast("请输入支付宝账号");

                        } else {
                            if (ParamMatchUtils.isPhoneAvailable(trim) || ParamMatchUtils.isEmail(trim)) {
                                alipayAccount = trim;
                                showWithdraw();
                            } else {
                                showShortToast("请输入正确的支付宝账号");
                            }
                        }
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        alertDialog.show();
    }

    //提现金额
    private void showWithdraw() {

        AppEditextAlertDialog alertDialog = new AppEditextAlertDialog(getActivity()).builder().setTitle("请输入提现金额")
                .setMsg("支付宝提现每笔仅支持100-50000元，每日最多2笔共50000元")
                .setPositiveButton("确定", new AppEditextAlertDialog.OnposClickLitener() {
                    @Override
                    public void onPosEditClick(String trim, String trim1, String trim2, String trim3) {

                        if (TextUtils.isEmpty(trim)) {

                            showShortToast("请输入提现金额");

                        } else {

                            withMoey = trim;
                            mPresenter.checkPayPwd();
                        }
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        alertDialog.et_name.setHint("请输入提现金额(必填)");
        alertDialog.et_name.setInputType(8194);
        alertDialog.show();
    }

    @Override
    public void onError(String s) {

        showShortToast(s);
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

    /***
     * 检测密码成功
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CheckPayEvent event) {
        String invoice = event.getStr();
        if (invoice.equals("9")) {

            //请求支付宝提现接口
            mPresenter.withdraw();
        }
    }

    /**
     * 设置支付密码成功的回调
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(SetPayPwdEvent event) {
        String invoice = event.getStr();

        if (invoice.equals("9")) {

            //请求支付宝提现接口
            mPresenter.withdraw();
        }
    }


    /**
     * 获取我的钱包数据成功
     *
     * @param data
     */
    @Override
    public void SuccessWallet(UserAcountBean data) {

        if (data != null) {

            allmoneyMeunValue.setText(data.getMoney() == null ? "¥" : "¥" + data.getMoney());
            accountAmount.setText(data.getFrozen_money() == null ? "¥" : "¥" + data.getFrozen_money());
            tvWithdrawMoney.setText(data.getWithdraw_money() == null ? "¥" : "¥" + data.getWithdraw_money());
        }
    }

    /***
     * 检验是否设置过支付密码
     * @param data
     */
    @Override
    public void onSuccessCheck(CheckPwdBean data) {

        if (data != null) {

            //0: 否，1： 是
            int is_set = data.getIs_set();
            if (is_set == 0) {

                // 去设置支付密码
                addFragment(SetPayPwdFragment.newIntence("9"));

            } else {

                //检测输入的支付密码
                addFragment(CheckPayPwdFragment.newIntence("9"));
            }
        }
    }

    @Override
    public void onSuccessed(String message) {

        showShortToast(message);
    }

    @Override
    public String getMoney() {
        return withMoey;
    }

    @Override
    public String getAlipayAccount() {
        return alipayAccount;
    }

    @Override
    public void onSuccessedWith(String message) {

        showShortToast("提现成功");
        mPresenter.getUserAccount();
        if (shapeLoadingDialog != null) {

            shapeLoadingDialog.dismiss();
        }
    }

    /***
     * 友盟统计
     */
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("WalletPage"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("WalletPage");
    }

    /**
     * 检测是否实名认证
     *
     * @param data
     */
    @Override
    public void setGetIdentitySuccess(IdEntityBean data) {
        idEntityBean = data;
        is_checked = data.getIs_checked();
    }

    /**
     * 实名认证成功刷新
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AuthenticationEvent event){
        mPresenter.checkIdentity();
    }
}

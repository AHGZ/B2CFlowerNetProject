package com.android.p2pflowernet.project.view.fragments.affirm.pay.check;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.callback.OnPayStatusListener;
import com.android.p2pflowernet.project.event.AffirmEvent;
import com.android.p2pflowernet.project.event.CheckPayEvent;
import com.android.p2pflowernet.project.event.CheckPwdEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.MD5Utils;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.PayPwdEditText;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.keyborad.CheckPayBoardDialog;
import com.android.p2pflowernet.project.view.fragments.checkmobile.ICheckMobilePresenter;
import com.android.p2pflowernet.project.view.fragments.checkmobile.ICheckMobileView;
import com.android.p2pflowernet.project.view.fragments.mine.wallet.bankcard.BankCardInfoActivity;
import com.android.p2pflowernet.project.view.fragments.mine.wallet.bankcard.BankcardInfoFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @描述:验证支付密码页面
 * @创建人：zhangpeisen
 * @创建时间：2017/11/15 下午4:03
 * @修改人：zhangpeisen
 * @修改时间：2017/11/15 下午4:03
 * @修改备注：
 * @throws
 */
public class CheckPayPwdFragment extends KFragment<ICheckMobileView,
        ICheckMobilePresenter> implements ICheckMobileView, OnPayStatusListener {

    @BindView(R.id.im_back)
    // 返回按钮
            ImageView im_back;
    @BindView(R.id.tv_click)
    // 右边交互按钮
            TextView tvClick;
    @BindView(R.id.checkpaypwd)
    PayPwdEditText mCheckpaypwd;

    @BindView(R.id.prompt_tv)
    // 支付输入提示
            TextView Prompt_tv;
    private CheckPayBoardDialog payBoardDialog;
    private String tag = "";
    private String pwd = "";
    private ShapeLoadingDialog shapeLoadingDialog;

    public static CheckPayPwdFragment newIntence(String tag) {

        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        CheckPayPwdFragment checkMoblieFragment = new CheckPayPwdFragment();
        checkMoblieFragment.setArguments(bundle);
        return checkMoblieFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        tag = arguments.getString("tag");
    }

    @Override
    public ICheckMobilePresenter createPresenter() {
        return new ICheckMobilePresenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.mine_checkpaypwd_fragment;
    }

    @Override
    public void initData() {

        //初始化沉浸式
        Utils.setStatusBar(getActivity(), 2, false);
        //增加控件点击区域
        UIUtils.setTouchDelegate(im_back, 50);
        UIUtils.setTouchDelegate(tvClick, 50);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CheckPwdEvent event) {

        pwd = event.getPwd();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();


        payBoardDialog = new CheckPayBoardDialog(getActivity(), 0).builder();
        payBoardDialog.setPayEdittext(mCheckpaypwd);
        payBoardDialog.setPrompTv(Prompt_tv);
        payBoardDialog.showborad();

        payBoardDialog.setOnPayStatusListener(this);
        payBoardDialog.show();

        payBoardDialog.dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (payBoardDialog != null) {
                    payBoardDialog.cancel();
                }
                removeFragment();
                //点击物理返回键，
                EventBus.getDefault().post(new AffirmEvent(true));
                return false;
            }
        });

        mCheckpaypwd.setFocusable(true);
        mCheckpaypwd.setEnabled(true);

    }

    @OnClick({R.id.im_back, R.id.checkpaypwd})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.im_back:

                showShortToast("支付取消");

                // 返回按钮
                removeFragment();

                break;
            case R.id.checkpaypwd:


                break;
        }
    }

    private void checkPay() {

        payBoardDialog = new CheckPayBoardDialog(getActivity(), 0).builder();
        payBoardDialog.setPayEdittext(mCheckpaypwd);
        payBoardDialog.setPrompTv(Prompt_tv);
        payBoardDialog.showborad();

        payBoardDialog.setOnPayStatusListener(this);
        payBoardDialog.show();
    }

    @Override
    public void onError(String s) {

        showShortToast(s);
    }

    @Override
    public String getPwd() {

        return MD5Utils.MD5To32(pwd);
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void onSetSuccess(String message) {
        // 验证通过事件处理
        payBoardDialog.cancel();
        if (tag.equals("0")) {

            removeFragment();
            EventBus.getDefault().post(new CheckPayEvent("0"));//0,解绑银行卡

        } else if (tag.equals("1")) {

            removeFragment();
            addFragment(BankcardInfoFragment.newIntence("1"));//1,添加银行卡

        } else if (tag.equals("2")) {//设置-修改支付密码

            removeFragment();
            EventBus.getDefault().post(new CheckPayEvent("2"));//设置-修改支付密码

        } else if (tag.equals("3")) {//购物车订单

            removeFragment();
            EventBus.getDefault().post(new CheckPayEvent("3"));//3，购物车订单

        } else if (tag.equals("4")) {//我的订单列表

            removeFragment();
            EventBus.getDefault().post(new CheckPayEvent("4"));//我的订单列表

        } else if (tag.equals("6")) {//购买合伙人资质（代理人资质）

            removeFragment();
            EventBus.getDefault().post(new CheckPayEvent("6"));//购买合伙人资质（代理人资质）

        } else if (tag.equals("7")) {//提现

            removeFragment();
            EventBus.getDefault().post(new CheckPayEvent("7"));//提现

        } else if (tag.equals("8")) {//申请云工是否绑定银行卡

            removeFragment();
            Intent intent = new Intent(getActivity(), BankCardInfoActivity.class);
            intent.putExtra("tag", "8");
            startActivity(intent);

        }else if(tag.equals("9")) {

            removeFragment();
            EventBus.getDefault().post(new CheckPayEvent("9"));//提现
        }
    }

    @Override
    public void showDialog() {

        shapeLoadingDialog.show();
    }

    @Override
    public void onPayStatus(String right, boolean isPass) {

        if (isPass) {

            mPresenter.checkPayPwd();
        }
    }
}

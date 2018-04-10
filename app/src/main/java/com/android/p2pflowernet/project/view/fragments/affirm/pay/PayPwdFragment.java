package com.android.p2pflowernet.project.view.fragments.affirm.pay;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.callback.OnPasswordInputFinish;
import com.android.p2pflowernet.project.entity.PayPwdBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.MD5Utils;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.PasswordView;
import com.android.p2pflowernet.project.view.customview.actionsheet.AppPartnerAlertDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2017/11/2.
 * by--支付密码输入页面
 */

public class PayPwdFragment extends KFragment<IPayPwdView, IPayPwdPrenter>
        implements NormalTopBar.normalTopClickListener, IPayPwdView {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.btn_complete)
    Button btnComplete;
    @BindView(R.id.password_view)
    PasswordView passwordView;
    @BindView(R.id.tv_pay_pwd)
    TextView tvPayPwd;
    @BindView(R.id.password_view1)
    PasswordView passwordView1;
    private String pwd;
    private String confirm_pwd;
    private static final int MESSAGE_LOGIN = 1;//延迟删除输入密码

    @Override
    public IPayPwdPrenter createPresenter() {

        return new IPayPwdPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.pay_pwd_fragment;
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case MESSAGE_LOGIN://重置输入的密码

                    if (passwordView1 != null) {

                        passwordView1.setStrPassword();
                        passwordView1.getForgetTextView().setText("");
                    }

                    break;
            }
        }
    };

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setTitleText("设置支付密码");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);

        //初始化状态栏
        Utils.setStatusBar(getActivity(), 0, false);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);

        //标题栏的点击回调
        normalTop.setTopClickListener(this);

        //设置密码输入框的点击事件
        for (int i = 0; i < passwordView1.getTvList().length; i++) {

            TextView[] tvList = passwordView1.getTvList();
            tvList[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    passwordView1.getGridView().setVisibility(View.VISIBLE);
                    passwordView1.getView_gone().setVisibility(View.VISIBLE);
                    btnComplete.setVisibility(View.GONE);
                }
            });
        }


        //添加密码输入完成的响应
        passwordView.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish() {

                //也就是说——>实现你的交易逻辑什么的在这里写
                passwordView.getGridView().setVisibility(View.GONE);
                passwordView.getView_gone().setVisibility(View.GONE);
                tvPayPwd.setText("确认6位数字支付密码");
                pwd = passwordView.getStrPassword().toString().trim();
                passwordView.setVisibility(View.GONE);
                passwordView1.setVisibility(View.VISIBLE);

            }
        });

        //确认支付密码的完成响应
        passwordView1.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish() {

                if (pwd.equals(passwordView1.getStrPassword().toString().trim())) {

                    passwordView1.getForgetTextView().setText("");

                    //也就是说——>实现你的交易逻辑什么的在这里写
                    passwordView1.getGridView().setVisibility(View.GONE);
                    passwordView1.getView_gone().setVisibility(View.GONE);

                    confirm_pwd = passwordView1.getStrPassword().toString().trim();
                    btnComplete.setVisibility(View.VISIBLE);

                } else {

                    passwordView1.getForgetTextView().setText("确认密码输入有误,请重新输入");
                    passwordView1.getForgetTextView().setTextColor(Color.parseColor("#FF2E00"));
                    //延迟1秒清空输入密码
                    handler.sendEmptyMessageDelayed(MESSAGE_LOGIN, 2000);
                }
            }
        });
    }

    /**
     * @Title: showAlertMsgDialog @Description: TODO 弹出消息框 @param @param
     * msg @param @param title @param @param positive @param @param
     * negative @return_ticket void 返回类型 @throws
     */
    public void showAlertMsgDialog(String msg, String title, String positive, String negative) {
        AppPartnerAlertDialog alertDialog = new AppPartnerAlertDialog(getActivity()).builder().setTitle(title).setMsg(msg)
                .setPositiveButton(positive, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        removeFragment();
                    }
                });
        alertDialog.show();
    }

    public static KFragment newIntence() {

        Bundle bundle = new Bundle();
        PayPwdFragment payPwdFragment = new PayPwdFragment();
        payPwdFragment.setArguments(bundle);

        return payPwdFragment;
    }

    @OnClick(R.id.btn_complete)
    public void onClick() {//完成按钮的点击事件

        mPresenter.addPwd();
    }


    @Override
    public void initData() {

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
    public void onError(String s) {

        showShortToast(s);

    }

    @Override
    public void showDialog() {

    }

    @Override
    public String getPwd() {

        return MD5Utils.MD5To32(pwd);
    }

    @Override
    public String getConfirmPwd() {

        return MD5Utils.MD5To32(confirm_pwd);
    }

    @Override
    public void hideDialog() {

    }

    @Override
    public void onSetSuccess(PayPwdBean data) {

        showAlertMsgDialog("恭喜你支付密码设置成功", "温馨提示", "确定", null);

    }

    @Override
    public void onSuccess(String message) {
        showShortToast(message);
    }
}

package com.android.p2pflowernet.project.view.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.view.customview.actionsheet.AppPartnerAlertDialog;
import com.android.p2pflowernet.project.callback.OnPasswordInputFinish;

import butterknife.BindView;

/**
 * Created by caishen on 2017/11/7.
 * by--自定义输入密码的弹窗
 */

public class PayPwdPopupWindow extends PopupWindow {

    private final View view;
    private final Context mContext;
    NormalTopBar normalTop;
    @BindView(R.id.password_view)
    PasswordView passwordView;
    private static final int MESSAGE_LOGIN = 1;//延迟删除输入密码

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case MESSAGE_LOGIN://重置输入的密码

                    passwordView.setStrPassword();
                    passwordView.getForgetTextView().setText("");

                    break;
            }
        }
    };

    public PayPwdPopupWindow(Activity activity, String pwd) {
        this.mContext = activity;

        this.view = LayoutInflater.from(activity).inflate(R.layout.pay_pwd_window, null);
        normalTop = (NormalTopBar) view.findViewById(R.id.normal_top);
        passwordView = (PasswordView) view.findViewById(R.id.password_view);
        normalTop.setTitleText("输入支付密码");
        normalTop.setTopClickListener(new NormalTopBar.normalTopClickListener() {
            @Override
            public void onLeftClick(View view) {

                dismiss();
            }

            @Override
            public void onRightClick(View view) {

            }

            @Override
            public void onTitleClick(View view) {

            }
        });

        //输入完成的回调
        passwordView.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish() {

                if (pwd.equals(passwordView.getStrPassword().toString())) {

                    dismiss();
                    passwordView.getForgetTextView().setText("");
                    showAlertMsgDialog("恭喜你支付密码设置成功", "温馨提示", "确定", null);

                } else {

                    passwordView.getForgetTextView().setText("密码输入有误,请重新输入");
                    //延迟两秒清空输入密码
                    handler.sendEmptyMessageDelayed(MESSAGE_LOGIN, 1000);

                }
            }
        });

        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.id_pop_layout).getTop();

                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


    /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        // 设置弹出窗体的宽和高
        this.setHeight(height * 3 / 4);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.take_photo_anim);

    }


    /**
     * @Title: showAlertMsgDialog @Description: TODO 弹出消息框 @param @param
     * msg @param @param title @param @param positive @param @param
     * negative @return_ticket void 返回类型 @throws
     */
    public void showAlertMsgDialog(String msg, String title, String positive, String negative) {

        AppPartnerAlertDialog alertDialog = new AppPartnerAlertDialog((Activity) mContext).builder().setTitle(title).setMsg(msg)
                .setPositiveButton(positive, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        dismiss();
                    }
                });
        alertDialog.show();
    }
}

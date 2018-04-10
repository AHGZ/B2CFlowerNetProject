package com.android.p2pflowernet.project.view.customview.keyborad;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.callback.OnPayStatusListener;
import com.android.p2pflowernet.project.event.SetPayEvent;
import com.android.p2pflowernet.project.view.customview.PayPwdEditText;

import org.greenrobot.eventbus.EventBus;

import static com.android.p2pflowernet.project.utils.ConvertUtils.getResources;

/**
 * author: zhangpeisen
 * created on: 2017/11/22 上午11:57
 * description:
 */
public class PayBoardDialog implements XNumberKeyboardView.IOnKeyboardListener {

    private Display display;
    private Context context;
    // tag 0：只弹出键盘 1：验证支付密码的view
    private int mTag;
    // 向dialog设置view
    private View payView;
    // 软键盘
    XNumberKeyboardView keyboardView;
    // 支付输入提示
    TextView Prompt_tv;
    // 密码 输入框
    PayPwdEditText mCheckpaypwd;
    // 支付弹出框返回键
    private ImageView withdraw_back;
    private Dialog dialog;

    // 是否是第一次输入
    boolean IsFristInput = true;
    // 获取第一次输入密码
    private String tempTextstr;
    // 监听校验状态改变右边按钮的值
    private OnPayStatusListener onPayStatusListener;

    public PayBoardDialog(Activity activity, int tag) {
        this.context = activity;
        this.mTag = tag;
        WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public PayBoardDialog builder() {
        if (mTag == 0) {
            // 只弹出键盘
            payView = LayoutInflater.from(context).inflate(R.layout.paykeyboard_pop, null);
            keyboardView = (XNumberKeyboardView) payView.findViewById(R.id.view_keyboard);
            keyboardView.setVisibility(View.GONE);
        } else {
            // 验证支付密码的view
            payView = LayoutInflater.from(context).inflate(R.layout.mine_withdrawpaypwd_layout, null);
            keyboardView = (XNumberKeyboardView) payView.findViewById(R.id.view_keyboard);
            keyboardView = (XNumberKeyboardView) payView.findViewById(R.id.withdraw_keyboard);
            mCheckpaypwd = (PayPwdEditText) payView.findViewById(R.id.withdraw_checkpaypwd);
            Prompt_tv = (TextView) payView.findViewById(R.id.withdraw_prompt_tv);
            withdraw_back = (ImageView) payView.findViewById(R.id.withdraw_back);
        }
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.PopWindowstyle);
        dialog.setContentView(payView);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = display.getWidth();
        dialog.setCanceledOnTouchOutside(true);
        return this;
    }

    public void setOnPayStatusListener(OnPayStatusListener onpaystatuslistener) {
        this.onPayStatusListener = onpaystatuslistener;
    }

    // 点击返回
    public PayBoardDialog setBackKey(final View.OnClickListener listener) {
        withdraw_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                dialog.dismiss();
            }
        });
        return this;
    }


    // 设置支付密码
    public void setPayEdittext(PayPwdEditText checkpaypwd) {
        this.mCheckpaypwd = checkpaypwd;
    }

    // 支付输入提示
    public void setPrompTv(TextView promptv) {
        this.Prompt_tv = promptv;
    }


    private void setLayout() {

        // 当前支付密码
        mCheckpaypwd.initStyle(R.drawable.checkpaypwd_bg, 6, 0.33f,
                Color.parseColor("#D5D5D5"), Color.parseColor("#000000"), 20);
        keyboardView.setIOnKeyboardListener(this);

        if (mTag == 0) {
            mCheckpaypwd.setOnTextChangedListener(new PayPwdEditText.OnTextChangedListener() {
                @Override
                public void onChangedTv(String str) {

                    // 通过值来检测右边按钮文本的变化
                    if (onPayStatusListener != null)
                        onPayStatusListener.onPayStatus(str, false);
                }
            });
        }
        mCheckpaypwd.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {
                if (IsFristInput) {
                    // 第一次输入支付密码
                    tempTextstr = str;
                    Prompt_tv.setText("请再次输入");
                    Prompt_tv.setTextColor(getResources().getColor(R.color.black));
                    mCheckpaypwd.clearText();
                    if (mTag == 0) {
                        if (onPayStatusListener != null)
                            onPayStatusListener.onPayStatus("取消", false);
                    }

                } else {

                    if (str.equals(tempTextstr)) {

                        EventBus.getDefault().post(new SetPayEvent(tempTextstr, str));

                        // 第二次输入支付密码
                        Prompt_tv.setText("支付密码验证成功");
                        Prompt_tv.setTextColor(getResources().getColor(R.color.colorstart));
                        if (onPayStatusListener != null)
                            onPayStatusListener.onPayStatus("完成", true);
//                        if (onPayStatusListener != null)
//                            ((CheckPayPwdFinish) onPayStatusListener).CheckPayPwdFinish(str);
                        dialog.dismiss();

                    } else {

                        Prompt_tv.setText("两次输入不一致");
                        Prompt_tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                        Prompt_tv.setAnimation(shakeAnimation(2));
                        mCheckpaypwd.clearText();
                        if (mTag == 0) {
                            if (onPayStatusListener != null)
                                onPayStatusListener.onPayStatus("取消", false);
                        }
                        IsFristInput =true;
                    }
                }

                IsFristInput = false;
            }
        });
    }

    // 抖动动画
    public static Animation shakeAnimation(int counts) {

        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    public void showborad() {
        keyboardView.setVisibility(View.VISIBLE);
    }

    public void show() {
        setLayout();
        dialog.show();
    }

    public void cancel() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public void onInsertKeyEvent(String text) {
        mCheckpaypwd.setPwdText(text);
    }

    @Override
    public void onDeleteKeyEvent() {
        int last = mCheckpaypwd.getPwdText().length();
        if (last > 0) {
            //删除最后一位
            mCheckpaypwd.gePayText().delete(last - 1, last);
        }
    }

    public interface CheckPayPwdFinish extends OnPayStatusListener {
        void CheckPayPwdFinish(String pwd);
    }
}

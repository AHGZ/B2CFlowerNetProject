package com.android.p2pflowernet.project.utils;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by caishen on 2017/12/20.
 * by--倒计时
 */

public class CountDownUtil extends CountDownTimer {

    private TextView tv;//

    /**
     * @param millisInFuture    倒计时时间
     * @param countDownInterval 间隔
     * @param tv                控件
     */
    public CountDownUtil(long millisInFuture, long countDownInterval,
                         TextView tv) {
        super(millisInFuture, countDownInterval);
        this.tv = tv;

    }

    @SuppressLint("NewApi")
    @Override
    public void onTick(long millisUntilFinished) {
        tv.setText(DateUtils.getHours(millisUntilFinished));//设置时间
        //这里接收的是毫秒值,当然,我们要将他格式化一下
    }

    @SuppressLint("NewApi")
    @Override
    public void onFinish() {

    }
}

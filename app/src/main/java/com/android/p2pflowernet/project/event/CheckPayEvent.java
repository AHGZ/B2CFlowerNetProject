package com.android.p2pflowernet.project.event;

/**
 * Created by caishen on 2017/11/30.
 * by--检验是否输入支付密码成功
 */

public class CheckPayEvent {
    private final String str;

    public CheckPayEvent(String s) {
        this.str = s;
    }

    public String getStr() {
        return str;
    }
}

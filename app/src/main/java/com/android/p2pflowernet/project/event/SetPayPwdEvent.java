package com.android.p2pflowernet.project.event;

/**
 * Created by caishen on 2017/12/7.
 * by--设置支付密码
 */

public class SetPayPwdEvent {
    private final String str;

    public SetPayPwdEvent(String s) {
        this.str = s;
    }

    public String getStr() {
        return str;
    }
}

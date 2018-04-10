package com.android.p2pflowernet.project.event;

/**
 * Created by caishen on 2017/12/4.
 * by--设置支付密码
 */

public class SetPayEvent {
    private final String firstStr;
    private final String lastStr;

    public SetPayEvent(String tempTextstr, String str) {

        this.firstStr = str;
        this.lastStr = tempTextstr;
    }

    public String getFirstStr() {
        return firstStr;
    }

    public String getLastStr() {
        return lastStr;
    }
}

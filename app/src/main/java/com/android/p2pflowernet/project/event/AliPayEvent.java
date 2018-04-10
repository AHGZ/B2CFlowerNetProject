package com.android.p2pflowernet.project.event;

/**
 * Created by caishen on 2017/11/8.
 * by--支付宝支付回调
 */

public class AliPayEvent {


    String result;

    public AliPayEvent(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}

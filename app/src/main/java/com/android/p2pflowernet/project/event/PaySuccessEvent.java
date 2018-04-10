package com.android.p2pflowernet.project.event;

/**
 * author: zhangpeisen
 * created on: 2017/12/27 下午7:42
 * description:
 */
public class PaySuccessEvent {
    String ordernum;

    public PaySuccessEvent(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getOrdernum() {
        return ordernum;
    }
}

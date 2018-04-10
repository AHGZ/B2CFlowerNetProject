package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * author: zhangpeisen
 * created on: 2017/11/6 下午1:47
 * description: 银联支付返回值封装类
 */
public class UnionPayEntity implements Serializable {

    private String pay_result;

    public String getPay_result() {
        return pay_result;
    }

    public void setPay_result(String pay_result) {
        this.pay_result = pay_result;
    }
}

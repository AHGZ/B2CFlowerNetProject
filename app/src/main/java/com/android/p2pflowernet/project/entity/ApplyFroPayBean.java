package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by caishen on 2017/12/22.
 * by--身份调起支付请求
 */

public class ApplyFroPayBean implements Serializable{


    /**
     * balance : 1
     * yu_e : 100,007,460.00
     * order_num : 751893702
     * sale_price : 10,000.00
     * subject : 751893702
     */

    private int balance;
    private String yu_e;
    private String order_num;
    private String sale_price;
    private String subject;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getYu_e() {
        return yu_e;
    }

    public void setYu_e(String yu_e) {
        this.yu_e = yu_e;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}

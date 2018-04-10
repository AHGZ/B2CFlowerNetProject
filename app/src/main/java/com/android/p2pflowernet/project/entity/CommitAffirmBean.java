package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by caishen on 2017/12/14.
 * by--提交订单
 */

public class CommitAffirmBean implements Serializable{


    /**
     * balance : 0
     * yu_e : 0.00
     * order_num : 42606503
     * sale_price : 2281.00
     * subject : 42606503
     * body : 42606503,1
     */

    private int balance;
    private String yu_e;
    private String order_num;
    private String sale_price;
    private String subject;
    private String body;

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

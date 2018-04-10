package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by caishen on 2018/1/24.
 * by--
 */

public class O2oorderCommitBean implements Serializable{


    /**
     * balance : 98905579.98
     * order_num : 2883102
     * sale_price : 33
     * subject : 2883102
     * body : 2883102
     */

    private String balance;
    private String order_num;
    private String sale_price;
    private String subject;
    private String body;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
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

package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by heguozhong on 2018/1/31/031.
 */

public class GroupPutOrderBean implements Serializable{

    /**
     * yu_e : 98,742,278.48
     * order_num : 17632502
     * sale_price : 222.00
     * subject : 17632502
     * body : 17632502,1
     */

    private String yu_e;
    private String order_num;
    private String sale_price;
    private String subject;
    private String body;

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

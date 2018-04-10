package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * author: zhangpeisen
 * created on: 2017/12/6 下午5:32
 * description: 改变商品属性
 */
public class ChangeGsAttrBean implements Serializable {
    /**
     * id : 2
     * stock : 5
     * sale_price : 340.00
     * img : business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg
     */

    private String id;
    private String stock;
    private String sale_price;
    private String img;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "ChangeGsAttrBean{" +
                "id='" + id + '\'' +
                ", stock='" + stock + '\'' +
                ", sale_price='" + sale_price + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}

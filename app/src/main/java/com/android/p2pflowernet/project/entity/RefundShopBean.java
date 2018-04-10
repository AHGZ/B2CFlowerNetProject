package com.android.p2pflowernet.project.entity;

/**
 * Created by Administrator on 2018/1/24.
 */

public class RefundShopBean {
    private String shopName;//商品名
    private String imgUrl;//图片地址
    private int pPrice;//卖价
    private int oPrice;//原价
    private int number;//数量
    private int takeBack;//花返

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getpPrice() {
        return pPrice;
    }

    public void setpPrice(int pPrice) {
        this.pPrice = pPrice;
    }

    public int getoPrice() {
        return oPrice;
    }

    public void setoPrice(int oPrice) {
        this.oPrice = oPrice;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getTakeBack() {
        return takeBack;
    }

    public void setTakeBack(int takeBack) {
        this.takeBack = takeBack;
    }

    @Override
    public String toString() {
        return "RefundShopBean{" +
                "shopName=" + shopName +
                ", imgUrl=" + imgUrl +
                ", pPrice=" + pPrice +
                ", oPrice=" + oPrice +
                ", number=" + number +
                ", takeBack=" + takeBack +
                '}';
    }
}

package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author: zhangpeisen
 * created on: 2017/11/6 下午6:41
 * description:
 */
public class ShopInfoEntity implements Serializable {
    private String shopId;
    private String shopName;
    private String shopIcon;
    //..
    private List<GoodsEntity> goodsEntityList;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopIcon() {
        return shopIcon;
    }

    public void setShopIcon(String shopIcon) {
        this.shopIcon = shopIcon;
    }

    public List<GoodsEntity> getGoodsEntityList() {
        return goodsEntityList;
    }

    public void setGoodsEntityList(List<GoodsEntity> goodsEntityList) {
        this.goodsEntityList = goodsEntityList;
    }

    @Override
    public String toString() {
        return "ShopInfoEntity{" +
                "shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopIcon='" + shopIcon + '\'' +
                ", goodsEntityList=" + goodsEntityList +
                '}';
    }
}


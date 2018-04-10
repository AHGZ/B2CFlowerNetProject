package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * author: zhangpeisen
 * created on: 2017/11/6 下午6:43
 * description:
 */
public class GoodsEntity implements Serializable {
    // List<>
    private String goodsId;
    private String goodsName;
    private String goodsIcon;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsIcon() {
        return goodsIcon;
    }

    public void setGoodsIcon(String goodsIcon) {
        this.goodsIcon = goodsIcon;
    }
}

package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/9.
 * child商品类
 */
public class GoodsBean implements Serializable {

    private String name;
    private String cover_price;
    private String rabate_price;
    private String figure;
    private String product_id;
    private String shopId;
    private String color;
    private int isFirst = 2;
    private boolean shopSelect = true;
    private boolean stockOut = true;

    public boolean isStockOut() {
        return stockOut;
    }

    public void setStockOut(boolean stockOut) {
        this.stockOut = stockOut;
    }

    public void setShopSelect(boolean shopSelect) {
        this.shopSelect = shopSelect;
    }

    public int getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(int isFirst) {
        this.isFirst = isFirst;
    }

    public String getRabate_price() {
        return rabate_price;
    }

    public void setRabate_price(String rabate_price) {
        this.rabate_price = rabate_price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    private int number = 1;

    /**
     * 是否处于编辑状态
     */
    private boolean isEditing;
    /**
     * 是否被选中
     */
    private boolean isChildSelected = true;

    public GoodsBean() {


    }

    public GoodsBean(String name, String cover_price, String figure, String product_id, String shop_id, String rabate, String color) {
        this.name = name;
        this.cover_price = cover_price;
        this.figure = figure;
        this.product_id = product_id;
        this.shopId = shop_id;
        this.rabate_price = rabate;
        this.color = color;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover_price() {
        cover_price.substring(0, cover_price.length() - 1);
        return cover_price;
    }

    public void setCover_price(String cover_price) {
        this.cover_price = cover_price;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public boolean isEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public boolean isChildSelected() {
        return isChildSelected;
    }

    public void setIsChildSelected(boolean isChildSelected) {
        this.isChildSelected = isChildSelected;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "GoodsBean{" +
                "name='" + name + '\'' +
                ", cover_price='" + cover_price + '\'' +
                ", figure='" + figure + '\'' +
                ", product_id='" + product_id + '\'' +
                ", number=" + number +
                ", isEditing=" + isEditing +
                ", isChildSelected=" + isChildSelected +
                '}';
    }

    public boolean isShopSelect() {
        return shopSelect;
    }
}

package com.android.p2pflowernet.project.event;

import com.android.p2pflowernet.project.entity.ShopCart;

/**
 * Created by dalong on 2016/12/27.
 */

public class MessageEvent {

    private final String state;
    public int num;
    public double price;
    public ShopCart shopCart;


    public MessageEvent(String state, int totalNum, double price, ShopCart shopCart) {
        this.num = totalNum;
        this.price = price;
        this.shopCart = shopCart;
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ShopCart getShopCart() {
        return shopCart;
    }

    public void setShopCart(ShopCart shopCart) {
        this.shopCart = shopCart;
    }
}

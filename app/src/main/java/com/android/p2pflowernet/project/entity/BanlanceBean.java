package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2017/12/16.
 * by--余额支付
 */

public class BanlanceBean implements Serializable{


    /**
     * list : []
     * money : 99997719
     */

    private int money;
    private List<?> list;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}

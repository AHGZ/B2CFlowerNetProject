package com.android.p2pflowernet.project.event;

import java.io.Serializable;

/**
 * Created by caishen on 2017/12/16.
 * by--用户余额
 */

public class UserBanclanceBean implements Serializable{


    /**
     * money : 99990876.00
     */

    private String money;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}

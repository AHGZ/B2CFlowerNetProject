package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by caishen on 2017/12/18.
 * by--我的钱包
 */

public class UserAcountBean implements Serializable {


    /**
     * frozen_money : 4346.00
     * money : 99951816.98
     * withdraw_money : 99947470.98
     */

    private String frozen_money;//未到账资金
    private String money;//总资金
    private String withdraw_money;//可用资金

    public String getFrozen_money() {
        return frozen_money;
    }

    public void setFrozen_money(String frozen_money) {
        this.frozen_money = frozen_money;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getWithdraw_money() {
        return withdraw_money;
    }

    public void setWithdraw_money(String withdraw_money) {
        this.withdraw_money = withdraw_money;
    }
}

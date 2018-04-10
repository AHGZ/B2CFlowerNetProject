package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * author: zhangpeisen
 * created on: 2017/12/23 上午10:52
 * description: 提现内容
 */
public class WithDrawInfoBean implements Serializable {

    /**
     * card_num : 6214830104139455
     * bank_name : 招商银行
     * money : 99919260.00
     * Withdrawals : 0
     */

    private String card_num;
    private String bank_name;
    private String money;
    private String Withdrawals;



    private String bankimg;

    public String getCard_num() {
        return card_num;
    }

    public void setCard_num(String card_num) {
        this.card_num = card_num;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getWithdrawals() {
        return Withdrawals;
    }

    public void setWithdrawals(String Withdrawals) {
        this.Withdrawals = Withdrawals;
    }
    public String getBankimg() {
        return bankimg;
    }

    public void setBankimg(String bankimg) {
        this.bankimg = bankimg;
    }

    @Override
    public String toString() {
        return "WithDrawInfoBean{" +
                "card_num='" + card_num + '\'' +
                ", bank_name='" + bank_name + '\'' +
                ", money='" + money + '\'' +
                ", Withdrawals='" + Withdrawals + '\'' +
                ", bankimg='" + bankimg + '\'' +
                '}';
    }
}

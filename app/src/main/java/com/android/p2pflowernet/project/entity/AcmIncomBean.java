package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * author: zhangpeisen
 * created on: 2017/12/26 下午3:16
 * description: 累计收益
 */
public class AcmIncomBean implements Serializable {


    /**
     * is_realname : 0
     * is_partner : 0
     * is_merchant : 0
     * is_manufac : 0
     * is_agent : 0
     * merchant_money : 0
     * manufac_money : 0
     * Totalincome : 0.00
     * Totalrebate : 0.00
     * Recomincome : 0.00
     * partner : 0.00
     * agent : 0.00
     */

    private int is_realname;
    private int is_partner;
    private int is_merchant;
    private int is_manufac;
    private int is_agent;
    private int merchant_money;
    private int manufac_money;
    private String Totalincome;
    private String Totalrebate;
    private String Recomincome;
    private String partner;
    private String agent;

    public int getIs_realname() {
        return is_realname;
    }

    public void setIs_realname(int is_realname) {
        this.is_realname = is_realname;
    }

    public int getIs_partner() {
        return is_partner;
    }

    public void setIs_partner(int is_partner) {
        this.is_partner = is_partner;
    }

    public int getIs_merchant() {
        return is_merchant;
    }

    public void setIs_merchant(int is_merchant) {
        this.is_merchant = is_merchant;
    }

    public int getIs_manufac() {
        return is_manufac;
    }

    public void setIs_manufac(int is_manufac) {
        this.is_manufac = is_manufac;
    }

    public int getIs_agent() {
        return is_agent;
    }

    public void setIs_agent(int is_agent) {
        this.is_agent = is_agent;
    }

    public int getMerchant_money() {
        return merchant_money;
    }

    public void setMerchant_money(int merchant_money) {
        this.merchant_money = merchant_money;
    }

    public int getManufac_money() {
        return manufac_money;
    }

    public void setManufac_money(int manufac_money) {
        this.manufac_money = manufac_money;
    }

    public String getTotalincome() {
        return Totalincome;
    }

    public void setTotalincome(String Totalincome) {
        this.Totalincome = Totalincome;
    }

    public String getTotalrebate() {
        return Totalrebate;
    }

    public void setTotalrebate(String Totalrebate) {
        this.Totalrebate = Totalrebate;
    }

    public String getRecomincome() {
        return Recomincome;
    }

    public void setRecomincome(String Recomincome) {
        this.Recomincome = Recomincome;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    @Override
    public String toString() {
        return "AcmIncomBean{" +
                "Totalincome='" + Totalincome + '\'' +
                ", Totalrebate='" + Totalrebate + '\'' +
                ", Recomincome='" + Recomincome + '\'' +
                ", partner='" + partner + '\'' +
                ", agent='" + agent + '\'' +
                '}';
    }

}

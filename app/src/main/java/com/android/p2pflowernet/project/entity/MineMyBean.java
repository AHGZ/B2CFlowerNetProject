package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * author: zhangpeisen
 * created on: 2017/12/25 上午11:49
 * description:我的页面实体类
 */
public class MineMyBean implements Serializable {
    /**
     * Yestprofit : .00
     * zbRebate : 140.00
     * AlRebate : 51.00
     * nickname : mygod
     * invite_code : bbbbbb
     * is_partner : 1
     * is_agent : 1
     * is_merchant : 0
     * is_staff : 1
     * Totalincome : 11202.00
     * eval : 0
     * payment : 1
     * Delivergoods : 0
     * Goodsreceipt : 0
     * refund : 0
     * small_head : user_img/20171220/76838e1bb2d82d7bdb4cff0ef96ed202.png
     */

    private String Yestprofit;
    private String zbRebate;
    private String AlRebate;
    private String nickname;
    private String invite_code;
    private String is_partner;
    private String is_agent;
    private String is_merchant;
    private String is_staff;
    private String Totalincome;
    private int eval;
    private int payment;
    private int Delivergoods;
    private int Goodsreceipt;
    private int refund;
    private String small_head;
    private String is_manufac;

    public String getIs_manufac() {
        return is_manufac;
    }

    public void setIs_manufac(String is_manufac) {
        this.is_manufac = is_manufac;
    }

    public String getYestprofit() {
        return Yestprofit;
    }

    public void setYestprofit(String Yestprofit) {
        this.Yestprofit = Yestprofit;
    }

    public String getZbRebate() {
        return zbRebate;
    }

    public void setZbRebate(String zbRebate) {
        this.zbRebate = zbRebate;
    }

    public String getAlRebate() {
        return AlRebate;
    }

    public void setAlRebate(String AlRebate) {
        this.AlRebate = AlRebate;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getIs_partner() {
        return is_partner;
    }

    public void setIs_partner(String is_partner) {
        this.is_partner = is_partner;
    }

    public String getIs_agent() {
        return is_agent;
    }

    public void setIs_agent(String is_agent) {
        this.is_agent = is_agent;
    }

    public String getIs_merchant() {
        return is_merchant;
    }

    public void setIs_merchant(String is_merchant) {
        this.is_merchant = is_merchant;
    }

    public String getIs_staff() {
        return is_staff;
    }

    public void setIs_staff(String is_staff) {
        this.is_staff = is_staff;
    }

    public String getTotalincome() {
        return Totalincome;
    }

    public void setTotalincome(String Totalincome) {
        this.Totalincome = Totalincome;
    }

    public int getEval() {
        return eval;
    }

    public void setEval(int eval) {
        this.eval = eval;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public int getDelivergoods() {
        return Delivergoods;
    }

    public void setDelivergoods(int Delivergoods) {
        this.Delivergoods = Delivergoods;
    }

    public int getGoodsreceipt() {
        return Goodsreceipt;
    }

    public void setGoodsreceipt(int Goodsreceipt) {
        this.Goodsreceipt = Goodsreceipt;
    }

    public int getRefund() {
        return refund;
    }

    public void setRefund(int refund) {
        this.refund = refund;
    }

    public String getSmall_head() {
        return small_head;
    }

    public void setSmall_head(String small_head) {
        this.small_head = small_head;
    }

    @Override
    public String toString() {
        return "MineMyBean{" +
                "Yestprofit='" + Yestprofit + '\'' +
                ", zbRebate='" + zbRebate + '\'' +
                ", AlRebate='" + AlRebate + '\'' +
                ", nickname='" + nickname + '\'' +
                ", invite_code='" + invite_code + '\'' +
                ", is_partner='" + is_partner + '\'' +
                ", is_agent='" + is_agent + '\'' +
                ", is_merchant='" + is_merchant + '\'' +
                ", is_staff='" + is_staff + '\'' +
                ", Totalincome='" + Totalincome + '\'' +
                ", eval=" + eval +
                ", payment=" + payment +
                ", Delivergoods=" + Delivergoods +
                ", Goodsreceipt=" + Goodsreceipt +
                ", refund=" + refund +
                ", small_head='" + small_head + '\'' +
                '}';
    }
}

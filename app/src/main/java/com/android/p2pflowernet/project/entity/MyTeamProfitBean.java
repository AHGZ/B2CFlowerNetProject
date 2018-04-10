package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by caishen on 2017/12/19.
 * by--团队收益
 */

public class MyTeamProfitBean implements Serializable{


    /**
     * user_id : 9
     * user_count : 20
     * agent_count : 10
     * partner_count : 10
     * staff_count : 10
     * merch_count : 10
     * manufac_count : 10
     * indirect_partner_count : 19
     * order_count : 30
     * rebate_times : 11
     * consum_total : 10000.00
     * rebate_total : 100.00
     * rebate_surplus : 110.00
     * invite_income_total : 100.00
     * partner_income_total : 10.00
     * agent_income_total : 10.00
     * staff_income_total : 0.00
     * recom_rebate_profit : 10.00
     * recom_partner_profit : 10.00
     * recom_agent_profit : 20.00
     * recom_staff_profit : 12.00
     * recom_merch_profit : 13.00
     * recom_manufac_profit : 15.00
     * indirect_partner_profit : 17.00
     * contribution : 17.00
     * indirect_contribution : 18.00
     * income_total : 25.00
     * withdraw_total : 0.00
     * reference_img : user_img/20171219/bc8f4d4eb26a5a6974d2f81767f60d34.png
     * reference_nickname : 王一样的男人
     * user_img : user_img/20171219/0ce061a530b31feb6ad69cd016051b21.png
     * user_nickname : 申哥威武
     */

    private String user_id;
    private String user_count;
    private String agent_count;
    private String partner_count;
    private String staff_count;
    private String merch_count;
    private String manufac_count;
    private String indirect_partner_count;
    private String order_count;
    private String rebate_times;
    private String consum_total;
    private double rebate_total;
    private String rebate_surplus;
    private String invite_income_total;
    private String partner_income_total;
    private String agent_income_total;
    private String staff_income_total;
    private double recom_rebate_profit;
    private double recom_partner_profit;
    private double recom_agent_profit;
    private String recom_staff_profit;
    private double recom_merch_profit;
    private String recom_manufac_profit;
    private double indirect_partner_profit;
    private String contribution;
    private String indirect_contribution;
    private String income_total;
    private String withdraw_total;
    private String reference_img;
    private String reference_nickname;
    private String user_img;
    private String user_nickname;
    private String redenvelopes;//红包数

    public String getRedenvelopes() {
        return redenvelopes;
    }

    public void setRedenvelopes(String redenvelopes) {
        this.redenvelopes = redenvelopes;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_count() {
        return user_count;
    }

    public void setUser_count(String user_count) {
        this.user_count = user_count;
    }

    public String getAgent_count() {
        return agent_count;
    }

    public void setAgent_count(String agent_count) {
        this.agent_count = agent_count;
    }

    public String getPartner_count() {
        return partner_count;
    }

    public void setPartner_count(String partner_count) {
        this.partner_count = partner_count;
    }

    public String getStaff_count() {
        return staff_count;
    }

    public void setStaff_count(String staff_count) {
        this.staff_count = staff_count;
    }

    public String getMerch_count() {
        return merch_count;
    }

    public void setMerch_count(String merch_count) {
        this.merch_count = merch_count;
    }

    public String getManufac_count() {
        return manufac_count;
    }

    public void setManufac_count(String manufac_count) {
        this.manufac_count = manufac_count;
    }

    public String getIndirect_partner_count() {
        return indirect_partner_count;
    }

    public void setIndirect_partner_count(String indirect_partner_count) {
        this.indirect_partner_count = indirect_partner_count;
    }

    public String getOrder_count() {
        return order_count;
    }

    public void setOrder_count(String order_count) {
        this.order_count = order_count;
    }

    public String getRebate_times() {
        return rebate_times;
    }

    public void setRebate_times(String rebate_times) {
        this.rebate_times = rebate_times;
    }

    public String getConsum_total() {
        return consum_total;
    }

    public void setConsum_total(String consum_total) {
        this.consum_total = consum_total;
    }

    public double getRebate_total() {
        return rebate_total;
    }

    public void setRebate_total(double rebate_total) {
        this.rebate_total = rebate_total;
    }

    public String getRebate_surplus() {
        return rebate_surplus;
    }

    public void setRebate_surplus(String rebate_surplus) {
        this.rebate_surplus = rebate_surplus;
    }

    public String getInvite_income_total() {
        return invite_income_total;
    }

    public void setInvite_income_total(String invite_income_total) {
        this.invite_income_total = invite_income_total;
    }

    public String getPartner_income_total() {
        return partner_income_total;
    }

    public void setPartner_income_total(String partner_income_total) {
        this.partner_income_total = partner_income_total;
    }

    public String getAgent_income_total() {
        return agent_income_total;
    }

    public void setAgent_income_total(String agent_income_total) {
        this.agent_income_total = agent_income_total;
    }

    public String getStaff_income_total() {
        return staff_income_total;
    }

    public void setStaff_income_total(String staff_income_total) {
        this.staff_income_total = staff_income_total;
    }

    public double getRecom_rebate_profit() {
        return recom_rebate_profit;
    }

    public void setRecom_rebate_profit(double recom_rebate_profit) {
        this.recom_rebate_profit = recom_rebate_profit;
    }

    public double getRecom_partner_profit() {
        return recom_partner_profit;
    }

    public void setRecom_partner_profit(double recom_partner_profit) {
        this.recom_partner_profit = recom_partner_profit;
    }

    public double getRecom_agent_profit() {
        return recom_agent_profit;
    }

    public void setRecom_agent_profit(double recom_agent_profit) {
        this.recom_agent_profit = recom_agent_profit;
    }

    public String getRecom_staff_profit() {
        return recom_staff_profit;
    }

    public void setRecom_staff_profit(String recom_staff_profit) {
        this.recom_staff_profit = recom_staff_profit;
    }

    public double getRecom_merch_profit() {
        return recom_merch_profit;
    }

    public void setRecom_merch_profit(double recom_merch_profit) {
        this.recom_merch_profit = recom_merch_profit;
    }

    public String getRecom_manufac_profit() {
        return recom_manufac_profit;
    }

    public void setRecom_manufac_profit(String recom_manufac_profit) {
        this.recom_manufac_profit = recom_manufac_profit;
    }

    public double getIndirect_partner_profit() {
        return indirect_partner_profit;
    }

    public void setIndirect_partner_profit(double indirect_partner_profit) {
        this.indirect_partner_profit = indirect_partner_profit;
    }

    public String getContribution() {
        return contribution;
    }

    public void setContribution(String contribution) {
        this.contribution = contribution;
    }

    public String getIndirect_contribution() {
        return indirect_contribution;
    }

    public void setIndirect_contribution(String indirect_contribution) {
        this.indirect_contribution = indirect_contribution;
    }

    public String getIncome_total() {
        return income_total;
    }

    public void setIncome_total(String income_total) {
        this.income_total = income_total;
    }

    public String getWithdraw_total() {
        return withdraw_total;
    }

    public void setWithdraw_total(String withdraw_total) {
        this.withdraw_total = withdraw_total;
    }

    public String getReference_img() {
        return reference_img;
    }

    public void setReference_img(String reference_img) {
        this.reference_img = reference_img;
    }

    public String getReference_nickname() {
        return reference_nickname;
    }

    public void setReference_nickname(String reference_nickname) {
        this.reference_nickname = reference_nickname;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }
}

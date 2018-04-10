package com.android.p2pflowernet.project.entity;

import java.util.List;

/**
 * Created by zhangkun on 2018/2/6.
 */

public class RoleBean {

    /**
     * list : [{"id":"1","province_id":"110000","last_stats_date":"2018-02-06","user_count":"50","agent_count":"12","partner_count":"12","staff_count":"12","merch_count":"12","manufac_count":"12","total_order_amount":"0.00","total_rebate":"0.00","user_income":"0.00","agent_award":"0.00","agent_income":"0.00","partner_income":"0.00","staff_income":"0.00","province":"北京"},{"id":"2","province_id":"130000","last_stats_date":"2018-02-06","user_count":"60","agent_count":"14","partner_count":"16","staff_count":"20","merch_count":"21","manufac_count":"20","total_order_amount":"0.00","total_rebate":"0.00","user_income":"0.00","agent_award":"0.00","agent_income":"0.00","partner_income":"0.00","staff_income":"0.00","province":"河北省"}]
     * max : 60
     * time : 1517846400
     */

    private String max;
    private int time;
    private List<ListBean> list;

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 1
         * province_id : 110000
         * last_stats_date : 2018-02-06
         * user_count : 50
         * agent_count : 12
         * partner_count : 12
         * staff_count : 12
         * merch_count : 12
         * manufac_count : 12
         * total_order_amount : 0.00
         * total_rebate : 0.00
         * user_income : 0.00
         * agent_award : 0.00
         * agent_income : 0.00
         * partner_income : 0.00
         * staff_income : 0.00
         * province : 北京
         */

        private String id;
        private String province_id;
        private String last_stats_date;
        private String user_count;
        private String agent_count;
        private String partner_count;
        private String staff_count;
        private String merch_count;
        private String manufac_count;
        private String total_order_amount;
        private String total_rebate;
        private String user_income;
        private String agent_award;
        private String agent_income;
        private String partner_income;
        private String staff_income;
        private String province;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProvince_id() {
            return province_id;
        }

        public void setProvince_id(String province_id) {
            this.province_id = province_id;
        }

        public String getLast_stats_date() {
            return last_stats_date;
        }

        public void setLast_stats_date(String last_stats_date) {
            this.last_stats_date = last_stats_date;
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

        public String getTotal_order_amount() {
            return total_order_amount;
        }

        public void setTotal_order_amount(String total_order_amount) {
            this.total_order_amount = total_order_amount;
        }

        public String getTotal_rebate() {
            return total_rebate;
        }

        public void setTotal_rebate(String total_rebate) {
            this.total_rebate = total_rebate;
        }

        public String getUser_income() {
            return user_income;
        }

        public void setUser_income(String user_income) {
            this.user_income = user_income;
        }

        public String getAgent_award() {
            return agent_award;
        }

        public void setAgent_award(String agent_award) {
            this.agent_award = agent_award;
        }

        public String getAgent_income() {
            return agent_income;
        }

        public void setAgent_income(String agent_income) {
            this.agent_income = agent_income;
        }

        public String getPartner_income() {
            return partner_income;
        }

        public void setPartner_income(String partner_income) {
            this.partner_income = partner_income;
        }

        public String getStaff_income() {
            return staff_income;
        }

        public void setStaff_income(String staff_income) {
            this.staff_income = staff_income;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }
    }
}

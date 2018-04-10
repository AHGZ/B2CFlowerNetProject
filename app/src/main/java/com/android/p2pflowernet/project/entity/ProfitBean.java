package com.android.p2pflowernet.project.entity;

/**
 * Created by zhangkun on 2018/2/6.
 */

public class ProfitBean {

    /**
     * list : {"user_income":"0.00","agent_income":"0.00","partner_income":"0.00","staff_income":"0.00","max":"0.00","time":1517846400}
     */

    private ListBean list;

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * user_income : 0.00
         * agent_income : 0.00
         * partner_income : 0.00
         * staff_income : 0.00
         * max : 0.00
         * time : 1517846400
         */

        private String user_income;
        private String agent_income;
        private String partner_income;
        private String staff_income;
        private String max;
        private int time;

        public String getUser_income() {
            return user_income;
        }

        public void setUser_income(String user_income) {
            this.user_income = user_income;
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
    }
}

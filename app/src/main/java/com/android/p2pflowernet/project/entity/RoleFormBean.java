package com.android.p2pflowernet.project.entity;

/**
 * Created by zhangkun on 2018/2/6.
 */

public class RoleFormBean {


    /**
     * list : {"user_count":"110","agent_count":"26","partner_count":"28","staff_count":"32","merch_count":"33","manufac_count":"32","max":120,"time":1517846400}
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
         * user_count : 110
         * agent_count : 26
         * partner_count : 28
         * staff_count : 32
         * merch_count : 33
         * manufac_count : 32
         * max : 120
         * time : 1517846400
         */

        private String user_count;
        private String agent_count;
        private String partner_count;
        private String staff_count;
        private String merch_count;
        private String manufac_count;
        private int max;
        private int time;

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

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
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

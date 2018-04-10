package com.android.p2pflowernet.project.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */

public class CouponCodeBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * group_code : 33697220
         * order_num : 8611702
         * state : 0
         * code_name : 券码1
         */

        private String group_code;
        private String order_num;
        private String state;
        private String code_name;

        public String getGroup_code() {
            return group_code;
        }

        public void setGroup_code(String group_code) {
            this.group_code = group_code;
        }

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCode_name() {
            return code_name;
        }

        public void setCode_name(String code_name) {
            this.code_name = code_name;
        }
    }
}

package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author: zhangpeisen
 * created on: 2017/12/25 上午11:28
 * description:
 */
public class BillAllBean implements Serializable {


    /**
     * list : [{"type":"订单支付","created":"2018-03-12 13:07:36","money":"-3000.00"}]
     * Withdrawals : 0
     */

    private String Withdrawals;
    private List<ListBean> list;

    public String getWithdrawals() {
        return Withdrawals;
    }

    public void setWithdrawals(String Withdrawals) {
        this.Withdrawals = Withdrawals;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * type : 订单支付
         * created : 2018-03-12 13:07:36
         * money : -3000.00
         */

        private String type;
        private String created;
        private String money;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}

package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2018/1/12.
 * by--代理人申请排队数据
 */

public class AgentQuereBean implements Serializable{


    /**
     * aq : [{"phone":"18701606451","nickname":"","qual_fund_amount":"0.00","created":1515745651}]
     * count : 1
     */

    private int count;
    private List<AqBean> aq;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<AqBean> getAq() {
        return aq;
    }

    public void setAq(List<AqBean> aq) {
        this.aq = aq;
    }

    public static class AqBean {
        /**
         * phone : 18701606451
         * nickname :
         * qual_fund_amount : 0.00
         * created : 1515745651
         */

        private String phone;
        private String nickname;
        private String qual_fund_amount;
        private String created;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getQual_fund_amount() {
            return qual_fund_amount;
        }

        public void setQual_fund_amount(String qual_fund_amount) {
            this.qual_fund_amount = qual_fund_amount;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }
    }
}

package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2017/12/20.
 * by--代理办公
 */

public class AgentOfficeBean implements Serializable{


    /**
     * user : 6
     * Partner : 0
     * qual_fund_amount : 0.00
     * appro_mode : 1
     * Illegalnum : 0
     * notaudited : [{"partner_qual_amount":"100000.00","nickname":"噢噢","endtime":"2017-12-20 19:53:45"},{"partner_qual_amount":"100000.00","nickname":"","endtime":"2017-12-20 19:53:45"}]
     */

    private int user;
    private int Partner;
    private String qual_fund_amount;
    private String appro_mode;
    private int Illegalnum;
    private List<NotauditedBean> notaudited;

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getPartner() {
        return Partner;
    }

    public void setPartner(int Partner) {
        this.Partner = Partner;
    }

    public String getQual_fund_amount() {
        return qual_fund_amount;
    }

    public void setQual_fund_amount(String qual_fund_amount) {
        this.qual_fund_amount = qual_fund_amount;
    }

    public String getAppro_mode() {
        return appro_mode;
    }

    public void setAppro_mode(String appro_mode) {
        this.appro_mode = appro_mode;
    }

    public int getIllegalnum() {
        return Illegalnum;
    }

    public void setIllegalnum(int Illegalnum) {
        this.Illegalnum = Illegalnum;
    }

    public List<NotauditedBean> getNotaudited() {
        return notaudited;
    }

    public void setNotaudited(List<NotauditedBean> notaudited) {
        this.notaudited = notaudited;
    }

    public static class NotauditedBean {
        /**
         * partner_qual_amount : 100000.00
         * nickname : 噢噢
         * endtime : 2017-12-20 19:53:45
         */

        private String partner_qual_amount;
        private String nickname;
        private String endtime;
        private String record_id;

        public String getRecord_id() {
            return record_id;
        }

        public void setRecord_id(String record_id) {
            this.record_id = record_id;
        }

        public String getPartner_qual_amount() {
            return partner_qual_amount;
        }

        public void setPartner_qual_amount(String partner_qual_amount) {
            this.partner_qual_amount = partner_qual_amount;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }
    }
}

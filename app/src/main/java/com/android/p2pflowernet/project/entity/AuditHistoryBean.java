package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2017/12/1.
 * by--审批历史
 */

public class AuditHistoryBean implements Serializable {

    /**
     * nickname : mygod
     * created : 1514165352
     * partner_qual_amount : 40000.00
     * agent_appro_state : 1
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        private String nickname;
        private String created;
        private String partner_qual_amount;
        private String agent_appro_state;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getPartner_qual_amount() {
            return partner_qual_amount;
        }

        public void setPartner_qual_amount(String partner_qual_amount) {
            this.partner_qual_amount = partner_qual_amount;
        }

        public String getAgent_appro_state() {
            return agent_appro_state;
        }

        public void setAgent_appro_state(String agent_appro_state) {
            this.agent_appro_state = agent_appro_state;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "nickname='" + nickname + '\'' +
                    ", created='" + created + '\'' +
                    ", partner_qual_amount='" + partner_qual_amount + '\'' +
                    ", agent_appro_state='" + agent_appro_state + '\'' +
                    '}';
        }
    }

    public AuditHistoryBean(List<ListBean> list) {
        this.list = list;
    }
}

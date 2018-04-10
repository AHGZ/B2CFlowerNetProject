package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2017/12/19.
 * by--我的团队贡献排行榜
 */

public class ContriRankBean implements Serializable {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * phone : 18513081389
         * nickname : 666666
         * contribution : 39.00
         * created : 1511432222
         */

        private String phone;
        private String nickname;
        private String contribution;
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

        public String getContribution() {
            return contribution;
        }

        public void setContribution(String contribution) {
            this.contribution = contribution;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }
    }
}

package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2017/12/28.
 * by--指定消息列表
 */

public class MessaTypeBean implements Serializable{


    private List<ListsBean> lists;

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public static class ListsBean {
        /**
         * id : 17
         * title : 合伙人审批通知
         * content : 恭喜您！由于会员188****8887已经升级为合伙人，作为间接推荐人平台发放的300.00元奖励金已经到帐，请进入个人中心查看。
         * is_read : 0
         * created : 2017-12-28 15:47:10
         */

        private String id;
        private String title;
        private String content;
        private String is_read;
        private String created;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIs_read() {
            return is_read;
        }

        public void setIs_read(String is_read) {
            this.is_read = is_read;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }
    }
}

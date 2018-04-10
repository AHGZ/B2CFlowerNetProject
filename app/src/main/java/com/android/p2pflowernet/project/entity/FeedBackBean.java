package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2017/11/24.
 * by--意见反馈
 */

public class FeedBackBean implements Serializable{


    private List<FlBean> fl;

    public List<FlBean> getFl() {
        return fl;
    }

    public void setFl(List<FlBean> fl) {
        this.fl = fl;
    }

    public static class FlBean {
        /**
         * id : 4
         * user_id : 9
         * content : 31618股
         * type : 1
         * is_read : 0
         * reply_content :
         * reply_admin_id : 1
         * reply_time : 0
         * created : 1511505872
         * login_name : 12313123123123
         */

        private String id;
        private String user_id;
        private String content;
        private String type;
        private String is_read;
        private String reply_content;
        private String reply_admin_id;
        private String reply_time;
        private String created;
        private String login_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIs_read() {
            return is_read;
        }

        public void setIs_read(String is_read) {
            this.is_read = is_read;
        }

        public String getReply_content() {
            return reply_content;
        }

        public void setReply_content(String reply_content) {
            this.reply_content = reply_content;
        }

        public String getReply_admin_id() {
            return reply_admin_id;
        }

        public void setReply_admin_id(String reply_admin_id) {
            this.reply_admin_id = reply_admin_id;
        }

        public String getReply_time() {
            return reply_time;
        }

        public void setReply_time(String reply_time) {
            this.reply_time = reply_time;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getLogin_name() {
            return login_name;
        }

        public void setLogin_name(String login_name) {
            this.login_name = login_name;
        }
    }
}

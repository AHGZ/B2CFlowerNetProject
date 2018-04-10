package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by caishen on 2017/12/28.
 * by--
 */

public class MessaDetailBean implements Serializable{


    /**
     * info : {"id":"14","title":"合伙人申请通过","content":"恭喜您！您已成功购买合伙人资质，赶快踏上新的花返旅程吧！","is_read":"1","created":"1514447230","up_start":1}
     */

    private InfoBean info;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * id : 14
         * title : 合伙人申请通过
         * content : 恭喜您！您已成功购买合伙人资质，赶快踏上新的花返旅程吧！
         * is_read : 1
         * created : 1514447230
         * up_start : 1
         */

        private String id;
        private String title;
        private String content;
        private String is_read;
        private String created;
        private int up_start;

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

        public int getUp_start() {
            return up_start;
        }

        public void setUp_start(int up_start) {
            this.up_start = up_start;
        }
    }
}

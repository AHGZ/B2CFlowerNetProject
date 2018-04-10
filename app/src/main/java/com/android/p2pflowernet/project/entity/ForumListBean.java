package com.android.p2pflowernet.project.entity;

import java.util.List;

/**
 * Created by zhangkun on 2018/1/29.
 */

public class ForumListBean {

    private List<ListsBean> lists;

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public static class ListsBean {
        /**
         * id : 1
         * title : 鸡毛天天吃面
         * short_title : 鸡毛吃面
         * channel_id : 1
         * is_top : 0
         * channel : 云工
         * type : 2
         * show_img : 0
         * video_id : 82
         * updated : 1592223365
         * url : o2o_good/2018/01-03/3b27acd6a7f856cad3835dfbc61eecae.jpg
         */

        private int id;
        private String title;
        private String short_title;
        private int channel_id;
        private int is_top;
        private String channel;
        private int type;
        private int show_img;
        private int video_id;
        private int updated;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getShort_title() {
            return short_title;
        }

        public void setShort_title(String short_title) {
            this.short_title = short_title;
        }

        public int getChannel_id() {
            return channel_id;
        }

        public void setChannel_id(int channel_id) {
            this.channel_id = channel_id;
        }

        public int getIs_top() {
            return is_top;
        }

        public void setIs_top(int is_top) {
            this.is_top = is_top;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getShow_img() {
            return show_img;
        }

        public void setShow_img(int show_img) {
            this.show_img = show_img;
        }

        public int getVideo_id() {
            return video_id;
        }

        public void setVideo_id(int video_id) {
            this.video_id = video_id;
        }

        public int getUpdated() {
            return updated;
        }

        public void setUpdated(int updated) {
            this.updated = updated;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}

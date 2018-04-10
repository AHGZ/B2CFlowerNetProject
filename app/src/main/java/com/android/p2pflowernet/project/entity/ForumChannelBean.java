package com.android.p2pflowernet.project.entity;

import java.util.List;

/**
 * Created by zhangkun on 2018/1/29.
 */

public class ForumChannelBean {

    private List<ChannelBean> channel;

    public List<ChannelBean> getChannel() {
        return channel;
    }

    public void setChannel(List<ChannelBean> channel) {
        this.channel = channel;
    }

    public static class ChannelBean {
        /**
         * id : 1
         * title : 云工
         */

        private int id;
        private String title;

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
    }
}

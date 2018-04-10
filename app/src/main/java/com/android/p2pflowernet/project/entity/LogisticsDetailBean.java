package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * author: zhangpeisen
 * created on: 2017/12/23 下午3:05
 * description: 物流详情
 */
public class LogisticsDetailBean implements Serializable {
    /**
     * time : 2017-12-23 08:37:55
     * ftime : 2017-12-23 08:37:55
     * context : 北京市 派送不成功-原因：节假日延迟派送
     */

    private ArrayList<ListsBean> lists;

    public ArrayList<ListsBean> getLists() {
        return lists;
    }

    public void setLists(ArrayList<ListsBean> lists) {
        this.lists = lists;
    }

    public static class ListsBean implements Serializable {
        private String time;
        private String ftime;
        private String context;

        private boolean isHead;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public boolean isHead() {
            return isHead;
        }

        public void setHead(boolean isHead) {
            this.isHead = isHead;
        }

        @Override
        public String toString() {
            return "ListsBean{" +
                    "time='" + time + '\'' +
                    ", ftime='" + ftime + '\'' +
                    ", context='" + context + '\'' +
                    ", isHead=" + isHead +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LogisticsDetailBean{" +
                "lists=" + lists +
                '}';
    }
}

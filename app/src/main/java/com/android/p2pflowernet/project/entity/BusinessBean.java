package com.android.p2pflowernet.project.entity;

import java.util.List;

/**
 * Created by zhangkun on 2018/2/6.
 */

public class BusinessBean {

    /**
     * list : [{"award_count":"35","last_stats_date":"2018-02-06","province_id":"110000","province":"北京"},{"award_count":"100","last_stats_date":"2018-02-06","province_id":"130000","province":"河北省"}]
     * time : 1517846400
     */

    private int time;
    private List<ListBean> list;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * award_count : 35
         * last_stats_date : 2018-02-06
         * province_id : 110000
         * province : 北京
         */

        private String award_count;
        private String last_stats_date;
        private String province_id;
        private String province;

        public String getAward_count() {
            return award_count;
        }

        public void setAward_count(String award_count) {
            this.award_count = award_count;
        }

        public String getLast_stats_date() {
            return last_stats_date;
        }

        public void setLast_stats_date(String last_stats_date) {
            this.last_stats_date = last_stats_date;
        }

        public String getProvince_id() {
            return province_id;
        }

        public void setProvince_id(String province_id) {
            this.province_id = province_id;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }
    }
}

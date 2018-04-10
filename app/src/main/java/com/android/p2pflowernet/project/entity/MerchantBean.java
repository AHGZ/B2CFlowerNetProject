package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by heguozhong on 2018/1/13/013.
 * 店铺商家信息
 */

public class MerchantBean implements Serializable {

    /**
     * info : {"id":"1","area_name":"北京北京市东城区","address":"你在哪里呢。我","manager_tel":"18513028980","distrib":"商家配送","time_setting":[{"starttime":"0","endtime":"86340"}],"activity":[{"order_amount":"100.00","rebate":"20.00"},{"order_amount":"180.00","rebate":"10.00"},{"order_amount":"200.00","rebate":"40.00"}]}
     */

    private InfoBean info;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean implements Serializable{
        /**
         * id : 1
         * area_name : 北京北京市东城区
         * address : 你在哪里呢。我
         * manager_tel : 18513028980
         * distrib : 商家配送
         * time_setting : [{"starttime":"0","endtime":"86340"}]
         * activity : [{"order_amount":"100.00","rebate":"20.00"},{"order_amount":"180.00","rebate":"10.00"},{"order_amount":"200.00","rebate":"40.00"}]
         */

        private String id;
        private String area_name;
        private String address;
        private String manager_tel;
        private String distrib;
        private List<TimeSettingBean> time_setting;
        private List<ActivityBean> activity;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getManager_tel() {
            return manager_tel;
        }

        public void setManager_tel(String manager_tel) {
            this.manager_tel = manager_tel;
        }

        public String getDistrib() {
            return distrib;
        }

        public void setDistrib(String distrib) {
            this.distrib = distrib;
        }

        public List<TimeSettingBean> getTime_setting() {
            return time_setting;
        }

        public void setTime_setting(List<TimeSettingBean> time_setting) {
            this.time_setting = time_setting;
        }

        public List<ActivityBean> getActivity() {
            return activity;
        }

        public void setActivity(List<ActivityBean> activity) {
            this.activity = activity;
        }

        public static class TimeSettingBean implements Serializable{
            /**
             * starttime : 0
             * endtime : 86340
             */

            private String starttime;
            private String endtime;

            public String getStarttime() {
                return starttime;
            }

            public void setStarttime(String starttime) {
                this.starttime = starttime;
            }

            public String getEndtime() {
                return endtime;
            }

            public void setEndtime(String endtime) {
                this.endtime = endtime;
            }
        }

        public static class ActivityBean implements Serializable{
            /**
             * order_amount : 100.00
             * rebate : 20.00
             */

            private float order_amount;
            private float rebate;

            public float getOrder_amount() {
                return order_amount;
            }

            public void setOrder_amount(float order_amount) {
                this.order_amount = order_amount;
            }

            public float getRebate() {
                return rebate;
            }

            public void setRebate(float rebate) {
                this.rebate = rebate;
            }
        }
    }
}

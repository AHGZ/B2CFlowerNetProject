package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by heguozhong on 2018/1/29/029.
 * 外卖美食三级
 */

public class TakeCateThreeBean implements Serializable{

    private List<ListBean> list;
    private List<ZongheBean> zonghe;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<ZongheBean> getZonghe() {
        return zonghe;
    }

    public void setZonghe(List<ZongheBean> zonghe) {
        this.zonghe = zonghe;
    }

    public static class ListBean {
        /**
         * distance : 973
         * distrib_quota : 19.00
         * service_time : 30
         * distrib_money : 5
         * file_path : null
         * merch_id : 1
         * merch_name : 武汉一高校
         * month_sale : 0
         * eval_score : 5
         * invoice_setting : 1
         * self_pick_setting : 1
         */

        private int distance;
        private String distrib_quota;
        private int service_time;
        private String distrib_money;
        private Object file_path;
        private String merch_id;
        private String merch_name;
        private int month_sale;
        private String eval_score;
        private int invoice_setting;
        private int self_pick_setting;

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public String getDistrib_quota() {
            return distrib_quota;
        }

        public void setDistrib_quota(String distrib_quota) {
            this.distrib_quota = distrib_quota;
        }

        public int getService_time() {
            return service_time;
        }

        public void setService_time(int service_time) {
            this.service_time = service_time;
        }

        public String getDistrib_money() {
            return distrib_money;
        }

        public void setDistrib_money(String distrib_money) {
            this.distrib_money = distrib_money;
        }

        public Object getFile_path() {
            return file_path;
        }

        public void setFile_path(Object file_path) {
            this.file_path = file_path;
        }

        public String getMerch_id() {
            return merch_id;
        }

        public void setMerch_id(String merch_id) {
            this.merch_id = merch_id;
        }

        public String getMerch_name() {
            return merch_name;
        }

        public void setMerch_name(String merch_name) {
            this.merch_name = merch_name;
        }

        public int getMonth_sale() {
            return month_sale;
        }

        public void setMonth_sale(int month_sale) {
            this.month_sale = month_sale;
        }

        public String getEval_score() {
            return eval_score;
        }

        public void setEval_score(String eval_score) {
            this.eval_score = eval_score;
        }

        public int getInvoice_setting() {
            return invoice_setting;
        }

        public void setInvoice_setting(int invoice_setting) {
            this.invoice_setting = invoice_setting;
        }

        public int getSelf_pick_setting() {
            return self_pick_setting;
        }

        public void setSelf_pick_setting(int self_pick_setting) {
            this.self_pick_setting = self_pick_setting;
        }
    }

    public static class ZongheBean {
        /**
         * name : 评分最高
         * state : 1
         */

        private String name;
        private int state;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}

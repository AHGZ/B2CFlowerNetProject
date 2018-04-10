package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2018/1/26.
 * by--
 */

public class TakeOutOrderGroupBean implements Serializable{
    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * title : dsf
         * imgs : 433,434,440,441
         * order_num : 656602
         * manager_name : 去你妹的
         * state : 3
         * price : 111.00
         * rebate : 11.00
         * group_id : 5
         * merch_id : 1
         * starttime : 1514736000
         * endtime : 1520784000
         * num : 1
         * eval_state : 0
         * rebate_amount : 11.00
         * order_amount : 111.00
         * file_path : img/o2o_good/2018/01-15/0de93b9f38189d1392ce965b02cd500e.jpg
         */

        private String title;
        private String imgs;
        private String order_num;
        private String manager_name;
        private String state;
        private String price;
        private String rebate;
        private int group_id;
        private int merch_id;
        private String starttime;
        private String endtime;
        private String num;
        private String eval_state;
        private String rebate_amount;
        private String order_amount;
        private String file_path;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public String getManager_name() {
            return manager_name;
        }

        public void setManager_name(String manager_name) {
            this.manager_name = manager_name;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getRebate() {
            return rebate;
        }

        public void setRebate(String rebate) {
            this.rebate = rebate;
        }

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public int getMerch_id() {
            return merch_id;
        }

        public void setMerch_id(int merch_id) {
            this.merch_id = merch_id;
        }

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

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getEval_state() {
            return eval_state;
        }

        public void setEval_state(String eval_state) {
            this.eval_state = eval_state;
        }

        public String getRebate_amount() {
            return rebate_amount;
        }

        public void setRebate_amount(String rebate_amount) {
            this.rebate_amount = rebate_amount;
        }

        public String getOrder_amount() {
            return order_amount;
        }

        public void setOrder_amount(String order_amount) {
            this.order_amount = order_amount;
        }

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }
    }

}

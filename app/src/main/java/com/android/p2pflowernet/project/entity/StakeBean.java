package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2017/11/27.
 * by--入股明细
 */

public class StakeBean implements Serializable{


    /**
     * count : 4
     * lists : [{"id":"32","partner_qual_num":"1","partner_qual_amount":"10000.00","created":"1514000337","policy_id":"0","record_id":"32","name":"追加股份","date":"2017-12-23"},{"id":"31","partner_qual_num":"5","partner_qual_amount":"50000.00","created":"1513999968","policy_id":"0","record_id":"31","name":"追加股份","date":"2017-12-23"},{"id":"30","partner_qual_num":"5","partner_qual_amount":"50000.00","created":"1513999731","policy_id":"0","record_id":"30","name":"追加股份","date":"2017-12-23"},{"id":"29","partner_qual_num":"5","partner_qual_amount":"50000.00","created":"1513999589","policy_id":"0","record_id":"29","name":"追加股份","date":"2017-12-23"},{"id":"28","partner_qual_num":"5","partner_qual_amount":"50000.00","created":"1513998542","policy_id":"0","record_id":"28","name":"追加股份","date":"2017-12-23"},{"id":"27","partner_qual_num":"1","partner_qual_amount":"10000.00","created":"1513998305","policy_id":"0","record_id":"27","name":"追加股份","date":"2017-12-23"},{"id":"26","partner_qual_num":"5","partner_qual_amount":"50000.00","created":"1513998218","policy_id":"0","record_id":"26","name":"追加股份","date":"2017-12-23"},{"id":"25","partner_qual_num":"5","partner_qual_amount":"50000.00","created":"1513998182","policy_id":"0","record_id":"25","name":"追加股份","date":"2017-12-23"},{"id":"24","partner_qual_num":"5","partner_qual_amount":"50000.00","created":"1513998002","policy_id":"0","record_id":"24","name":"追加股份","date":"2017-12-23"},{"id":"23","partner_qual_num":"1","partner_qual_amount":"10000.00","created":"1513997108","policy_id":"0","record_id":"23","name":"追加股份","date":"2017-12-23"}]
     */

    private int count;
    private List<ListsBean> lists;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public static class ListsBean implements Serializable{
        /**
         * id : 32
         * partner_qual_num : 1
         * partner_qual_amount : 10000.00
         * created : 1514000337
         * policy_id : 0
         * record_id : 32
         * name : 追加股份
         * date : 2017-12-23
         */

        private String id;
        private String partner_qual_num;
        private String partner_qual_amount;
        private String created;
        private String policy_id;
        private String record_id;//对应资质购买ID（17-11-24新增）
        private String name;
        private String date;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPartner_qual_num() {
            return partner_qual_num;
        }

        public void setPartner_qual_num(String partner_qual_num) {
            this.partner_qual_num = partner_qual_num;
        }

        public String getPartner_qual_amount() {
            return partner_qual_amount;
        }

        public void setPartner_qual_amount(String partner_qual_amount) {
            this.partner_qual_amount = partner_qual_amount;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getPolicy_id() {
            return policy_id;
        }

        public void setPolicy_id(String policy_id) {
            this.policy_id = policy_id;
        }

        public String getRecord_id() {
            return record_id;
        }

        public void setRecord_id(String record_id) {
            this.record_id = record_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}

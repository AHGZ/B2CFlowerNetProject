package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2018/1/16.
 * by--外卖订单
 */

public class TakeOutBean implements Serializable {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * order_id : 52
         * merch_id : 1
         * order_num : 29142802
         * pay_amount : 33.00
         * rebate_amount : 10.00
         * goods_count : 1
         * state : 1
         * eval_state : 0
         * refund_state : 0
         * created : 1516772088
         * manager_name : 去你妹的
         * file_path : null
         * countdown_time : 0
         * goods : [{"id":"577","user_id":"30","order_num":"29142802","goods_id":"33","spec_id":"0","goods_num":"1","goods_price":"30.00","rebate":"10.00","goods_name":"豆腐","goods_spec":"","spec_img":"577","user_type":"4","file_type":"3","file_path":"img/o2o_good/2018/01-17/10ea4ff0fc1302b1df8db8a384b8ab04.jpg","created":"1516186610"}]
         */

        private String distrib_id;
        private String distrib_mode;
        private String refund_id;
        private String order_id;
        private String merch_id;
        private String order_num;
        private String pay_amount;
        private String rebate_amount;
        private String goods_count;
        private String state;
        private String eval_state;
        private String refund_state;
        private String created;
        private String manager_name;
        private String file_path;
        private String countdown_time;
        private String pay_channel;
        private List<GoodsBean> goods;

        public String getDistrib_id() {
            return distrib_id;
        }

        public void setDistrib_id(String distrib_id) {
            this.distrib_id = distrib_id;
        }

        public String getDistrib_mode() {
            return distrib_mode;
        }

        public void setDistrib_mode(String distrib_mode) {
            this.distrib_mode = distrib_mode;
        }

        public String getRefund_id() {
            return refund_id;
        }

        public void setRefund_id(String refund_id) {
            this.refund_id = refund_id;
        }

        public String getPay_channel() {
            return pay_channel;
        }

        public void setPay_channel(String pay_channel) {
            this.pay_channel = pay_channel;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getMerch_id() {
            return merch_id;
        }

        public void setMerch_id(String merch_id) {
            this.merch_id = merch_id;
        }

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public String getPay_amount() {
            return pay_amount;
        }

        public void setPay_amount(String pay_amount) {
            this.pay_amount = pay_amount;
        }

        public String getRebate_amount() {
            return rebate_amount;
        }

        public void setRebate_amount(String rebate_amount) {
            this.rebate_amount = rebate_amount;
        }

        public String getGoods_count() {
            return goods_count;
        }

        public void setGoods_count(String goods_count) {
            this.goods_count = goods_count;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getEval_state() {
            return eval_state;
        }

        public void setEval_state(String eval_state) {
            this.eval_state = eval_state;
        }

        public String getRefund_state() {
            return refund_state;
        }

        public void setRefund_state(String refund_state) {
            this.refund_state = refund_state;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getManager_name() {
            return manager_name;
        }

        public void setManager_name(String manager_name) {
            this.manager_name = manager_name;
        }

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }

        public String getCountdown_time() {
            return countdown_time;
        }

        public void setCountdown_time(String countdown_time) {
            this.countdown_time = countdown_time;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean implements Serializable {
            /**
             * id : 577
             * user_id : 30
             * order_num : 29142802
             * goods_id : 33
             * spec_id : 0
             * goods_num : 1
             * goods_price : 30.00
             * rebate : 10.00
             * goods_name : 豆腐
             * goods_spec :
             * spec_img : 577
             * user_type : 4
             * file_type : 3
             * file_path : img/o2o_good/2018/01-17/10ea4ff0fc1302b1df8db8a384b8ab04.jpg
             * created : 1516186610
             */

            private String id;
            private String user_id;
            private String order_num;
            private String goods_id;
            private String spec_id;
            private String goods_num;
            private String goods_price;
            private String rebate;
            private String goods_name;
            private String goods_spec;
            private String spec_img;
            private String user_type;
            private String file_type;
            private String file_path;
            private String created;

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

            public String getOrder_num() {
                return order_num;
            }

            public void setOrder_num(String order_num) {
                this.order_num = order_num;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getSpec_id() {
                return spec_id;
            }

            public void setSpec_id(String spec_id) {
                this.spec_id = spec_id;
            }

            public String getGoods_num() {
                return goods_num;
            }

            public void setGoods_num(String goods_num) {
                this.goods_num = goods_num;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public String getRebate() {
                return rebate;
            }

            public void setRebate(String rebate) {
                this.rebate = rebate;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getGoods_spec() {
                return goods_spec;
            }

            public void setGoods_spec(String goods_spec) {
                this.goods_spec = goods_spec;
            }

            public String getSpec_img() {
                return spec_img;
            }

            public void setSpec_img(String spec_img) {
                this.spec_img = spec_img;
            }

            public String getUser_type() {
                return user_type;
            }

            public void setUser_type(String user_type) {
                this.user_type = user_type;
            }

            public String getFile_type() {
                return file_type;
            }

            public void setFile_type(String file_type) {
                this.file_type = file_type;
            }

            public String getFile_path() {
                return file_path;
            }

            public void setFile_path(String file_path) {
                this.file_path = file_path;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }
        }
    }
}

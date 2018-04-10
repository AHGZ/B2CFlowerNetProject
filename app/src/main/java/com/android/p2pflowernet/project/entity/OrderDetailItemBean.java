package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2017/12/13.
 * by--订单详情
 */

public class OrderDetailItemBean implements Serializable {


    /**
     * id : 1
     * order_num : 201712110300001
     * pay_amount : 1676.00
     * freight : 10.00
     * invoice_cost : 0.00
     * invoice_type : 0
     * customer_name : 刘先生
     * customer_tel : 13986765412
     * rebate_amount : 309.00
     * goods_total_num : 1
     * state : 0
     * created : 2017-12-11 10:36:01
     * manufac_name : test1
     * address : 北京北京市西城区大师傅萨达阿萨德群无大师傅奥术大师
     * deadline : 2017-12-11 11:06:01
     * lists : [{"id":"1","goods_id":"4","spec_id":"4","goods_price":"2282.00","rebate":"306.00","goods_num":"1","goods_name":"花花公子真皮皮衣男2017秋冬新品海宁绵羊皮真皮男士商务修身","goods_spec":"红色 160","is_noreason":"0","is_certified":"0","waybill_num":"10154611321123","label":0,"file_path":"business_auth_img/20171123/31f2fcb5acce00a9c2c305df4bd057fe.jpg"}]
     */

    private String id;
    private String order_num;
    private String pay_amount;
    private String freight;
    private String invoice_cost;
    private String invoice_type;
    private String customer_name;
    private String customer_tel;
    private String rebate_amount;
    private String goods_total_num;
    private String state;
    private String created;
    private String manufac_name;
    private String address;
    private String deadline;
    private List<ListsBean> lists;
    private String trade_no;
    private String pay_time;
    private String deliv_time;
    private String confirm_time;
    private String ended;
    private String count_down;

    public void setCount_down(String count_down) {
        this.count_down = count_down;
    }

    public String getEnded() {
        return ended;
    }

    public void setEnded(String ended) {
        this.ended = ended;
    }

    public void setConfirm_time(String confirm_time) {
        this.confirm_time = confirm_time;
    }

    public void setDeliv_time(String deliv_time) {
        this.deliv_time = deliv_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getInvoice_cost() {
        return invoice_cost;
    }

    public void setInvoice_cost(String invoice_cost) {
        this.invoice_cost = invoice_cost;
    }

    public String getInvoice_type() {
        return invoice_type;
    }

    public void setInvoice_type(String invoice_type) {
        this.invoice_type = invoice_type;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_tel() {
        return customer_tel;
    }

    public void setCustomer_tel(String customer_tel) {
        this.customer_tel = customer_tel;
    }

    public String getRebate_amount() {
        return rebate_amount;
    }

    public void setRebate_amount(String rebate_amount) {
        this.rebate_amount = rebate_amount;
    }

    public String getGoods_total_num() {
        return goods_total_num;
    }

    public void setGoods_total_num(String goods_total_num) {
        this.goods_total_num = goods_total_num;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getManufac_name() {
        return manufac_name;
    }

    public void setManufac_name(String manufac_name) {
        this.manufac_name = manufac_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public String getPay_time() {
        return pay_time;
    }

    public String getDeliv_time() {
        return deliv_time;
    }

    public String getConfirm_time() {
        return confirm_time;
    }

    public String getCount_down() {
        return count_down;
    }

    public static class ListsBean implements Serializable {
        /**
         * id : 1
         * goods_id : 4
         * spec_id : 4
         * goods_price : 2282.00
         * rebate : 306.00
         * goods_num : 1
         * goods_name : 花花公子真皮皮衣男2017秋冬新品海宁绵羊皮真皮男士商务修身
         * goods_spec : 红色 160
         * is_noreason : 0
         * is_certified : 0
         * waybill_num : 10154611321123
         * label : 0
         * file_path : business_auth_img/20171123/31f2fcb5acce00a9c2c305df4bd057fe.jpg
         */

        private String id;
        private String goods_id;
        private String spec_id;
        private double goods_price;
        private String rebate;
        private int goods_num;
        private String goods_name;
        private String goods_spec;
        private String is_noreason;
        private String is_certified;
        private String waybill_num;
        private int label;
        private String file_path;
        private String refund_state;
        private String eval_state;


        private String express_id;

        public String getRefund_state() {
            return refund_state;
        }

        public void setRefund_state(String refund_state) {
            this.refund_state = refund_state;
        }

        public String getEval_state() {
            return eval_state;
        }

        public void setEval_state(String eval_state) {
            this.eval_state = eval_state;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public double getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(double goods_price) {
            this.goods_price = goods_price;
        }

        public String getRebate() {
            return rebate;
        }

        public void setRebate(String rebate) {
            this.rebate = rebate;
        }

        public int getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(int goods_num) {
            this.goods_num = goods_num;
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

        public String getIs_noreason() {
            return is_noreason;
        }

        public void setIs_noreason(String is_noreason) {
            this.is_noreason = is_noreason;
        }

        public String getIs_certified() {
            return is_certified;
        }

        public void setIs_certified(String is_certified) {
            this.is_certified = is_certified;
        }

        public String getWaybill_num() {
            return waybill_num;
        }

        public void setWaybill_num(String waybill_num) {
            this.waybill_num = waybill_num;
        }

        public int getLabel() {
            return label;
        }

        public void setLabel(int label) {
            this.label = label;
        }

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }

        public String getExpress_id() {
            return express_id;
        }

        public void setExpress_id(String express_id) {
            this.express_id = express_id;
        }

        @Override
        public String toString() {
            return "ListsBean{" +
                    "id='" + id + '\'' +
                    ", goods_id='" + goods_id + '\'' +
                    ", spec_id='" + spec_id + '\'' +
                    ", goods_price=" + goods_price +
                    ", rebate='" + rebate + '\'' +
                    ", goods_num=" + goods_num +
                    ", goods_name='" + goods_name + '\'' +
                    ", goods_spec='" + goods_spec + '\'' +
                    ", is_noreason='" + is_noreason + '\'' +
                    ", is_certified='" + is_certified + '\'' +
                    ", waybill_num='" + waybill_num + '\'' +
                    ", label=" + label +
                    ", file_path='" + file_path + '\'' +
                    ", refund_state='" + refund_state + '\'' +
                    ", eval_state='" + eval_state + '\'' +
                    ", express_id='" + express_id + '\'' +
                    '}';
        }
    }
}

package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by heguozhong on 2018/1/24/024.
 * 订单详情
 */

public class OrderDetailsBean implements Serializable{


    /**
     * merch_id : 1
     * customer_name : 你密码
     * customer_tel : 13585280000
     * location : 北京-北京市-朝阳区
     * detail_address : 你一天
     * distrib_mode : 2
     * goods_price : 30.00
     * box_cost : 3.00
     * rider_name :
     * rider_phone :
     * booking_time : 14:04
     * distrib_cost : 0.00
     * pay_amount : 33.00
     * rebate_amount : 10.00
     * note :
     * state : 4
     * created : 2018-01-24 13:34:48
     * pay_time : 1970-01-01 08:00:00
     * deliv_time : 0
     * confirm_time : 1970-01-01 08:00:00
     * ended : 0
     * pay_channel : 0
     * eval_state : 1
     * refund_state : 0
     * distrib_name : 商家配送
     * rider_score : 0
     * rider_latitude : 0
     * rider_longitude : 0
     * mer_latitude : 39.9316009
     * mer_longitude : 116.6054497
     * logo_url : img/apply_identity/2018/01-22/59c2b5395865cfb93585ce3b519c55d4.jpg
     * merch_name : 武汉一高校
     * manager_tel : 18513028980
     * area_name : 北京北京市东城区
     * address : 你在哪里呢。我
     * last_out : 23:45
     * goods_lists : [{"goods_id":"33","spec_id":"0","goods_name":"豆腐","goods_price":"30.00","rebate":"10.00","goods_spec":"","file_path":"img/o2o_good/2018/01-17/10ea4ff0fc1302b1df8db8a384b8ab04.jpg","goods_num":"1"}]
     */

    private String id;
    private int is_invoice;
    private String invoice_type;
    private String title;
    private String tax_num;
    private String refund_amount;
    private String reason;
    private String refund_id;
    private String explain;
    private String refuse_reason;
    private String finished;
    private String re_state;
    private String merch_id;
    private String customer_name;
    private String customer_tel;
    private String location;
    private String detail_address;
    private int distrib_mode;
    private String goods_price;
    private String box_cost;
    private String rider_name;
    private String rider_phone;
    private String booking_time;
    private String distrib_cost;
    private String pay_amount;
    private String rebate_amount;
    private String note;
    private String state;
    private String created;
    private String pay_time;
    private int deliv_time;
    private String confirm_time;
    private String ended;
    private int pay_channel;
    private String eval_state;
    private String refund_state;
    private String distrib_name;
    private int rider_score;
    private int rider_latitude;
    private int rider_longitude;
    private double mer_latitude;
    private double mer_longitude;
    private String logo_url;
    private String merch_name;
    private String manager_tel;
    private String area_name;
    private String address;
    private String last_out;
    private List<GoodsListsBean> goods_lists;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public int getIs_invoice() {
        return is_invoice;
    }

    public void setIs_invoice(int is_invoice) {
        this.is_invoice = is_invoice;
    }

    public String getInvoice_type() {
        return invoice_type;
    }

    public void setInvoice_type(String invoice_type) {
        this.invoice_type = invoice_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTax_num() {
        return tax_num;
    }

    public void setTax_num(String tax_num) {
        this.tax_num = tax_num;
    }


    public String getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(String refund_amount) {
        this.refund_amount = refund_amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRefund_id() {
        return refund_id;
    }

    public void setRefund_id(String refund_id) {
        this.refund_id = refund_id;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getRefuse_reason() {
        return refuse_reason;
    }

    public void setRefuse_reason(String refuse_reason) {
        this.refuse_reason = refuse_reason;
    }

    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }

    public String getRe_state() {
        return re_state;
    }

    public void setRe_state(String re_state) {
        this.re_state = re_state;
    }

    public String getMerch_id() {
        return merch_id;
    }

    public void setMerch_id(String merch_id) {
        this.merch_id = merch_id;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetail_address() {
        return detail_address;
    }

    public void setDetail_address(String detail_address) {
        this.detail_address = detail_address;
    }

    public int getDistrib_mode() {
        return distrib_mode;
    }

    public void setDistrib_mode(int distrib_mode) {
        this.distrib_mode = distrib_mode;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getBox_cost() {
        return box_cost;
    }

    public void setBox_cost(String box_cost) {
        this.box_cost = box_cost;
    }

    public String getRider_name() {
        return rider_name;
    }

    public void setRider_name(String rider_name) {
        this.rider_name = rider_name;
    }

    public String getRider_phone() {
        return rider_phone;
    }

    public void setRider_phone(String rider_phone) {
        this.rider_phone = rider_phone;
    }

    public String getBooking_time() {
        return booking_time;
    }

    public void setBooking_time(String booking_time) {
        this.booking_time = booking_time;
    }

    public String getDistrib_cost() {
        return distrib_cost;
    }

    public void setDistrib_cost(String distrib_cost) {
        this.distrib_cost = distrib_cost;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public int getDeliv_time() {
        return deliv_time;
    }

    public void setDeliv_time(int deliv_time) {
        this.deliv_time = deliv_time;
    }

    public String getConfirm_time() {
        return confirm_time;
    }

    public void setConfirm_time(String confirm_time) {
        this.confirm_time = confirm_time;
    }

    public String getEnded() {
        return ended;
    }

    public void setEnded(String ended) {
        this.ended = ended;
    }

    public int getPay_channel() {
        return pay_channel;
    }

    public void setPay_channel(int pay_channel) {
        this.pay_channel = pay_channel;
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

    public String getDistrib_name() {
        return distrib_name;
    }

    public void setDistrib_name(String distrib_name) {
        this.distrib_name = distrib_name;
    }

    public int getRider_score() {
        return rider_score;
    }

    public void setRider_score(int rider_score) {
        this.rider_score = rider_score;
    }

    public int getRider_latitude() {
        return rider_latitude;
    }

    public void setRider_latitude(int rider_latitude) {
        this.rider_latitude = rider_latitude;
    }

    public int getRider_longitude() {
        return rider_longitude;
    }

    public void setRider_longitude(int rider_longitude) {
        this.rider_longitude = rider_longitude;
    }

    public double getMer_latitude() {
        return mer_latitude;
    }

    public void setMer_latitude(double mer_latitude) {
        this.mer_latitude = mer_latitude;
    }

    public double getMer_longitude() {
        return mer_longitude;
    }

    public void setMer_longitude(double mer_longitude) {
        this.mer_longitude = mer_longitude;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getMerch_name() {
        return merch_name;
    }

    public void setMerch_name(String merch_name) {
        this.merch_name = merch_name;
    }

    public String getManager_tel() {
        return manager_tel;
    }

    public void setManager_tel(String manager_tel) {
        this.manager_tel = manager_tel;
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

    public String getLast_out() {
        return last_out;
    }

    public void setLast_out(String last_out) {
        this.last_out = last_out;
    }

    public List<GoodsListsBean> getGoods_lists() {
        return goods_lists;
    }

    public void setGoods_lists(List<GoodsListsBean> goods_lists) {
        this.goods_lists = goods_lists;
    }

    public static class GoodsListsBean {
        /**
         * goods_id : 33
         * spec_id : 0
         * goods_name : 豆腐
         * goods_price : 30.00
         * rebate : 10.00
         * goods_spec :
         * file_path : img/o2o_good/2018/01-17/10ea4ff0fc1302b1df8db8a384b8ab04.jpg
         * goods_num : 1
         */

        private String goods_id;
        private String spec_id;
        private String goods_name;
        private String goods_price;
        private String rebate;
        private String goods_spec;
        private String file_path;
        private String goods_num;

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

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
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

        public String getGoods_spec() {
            return goods_spec;
        }

        public void setGoods_spec(String goods_spec) {
            this.goods_spec = goods_spec;
        }

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }
    }
}

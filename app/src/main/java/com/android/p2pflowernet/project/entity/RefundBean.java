package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author: zhangpeisen
 * created on: 2017/12/15 下午2:33
 * description: 退换货列表
 */
public class RefundBean implements Serializable {

    /**
     * id : 24
     * order_num : 124251603
     * manufac_id : 10088
     * og_id : 114
     * refund_state : 9
     * arbit_id : 0
     * express_name :
     * waybill_num :
     * manufac_name : test1
     * goods_name : 花花公子真皮皮衣男2017秋冬新品海宁绵羊皮真皮男士商务修身
     * goods_spec : 红色 160
     * img_path : business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg
     */

    private List<ListsBean> lists;

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public static class ListsBean implements Serializable{
        private String id;
        private String order_num;
        private String manufac_id;
        private String og_id;
        private String refund_state;
        private String arbit_id;
        private String express_name;
        private String waybill_num;
        private String manufac_name;
        private String goods_name;
        private String goods_spec;
        private List<String> img_path;

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

        public String getManufac_id() {
            return manufac_id;
        }

        public void setManufac_id(String manufac_id) {
            this.manufac_id = manufac_id;
        }

        public String getOg_id() {
            return og_id;
        }

        public void setOg_id(String og_id) {
            this.og_id = og_id;
        }

        public String getRefund_state() {
            return refund_state;
        }

        public void setRefund_state(String refund_state) {
            this.refund_state = refund_state;
        }

        public String getArbit_id() {
            return arbit_id;
        }

        public void setArbit_id(String arbit_id) {
            this.arbit_id = arbit_id;
        }

        public String getExpress_name() {
            return express_name;
        }

        public void setExpress_name(String express_name) {
            this.express_name = express_name;
        }

        public String getWaybill_num() {
            return waybill_num;
        }

        public void setWaybill_num(String waybill_num) {
            this.waybill_num = waybill_num;
        }

        public String getManufac_name() {
            return manufac_name;
        }

        public void setManufac_name(String manufac_name) {
            this.manufac_name = manufac_name;
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

        public List<String> getImg_path() {
            return img_path;
        }

        public void setImg_path(List<String> img_path) {
            this.img_path = img_path;
        }

        @Override
        public String toString() {
            return "ListsBean{" +
                    "id='" + id + '\'' +
                    ", order_num='" + order_num + '\'' +
                    ", manufac_id='" + manufac_id + '\'' +
                    ", og_id='" + og_id + '\'' +
                    ", refund_state='" + refund_state + '\'' +
                    ", arbit_id='" + arbit_id + '\'' +
                    ", express_name='" + express_name + '\'' +
                    ", waybill_num='" + waybill_num + '\'' +
                    ", manufac_name='" + manufac_name + '\'' +
                    ", goods_name='" + goods_name + '\'' +
                    ", goods_spec='" + goods_spec + '\'' +
                    ", img_path='" + img_path + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RefundBean{" +
                "lists=" + lists +
                '}';
    }
}

package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author: zhangpeisen
 * created on: 2017/12/12 下午5:47
 * description: 订单列表
 */
public class OrderListBean implements Serializable {

    /**
     * id : 1
     * order_num : 201712110300001
     * manufac_id : 10088
     * pay_amount : 1676.00
     * rebate_amount : 309.00
     * goods_total_num : 1
     * state : 0
     * manufac_name : test1
     * img_path : ["business_auth_img/20171123/31f2fcb5acce00a9c2c305df4bd057fe.jpg"]
     * goods_name : 花花公子真皮皮衣男2017秋冬新品海宁绵羊皮真皮男士商务修身
     * spec_name : 红色 160
     * label : 0
     */

    private List<ListsBean> lists;

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public static class ListsBean implements Serializable {
        private String id;
        private String order_num;
        private String manufac_id;
        private String pay_amount;
        private String rebate_amount;
        private String goods_total_num;
        private String state;
        private String manufac_name;
        private String goods_name;
        private String spec_name;
        private int label;
        private List<String> img_path;
        private String is_return;

        public String getIs_return() {
            return is_return;
        }

        public void setIs_return(String is_return) {
            this.is_return = is_return;
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

        public String getManufac_id() {
            return manufac_id;
        }

        public void setManufac_id(String manufac_id) {
            this.manufac_id = manufac_id;
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

        public String getSpec_name() {
            return spec_name;
        }

        public void setSpec_name(String spec_name) {
            this.spec_name = spec_name;
        }

        public int getLabel() {
            return label;
        }

        public void setLabel(int label) {
            this.label = label;
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
                    ", pay_amount='" + pay_amount + '\'' +
                    ", rebate_amount='" + rebate_amount + '\'' +
                    ", goods_total_num='" + goods_total_num + '\'' +
                    ", state='" + state + '\'' +
                    ", manufac_name='" + manufac_name + '\'' +
                    ", goods_name='" + goods_name + '\'' +
                    ", spec_name='" + spec_name + '\'' +
                    ", label=" + label +
                    ", img_path=" + img_path +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "OrderListBean{" +
                "lists=" + lists +
                '}';
    }
}

package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2017/12/14.
 * by--待评价
 */

public class WaitEvaluatedBean implements Serializable{


    private List<ListsBean> lists;

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public static class ListsBean {
        /**
         * id : 8
         * order_num : 201712110300004
         * spec_id : 6
         * goods_id : 4
         * goods_name : 花花公子真皮皮衣男2017秋冬新品海宁绵羊皮真皮男士商务修身
         * goods_spec : 红色 160
         * img_path : business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg
         * manufac_name : test1
         */

        private String id;
        private String order_num;
        private String spec_id;
        private String goods_id;
        private String goods_name;
        private String goods_spec;
        private String img_path;
        private String manufac_name;

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

        public String getSpec_id() {
            return spec_id;
        }

        public void setSpec_id(String spec_id) {
            this.spec_id = spec_id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
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

        public String getImg_path() {
            return img_path;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }

        public String getManufac_name() {
            return manufac_name;
        }

        public void setManufac_name(String manufac_name) {
            this.manufac_name = manufac_name;
        }
    }
}

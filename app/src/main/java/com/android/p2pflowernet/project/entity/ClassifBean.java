package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2017/12/2.
 * by--分类筛选
 */

public class ClassifBean implements Serializable {

    /**
     * goods : [{"id":"1","goods_name":"红豆（Hodo）男装","guige":"红色 160","sale_price":"588.00","huafan":"88.00","img":"business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg"},{"id":"3","goods_name":"小米男装","guige":"红色 160","sale_price":"340.00","huafan":"140.00","img":"business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg"},{"id":"4","goods_name":"花花公子真皮皮衣男2017秋冬新品海宁绵羊皮真皮男士商务修身","guige":"红色 160","sale_price":"2281.00","huafan":"615.00","img":"business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg"}]
     * cate_id : 1001
     */

    private String cate_id;
    /**
     * id : 1
     * goods_name : 红豆（Hodo）男装
     * guige : 红色 160
     * sale_price : 588.00
     * huafan : 88.00
     * img : business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg
     */

    private List<GoodsBean> goods;

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class GoodsBean implements Serializable {
        private String id;
        private String goods_name;
        private String guige;
        private String sale_price;
        private String huafan;
        private String img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGuige() {
            return guige;
        }

        public void setGuige(String guige) {
            this.guige = guige;
        }

        public String getSale_price() {
            return sale_price;
        }

        public void setSale_price(String sale_price) {
            this.sale_price = sale_price;
        }

        public String getHuafan() {
            return huafan;
        }

        public void setHuafan(String huafan) {
            this.huafan = huafan;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        @Override
        public String toString() {
            return "GoodsBean{" +
                    "id='" + id + '\'' +
                    ", goods_name='" + goods_name + '\'' +
                    ", guige='" + guige + '\'' +
                    ", sale_price='" + sale_price + '\'' +
                    ", huafan='" + huafan + '\'' +
                    ", img='" + img + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ClassifBean{" +
                "cate_id='" + cate_id + '\'' +
                ", goods=" + goods +
                '}';
    }
}

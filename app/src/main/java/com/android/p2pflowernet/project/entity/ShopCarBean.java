package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2017/12/7.
 * by--购物车
 */

public class ShopCarBean implements Serializable{
    /**
     * list : [{"manufac_id":"10088","manufac_name":"test1","file_path":"business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg","shop":[{"huafan":492,"sale_price":"2281.00","count":"10","spec_id":"3","goods_id":"4","file_path":"business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg","manufac_id":"10088","cart_goods_id":"15","goods_name":"花花公子真皮皮衣男2017秋冬新品海宁绵羊皮真皮男士商务修身","goods_spec":"红色 160","select":"0"},{"huafan":112,"sale_price":"340.00","count":"3","spec_id":"2","goods_id":"3","file_path":"business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg","manufac_id":"10088","cart_goods_id":"13","goods_name":"小米男装","goods_spec":"红色 160","select":"1"}],"select":0}]
     * select : 0
     */

    private String select;
    private List<ListBean> list;
    boolean selects= Boolean.getBoolean(select);
    private boolean isChoosed=selects;

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * manufac_id : 10088
         * manufac_name : test1
         * file_path : business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg
         * shop : [{"huafan":492,"sale_price":"2281.00","count":"10","spec_id":"3","goods_id":"4","file_path":"business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg","manufac_id":"10088","cart_goods_id":"15","goods_name":"花花公子真皮皮衣男2017秋冬新品海宁绵羊皮真皮男士商务修身","goods_spec":"红色 160","select":"0"},{"huafan":112,"sale_price":"340.00","count":"3","spec_id":"2","goods_id":"3","file_path":"business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg","manufac_id":"10088","cart_goods_id":"13","goods_name":"小米男装","goods_spec":"红色 160","select":"1"}]
         * select : 0
         */

        private String manufac_id;
        private String manufac_name;
        private String file_path;
        private String select;
        private List<ShopBean> shop;
        boolean selects= Boolean.getBoolean(select);
        private boolean isChoosed=selects;

        public boolean isChoosed() {
            return isChoosed;
        }

        public void setChoosed(boolean choosed) {
            isChoosed = choosed;
        }

        public String getManufac_id() {
            return manufac_id;
        }

        public void setManufac_id(String manufac_id) {
            this.manufac_id = manufac_id;
        }

        public String getManufac_name() {
            return manufac_name;
        }

        public void setManufac_name(String manufac_name) {
            this.manufac_name = manufac_name;
        }

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }

        public String getSelect() {
            return select;
        }

        public void setSelect(String select) {
            this.select = select;
        }

        public List<ShopBean> getShop() {
            return shop;
        }

        public void setShop(List<ShopBean> shop) {
            this.shop = shop;
        }

        public static class ShopBean implements Serializable{
            /**
             * huafan : 492
             * sale_price : 2281.00
             * count : 10
             * spec_id : 3
             * goods_id : 4
             * file_path : business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg
             * manufac_id : 10088
             * cart_goods_id : 15
             * goods_name : 花花公子真皮皮衣男2017秋冬新品海宁绵羊皮真皮男士商务修身
             * goods_spec : 红色 160
             * select : 0
             */

            private String huafan;
            private String sale_price;
            private int count;
            private String spec_id;
            private String goods_id;
            private String file_path;
            private String manufac_id;
            private String cart_goods_id;
            private String goods_name;
            private String goods_spec;
            private String select;
            private String is_sale;
            private int stock;
            boolean selects = Boolean.getBoolean(select);
            private boolean isChoosed=selects;

            public String getIs_sale() {
                return is_sale;
            }

            public void setIs_sale(String is_sale) {
                this.is_sale = is_sale;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            public boolean isChoosed() {
                return isChoosed;
            }

            public void setChoosed(boolean choosed) {
                isChoosed = choosed;
            }

            public String getHuafan() {
                return huafan;
            }

            public void setHuafan(String huafan) {
                this.huafan = huafan;
            }

            public String getSale_price() {
                return sale_price;
            }

            public void setSale_price(String sale_price) {
                this.sale_price = sale_price;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
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

            public String getFile_path() {
                return file_path;
            }

            public void setFile_path(String file_path) {
                this.file_path = file_path;
            }

            public String getManufac_id() {
                return manufac_id;
            }

            public void setManufac_id(String manufac_id) {
                this.manufac_id = manufac_id;
            }

            public String getCart_goods_id() {
                return cart_goods_id;
            }

            public void setCart_goods_id(String cart_goods_id) {
                this.cart_goods_id = cart_goods_id;
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

            public String getSelect() {
                return select;
            }

            public void setSelect(String select) {
                this.select = select;
            }
        }
    }
}

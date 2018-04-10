package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2018/2/3.
 * by--店铺内搜索的商品数据
 */

public class SearchStoreBean implements Serializable {
    /**
     * business_name : 店铺名称1
     * logo_url : img/apply_identity/2018/02-02/05f4a76a0b5ecc03c3adf2e1d6151c75.jpg
     * distrib_money : 7.00
     * distrib_quota : 25.00
     * lists : [{"id":13,"name":"蝴蝶结的家","sales_month":0,"goods_img":"img/o2o_good/2018/01-31/f577e4bc9dc989520d7668fe7415f489.jpg","price":58,"supply_price":10,"stock":100,"box_num":1,"box_price":1,"huafan":0,"unit":"盘","is_spec":0},{"id":19,"name":"蝴蝶结的家宝宝的","sales_month":0,"goods_img":"img/o2o_good/2018/01-31/e24d9244e194d0a028e6d689b72aa140.jpg","price":22,"supply_price":2,"stock":10,"box_num":1,"box_price":1,"huafan":0,"unit":"升","is_spec":0}]
     * is_close : 0
     * id : 1
     */

    private String business_name;
    private String logo_url;
    private String distrib_money;
    private String distrib_quota;
    private int is_close;
    private int id;
    private List<O2oIndexBean.ListsBean.GoodsListBean> lists;

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getDistrib_money() {
        return distrib_money;
    }

    public void setDistrib_money(String distrib_money) {
        this.distrib_money = distrib_money;
    }

    public String getDistrib_quota() {
        return distrib_quota;
    }

    public void setDistrib_quota(String distrib_quota) {
        this.distrib_quota = distrib_quota;
    }

    public int getIs_close() {
        return is_close;
    }

    public void setIs_close(int is_close) {
        this.is_close = is_close;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<O2oIndexBean.ListsBean.GoodsListBean> getLists() {
        return lists;
    }

    public void setLists(List<O2oIndexBean.ListsBean.GoodsListBean> lists) {
        this.lists = lists;
    }

    public static class ListsBean {
        /**
         * id : 13
         * name : 蝴蝶结的家
         * sales_month : 0
         * goods_img : img/o2o_good/2018/01-31/f577e4bc9dc989520d7668fe7415f489.jpg
         * price : 58
         * supply_price : 10
         * stock : 100
         * box_num : 1
         * box_price : 1
         * huafan : 0
         * unit : 盘
         * is_spec : 0
         */

        private int id;
        private String name;
        private int sales_month;
        private String goods_img;
        private int price;
        private int supply_price;
        private int stock;
        private int box_num;
        private int box_price;
        private int huafan;
        private String unit;
        private int is_spec;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSales_month() {
            return sales_month;
        }

        public void setSales_month(int sales_month) {
            this.sales_month = sales_month;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getSupply_price() {
            return supply_price;
        }

        public void setSupply_price(int supply_price) {
            this.supply_price = supply_price;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public int getBox_num() {
            return box_num;
        }

        public void setBox_num(int box_num) {
            this.box_num = box_num;
        }

        public int getBox_price() {
            return box_price;
        }

        public void setBox_price(int box_price) {
            this.box_price = box_price;
        }

        public int getHuafan() {
            return huafan;
        }

        public void setHuafan(int huafan) {
            this.huafan = huafan;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getIs_spec() {
            return is_spec;
        }

        public void setIs_spec(int is_spec) {
            this.is_spec = is_spec;
        }
    }


//    private List<O2oIndexBean.ListsBean.GoodsListBean> list;
//    private String business_name;
//    private String logo_url;
//    private String distrib_money;
//    private String distrib_quota;
//    private String is_close;
//    private String id;
//
//
//
//
//    public String getIs_close() {
//        return is_close;
//    }
//
//    public void setIs_close(String is_close) {
//        this.is_close = is_close;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getBusiness_name() {
//        return business_name;
//    }
//
//    public void setBusiness_name(String business_name) {
//        this.business_name = business_name;
//    }
//
//    public String getLogo_url() {
//        return logo_url;
//    }
//
//    public void setLogo_url(String logo_url) {
//        this.logo_url = logo_url;
//    }
//
//    public String getDistrib_money() {
//        return distrib_money;
//    }
//
//    public void setDistrib_money(String distrib_money) {
//        this.distrib_money = distrib_money;
//    }
//
//    public String getDistrib_quota() {
//        return distrib_quota;
//    }
//
//    public void setDistrib_quota(String distrib_quota) {
//        this.distrib_quota = distrib_quota;
//    }
//
//    public List<O2oIndexBean.ListsBean.GoodsListBean> getList() {
//        return list;
//    }
//
//    public void setList(List<O2oIndexBean.ListsBean.GoodsListBean> list) {
//        this.list = list;
//    }



}

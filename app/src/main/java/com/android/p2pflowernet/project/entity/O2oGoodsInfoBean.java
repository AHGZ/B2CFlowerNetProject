package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2018/1/25.
 * by--o2o商品明细
 */

public class O2oGoodsInfoBean implements Serializable{

    /**
     * name : 豆腐
     * goods_img : img/o2o_good/2018/01-17/10ea4ff0fc1302b1df8db8a384b8ab04.jpg
     * stock : 1000
     * box_num : 1
     * box_price : 3.00
     * price : 30.00
     * supply_price : 20.00
     * huafan : 10.00
     * lists : [{"score":"3","content":"dsadasdsadas","is_anon":"1","created":"2018-01-09 18:51:23","username":"匿名用户","file_path":""},{"score":"3","content":"dsadasdsadas","is_anon":"1","created":"2018-01-09 18:51:23","username":"匿名用户","file_path":""}]
     */

    private String name;
    private String goods_img;
    private String stock;
    private String box_num;
    private String box_price;
    private String price;
    private String supply_price;
    private String huafan;
    private List<ListsBean> lists;
    private String sales_month;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSales_month() {
        return sales_month;
    }

    public void setSales_month(String sales_month) {
        this.sales_month = sales_month;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getBox_num() {
        return box_num;
    }

    public void setBox_num(String box_num) {
        this.box_num = box_num;
    }

    public String getBox_price() {
        return box_price;
    }

    public void setBox_price(String box_price) {
        this.box_price = box_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSupply_price() {
        return supply_price;
    }

    public void setSupply_price(String supply_price) {
        this.supply_price = supply_price;
    }

    public String getHuafan() {
        return huafan;
    }

    public void setHuafan(String huafan) {
        this.huafan = huafan;
    }

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public String getSales_Mouth() {
        return sales_month;
    }

    public static class ListsBean {
        /**
         * score : 3
         * content : dsadasdsadas
         * is_anon : 1
         * created : 2018-01-09 18:51:23
         * username : 匿名用户
         * file_path :
         */

        private String score;
        private String content;
        private String is_anon;
        private String created;
        private String username;
        private String file_path;

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIs_anon() {
            return is_anon;
        }

        public void setIs_anon(String is_anon) {
            this.is_anon = is_anon;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }
    }
}

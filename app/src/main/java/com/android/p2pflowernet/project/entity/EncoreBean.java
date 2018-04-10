package com.android.p2pflowernet.project.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/25.
 */

public class EncoreBean {

    private List<ListsBean> lists;

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public static class ListsBean {
        /**
         * id : 12
         * spec : 15
         * num : 2
         * name : 宫保鸡丁12
         * price : 20.00
         * supply_price : 18.00
         * stock : 100
         * box_num : 20
         * box_price : 0.50
         * is_exis : 1
         */

        private int id;
        private int spec;
        private int num;
        private String name;
        private String price;
        private String supply_price;
        private int stock;
        private int box_num;
        private String box_price;
        private int is_exis;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSpec() {
            return spec;
        }

        public void setSpec(int spec) {
            this.spec = spec;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getBox_price() {
            return box_price;
        }

        public void setBox_price(String box_price) {
            this.box_price = box_price;
        }

        public int getIs_exis() {
            return is_exis;
        }

        public void setIs_exis(int is_exis) {
            this.is_exis = is_exis;
        }
    }
}

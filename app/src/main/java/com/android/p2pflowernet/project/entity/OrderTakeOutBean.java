package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2018/1/5.
 * by--外卖订单
 */

public class OrderTakeOutBean implements Serializable {

    private String name;
    private String state;
    private List<ListsBean> list;
    private String killtime;
    private String huafan;
    private String saleprice;
    private String filepath;
    private String num;

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<ListsBean> getList() {
        return list;
    }

    public void setList(List<ListsBean> list) {
        this.list = list;
    }

    public String getKilltime() {
        return killtime;
    }

    public void setKilltime(String killtime) {
        this.killtime = killtime;
    }

    public String getHuafan() {
        return huafan;
    }

    public void setHuafan(String huafan) {
        this.huafan = huafan;
    }

    public String getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(String saleprice) {
        this.saleprice = saleprice;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public CharSequence getNum() {
        return num;
    }

    public static class ListsBean implements Serializable {
        private String name;
        private String price;
        private String num;
        private String huafan;
        private String saleprice;

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

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getHuafan() {
            return huafan;
        }

        public void setHuafan(String huafan) {
            this.huafan = huafan;
        }

        public String getSaleprice() {
            return saleprice;
        }

        public void setSaleprice(String saleprice) {
            this.saleprice = saleprice;
        }


        @Override
        public String toString() {
            return "ListsBean{" +
                    "name='" + name + '\'' +
                    ", price='" + price + '\'' +
                    ", num='" + num + '\'' +
                    ", huafan='" + huafan + '\'' +
                    ", saleprice='" + saleprice + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "OrderTakeOutBean{" +
                "name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", list=" + list +
                ", killtime='" + killtime + '\'' +
                ", huafan='" + huafan + '\'' +
                ", saleprice='" + saleprice + '\'' +
                ", filepath='" + filepath + '\'' +
                '}';
    }
}

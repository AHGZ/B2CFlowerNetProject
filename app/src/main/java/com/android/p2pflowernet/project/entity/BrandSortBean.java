package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author: zhangpeisen
 * created on: 2017/12/12 下午3:02
 * description: 品牌筛选实体类
 */
public class BrandSortBean implements Serializable {


    private List<ListsBeanX> lists;

    public List<ListsBeanX> getLists() {
        return lists;
    }

    public void setLists(List<ListsBeanX> lists) {
        this.lists = lists;
    }

    public static class ListsBeanX implements Serializable{
        /**
         * name : 品牌
         * en_name : brand
         * lists : [{"cate_id":"1001","brand_id":"34","name":"EE1","first_char":"E"},{"cate_id":"1001","brand_id":"17","name":"R111","first_char":"R"},{"cate_id":"1001","brand_id":"19","name":"R111111dfds","first_char":"R"},{"cate_id":"1001","brand_id":"20","name":"YYY","first_char":"R"},{"cate_id":"1001","brand_id":"18","name":"R111111","first_char":"R"},{"cate_id":"1001","brand_id":"21","name":"YYY1","first_char":"R"},{"cate_id":"1001","brand_id":"2","name":"121","first_char":"T"},{"cate_id":"1001","brand_id":"3","name":"111","first_char":"T"}]
         */

        private String name;
        private String en_name;
        private List<ListsBean> lists;
        //状态是否打开
        private boolean isoPen = true;
        private String showStr = "";

        public String getShowStr() {
            return showStr;
        }

        public void setShowStr(String showStr) {
            this.showStr = showStr;
        }

        public boolean isoPen() {
            return isoPen;
        }

        public void setIsoPen(boolean isoPen) {
            this.isoPen = isoPen;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEn_name() {
            return en_name;
        }

        public void setEn_name(String en_name) {
            this.en_name = en_name;
        }

        public List<ListsBean> getLists() {
            return lists;
        }

        public void setLists(List<ListsBean> lists) {
            this.lists = lists;
        }

        public static class ListsBean implements Serializable{
            /**
             * cate_id : 1001
             * brand_id : 34
             * name : EE1
             * first_char : E
             */

            private String cate_id;
            private String brand_id;
            private String name;
            private String first_char;
            private boolean chick;//是否选中

            public boolean isChick() {
                return chick;
            }
            public String getCate_id() {
                return cate_id;
            }

            public void setCate_id(String cate_id) {
                this.cate_id = cate_id;
            }

            public String getBrand_id() {
                return brand_id;
            }

            public void setBrand_id(String brand_id) {
                this.brand_id = brand_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFirst_char() {
                return first_char;
            }

            public void setFirst_char(String first_char) {
                this.first_char = first_char;
            }

            public void setChick(boolean chick) {
                this.chick = chick;
            }

            @Override
            public String toString() {
                return "ListsBean{" +
                        "cate_id='" + cate_id + '\'' +
                        ", brand_id='" + brand_id + '\'' +
                        ", name='" + name + '\'' +
                        ", first_char='" + first_char + '\'' +
                        ", chick=" + chick +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "ListsBeanX{" +
                    "name='" + name + '\'' +
                    ", en_name='" + en_name + '\'' +
                    ", lists=" + lists +
                    ", isoPen=" + isoPen +
                    ", showStr='" + showStr + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BrandSortBean{" +
                "lists=" + lists +
                '}';
    }
}

package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author: zhangpeisen
 * created on: 2017/12/14 下午4:36
 * description:
 */
public class AllSortBean implements Serializable {

    /**
     * cate_id : 1001
     * brand_id : 34
     * name : EE1
     * first_char : E
     */

    private List<ListsBean> lists;

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public static class ListsBean implements Serializable {
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
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AllSortBean{" +
                "lists=" + lists +
                '}';
    }
}

package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author: zhangpeisen
 * created on: 2017/12/8 下午6:03
 * description: 产品参数
 */
public class ProductParamBean implements Serializable {

    /**
     * id : 2
     * name : 人群
     * val : 青年
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
        private String name;
        private String val;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "ListsBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", val='" + val + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ProductParamBean{" +
                "lists=" + lists +
                '}';
    }
}

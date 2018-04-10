package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author: zhangpeisen
 * created on: 2017/12/5 上午9:59
 * description: 商品一级分类列表
 */
public class BrandClassBean implements Serializable {

    /**
     * id : 1001
     * name : 衣服
     */
    // 分类菜单(左)
    private List<FlBean> fl;
    /**
     * id : 1003
     * name : 男士大衣
     */
    // 内容(右)
    private List<RecommendBean> Recommend;

    public List<FlBean> getFl() {
        return fl;
    }

    public void setFl(List<FlBean> fl) {
        this.fl = fl;
    }

    public List<RecommendBean> getRecommend() {
        return Recommend;
    }

    public void setRecommend(List<RecommendBean> Recommend) {
        this.Recommend = Recommend;
    }

    public static class FlBean implements Serializable {
        private String id;
        private String name;
        private boolean choose;

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

        public void setChoose(boolean choose) {
            this.choose = choose;
        }

        public boolean isChoose() {
            return choose;
        }

        @Override
        public String toString() {
            return "FlBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }


    }

    public static class RecommendBean implements Serializable {

        /**
         * id : 1003
         * name : 男士大衣
         * file_path : business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg
         */

        private String id;
        private String name;
        private String file_path;

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

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }

        @Override
        public String toString() {
            return "RecommendBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", file_path='" + file_path + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BrandClassBean{" +
                "fl=" + fl +
                ", Recommend=" + Recommend +
                '}';
    }
}

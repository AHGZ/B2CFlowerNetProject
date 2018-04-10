package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author: zhangpeisen
 * created on: 2017/12/5 下午1:47
 * description: 商品二级分类
 */
public class BrandScendBean implements Serializable {

    /**
     * id : 1002
     * name : 男装
     * file_path : business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg
     * three : [{"id":"1003","name":"男士大衣","file_path":"business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg"},{"id":"1004","name":"男士西服","file_path":"business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg"}]
     */

    private List<FlBean> fl;

    public List<FlBean> getFl() {
        return fl;
    }

    public void setFl(List<FlBean> fl) {
        this.fl = fl;
    }

    public static class FlBean implements Serializable{
        private String id;
        private String name;
        private String file_path;
        /**
         * id : 1003
         * name : 男士大衣
         * file_path : business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg
         */

        private List<ThreeBean> three;

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

        public List<ThreeBean> getThree() {
            return three;
        }

        public void setThree(List<ThreeBean> three) {
            this.three = three;
        }

        public static class ThreeBean implements Serializable{
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
                return "ThreeBean{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", file_path='" + file_path + '\'' +
                        '}';
            }
        }
        @Override
        public String toString() {
            return "FlBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", file_path='" + file_path + '\'' +
                    ", three=" + three +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BrandScendBean{" +
                "fl=" + fl +
                '}';
    }
}

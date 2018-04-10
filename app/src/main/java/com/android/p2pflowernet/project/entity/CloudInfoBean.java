package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2017/11/28.
 * by--云工详细信息
 */

public class CloudInfoBean implements Serializable{


    private List<StBean> st;

    public List<StBean> getSt() {
        return st;
    }

    public void setSt(List<StBean> st) {
        this.st = st;
    }

    public static class StBean {
        /**
         * id : 7
         * realname :
         * sex : 1
         * id_number : 130503198805160071
         * imgs : [{"img_id":"85","img_type":"1","file_path":"business_auth_img/20171128/a103beccdb047b8bff703069550014a7.png"},{"img_id":"86","img_type":"2","file_path":"business_auth_img/20171128/6882b06337af729661c46795bf827fea.png"}]
         */

        private String id;
        private String realname;
        private String sex;
        private String id_number;
        private List<ImgsBean> imgs;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getId_number() {
            return id_number;
        }

        public void setId_number(String id_number) {
            this.id_number = id_number;
        }

        public List<ImgsBean> getImgs() {
            return imgs;
        }

        public void setImgs(List<ImgsBean> imgs) {
            this.imgs = imgs;
        }

        public static class ImgsBean {
            /**
             * img_id : 85
             * img_type : 1
             * file_path : business_auth_img/20171128/a103beccdb047b8bff703069550014a7.png
             */

            private String img_id;
            private String img_type;
            private String file_path;

            public String getImg_id() {
                return img_id;
            }

            public void setImg_id(String img_id) {
                this.img_id = img_id;
            }

            public String getImg_type() {
                return img_type;
            }

            public void setImg_type(String img_type) {
                this.img_type = img_type;
            }

            public String getFile_path() {
                return file_path;
            }

            public void setFile_path(String file_path) {
                this.file_path = file_path;
            }
        }
    }
}

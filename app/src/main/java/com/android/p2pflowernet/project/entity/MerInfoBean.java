package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2017/12/4.
 * by--商家信息
 */

public class MerInfoBean implements Serializable {


    private List<AplistBean> aplist;

    public List<AplistBean> getAplist() {
        return aplist;
    }

    public void setAplist(List<AplistBean> aplist) {
        this.aplist = aplist;
    }

    public static class AplistBean implements Serializable {
        /**
         * id : 1
         * user_id : 9
         * region : 110114
         * realname : 张怡
         * sex : 1
         * id_number : 130503198805160711
         * is_legal : 1
         * shop_name : 名字
         * license_name : 定心
         * uniform_social_credit_code : 318494
         * customer_tel : 3464949
         * shop_province_id : 110000
         * shop_city_id : 110100
         * shop_district_id : 110101
         * area_name : 北京-北京市-东城区
         * shop_detail_address : MiGo心
         * type : 1
         * state : 10
         * admin_id : 0
         * updated : 1512364784
         * created : 1512364784
         * imgs : [{"img_id":"1","img_type":"6","file_path":"business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg"},{"img_id":"2","img_type":"7","file_path":"business_auth_img/20171123/0c82538b7641c36fa94ad2434d15f2db.jpg"},{"img_id":"3","img_type":"8","file_path":"business_auth_img/20171123/a83dd456d520aa3ac47ac0bff99f6b7e.jpg"},{"img_id":"4","img_type":"10","file_path":"business_auth_img/20171123/31f2fcb5acce00a9c2c305df4bd057fe.jpg"},{"img_id":"5","img_type":"11","file_path":"business_auth_img/20171123/5eae8db5b9563d3bd2bbfa9e025dfd13.jpg"},{"img_id":"6","img_type":"12","file_path":"business_auth_img/20171123/8da76b258a7aeb7f534c9bbb87726786.jpg"},{"img_id":"7","img_type":"9","file_path":"business_auth_img/20171123/2ba5a515b07911480b566990650457ea.jpg"},{"img_id":"8","img_type":"9","file_path":"business_auth_img/20171123/b5ec344648ceb024eed4628c78ddc16f.jpg"},{"img_id":"152","img_type":"6","file_path":"/storage/emulated/0/DCIM/camera/IMG_20171127_160338.jpg"},{"img_id":"153","img_type":"7","file_path":"/storage/emulated/0/DCIM/camera/IMG_20171127_160338.jpg"},{"img_id":"154","img_type":"8","file_path":"/storage/emulated/0/DCIM/camera/IMG_20171127_160338.jpg"},{"img_id":"155","img_type":"10","file_path":"/storage/emulated/0/DCIM/camera/IMG_20171127_160338.jpg"},{"img_id":"156","img_type":"11","file_path":"/storage/emulated/0/DCIM/camera/IMG_20171127_160338.jpg"},{"img_id":"157","img_type":"12","file_path":"/storage/emulated/0/DCIM/camera/IMG_20171127_160338.jpg"},{"img_id":"158","img_type":"9","file_path":"/storage/emulated/0/DCIM/camera/IMG_20171127_160338.jpg"}]
         */

        private String id;
        private String user_id;
        private String region;
        private String realname;
        private String sex;
        private String id_number;
        private int is_legal;
        private String shop_name;
        private String license_name;
        private String uniform_social_credit_code;
        private String customer_tel;
        private int shop_province_id;
        private int shop_city_id;
        private int shop_district_id;
        private String area_name;
        private String shop_detail_address;
        private String type;
        private String state;
        private String admin_id;
        private String updated;
        private String created;
        private int first_cate_id;
        private int second_cate_id;
        private int third_cate_id;
        private String merch_type;
        private String legal_name;
        private String legal_idnum;
        private String manager_tel;

        public String getLegal_name() {
            return legal_name;
        }

        public void setLegal_name(String legal_name) {
            this.legal_name = legal_name;
        }

        public String getLegal_idnum() {
            return legal_idnum;
        }

        public void setLegal_idnum(String legal_idnum) {
            this.legal_idnum = legal_idnum;
        }

        public int getFirst_cate_id() {
            return first_cate_id;
        }

        public void setFirst_cate_id(int first_cate_id) {
            this.first_cate_id = first_cate_id;
        }

        public int getSecond_cate_id() {
            return second_cate_id;
        }

        public void setSecond_cate_id(int second_cate_id) {
            this.second_cate_id = second_cate_id;
        }

        public int getThird_cate_id() {
            return third_cate_id;
        }

        public void setThird_cate_id(int third_cate_id) {
            this.third_cate_id = third_cate_id;
        }

        public String getMerch_type() {
            return merch_type;
        }

        public void setMerch_type(String merch_type) {
            this.merch_type = merch_type;
        }

        private List<ImgsBean> imgs;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
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

        public int getIs_legal() {
            return is_legal;
        }

        public void setIs_legal(int is_legal) {
            this.is_legal = is_legal;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getLicense_name() {
            return license_name;
        }

        public void setLicense_name(String license_name) {
            this.license_name = license_name;
        }

        public String getUniform_social_credit_code() {
            return uniform_social_credit_code;
        }

        public void setUniform_social_credit_code(String uniform_social_credit_code) {
            this.uniform_social_credit_code = uniform_social_credit_code;
        }

        public String getCustomer_tel() {
            return customer_tel;
        }

        public void setCustomer_tel(String customer_tel) {
            this.customer_tel = customer_tel;
        }

        public int getShop_province_id() {
            return shop_province_id;
        }

        public void setShop_province_id(int shop_province_id) {
            this.shop_province_id = shop_province_id;
        }

        public int getShop_city_id() {
            return shop_city_id;
        }

        public void setShop_city_id(int shop_city_id) {
            this.shop_city_id = shop_city_id;
        }

        public int getShop_district_id() {
            return shop_district_id;
        }

        public void setShop_district_id(int shop_district_id) {
            this.shop_district_id = shop_district_id;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public String getShop_detail_address() {
            return shop_detail_address;
        }

        public void setShop_detail_address(String shop_detail_address) {
            this.shop_detail_address = shop_detail_address;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getAdmin_id() {
            return admin_id;
        }

        public void setAdmin_id(String admin_id) {
            this.admin_id = admin_id;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public List<ImgsBean> getImgs() {
            return imgs;
        }

        public void setImgs(List<ImgsBean> imgs) {
            this.imgs = imgs;
        }

        public String manager_tel() {
            return manager_tel;
        }

        public void setManager_tel(String manager_tel) {
            this.manager_tel = manager_tel;
        }

        public static class ImgsBean {
            /**
             * img_id : 1
             * img_type : 6
             * file_path : business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg
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

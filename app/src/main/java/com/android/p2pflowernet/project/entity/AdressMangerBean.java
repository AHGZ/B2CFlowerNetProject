package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2017/11/23.
 * by--地址管理
 */

public class AdressMangerBean implements Serializable{


    private List<UalBean> ual;

    public List<UalBean> getUal() {
        return ual;
    }

    public void setUal(List<UalBean> ual) {
        this.ual = ual;
    }

    public static class UalBean {
        /**
         * id : 6
         * name : 张二狗
         * telephone : 18210672821
         * province_id : 110000
         * city_id : 110100
         * district_id : 110114
         * location : 110000110100110114甜筒
         * address : 甜筒
         * is_default : 0
         */

        private String id;
        private String name;
        private String telephone;
        private String province_id;
        private String city_id;
        private String district_id;
        private String location;
        private String address;
        private String is_default;

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

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getProvince_id() {
            return province_id;
        }

        public void setProvince_id(String province_id) {
            this.province_id = province_id;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getDistrict_id() {
            return district_id;
        }

        public void setDistrict_id(String district_id) {
            this.district_id = district_id;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }
    }
}

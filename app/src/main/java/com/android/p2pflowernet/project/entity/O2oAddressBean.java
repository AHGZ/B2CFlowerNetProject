package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author: zhangpeisen
 * created on: 2018/1/16 下午4:06
 * description:
 */
public class O2oAddressBean implements Serializable{

    /**
     * id : 8
     * name : 电话
     * telephone : 15201260959
     * location : 龙湖长楹天街
     * address : 902
     * longitude : 116.5995042
     * latitude : 39.9241351
     * is_beyond : 0
     */

    private List<ListsBean> lists;

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public static class ListsBean implements Serializable{
        private String id;
        private String name;
        private String telephone;
        private String location;
        private String address;
        private String longitude;
        private String latitude;
        private String site_name;//商圈地址
        private int is_beyond;

        public String getSite_name() {
            return site_name;
        }

        public void setSite_name(String site_name) {
            this.site_name = site_name;
        }

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

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public int getIs_beyond() {
            return is_beyond;
        }

        public void setIs_beyond(int is_beyond) {
            this.is_beyond = is_beyond;
        }

        @Override
        public String toString() {
            return "ListsBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", telephone='" + telephone + '\'' +
                    ", location='" + location + '\'' +
                    ", address='" + address + '\'' +
                    ", longitude='" + longitude + '\'' +
                    ", latitude='" + latitude + '\'' +
                    ", is_beyond=" + is_beyond +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "O2oAddressBean{" +
                "lists=" + lists +
                '}';
    }
}

package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/27.
 */

public class GroupHomeBean implements Serializable{

    private List<ListBean> list;//团购商品列表
    private List<CategoryBean> Category;//团购分类
    private List<CateAllBean> cateAll;//全部分类
    private List<DistrictBean> district;//商家

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<CategoryBean> getCategory() {
        return Category;
    }

    public void setCategory(List<CategoryBean> Category) {
        this.Category = Category;
    }

    public List<CateAllBean> getCateAll() {
        return cateAll;
    }

    public void setCateAll(List<CateAllBean> cateAll) {
        this.cateAll = cateAll;
    }

    public List<DistrictBean> getDistrict() {
        return district;
    }

    public void setDistrict(List<DistrictBean> district) {
        this.district = district;
    }

    public static class ListBean implements Serializable{
        /**
         * id : 9
         * merchant_id : 1
         * title : dsf
         * sold_num : 0
         * market_price : 123.00
         * price : 111.00
         * supply_price : 100.00
         * huafan : 23
         * distance : 978
         * imgs : 540,541
         * consume_avg : 12.00
         * is_new : 0
         * merch_name : 武汉一高校
         */

        private String id;
        private String merchant_id;
        private String title;
        private String sold_num;
        private String market_price;
        private String price;
        private String huafan;
        private int distance;
        private String imgs;
        private String consume_avg;
        private String is_new;
        private String merch_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(String merchant_id) {
            this.merchant_id = merchant_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSold_num() {
            return sold_num;
        }

        public void setSold_num(String sold_num) {
            this.sold_num = sold_num;
        }

        public String getMarket_price() {
            return market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getHuafan() {
            return huafan;
        }

        public void setHuafan(String huafan) {
            this.huafan = huafan;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public String getConsume_avg() {
            return consume_avg;
        }

        public void setConsume_avg(String consume_avg) {
            this.consume_avg = consume_avg;
        }

        public String getIs_new() {
            return is_new;
        }

        public void setIs_new(String is_new) {
            this.is_new = is_new;
        }

        public String getMerch_name() {
            return merch_name;
        }

        public void setMerch_name(String merch_name) {
            this.merch_name = merch_name;
        }
    }

    public static class CategoryBean implements Serializable{
        /**
         * cate_id : 100101
         * name : 快餐小吃
         * file_path : null
         */

        private String cate_id;
        private String name;
        private Object file_path;

        public String getCate_id() {
            return cate_id;
        }

        public void setCate_id(String cate_id) {
            this.cate_id = cate_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getFile_path() {
            return file_path;
        }

        public void setFile_path(Object file_path) {
            this.file_path = file_path;
        }
    }

    public static class CateAllBean implements Serializable{
        /**
         * cate_id : 0
         * name : 全部
         * file_path :
         */

        private String cate_id;
        private String name;
        private String file_path;

        public String getCate_id() {
            return cate_id;
        }

        public void setCate_id(String cate_id) {
            this.cate_id = cate_id;
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
    }

    public static class DistrictBean implements Serializable{
        /**
         * id : 0
         * region_name : 全部
         * parent_id :
         * region_type : 2
         */

        private String id;
        private String region_name;
        private String parent_id;
        private int region_type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRegion_name() {
            return region_name;
        }

        public void setRegion_name(String region_name) {
            this.region_name = region_name;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public int getRegion_type() {
            return region_type;
        }

        public void setRegion_type(int region_type) {
            this.region_type = region_type;
        }
    }
}

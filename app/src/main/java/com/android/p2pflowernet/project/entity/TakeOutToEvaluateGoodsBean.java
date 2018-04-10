package com.android.p2pflowernet.project.entity;

import java.util.List;

/**
 * Created by zhangkun on 2018/1/22.
 */

public class TakeOutToEvaluateGoodsBean {


    /**
     * distrib_mode : 1
     * confirm_time : 1970-01-01 08:00:00
     * merch_name : 武汉一高校
     * logo_url : img/o2o_good/2018/01-03/d74038ae515a50e5a3a88d179f9041da.jpg
     * is_anon : 1
     * goods_list : [{"goods_id":12,"goods_name":"宫保鸡丁12","goods_spec":"甜的"},{"goods_id":12,"goods_name":"宫保鸡丁12","goods_spec":"辣的"},{"goods_id":21,"goods_name":"急死","goods_spec":"甜的"}]
     */

    private int distrib_mode;
    private int district_id;//配送平台ID
    private String distrib_name;//配送名称
    private String distrib_logo;//配送Logo
    private String confirm_time;
    private String merch_name;
    private String logo_url;
    private int is_anon;
    private List<GoodsListBean> goods_list;

    public int getDistrib_mode() {
        return distrib_mode;
    }

    public void setDistrib_mode(int distrib_mode) {
        this.distrib_mode = distrib_mode;
    }

    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    public String getDistrib_name() {
        return distrib_name;
    }

    public void setDistrib_name(String distrib_name) {
        this.distrib_name = distrib_name;
    }

    public String getDistrib_logo() {
        return distrib_logo;
    }

    public void setDistrib_logo(String distrib_logo) {
        this.distrib_logo = distrib_logo;
    }

    public String getConfirm_time() {
        return confirm_time;
    }

    public void setConfirm_time(String confirm_time) {
        this.confirm_time = confirm_time;
    }

    public String getMerch_name() {
        return merch_name;
    }

    public void setMerch_name(String merch_name) {
        this.merch_name = merch_name;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public int getIs_anon() {
        return is_anon;
    }

    public void setIs_anon(int is_anon) {
        this.is_anon = is_anon;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class GoodsListBean {
        /**
         * goods_id : 12
         * goods_name : 宫保鸡丁12
         * goods_spec : 甜的
         */

        private int goods_id;
        private String goods_name;
        private String goods_spec;
        private int score;
        private String content;

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_spec() {
            return goods_spec;
        }

        public void setGoods_spec(String goods_spec) {
            this.goods_spec = goods_spec;
        }
    }
}

package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2018/1/20.
 * by--o2o首页数据
 */

public class O2oHomeBean implements Serializable{


    private List<ListBean> list;
    private List<ZongheBean> zonghe;
    private List<CategoryBean> category;
    private List<CarouselBean> Carousel;
    private boolean choiced;
    private int z_cateid;

    public List<CarouselBean> getCarousel() {
        return Carousel;
    }

    public void setCarousel(List<CarouselBean> carousel) {
        Carousel = carousel;
    }
    public int getZcateid() {
        return z_cateid;
    }

    public void setZcateid(int z_cateid) {
        this.z_cateid = z_cateid;
    }
    public boolean isChoiced() {
        return choiced;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<ZongheBean> getZonghe() {
        return zonghe;
    }

    public void setZonghe(List<ZongheBean> zonghe) {
        this.zonghe = zonghe;
    }

    public List<CategoryBean> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryBean> category) {
        this.category = category;
    }

    public void setChoiced(boolean choiced) {
        this.choiced = choiced;
    }

    public static class ListBean implements Serializable{
        /**
         * distance : 3603
         * distrib_quota : 19.00
         * service_time : 39
         * distrib_money : 6.60
         * file_path : null
         * merch_name : 武汉一高校
         * self_pick_setting : 1
         */

        private String latitude;
        private String longitude;
        private int distrib_setting;
        private int distance;
        private String distrib_quota;
        private String service_time;
        private String distrib_money;
        private String file_path;
        private String merch_name;
        private String month_sale;
        private String invoice_setting;
        private String eval_score;
        private String self_pick_setting;
        private String merch_id;


        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
        public String getMerch_id() {
            return merch_id;
        }

        public void setMerch_id(String merch_id) {
            this.merch_id = merch_id;
        }

        public String getMonth_sale() {
            return month_sale;
        }

        public void setMonth_sale(String month_sale) {
            this.month_sale = month_sale;
        }

        public String getInvoice_setting() {
            return invoice_setting;
        }

        public void setInvoice_setting(String invoice_setting) {
            this.invoice_setting = invoice_setting;
        }

        public String getEval_score() {
            return eval_score;
        }

        public void setEval_score(String eval_score) {
            this.eval_score = eval_score;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public String getDistrib_quota() {
            return distrib_quota;
        }

        public void setDistrib_quota(String distrib_quota) {
            this.distrib_quota = distrib_quota;
        }

        public String getService_time() {
            return service_time;
        }

        public void setService_time(String service_time) {
            this.service_time = service_time;
        }

        public String getDistrib_money() {
            return distrib_money;
        }

        public void setDistrib_money(String distrib_money) {
            this.distrib_money = distrib_money;
        }

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }

        public String getMerch_name() {
            return merch_name;
        }

        public void setMerch_name(String merch_name) {
            this.merch_name = merch_name;
        }

        public String getSelf_pick_setting() {
            return self_pick_setting;
        }

        public void setSelf_pick_setting(String self_pick_setting) {
            this.self_pick_setting = self_pick_setting;
        }
        public int getDistrib_setting() {
            return distrib_setting;
        }

        public void setDistrib_setting(int distrib_setting) {
            this.distrib_setting = distrib_setting;
        }
    }

    public static class CarouselBean implements Serializable{


        /**
         * id : 2
         * name : 测试
         * img : 74
         * position : 2
         * url :
         * starttime : 1516377600
         * endtime : 1516809600
         * is_show : 1
         * sort : 100
         * note : 111
         * updated : 1514270094
         * created : 1514270094
         * file_path : o2o_good/2018/01-03/3b27acd6a7f856cad3835dfbc61eecae.jpg
         */

        private String id;
        private String name;
        private String img;
        private String position;
        private String url;
        private String starttime;
        private String endtime;
        private String is_show;
        private String sort;
        private String note;
        private String updated;
        private String created;
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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
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

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }
    }

    public static class ZongheBean implements Serializable{
        /**
         * name : 评分最高
         * state : 1
         */

        private String name;
        private int state;
        private boolean choiced;

        public boolean isChoiced() {
            return choiced;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public void setChoiced(boolean choiced) {
            this.choiced = choiced;
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
}

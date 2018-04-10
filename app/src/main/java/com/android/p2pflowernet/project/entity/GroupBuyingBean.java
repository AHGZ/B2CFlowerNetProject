package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by heguozhong on 2018/1/17/017.
 * 团购详情数据
 */

public class GroupBuyingBean implements Serializable {

    /**
     * info : {"id":"5","merch_id":"1","province_id":"110000","city_id":"110100","district_id":"110101","title":"dsf","short_title":"dfgsfddg","intro":"sa","total_num":"6","sold_num":"0","market_price":"123.00","price":"111.00","supply_price":"100.00","stock":"90","consume_avg":"12.00","is_takeaway":"0","description":["img/o2o_good/2018/01-15/0de93b9f38189d1392ce965b02cd500e.jpg","img/o2o_good/2018/01-15/580e9cbb3048c74c5f99b997fc9bd30e.jpg"],"merch_info":{"name":"武汉一高校","address":"北京北京市东城区你在哪里呢。我","tel":"18513028980","latitude":"39.9316009","longitude":"116.6054497","distance":972},"notice":[{"name":"适用人数：","value":"单人餐"},{"name":"有效期：","value":"20180101至20180124"},{"name":"使用时间：","value":"09:00-17:00"},{"name":"节假日是否可用：","value":"可用"},{"name":"六、日是否可用：","value":"不可用"},{"name":"是否需要预约：","value":"需要"},{"name":"预约提示：","value":"1"},{"name":"使用规则：","value":"填写使用规则"},{"name":"温馨提示：","value":"辅导费更好的话"}],"img_list":["img/o2o_good/2018/01-15/0de93b9f38189d1392ce965b02cd500e.jpg","img/o2o_good/2018/01-15/580e9cbb3048c74c5f99b997fc9bd30e.jpg","img/o2o_good/2018/01-16/0de93b9f38189d1392ce965b02cd500e.jpg","img/o2o_good/2018/01-16/580e9cbb3048c74c5f99b997fc9bd30e.jpg"],"goods_list":[{"goods_name":"甜的","num":"11","price":"19.20"},{"goods_name":"甜的","num":"11","price":"19.20"},{"goods_name":"甜的","num":"11","price":"19.20"}],"eval_list":[{"score":"5","content":"去你妹的","reply":"","is_anon":"1","created":"2018-01-18 17:20:49","username":"匿名用户","header_img":"","img_list":[]},{"score":"5","content":"dsadasdasd范德萨发","reply":"","is_anon":"1","created":"2018-11-26 23:35:42","username":"匿名用户","header_img":"","img_list":["img/o2o_good/2018/01-15/0de93b9f38189d1392ce965b02cd500e.jpg"]}]}
     */

    private InfoBean info;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean implements Serializable{
        /**
         * id : 5
         * merch_id : 1
         * province_id : 110000
         * city_id : 110100
         * district_id : 110101
         * title : dsf
         * short_title : dfgsfddg
         * intro : sa
         * total_num : 6
         * sold_num : 0
         * market_price : 123.00
         * price : 111.00
         * supply_price : 100.00
         * stock : 90
         * consume_avg : 12.00
         * is_takeaway : 0
         * description : ["img/o2o_good/2018/01-15/0de93b9f38189d1392ce965b02cd500e.jpg","img/o2o_good/2018/01-15/580e9cbb3048c74c5f99b997fc9bd30e.jpg"]
         * merch_info : {"name":"武汉一高校","address":"北京北京市东城区你在哪里呢。我","tel":"18513028980","latitude":"39.9316009","longitude":"116.6054497","distance":972}
         * notice : [{"name":"适用人数：","value":"单人餐"},{"name":"有效期：","value":"20180101至20180124"},{"name":"使用时间：","value":"09:00-17:00"},{"name":"节假日是否可用：","value":"可用"},{"name":"六、日是否可用：","value":"不可用"},{"name":"是否需要预约：","value":"需要"},{"name":"预约提示：","value":"1"},{"name":"使用规则：","value":"填写使用规则"},{"name":"温馨提示：","value":"辅导费更好的话"}]
         * img_list : ["img/o2o_good/2018/01-15/0de93b9f38189d1392ce965b02cd500e.jpg","img/o2o_good/2018/01-15/580e9cbb3048c74c5f99b997fc9bd30e.jpg","img/o2o_good/2018/01-16/0de93b9f38189d1392ce965b02cd500e.jpg","img/o2o_good/2018/01-16/580e9cbb3048c74c5f99b997fc9bd30e.jpg"]
         * goods_list : [{"goods_name":"甜的","num":"11","price":"19.20"},{"goods_name":"甜的","num":"11","price":"19.20"},{"goods_name":"甜的","num":"11","price":"19.20"}]
         * eval_list : [{"score":"5","content":"去你妹的","reply":"","is_anon":"1","created":"2018-01-18 17:20:49","username":"匿名用户","header_img":"","img_list":[]},{"score":"5","content":"dsadasdasd范德萨发","reply":"","is_anon":"1","created":"2018-11-26 23:35:42","username":"匿名用户","header_img":"","img_list":["img/o2o_good/2018/01-15/0de93b9f38189d1392ce965b02cd500e.jpg"]}]
         */



        private int id;
        private int merch_id;
        private int province_id;
        private int city_id;
        private int district_id;
        private String title;
        private String short_title;
        private String intro;
        private int total_num;
        private int sold_num;
        private String market_price;
        private String price;
        private String supply_price;
        private int stock;
        private String consume_avg;
        private int is_takeaway;
        private MerchInfoBean merch_info;
        private List<String> description;
        private String url;
        private List<NoticeBean> notice;
        private List<String> img_list;
        private String eval_score;
        private List<GoodsListBean> goods_list;
        private List<EvalListBean> eval_list;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMerch_id() {
            return merch_id;
        }

        public void setMerch_id(int merch_id) {
            this.merch_id = merch_id;
        }

        public int getProvince_id() {
            return province_id;
        }

        public void setProvince_id(int province_id) {
            this.province_id = province_id;
        }

        public int getCity_id() {
            return city_id;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;
        }

        public int getDistrict_id() {
            return district_id;
        }

        public void setDistrict_id(int district_id) {
            this.district_id = district_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getShort_title() {
            return short_title;
        }

        public void setShort_title(String short_title) {
            this.short_title = short_title;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public int getTotal_num() {
            return total_num;
        }

        public void setTotal_num(int total_num) {
            this.total_num = total_num;
        }

        public int getSold_num() {
            return sold_num;
        }

        public void setSold_num(int sold_num) {
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

        public String getSupply_price() {
            return supply_price;
        }

        public void setSupply_price(String supply_price) {
            this.supply_price = supply_price;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public String getConsume_avg() {
            return consume_avg;
        }

        public void setConsume_avg(String consume_avg) {
            this.consume_avg = consume_avg;
        }

        public int getIs_takeaway() {
            return is_takeaway;
        }

        public void setIs_takeaway(int is_takeaway) {
            this.is_takeaway = is_takeaway;
        }

        public MerchInfoBean getMerch_info() {
            return merch_info;
        }

        public void setMerch_info(MerchInfoBean merch_info) {
            this.merch_info = merch_info;
        }

        public List<String> getDescription() {
            return description;
        }

        public void setDescription(List<String> description) {
            this.description = description;
        }

        public List<NoticeBean> getNotice() {
            return notice;
        }

        public void setNotice(List<NoticeBean> notice) {
            this.notice = notice;
        }

        public String getEval_score() {
            return eval_score;
        }

        public void setEval_score(String eval_score) {
            this.eval_score = eval_score;
        }

        public List<String> getImg_list() {
            return img_list;
        }

        public void setImg_list(List<String> img_list) {
            this.img_list = img_list;
        }

        public List<GoodsListBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsListBean> goods_list) {
            this.goods_list = goods_list;
        }

        public List<EvalListBean> getEval_list() {
            return eval_list;
        }

        public void setEval_list(List<EvalListBean> eval_list) {
            this.eval_list = eval_list;
        }

        public static class MerchInfoBean implements  Serializable{
            /**
             * name : 武汉一高校
             * address : 北京北京市东城区你在哪里呢。我
             * tel : 18513028980
             * latitude : 39.9316009
             * longitude : 116.6054497
             * distance : 972
             */

            private String name;
            private String address;
            private String tel;
            private String latitude;
            private String longitude;
            private int distance;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

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

            public int getDistance() {
                return distance;
            }

            public void setDistance(int distance) {
                this.distance = distance;
            }
        }

        public static class NoticeBean implements  Serializable{
            /**
             * name : 适用人数：
             * value : 单人餐
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class GoodsListBean implements  Serializable{
            /**
             * goods_name : 甜的
             * num : 11
             * price : 19.20
             */

            private String goods_name;
            private String num;
            private String price;

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }

        public static class EvalListBean implements  Serializable{
            /**
             * score : 5
             * content : 去你妹的
             * reply :
             * is_anon : 1
             * created : 2018-01-18 17:20:49
             * username : 匿名用户
             * header_img :
             * img_list : []
             */

            private int score;
            private String content;
            private String reply;
            private int is_anon;
            private String created;
            private String username;
            private String header_img;
            private List<String> img_list;

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

            public String getReply() {
                return reply;
            }

            public void setReply(String reply) {
                this.reply = reply;
            }

            public int getIs_anon() {
                return is_anon;
            }

            public void setIs_anon(int is_anon) {
                this.is_anon = is_anon;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getHeader_img() {
                return header_img;
            }

            public void setHeader_img(String header_img) {
                this.header_img = header_img;
            }

            public List<String> getImg_list() {
                return img_list;
            }

            public void setImg_list(List<String> img_list) {
                this.img_list = img_list;
            }
        }
    }

}

package com.android.p2pflowernet.project.entity;

import java.util.List;

/**
 * Created by heguozhong on 2018/1/26/026.
 * 团购明细
 */

public class GroupFullDetailBean {
    /**
     * order : {"order_num":"697902","state":"3","eval_state":"0","num":"1","time":"1516602406","sy_time":0,"pay_channel":"0","pay_time":"0","ended":"0","code":[{"group_code":"47811123","state":"2","use_time":"1517293712"}]}
     * group : {"id":"5","merch_id":"1","province_id":"110000","city_id":"110100","district_id":"110101","title":"dsf","short_title":"dfgsfddg","intro":"sa","market_price":"123.00","price":"111.00","supply_price":"100.00","consume_avg":"12.00","starttime":"20180101","endtime":"20180312","day_start":"09:00","day_end":"17:00","holiday_usable":"1","weekend_usable":"0","need_resv":"1","resv_tips":"1","rules":"填写使用规则","tips":"辅导费更好的话","is_takeaway":"0","keywords":"水电费 的方式更多","description":"433,434","imgs":"433,434,440,441","fit_type":"1","merch_info":{"file_path":"","name":"武汉一高校","address":"北京北京市东城区你在哪里呢。我","tel":"18701608977","distance":981},"notice":[{"name":"适用人数：","value":"单人餐"},{"name":"有效期：","value":"20180101至20180312"},{"name":"使用时间：","value":"09:00-17:00"},{"name":"节假日是否可用：","value":"可用"},{"name":"六、日是否可用：","value":"不可用"},{"name":"是否需要预约：","value":"需要"},{"name":"预约提示：","value":"1"},{"name":"使用规则：","value":"填写使用规则"},{"name":"温馨提示：","value":"辅导费更好的话"}],"zprice":"6.00","znum":33,"goods_list":[{"num":"11","price":"211.20","goods_name":"甜的"},{"num":"11","price":"211.20","goods_name":"甜的"},{"num":"11","price":"211.20","goods_name":"甜的"}]}
     */

    private OrderBean order;
    private GroupBean group;

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public GroupBean getGroup() {
        return group;
    }

    public void setGroup(GroupBean group) {
        this.group = group;
    }

    public static class OrderBean {
        /**
         * order_num : 697902
         * state : 3
         * eval_state : 0
         * num : 1
         * time : 1516602406
         * sy_time : 0
         * pay_channel : 0
         * pay_time : 0
         * ended : 0
         * code : [{"group_code":"47811123","state":"2","use_time":"1517293712"}]
         */

        private String order_num;
        private int state;
        private String eval_state;
        private String num;
        private String time;
        private int sy_time;
        private int pay_channel;
        private String pay_time;
        private String ended;
        private List<CodeBean> code;

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getEval_state() {
            return eval_state;
        }

        public void setEval_state(String eval_state) {
            this.eval_state = eval_state;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getSy_time() {
            return sy_time;
        }

        public void setSy_time(int sy_time) {
            this.sy_time = sy_time;
        }

        public int getPay_channel() {
            return pay_channel;
        }

        public void setPay_channel(int pay_channel) {
            this.pay_channel = pay_channel;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getEnded() {
            return ended;
        }

        public void setEnded(String ended) {
            this.ended = ended;
        }

        public List<CodeBean> getCode() {
            return code;
        }

        public void setCode(List<CodeBean> code) {
            this.code = code;
        }

        public static class CodeBean {
            /**
             * group_code : 47811123
             * state : 2
             * use_time : 1517293712
             */
            private String group_code;
            private int state;
            private String use_time;

            public String getGroup_code() {
                return group_code;
            }

            public void setGroup_code(String group_code) {
                this.group_code = group_code;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getUse_time() {
                return use_time;
            }

            public void setUse_time(String use_time) {
                this.use_time = use_time;
            }
        }
    }

    public static class GroupBean {
        /**
         * id : 5
         * merch_id : 1
         * province_id : 110000
         * city_id : 110100
         * district_id : 110101
         * title : dsf
         * short_title : dfgsfddg
         * intro : sa
         * market_price : 123.00
         * price : 111.00
         * supply_price : 100.00
         * consume_avg : 12.00
         * starttime : 20180101
         * endtime : 20180312
         * day_start : 09:00
         * day_end : 17:00
         * holiday_usable : 1
         * weekend_usable : 0
         * need_resv : 1
         * resv_tips : 1
         * rules : 填写使用规则
         * tips : 辅导费更好的话
         * is_takeaway : 0
         * keywords : 水电费 的方式更多
         * description : 433,434
         * imgs : 433,434,440,441
         * fit_type : 1
         * merch_info : {"file_path":"","name":"武汉一高校","address":"北京北京市东城区你在哪里呢。我","tel":"18701608977","distance":981}
         * notice : [{"name":"适用人数：","value":"单人餐"},{"name":"有效期：","value":"20180101至20180312"},{"name":"使用时间：","value":"09:00-17:00"},{"name":"节假日是否可用：","value":"可用"},{"name":"六、日是否可用：","value":"不可用"},{"name":"是否需要预约：","value":"需要"},{"name":"预约提示：","value":"1"},{"name":"使用规则：","value":"填写使用规则"},{"name":"温馨提示：","value":"辅导费更好的话"}]
         * zprice : 6.00
         * znum : 33
         * goods_list : [{"num":"11","price":"211.20","goods_name":"甜的"},{"num":"11","price":"211.20","goods_name":"甜的"},{"num":"11","price":"211.20","goods_name":"甜的"}]
         */


        private String id;
        private String merch_id;
        private String province_id;
        private String city_id;
        private String district_id;
        private String title;
        private String short_title;
        private String intro;
        private String market_price;
        private String price;
        private String supply_price;
        private String consume_avg;
        private String starttime;
        private String endtime;
        private String day_start;
        private String day_end;
        private String holiday_usable;
        private String weekend_usable;
        private String need_resv;
        private String resv_tips;
        private String rules;
        private String tips;
        private int is_takeaway;
        private String keywords;
        private String description;
        private String imgs;
        private String fit_type;
        private MerchInfoBean merch_info;
        private String zprice;
        private int znum;
        private List<NoticeBean> notice;
        private List<GoodsListBean> goods_list;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMerch_id() {
            return merch_id;
        }

        public void setMerch_id(String merch_id) {
            this.merch_id = merch_id;
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

        public String getConsume_avg() {
            return consume_avg;
        }

        public void setConsume_avg(String consume_avg) {
            this.consume_avg = consume_avg;
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

        public String getDay_start() {
            return day_start;
        }

        public void setDay_start(String day_start) {
            this.day_start = day_start;
        }

        public String getDay_end() {
            return day_end;
        }

        public void setDay_end(String day_end) {
            this.day_end = day_end;
        }

        public String getHoliday_usable() {
            return holiday_usable;
        }

        public void setHoliday_usable(String holiday_usable) {
            this.holiday_usable = holiday_usable;
        }

        public String getWeekend_usable() {
            return weekend_usable;
        }

        public void setWeekend_usable(String weekend_usable) {
            this.weekend_usable = weekend_usable;
        }

        public String getNeed_resv() {
            return need_resv;
        }

        public void setNeed_resv(String need_resv) {
            this.need_resv = need_resv;
        }

        public String getResv_tips() {
            return resv_tips;
        }

        public void setResv_tips(String resv_tips) {
            this.resv_tips = resv_tips;
        }

        public String getRules() {
            return rules;
        }

        public void setRules(String rules) {
            this.rules = rules;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public int getIs_takeaway() {
            return is_takeaway;
        }

        public void setIs_takeaway(int is_takeaway) {
            this.is_takeaway = is_takeaway;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public String getFit_type() {
            return fit_type;
        }

        public void setFit_type(String fit_type) {
            this.fit_type = fit_type;
        }

        public MerchInfoBean getMerch_info() {
            return merch_info;
        }

        public void setMerch_info(MerchInfoBean merch_info) {
            this.merch_info = merch_info;
        }

        public String getZprice() {
            return zprice;
        }

        public void setZprice(String zprice) {
            this.zprice = zprice;
        }

        public int getZnum() {
            return znum;
        }

        public void setZnum(int znum) {
            this.znum = znum;
        }

        public List<NoticeBean> getNotice() {
            return notice;
        }

        public void setNotice(List<NoticeBean> notice) {
            this.notice = notice;
        }

        public List<GoodsListBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsListBean> goods_list) {
            this.goods_list = goods_list;
        }

        public static class MerchInfoBean {
            /**
             * file_path :
             * name : 武汉一高校
             * address : 北京北京市东城区你在哪里呢。我
             * tel : 18701608977
             * distance : 981
             */

            private String file_path;
            private String name;
            private String address;
            private String tel;
            private int distance;

            public String getFile_path() {
                return file_path;
            }

            public void setFile_path(String file_path) {
                this.file_path = file_path;
            }

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

            public int getDistance() {
                return distance;
            }

            public void setDistance(int distance) {
                this.distance = distance;
            }
        }

        public static class NoticeBean {
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

        public static class GoodsListBean {
            /**
             * num : 11
             * price : 211.20
             * goods_name : 甜的
             */

            private String num;
            private String price;
            private String goods_name;

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

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }
        }
    }

}

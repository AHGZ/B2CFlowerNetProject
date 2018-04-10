package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/2/9/009.
 */

public class GoPayBean implements Serializable{

    /**
     * lists : [{"time":60300,"distrib_money":"0.00","data":{"hours":16,"minutes":"45"}},{"time":61200,"distrib_money":"0.00","data":{"hours":17,"minutes":0}},{"time":62100,"distrib_money":"0.00","data":{"hours":17,"minutes":"15"}},{"time":63000,"distrib_money":"0.00","data":{"hours":17,"minutes":"30"}},{"time":63900,"distrib_money":"0.00","data":{"hours":17,"minutes":"45"}},{"time":64800,"distrib_money":"0.00","data":{"hours":18,"minutes":0}},{"time":65700,"distrib_money":"0.00","data":{"hours":18,"minutes":"15"}},{"time":66600,"distrib_money":"0.00","data":{"hours":18,"minutes":"30"}},{"time":67500,"distrib_money":"0.00","data":{"hours":18,"minutes":"45"}},{"time":68400,"distrib_money":"0.00","data":{"hours":19,"minutes":0}},{"time":69300,"distrib_money":"0.00","data":{"hours":19,"minutes":"15"}},{"time":70200,"distrib_money":"0.00","data":{"hours":19,"minutes":"30"}},{"time":71100,"distrib_money":"0.00","data":{"hours":19,"minutes":"45"}},{"time":72000,"distrib_money":"0.00","data":{"hours":20,"minutes":0}},{"time":72900,"distrib_money":"0.00","data":{"hours":20,"minutes":"15"}},{"time":73800,"distrib_money":"0.00","data":{"hours":20,"minutes":"30"}},{"time":74700,"distrib_money":"0.00","data":{"hours":20,"minutes":"45"}},{"time":75600,"distrib_money":"0.00","data":{"hours":21,"minutes":0}},{"time":76500,"distrib_money":"0.00","data":{"hours":21,"minutes":"15"}},{"time":77400,"distrib_money":"0.00","data":{"hours":21,"minutes":"30"}},{"time":78300,"distrib_money":"0.00","data":{"hours":21,"minutes":"45"}},{"time":79200,"distrib_money":"0.00","data":{"hours":22,"minutes":0}},{"time":80100,"distrib_money":"0.00","data":{"hours":22,"minutes":"15"}},{"time":81000,"distrib_money":"0.00","data":{"hours":22,"minutes":"30"}},{"time":81900,"distrib_money":"0.00","data":{"hours":22,"minutes":"45"}},{"time":82800,"distrib_money":"0.00","data":{"hours":23,"minutes":0}},{"time":83700,"distrib_money":"0.00","data":{"hours":23,"minutes":"15"}},{"time":84600,"distrib_money":"0.00","data":{"hours":23,"minutes":"30"}},{"time":85500,"distrib_money":"0.00","data":{"hours":23,"minutes":"45"}}]
     * self_lists : [{"time":58500,"distrib_money":"0.00","data":{"hours":16,"minutes":"15"}},{"time":59400,"distrib_money":"0.00","data":{"hours":16,"minutes":"30"}},{"time":60300,"distrib_money":"0.00","data":{"hours":16,"minutes":"45"}},{"time":61200,"distrib_money":"0.00","data":{"hours":17,"minutes":0}},{"time":62100,"distrib_money":"0.00","data":{"hours":17,"minutes":"15"}},{"time":63000,"distrib_money":"0.00","data":{"hours":17,"minutes":"30"}},{"time":63900,"distrib_money":"0.00","data":{"hours":17,"minutes":"45"}},{"time":64800,"distrib_money":"0.00","data":{"hours":18,"minutes":0}},{"time":65700,"distrib_money":"0.00","data":{"hours":18,"minutes":"15"}},{"time":66600,"distrib_money":"0.00","data":{"hours":18,"minutes":"30"}},{"time":67500,"distrib_money":"0.00","data":{"hours":18,"minutes":"45"}},{"time":68400,"distrib_money":"0.00","data":{"hours":19,"minutes":0}},{"time":69300,"distrib_money":"0.00","data":{"hours":19,"minutes":"15"}},{"time":70200,"distrib_money":"0.00","data":{"hours":19,"minutes":"30"}},{"time":71100,"distrib_money":"0.00","data":{"hours":19,"minutes":"45"}},{"time":72000,"distrib_money":"0.00","data":{"hours":20,"minutes":0}},{"time":72900,"distrib_money":"0.00","data":{"hours":20,"minutes":"15"}},{"time":73800,"distrib_money":"0.00","data":{"hours":20,"minutes":"30"}},{"time":74700,"distrib_money":"0.00","data":{"hours":20,"minutes":"45"}},{"time":75600,"distrib_money":"0.00","data":{"hours":21,"minutes":0}},{"time":76500,"distrib_money":"0.00","data":{"hours":21,"minutes":"15"}},{"time":77400,"distrib_money":"0.00","data":{"hours":21,"minutes":"30"}},{"time":78300,"distrib_money":"0.00","data":{"hours":21,"minutes":"45"}},{"time":79200,"distrib_money":"0.00","data":{"hours":22,"minutes":0}},{"time":80100,"distrib_money":"0.00","data":{"hours":22,"minutes":"15"}},{"time":81000,"distrib_money":"0.00","data":{"hours":22,"minutes":"30"}},{"time":81900,"distrib_money":"0.00","data":{"hours":22,"minutes":"45"}},{"time":82800,"distrib_money":"0.00","data":{"hours":23,"minutes":0}},{"time":83700,"distrib_money":"0.00","data":{"hours":23,"minutes":"15"}},{"time":84600,"distrib_money":"0.00","data":{"hours":23,"minutes":"30"}},{"time":85500,"distrib_money":"0.00","data":{"hours":23,"minutes":"45"}}]
     * self_url : /home/static_html/o2o_self_greement
     * reach_time : 59640
     * is_self : 1
     * is_invoice : 1
     * invoice_explain : 订单金额满100.00可开票！
     * add_info : {"id":"30","name":"何国忠","sex":"1","telephone":"18911005030","location":"北京市-北京市-朝阳区","site_name":"龙湖长楹天街","address":"北京市朝阳区朝阳北路与管庄路口交界处向东200米(万象"}
     */

    private String self_url;
    private int reach_time;
    private int is_self;
    private int is_invoice;
    private String invoice_explain;
    private AddInfoBean add_info;
    private List<ListsBean> lists;
    private List<SelfListsBean> self_lists;

    public String getSelf_url() {
        return self_url;
    }

    public void setSelf_url(String self_url) {
        this.self_url = self_url;
    }

    public int getReach_time() {
        return reach_time;
    }

    public void setReach_time(int reach_time) {
        this.reach_time = reach_time;
    }

    public int getIs_self() {
        return is_self;
    }

    public void setIs_self(int is_self) {
        this.is_self = is_self;
    }

    public int getIs_invoice() {
        return is_invoice;
    }

    public void setIs_invoice(int is_invoice) {
        this.is_invoice = is_invoice;
    }

    public String getInvoice_explain() {
        return invoice_explain;
    }

    public void setInvoice_explain(String invoice_explain) {
        this.invoice_explain = invoice_explain;
    }

    public AddInfoBean getAdd_info() {
        return add_info;
    }

    public void setAdd_info(AddInfoBean add_info) {
        this.add_info = add_info;
    }

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public List<SelfListsBean> getSelf_lists() {
        return self_lists;
    }

    public void setSelf_lists(List<SelfListsBean> self_lists) {
        this.self_lists = self_lists;
    }

    public static class AddInfoBean implements Serializable{
        /**
         * id : 30
         * name : 何国忠
         * sex : 1
         * telephone : 18911005030
         * location : 北京市-北京市-朝阳区
         * site_name : 龙湖长楹天街
         * address : 北京市朝阳区朝阳北路与管庄路口交界处向东200米(万象
         */

        private String id;
        private String name;
        private String sex;
        private String telephone;
        private String location;
        private String site_name;
        private String address;

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

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
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

        public String getSite_name() {
            return site_name;
        }

        public void setSite_name(String site_name) {
            this.site_name = site_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static class ListsBean implements Serializable{
        /**
         * time : 60300
         * distrib_money : 0.00
         * data : {"hours":16,"minutes":"45"}
         */

        private int time;
        private String distrib_money;
        private DataBean data;

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getDistrib_money() {
            return distrib_money;
        }

        public void setDistrib_money(String distrib_money) {
            this.distrib_money = distrib_money;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean implements Serializable{
            /**
             * hours : 16
             * minutes : 45
             */

            private int hours;
            private String minutes;

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public String getMinutes() {
                return minutes;
            }

            public void setMinutes(String minutes) {
                this.minutes = minutes;
            }
        }
    }

    public static class SelfListsBean implements Serializable{
        /**
         * time : 58500
         * distrib_money : 0.00
         * data : {"hours":16,"minutes":"15"}
         */

        private int time;
        private String distrib_money;
        private DataBeanX data;

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getDistrib_money() {
            return distrib_money;
        }

        public void setDistrib_money(String distrib_money) {
            this.distrib_money = distrib_money;
        }

        public DataBeanX getData() {
            return data;
        }

        public void setData(DataBeanX data) {
            this.data = data;
        }

        public static class DataBeanX implements Serializable{
            /**
             * hours : 16
             * minutes : 15
             */

            private int hours;
            private String minutes;

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public String getMinutes() {
                return minutes;
            }

            public void setMinutes(String minutes) {
                this.minutes = minutes;
            }
        }
    }
}

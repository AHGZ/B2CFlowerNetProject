package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2017/12/12.
 * by--确认订单
 */

public class OrderDetailBean implements Serializable{


    /**
     * list : [{"mfrid":"10088","mfrname":"test1","num":3,"sale_price":"6905.00","huafan":"1108.20","invoice_money":"0.00","express_money":"40.00","goods":[{"spec_id":"4","goods_name":"花花公子真皮皮衣男2017秋冬新品海宁绵羊皮真皮男士商务修身","huafan":"369.60","file_path":"business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg","sale_price":"2282.00","num":"2","is_noreason":"保证正品"},{"spec_id":"3","goods_name":"花花公子真皮皮衣男2017秋冬新品海宁绵羊皮真皮男士商务修身","huafan":"369.00","file_path":"business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg","sale_price":"2281.00","num":"1","is_noreason":"保证正品"}]}]
     * select : 0
     * user : {"name":"你的","telephone":"18513081386","location":"朝阳的人生就是一个很简单很复杂很重要很美好很梦幻之美？武警总指挥部","address":"北京-北京市-东城区"}
     * invoice_state : 0
     * num : 3
     * huafan : 1108.20
     * sale_price : 6905.00
     * invoice_money : 0.00
     */

    private String select;
    private UserBean user;
    private String invoice_state;
    private String num;
    private String huafan;
    private String sale_price;
    private String invoice_money;
    private String express_money;
    private String source;
    private String address_id;
    private List<ListBean> list;

    public String getExpress_money() {
        return express_money;
    }

    public void setExpress_money(String express_money) {
        this.express_money = express_money;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getInvoice_state() {
        return invoice_state;
    }

    public void setInvoice_state(String invoice_state) {
        this.invoice_state = invoice_state;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getHuafan() {
        return huafan;
    }

    public void setHuafan(String huafan) {
        this.huafan = huafan;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getInvoice_money() {
        return invoice_money;
    }

    public void setInvoice_money(String invoice_money) {
        this.invoice_money = invoice_money;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public String getSource() {
        return source;
    }

    public static class UserBean implements Serializable{
        /**
         * name : 你的
         * telephone : 18513081386
         * location : 朝阳的人生就是一个很简单很复杂很重要很美好很梦幻之美？武警总指挥部
         * address : 北京-北京市-东城区
         */

        private String name;
        private String telephone;
        private String location;
        private String address;
        private String address_id;

        public String getAddress_id() {
            return address_id;
        }

        public void setAddress_id(String address_id) {
            this.address_id = address_id;
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
    }

    public static class ListBean implements Serializable{
        /**
         * mfrid : 10088
         * mfrname : test1
         * num : 3
         * sale_price : 6905.00
         * huafan : 1108.20
         * invoice_money : 0.00
         * express_money : 40.00
         * goods : [{"spec_id":"4","goods_name":"花花公子真皮皮衣男2017秋冬新品海宁绵羊皮真皮男士商务修身","huafan":"369.60","file_path":"business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg","sale_price":"2282.00","num":"2","is_noreason":"保证正品"},{"spec_id":"3","goods_name":"花花公子真皮皮衣男2017秋冬新品海宁绵羊皮真皮男士商务修身","huafan":"369.00","file_path":"business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg","sale_price":"2281.00","num":"1","is_noreason":"保证正品"}]
         */

        private String mfrid;
        private String mfrname;
        private int num;
        private String sale_price;
        private String huafan;
        private String invoice_money;
        private String express_money;
        private List<GoodsBean> goods;

        public String getMfrid() {
            return mfrid;
        }

        public void setMfrid(String mfrid) {
            this.mfrid = mfrid;
        }

        public String getMfrname() {
            return mfrname;
        }

        public void setMfrname(String mfrname) {
            this.mfrname = mfrname;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getSale_price() {
            return sale_price;
        }

        public void setSale_price(String sale_price) {
            this.sale_price = sale_price;
        }

        public String getHuafan() {
            return huafan;
        }

        public void setHuafan(String huafan) {
            this.huafan = huafan;
        }

        public String getInvoice_money() {
            return invoice_money;
        }

        public void setInvoice_money(String invoice_money) {
            this.invoice_money = invoice_money;
        }

        public String getExpress_money() {
            return express_money;
        }

        public void setExpress_money(String express_money) {
            this.express_money = express_money;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean implements Serializable{
            /**
             * spec_id : 4
             * goods_name : 花花公子真皮皮衣男2017秋冬新品海宁绵羊皮真皮男士商务修身
             * huafan : 369.60
             * file_path : business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg
             * sale_price : 2282.00
             * num : 2
             * is_noreason : 保证正品
             */

            private String spec_id;
            private String goods_name;
            private String huafan;
            private String file_path;
            private String sale_price;
            private String num;
            private String is_noreason;
            private String goods_spec;

            public String getGoods_spec() {
                return goods_spec;
            }

            public void setGoods_spec(String goods_spec) {
                this.goods_spec = goods_spec;
            }

            public String getSpec_id() {
                return spec_id;
            }

            public void setSpec_id(String spec_id) {
                this.spec_id = spec_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getHuafan() {
                return huafan;
            }

            public void setHuafan(String huafan) {
                this.huafan = huafan;
            }

            public String getFile_path() {
                return file_path;
            }

            public void setFile_path(String file_path) {
                this.file_path = file_path;
            }

            public String getSale_price() {
                return sale_price;
            }

            public void setSale_price(String sale_price) {
                this.sale_price = sale_price;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getIs_noreason() {
                return is_noreason;
            }

            public void setIs_noreason(String is_noreason) {
                this.is_noreason = is_noreason;
            }
        }
    }
}

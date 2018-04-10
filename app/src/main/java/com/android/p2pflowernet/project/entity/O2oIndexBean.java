package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author: zhangpeisen
 * created on: 2018/1/10 上午10:44
 * description: O2o首页店铺数据
 */
public class O2oIndexBean implements Serializable {

    /**
     * merch_name :
     * logo_url : img/o2o_good/2018/01-03/d74038ae515a50e5a3a88d179f9041da.jpg
     * distrib_money : 123.00
     * distrib_quota : 19.00
     * lists : [{"id":"1","name":"分","num":"8","goods_list":[{"id":"16","name":"宫保鸡丁41","goods_img":"img/o2o_good/2018/01-03/d74038ae515a50e5a3a88d179f9041da.jpg","price":"19.20","supply_price":"18.10","unit":"盘","stock":"100","box_num":"20","box_price":"0.50","is_spec":1,"spec_list":[{"id":"27","goods_spec":"甜的","o_spec_price":"19.20","spec_price":"18.10","cur_spec_stock":"11"},{"id":"28","goods_spec":"辣的","o_spec_price":"21.20","spec_price":"18.10","cur_spec_stock":"11"}],"huafan":"1.10"},{"id":"28","name":"宫保鸡丁4a","goods_img":"img/o2o_good/2018/01-03/9d99d3a8a86523e1d08f488ccfc3b2b5.jpg","price":"20.00","supply_price":"18.00","unit":"盘","stock":"100","box_num":"20","box_price":"0.50","is_spec":0,"spec_list":[],"huafan":"2.00"},{"id":"23","name":"宫保鸡丁412","goods_img":"img/o2o_good/2018/01-03/9d99d3a8a86523e1d08f488ccfc3b2b5.jpg","price":"1.00","supply_price":"1.00","unit":"盘","stock":"100","box_num":"20","box_price":"0.50","is_spec":1,"spec_list":[{"id":"48","goods_spec":"可恨","o_spec_price":"1.00","spec_price":"1.00","cur_spec_stock":"123"},{"id":"53","goods_spec":"甜的","o_spec_price":"19.20","spec_price":"18.10","cur_spec_stock":"11"},{"id":"54","goods_spec":"辣的","o_spec_price":"21.20","spec_price":"18.10","cur_spec_stock":"11"}],"huafan":"0.00"},{"id":"29","name":"宫保鸡丁4a23","goods_img":"img/o2o_good/2018/01-03/9d99d3a8a86523e1d08f488ccfc3b2b5.jpg","price":"321.00","supply_price":"321.00","unit":"盘","stock":"100","box_num":"20","box_price":"0.50","is_spec":1,"spec_list":[{"id":"74","goods_spec":"1231","o_spec_price":"321.00","spec_price":"321.00","cur_spec_stock":"123"}],"huafan":"0.00"},{"id":"26","name":"宫保鸡丁231","goods_img":"img/o2o_good/2018/01-03/9d99d3a8a86523e1d08f488ccfc3b2b5.jpg","price":"19.20","supply_price":"18.10","unit":"盘","stock":"100","box_num":"20","box_price":"0.50","is_spec":1,"spec_list":[{"id":"66","goods_spec":"甜的","o_spec_price":"19.20","spec_price":"18.10","cur_spec_stock":"11"},{"id":"67","goods_spec":"辣的","o_spec_price":"21.20","spec_price":"18.10","cur_spec_stock":"11"}],"huafan":"1.10"},{"id":"17","name":"宫保鸡丁43","goods_img":"img/o2o_good/2018/01-03/d74038ae515a50e5a3a88d179f9041da.jpg","price":"19.20","supply_price":"18.10","unit":"盘","stock":"100","box_num":"20","box_price":"0.50","is_spec":1,"spec_list":[{"id":"30","goods_spec":"甜的","o_spec_price":"19.20","spec_price":"18.10","cur_spec_stock":"11"},{"id":"31","goods_spec":"辣的","o_spec_price":"21.20","spec_price":"18.10","cur_spec_stock":"11"}],"huafan":"1.10"}]},{"id":"6","name":"这里","num":"1","goods_list":[{"id":"10","name":"宫保鸡丁10","goods_img":"img/o2o_good/2018/01-03/d74038ae515a50e5a3a88d179f9041da.jpg","price":"20.00","supply_price":"18.00","unit":"盘","stock":"100","box_num":"20","box_price":"0.50","is_spec":0,"spec_list":[],"huafan":"2.00"}]},{"id":"5","name":"呢","num":"1","goods_list":[{"id":"12","name":"宫保鸡丁12","goods_img":"img/o2o_good/2018/01-03/d74038ae515a50e5a3a88d179f9041da.jpg","price":"19.20","supply_price":"18.10","unit":"盘","stock":"100","box_num":"20","box_price":"0.50","is_spec":1,"spec_list":[{"id":"15","goods_spec":"甜的","o_spec_price":"19.20","spec_price":"18.10","cur_spec_stock":"11"},{"id":"16","goods_spec":"辣的","o_spec_price":"21.20","spec_price":"18.10","cur_spec_stock":"11"}],"huafan":"1.10"}]},{"id":"4","name":"当当","num":"1","goods_list":[{"id":"13","name":"宫保鸡丁13","goods_img":"img/o2o_good/2018/01-03/d74038ae515a50e5a3a88d179f9041da.jpg","price":"19.20","supply_price":"18.10","unit":"盘","stock":"100","box_num":"20","box_price":"0.50","is_spec":1,"spec_list":[{"id":"18","goods_spec":"甜的","o_spec_price":"19.20","spec_price":"18.10","cur_spec_stock":"11"},{"id":"19","goods_spec":"辣的","o_spec_price":"21.20","spec_price":"18.10","cur_spec_stock":"11"}],"huafan":"1.10"}]}]
     * is_close : 0
     * id : 1
     * business_name : 武汉一高校
     */

    private String merch_name;
    private String logo_url;
    private String distrib_money;
    private String distrib_quota;
    private int is_close;
    private String id;
    private String business_name;
    /**
     * id : 1
     * name : 分
     * num : 8
     * goods_list : [{"id":"16","name":"宫保鸡丁41","goods_img":"img/o2o_good/2018/01-03/d74038ae515a50e5a3a88d179f9041da.jpg","price":"19.20","supply_price":"18.10","unit":"盘","stock":"100","box_num":"20","box_price":"0.50","is_spec":1,"spec_list":[{"id":"27","goods_spec":"甜的","o_spec_price":"19.20","spec_price":"18.10","cur_spec_stock":"11"},{"id":"28","goods_spec":"辣的","o_spec_price":"21.20","spec_price":"18.10","cur_spec_stock":"11"}],"huafan":"1.10"},{"id":"28","name":"宫保鸡丁4a","goods_img":"img/o2o_good/2018/01-03/9d99d3a8a86523e1d08f488ccfc3b2b5.jpg","price":"20.00","supply_price":"18.00","unit":"盘","stock":"100","box_num":"20","box_price":"0.50","is_spec":0,"spec_list":[],"huafan":"2.00"},{"id":"23","name":"宫保鸡丁412","goods_img":"img/o2o_good/2018/01-03/9d99d3a8a86523e1d08f488ccfc3b2b5.jpg","price":"1.00","supply_price":"1.00","unit":"盘","stock":"100","box_num":"20","box_price":"0.50","is_spec":1,"spec_list":[{"id":"48","goods_spec":"可恨","o_spec_price":"1.00","spec_price":"1.00","cur_spec_stock":"123"},{"id":"53","goods_spec":"甜的","o_spec_price":"19.20","spec_price":"18.10","cur_spec_stock":"11"},{"id":"54","goods_spec":"辣的","o_spec_price":"21.20","spec_price":"18.10","cur_spec_stock":"11"}],"huafan":"0.00"},{"id":"29","name":"宫保鸡丁4a23","goods_img":"img/o2o_good/2018/01-03/9d99d3a8a86523e1d08f488ccfc3b2b5.jpg","price":"321.00","supply_price":"321.00","unit":"盘","stock":"100","box_num":"20","box_price":"0.50","is_spec":1,"spec_list":[{"id":"74","goods_spec":"1231","o_spec_price":"321.00","spec_price":"321.00","cur_spec_stock":"123"}],"huafan":"0.00"},{"id":"26","name":"宫保鸡丁231","goods_img":"img/o2o_good/2018/01-03/9d99d3a8a86523e1d08f488ccfc3b2b5.jpg","price":"19.20","supply_price":"18.10","unit":"盘","stock":"100","box_num":"20","box_price":"0.50","is_spec":1,"spec_list":[{"id":"66","goods_spec":"甜的","o_spec_price":"19.20","spec_price":"18.10","cur_spec_stock":"11"},{"id":"67","goods_spec":"辣的","o_spec_price":"21.20","spec_price":"18.10","cur_spec_stock":"11"}],"huafan":"1.10"},{"id":"17","name":"宫保鸡丁43","goods_img":"img/o2o_good/2018/01-03/d74038ae515a50e5a3a88d179f9041da.jpg","price":"19.20","supply_price":"18.10","unit":"盘","stock":"100","box_num":"20","box_price":"0.50","is_spec":1,"spec_list":[{"id":"30","goods_spec":"甜的","o_spec_price":"19.20","spec_price":"18.10","cur_spec_stock":"11"},{"id":"31","goods_spec":"辣的","o_spec_price":"21.20","spec_price":"18.10","cur_spec_stock":"11"}],"huafan":"1.10"}]
     */

    private List<ListsBean> lists;

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

    public String getDistrib_money() {
        return distrib_money;
    }

    public void setDistrib_money(String distrib_money) {
        this.distrib_money = distrib_money;
    }

    public String getDistrib_quota() {
        return distrib_quota;
    }

    public void setDistrib_quota(String distrib_quota) {
        this.distrib_quota = distrib_quota;
    }

    public int getIs_close() {
        return is_close;
    }

    public void setIs_close(int is_close) {
        this.is_close = is_close;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public static class ListsBean implements Serializable {
        private String id;
        private String name;
        private String num;
        /**
         * id : 16
         * name : 宫保鸡丁41
         * goods_img : img/o2o_good/2018/01-03/d74038ae515a50e5a3a88d179f9041da.jpg
         * price : 19.20
         * supply_price : 18.10
         * unit : 盘
         * stock : 100
         * box_num : 20
         * box_price : 0.50
         * is_spec : 1
         * spec_list : [{"id":"27","goods_spec":"甜的","o_spec_price":"19.20","spec_price":"18.10","cur_spec_stock":"11"},{"id":"28","goods_spec":"辣的","o_spec_price":"21.20","spec_price":"18.10","cur_spec_stock":"11"}]
         * huafan : 1.10
         */

        private List<GoodsListBean> goods_list;

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

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public List<GoodsListBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsListBean> goods_list) {
            this.goods_list = goods_list;
        }

        public static class GoodsListBean implements Serializable {
            private int tag;
            private String id;
            private String name;
            private String goods_img;
            private String price;
            private String supply_price;
            private String unit;
            private String stock;
            private String box_num;
            private String box_price;
            private int is_spec;
            private String huafan;
            /**
             * id : 27
             * goods_spec : 甜的
             * o_spec_price : 19.20
             * spec_price : 18.10
             * cur_spec_stock : 11
             */

            private List<SpecListBean> spec_list;

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

            public String getGoods_img() {
                return goods_img;
            }

            public void setGoods_img(String goods_img) {
                this.goods_img = goods_img;
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

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getStock() {
                return stock;
            }

            public void setStock(String stock) {
                this.stock = stock;
            }

            public String getBox_num() {
                return box_num;
            }

            public void setBox_num(String box_num) {
                this.box_num = box_num;
            }

            public String getBox_price() {
                return box_price;
            }

            public void setBox_price(String box_price) {
                this.box_price = box_price;
            }

            public int getIs_spec() {
                return is_spec;
            }

            public void setIs_spec(int is_spec) {
                this.is_spec = is_spec;
            }

            public String getHuafan() {
                return huafan;
            }

            public void setHuafan(String huafan) {
                this.huafan = huafan;
            }

            public List<SpecListBean> getSpec_list() {
                return spec_list;
            }

            public void setSpec_list(List<SpecListBean> spec_list) {
                this.spec_list = spec_list;
            }

            public int getTag() {
                return tag;
            }

            public void setTag(int tag) {
                this.tag = tag;
            }

            public int hashCode() {

                int code = this.name.hashCode() + (int) Double.parseDouble(this.price);
                return code;
            }

            @Override
            public boolean equals(Object obj) {

                if (obj == this) return true;

                return obj instanceof GoodsListBean &&
                        this.name.equals(((GoodsListBean) obj).name) &&
                        this.price == ((GoodsListBean) obj).getPrice() &&
                        this.stock == ((GoodsListBean) obj).stock;
            }

            public static class SpecListBean extends SelectBean implements Serializable {
                private String id;
                private String goods_spec;
                private String o_spec_price;
                private String spec_price;
                private String cur_spec_stock;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getGoods_spec() {
                    return goods_spec;
                }

                public void setGoods_spec(String goods_spec) {
                    this.goods_spec = goods_spec;
                }

                public String getO_spec_price() {
                    return o_spec_price;
                }

                public void setO_spec_price(String o_spec_price) {
                    this.o_spec_price = o_spec_price;
                }

                public String getSpec_price() {
                    return spec_price;
                }

                public void setSpec_price(String spec_price) {
                    this.spec_price = spec_price;
                }

                public String getCur_spec_stock() {
                    return cur_spec_stock;
                }

                public void setCur_spec_stock(String cur_spec_stock) {
                    this.cur_spec_stock = cur_spec_stock;
                }
                @Override
                public String toString() {
                    return "SpecListBean{" +
                            "id='" + id + '\'' +
                            ", goods_spec='" + goods_spec + '\'' +
                            ", o_spec_price='" + o_spec_price + '\'' +
                            ", spec_price='" + spec_price + '\'' +
                            ", cur_spec_stock='" + cur_spec_stock + '\'' +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "GoodsListBean{" +
                        "tag=" + tag +
                        ", id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", goods_img='" + goods_img + '\'' +
                        ", price='" + price + '\'' +
                        ", supply_price='" + supply_price + '\'' +
                        ", unit='" + unit + '\'' +
                        ", stock='" + stock + '\'' +
                        ", box_num='" + box_num + '\'' +
                        ", box_price='" + box_price + '\'' +
                        ", is_spec=" + is_spec +
                        ", huafan='" + huafan + '\'' +
                        ", spec_list=" + spec_list +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "ListsBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", num='" + num + '\'' +
                    ", goods_list=" + goods_list +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "O2oIndexBean{" +
                "merch_name='" + merch_name + '\'' +
                ", logo_url='" + logo_url + '\'' +
                ", distrib_money='" + distrib_money + '\'' +
                ", distrib_quota='" + distrib_quota + '\'' +
                ", is_close=" + is_close +
                ", id='" + id + '\'' +
                ", business_name='" + business_name + '\'' +
                ", lists=" + lists +
                '}';
    }
}

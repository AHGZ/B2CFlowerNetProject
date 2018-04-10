package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2018/1/31.
 * by--查看更多比价列表数据
 */

public class CompareListBean implements Serializable {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * name : 京东
         * logo : jd.png
         * goods : [{"id":"1","spec_id":"1","plat":"1","item_id":"0","goods_name":"水电费","price":"11.00","eval_num":"0","eval_url":"11","img_url":"11","url":"11","updated":"1","created":"1"},{"id":"2","spec_id":"1","plat":"1","item_id":"0","goods_name":"水电费","price":"11.00","eval_num":"0","eval_url":"11","img_url":"11","url":"11","updated":"1","created":"1"}]
         */

        private String name;
        private String logo;
        private List<GoodsBean> goods;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * id : 1
             * spec_id : 1
             * plat : 1
             * item_id : 0
             * goods_name : 水电费
             * price : 11.00
             * eval_num : 0
             * eval_url : 11
             * img_url : 11
             * url : 11
             * updated : 1
             * created : 1
             */

            private String id;
            private String spec_id;
            private String plat;
            private String item_id;
            private String goods_name;
            private String price;
            private String eval_num;
            private String eval_url;
            private String img_url;
            private String url;
            private String updated;
            private String created;
            private String spec;

            public String getSpec() {
                return spec;
            }

            public void setSpec(String spec) {
                this.spec = spec;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSpec_id() {
                return spec_id;
            }

            public void setSpec_id(String spec_id) {
                this.spec_id = spec_id;
            }

            public String getPlat() {
                return plat;
            }

            public void setPlat(String plat) {
                this.plat = plat;
            }

            public String getItem_id() {
                return item_id;
            }

            public void setItem_id(String item_id) {
                this.item_id = item_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getEval_num() {
                return eval_num;
            }

            public void setEval_num(String eval_num) {
                this.eval_num = eval_num;
            }

            public String getEval_url() {
                return eval_url;
            }

            public void setEval_url(String eval_url) {
                this.eval_url = eval_url;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
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
        }
    }
}

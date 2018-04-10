package com.android.p2pflowernet.project.entity;

import java.util.List;

/**
 * 作者：Yzp on 2017-04-14 11:01
 * 邮箱：15111424807@163.com
 * QQ: 486492302
 */
public class DateModle {

    private List<GoodsTypesBean> goods_types;

    public List<GoodsTypesBean> getGoods_types() {
        return goods_types;
    }

    public void setGoods_types(List<GoodsTypesBean> goods_types) {
        this.goods_types = goods_types;
    }

    public static class GoodsTypesBean {
        /**
         * attr_list : [{"attr_value":"S"},{"attr_value":"M"},{"attr_value":"L"},{"attr_value":"XL"},{"attr_value":"XXL"}]
         * attr_name : 尺码
         */

        private String attr_name;
        private List<AttrListBean> attr_list;

        public String getAttr_name() {
            return attr_name;
        }

        public void setAttr_name(String attr_name) {
            this.attr_name = attr_name;
        }

        public List<AttrListBean> getAttr_list() {
            return attr_list;
        }

        public void setAttr_list(List<AttrListBean> attr_list) {
            this.attr_list = attr_list;
        }

        public static class AttrListBean {
            /**
             * attr_value : S
             */

            private String attr_value;
            private String img_url;

            public String getAttr_value() {
                return attr_value;
            }

            public void setAttr_value(String attr_value) {
                this.attr_value = attr_value;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }
        }
    }
}

package com.android.p2pflowernet.project.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * author: zhangpeisen
 * created on: 2017/12/6 下午2:27
 * description: 商品属性实体类
 */
public class GoodsAttrBean implements Serializable {
    /**
     * lists : [{"attr_name":"颜色","attr_value":[{"id":"3","option_name":"黑色010","is_default":0},{"id":"4","option_name":"黑色88736","is_default":0}]},{"attr_name":"尺码","attr_value":[{"id":"5","option_name":"L/170","is_default":0},{"id":"6","option_name":"XL/175","is_default":0},{"id":"7","option_name":"XXL/180","is_default":0},{"id":"8","option_name":"3XL/185","is_default":0},{"id":"9","option_name":"4XL/190","is_default":0}]}]
     * img : business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg
     * stock : 5
     * sale_price : 340.00
     */
    private String img;
    private String stock;
    private String sale_price;
    /**
     * attr_name : 颜色
     * attr_value : [{"id":"3","option_name":"黑色010","is_default":0},{"id":"4","option_name":"黑色88736","is_default":0}]
     */

    private List<ListsBean> lists;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public static class ListsBean implements Serializable {
        private String attr_name;
        /**
         * id : 3
         * option_name : 黑色010
         * is_default : 0
         */

        private List<AttrValueBean> attr_value;

        public String getAttr_name() {
            return attr_name;
        }

        public void setAttr_name(String attr_name) {
            this.attr_name = attr_name;
        }

        public List<AttrValueBean> getAttr_value() {
            return attr_value;
        }

        public void setAttr_value(List<AttrValueBean> attr_value) {
            this.attr_value = attr_value;
        }

        public static class AttrValueBean implements Serializable, Parcelable {
            private String id;
            private String option_name;
            private int is_default;
            private boolean isSelected = false;

            public AttrValueBean() {
            }

            public AttrValueBean(String id, String option_name, int is_default) {
                this.id = id;
                this.option_name = option_name;
                this.is_default = is_default;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOption_name() {
                return option_name;
            }

            public void setOption_name(String option_name) {
                this.option_name = option_name;
            }

            public int getIs_default() {
                return is_default;
            }

            public void setIs_default(int is_default) {
                this.is_default = is_default;
            }

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }

            @Override
            public String toString() {
                return "AttrValueBean{" +
                        "id='" + id + '\'' +
                        ", option_name='" + option_name + '\'' +
                        ", is_default=" + is_default +
                        '}';
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.option_name);
                dest.writeInt(this.is_default);
            }

            protected AttrValueBean(Parcel in) {
                this.id = in.readString();
                this.option_name = in.readString();
                this.is_default = in.readInt();
            }

            public static final Creator<AttrValueBean> CREATOR = new Creator<AttrValueBean>() {
                @Override
                public AttrValueBean createFromParcel(Parcel source) {
                    return new AttrValueBean(source);
                }

                @Override
                public AttrValueBean[] newArray(int size) {
                    return new AttrValueBean[size];
                }
            };

        }

        @Override
        public String toString() {
            return "ListsBean{" +
                    "attr_name='" + attr_name + '\'' +
                    ", attr_value=" + attr_value +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GoodsAttrBean{" +
                "img='" + img + '\'' +
                ", stock='" + stock + '\'' +
                ", sale_price='" + sale_price + '\'' +
                ", lists=" + lists +
                '}';
    }
}

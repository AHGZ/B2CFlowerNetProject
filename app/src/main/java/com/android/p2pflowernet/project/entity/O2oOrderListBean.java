package com.android.p2pflowernet.project.entity;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;

import com.android.p2pflowernet.project.o2omain.utils.ViewUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class O2oOrderListBean implements Serializable {


    private List<DataEntity> data;

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * goodscatrgory : {"goodsitem":[{"name":"苹果","price":10,"introduce":"苹果好吃啊，很甜！","moreStandard":false},{"name":"香蕉","price":10,"introduce":"香蕉好吃啊，又大很甜！","moreStandard":false},{"name":"橘子","price":10,"introduce":"橘子非常好吃啊，很甜！","moreStandard":false},{"name":"榴莲","price":10,"introduce":"我对象喜欢吃榴莲！","moreStandard":false},{"name":"桃子","price":10,"introduce":"大龙家的桃子就是好吃！","moreStandard":false},{"name":"橘子","price":10,"introduce":"橘子非常好吃啊，很甜！","moreStandard":false},{"name":"梨","price":10,"introduce":"梨非常好吃啊，很甜！","moreStandard":false}],"name":"水果"}
         */

        private GoodscatrgoryEntity goodscatrgory;

        public GoodscatrgoryEntity getGoodscatrgory() {
            return goodscatrgory;
        }

        public void setGoodscatrgory(GoodscatrgoryEntity goodscatrgory) {
            this.goodscatrgory = goodscatrgory;
        }

        public static class GoodscatrgoryEntity {
            /**
             * goodsitem : [{"name":"苹果","price":10,"introduce":"苹果好吃啊，很甜！","moreStandard":false},{"name":"香蕉","price":10,"introduce":"香蕉好吃啊，又大很甜！","moreStandard":false},{"name":"橘子","price":10,"introduce":"橘子非常好吃啊，很甜！","moreStandard":false},{"name":"榴莲","price":10,"introduce":"我对象喜欢吃榴莲！","moreStandard":false},{"name":"桃子","price":10,"introduce":"大龙家的桃子就是好吃！","moreStandard":false},{"name":"橘子","price":10,"introduce":"橘子非常好吃啊，很甜！","moreStandard":false},{"name":"梨","price":10,"introduce":"梨非常好吃啊，很甜！","moreStandard":false}]
             * name : 水果
             */

            private String name;
            private int bugNum;
            private List<GoodsitemEntity> goodsitem;

            public String getName() {
                return name;
            }

            public int getBugNum() {
                return bugNum;
            }

            public void setBugNum(int bugNum) {
                this.bugNum = bugNum;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<GoodsitemEntity> getGoodsitem() {
                return goodsitem;
            }

            public void setGoodsitem(List<GoodsitemEntity> goodsitem) {
                this.goodsitem = goodsitem;
            }

            public static class GoodsitemEntity {
                /**
                 * id：1
                 * name : 苹果
                 * price : 10
                 * introduce : 苹果好吃啊，很甜！
                 * goodsImgUrl : ""
                 * moreStandard : false
                 */
                private int id;
                private String name;
                private int price;
                private String introduce;
                private String goodsImgUrl;
                private boolean moreStandard;
                private BigDecimal bigDecimalPrice;
                private long selectCount;
                private String type;//类

                public long getSelectCount() {
                    return selectCount;
                }

                public void setSelectCount(long selectCount) {
                    this.selectCount = selectCount;
                }

                public BigDecimal getBigDecimalPrice() {
                    return bigDecimalPrice;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getPrice() {
                    return price;
                }

                public void setPrice(int price) {
                    this.price = price;
                }

                public String getIntroduce() {
                    return introduce;
                }

                public void setIntroduce(String introduce) {
                    this.introduce = introduce;
                }

                public String getGoodsImgUrl() {
                    return goodsImgUrl;
                }

                public void setGoodsImgUrl(String goodsImgUrl) {
                    this.goodsImgUrl = goodsImgUrl;
                }

                public boolean isMoreStandard() {
                    return moreStandard;
                }

                public void setMoreStandard(boolean moreStandard) {
                    this.moreStandard = moreStandard;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public SpannableString getStrPrice(Context context) {
                    String priceStr = String.valueOf(getBigDecimalPrice());
                    SpannableString spanString = new SpannableString("¥" + priceStr);
                    AbsoluteSizeSpan span = new AbsoluteSizeSpan(ViewUtils.sp2px(context, 11));
                    spanString.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    return spanString;
                }

                public SpannableString getStrPrice(Context context, BigDecimal price) {
                    String priceStr = String.valueOf(price);
                    SpannableString spanString = new SpannableString("¥" + priceStr);
                    AbsoluteSizeSpan span = new AbsoluteSizeSpan(ViewUtils.sp2px(context, 11));
                    spanString.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    return spanString;
                }

            }
        }
    }
}

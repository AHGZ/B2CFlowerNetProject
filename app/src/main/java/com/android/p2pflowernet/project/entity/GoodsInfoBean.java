package com.android.p2pflowernet.project.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author: zhangpeisen
 * created on: 2017/12/6 上午10:29
 * description: 商品信息实体类
 */
public class GoodsInfoBean implements Serializable {
    /**
     * id : 3
     * unit : 件
     * goods_name : 小米男装
     * is_noreason : 1
     * is_certified : 1
     * attr_id1 : 1234
     * attr_id2 : 1235
     * attr_id3 : 0
     * market_price : 330.00
     * goods_spec : 红色 160
     * intro : 男装
     * short_title : 上衣
     * invoice_state : 1
     * invoice_rate : 0.0000
     * manufac_id : 10088
     * sale_price : 340.00
     * supply_price : 200.00
     * goods_img : 17,19,20
     * spec_id : 2
     * name : test1
     * manufac_type : 1
     * all : 100
     * new : 50
     * desc : 1
     * seller : 4.0
     * logistics : 5.0
     * manufac_logo : business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg
     * huafan : 140.00
     * partner : 112.00
     * agent : 70.00
     * tuijian : 70.00
     * putong : 70.00
     * gaoji : 84.00
     * guige : 颜色 尺码
     * zt : 支持7天无理由退货
     * xqurl : http://www.baidu.com
     * imgurl : ["business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg","business_auth_img/20171123/a83dd456d520aa3ac47ac0bff99f6b7e.jpg","business_auth_img/20171123/31f2fcb5acce00a9c2c305df4bd057fe.jpg"]
     * kuaidi : 0.00
     * ysales : 2345
     * dizhi : 北京
     * count : 100
     * is_partner : 1
     * is_agent : 1
     */

    private GoodsBean goods;
    /**
     * id : 2
     * user_id : 8
     * manufac_id : 2
     * goods_id : 3
     * goods_spec : 白色
     * is_anonymous : 0
     * goods_desc_score : 4
     * content : 46546dasda城市调查粉色的发生的
     * is_reply : 1
     * reply_content : 打撒付多撒大所大所大所大所多
     * reply_id : 0
     * reply_time : 2017-12-01
     * buy_time : 2017-12-01
     * created : 2017-12-04
     * header_img : business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg
     * nickname : 地方法规
     * img_lists : ["business_auth_img/20171123/a83dd456d520aa3ac47ac0bff99f6b7e.jpg","business_auth_img/20171123/31f2fcb5acce00a9c2c305df4bd057fe.jpg","business_auth_img/20171123/5eae8db5b9563d3bd2bbfa9e025dfd13.jpg"]
     */

    private List<ListsBean> lists;

    public GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsBean goods) {
        this.goods = goods;
    }

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    private List<CompareBean> compare;

    public List<CompareBean> getCompare() {
        return compare;
    }

    public void setCompare(List<CompareBean> compare) {
        this.compare = compare;
    }

    /**
     * 比价
     */
    public static class CompareBean implements Serializable{


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



    public static class GoodsBean implements Serializable{
        private String id;
        private String unit;
        private String goods_name;
        private String is_noreason;
        private String is_certified;
        private String attr_id1;
        private String attr_id2;
        private String attr_id3;
        private String market_price;
        private String goods_spec;
        private String intro;
        private String short_title;
        private String invoice_state;
        private String invoice_rate;
        private String manufac_id;
        private String sale_price;
        private String supply_price;
        private String goods_img;
        private String spec_id;
        private String name;
        private String manufac_type;
        private String all;
        @SerializedName("new")
        private String newX;
        private String desc;
        private String seller;
        private String logistics;
        private String manufac_logo;
        private String huafan;
        private String partner;
        private String agent;
        private String tuijian;
        private String putong;
        private String gaoji;
        private String guige;
        private String zt;
        private String xqurl;
        private String kuaidi;
        private int ysales;
        private String dizhi;
        private String count;
        private String is_partner;
        private String is_agent;
        private ArrayList<String> imgurl;
        private String description;

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getIs_noreason() {
            return is_noreason;
        }

        public void setIs_noreason(String is_noreason) {
            this.is_noreason = is_noreason;
        }

        public String getIs_certified() {
            return is_certified;
        }

        public void setIs_certified(String is_certified) {
            this.is_certified = is_certified;
        }

        public String getAttr_id1() {
            return attr_id1;
        }

        public void setAttr_id1(String attr_id1) {
            this.attr_id1 = attr_id1;
        }

        public String getAttr_id2() {
            return attr_id2;
        }

        public void setAttr_id2(String attr_id2) {
            this.attr_id2 = attr_id2;
        }

        public String getAttr_id3() {
            return attr_id3;
        }

        public void setAttr_id3(String attr_id3) {
            this.attr_id3 = attr_id3;
        }

        public String getMarket_price() {
            return market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public String getGoods_spec() {
            return goods_spec;
        }

        public void setGoods_spec(String goods_spec) {
            this.goods_spec = goods_spec;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getShort_title() {
            return short_title;
        }

        public void setShort_title(String short_title) {
            this.short_title = short_title;
        }

        public String getInvoice_state() {
            return invoice_state;
        }

        public void setInvoice_state(String invoice_state) {
            this.invoice_state = invoice_state;
        }

        public String getInvoice_rate() {
            return invoice_rate;
        }

        public void setInvoice_rate(String invoice_rate) {
            this.invoice_rate = invoice_rate;
        }

        public String getManufac_id() {
            return manufac_id;
        }

        public void setManufac_id(String manufac_id) {
            this.manufac_id = manufac_id;
        }

        public String getSale_price() {
            return sale_price;
        }

        public void setSale_price(String sale_price) {
            this.sale_price = sale_price;
        }

        public String getSupply_price() {
            return supply_price;
        }

        public void setSupply_price(String supply_price) {
            this.supply_price = supply_price;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        public String getSpec_id() {
            return spec_id;
        }

        public void setSpec_id(String spec_id) {
            this.spec_id = spec_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getManufac_type() {
            return manufac_type;
        }

        public void setManufac_type(String manufac_type) {
            this.manufac_type = manufac_type;
        }

        public String getAll() {
            return all;
        }

        public void setAll(String all) {
            this.all = all;
        }

        public String getNewX() {
            return newX;
        }

        public void setNewX(String newX) {
            this.newX = newX;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getSeller() {
            return seller;
        }

        public void setSeller(String seller) {
            this.seller = seller;
        }

        public String getLogistics() {
            return logistics;
        }

        public void setLogistics(String logistics) {
            this.logistics = logistics;
        }

        public String getManufac_logo() {
            return manufac_logo;
        }

        public void setManufac_logo(String manufac_logo) {
            this.manufac_logo = manufac_logo;
        }

        public String getHuafan() {
            return huafan;
        }

        public void setHuafan(String huafan) {
            this.huafan = huafan;
        }

        public String getPartner() {
            return partner;
        }

        public void setPartner(String partner) {
            this.partner = partner;
        }

        public String getAgent() {
            return agent;
        }

        public void setAgent(String agent) {
            this.agent = agent;
        }

        public String getTuijian() {
            return tuijian;
        }

        public void setTuijian(String tuijian) {
            this.tuijian = tuijian;
        }

        public String getPutong() {
            return putong;
        }

        public void setPutong(String putong) {
            this.putong = putong;
        }

        public String getGaoji() {
            return gaoji;
        }

        public void setGaoji(String gaoji) {
            this.gaoji = gaoji;
        }

        public String getGuige() {
            return guige;
        }

        public void setGuige(String guige) {
            this.guige = guige;
        }

        public String getZt() {
            return zt;
        }

        public void setZt(String zt) {
            this.zt = zt;
        }

        public String getXqurl() {
            return xqurl;
        }

        public void setXqurl(String xqurl) {
            this.xqurl = xqurl;
        }

        public String getKuaidi() {
            return kuaidi;
        }

        public void setKuaidi(String kuaidi) {
            this.kuaidi = kuaidi;
        }

        public int getYsales() {
            return ysales;
        }

        public void setYsales(int ysales) {
            this.ysales = ysales;
        }

        public String getDizhi() {
            return dizhi;
        }

        public void setDizhi(String dizhi) {
            this.dizhi = dizhi;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getIs_partner() {
            return is_partner;
        }

        public void setIs_partner(String is_partner) {
            this.is_partner = is_partner;
        }

        public String getIs_agent() {
            return is_agent;
        }

        public void setIs_agent(String is_agent) {
            this.is_agent = is_agent;
        }

        public ArrayList<String> getImgurl() {
            return imgurl;
        }

        public void setImgurl(ArrayList<String> imgurl) {
            this.imgurl = imgurl;
        }

        @Override
        public String toString() {
            return "GoodsBean{" +
                    "id='" + id + '\'' +
                    ", unit='" + unit + '\'' +
                    ", goods_name='" + goods_name + '\'' +
                    ", is_noreason='" + is_noreason + '\'' +
                    ", is_certified='" + is_certified + '\'' +
                    ", attr_id1='" + attr_id1 + '\'' +
                    ", attr_id2='" + attr_id2 + '\'' +
                    ", attr_id3='" + attr_id3 + '\'' +
                    ", market_price='" + market_price + '\'' +
                    ", goods_spec='" + goods_spec + '\'' +
                    ", intro='" + intro + '\'' +
                    ", short_title='" + short_title + '\'' +
                    ", invoice_state='" + invoice_state + '\'' +
                    ", invoice_rate='" + invoice_rate + '\'' +
                    ", manufac_id='" + manufac_id + '\'' +
                    ", sale_price='" + sale_price + '\'' +
                    ", supply_price='" + supply_price + '\'' +
                    ", goods_img='" + goods_img + '\'' +
                    ", spec_id='" + spec_id + '\'' +
                    ", name='" + name + '\'' +
                    ", manufac_type='" + manufac_type + '\'' +
                    ", all='" + all + '\'' +
                    ", newX=" + newX +
                    ", desc='" + desc + '\'' +
                    ", seller='" + seller + '\'' +
                    ", logistics='" + logistics + '\'' +
                    ", manufac_logo='" + manufac_logo + '\'' +
                    ", huafan='" + huafan + '\'' +
                    ", partner='" + partner + '\'' +
                    ", agent='" + agent + '\'' +
                    ", tuijian='" + tuijian + '\'' +
                    ", putong='" + putong + '\'' +
                    ", gaoji='" + gaoji + '\'' +
                    ", guige='" + guige + '\'' +
                    ", zt='" + zt + '\'' +
                    ", xqurl='" + xqurl + '\'' +
                    ", kuaidi='" + kuaidi + '\'' +
                    ", ysales=" + ysales +
                    ", dizhi='" + dizhi + '\'' +
                    ", count='" + count + '\'' +
                    ", is_partner='" + is_partner + '\'' +
                    ", is_agent='" + is_agent + '\'' +
                    ", imgurl=" + imgurl +
                    '}';
        }

        public String getDescription() {
            return description;
        }
    }
    @Override
    public String toString() {
        return "GoodsInfoBean{" +
                "goods=" + goods +
                ", lists=" + lists +
                '}';
    }
}

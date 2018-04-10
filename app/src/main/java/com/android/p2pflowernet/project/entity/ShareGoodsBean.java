package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by caishen on 2018/1/29.
 * by--商品分享详情
 */

public class ShareGoodsBean implements Serializable{


    /**
     * id : 1
     * title : 红豆（Hodo）男装
     * intro : 男新款秋冬修身平驳领可拆卸内里羊毛混纺毛呢大衣 B5藏青 185/100A
     * goods_img : 250,251
     * img : img/o2o_good/2018/01-03/d74038ae515a50e5a3a88d179f9041da.jpg
     * share_url : http://192.168.1.253:8087/detail/1.html
     */

    private String id;
    private String title;
    private String intro;
    private String goods_img;
    private String img;
    private String share_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }
}

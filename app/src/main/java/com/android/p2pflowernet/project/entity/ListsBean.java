package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * author: zhangpeisen
 * created on: 2017/12/6 下午1:27
 * description: 评价实体类
 */
public class ListsBean implements Serializable {
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
     * imgurl : ["business_auth_img/20171123/a83dd456d520aa3ac47ac0bff99f6b7e.jpg","business_auth_img/20171123/31f2fcb5acce00a9c2c305df4bd057fe.jpg","business_auth_img/20171123/5eae8db5b9563d3bd2bbfa9e025dfd13.jpg"]
     */

    private String id;
    private String user_id;
    private String manufac_id;
    private String goods_id;
    private String goods_spec;
    private String is_anonymous;
    private String goods_desc_score;
    private String content;
    private String is_reply;
    private String reply_content;
    private String reply_id;
    private String reply_time;
    private String buy_time;
    private String created;
    private String header_img;
    private String nickname;


    private ArrayList<String> img_lists;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getManufac_id() {
        return manufac_id;
    }

    public void setManufac_id(String manufac_id) {
        this.manufac_id = manufac_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_spec() {
        return goods_spec;
    }

    public void setGoods_spec(String goods_spec) {
        this.goods_spec = goods_spec;
    }

    public String getIs_anonymous() {
        return is_anonymous;
    }

    public void setIs_anonymous(String is_anonymous) {
        this.is_anonymous = is_anonymous;
    }

    public String getGoods_desc_score() {
        return goods_desc_score;
    }

    public void setGoods_desc_score(String goods_desc_score) {
        this.goods_desc_score = goods_desc_score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIs_reply() {
        return is_reply;
    }

    public void setIs_reply(String is_reply) {
        this.is_reply = is_reply;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public String getReply_id() {
        return reply_id;
    }

    public void setReply_id(String reply_id) {
        this.reply_id = reply_id;
    }

    public String getReply_time() {
        return reply_time;
    }

    public void setReply_time(String reply_time) {
        this.reply_time = reply_time;
    }

    public String getBuy_time() {
        return buy_time;
    }

    public void setBuy_time(String buy_time) {
        this.buy_time = buy_time;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getHeader_img() {
        return header_img;
    }

    public void setHeader_img(String header_img) {
        this.header_img = header_img;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public ArrayList<String> getImg_lists() {
        return img_lists;
    }

    public void setImg_lists(ArrayList<String> img_lists) {
        this.img_lists = img_lists;
    }

    @Override
    public String toString() {
        return "ListsBean{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", manufac_id='" + manufac_id + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", goods_spec='" + goods_spec + '\'' +
                ", is_anonymous='" + is_anonymous + '\'' +
                ", goods_desc_score='" + goods_desc_score + '\'' +
                ", content='" + content + '\'' +
                ", is_reply='" + is_reply + '\'' +
                ", reply_content='" + reply_content + '\'' +
                ", reply_id='" + reply_id + '\'' +
                ", reply_time='" + reply_time + '\'' +
                ", buy_time='" + buy_time + '\'' +
                ", created='" + created + '\'' +
                ", header_img='" + header_img + '\'' +
                ", nickname='" + nickname + '\'' +
                ", img_lists=" + img_lists +
                '}';
    }
}

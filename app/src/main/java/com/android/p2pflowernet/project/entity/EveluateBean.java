package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2017/12/5.
 * by--商品详情评价适配器
 */

public class EveluateBean implements Serializable {
    /**
     * count : 1
     * lists : [{"id":"2","user_id":"8","manufac_id":"2","goods_id":"3","goods_spec":"1","is_anonymous":"0","goods_desc_score":"4","content":"46546dasda城市调查粉色的发生的","is_reply":"1","reply_content":"打撒付多撒大所大所大所大所多","reply_id":"0","reply_time":"2017-12-01","buy_time":"2017-12-01","created":"2017-12-04","header_img":"business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg","nickname":"地方法规","img_lists":["business_auth_img/20171123/a83dd456d520aa3ac47ac0bff99f6b7e.jpg","business_auth_img/20171123/31f2fcb5acce00a9c2c305df4bd057fe.jpg","business_auth_img/20171123/5eae8db5b9563d3bd2bbfa9e025dfd13.jpg"]},{"id":"1","user_id":"3","manufac_id":"1","goods_id":"3","goods_spec":"1","is_anonymous":"2","goods_desc_score":"3","content":"46546dasda城市调查粉色的发生的","is_reply":"1","reply_content":"打撒付多撒大所大所大所大所多","reply_id":"0","reply_time":"2017-12-01","buy_time":"2017-12-01","created":"2017-12-04","header_img":"","nickname":"匿名用户","img_lists":["business_auth_img/20171123/2ba5a515b07911480b566990650457ea.jpg","business_auth_img/20171123/b5ec344648ceb024eed4628c78ddc16f.jpg","business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg"]}]
     */
    private int count;
    private String all_num;
    private String good_num;
    private String general_num;
    private String neg_num;
    private String pic_eval_num;

    public String getAll_num() {
        return all_num;
    }

    public void setAll_num(String all_num) {
        this.all_num = all_num;
    }

    public String getGood_num() {
        return good_num;
    }

    public void setGood_num(String good_num) {
        this.good_num = good_num;
    }

    public String getGeneral_num() {
        return general_num;
    }

    public void setGeneral_num(String general_num) {
        this.general_num = general_num;
    }

    public String getNeg_num() {
        return neg_num;
    }

    public void setNeg_num(String neg_num) {
        this.neg_num = neg_num;
    }

    public String getPic_eval_num() {
        return pic_eval_num;
    }

    public void setPic_eval_num(String pic_eval_num) {
        this.pic_eval_num = pic_eval_num;
    }

    private List<ListsBean> lists;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }


    @Override
    public String toString() {
        return "EveluateBean{" +
                "count=" + count +
                ", lists=" + lists +
                '}';
    }
}

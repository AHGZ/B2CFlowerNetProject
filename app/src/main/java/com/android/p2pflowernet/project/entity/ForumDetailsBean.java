package com.android.p2pflowernet.project.entity;

/**
 * Created by Administrator on 2018/1/29.
 */

public class ForumDetailsBean {

    /**
     * video_url : o2o_good/2018/01-03/3b27acd6a7f856cad3835dfbc61eecae.jpg
     * content : 鸡毛鸡毛鸡毛鸡毛鸡毛鸡毛鸡毛鸡毛鸡毛
     */

    private String video_url;
    private String content;
    private int page_count;

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

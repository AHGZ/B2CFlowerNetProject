package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by caishen on 2018/2/1.
 * by--获取广告播放权限
 */

public class SplashBean implements Serializable{


    /**
     * id : 521
     * show_mode : 1
     * starttime : 1517472421
     * endtime : 1525104000
     * is_enable : 1
     * show_img_id : 703
     * link_url : http://www.baidu.com
     * show_length : 5
     * note :
     * created : 1517472421
     * file_path : img/o2o_good/2018/01-30/3a21c4910795c5f6a4bb07107e625332.jpg
     */

    private String id;
    private String show_mode;
    private String starttime;
    private String endtime;
    private String is_enable;
    private String show_img_id;
    private String link_url;
    private int show_length;
    private String note;
    private String created;
    private String file_path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShow_mode() {
        return show_mode;
    }

    public void setShow_mode(String show_mode) {
        this.show_mode = show_mode;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getIs_enable() {
        return is_enable;
    }

    public void setIs_enable(String is_enable) {
        this.is_enable = is_enable;
    }

    public String getShow_img_id() {
        return show_img_id;
    }

    public void setShow_img_id(String show_img_id) {
        this.show_img_id = show_img_id;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public int getShow_length() {
        return show_length;
    }

    public void setShow_length(int show_length) {
        this.show_length = show_length;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}

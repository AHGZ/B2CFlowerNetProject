package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by caishen on 2018/1/29.
 * by--邀请分享
 */

public class ShareCodeBean implements Serializable{


    /**
     * title : 您的好友邀请您注册“花返网”一起开启花返之旅
     * intro : 验证手机号，和您的朋友一起分享购物利润，这是我们之间的秘密，千万别让其他人知道！！！
     * img : null
     * share_url : http://192.168.1.253:8087/share/index.html?code=bbbbbb
     */

    private String title;
    private String intro;
    private String img;
    private String share_url;

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

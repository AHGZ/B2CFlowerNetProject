package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by caishen on 2017/12/20.
 * by--云工办公
 */

public class CloudOfficeBean implements Serializable {


    /**
     * user : 3
     * Partner : 0
     */

    private int user;//会员
    private int Partner;//合伙人

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getPartner() {
        return Partner;
    }

    public void setPartner(int Partner) {
        this.Partner = Partner;
    }
}

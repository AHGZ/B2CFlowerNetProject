package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by caishen on 2017/12/22.
 * by--成长计划（云工）
 */

public class CloudGrowBean implements Serializable{


    private String Copper;
    private String Silver;
    private String Gold;
    private String Diamonds;

    public String getCopper() {
        return Copper;
    }

    public void setCopper(String copper) {
        Copper = copper;
    }

    public String getSilver() {
        return Silver;
    }

    public void setSilver(String silver) {
        Silver = silver;
    }

    public String getGold() {
        return Gold;
    }

    public void setGold(String gold) {
        Gold = gold;
    }

    public String getDiamonds() {
        return Diamonds;
    }

    public void setDiamonds(String diamonds) {
        Diamonds = diamonds;
    }
}

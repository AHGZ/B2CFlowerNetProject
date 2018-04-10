package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by caishen on 2017/12/22.
 * by--成长计划（代理）
 */

public class AgentGrowBean implements Serializable{

    private String Copper;
    private String Silver;
    private String Gold;

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
}

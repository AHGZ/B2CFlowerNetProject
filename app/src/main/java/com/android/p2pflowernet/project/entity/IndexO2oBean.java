package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by caishen on 2017/12/28.
 * by--生活首页
 */

public class IndexO2oBean implements Serializable{


    private String nickname;
    private int star;
    private String qis;
    private String peis;
    private String rj;
    private String minue;
    private String distance;
    private String ativity;
    private String height;
    private String yues;

    public String getYues() {
        return yues;
    }

    public void setYues(String yues) {
        this.yues = yues;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getQis() {
        return qis;
    }

    public void setQis(String qis) {
        this.qis = qis;
    }

    public String getPeis() {
        return peis;
    }

    public void setPeis(String peis) {
        this.peis = peis;
    }

    public String getRj() {
        return rj;
    }

    public void setRj(String rj) {
        this.rj = rj;
    }

    public String getMinue() {
        return minue;
    }

    public void setMinue(String minue) {
        this.minue = minue;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAtivity() {
        return ativity;
    }

    public void setAtivity(String ativity) {
        this.ativity = ativity;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "IndexO2oBean{" +
                "nickname='" + nickname + '\'' +
                ", star=" + star +
                ", qis='" + qis + '\'' +
                ", peis='" + peis + '\'' +
                ", rj='" + rj + '\'' +
                ", minue='" + minue + '\'' +
                ", distance='" + distance + '\'' +
                ", ativity='" + ativity + '\'' +
                ", height='" + height + '\'' +
                ", yues='" + yues + '\'' +
                '}';
    }
}

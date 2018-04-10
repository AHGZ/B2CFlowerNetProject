package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by caishen on 2017/11/13.
 * by--
 */

public class Region implements Serializable{

    String region_name;

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    @Override
    public String toString() {
        return "Region{" +
                "region_name='" + region_name + '\'' +
                '}';
    }
}

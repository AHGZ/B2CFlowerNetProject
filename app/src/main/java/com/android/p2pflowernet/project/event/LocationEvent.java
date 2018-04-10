package com.android.p2pflowernet.project.event;

/**
 * author: zhangpeisen
 * created on: 2017/10/10 上午11:22
 * description:定位信息
 */
public class LocationEvent {
    public String location;
    public String longitude;
    public String latitude;

    public LocationEvent(String location, String longitude, String latitude) {
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}

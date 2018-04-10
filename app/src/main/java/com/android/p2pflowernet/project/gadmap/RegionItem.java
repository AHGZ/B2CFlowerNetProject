package com.android.p2pflowernet.project.gadmap;

import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.android.p2pflowernet.project.R;

/**
 * Created by zhangkun on 2018/2/7.
 */

public class RegionItem implements ClusterItem {

    private LatLng mLatLng;
    private String mTitle;
    private int type;

    public RegionItem(LatLng latLng, String title, int type) {
        this.mLatLng = latLng;
        this.mTitle = title;
        this.type = type;
    }

    @Override
    public LatLng getPosition() {
        return mLatLng;
    }

    @Override
    public BitmapDescriptor getBitmapDescriptor() {

        if (type == 11) {
            return BitmapDescriptorFactory.fromResource(R.mipmap.icon_hy_dw);
        } else if (type == 12) {
            return BitmapDescriptorFactory.fromResource(R.mipmap.icon_dlr_dw);
        } else if (type == 13) {
            return BitmapDescriptorFactory.fromResource(R.mipmap.icon_ghs_dw);
        } else if (type == 14) {
            return BitmapDescriptorFactory.fromResource(R.mipmap.icon_sj_dw);
        } else if (type == 15) {
            return BitmapDescriptorFactory.fromResource(R.mipmap.icon_hhr_dw);
        } else if (type == 16) {
            return BitmapDescriptorFactory.fromResource(R.mipmap.icon_yg_dw);
        } else {
            return BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_gcoding);
        }
    }

    public String getTitle() {
        return mTitle;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

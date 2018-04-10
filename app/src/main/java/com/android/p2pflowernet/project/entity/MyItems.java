package com.android.p2pflowernet.project.entity;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.utils.clusterutil.clustering.ClusterItem;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.model.LatLng;

/**
 * Created by caishen on 2018/2/1.
 * by--
 */

public class MyItems  implements ClusterItem {

    private final LatLng mPosition;
    private final int type;

    public MyItems(LatLng latLng,int type) {
        this.mPosition = latLng;
        this.type = type;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }//返回marker的坐标

    @Override
    public BitmapDescriptor getBitmapDescriptor() {//返回marker的图标
        if (type == 1) {
            return BitmapDescriptorFactory.fromResource(R.mipmap.icon_dlr_dw);
        }else if(type == 2){
            return BitmapDescriptorFactory.fromResource(R.mipmap.icon_ghs_dw);
        }else if(type == 3){
            return BitmapDescriptorFactory.fromResource(R.mipmap.icon_sj_dw);
        }else if(type == 4){
            return BitmapDescriptorFactory.fromResource(R.mipmap.icon_hhr_dw);
        }else if(type == 5){
            return BitmapDescriptorFactory.fromResource(R.mipmap.icon_yg_dw);
        }else if(type == 6){
            return BitmapDescriptorFactory.fromResource(R.mipmap.icon_hy_dw);
        }else{
            return BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_gcoding);
        }
    }
}

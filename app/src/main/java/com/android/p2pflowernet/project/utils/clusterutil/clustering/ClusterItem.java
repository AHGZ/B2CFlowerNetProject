/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */

package com.android.p2pflowernet.project.utils.clusterutil.clustering;


import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.model.LatLng;

/**
 * ClusterItem represents a marker on the activity_course_order_layout.
 */
public interface ClusterItem {

    /**
     * The position of this marker. This must always return_ticket the same value.
     */
    LatLng getPosition();

    BitmapDescriptor getBitmapDescriptor();
}
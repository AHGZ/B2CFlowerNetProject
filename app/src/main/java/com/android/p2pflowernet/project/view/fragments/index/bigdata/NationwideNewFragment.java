package com.android.p2pflowernet.project.view.fragments.index.bigdata;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MultiPointItem;
import com.amap.api.maps.model.MultiPointOverlay;
import com.amap.api.maps.model.MultiPointOverlayOptions;
import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.mvp.KFragment;

import butterknife.BindView;

/**
 * Created by zhangkun on 2018/2/28.
 */

public class NationwideNewFragment extends KFragment<IBigDataShowView,IBigDataShowPrenter> {

    @BindView(R.id.mapView)
    MapView mMapView;
    @BindView(R.id.tv_dlr)
    TextView tv_dlr;
    @BindView(R.id.tv_ghs)
    TextView tv_ghs;
    @BindView(R.id.tv_sj)
    TextView tv_sj;
    @BindView(R.id.tv_hhr)
    TextView tv_hhr;
    @BindView(R.id.tv_yg)
    TextView tv_yg;
    @BindView(R.id.tv_hy)
    TextView tv_hy;

    private AMap aMap;
    boolean isDestroy = false;
    Marker marker = null;

    @Override
    public IBigDataShowPrenter createPresenter() {
        return new IBigDataShowPrenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_nationwide;
    }

    public static Fragment newIntence() {

        NationwideNewFragment nationwideFragment = new NationwideNewFragment();
        Bundle bundle = new Bundle();
        nationwideFragment.setArguments(bundle);
        return nationwideFragment;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);

        if (aMap == null) {
            // 初始化地图
            aMap = mMapView.getMap();

            //添加一个Marker用来展示海量点点击效果
            marker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.marker_blue);

            MultiPointOverlayOptions overlayOptions = new MultiPointOverlayOptions();
            overlayOptions.icon(bitmapDescriptor);
            overlayOptions.anchor(0.5f,0.5f);

            final MultiPointOverlay multiPointOverlay = aMap.addMultiPointOverlay(overlayOptions);
            aMap.setOnMultiPointClickListener(new AMap.OnMultiPointClickListener() {
                @Override
                public boolean onPointClick(MultiPointItem pointItem) {
                    android.util.Log.i("amap ", "onPointClick");

                    if(marker.isRemoved()) {
                        //调用amap clear之后会移除marker，重新添加一个
                        marker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    }
                    //添加一个Marker用来展示海量点点击效果
                    marker.setPosition(pointItem.getLatLng());
                    marker.setToTop();
                    return false;
                }
            });

            aMap.moveCamera(CameraUpdateFactory.zoomTo(3));

            initMapData();
        }
    }

    //添加数据
    private void initMapData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        isDestroy = true;
    }
}

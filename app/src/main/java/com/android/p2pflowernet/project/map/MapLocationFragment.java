package com.android.p2pflowernet.project.map;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.SPUtils;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import butterknife.BindView;

/**
 * @描述:地图定位主页
 * @创建人：zhangpeisen
 * @创建时间：2017/10/30 下午5:40
 * @修改人：zhangpeisen
 * @修改时间：2017/10/30 下午5:40
 * @修改备注：
 * @throws
 */


/**
 * @描述:地图定位主页
 * @创建人：zhangpeisen
 * @创建时间：2017/10/30 下午5:40
 * @修改人：zhangpeisen
 * @修改时间：2017/10/30 下午5:40
 * @修改备注：
 * @throws
 */
public class MapLocationFragment extends KFragment<MapLocationView, MapLocationPrenter> implements SensorEventListener, NormalTopBar.normalTopClickListener, OnGetGeoCoderResultListener, MapLocationView {
    @BindView(R.id.normal_top)
    //自定义通用标题
            NormalTopBar normalTopBar;

    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;
    BitmapDescriptor mCurrentMarker;
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    @BindView(R.id.bmapView)
    MapView mMapView;
    BaiduMap mBaiduMap;
    private MyLocationData locData;
    private GeoCoder geoCoder;
    // 加载进度条
    private ShapeLoadingDialog shapeLoadingDialog;
    // 经度
    private double longitude = 0.00;
    // 纬度
    private double latitude = 0.00;


    public static MapLocationFragment newInstance() {
        Bundle args = new Bundle();
        MapLocationFragment fragment = new MapLocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public MapLocationPrenter createPresenter() {
        return new MapLocationPrenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.storemap_fragment;
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        //初始化沉浸式
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.white));
        // 初始化标题及相关事件监听
        normalTopBar.setTitleText("商家定位");
        normalTopBar.getRightImage().setVisibility(View.GONE);
        // 扩大事件的点击范围
        UIUtils.setTouchDelegate(normalTopBar.getLeftImage(), 50);
        normalTopBar.setTopClickListener(this);
        // 地图初始化
        mBaiduMap = mMapView.getMap();
        geoCoder = GeoCoder.newInstance();
        geoCoder.setOnGetGeoCodeResultListener(this);
        //为系统的方向传感器注册监听器
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);//获取传感器管理服务
        Sensor sensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (null != sensor)
            mSensorManager.registerListener(this, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();
        // 定位初始化
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
        // 修改为自定义marker
        mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_dw);

        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker,
                accuracyCircleFillColor, accuracyCircleStrokeColor));
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.overlook(0);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                LatLng ptCenter = mBaiduMap.getMapStatus().target;
                geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(ptCenter));
            }
        });
    }

    @Override
    public void initData() {


    }

    @Override
    public void onLeftClick(View view) {
        removeFragment();
    }

    @Override
    public void onRightClick(View view) {

    }

    @Override
    public void onTitleClick(View view) {

    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        // 设置地图的中心点
        MapStatusUpdate statusMap = MapStatusUpdateFactory.newLatLng(geoCodeResult.getLocation());
        mBaiduMap.animateMapStatus(statusMap);
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            showShortToast("抱歉,未能找到结果");
            return;
        }
        mBaiduMap.clear();
        // 用来改变地图的状态
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(reverseGeoCodeResult.getLocation()));
        latitude = reverseGeoCodeResult.getLocation().latitude;
        longitude = reverseGeoCodeResult.getLocation().longitude;
//        showShortToast("经纬度:" + reverseGeoCodeResult.getLocation() + "," + "定位地址"
//                + reverseGeoCodeResult.getAddressDetail().city + "," + reverseGeoCodeResult.getAddressDetail().district + ","
//                + reverseGeoCodeResult.getAddressDetail().street);
        SPUtils.put(getActivity(), "latitude", latitude);
        SPUtils.put(getActivity(), "longitude", longitude);
    }


    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(18.0f);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            SPUtils.put(getActivity(), "latitude", mCurrentLat);
            SPUtils.put(getActivity(), "longitude",mCurrentLon);
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onDestroyView() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroyView();
    }

}
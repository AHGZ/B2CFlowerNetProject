package com.android.p2pflowernet.project.o2omain.fragment.index.takeout.location.confirmaddress;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.event.AddLocationEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.fragment.index.takeout.location.AddLocationActivity;
import com.android.p2pflowernet.project.o2omain.fragment.index.takeout.location.alladdress.AllAddressActivity;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangkun on 2018/2/2.
 */

public class ConfirmAddressFragment extends KFragment<IConfirmAddressView, IConfirmAddressPresenter> implements NormalTopBar.normalTopClickListener
        , BaiduMap.OnMapStatusChangeListener, BDLocationListener, OnGetGeoCoderResultListener {

    @BindView(R.id.normal_top)
    NormalTopBar topBar;
    @BindView(R.id.fragment_confirm_address_tv_name)
    TextView tv_name;//定位地方名称
    @BindView(R.id.fragment_confirm_address_tv_detailed)
    TextView tv_detailed;//详细地址
    @BindView(R.id.fragment_confirm_address_tv_sure)
    TextView tv_sure;//确定按钮
    @BindView(R.id.fragment_confirm_address_linearLayout)
    LinearLayout mLinearLayout;//
    @BindView(R.id.fragment_confirm_address_mapView)
    MapView mMapView;

    //定位模式
    private MyLocationConfiguration.LocationMode mCurrentMode;
    //定位端
    private LocationClient mLocClient;
    //是否是第一次定位
    private boolean isFirstLoc = true;
    //定位坐标
    private LatLng locationLatLng;
    //定位城市
    private String city;
    //反地理编码
    private GeoCoder geoCoder;

    private BaiduMap mBaiduMap;
    private List<PoiInfo> poiInfoList;//检索附近位置信息
    private Marker marker;
    private PoiInfo poiInfo;
    private String flag;//标识从哪个页面跳转过来的 1-添加收货地址页面 2-选择收货地址页面

    @Override
    public IConfirmAddressPresenter createPresenter() {
        return new IConfirmAddressPresenter();
    }

    public static KFragment newIntence(String flag) {
        ConfirmAddressFragment fragment = new ConfirmAddressFragment();
        Bundle bundle = new Bundle();
        bundle.putString("flag", flag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flag = getArguments().getString("flag");
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_confirm_address;
    }

    @Override
    public void initData() {
        //移除放大缩小图标
        mMapView.showZoomControls(false);
        mBaiduMap = mMapView.getMap();
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder().zoom(18).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);

        //地图状态改变相关监听
        mBaiduMap.setOnMapStatusChangeListener(this);

        //开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        //定位图层显示方式
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        /**
         * 设置定位图层配置信息，只有先允许定位图层后设置定位图层配置信息才会生效
         * customMarker 用户自定义定位图标
         * enableDirection 是否允许显示方向信息
         * locationMode 定位图层显示方式
         */
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));

        //初始化定位
        mLocClient = new LocationClient(getActivity());
        //注册定位监听
        mLocClient.registerLocationListener(this);
        //定位选项
        LocationClientOption option = new LocationClientOption();
        /**
         * coorType - 取值有 3 个：
         * 返回国测局经纬度坐标系：gcj02
         * 返回百度墨卡托坐标系 ：bd09
         * 返回百度经纬度坐标系 ：bd09ll
         */
        option.setCoorType("bd09ll");
        //设置是否需要地址信息，默认为无地址
        option.setIsNeedAddress(true);
        //设置是否需要返回位置语义化信息，可以在 BDLocation.getLocationDescribe()中得到数据，ex:"在天安门附近"， 可以用作地址信息的补充
        option.setIsNeedLocationDescribe(true);
        //设置是否需要返回位置 POI 信息，可以在 BDLocation.getPoiList()中得到数据
        option.setIsNeedLocationPoiList(true);
        /**
         * 设置定位模式
         * Battery_Saving
         * 低功耗模式
         * Device_Sensors
         * 仅设备(Gps)模式
         * Hight_Accuracy
         * 高精度模式
         */
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //设置是否打开 gps 进行定位
        option.setOpenGps(true);
        //设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效
        option.setScanSpan(1000);

        //设置 LocationClientOption
        mLocClient.setLocOption(option);

        //开始定位
        mLocClient.start();

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        Eyes.setStatusBarColor(getActivity(), getResources().getColor(R.color.red));
        topBar.setTitleText("确认收货地址");
        topBar.setTitleTextColor(getResources().getColor(R.color.white));
        topBar.setLeftImageId(R.mipmap.icon_back_white);
        topBar.setBackgroundColor(getResources().getColor(R.color.red));
        topBar.setTopClickListener(this);

        poiInfoList = new ArrayList<>();

        initData();
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
    public void onResume() {
        super.onResume();
        // 恢复时同时恢复地图控件
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //暂停时同时暂停地图控件
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //退出时停止定位
        mLocClient.stop();
        //退出时关闭定位图层
        if (null != mBaiduMap) {
            mBaiduMap.setMyLocationEnabled(false);
        }

        // 销毁时同时销毁地图控件
        if (null != mMapView) {
            mMapView.onDestroy();
        }

        //释放资源
        if (geoCoder != null) {
            geoCoder.destroy();
        }

        mMapView = null;
    }

    /**
     * 手势操作地图，设置地图状态等操作导致地图状态开始改变
     *
     * @param mapStatus 地图状态改变开始时的地图状态
     */
    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }


    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

    }

    /**
     * 地图状态变化中
     *
     * @param mapStatus 当前地图状态
     */
    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    /**
     * 地图状态改变结束
     *
     * @param mapStatus 地图状态改变结束后的地图状态
     */
    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
        //地图操作的中心点
        LatLng cenpt = mapStatus.target;
        if (geoCoder != null) {
            geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(cenpt));
        }
    }

    //定位监听
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        //如果 bdLocation 为空或 mapView 销毁后不再处理新数据接收的位置
        if (bdLocation == null || mBaiduMap == null) {
            return;
        }

        //定位数据
        MyLocationData data = new MyLocationData.Builder()
                //定位精度 bdLocation.getRadius()
                .accuracy(bdLocation.getRadius())
                //此处设置开发者获取到的方向信息，顺时针 0-360
                .direction(bdLocation.getDirection())
                //经度
                .latitude(bdLocation.getLatitude())
                //纬度
                .longitude(bdLocation.getLongitude())
                //构建
                .build();

        //设置定位数据
//        mBaiduMap.setMyLocationData(data);

        //是否是第一次定位
        if (isFirstLoc) {
            isFirstLoc = false;
            LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(ll, 18);

            mBaiduMap.animateMapStatus(msu);
            //获取坐标，待会用于 POI 信息点与定位的距离
            locationLatLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            //获取城市，待会用于 POISearch
            city = bdLocation.getCity();

            //创建 GeoCoder 实例对象
            geoCoder = GeoCoder.newInstance();
            //发起反地理编码请求(经纬度->地址信息)
            ReverseGeoCodeOption reverseGeoCodeOption = new ReverseGeoCodeOption();
            //设置反地理编码位置坐标
            reverseGeoCodeOption.location(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()));
            //设置查询结果监听者
            geoCoder.setOnGetGeoCodeResultListener(this);
            geoCoder.reverseGeoCode(reverseGeoCodeOption);
        }

    }

    //地理编码查询结果回调函数
    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
    }

    //反地理编码查询结果回调函数
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        List<PoiInfo> poiInfos = reverseGeoCodeResult.getPoiList();
        if (poiInfoList != null) {
            poiInfoList.clear();
            poiInfoList.addAll(poiInfos);
            if (null != poiInfoList && poiInfoList.size() > 0) {
                poiInfo = poiInfoList.get(0);
                tv_name.setText(poiInfos.get(0).name);
                tv_detailed.setText(poiInfos.get(0).address);
            } else {
                tv_name.setText("");
                tv_detailed.setText("");
            }
        }
    }

    @OnClick({R.id.fragment_confirm_address_tv_sure, R.id.fragment_confirm_address_linearLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_confirm_address_tv_sure://确定
                if (null != poiInfo) {
                    Intent intent1 = new Intent(getActivity(), AddLocationActivity.class);
                    Bundle bundle = new Bundle();
                    if ("1".equals(flag)) {
                        //添加收货地址页面
                        EventBus.getDefault().post(new AddLocationEvent(poiInfo));
                    } else if ("2".equals(flag)) {
                        //选择收货地址页面
                        bundle.putParcelable("PoiInfo", poiInfo);
                    }
                    intent1.putExtras(bundle);
                    startActivity(intent1);
                    removeFragment();

                }
                break;
            case R.id.fragment_confirm_address_linearLayout://跳转全部页面
                Intent intent = new Intent(getActivity(), AllAddressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", (Serializable) poiInfoList);
                bundle.putString("city", city);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data && resultCode == 2) {
            Bundle bundle = data.getExtras();
            poiInfo = bundle.getParcelable("PoiInfo");
            tv_name.setText(poiInfo.name);
            tv_detailed.setText(poiInfo.address);
        }
    }
}

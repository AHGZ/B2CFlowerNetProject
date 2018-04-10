package com.android.p2pflowernet.project.view.fragments.index.bigdata;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MultiPointItem;
import com.amap.api.maps.model.MultiPointOverlay;
import com.amap.api.maps.model.MultiPointOverlayOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.BigDataMapBean;
import com.android.p2pflowernet.project.gadmap.Cluster;
import com.android.p2pflowernet.project.gadmap.ClusterClickListener;
import com.android.p2pflowernet.project.gadmap.ClusterItem;
import com.android.p2pflowernet.project.gadmap.ClusterOverlay;
import com.android.p2pflowernet.project.gadmap.ClusterRender;
import com.android.p2pflowernet.project.gadmap.RegionItem;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by caishen on 2018/2/1.
 * by--花返网全国发展展示
 */

public class NationwideFragment extends KFragment<INationwideView, INationwidePrenter> implements ClusterRender, ClusterClickListener, INationwideView
                ,DistrictSearch.OnDistrictSearchListener,GeocodeSearch.OnGeocodeSearchListener{
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
    private UiSettings mUiSettings;
    private ClusterOverlay mClusterOverlay;
    private int clusterRadius = 100;
    private Map<Integer, Drawable> mBackDrawAbles = new HashMap<Integer, Drawable>();

    private ShapeLoadingDialog shapeLoadingDialog;
    private Marker marker;
    private boolean isDestroy = false;
    private MultiPointOverlay agentMultiPointOverlay;
    private MultiPointOverlay manufacMultiPointOverlay;
    private MultiPointOverlay merchantMultiPointOverlay;
    private MultiPointOverlay partnerMultiPointOverlay;
    private MultiPointOverlay staffMultiPointOverlay;
    private MultiPointOverlay userMultiPointOverlay;
    private List<LatLng> data = new ArrayList<>();
    private DistrictSearch districtSearch;
    private GeocodeSearch geocoderSearch;

    @Override
    public INationwidePrenter createPresenter() {

        return new INationwidePrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_nationwide;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity()).loadText("加载中...")
                .delay(5000)
                .build();


        if (aMap == null) {
            // 初始化地图
            aMap = mMapView.getMap();
            mUiSettings = aMap.getUiSettings();
            mUiSettings.setRotateGesturesEnabled(false);
            mUiSettings.setTiltGesturesEnabled(false);
        }

        //初始化区域搜索
        districtSearch = new DistrictSearch(getContext());
        districtSearch.setOnDistrictSearchListener(this);//绑定监听器;

        //初始化反地理编码
        geocoderSearch = new GeocodeSearch(getContext());
        geocoderSearch.setOnGeocodeSearchListener(this);

        //添加一个Marker用来展示海量点点击效果
        marker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        //代理人
        BitmapDescriptor agentBitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.icon_dlr_dw_1);
        MultiPointOverlayOptions agentOverlayOptions = new MultiPointOverlayOptions();
        agentOverlayOptions.icon(agentBitmapDescriptor);
        agentOverlayOptions.anchor(0.5f,0.5f);
        agentMultiPointOverlay = aMap.addMultiPointOverlay(agentOverlayOptions);
        //供货商
        BitmapDescriptor manufacBitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.icon_dhs_dw_1);
        MultiPointOverlayOptions manufacOverlayOptions = new MultiPointOverlayOptions();
        manufacOverlayOptions.icon(manufacBitmapDescriptor);
        manufacOverlayOptions.anchor(0.5f,0.5f);
        manufacMultiPointOverlay = aMap.addMultiPointOverlay(manufacOverlayOptions);
        //商家
        BitmapDescriptor merchantBitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.icon_sj_dw_1);
        MultiPointOverlayOptions merchantOverlayOptions = new MultiPointOverlayOptions();
        merchantOverlayOptions.icon(merchantBitmapDescriptor);
        merchantOverlayOptions.anchor(0.5f,0.5f);
        merchantMultiPointOverlay = aMap.addMultiPointOverlay(merchantOverlayOptions);
        //合伙人
        BitmapDescriptor partnerBitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.icon_hhr_dw);
        MultiPointOverlayOptions partnerOverlayOptions = new MultiPointOverlayOptions();
        partnerOverlayOptions.icon(partnerBitmapDescriptor);
        partnerOverlayOptions.anchor(0.5f,0.5f);
        partnerMultiPointOverlay = aMap.addMultiPointOverlay(partnerOverlayOptions);
        //云工
        BitmapDescriptor staffBitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.icon_yg_dw_1);
        MultiPointOverlayOptions staffOverlayOptions = new MultiPointOverlayOptions();
        staffOverlayOptions.icon(staffBitmapDescriptor);
        staffOverlayOptions.anchor(0.5f,0.5f);
        staffMultiPointOverlay = aMap.addMultiPointOverlay(staffOverlayOptions);
        //会员
        BitmapDescriptor userBitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.icon_hy_dw);
        MultiPointOverlayOptions userOverlayOptions = new MultiPointOverlayOptions();
        userOverlayOptions.icon(userBitmapDescriptor);
        userOverlayOptions.anchor(0.5f,0.5f);
        userMultiPointOverlay = aMap.addMultiPointOverlay(userOverlayOptions);

        aMap.setOnMultiPointClickListener(new AMap.OnMultiPointClickListener() {
            @Override
            public boolean onPointClick(MultiPointItem pointItem) {
                android.util.Log.i("amap ", "onPointClick");

//                if(marker.isRemoved()) {
//                    //调用amap clear之后会移除marker，重新添加一个
//                    marker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
//                }
//                //添加一个Marker用来展示海量点点击效果
//                marker.setPosition(pointItem.getLatLng());
//                marker.setToTop();
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pointItem.getLatLng(), 8));

                //根据经纬度获取省市
//                LatLonPoint latLonPoint = new LatLonPoint(pointItem.getLatLng().latitude,pointItem.getLatLng().longitude);
//                RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,GeocodeSearch.AMAP);
//                geocoderSearch.getFromLocationAsyn(query);

                DistrictSearchQuery query = new DistrictSearchQuery();
                query.setKeywords("北京市");//传入关键字
                query.setShowBoundary(true);//是否返回边界值
                //不显示子区域边界
                query.setShowChild(false);
                districtSearch.setQuery(query);
                //开启异步搜索
                districtSearch.searchDistrictAsyn();

                return false;
            }
        });

        aMap.moveCamera(CameraUpdateFactory.zoomTo(3));

        initMapData();

        mPresenter.getMapData();
    }

    private void initMapData() {
        showProgressDialog();
        //开启子线程读取文件
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<MultiPointItem> list = new ArrayList<MultiPointItem>();
                //代理人
                List<MultiPointItem> agentList = new ArrayList<MultiPointItem>();
                //厂家
                List<MultiPointItem> manufacList = new ArrayList<MultiPointItem>();
                //商家
                List<MultiPointItem> merchantList = new ArrayList<MultiPointItem>();
                //合伙人
                List<MultiPointItem> partnerList = new ArrayList<MultiPointItem>();
                //云工
                List<MultiPointItem> staffList = new ArrayList<MultiPointItem>();
                //会员
                List<MultiPointItem> userList = new ArrayList<MultiPointItem>();
                String styleName = "style_json.json";
                FileOutputStream outputStream = null;
                InputStream inputStream = null;
                String filePath = null;
                try {
                    inputStream = getContext().getResources().openRawResource(R.raw.point10w);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        if (isDestroy) {
                            //已经销毁地图了，退出循环
                            return;
                        }

                        String[] str = line.split(",");
                        if (str == null) {
                            continue;
                        }
                        double lat = Double.parseDouble(str[1].trim());
                        double lon = Double.parseDouble(str[0].trim());

                        LatLng latLng = new LatLng(lat, lon, false);//保证经纬度没有问题的时候可以填false
                        data.add(latLng);
//                        MultiPointItem multiPointItem = new MultiPointItem(latLng);
//                        list.add(multiPointItem);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (inputStream != null)
                            inputStream.close();

                        if (outputStream != null)
                            outputStream.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                for (int i = 0; i < data.size(); i++) {
                    for (int j = 0; j < 10; j++) {
                        double lat = Math.random() + data.get(i).latitude;
                        double lon = Math.random() + data.get(i).longitude;
                        LatLng latLng = new LatLng(lat, lon, false);//保证经纬度没有问题的时候可以填false
                        MultiPointItem multiPointItem = new MultiPointItem(latLng);
                        list.add(multiPointItem);
                    }
                }

                for (int i = 0; i < list.size(); i++) {
//                    if (i <= 15000) {
//                        agentList.add(list.get(i));
//                    } else if (i <= 30000) {
//                        manufacList.add(list.get(i));
//                    } else if (i <= 45000) {
//                        merchantList.add(list.get(i));
//                    } else if (i <= 60000) {
//                        partnerList.add(list.get(i));
//                    } else if (i <= 75000) {
//                        staffList.add(list.get(i));
//                    } else if (i < list.size()) {
//                        userList.add(list.get(i));
//                    }
                    if (i%2 == 0) {
                        userList.add(list.get(i));
                    }else{
                        partnerList.add(list.get(i));
                    }
                }

//                if (agentMultiPointOverlay != null) {
//                    agentMultiPointOverlay.setItems(agentList);
//                    agentMultiPointOverlay.setEnable(true);
//                }
//                if (manufacMultiPointOverlay != null) {
//                    manufacMultiPointOverlay.setItems(manufacList);
//                    manufacMultiPointOverlay.setEnable(true);
//                }
//                if (merchantMultiPointOverlay != null) {
//                    merchantMultiPointOverlay.setItems(merchantList);
//                    merchantMultiPointOverlay.setEnable(true);
//                }
                if (partnerMultiPointOverlay != null) {
                    partnerMultiPointOverlay.setItems(partnerList);
                    partnerMultiPointOverlay.setEnable(true);
                }
//                if (staffMultiPointOverlay != null) {
//                    staffMultiPointOverlay.setItems(staffList);
//                    staffMultiPointOverlay.setEnable(true);
//                }
                if (userMultiPointOverlay != null) {
                    userMultiPointOverlay.setItems(userList);
                    userMultiPointOverlay.setEnable(true);
                }
                dissmissProgressDialog();

                //
            }
        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        //销毁资源
        isDestroy = true;
        if (mClusterOverlay != null) {
            mClusterOverlay.onDestroy();
        }

        if (mMapView != null) {
            mMapView.onDestroy();
        }
    }

    public static Fragment newIntence() {

        NationwideFragment nationwideFragment = new NationwideFragment();
        Bundle bundle = new Bundle();
        nationwideFragment.setArguments(bundle);
        return nationwideFragment;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public Drawable getDrawAble(Cluster cluster) {
        int radius = dp2px(getContext(), 80);
        if (cluster.getClusterCount() == 1) {
            int type = ((RegionItem) cluster.getClusterItems().get(0)).getType();
            if (type == 12) {//代理人
                Drawable bitmapDrawable = mBackDrawAbles.get(12);
                if (bitmapDrawable == null) {
                    bitmapDrawable =
                            getContext().getResources().getDrawable(
                                    R.mipmap.icon_dlr_dw);
                    mBackDrawAbles.put(12, bitmapDrawable);
                }
                return bitmapDrawable;
            } else if (type == 13) {//供货商
                Drawable bitmapDrawable = mBackDrawAbles.get(13);
                if (bitmapDrawable == null) {
                    bitmapDrawable =
                            getContext().getResources().getDrawable(
                                    R.mipmap.icon_ghs_dw);
                    mBackDrawAbles.put(13, bitmapDrawable);
                }
                return bitmapDrawable;
            } else if (type == 14) {//商家
                Drawable bitmapDrawable = mBackDrawAbles.get(14);
                if (bitmapDrawable == null) {
                    bitmapDrawable =
                            getContext().getResources().getDrawable(
                                    R.mipmap.icon_sj_dw);
                    mBackDrawAbles.put(14, bitmapDrawable);
                }
                return bitmapDrawable;
            } else if (type == 15) {//合伙人
                Drawable bitmapDrawable = mBackDrawAbles.get(15);
                if (bitmapDrawable == null) {
                    bitmapDrawable =
                            getContext().getResources().getDrawable(
                                    R.mipmap.icon_hhr_dw);
                    mBackDrawAbles.put(15, bitmapDrawable);
                }
                return bitmapDrawable;
            } else if (type == 16) {//云工
                Drawable bitmapDrawable = mBackDrawAbles.get(16);
                if (bitmapDrawable == null) {
                    bitmapDrawable =
                            getContext().getResources().getDrawable(
                                    R.mipmap.icon_yg_dw);
                    mBackDrawAbles.put(16, bitmapDrawable);
                }
                return bitmapDrawable;
            } else if (type == 11) {//会员
                Drawable bitmapDrawable = mBackDrawAbles.get(11);
                if (bitmapDrawable == null) {
                    bitmapDrawable =
                            getContext().getResources().getDrawable(
                                    R.mipmap.icon_hy_dw);
                    mBackDrawAbles.put(11, bitmapDrawable);
                }
                return bitmapDrawable;
            } else {

                Drawable bitmapDrawable = mBackDrawAbles.get(1);
                if (bitmapDrawable == null) {
                    bitmapDrawable =
                            getContext().getResources().getDrawable(
                                    R.drawable.icon_openmap_mark);
                    mBackDrawAbles.put(1, bitmapDrawable);
                }
                return bitmapDrawable;
            }

        } else if (cluster.getClusterCount() < 5) {

            Drawable bitmapDrawable = mBackDrawAbles.get(2);
            if (bitmapDrawable == null) {
                bitmapDrawable = new BitmapDrawable(null, drawCircle(radius,
                        Color.argb(159, 210, 154, 6)));
                mBackDrawAbles.put(2, bitmapDrawable);
            }

            return bitmapDrawable;
        } else if (cluster.getClusterCount() < 10) {
            Drawable bitmapDrawable = mBackDrawAbles.get(3);
            if (bitmapDrawable == null) {
                bitmapDrawable = new BitmapDrawable(null, drawCircle(radius,
                        Color.argb(199, 217, 114, 0)));
                mBackDrawAbles.put(3, bitmapDrawable);
            }

            return bitmapDrawable;
        } else {
            Drawable bitmapDrawable = mBackDrawAbles.get(4);
            if (bitmapDrawable == null) {
                bitmapDrawable = new BitmapDrawable(null, drawCircle(radius,
                        Color.argb(235, 215, 66, 2)));
                mBackDrawAbles.put(4, bitmapDrawable);
            }

            return bitmapDrawable;
        }
    }

    private Bitmap drawCircle(int radius, int color) {

        Bitmap bitmap = Bitmap.createBitmap(radius * 2, radius * 2,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        RectF rectF = new RectF(0, 0, radius * 2, radius * 2);
        paint.setColor(color);
        canvas.drawArc(rectF, 0, 360, true, paint);
        return bitmap;
    }

    @Override
    public void onClick(Marker marker, List<ClusterItem> clusterItems) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (ClusterItem clusterItem : clusterItems) {
            builder.include(clusterItem.getPosition());
        }
        LatLngBounds latLngBounds = builder.build();
//        aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 0));
        LatLng latLng = marker.getPosition();
        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
//        aMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    @Override
    public void onError(String s) {
        showShortToast(s);
    }

    @Override
    public void showDialog() {
        shapeLoadingDialog.show();
    }

    @Override
    public void hideDialog() {
        if (shapeLoadingDialog.isShowing()) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void onSuccess(BigDataMapBean data) {
        if (null != data) {
            //会员数量
            String userCount = data.getLists().getUser().getCount();
            tv_hy.setText("会员(" + (TextUtils.isEmpty(userCount) ? "0" : userCount) + ")");
            //代理人
            String agentCount = data.getLists().getAgent().getCount();
            tv_dlr.setText("代理人(" + (TextUtils.isEmpty(agentCount) ? "0" : agentCount) + ")");
            //厂家
            String manufacCount = data.getLists().getManufac().getCount();
            tv_ghs.setText("供货商(" + (TextUtils.isEmpty(manufacCount) ? "0" : manufacCount) + ")");
            //商家
            String merchantCount = data.getLists().getMerchant().getCount();
            tv_sj.setText("商家(" + (TextUtils.isEmpty(merchantCount) ? "0" : merchantCount) + ")");
            //合伙人
            String partnerCount = data.getLists().getPartner().getCount();
            tv_hhr.setText("合伙人(" + (TextUtils.isEmpty(partnerCount) ? "0" : partnerCount) + ")");
            //云工
            String staffCount = data.getLists().getStaff().getCount();
            tv_yg.setText("云工(" + (TextUtils.isEmpty(staffCount) ? "0" : staffCount) + ")");

            setMapData(data);
        }
    }

    private void setMapData(final BigDataMapBean data) {
        //添加测试数据
        new Thread() {
            public void run() {
                List<ClusterItem> items = new ArrayList<ClusterItem>();
                //会员
                List<BigDataMapBean.ListsBean.UserBean.ListBean> userBeans = data.getLists().getUser().getList();
                for (int i = 0; i < userBeans.size(); i++) {
                    BigDataMapBean.ListsBean.UserBean.ListBean listBean = userBeans.get(i);
                    LatLng latLng = new LatLng(Double.parseDouble(listBean.getReg_lat()), Double.parseDouble(listBean.getReg_lng()), false);
                    RegionItem regionItem = new RegionItem(latLng, "会员" + i, 11);
                    items.add(regionItem);
                }

                //代理人
                List<BigDataMapBean.ListsBean.AgentBean.ListBeanX> listBeanXs = data.getLists().getAgent().getList();
                for (int i = 0; i < listBeanXs.size(); i++) {
                    BigDataMapBean.ListsBean.AgentBean.ListBeanX listBeanX = listBeanXs.get(i);
                    LatLng latLng = new LatLng(Double.parseDouble(listBeanX.getReg_lat()), Double.parseDouble(listBeanX.getReg_lng()), false);
                    RegionItem regionItem = new RegionItem(latLng, "代理人" + i, 12);
                    items.add(regionItem);
                }

                //厂家
                List<BigDataMapBean.ListsBean.ManufacBean.ListBeanXXXX> listBeanXXXXs = data.getLists().getManufac().getList();
                for (int i = 0; i < listBeanXXXXs.size(); i++) {
                    BigDataMapBean.ListsBean.ManufacBean.ListBeanXXXX listBeanXXXX = listBeanXXXXs.get(i);
                    LatLng latLng = new LatLng(Double.parseDouble(listBeanXXXX.getReg_lat()), Double.parseDouble(listBeanXXXX.getReg_lng()), false);
                    RegionItem regionItem = new RegionItem(latLng, "厂家" + i, 13);
                    items.add(regionItem);
                }

                //商家
                List<BigDataMapBean.ListsBean.MerchantBean.ListBeanXXX> listBeanXXXs = data.getLists().getMerchant().getList();
                for (int i = 0; i < listBeanXXXs.size(); i++) {
                    BigDataMapBean.ListsBean.MerchantBean.ListBeanXXX listBeanXXX = listBeanXXXs.get(i);
                    LatLng latLng = new LatLng(Double.parseDouble(listBeanXXX.getReg_lat()), Double.parseDouble(listBeanXXX.getReg_lng()), false);
                    RegionItem regionItem = new RegionItem(latLng, "商家" + i, 14);
                    items.add(regionItem);
                }

                //合伙人
                List<BigDataMapBean.ListsBean.PartnerBean.ListBeanXX> listBeanXXs = data.getLists().getPartner().getList();
                for (int i = 0; i < listBeanXXs.size(); i++) {
                    BigDataMapBean.ListsBean.PartnerBean.ListBeanXX listBeanXX = listBeanXXs.get(i);
                    LatLng latLng = new LatLng(Double.parseDouble(listBeanXX.getReg_lat()), Double.parseDouble(listBeanXX.getReg_lng()), false);
                    RegionItem regionItem = new RegionItem(latLng, "合伙人" + i, 15);
                    items.add(regionItem);
                }

                //云工
                List<BigDataMapBean.ListsBean.StaffBean.ListBeanXXXXX> listBeanXXXXXs = data.getLists().getStaff().getList();
                for (int i = 0; i < listBeanXXXXXs.size(); i++) {
                    BigDataMapBean.ListsBean.StaffBean.ListBeanXXXXX listBeanXXXXX = listBeanXXXXXs.get(i);
                    LatLng latLng = new LatLng(Double.parseDouble(listBeanXXXXX.getReg_lat()), Double.parseDouble(listBeanXXXXX.getReg_lng()), false);
                    RegionItem regionItem = new RegionItem(latLng, "云工" + i, 16);
                    items.add(regionItem);
                }

                mClusterOverlay = new ClusterOverlay(aMap, items, dp2px(getContext(), clusterRadius), getContext());
                mClusterOverlay.setClusterRenderer(NationwideFragment.this);
                mClusterOverlay.setOnClusterClickListener(NationwideFragment.this);
            }

        }.start();
    }


    private ProgressDialog progDialog = null;// 添加海量点时

    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(getContext());
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("正在解析添加海量点中，请稍后...");
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    //获取行政区划数据回调
    @Override
    public void onDistrictSearched(DistrictResult districtResult) {
        //在回调函数中解析districtResult获取行政区划信息
        if (districtResult == null || districtResult.getDistrict()==null) {
            return;
        }
        //通过ErrorCode判断是否成功
        if(districtResult.getAMapException() != null && districtResult.getAMapException().getErrorCode() == AMapException.CODE_AMAP_SUCCESS) {
            final DistrictItem item = districtResult.getDistrict().get(0);

            if (item == null) {
                return;
            }
            LatLonPoint centerLatLng = item.getCenter();
            if (centerLatLng != null) {
                aMap.moveCamera(

                        CameraUpdateFactory.newLatLngZoom(new LatLng(centerLatLng.getLatitude(), centerLatLng.getLongitude()), 8));
            }


            new Thread() {
                public void run() {

                    String[] polyStr = item.districtBoundary();
                    if (polyStr == null || polyStr.length == 0) {
                        return;
                    }
                    for (String str : polyStr) {
                        String[] lat = str.split(";");
                        PolylineOptions polylineOption = new PolylineOptions();
                        boolean isFirst = true;
                        LatLng firstLatLng = null;
                        for (String latstr : lat) {
                            String[] lats = latstr.split(",");
                            if (isFirst) {
                                isFirst = false;
                                firstLatLng = new LatLng(Double
                                        .parseDouble(lats[1]), Double
                                        .parseDouble(lats[0]));
                            }
                            polylineOption.add(new LatLng(Double
                                    .parseDouble(lats[1]), Double
                                    .parseDouble(lats[0])));
                        }
                        if (firstLatLng != null) {
                            polylineOption.add(firstLatLng);
                        }

                        polylineOption.width(10).color(Color.BLUE);
                        aMap.addPolyline(polylineOption);
                    }
                }
            }.start();
        }
    }

    //反地理编码回调
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        //解析result获取地址描述信息
        if (i == 1000) {
            if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null
                    && regeocodeResult.getRegeocodeAddress().getFormatAddress() != null) {
                String city = regeocodeResult.getRegeocodeAddress().getProvince();
                Log.e("onRegeocodeSearched=", city );
                DistrictSearchQuery query = new DistrictSearchQuery();
                query.setKeywords(city);//传入关键字
                query.setShowBoundary(true);//是否返回边界值
                //不显示子区域边界
                query.setShowChild(false);
                districtSearch.setQuery(query);
                //开启异步搜索
                districtSearch.searchDistrictAsyn();
            }
        }
    }

    //反地理编码回调
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
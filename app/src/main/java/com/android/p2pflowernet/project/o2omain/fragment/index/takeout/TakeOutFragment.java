package com.android.p2pflowernet.project.o2omain.fragment.index.takeout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.ActivityAdapter;
import com.android.p2pflowernet.project.adapter.IndexO2OAdapter;
import com.android.p2pflowernet.project.adapter.SelListAdapter;
import com.android.p2pflowernet.project.adapter.TakeOutGridviewAdapter;
import com.android.p2pflowernet.project.callback.AdapterLoader;
import com.android.p2pflowernet.project.entity.DistriButionBean;
import com.android.p2pflowernet.project.entity.DropBean;
import com.android.p2pflowernet.project.entity.O2oAddressBean;
import com.android.p2pflowernet.project.entity.O2oHomeBean;
import com.android.p2pflowernet.project.event.O2oTakeOutAddressEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.activity.StoreDetailActivity;
import com.android.p2pflowernet.project.o2omain.fragment.index.O2oIndexModel;
import com.android.p2pflowernet.project.o2omain.fragment.index.takeout.cate.CateActivity;
import com.android.p2pflowernet.project.o2omain.fragment.index.takeout.location.LocationO2oActivity;
import com.android.p2pflowernet.project.utils.SPUtils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.DropdownButton;
import com.android.p2pflowernet.project.view.customview.NoScrollGridView;
import com.android.p2pflowernet.project.view.customview.PopWinDownUtil;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by heguozhong on 2017/12/28/028.
 * 外卖主界面
 */

public class TakeOutFragment extends KFragment<ITakeOutView, ITakeOutPresenter>
        implements PopWinDownUtil.OnDismissLisener, ITakeOutView, TextWatcher, View.OnKeyListener, CustomEditText.IMyRightDrawableClick, TextView.OnEditorActionListener
        , OnGetGeoCoderResultListener, BDLocationListener {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.tosearch_tv)
    CustomEditText tosearchTv;
    @BindView(R.id.message_linear)
    LinearLayout messageLinear;

    //地址图标
    @BindView(R.id.take_out_recyclerview_item_address_img)
    ImageView takeOutRecyclerviewItemAddressImg;
    //地址文字提示
    @BindView(R.id.take_out_recyclerview_item_address_text)
    TextView takeOutRecyclerviewItemAddressText;
    //地址最右侧箭头图标
    @BindView(R.id.take_out_recyclerview_item_address_rightimg)
    ImageView takeOutRecyclerviewItemAddressRightimg;
    //地址linearlayout布局
    @BindView(R.id.ll_location)
    LinearLayout llLocation;
    //展示外卖，团购优惠，生鲜蔬果......的gridview布局
    @BindView(R.id.gridview)
    NoScrollGridView gridview;

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    //筛选按钮
    @BindView(R.id.db_goods_list_sort)
    DropdownButton dbGoodsListSort;
    //销量最高文字提示
    @BindView(R.id.tv_goods_list_sales)
    TextView tvGoodsListSales;
    //销量最高图标
    @BindView(R.id.iv_goods_list_sales)
    ImageView ivGoodsListSales;
    //销量最高linearlayout布局
    @BindView(R.id.ll_goods_list_sales)
    LinearLayout llGoodsListSales;
    //距离最近文字提示
    @BindView(R.id.tv_goods_list_price)
    TextView tvGoodsListPrice;
    //距离最近图标
    @BindView(R.id.iv_goods_list_arrow)
    ImageView ivGoodsListArrow;
    //距离最近linearlayout布局
    @BindView(R.id.ll_goods_list_nearby)
    LinearLayout llGoodsListNearby;
    //筛选文字提示
    @BindView(R.id.tv_goods_list_select)
    TextView tvGoodsListSelect;
    //筛选文字图标
    @BindView(R.id.iv_goods_list_sel)
    ImageView ivGoodsListSel;
    //筛选linearlayout布局
    @BindView(R.id.ll_goods_list_sel)
    LinearLayout llGoodsListSel;
    //展示主要内容的recyclerview布局
    @BindView(R.id.take_out_recyclerview)
    LRecyclerView takeOutRecyclerview;
    @BindView(R.id.search)
    RelativeLayout search;

    private PopWinDownUtil popWinDownUtil;
    private List<DropBean> sorts;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private String state = "1";
    private String latitude = "0";//纬度
    private String longitude = "0";//经度
    private String sreens = "";//筛选条件
    private int page = 1;
    private ShapeLoadingDialog shapeLoadingDialog;
    private List<O2oHomeBean.ListBean> list;
    private IndexO2OAdapter indexO2OAdapter;
    private boolean isLoadingMore = false;//是否加载更多数据
    private static final int REQUEST_COUNT = 10;
    private List<DistriButionBean.DistriButionsBean> distribution;
    private SelListAdapter mAdapter;//筛选的适配器
    private int count = 10;
    private List<O2oHomeBean.ZongheBean> zonghe;
    private List<O2oHomeBean.ZongheBean> zonghe1;
    private O2oHomeBean data;
    private String tag = "0";//0-正常外卖首页数据 1-外卖首页搜索数据


    //定位端
    private LocationClient mLocClient;
    //是否是第一次定位
    private boolean isFirstLoc = true;
    //定位坐标
    private LatLng locationLatLng;
    //反地理编码
    private GeoCoder geoCoder;

    public static TakeOutFragment newInstance() {

        Bundle args = new Bundle();
        TakeOutFragment fragment = new TakeOutFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public ITakeOutPresenter createPresenter() {
        return new ITakeOutPresenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.activity_take_out;
    }

    @Override
    public void initData() {

        mPresenter.getTakeOut();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸式
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.coloro2o));

        //取出经纬度
        latitude = SPUtils.get(getActivity(), "latitude", "").toString();
        longitude = SPUtils.get(getActivity(), "longitude", "").toString();

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .delay(5000)
                .loadText("加载中...")
                .build();

        // 搜索框的键盘搜索键点击回调
        tosearchTv.setOnKeyListener(this);

        //设置搜索框的删除按钮的点击事件
        tosearchTv.setRightDrawable(getResources().getDrawable(R.drawable.et_clear_search));
        tosearchTv.setDrawableClick(this);
        tosearchTv.setOnEditorActionListener(this);

//        tosearchTv.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
//        tosearchTv.setInputType(EditorInfo.TYPE_CLASS_TEXT);


        //获取布局管理者
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //设置为垂直排列格式
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //recyclerview应用垂直排列格式
        takeOutRecyclerview.setLayoutManager(linearLayoutManager);

        //设置底部加载文字提示
        takeOutRecyclerview.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        takeOutRecyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
        takeOutRecyclerview.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        takeOutRecyclerview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        takeOutRecyclerview.setArrowImageView(R.drawable.iconfont_downgrey);  //设置下拉刷新箭头

        //添加分割线
        takeOutRecyclerview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = 0;
                // top bottom left right 对应的值应该是dpi 而不是dp  dpi根据不同手机而不同

                int i = parent.getChildLayoutPosition(view) % 1;//每行n个
                switch (i) {
                    case 0://第一个
                        outRect.left = convertDpToPixel(0);
                        outRect.right = convertDpToPixel(0);
                        outRect.bottom = convertDpToPixel(1);
                        outRect.top = convertDpToPixel(1);
                        break;
                }
            }
        });

        //初始化数据
        initData();
        initGeoCoder();
        initClick();
    }

    private void initGeoCoder() {

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
//        option.setScanSpan(1000);

        //设置 LocationClientOption
        mLocClient.setLocOption(option);

        //开始定位
        mLocClient.start();

        //创建 GeoCoder 实例对象
        geoCoder = GeoCoder.newInstance();
    }

    private void initClick() {

        //禁止下拉刷新
        takeOutRecyclerview.setPullRefreshEnabled(false);
        takeOutRecyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                if (indexO2OAdapter != null) {

                    if (count >= indexO2OAdapter.getItemCount()) {

                        isLoadingMore = true;
                        page += 1;
                        mPresenter.getTakeOut();

                    } else {

                        takeOutRecyclerview.setNoMore(true);
                        showShortToast("没有更多数据了！");
                    }
                }
            }
        });

    }

    @OnClick({R.id.ll_back, R.id.ll_location, R.id.ll_goods_list_sel, R.id.ll_goods_list_sales, R.id.ll_goods_list_nearby,})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back://

                removeFragment();

                break;
            //添加地址管理
            case R.id.ll_location:
                Intent intent = new Intent(getActivity(), LocationO2oActivity.class);
                intent.putExtra("flag", "2");
                startActivity(intent);

                break;
            //筛选框
            case R.id.ll_goods_list_sel:
                //设置appBar收起
                appBar.setExpanded(false);

                //改变其他按钮颜色颜色
                tvGoodsListSales.setTextColor(Color.parseColor("#444444"));
                tvGoodsListPrice.setTextColor(Color.parseColor("#444444"));
                tvGoodsListSelect.setTextColor(Color.RED);

                //延迟500毫秒弹出筛选框
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        initSel(llGoodsListSel);
                    }
                }, 500);

                break;
            //销量最高点击事件
            case R.id.ll_goods_list_sales:

                state = "2";
                if (tag.equals("0")) {//
                    mPresenter.getTakeOut();
                } else {//搜索数据
                    mPresenter.getSerchTakeOut();
                }

                tvGoodsListSales.setTextColor(Color.RED);
                tvGoodsListPrice.setTextColor(Color.parseColor("#444444"));
                tvGoodsListSelect.setTextColor(Color.parseColor("#444444"));

                break;
            //距离最近点击事件
            case R.id.ll_goods_list_nearby:

                state = "3";
                if (tag.equals("0")) {//
                    mPresenter.getTakeOut();
                } else {//搜索数据
                    mPresenter.getSerchTakeOut();
                }

                tvGoodsListSales.setTextColor(Color.parseColor("#444444"));
                tvGoodsListPrice.setTextColor(Color.RED);
                tvGoodsListSelect.setTextColor(Color.parseColor("#444444"));

                break;
        }
    }


    /**
     * 输入框右侧的点击回调
     *
     * @param view
     */
    @Override
    public void rightDrawableClick(View view) {

        switch (view.getId()) {
            case R.id.tosearch_tv:

                //清除所有搜索的字符
                tosearchTv.setText("");

                break;
        }
    }

    //筛选弹窗
    private void initSel(View llGoodsListSel) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dropdown_goods_sel, null);
        ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.ex_listView);
        Button fram_reset_but = (Button) view.findViewById(R.id.fram_reset_but);//重置
        Button fram_ok_but = (Button) view.findViewById(R.id.fram_ok_but);//完成
        //完成
        fram_ok_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tag.equals("0")) {//
                    mPresenter.getTakeOut();
                } else {//搜索数据
                    mPresenter.getSerchTakeOut();
                }
                popWinDownUtil.hide();
            }
        });


        //重置
        fram_reset_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetData();
            }
        });

        if (distribution != null && distribution.size() > 0) {

            if (mAdapter != null) {

                mAdapter.notifyDataSetChanged();
            }

        } else {

            distribution = O2oIndexModel.ShopQueue();
        }

        //设置筛选的适配器
        mAdapter = new SelListAdapter(getActivity(), distribution);
        expandableListView.setAdapter(mAdapter);
        expandableListView.setGroupIndicator(null); //设置属性 GroupIndicator 去掉向下箭头

        for (int i = 0; i < mAdapter.getGroupCount(); i++) {
            expandableListView.expandGroup(i); //关键步骤4:初始化，将ExpandableListView以展开的方式显示
        }

        //设置选中数据回调
        mAdapter.setOnCheckedClickLitener(new SelListAdapter.OnCheckedClickLitener() {
            @Override
            public void onCheckedCLicklitener(View view, int position,
                                              List<DistriButionBean.DistriButionsBean> data) {

                String id = "";
                for (int i = 0; i < data.size(); i++) {

                    List<DistriButionBean.DistriButionsBean.DistriBean> distri = data.get(i).getDistri();

                    for (int i1 = 0; i1 < distri.size(); i1++) {

                        if (distri.get(i1).ischoose()) {

                            id += position + 1 + ",";
                        }
                    }
                }

                if (!id.equals("") || !id.equals(",")) {

                    sreens = id.toString().substring(0, id.toString().lastIndexOf(","));
                }

                mAdapter.notifyDataSetChanged();
            }
        });


        //添加尾部布局
        View footView = LayoutInflater.from(getActivity()).inflate(R.layout.dropdown_goods_sel_item, null);
        GridView gridview = (GridView) footView.findViewById(R.id.gridview);
        TextView tv_activity = (TextView) footView.findViewById(R.id.tv_activity);


        //设置尾部活动的适配器
        final ActivityAdapter activityAdapter = new ActivityAdapter(getActivity());
        gridview.setAdapter(activityAdapter);

        //点击事件
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                activityAdapter.changeState(position);
            }
        });

//        //添加尾部布局
//        expandableListView.addFooterView(footView);

        popWinDownUtil = new PopWinDownUtil(getActivity(), view, llGoodsListSel);
        popWinDownUtil.setOnDismissListener(this);
        popWinDownUtil.show();
    }


    //重置数据
    private void resetData() {

        sreens = "";
        for (int i = 0; i < distribution.size(); i++) {

            List<DistriButionBean.DistriButionsBean.DistriBean> distri = distribution.get(i).getDistri();

            for (int i1 = 0; i1 < distri.size(); i1++) {

                distri.get(i1).setIschoose(false);
            }
        }

        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    private int convertDpToPixel(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return (int) (dp * displayMetrics.density);
    }

    @Override
    public void onDismiss() {
        if (popWinDownUtil != null && popWinDownUtil.isShowing()) {
            popWinDownUtil.hide();
        }
    }

    @Override
    public void onError(String s) {
        showShortToast(s);
    }

    @Override
    public String getstate() {
        return state;
    }

    @Override
    public String getlatitude() {

        return latitude;
    }

    @Override
    public String getlongitude() {

        return longitude;
    }

    @Override
    public int getpages() {
        return page;
    }

    @Override
    public String getsreen() {
        return sreens;
    }

    @Override
    public void showDialog() {

        shapeLoadingDialog.show();
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void onSuccessData(final O2oHomeBean data) {

        if (data != null) {
            this.data = data;
            if (zonghe != null && zonghe.size() > 0) {
            } else {
                dbGoodsListSort.setData(data.getZonghe());
            }

            //设置综合排序的数据
            zonghe = data.getZonghe();

            //设置综合排序的点击事件
            dbGoodsListSort.setOnDropItemSelectListener(new DropdownButton.OnDropItemSelectListener() {
                @Override
                public void onDropItemSelect(int Postion) {

                    state = String.valueOf(zonghe.get(Postion).getState());
                    mPresenter.getTakeOut();
                }
            });

            if (data.getCategory() != null || data.getCategory().size() > 0) {

                //设置美食，甜点饮品，家常菜......gridview适配器
                TakeOutGridviewAdapter takeOutGridviewAdapter = new TakeOutGridviewAdapter(getActivity(), data.getCategory());
                gridview.setAdapter(takeOutGridviewAdapter);
                //设置girdview的item的点击事件
                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //跳转美食页面
                        Intent intent = new Intent(getActivity(), CateActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("cate_id", data.getCategory().get(position).getCate_id());
                        bundle.putInt("zcate_id", data.getZcateid());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }

            List<O2oHomeBean.ListBean> lists = data.getList();
            count = lists.size();

            if (!isLoadingMore) {

                list = lists;

                if (list != null && list.size() > 0) {

                    takeOutRecyclerview.setVisibility(View.VISIBLE);
                    //设置附近商家的适配器
                    indexO2OAdapter = new IndexO2OAdapter(getActivity());
                    indexO2OAdapter.setList(list);
                    mLRecyclerViewAdapter = new LRecyclerViewAdapter(indexO2OAdapter);
                    takeOutRecyclerview.setAdapter(mLRecyclerViewAdapter);

                    indexO2OAdapter.setOnItemClickListener(new AdapterLoader.OnItemClickListener<O2oHomeBean.ListBean>() {
                        @Override
                        public void onItemClick(View itemView, int position, O2oHomeBean.ListBean item) {
                            Log.e("Tag===================", "跳了==============");
                            showShortToast("不跳");
                            Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                            intent.putExtra("merch_id", list.get(position).getMerch_id());
                            startActivity(intent);
                        }
                    });

                } else {

                    takeOutRecyclerview.setVisibility(View.GONE);
                    showShortToast("暂无商家信息");
                }

            } else {//加载更多数据

                isLoadingMore = false;
                if (lists.size() > 0) {

                    list.addAll(lists);
                    indexO2OAdapter.notifyDataSetChanged();
                    takeOutRecyclerview.refreshComplete(lists.size());

                } else {

                    takeOutRecyclerview.setNoMore(true);
                    showShortToast("没有更多数据了！");
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(O2oTakeOutAddressEvent event) {
        if (null != event) {
            O2oAddressBean.ListsBean listsBean = event.getListsBean();
            takeOutRecyclerviewItemAddressText.setText(TextUtils.isEmpty(listsBean.getSite_name()) ?
                    "" : listsBean.getSite_name());
        }
    }

    @Override
    public String getSearchKey() {

        return tosearchTv.getText().toString().trim();
    }

    /**
     * 搜索店铺数据
     *
     * @param data
     */
    @Override
    public void onSuccessSearch(final O2oHomeBean data) {

        if (data != null) {

            if (zonghe != null && zonghe.size() > 0) {

            } else {

                dbGoodsListSort.setData(data.getZonghe());
            }

            //设置综合排序的数据
            zonghe = data.getZonghe();

            //设置综合排序的点击事件
            dbGoodsListSort.setOnDropItemSelectListener(new DropdownButton.OnDropItemSelectListener() {
                @Override
                public void onDropItemSelect(int Postion) {

                    state = String.valueOf(zonghe.get(Postion).getState());
                    mPresenter.getTakeOut();
                }
            });

            if (data.getCategory() != null || data.getCategory().size() > 0) {

                //设置美食，甜点饮品，家常菜......gridview适配器
                TakeOutGridviewAdapter takeOutGridviewAdapter = new TakeOutGridviewAdapter(getActivity(), data.getCategory());
                gridview.setAdapter(takeOutGridviewAdapter);
                //设置girdview的item的点击事件
                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //跳转美食页面
                        Intent intent = new Intent(getActivity(), CateActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("cate_id", data.getCategory().get(position).getCate_id());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }

            List<O2oHomeBean.ListBean> lists = data.getList();
            count = lists.size();

            if (!isLoadingMore) {

                list = lists;

                if (list != null && list.size() > 0) {

                    takeOutRecyclerview.setVisibility(View.VISIBLE);
                    //设置附近商家的适配器
                    indexO2OAdapter = new IndexO2OAdapter(getActivity());
                    indexO2OAdapter.setList(list);
                    mLRecyclerViewAdapter = new LRecyclerViewAdapter(indexO2OAdapter);
                    takeOutRecyclerview.setAdapter(mLRecyclerViewAdapter);

                    indexO2OAdapter.setOnItemClickListener(new AdapterLoader.OnItemClickListener<O2oHomeBean.ListBean>() {
                        @Override
                        public void onItemClick(View itemView, int position, O2oHomeBean.ListBean item) {
                            startActivity(new Intent(getActivity(), StoreDetailActivity.class));
                        }
                    });

                } else {

                    takeOutRecyclerview.setVisibility(View.GONE);
                    showShortToast("暂无商家信息");
                }

            } else {//加载更多数据

                isLoadingMore = false;
                if (lists.size() > 0) {

                    list.addAll(lists);
                    indexO2OAdapter.notifyDataSetChanged();
                    takeOutRecyclerview.refreshComplete(lists.size());

                } else {

                    takeOutRecyclerview.setNoMore(true);
                    showShortToast("没有更多数据了！");
                }
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能

            if (tosearchTv.getText().toString().trim().equals("")) {

                showShortToast("什么也没有输入哦！");

            } else {

                tag = "1";
                //请求搜索数据
                mPresenter.getSerchTakeOut();
            }
        }
        return false;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        return actionId == EditorInfo.IME_ACTION_SEND
                || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
    }

    //地理编码查询结果回调函数
    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    //反地理编码查询结果回调函数
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        List<PoiInfo> poiInfos = reverseGeoCodeResult.getPoiList();
        if (poiInfos.size() > 0) {
            PoiInfo poiInfo = poiInfos.get(0);
            if (null != poiInfo) {
                if (null != takeOutRecyclerviewItemAddressText) {
                    takeOutRecyclerviewItemAddressText.setText(TextUtils.isEmpty(poiInfo.name) ? "" : poiInfo.name);
                }
            }
        } else {
            takeOutRecyclerviewItemAddressText.setText("");
        }
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        //如果 bdLocation 为空或 mapView 销毁后不再处理新数据接收的位置
        if (bdLocation == null) {
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
        //发起反地理编码请求(经纬度->地址信息)
        ReverseGeoCodeOption reverseGeoCodeOption = new ReverseGeoCodeOption();
        //设置反地理编码位置坐标
        reverseGeoCodeOption.location(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()));
        //设置查询结果监听者
        geoCoder.setOnGetGeoCodeResultListener(this);
        geoCoder.reverseGeoCode(reverseGeoCodeOption);
    }

    /***
     * 友盟统计
     */
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("takeOutPage"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("takeOutPage");
    }
}

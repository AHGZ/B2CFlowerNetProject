package com.android.p2pflowernet.project.o2omain.fragment.index;

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
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.ActivityAdapter;
import com.android.p2pflowernet.project.adapter.IndexO2OAdapter;
import com.android.p2pflowernet.project.adapter.IndexO2oMenueAdapter;
import com.android.p2pflowernet.project.adapter.SelListAdapter;
import com.android.p2pflowernet.project.base.BaseApplication;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.DistriButionBean;
import com.android.p2pflowernet.project.entity.O2oAddressBean;
import com.android.p2pflowernet.project.entity.O2oHomeBean;
import com.android.p2pflowernet.project.entity.O2oIndexBean;
import com.android.p2pflowernet.project.event.O2oHomeAddressEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.activity.StoreDetailActivity;
import com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying.GroupBuyingActivity;
import com.android.p2pflowernet.project.o2omain.fragment.index.takeout.TakeOutActivity;
import com.android.p2pflowernet.project.o2omain.fragment.index.takeout.location.LocationO2oActivity;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.utils.SPUtils;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.DropdownButton;
import com.android.p2pflowernet.project.view.customview.NoScrollGridView;
import com.android.p2pflowernet.project.view.customview.PopWinDownUtil;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.AppPartnerAlertDialog;
import com.android.p2pflowernet.project.view.fragments.search.SearchActivity;
import com.android.p2pflowernet.project.zxing.activity.CaptureActivity;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.AppBarStateChangeListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.umeng.analytics.MobclickAgent;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhangpeisen
 * created on: 2017/12/27 上午11:35
 * description:o2o首页
 */
public class O2oIndexFragment extends KFragment<O2oIndexView, O2oIndexPresenter>
        implements PopWinDownUtil.OnDismissLisener, O2oIndexView {


    @BindView(R.id.location_tv)
    TextView locationTv;
    @BindView(R.id.location_linear)
    LinearLayout locationLinear;
    @BindView(R.id.tosearch_framelayout)
    FrameLayout tosearchFramelayout;
    @BindView(R.id.message_linear)
    LinearLayout messageLinear;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.gridview)
    NoScrollGridView gridview;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.db_goods_list_sort)
    DropdownButton dbGoodsListSort;
    @BindView(R.id.tv_goods_list_sales)
    TextView tvGoodsListSales;
    @BindView(R.id.iv_goods_list_sales)
    ImageView ivGoodsListSales;
    @BindView(R.id.ll_goods_list_sales)
    LinearLayout llGoodsListSales;
    @BindView(R.id.tv_goods_list_price)
    TextView tvGoodsListPrice;
    @BindView(R.id.iv_goods_list_arrow)
    ImageView ivGoodsListArrow;
    @BindView(R.id.ll_goods_list_nearby)
    LinearLayout llGoodsListNearby;
    @BindView(R.id.tv_goods_list_select)
    TextView tvGoodsListSelect;
    @BindView(R.id.iv_goods_list_sel)
    ImageView ivGoodsListSel;
    @BindView(R.id.ll_goods_list_sel)
    LinearLayout llGoodsListSel;
    @BindView(R.id.recycler_view)
    LRecyclerView recyclerView;
    private List<String> mImages = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private boolean isLoadingMore = false;
    private static final int REQUEST_COUNT = 10;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private List<O2oHomeBean.ZongheBean> sorts = new ArrayList<>();
    private int click_count = 0;
    private PopWinDownUtil popWinDownUtil;
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;
    private int page = 1;
    private String state = "1";//1为综合排序，2为销量，3为距离最近，4为起送金额最低，5为配送费最低
    private ShapeLoadingDialog shapeLoadingDialog;
    private IndexO2OAdapter indexO2OAdapter;
    private List<O2oHomeBean.ListBean> list;//首页商家数据
    private double latitude = 0;//纬度
    private double longitude = 0;//经度
    private String sreens = "";//筛选条件
    private List<DistriButionBean.DistriButionsBean> distribution;//筛选数据
    private SelListAdapter mAdapter;
    private int count = 10;
    private List<O2oHomeBean.ZongheBean> zonghe;
    private String city;//城市
    private String searchName = "";//搜索关键字
    private AppBarStateChangeListener.State appBarState;//appBar状态
    private ExpandableListView expandableListView;

    public static O2oIndexFragment newInstance(String searchName) {

        Bundle args = new Bundle();
        args.putString("searchName", searchName);
        O2oIndexFragment fragment = new O2oIndexFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchName = getArguments().getString("searchName");
    }

    @Override
    public O2oIndexPresenter createPresenter() {
        return new O2oIndexPresenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.o2omainindex_fragment;
    }

    @Override
    public void initData() {

        //设置gridview适配器
        IndexO2oMenueAdapter o2oMenueAdapter = new IndexO2oMenueAdapter(getActivity());
        gridview.setAdapter(o2oMenueAdapter);

        //设置点击事件
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {//外卖
                    Intent intent = new Intent(getActivity(), TakeOutActivity.class);
                    intent.putExtra("searchName", "");
                    startActivity(intent);
                } else if (position == 1) {//团购优惠
                    Intent groupIntent = new Intent(getActivity(), GroupBuyingActivity.class);
                    groupIntent.putExtra("city", city);
                    startActivity(groupIntent);
                }
            }
        });

        if (TextUtils.isEmpty(searchName) || searchName.equals("")) {

            // 定位初始化
            mLocClient = new LocationClient(getActivity());
            mLocClient.registerLocationListener(myListener);
            LocationClientOption option = new LocationClientOption();
            option.setIsNeedAddress(true);
            option.setOpenGps(true); // 打开gps
            mLocClient.setLocOption(option);
            mLocClient.start();

            mPresenter.getO2oHome();
        } else {//搜索数据
            mPresenter.getSearchO2oHome();
        }

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸式
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.coloro2o));
        //设置布局管理
        UIUtils.setTouchDelegate(messageLinear, 50);
        UIUtils.setTouchDelegate(locationLinear, 50);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .delay(5000)
                .loadText("加载中...")
                .build();

        initData();

        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    //展开状态
                    appBarState = AppBarStateChangeListener.State.EXPANDED;
                } else if (Math.abs(verticalOffset) >= appBar.getTotalScrollRange()) {
                    //折叠状态
                    appBarState = AppBarStateChangeListener.State.COLLAPSED;
                }
            }
        });

        /**
         * 自定义RecyclerView实现对AppBarLayout的滚动效果
         */
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int visiblePosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                    if (visiblePosition == 0) {
                        appBar.setExpanded(true, true);
                    }
                }
            }
        });

        //设置底部加载文字提示
        recyclerView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
        recyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        recyclerView.setArrowImageView(R.drawable.iconfont_downgrey);  //设置下拉刷新箭头

        //添加分割线
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
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

        // 定位初始化
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedLocationPoiList(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true); // 打开gps
        mLocClient.setLocOption(option);
        mLocClient.start();

        initClick();
    }

    private void initSel(LinearLayout llGoodsListSel) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dropdown_goods_sel, null);
        expandableListView = (ExpandableListView) view.findViewById(R.id.ex_listView);
        Button fram_reset_but = (Button) view.findViewById(R.id.fram_reset_but);//重置
        Button fram_ok_but = (Button) view.findViewById(R.id.fram_ok_but);//完成

        //完成
        fram_ok_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(searchName) || searchName.equals("")) {
                    mPresenter.getO2oHome();
                } else {//搜索数据
                    mPresenter.getSearchO2oHome();
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

                            id += distri.get(i1).getId() + ",";
                        }
                    }
                }

                if (!id.equals("") && !id.equals(",")) {

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

            mAdapter = new SelListAdapter(getActivity(), distribution);
            expandableListView.setAdapter(mAdapter);
            expandableListView.setGroupIndicator(null); //设置属性 GroupIndicator 去掉向下箭头

            for (int i = 0; i < mAdapter.getGroupCount(); i++) {

                expandableListView.expandGroup(i);
            }
        }
    }


    //初始化监听
    private void initClick() {

        //禁止下拉刷新
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                if (indexO2OAdapter != null) {

                    if (count >= indexO2OAdapter.getItemCount()) {

                        isLoadingMore = true;
                        page += 1;
                        mPresenter.getO2oHome();

                    } else {

                        recyclerView.setNoMore(true);
                        showShortToast("没有更多数据了！");
                    }
                }
            }
        });
    }

    @OnClick({R.id.location_linear, R.id.message_linear, R.id.tosearch_framelayout,
            R.id.ll_goods_list_sales, R.id.ll_goods_list_nearby, R.id.ll_goods_list_sel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.location_linear://定位

                //定位模块
                Intent addressIntent = new Intent(getActivity(), LocationO2oActivity.class);
                addressIntent.putExtra("flag", "1");
                startActivity(addressIntent);

                break;
            case R.id.message_linear://扫一扫

                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                intent.putExtra("tag", "1");
                startActivity(intent);

                break;
            case R.id.tosearch_framelayout://搜索

                intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("tag", "1");//0-b2c 1-o2o 2-o2o外卖
                startActivity(intent);
//                //发送消息结束o2o首页
//                EventBus.getDefault().post(new OutO2oIndexHomeEvent());

                break;
            case R.id.ll_goods_list_sales://销量最高

                state = "2";
                if (TextUtils.isEmpty(searchName) || searchName.equals("")) {
                    mPresenter.getO2oHome();
                } else {//搜索数据
                    mPresenter.getSearchO2oHome();
                }

                //距离默认
                tvGoodsListPrice.setTextColor(Color.parseColor("#333538"));
                //筛选默认
                tvGoodsListSelect.setTextColor(Color.parseColor("#333538"));
                //销量红色
                tvGoodsListSales.setTextColor(Color.parseColor("#ed4141"));

                break;
            case R.id.ll_goods_list_nearby://距离最近

                state = "3";
                if (TextUtils.isEmpty(searchName) || searchName.equals("")) {
                    mPresenter.getO2oHome();
                } else {//搜索数据
                    mPresenter.getSearchO2oHome();
                }

                //价格点击事件
                click_count++;

                //距离默认
                tvGoodsListPrice.setTextColor(Color.parseColor("#ed4141"));
                //筛选默认
                tvGoodsListSelect.setTextColor(Color.parseColor("#333538"));
                //销量红色
                tvGoodsListSales.setTextColor(Color.parseColor("#333538"));

                if (click_count % 2 == 1) {
                    // 箭头向下红
                    ivGoodsListArrow.setBackgroundResource(R.drawable.icon_down_arrow);
                } else {
                    // 箭头向上红
                    ivGoodsListArrow.setBackgroundResource(R.drawable.icon_upward);
                }

                break;
            case R.id.ll_goods_list_sel://筛选

                //距离默认
                tvGoodsListPrice.setTextColor(Color.parseColor("#333538"));
                //筛选红色
                tvGoodsListSelect.setTextColor(Color.parseColor("#ed4141"));
                //销量默认
                tvGoodsListSales.setTextColor(Color.parseColor("#333538"));

                if (appBarState == AppBarStateChangeListener.State.EXPANDED) {
                    //展开状态
                    appBar.setExpanded(false);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            initSel(llGoodsListSel);
                        }
                    }, 500);
                } else if (appBarState == AppBarStateChangeListener.State.COLLAPSED) {
                    //折叠状态
                    initSel(llGoodsListSel);
                }

                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImages != null) {
            mImages.clear();
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
    public String merchId() {
        return null;
    }

    @Override
    public void onSuccess(O2oIndexBean o2oIndexBean) {

    }

    @Override
    public void onError(String message) {

        showShortToast(message);
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
    public String getstate() {
        return state;
    }

    @Override
    public int getpages() {
        return page;
    }

    @Override
    public String getsreen() {
        return sreens;
    }

    /***
     * 获取o2o首页数据
     * @param data
     */
    @Override
    public void onSuccessData(O2oHomeBean data) {

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
                    if (TextUtils.isEmpty(searchName) || searchName.equals("")) {
                        mPresenter.getO2oHome();
                    } else {//搜索数据
                        mPresenter.getSearchO2oHome();
                    }
                }
            });

            List<O2oHomeBean.CarouselBean> carousel = data.getCarousel();
            if (carousel != null && carousel.size() > 0) {

                ArrayList<String> strings = new ArrayList<>();

                for (int i = 0; i < carousel.size(); i++) {

                    strings.add(ApiUrlConstant.API_IMG_URL + carousel.get(i).getFile_path());
                }
                //设置轮播图
                banner.setImageLoader(new GlideImageLoader())
                        .setImages(strings)
                        .setDelayTime(5000)
                        .start();
            }

            List<O2oHomeBean.ListBean> lists = data.getList();
            count = lists.size();

            if (!isLoadingMore) {

                list = lists;

                if (list != null && list.size() > 0) {

//                    recyclerView.setVisibility(View.VISIBLE);

                    //设置附近商家的适配器
                    indexO2OAdapter = new IndexO2OAdapter(getActivity());
                    indexO2OAdapter.setList(list);
                    mLRecyclerViewAdapter = new LRecyclerViewAdapter(indexO2OAdapter);
                    recyclerView.setAdapter(mLRecyclerViewAdapter);

                    indexO2OAdapter.setO2oOnclickListener(new IndexO2OAdapter.O2oOnclickListener() {
                        @Override
                        public void o2oOnclickListener(View view, int position) {

                            Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                            intent.putExtra("merch_id", list.get(position).getMerch_id());
                            startActivity(intent);
                        }
                    });

                } else {

//                    recyclerView.setVisibility(View.GONE);
                    showShortToast("暂无商家信息");
                }

            } else {//加载更多数据

                isLoadingMore = false;
                if (lists.size() > 0) {

                    list.addAll(lists);
                    indexO2OAdapter.notifyDataSetChanged();
                    recyclerView.refreshComplete(lists.size());

                } else {

                    recyclerView.setNoMore(true);
                    showShortToast("没有更多数据了！");
                }
            }
        }
    }

    /***
     * 纬度
     * @return
     */
    @Override
    public String getlatitude() {

        String latitude = SPUtils.get(getActivity(), "latitude", "").toString();
        return latitude;
    }

    /***
     * 经度
     * @return
     */
    @Override
    public String getlongitude() {
        String longitude = SPUtils.get(getActivity(), "longitude", "").toString();
        return longitude;
    }


    @Override
    public String getSearchKey() {
        return searchName;
    }

    /**
     * 搜索首页数据展示
     *
     * @param data
     */
    @Override
    public void onSuccessSearch(O2oHomeBean data) {

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
                    if (TextUtils.isEmpty(searchName) || searchName.equals("")) {
                        mPresenter.getO2oHome();
                    } else {//搜索数据
                        mPresenter.getSearchO2oHome();
                    }
                }
            });

            List<O2oHomeBean.CarouselBean> carousel = data.getCarousel();
            if (carousel != null && carousel.size() > 0) {

                ArrayList<String> strings = new ArrayList<>();

                for (int i = 0; i < carousel.size(); i++) {

                    strings.add(ApiUrlConstant.API_IMG_URL + carousel.get(i).getFile_path());
                }
                //设置轮播图
                banner.setImageLoader(new GlideImageLoader())
                        .setImages(strings)
                        .setDelayTime(5000)
                        .start();
            }

            List<O2oHomeBean.ListBean> lists = data.getList();
            count = lists.size();

            if (!isLoadingMore) {

                list = lists;

                if (list != null && list.size() > 0) {

//                    recyclerView.setVisibility(View.VISIBLE);

                    //设置附近商家的适配器
                    indexO2OAdapter = new IndexO2OAdapter(getActivity());
                    indexO2OAdapter.setList(list);
                    mLRecyclerViewAdapter = new LRecyclerViewAdapter(indexO2OAdapter);
                    recyclerView.setAdapter(mLRecyclerViewAdapter);

                    indexO2OAdapter.setO2oOnclickListener(new IndexO2OAdapter.O2oOnclickListener() {
                        @Override
                        public void o2oOnclickListener(View view, int position) {

                            Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                            intent.putExtra("merch_id", list.get(position).getMerch_id());
                            startActivity(intent);
                        }
                    });

                } else {

//                    recyclerView.setVisibility(View.GONE);
                    showShortToast("没有搜索到相关店铺信息哦！");
                }

            } else {//加载更多数据

                isLoadingMore = false;
                if (lists.size() > 0) {

                    list.addAll(lists);
                    indexO2OAdapter.notifyDataSetChanged();
                    recyclerView.refreshComplete(lists.size());

                } else {

                    recyclerView.setNoMore(true);
                    showShortToast("没有更多数据了！");
                }
            }
        }
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                return;
            }

            //读取经纬度
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            city = location.getCity();

            SPUtils.put(getActivity(), "latitude", location.getLatitude());
            SPUtils.put(getActivity(), "longitude", location.getLongitude());
            SPUtils.put(BaseApplication.getContext(), "latitude", location.getLatitude());
            SPUtils.put(BaseApplication.getContext(), "longitude", location.getLongitude());
            String locationStr = locationTv.getText().toString().trim();
            if (locationStr.equals("")) {
                locationTv.setText(TextUtils.isEmpty(location.getCity()) ? "" : location.getCity());
            }
            if (TextUtils.isEmpty(searchName) || searchName.equals("")) {
            } else {
                if (!locationStr.equals(location.getCity())) {
                    showAlertMsgDialog("您是否切换到" + location.getCity(), "花返网", "确定", "取消");
                } else {
                    locationTv.setText(TextUtils.isEmpty(location.getCity()) ? "" : location.getCity());
                }
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {

        }
    }

    /**
     * @throws
     * @描述: 切换城市
     * @创建人：zhangpeisen
     * @创建时间：2018/1/15 下午3:04
     * @修改人：zhangpeisen
     * @修改时间：2018/1/15 下午3:04
     * @修改备注：
     */
    public void showAlertMsgDialog(String msg, String title, String positive, String negative) {
        AppPartnerAlertDialog alertDialog = new AppPartnerAlertDialog(getActivity()).builder().setTitle(title)
                .setMsg(msg).setPositiveButton(positive, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                    }
                }).setNegativeButton(negative, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        alertDialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(O2oHomeAddressEvent event) {
        if (null != event) {
            O2oAddressBean.ListsBean listsBean = event.getListsBean();
//            locationTv.setText(TextUtils.isEmpty(listsBean.getSite_name()) ? "": listsBean.getSite_name());
        }
    }


    /***
     * 友盟统计
     */
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("O2oIndexHomePage"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("O2oIndexHomePage");
    }

}

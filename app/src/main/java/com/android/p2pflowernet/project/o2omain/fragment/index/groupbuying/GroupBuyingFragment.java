package com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.GroupBuyingAdapter;
import com.android.p2pflowernet.project.adapter.GroupBuyingFiltrateAdapter;
import com.android.p2pflowernet.project.adapter.GroupBuyingGridviewAdapter;
import com.android.p2pflowernet.project.entity.DropBean;
import com.android.p2pflowernet.project.entity.GroupHomeBean;
import com.android.p2pflowernet.project.entity.MealsTypesBean;
import com.android.p2pflowernet.project.entity.O2oHomeBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying.shopdetails.ShopDetailsActivity;
import com.android.p2pflowernet.project.utils.SPUtils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.DropdownButton;
import com.android.p2pflowernet.project.view.customview.MyGridView;
import com.android.p2pflowernet.project.view.customview.NoScrollGridView;
import com.android.p2pflowernet.project.view.customview.PopWinDownUtil;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.android.p2pflowernet.project.R.id.db_goods_list_nearby;
import static com.android.p2pflowernet.project.R.id.iv_search;


/**
 * Created by heguozhong on 2017/12/28/028.
 * 团购优惠主界面
 */

public class GroupBuyingFragment extends KFragment<IGroupBuyingView, IGroupBuyingPresenter>
        implements PopWinDownUtil.OnDismissLisener, IGroupBuyingView {

    //火锅，甜品，自助餐gridview布局
    @BindView(R.id.gridview)
    NoScrollGridView gridview;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    //全部类别按钮
    @BindView(R.id.db_goods_list_sort)
    DropdownButton dbGoodsListSort;
    //附近商家
    @BindView(db_goods_list_nearby)
    DropdownButton nearbyListSort;
    //智能排序
    @BindView(R.id.db_goods_list_intelligence)
    DropdownButton db_intelligence;
    //筛选文字提示
    @BindView(R.id.tv_goods_list_select)
    TextView tvGoodsListSelect;
    //筛选图标
    @BindView(R.id.iv_goods_list_sel)
    ImageView ivGoodsListSel;
    //筛选linearlayout布局
    @BindView(R.id.ll_goods_list_sel)
    LinearLayout llGoodsListSel;
    //展示主要内容recyclerview
    @BindView(R.id.group_buying_recyclerview)
    LRecyclerView groupBuyingRecyclerview;
    //标题返回按钮
    @BindView(R.id.ll_back)
    LinearLayout llBack;

    //搜索输入框
    @BindView(R.id.tosearch_tv)
    EditText tosearchTv;
    //搜索图标
    @BindView(iv_search)
    ImageView ivSearch;

    private PopWinDownUtil popWinDownUtil;
    //装在筛选数据的集合
    private List<DropBean> sorts;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private ShapeLoadingDialog shapeLoadingDialog;
    private int pages = 1;//分页数
    private int zt = 1;//状态值 1
    private int yuyue;// 是否需要预约
    private int holiday_usable;//节假日可用
    private int weekend_usable;//周末可用
    private int is_new;//是否新品
    private int liebie = 0;//商家分类id
    private int district_id;//区域id
    private int state = 1;//状态 默认是1，1为返润最高2为评分最高
    private String city;//城市名称
    private String latitude;//纬度
    private String longitude;//经度
    private String fit_type = "";//适用类型
    private boolean isRefresh = false;//是否刷新
    private boolean isRebate = true;//返润最高
    private GroupHomeBean data;

    private List<GroupHomeBean.CategoryBean> categoryBeans;//分类数据
    private List<GroupHomeBean.CateAllBean> cateAllBeans;//全部数据分类
    private List<GroupHomeBean.DistrictBean> districtBeans;//附近商家区域数据
    private List<GroupHomeBean.ListBean> listBeans;//商品列表数据
    private GroupBuyingGridviewAdapter groupBuyingGridviewAdapter;//分类适配器
    private GroupBuyingAdapter groupBuyingAdapter;//商品列表适配器
    private Map<String, String> map;//存放全部分类 名称和对应ID
    private List<O2oHomeBean.ZongheBean> zongheBeans;//全部分类
    private Map<String, String> nearbyMap;//存放附近商家 区域名称和对应ID
    private List<O2oHomeBean.ZongheBean> nearbyBeans;//附近商家
    private List<O2oHomeBean.ZongheBean> intelligenceBeans;//智能排序
    private String[] intelligenceName = {"返润优先", "评分最高", "距离最近"};
    private String[] mealsTypes;//用餐类型
    private List<MealsTypesBean> mealsTypesBeans;//用餐类型实体
    private GroupBuyingFiltrateAdapter groupBuyingFiltrateAdapter;
    private String name;
    private String tag = "0";//0-正常外卖首页数据 1-外卖首页搜索数据

    public static GroupBuyingFragment newInstance(String city) {
        Bundle args = new Bundle();
        args.putString("city", city);
        GroupBuyingFragment fragment = new GroupBuyingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        city = getArguments().getString("city");
    }

    @Override
    public IGroupBuyingPresenter createPresenter() {
        return new IGroupBuyingPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_group_buying;
    }

    @Override
    public void initData() {
        mPresenter.getGroupHomeData();
        for (int i = 0; i < mealsTypes.length; i++) {
            MealsTypesBean bean = new MealsTypesBean();
            bean.setName(mealsTypes[i]);
            bean.setSelected(false);
            mealsTypesBeans.add(bean);
        }
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        //初始化沉浸式
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.coloro2o));

        mealsTypes = getResources().getStringArray(R.array.NumberOfMeals);
        categoryBeans = new ArrayList<>();
        cateAllBeans = new ArrayList<>();
        districtBeans = new ArrayList<>();
        listBeans = new ArrayList<>();
        map = new HashMap<>();
        zongheBeans = new ArrayList<>();
        nearbyMap = new HashMap<>();
        nearbyBeans = new ArrayList<>();
        mealsTypesBeans = new ArrayList<>();

        //智能排序设置数据源
        intelligenceBeans = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            O2oHomeBean.ZongheBean zongheBean = new O2oHomeBean.ZongheBean();
            zongheBean.setChoiced(false);
            zongheBean.setName(intelligenceName[i]);
            zongheBean.setState(i + 1);
            intelligenceBeans.add(zongheBean);
        }
        db_intelligence.setData(intelligenceBeans);

        //取出经纬度
        latitude = SPUtils.get(getActivity(), "latitude", "").toString();
        longitude = SPUtils.get(getActivity(), "longitude", "").toString();

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .delay(5000)
                .loadText("加载中...")
                .build();

        //获取布局管理者
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //设置为垂直排列格式
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //recyclerview应用垂直排列格式
        groupBuyingRecyclerview.setLayoutManager(linearLayoutManager);
        //设置适配器
        groupBuyingAdapter = new GroupBuyingAdapter(getActivity(), listBeans);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(groupBuyingAdapter);
        groupBuyingRecyclerview.setAdapter(mLRecyclerViewAdapter);

        //禁止下拉刷新
        groupBuyingRecyclerview.setPullRefreshEnabled(false);
        groupBuyingRecyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                new Handler().postDelayed(new Runnable() {
                    public void run() {

                    }
                }, 2000);
            }
        });
        //每个item的点击事件
        groupBuyingAdapter.setOnEveryItemClickListener(new GroupBuyingAdapter.OnEveryItemClickListener() {
            @Override
            public void onEveryItemClick(View v, int position) {

                //跳转详情页面
                Intent intent = new Intent(getActivity(), ShopDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("group_info", data.getList().get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //给火锅，甜品，自助餐......gridview设置适配器
        groupBuyingGridviewAdapter = new GroupBuyingGridviewAdapter(getActivity(), categoryBeans);
        gridview.setAdapter(groupBuyingGridviewAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                liebie = Integer.parseInt(map.get(zongheBeans.get(position + 1).getName()));
                if (tag.equals("0")) {
                    mPresenter.getGroupHomeData();
                } else {
                    mPresenter.searchGroupHomeData();
                }

                int height = toolbarLayout.getHeight();
                if (height > 0) {
                    toolbarLayout.setVisibility(View.GONE);
                }

                dbGoodsListSort.setText(cateAllBeans.get(position + 1).getName());
            }
        });

        groupBuyingRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                toolbarLayout.setVisibility(View.VISIBLE);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        //给全部类别按钮赋值
        dbGoodsListSort.setOnDropItemSelectListener(new DropdownButton.OnDropItemSelectListener() {
            @Override
            public void onDropItemSelect(int Postion) {
                isRefresh = false;
                pages = 1;
                liebie = Integer.parseInt(map.get(zongheBeans.get(Postion).getName()));
                if (tag.equals("0")) {
                    mPresenter.getGroupHomeData();
                } else {
                    mPresenter.searchGroupHomeData();
                }

            }
        });

        //给附近商家按钮赋值
        nearbyListSort.setOnDropItemSelectListener(new DropdownButton.OnDropItemSelectListener() {
            @Override
            public void onDropItemSelect(int Postion) {
                isRefresh = false;
                pages = 1;
                district_id = Integer.parseInt(nearbyMap.get(nearbyBeans.get(Postion).getName()));
                if (tag.equals("0")) {
                    mPresenter.getGroupHomeData();
                } else {
                    mPresenter.searchGroupHomeData();
                }

            }
        });

        //给智能排序按钮复制
        db_intelligence.setOnDropItemSelectListener(new DropdownButton.OnDropItemSelectListener() {
            @Override
            public void onDropItemSelect(int Postion) {
                isRefresh = false;
                pages = 1;
                state = intelligenceBeans.get(Postion).getState();
                if (tag.equals("0")) {
                    mPresenter.getGroupHomeData();
                } else {
                    mPresenter.searchGroupHomeData();
                }
            }
        });

        tosearchTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start == 0 && count == 0) {
                    tag = 0 + "";
                    mPresenter.getGroupHomeData();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tosearchTv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    // 当按了搜索之后关闭软键盘
                    ((InputMethodManager) tosearchTv.getContext().getSystemService(
                            Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    name = tosearchTv.getText().toString().trim();

                    tag = 1 + "";
                    mPresenter.searchGroupHomeData();
                    return true;
                }
                return false;
            }
        });

        //搜索图标点击，可以进行搜索
        ivSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                name = tosearchTv.getText().toString().trim();
                mPresenter.searchGroupHomeData();
            }
        });

        //初始化数据
        initData();

        initRecyclerView();
    }

    private void initRecyclerView() {
        groupBuyingRecyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pages++;
                isRefresh = true;
                mPresenter.getGroupHomeData();
            }
        });
    }

    @OnClick({R.id.ll_back, R.id.ll_goods_list_sel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //标题返回按钮监听
            case R.id.ll_back:
                removeFragment();
                break;
            //筛选排序点击事件
            case R.id.ll_goods_list_sel:
//                tvGoodsListSelect.setTextColor(Color.RED);
                initSel(view);
                break;

        }
    }

    //筛选弹窗
    private void initSel(View llGoodsListSel) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.groupbuying_dropdown_goods_sel, null);
        Button fram_reset_but = (Button) view.findViewById(R.id.fram_reset_but);//重置
        Button fram_ok_but = (Button) view.findViewById(R.id.fram_ok_but);//完成
        final CheckBox checkboxZkxyy = (CheckBox) view.findViewById(R.id.checkbox_zkxyy);//只看免预约
        final CheckBox checkboxJjrky = (CheckBox) view.findViewById(R.id.checkbox_jjrky);//节假日可用
        final CheckBox checkboxZlrky = (CheckBox) view.findViewById(R.id.checkbox_zlrky);//周六日可用
        final CheckBox checkboxZkxp = (CheckBox) view.findViewById(R.id.checkbox_zkxp);//只看新品
        MyGridView gridviewYcrs = (MyGridView) view.findViewById(R.id.gridview_ycrs);//用餐人数gridview
        //完成
        fram_ok_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRefresh = false;
                pages = 1;
                if (checkboxZkxyy.isChecked()) {
                    yuyue = 1;
                } else {
                    yuyue = 0;
                }
                if (checkboxJjrky.isChecked()) {
                    holiday_usable = 1;
                } else {
                    holiday_usable = 0;
                }
                if (checkboxZlrky.isChecked()) {
                    weekend_usable = 1;
                } else {
                    weekend_usable = 0;
                }
                if (checkboxZkxp.isChecked()) {
                    is_new = 1;
                } else {
                    is_new = 0;
                }
                if (tag.equals("0")) {
                    mPresenter.getGroupHomeData();
                } else {
                    mPresenter.searchGroupHomeData();
                }
                popWinDownUtil.hide();
            }
        });
        //重置
        fram_reset_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置按钮状态
                checkboxZkxyy.setChecked(false);
                checkboxJjrky.setChecked(false);
                checkboxZlrky.setChecked(false);
                checkboxZkxp.setChecked(false);

                for (int i = 0; i < mealsTypesBeans.size(); i++) {
                    mealsTypesBeans.get(i).setSelected(false);
                }
                groupBuyingFiltrateAdapter.notifyDataSetChanged();

                //设置值
                yuyue = 0;
                holiday_usable = 0;
                weekend_usable = 0;
                is_new = 0;
                fit_type = "";

            }
        });
        //设置Gridview的适配器
        groupBuyingFiltrateAdapter = new GroupBuyingFiltrateAdapter(getActivity(), mealsTypesBeans);
        gridviewYcrs.setAdapter(groupBuyingFiltrateAdapter);
        gridviewYcrs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //设置按钮是否选中
                for (int i = 0; i < mealsTypesBeans.size(); i++) {
                    MealsTypesBean typesBean = mealsTypesBeans.get(i);
                    if (i == position) {
                        typesBean.setSelected(true);
                    } else {
                        typesBean.setSelected(false);
                    }
                }

                //根据选中的item,获取用餐类型
                MealsTypesBean mealsTypesBean = mealsTypesBeans.get(position);
                if (mealsTypes[0].equals(mealsTypesBean.getName())) {
                    fit_type = "0";
                } else if (mealsTypes[1].equals(mealsTypesBean.getName())) {
                    fit_type = "1";
                } else if (mealsTypes[2].equals(mealsTypesBean.getName())) {
                    fit_type = "2";
                } else if (mealsTypes[3].equals(mealsTypesBean.getName())) {
                    fit_type = "3";
                } else if (mealsTypes[4].equals(mealsTypesBean.getName())) {
                    fit_type = "4";
                } else if (mealsTypes[5].equals(mealsTypesBean.getName())) {
                    fit_type = "5";
                }

                //刷新适配器
                groupBuyingFiltrateAdapter.notifyDataSetChanged();
            }
        });


        popWinDownUtil = new PopWinDownUtil(getActivity(), view, llGoodsListSel);
        popWinDownUtil.setOnDismissListener(this);
        popWinDownUtil.show();
    }

    @Override
    public void onDismiss() {
        if (popWinDownUtil != null && popWinDownUtil.isShowing()) {
            popWinDownUtil.hide();
        }
    }

    @Override
    public void onError(String string) {

        showShortToast(string);
    }

    @Override
    public void onSuccess(GroupHomeBean data) {
        if (null != data) {
            this.data = data;

            //分类栏设置数据并刷新适配器
            if (categoryBeans.size() == 0) {

                categoryBeans.addAll(data.getCategory());
                groupBuyingGridviewAdapter.notifyDataSetChanged();
            }

            //商品列表设置数据并刷新
            if (!isRefresh) {

                listBeans.clear();
                listBeans.addAll(data.getList());

                if (listBeans.size() == 0) {

                    groupBuyingRecyclerview.setVisibility(View.GONE);
                    showShortToast("暂无商家信息");

                } else {

                    groupBuyingRecyclerview.setVisibility(View.VISIBLE);
                    groupBuyingAdapter.notifyDataSetChanged();
                }

            } else {

                groupBuyingRecyclerview.setVisibility(View.VISIBLE);
                if (data.getList().size() > 0) {

                    listBeans.addAll(data.getList());
                    groupBuyingAdapter.notifyDataSetChanged();

                } else {

                    groupBuyingRecyclerview.setNoMore(true);
                    showShortToast("没有更多数据了！");
                }
            }

            //全部数据分类
            if (cateAllBeans.size() == 0) {

                cateAllBeans.addAll(data.getCateAll());

                for (int i = 0; i < cateAllBeans.size(); i++) {

                    GroupHomeBean.CateAllBean cateAllBean = cateAllBeans.get(i);
                    map.put(cateAllBean.getName(), cateAllBean.getCate_id());
                    O2oHomeBean.ZongheBean zongheBean = new O2oHomeBean.ZongheBean();
                    zongheBean.setName(cateAllBean.getName());
                    zongheBean.setChoiced(false);
                    zongheBeans.add(zongheBean);

                }

                dbGoodsListSort.setData(zongheBeans);
            }

            //附近商家
            if (districtBeans.size() == 0) {

                districtBeans.addAll(data.getDistrict());
                for (int i = 0; i < districtBeans.size(); i++) {
                    GroupHomeBean.DistrictBean districtBean = districtBeans.get(i);
                    O2oHomeBean.ZongheBean zongheBean = new O2oHomeBean.ZongheBean();
                    zongheBean.setName(districtBean.getRegion_name());
                    zongheBean.setChoiced(false);
                    nearbyMap.put(districtBean.getRegion_name(), districtBean.getId());
                    nearbyBeans.add(zongheBean);
                }
                nearbyListSort.setData(nearbyBeans);
            }
        }
    }

    //搜索成功方法
    @Override
    public void onSearchSuccess(GroupHomeBean data) {
        if (null != data) {

        }
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
    public int getPages() {
        return pages;
    }

    @Override
    public int getZt() {
        return zt;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public String getCity() {
        return city;
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
    public int getYuyue() {
        return yuyue;
    }

    @Override
    public int getHolidayUsable() {
        return holiday_usable;
    }

    @Override
    public int getWeekendUsable() {
        return weekend_usable;
    }

    @Override
    public int isNew() {
        return is_new;
    }

    @Override
    public String getFitType() {
        return fit_type;
    }

    @Override
    public int getLiebie() {
        return liebie;
    }

    @Override
    public int getDistrictId() {
        return district_id;
    }

    @Override
    public String getName() {
        return name;
    }

    /***
     * 友盟统计
     */
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("GroupBuyPage"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("GroupBuyPage");
    }
}

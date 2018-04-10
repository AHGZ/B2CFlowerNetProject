package com.android.p2pflowernet.project.view.fragments.goods.goodslist;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.ClassifyAdapter;
import com.android.p2pflowernet.project.adapter.RightSideslipLayAdapter;
import com.android.p2pflowernet.project.callback.AdapterLoader;
import com.android.p2pflowernet.project.callback.AllSortDatasLinstener;
import com.android.p2pflowernet.project.callback.CbSelectLinstener;
import com.android.p2pflowernet.project.entity.AllSortBean;
import com.android.p2pflowernet.project.entity.BrandSortBean;
import com.android.p2pflowernet.project.entity.ClassifBean;
import com.android.p2pflowernet.project.event.GoodListSearchEvent;
import com.android.p2pflowernet.project.event.MainEvent;
import com.android.p2pflowernet.project.event.RefreshAllBrandSort;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.activity.GoodsDetailActivity;
import com.android.p2pflowernet.project.view.activity.RightSideslipChildLay;
import com.android.p2pflowernet.project.view.customview.DropDownMenu;
import com.android.p2pflowernet.project.view.customview.MyListView;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.TopMiddlePopup;
import com.android.p2pflowernet.project.view.fragments.search.SearchActivity;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.android.p2pflowernet.project.R.id.ib_drawer_layout_back;
import static com.android.p2pflowernet.project.R.id.recyclerview;


/**
 * Created by caishen on 2017/10/23.
 * by--商品筛选列表
 */

public class GoodsListFragment extends KFragment<IGoodLIstView, IGoodsListPrenter>
        implements IGoodLIstView, AdapterLoader.OnItemClickListener<ClassifBean.GoodsBean> {


    @BindView(R.id.ic_back)
    ImageView icBack;
    @BindView(R.id.location_linear)
    LinearLayout locationLinear;
    @BindView(R.id.tosearch_tv)
    TextView tosearchTv;
    @BindView(R.id.message_linear)
    LinearLayout messageLinear;
    @BindView(R.id.ll_goods_list_head)
    LinearLayout llGoodsListHead;
    @BindView(R.id.tv_goods_list_sort)
    TextView tvGoodsListSort;
    @BindView(R.id.iv_sort)
    ImageView ivSort;
    @BindView(R.id.ll_goods_list_)
    LinearLayout llGoodsList;
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
    @BindView(R.id.ll_goods_list_price)
    LinearLayout llGoodsListPrice;
    @BindView(R.id.tv_goods_list_select)
    TextView tvGoodsListSelect;
    @BindView(R.id.iv_goods_list_sel)
    ImageView ivGoodsListSel;
    @BindView(R.id.ll_goods_list_sel)
    LinearLayout llGoodsListSel;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.mainrecyclerView)
    LRecyclerView mRecyclerView;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.select_brand_back_im)
    ImageView selectBrandBackIm;
    @BindView(R.id.topbar_lay)
    RelativeLayout topbarLay;
    @BindView(R.id.fram_reset_but)
    Button framResetBut;
    @BindView(R.id.fram_ok_but)
    Button framOkBut;
    @BindView(R.id.selsectFrameLV)
    MyListView selsectFrameLV;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.ll_select_root)
    RelativeLayout llSelectRoot;
    @BindView(R.id.btn_drawer_layout_cancel)
    Button btnDrawerLayoutCancel;
    @BindView(R.id.btn_drawer_layout_confirm)
    Button btnDrawerLayoutConfirm;
    @BindView(R.id.iv_price_no_limit)
    ImageView ivPriceNoLimit;
    @BindView(R.id.rl_price_nolimit)
    RelativeLayout rlPriceNolimit;
    @BindView(R.id.iv_price_0_15)
    ImageView ivPrice015;
    @BindView(R.id.rl_price_0_15)
    RelativeLayout rlPrice015;
    @BindView(R.id.iv_price_15_30)
    ImageView ivPrice1530;
    @BindView(R.id.rl_price_15_30)
    RelativeLayout rlPrice1530;
    @BindView(R.id.iv_price_30_50)
    ImageView ivPrice3050;
    @BindView(R.id.rl_price_30_50)
    RelativeLayout rlPrice3050;
    @BindView(R.id.iv_price_50_70)
    ImageView ivPrice5070;
    @BindView(R.id.rl_price_50_70)
    RelativeLayout rlPrice5070;
    @BindView(R.id.iv_price_70_100)
    ImageView ivPrice70100;
    @BindView(R.id.rl_price_70_100)
    RelativeLayout rlPrice70100;
    @BindView(R.id.iv_price_100)
    ImageView ivPrice100;
    @BindView(R.id.rl_price_100)
    RelativeLayout rlPrice100;
    @BindView(R.id.et_price_start)
    EditText etPriceStart;
    @BindView(R.id.v_price_line)
    View vPriceLine;
    @BindView(R.id.et_price_end)
    EditText etPriceEnd;
    @BindView(R.id.rl_select_price)
    RelativeLayout rlSelectPrice;
    @BindView(R.id.ll_price_root)
    LinearLayout llPriceRoot;
    @BindView(R.id.btn_drawer_theme_cancel)
    Button btnDrawerThemeCancel;
    @BindView(R.id.btn_drawer_theme_confirm)
    Button btnDrawerThemeConfirm;
    @BindView(R.id.iv_theme_all)
    ImageView ivThemeAll;
    @BindView(R.id.rl_theme_all)
    RelativeLayout rlThemeAll;
    @BindView(R.id.iv_theme_note)
    ImageView ivThemeNote;
    @BindView(R.id.rl_theme_note)
    RelativeLayout rlThemeNote;
    @BindView(R.id.iv_theme_funko)
    ImageView ivThemeFunko;
    @BindView(R.id.rl_theme_funko)
    RelativeLayout rlThemeFunko;
    @BindView(R.id.iv_theme_gsc)
    ImageView ivThemeGsc;
    @BindView(R.id.rl_theme_gsc)
    RelativeLayout rlThemeGsc;
    @BindView(R.id.iv_theme_origin)
    ImageView ivThemeOrigin;
    @BindView(R.id.rl_theme_origin)
    RelativeLayout rlThemeOrigin;
    @BindView(R.id.iv_theme_sword)
    ImageView ivThemeSword;
    @BindView(R.id.rl_theme_sword)
    RelativeLayout rlThemeSword;
    @BindView(R.id.iv_theme_food)
    ImageView ivThemeFood;
    @BindView(R.id.rl_theme_food)
    RelativeLayout rlThemeFood;
    @BindView(R.id.iv_theme_moon)
    ImageView ivThemeMoon;
    @BindView(R.id.rl_theme_moon)
    RelativeLayout rlThemeMoon;
    @BindView(R.id.iv_theme_quanzhi)
    ImageView ivThemeQuanzhi;
    @BindView(R.id.rl_theme_quanzhi)
    RelativeLayout rlThemeQuanzhi;
    @BindView(R.id.iv_theme_gress)
    ImageView ivThemeGress;
    @BindView(R.id.rl_theme_gress)
    RelativeLayout rlThemeGress;
    @BindView(R.id.ll_theme_root)
    LinearLayout llThemeRoot;
    @BindView(R.id.btn_drawer_type_cancel)
    Button btnDrawerTypeCancel;
    @BindView(R.id.btn_drawer_type_confirm)
    Button btnDrawerTypeConfirm;
    @BindView(R.id.expandableListView)
    ExpandableListView expandableListView;
    @BindView(R.id.ll_type_root)
    LinearLayout llTypeRoot;
    @BindView(R.id.dl_left)
    DrawerLayout dlLeft;
    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    @BindView(R.id.cb_isnew)
    CheckBox cbIsNew;
    @BindView(R.id.et_low_price)
    EditText et_low_price;
    @BindView(R.id.et_height_price)
    EditText et_height_price;
    @BindView(R.id.tv_rebut)
    TextView tvRebut;
    @BindView(R.id.ll_rebut)
    LinearLayout llRebut;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    private int click_count = 0;
    private TopMiddlePopup middlePopup;
    private int screenW;
    private int screenH;
    private RightSideslipLayAdapter slidLayFrameAdapter;
    private ListView selectList;
    // 筛选过后的变化的界面
    private View contentView = null;
    // 进度条
    private ShapeLoadingDialog shapeLoadingDialog;
    private RecyclerView recyclerView;
    private View inflateview;
    private List<ClassifBean.GoodsBean> goods;
    private ClassifyAdapter classifyAdapter;
    private int count = 10;//数据个数
    private int page = 1;//翻页个数
    private boolean isLoad = false;//是否是第一次加载数据
    private List<ClassifBean.GoodsBean> datas;
    // 品牌筛选id
    private String cate_id;
    private String mId;
    //  筛选状态 score_total,sales_num,price
    private String sift = "score_total";
    List<BrandSortBean.ListsBeanX.ListsBean> BranditemListsBean;
    private String order = "0";
    private String searchName = "";//搜索关键词
    private String tag = "0";//0-商品三级列表数据
    private List<BrandSortBean.ListsBeanX> lists;//筛选数据
    private String sealePrice = "0";//价格区间
    private List<AllSortBean.ListsBean> allSor;//全部分类数据
    private String isNew = "0";//是否新品 1为新品0为全部
    private String brand_id = "0";//品牌id
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;

    public static GoodsListFragment newInstance(String id, String tag, String searchName) {
        Bundle args = new Bundle();
        GoodsListFragment fragment = new GoodsListFragment();
        args.putString("id", id);
        args.putString("tag", tag);
        args.putString("searchName", searchName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = getArguments().getString("id");
        searchName = getArguments().getString("searchName");
        tag = getArguments().getString("tag");
    }

    @Override
    public IGoodsListPrenter createPresenter() {

        return new IGoodsListPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_goods_list;
    }

    @Override
    public void initData() {

        if (tag.equals("0")) {//三级列表

            mPresenter.GdThreeLists();

        } else {//搜索关键词

            mPresenter.getSerChList();
        }

        showSelectorLayout();//初始筛选化侧滑菜单数据
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        getScreenPixels();
        //让布局向上移来显示软键盘
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        UIUtils.setTouchDelegate(icBack, 50);
        //初始化沉浸栏
        Utils.setStatusBar(getActivity(), 0, false);
        linearLayoutManager = new LinearLayoutManager(getActivity());

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(10000)
                .build();

        selectList = (ListView) view.findViewById(R.id.selsectFrameLV);
        llSelectRoot.setVisibility(View.VISIBLE);
        contentView = View.inflate(getActivity(), R.layout.brand_goodslist_fragment, null);
        inflateview = View.inflate(getActivity(), R.layout.meunitem_layout, null);
        recyclerView = (RecyclerView) inflateview.findViewById(recyclerview);

        TextView ok = (TextView) inflateview.findViewById(R.id.ok);
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset >= 0) {

                } else {

                }
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDropDownMenu.closeMenu();
            }
        });

        //添加分割线
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = 0;
                // top bottom left right 对应的值应该是dpi 而不是dp  dpi根据不同手机而不同

                int i = parent.getChildLayoutPosition(view) % 2;//每行2个
                switch (i) {
                    case 0://第一个
                        outRect.left = convertDpToPixel(0);
                        outRect.right = convertDpToPixel(0);
                        outRect.bottom = convertDpToPixel(5);
                        break;
                    case 1://第二个
                        outRect.left = convertDpToPixel(5);
                        outRect.right = convertDpToPixel(0);
                        outRect.bottom = convertDpToPixel(5);
                        break;
                }
            }
        });


        //初始化数据
        initData();

        //初始化刷新
        initClick();
    }

    //初始化监听
    private void initClick() {

        //设置是否新品的事件
        cbIsNew.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isNew = "1";
                } else {
                    isNew = "0";
                }
            }
        });

        /**
         * 自定义RecyclerView实现对AppBarLayout的滚动效果
         */
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int visiblePosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                    if (visiblePosition == 0) {
                        appbar.setExpanded(true, true);
                    }
                }
            }
        });

        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式

        //禁止下拉刷新
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                if (classifyAdapter != null) {

                    if (count >= classifyAdapter.getItemCount()) {

                        isLoad = true;
                        page += 1;
                        if (tag.equals("0")) {
                            mPresenter.GdThreeLists();
                        } else {
                            mPresenter.getSerChList();
                        }

                    } else {

                        mRecyclerView.setNoMore(true);
                        showShortToast("没有更多数据了！");
                    }
                }
            }
        });

        dlLeft.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

                if (lists != null && lists.size() > 0) {
                    setUpList(lists);
                } else {
                    mPresenter.clickFilter();
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MainEvent event) {

        int str = event.getStr();
        if (str == 3) {
            removeFragment();//购物车
        }
    }

    @OnClick({R.id.ll_goods_list_price, R.id.rl_select_price, R.id.btn_drawer_layout_cancel, R.id.btn_drawer_layout_confirm,
            R.id.btn_drawer_theme_cancel, R.id.btn_drawer_theme_confirm, R.id.btn_drawer_type_cancel,
            R.id.btn_drawer_type_confirm, R.id.tv_goods_list_select, R.id.ic_back, R.id.tv_goods_list_sort
            , R.id.tosearch_tv, R.id.message_linear, R.id.tv_goods_list_sales, R.id.fram_ok_but, R.id.fram_reset_but, R.id.ll_rebut})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ic_back:
                removeFragment();

                break;

            case R.id.tosearch_tv://搜索

                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("tag", "3");//0-b2c 1-o2o 2-o2o外卖 3-商品分类
                startActivity(intent);
//                removeFragment();

                break;
            case R.id.message_linear://消息

                break;
            case R.id.tv_goods_list_sort://综合排序点击事件

                sift = "score_total";
                click_count = 0;
                //销量默认
                tvGoodsListSales.setTextColor(Color.parseColor("#333538"));
                //价格默认
                tvGoodsListPrice.setTextColor(Color.parseColor("#333538"));
                //筛选默认
                tvGoodsListSelect.setTextColor(Color.parseColor("#333538"));
                //综合红
                tvGoodsListSort.setTextColor(Color.parseColor("#ed4141"));
                //返润默认
                tvRebut.setTextColor(Color.parseColor("#333538"));

                ivSort.setImageDrawable(getResources().getDrawable(R.drawable.icon_down_arrow));

//                setPopup(0);
//                middlePopup.show(tvGoodsListSort);
                if (tag.equals("0")) {//三级列表

                    mPresenter.GdThreeLists();

                } else {//搜索关键词

                    mPresenter.getSerChList();
                }
                break;
            case R.id.tv_goods_list_sales://销量
                sift = "sales_num";
                //综合默认
                tvGoodsListSort.setTextColor(Color.parseColor("#333538"));
                ivSort.setImageDrawable(getResources().getDrawable(R.drawable.icon_default_down));
                //价格默认
                tvGoodsListPrice.setTextColor(Color.parseColor("#333538"));
                //筛选默认
                tvGoodsListSelect.setTextColor(Color.parseColor("#333538"));
                //销量红色
                tvGoodsListSales.setTextColor(Color.parseColor("#ed4141"));
                //返润默认
                tvRebut.setTextColor(Color.parseColor("#333538"));

                if (tag.equals("0")) {//三级列表

                    mPresenter.GdThreeLists();

                } else {//搜索关键词

                    mPresenter.getSerChList();
                }
                break;
            case R.id.ll_goods_list_price://价格排序
                sift = "sale_price";
                //价格点击事件
                click_count++;
                //综合排序变为默认
                tvGoodsListSort.setTextColor(Color.parseColor("#333538"));
                ivSort.setImageDrawable(getResources().getDrawable(R.drawable.icon_default_down));
                //价格红
                tvGoodsListPrice.setTextColor(Color.parseColor("#ed4141"));
                //销量默认
                tvGoodsListSales.setTextColor(Color.parseColor("#333538"));
                //筛选默认
                tvGoodsListSelect.setTextColor(Color.parseColor("#333538"));
                //返润默认
                tvRebut.setTextColor(Color.parseColor("#333538"));

                if (click_count % 2 == 1) {
                    order = "2";
                    // 箭头向下红
                    ivGoodsListArrow.setBackgroundResource(R.drawable.icon_down_arrow);
                } else {
                    order = "1";
                    // 箭头向上红
                    ivGoodsListArrow.setBackgroundResource(R.drawable.icon_upward);
                }
                if (tag.equals("0")) {//三级列表

                    mPresenter.GdThreeLists();

                } else {//搜索关键词

                    mPresenter.getSerChList();
                }

                break;
            case R.id.rl_select_price://价格筛选页面

                //价格筛选的页面
                llPriceRoot.setVisibility(View.VISIBLE);
                showPriceLayout();

                break;
            case R.id.btn_drawer_layout_cancel:

                Toast.makeText(getActivity(), "取消", Toast.LENGTH_SHORT).show();
                llSelectRoot.setVisibility(View.VISIBLE);
                showSelectorLayout();

                break;
            case R.id.btn_drawer_layout_confirm:

                Toast.makeText(getActivity(), "确认", Toast.LENGTH_SHORT).show();

                break;
            case R.id.btn_drawer_theme_cancel:

                llSelectRoot.setVisibility(View.VISIBLE);
                showSelectorLayout();

                break;
            case R.id.btn_drawer_theme_confirm:

                Toast.makeText(getActivity(), "确认", Toast.LENGTH_SHORT).show();

                break;
            case R.id.btn_drawer_type_cancel:

                Toast.makeText(getActivity(), "取消", Toast.LENGTH_SHORT).show();
                llSelectRoot.setVisibility(View.VISIBLE);
                showSelectorLayout();

                break;
            case R.id.btn_drawer_type_confirm:

                Toast.makeText(getActivity(), "确认", Toast.LENGTH_SHORT).show();

                break;
            case ib_drawer_layout_back:

                dlLeft.closeDrawers();

                break;
            case R.id.tv_goods_list_select://筛选按钮的点击事件

                if (tag.equals("0")) {//默认三级列表数据

                    if (lists != null && lists.size() > 0) {
                        setUpList(lists);
                    } else {
                        mPresenter.clickFilter();
                    }

                } else {//搜索关键词数据

                    if (lists != null && lists.size() > 0) {
                        setUpList(lists);
                    } else {
                        mPresenter.getSerChList();
                    }
                }

                //筛选红色
                tvGoodsListSelect.setTextColor(Color.parseColor("#ed4141"));
                //销量默认
                tvGoodsListSales.setTextColor(Color.parseColor("#333538"));
                //综合默认
                tvGoodsListSort.setTextColor(Color.parseColor("#333538"));
                ivSort.setImageDrawable(getResources().getDrawable(R.drawable.icon_default_down));
                //价格默认
                tvGoodsListPrice.setTextColor(Color.parseColor("#333538"));
                //返润默认
                tvRebut.setTextColor(Color.parseColor("#333538"));

                dlLeft.openDrawer(Gravity.RIGHT);

                break;
            case R.id.fram_reset_but://重置按钮

                resetData();

                break;
            case R.id.fram_ok_but://确定按钮

                if (TextUtils.isEmpty(et_low_price.getText().toString()) &&
                        !TextUtils.isEmpty(et_height_price.getText().toString().trim())) {
                    showShortToast("请填写最低价");
                    return;
                } else if (TextUtils.isEmpty(et_height_price.getText().toString().trim()) &&
                        !TextUtils.isEmpty(et_low_price.getText().toString().trim())) {
                    showShortToast("请填写最高价");
                    return;
                }

                //收回侧滑
                dlLeft.closeDrawer(GravityCompat.END);

                if (tag.equals("0")) {//三级列表
                    mPresenter.GdThreeLists();
                } else {//搜索关键词
                    mPresenter.getSerChList();
                }

                break;
            case R.id.ll_rebut://返润

                sift = "rebate_money";

                //综合默认
                tvGoodsListSort.setTextColor(Color.parseColor("#333538"));
                //返润
                tvRebut.setTextColor(Color.parseColor("#ed4141"));
                //价格默认
                tvGoodsListPrice.setTextColor(Color.parseColor("#333538"));
                //筛选默认
                tvGoodsListSelect.setTextColor(Color.parseColor("#333538"));
                //销量默认
                tvGoodsListSales.setTextColor(Color.parseColor("#333538"));

                if (tag.equals("0")) {//默认三级列表数据

                    if (lists != null && lists.size() > 0) {
                        setUpList(lists);
                    } else {
                        mPresenter.clickFilter();
                    }

                } else {//搜索关键词数据

                    if (lists != null && lists.size() > 0) {
                        setUpList(lists);
                    } else {
                        mPresenter.getSerChList();
                    }
                }

                break;
        }
    }


    //重置侧滑数据
    private void resetData() {

        et_height_price.setText("");
        et_low_price.setText("");
        cbIsNew.setChecked(false);
        brand_id = "0";

        if (lists == null || lists.size() <= 0) {
            return;
        }

        //重置分类数据
        for (int i = 0; i < lists.size(); i++) {
            //重置右上角商品选中以后的显示信息
//            lists.get(i).setName("");
            List<BrandSortBean.ListsBeanX.ListsBean> list = lists.get(i).getLists();
            for (int i1 = 0; i1 < list.size(); i1++) {
                list.get(i1).setChick(false);
            }
        }

        if (allSor != null && allSor.size() > 0) {

            //重置全部分类数据
            for (int i = 0; i < allSor.size(); i++) {
                allSor.get(i).setChick(false);
            }

            //发送消息刷新数据
            EventBus.getDefault().post(new RefreshAllBrandSort());
        }

        if (slidLayFrameAdapter != null) {

            slidLayFrameAdapter.notifyDataSetChanged();
            RightSideslipLayAdapter.mSelectvalues.setText("");
        }
    }

    /**
     * 设置弹窗
     *
     * @param type
     */
    private void setPopup(int type) {
        middlePopup = new TopMiddlePopup(getActivity(), screenW, screenH,
                onItemClickListener, getItemsName(), type);
    }


    @Override
    public void onBrandSortSuccess(BrandSortBean brandSortBean) {
        //初始化侧滑菜单数据
        if (brandSortBean == null) {
            return;
        }
        lists = brandSortBean.getLists();
        if (lists == null || lists.isEmpty()) {
            return;
        }
        setUpList(lists);
    }

    @Override
    public void onAllBrandSortSuccess(AllSortBean allSortBean) {
        // 加载全部菜单数据
        if (allSortBean == null) {
            return;
        }
        allSor = allSortBean.getLists();
        if (allSor == null || allSor.isEmpty()) {
            return;
        }
        getPopupWindow(allSor);
    }

    @Override
    public String getSearchName() {
        return searchName;
    }

    @Override
    public String getLowPrice() {
        return et_low_price.getText().toString().trim();
    }

    @Override
    public String getHeightPrice() {

        return et_height_price.getText().toString().trim();
    }

    private void setUpList(List<BrandSortBean.ListsBeanX> brandSortBeanLists) {

        //关闭品牌筛选的弹窗
        if (mDropDownMenu != null) {
            mDropDownMenu.closeMenu();
        }
        if (slidLayFrameAdapter == null) {
            slidLayFrameAdapter = new RightSideslipLayAdapter(getActivity(), brandSortBeanLists);
            selectList.setAdapter(slidLayFrameAdapter);
        } else {
            slidLayFrameAdapter.replaceAll(brandSortBeanLists);
        }

        slidLayFrameAdapter.setCbSelectLinstener(new CbSelectLinstener() {
            @Override
            public void selectbox(LinkedHashMap<String, Integer> selectboxMaps) {

                StringBuilder stringBuilder = new StringBuilder();
                if (selectboxMaps == null) {
                    return;
                }
                for (Map.Entry<String, Integer> entry : selectboxMaps.entrySet()) {
                    String selectv = entry.getKey();
                    stringBuilder.append(selectv).append(",");
                    cate_id = String.valueOf(entry.getValue());
                }

                RightSideslipLayAdapter.mSelectvalues.setText(stringBuilder.toString());
                slidLayFrameAdapter.notifyDataSetChanged();
            }
        });
        // 查看更多
        slidLayFrameAdapter.setAllSortDatasLinstener(new AllSortDatasLinstener() {
            @Override
            public void clickallsortdatas() {
                if (TextUtils.isEmpty(cate_id) && cate_id.equals("")) {
                    return;
                }

                if (allSor != null && allSor.size() > 0) {

                    getPopupWindow(allSor);

                } else {

                    //查看全部
                    mPresenter.moreBrand();
                }
            }
        });
    }


    private void getPopupWindow(List<AllSortBean.ListsBean> mSelectData) {
        if (mMenuPop != null) {
            dismissMenuPop();
            return;
        } else {
            initPopuptWindow(mSelectData);
        }
    }

    /**
     * 创建PopupWindow
     */
    private PopupWindow mMenuPop;
    public RightSideslipChildLay mDownMenu;

    protected void initPopuptWindow(List<AllSortBean.ListsBean> mSelectData) {

        mDownMenu = new RightSideslipChildLay(getActivity(), mSelectData, lists);
        if (mMenuPop == null) {
            mMenuPop = new PopupWindow(mDownMenu, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        }
        mMenuPop.setBackgroundDrawable(new BitmapDrawable());
        mMenuPop.setAnimationStyle(R.style.popupWindowAnimRight);
        mMenuPop.setFocusable(true);
        mMenuPop.showAtLocation(selectList, Gravity.TOP, 100, Utils.getStatusBarHeight(getActivity()));
        mMenuPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dismissMenuPop();
            }
        });
        mDownMenu.setBackDataLinstener(new RightSideslipChildLay.BackDataLinstener() {
            @Override
            public void backdata() {
                dismissMenuPop();
            }
        });
        mDownMenu.setConfirmDataLinstener(new RightSideslipChildLay.ConfirmDataLinstener() {
            @Override
            public void confirmdata(StringBuilder stringBuilder, List<AllSortBean.ListsBean> data) {
                dismissMenuPop();

                //找出对应的品牌名称选中
                for (int i = 0; i < lists.size(); i++) {

                    for (int i1 = 0; i1 < lists.get(i).getLists().size(); i1++) {

                        for (int i2 = 0; i2 < data.size(); i2++) {

                            if (data.get(i2).isChick() &&
                                    data.get(i2).getBrand_id().equals(lists.get(i).getLists().get(i1).getBrand_id())) {

                                lists.get(i).getLists().get(i1).setChick(data.get(i2).isChick());
                            }
                        }
                    }
                }

                if (slidLayFrameAdapter != null) {
//                    slidLayFrameAdapter.getSelectText().setText(stringBuilder.toString());
                    slidLayFrameAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 设置弹窗内容
     *
     * @return
     */
    private ArrayList<String> getItemsName() {

        ArrayList<String> items = new ArrayList<String>();
        items.add("综合排序");
        items.add("新品优先");
        return items;
    }


    /**
     * 弹窗点击事件
     */
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            middlePopup.dismiss();
        }
    };

    /**
     * 关闭窗口
     */
    private void dismissMenuPop() {
        if (mMenuPop != null) {
            mMenuPop.dismiss();
            mMenuPop = null;
        }
    }

    /**
     * 获取屏幕的宽和高
     */
    public void getScreenPixels() {

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenW = metrics.widthPixels;
        screenH = metrics.heightPixels;
    }


    //筛选页面
    private void showSelectorLayout() {


        if (lists != null && lists.size() > 0) {

            setUpList(lists);

        } else {

            //初始化侧滑数据
            mPresenter.clickFilter();
        }

        llPriceRoot.setVisibility(View.GONE);
        llThemeRoot.setVisibility(View.GONE);
        llTypeRoot.setVisibility(View.GONE);
    }

    //价格页面
    private void showPriceLayout() {

        llSelectRoot.setVisibility(View.GONE);
        llThemeRoot.setVisibility(View.GONE);
        llTypeRoot.setVisibility(View.GONE);
    }

    @Override
    public String getZt() {

        return sift;
    }

    @Override
    public String getSalePrice() {

        String low_price = et_low_price.getText().toString().trim();
        String height_price = et_height_price.getText().toString().trim();
        sealePrice = low_price + "," + height_price;
        if (TextUtils.isEmpty(sealePrice) || sealePrice.equals(",")) {
            sealePrice = "0";
        }

        return sealePrice;
    }

    @Override
    public String getOrder() {
        return order;
    }

    @Override
    public int getPages() {
        return page;
    }

    @Override
    public String getScendCateid() {
        return mId;
    }

    @Override
    public String getIsNew() {
        return isNew;
    }

    @Override
    public String getBrand() {

        if (lists != null && lists.size() > 0) {

            for (int i = 0; i < lists.size(); i++) {

                List<BrandSortBean.ListsBeanX.ListsBean> list = lists.get(i).getLists();

                for (int i1 = 0; i1 < list.size(); i1++) {

                    boolean chick = list.get(i1).isChick();
                    if (chick) {

                        brand_id += list.get(i1).getBrand_id() + ",";
                    }
                }
            }

            if (!brand_id.equals("0")) {

                return brand_id.toString().substring(0, brand_id.toString().lastIndexOf(","));
            }
        }

        return brand_id;
    }


    @Override
    public void onError(String errorMsg) {
        showShortToast(errorMsg);
    }

    @Override
    public void onSuccess(String message) {

        showShortToast(message);
    }

    @Override
    public void showDialog() {

        shapeLoadingDialog.show();
    }

    @Override
    public void hideDialog() {
        if (shapeLoadingDialog.isShowing())
            shapeLoadingDialog.dismiss();
    }

    @Override
    public void onSuccessClassif(ClassifBean data) {

        if (data != null) {

            goods = data.getGoods();

            if (!isLoad) {//首次加载

                cate_id = data.getCate_id();
                datas = goods;
                count = goods.size();

                if (datas != null && datas.size() > 0) {

                    mRecyclerView.setVisibility(View.VISIBLE);

                    //设置商品分类主页的适配器
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
                    mRecyclerView.setLayoutManager(gridLayoutManager);
                    classifyAdapter = new ClassifyAdapter(getActivity());
//                    classifyAdapter.attachRecyclerView(mRecyclerView);
                    classifyAdapter.setList(datas);
                    mLRecyclerViewAdapter = new LRecyclerViewAdapter(classifyAdapter);
                    mRecyclerView.setAdapter(mLRecyclerViewAdapter);
//                    classifyAdapter.setOnItemClickListener(this);

                } else {

                    mRecyclerView.setVisibility(View.GONE);
                    showShortToast("没有相关数据");
                }

            } else {//加载更多

                isLoad = false;
                if (goods.size() > 0) {

                    datas.addAll(goods);
                    classifyAdapter.notifyDataSetChanged();
                    mRecyclerView.refreshComplete(lists.size());

                } else {

                    mRecyclerView.setNoMore(true);
                    showShortToast("没有更多数据了！");
                }
            }
        }
    }

    private int convertDpToPixel(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return (int) (dp * displayMetrics.density);
    }

    @Override
    public String getCateid() {
        return TextUtils.isEmpty(cate_id) ? "" : cate_id;
    }

    /**
     * 分类筛选数据的点击事件
     *
     * @param itemView
     * @param position
     * @param item
     */
    @Override
    public void onItemClick(View itemView, int position, ClassifBean.GoodsBean item) {

        Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
        intent.putExtra("goodsId", item.getId());
        startActivity(intent);
    }

    /***
     * 友盟统计
     */
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("GoodListPage"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("GoodListPage");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GoodListSearchEvent event) {
        mId= event.getId();
        searchName = event.getSearchName();
        tag = event.getTag();
        mPresenter.getSerChList();
    }

}

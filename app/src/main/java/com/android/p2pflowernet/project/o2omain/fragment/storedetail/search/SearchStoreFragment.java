package com.android.p2pflowernet.project.o2omain.fragment.storedetail.search;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.BigramHeaderAdapter;
import com.android.p2pflowernet.project.adapter.O2oOrderDetailAdapter;
import com.android.p2pflowernet.project.adapter.PersonAdapter;
import com.android.p2pflowernet.project.adapter.PopCartAdapter;
import com.android.p2pflowernet.project.adapter.RecycleGoodsCategoryListAdapter;
import com.android.p2pflowernet.project.callback.ShopCartImp;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.GoPayBean;
import com.android.p2pflowernet.project.entity.O2oGoodsInfoBean;
import com.android.p2pflowernet.project.entity.O2oIndexBean;
import com.android.p2pflowernet.project.entity.SearchStoreBean;
import com.android.p2pflowernet.project.entity.ShopCart;
import com.android.p2pflowernet.project.event.GoodsaddAminEvent;
import com.android.p2pflowernet.project.event.MessageEvent;
import com.android.p2pflowernet.project.event.RefreshSyncListCart;
import com.android.p2pflowernet.project.helper.HistorySQLiteOpenHelper;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.fragment.storedetail.confirmorder.ConfirmOrderActivity;
import com.android.p2pflowernet.project.o2omain.view.MaxHeightRecyclerView;
import com.android.p2pflowernet.project.o2omain.view.ShopCarView;
import com.android.p2pflowernet.project.o2omain.view.shopcarview.StickyHeadersItemDecoration;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.utils.SPUtils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.CustomPopupWindow;
import com.android.p2pflowernet.project.view.customview.MyListView;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.android.p2pflowernet.project.o2omain.fragment.o2oorderlist.O2oOrderFragment.personAdapter;
import static com.android.p2pflowernet.project.o2omain.fragment.storedetail.StoreDetailFragment.carAdapter;

/**
 * Created by caishen on 2018/2/3.
 * by--搜索店铺商品页面
 */

public class SearchStoreFragment extends KFragment<ISearchStoreView, ISearchStorePrenter> implements
        ISearchStoreView, View.OnKeyListener, CustomEditText.IMyRightDrawableClick, TextView.OnEditorActionListener, TextWatcher, ShopCartImp {


    @BindView(R.id.ic_back)
    ImageView icBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.edit_search)
    TextView editSearch;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.tosearch_tv)
    CustomEditText tosearchTv;
    @BindView(R.id.listview)
    MyListView listview;
    @BindView(R.id.ll_clear)
    LinearLayout llClear;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;
    @BindView(R.id.scroll_history)
    ScrollView scrollHistory;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.blackview)
    View blackview;
    @BindView(R.id.clear_tv)
    TextView clearTv;
    @BindView(R.id.car_recyclerview)
    MaxHeightRecyclerView carRecyclerview;
    @BindView(R.id.car_container)
    LinearLayout carContainer;
    @BindView(R.id.car_nonselect)
    TextView carNonselect;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.distrib_money)
    TextView distribMoney;
    @BindView(R.id.amount_container)
    LinearLayout amountContainer;
    @BindView(R.id.car_limit)
    TextView carLimit;
    @BindView(R.id.car_rl)
    RelativeLayout carRl;
    @BindView(R.id.iv_shop_car)
    ImageView ivShopCar;
    @BindView(R.id.car_badge)
    TextView carBadge;
    @BindView(R.id.lin_dayang)
    LinearLayout linDayang;
    @BindView(R.id.shopCartMain)
    ShopCarView shopCartMain;
    @BindView(R.id.activity_main)
    CoordinatorLayout activityMain;
    private ShopCart mShopCart = null;//购物车数据
    private String search = "";//搜索关键字
    private RecycleGoodsCategoryListAdapter mGoodsCategoryListAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ShopCart shopCart;
    private BigramHeaderAdapter headerAdapter;
    private StickyHeadersItemDecoration top;
    private String merch_id = "";
    private ShapeLoadingDialog shapeLoadingDialog;
    private HistorySQLiteOpenHelper helper;
    private SimpleCursorAdapter adapter;
    private SQLiteDatabase db;
    private List<ShopCart> shopCarts;
    // 起送价
    private String distrib_quota = "0.00";
    private BottomSheetBehavior<LinearLayout> behavior;
    private O2oIndexBean.ListsBean.GoodsListBean goodsListBean;
    private String good_id = "";
    private int page = 1;
    private CustomPopupWindow mPop;
    private SearchStoreBean data1 = null;//商铺信息
    private int num = 0;
    private boolean isLoad = false;//是否加载更多数据（商品详情评价）
    private ImageView buyImg;
    private int count = 0;
    private O2oOrderDetailAdapter o2oOrderDetailAdapter;

    @Override
    public ISearchStorePrenter createPresenter() {
        return new ISearchStorePrenter();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShopCart = (ShopCart) getArguments().getSerializable("mShopCart");
        merch_id = getArguments().getString("merch_id");
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_search_setore;
    }

    @Override
    public void initData() {


    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        helper = new HistorySQLiteOpenHelper(getActivity());
        //初始化沉浸式
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.coloro2o));

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycleview.setLayoutManager(mLinearLayoutManager);

        //添加分割线
        recycleview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = 0;
                // top bottom left right 对应的值应该是dpi 而不是dp  dpi根据不同手机而不同

                int i = parent.getChildLayoutPosition(view) % 1;//每行1个
                switch (i) {
                    case 0://第一个
                        outRect.left = convertDpToPixel(0);
                        outRect.right = convertDpToPixel(0);
                        outRect.bottom = convertDpToPixel(8);
                        break;
                }
            }
        });

        //设置搜索框的删除按钮的点击事件
        tosearchTv.setRightDrawable(getResources().getDrawable(R.drawable.et_clear_search));
        tosearchTv.setDrawableClick(this);

        // 搜索框的键盘搜索键点击回调
        tosearchTv.setOnKeyListener(this);

        // 搜索框的文本变化实时监听
        tosearchTv.addTextChangedListener(this);

        // 设置已选数据
        behavior = BottomSheetBehavior.from(carContainer);
        shopCartMain.setBehavior(behavior, blackview);
        carRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((DefaultItemAnimator) carRecyclerview.getItemAnimator()).setSupportsChangeAnimations(false);

        shopCarts = new ArrayList<>();

        queryData("");
    }

    public static KFragment newIntence(Bundle extras, String merch_id) {

        ShopCart mShopCart = (ShopCart) extras.getSerializable("mShopCart");
        SearchStoreFragment searchStoreFragment = new SearchStoreFragment();
        Bundle bundle = new Bundle();
        bundle.putString("merch_id", merch_id);
        bundle.putSerializable("mShopCart", mShopCart);
        searchStoreFragment.setArguments(bundle);
        return searchStoreFragment;
    }

    private int convertDpToPixel(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return (int) (dp * displayMetrics.density);
    }


    /**
     * 显示减号的动画
     *
     * @return
     */
    private Animation getShowAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF,
                0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 2f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }

    /**
     * 隐藏减号的动画
     *
     * @return
     */
    private Animation getHiddenAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 4f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }


    /**
     * 开始动画
     *
     * @param view
     */
    private void startAnim(View view) {

        buyImg = new ImageView(mActivity);
        buyImg.setBackgroundResource(R.mipmap.icon_goods_add_item);// 设置buyImg的图片
        int[] loc = new int[2];
        view.getLocationInWindow(loc);
        int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
        view.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
        EventBus.getDefault().post(new GoodsaddAminEvent(buyImg, startLocation));
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能

            // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
            boolean hasData = hasData(tosearchTv.getText().toString().trim());

            if (!hasData) {

                //输入为空不能插入数据
                if (!tosearchTv.getText().toString().trim().equals("")) {
                    insertData(tosearchTv.getText().toString().trim());
                    queryData("");
                }
            }

            if (tosearchTv.getText().toString().trim().equals("")) {

                showShortToast("什么也没有输入哦！");

            } else {

                mPresenter.searchStore();
            }
        }
        return false;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
    }

    @Override
    public void rightDrawableClick(View view) {
        switch (view.getId()) {
            case R.id.tosearch_tv:

                //清除所有搜索的字符
                tosearchTv.setText("");

                break;
        }
    }

    @Override
    public String merchId() {
        return merch_id;
    }

    @Override
    public String longitude() {
        String longitude = SPUtils.get(getActivity(), "longitude", "").toString();
        return longitude;
    }

    @Override
    public String latitude() {
        String latitude = SPUtils.get(getActivity(), "latitude", "").toString();
        return latitude;
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

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void onSuccess(SearchStoreBean data) {

        if (data != null) {

            data1 = data;
            //设置店铺数据
            int is_close = data.getIs_close();
            if (is_close == 0) {

                // 0 营业中
                carNonselect.setText("未选购商品");
                carRl.setEnabled(true);

            } else {

                // 1 打烊
                carNonselect.setText("本店已打烊");
                carRl.setEnabled(false);
            }

            // 配送费
            distribMoney.setText(TextUtils.isEmpty(data.getDistrib_money()) ? "" : "另需配送费¥"
                    + data.getDistrib_money() + "元");
            // 起送价
            distrib_quota = data.getDistrib_quota();
            carLimit.setText(TextUtils.isEmpty(data.getDistrib_quota()) ? "" : "¥"
                    + data.getDistrib_quota() + "元起送");

            final List<O2oIndexBean.ListsBean.GoodsListBean> list = data.getLists();
            distrib_quota = data.getDistrib_quota();

            if (list != null && list.size() > 0) {

                scrollHistory.setVisibility(View.GONE);
                recycleview.setVisibility(View.VISIBLE);
                shopCart = new ShopCart();
                personAdapter = new PersonAdapter(getActivity(), list, shopCart);
                personAdapter.setmActivity(getActivity());
                personAdapter.setShopCartImp(this);
                recycleview.setAdapter(personAdapter);

                //设置点击事件
                personAdapter.setOnItemClickLitener(new PersonAdapter.OnItemClickLitener() {
                    @Override
                    public void OnitemCLickLitener(View view, int position) {

                        good_id = list.get(position).getId();
                        goodsListBean = list.get(position);
                        mPresenter.get_goods_info();
                    }
                });

            } else {

                showShortToast("没有相关商品");
                recycleview.setVisibility(View.GONE);
                scrollHistory.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public String searchKey() {

        return tosearchTv.getText().toString().trim();
    }


    /***
     * 去结算的回调
     * @param data
     */
    @Override
    public void onSuccessGoPay(GoPayBean data) {

        if (data != null) {

            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < shopCarts.size(); i++) {

                ShopCart shopCart = shopCarts.get(i);
                JSONObject tmpObj = null;
                tmpObj = new JSONObject();
                tmpObj.put("id", shopCart.getId());
                tmpObj.put("spec", shopCart.getSpec());
                tmpObj.put("num", String.valueOf(shopCart.getNum()));
                jsonArray.add(tmpObj);
                tmpObj = null;
            }

            String goodslist = jsonArray.toString();

            // 商品ID
            Bundle bundle = new Bundle();
            Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
            bundle.putString("goodslist", goodslist);
            bundle.putString("merch_id", merchId());

            // 购物车数据
            bundle.putSerializable("shopcart", mShopCart);
            bundle.putSerializable("gopaybean", data);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        removeFragment();
    }

    /**
     * 商家休息中
     *
     * @param message
     */
    @Override
    public void onErrorRest(String message) {

        // 1 打烊
        carNonselect.setText("本店已打烊");
        carRl.setEnabled(false);
        showShortToast(message);
    }

    @Override
    public String getGoodId() {
        return good_id;
    }

    @Override
    public int getpage() {
        return page;
    }

    /**
     * 商品明细的成功回调
     *
     * @param data
     */
    @Override
    public void onSuccessData(O2oGoodsInfoBean data) {

        if (data != null) {
            //初始化底部弹窗
            initPopuW(goodsListBean, data, recycleview, this.shopCart);
        }
    }


    //初始化商品明细的弹窗
    private void initPopuW(O2oIndexBean.ListsBean.GoodsListBean goodsListBean,
                           O2oGoodsInfoBean data, RecyclerView goodsRecycleView, ShopCart shopCart) {

        List<O2oGoodsInfoBean.ListsBean> lists = null;
        shopCarts = new ArrayList<ShopCart>();
        mPop = new CustomPopupWindow(getActivity());
        //获取布局管理者
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //设置为垂直排列格式
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //recyclerview应用垂直排列格式
        mPop.recycleview.setLayoutManager(linearLayoutManager);
        mPop.recycleview.setNestedScrollingEnabled(false);


        //设置店铺数据
        if (data1 != null) {

            int is_close = data1.getIs_close();
            if (is_close == 0) {
                // 0 营业中
                mPop.carNonselect.setText("未选购商品");
                mPop.carRl.setEnabled(true);

            } else {

                // 1 打烊
                mPop.carNonselect.setText("本店已打烊");
                mPop.carRl.setEnabled(false);
            }

            // 配送费
            mPop.distribMoney.setText(TextUtils.isEmpty(data1.getDistrib_money()) ? "" : "另需配送费¥"
                    + data1.getDistrib_money() + "元");
            // 起送价
            mPop.distrib_quota = data1.getDistrib_quota();
            mPop.carLimit.setText(TextUtils.isEmpty(data1.getDistrib_quota()) ? "" : "¥"
                    + data1.getDistrib_quota() + "元起送");
        }

        // 设置已选数据
        behavior = BottomSheetBehavior.from(mPop.mCarContainer);
        mPop.shopCartMain.setBehavior(behavior, mPop.blackView);
        mPop.carRecView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((DefaultItemAnimator) mPop.carRecView.getItemAnimator()).setSupportsChangeAnimations(false);

        //判断是否选中商品，选中就显示加减按钮
        if (shopCart != null && shopCart.getShoppingAccount() > 0) {

            this.shopCart = shopCart;

            changeShopCarts(goodsListBean);
        }

        //设置点击事件
        initCLick(mPop, goodsListBean);

        mPop.pullRefresh.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {

                isLoad = true;
                page = 1;
                mPresenter.get_goods_info();
                mPop.pullRefresh.finishRefresh();
            }

            @Override
            public void loadMore() {

                if (o2oOrderDetailAdapter != null) {

                    if (count >= o2oOrderDetailAdapter.getItemCount()) {

                        isLoad = true;
                        page += 1;
                        mPresenter.get_goods_info();

                    } else {

                        showShortToast("没有更多数据了！");
                    }
                }
                mPop.pullRefresh.finishLoadMore();
            }
        });

        if (data != null) {

            lists = data.getLists();
            count = lists.size();

            mPop.tvTitle.setText(data.getName() == null ? "" : data.getName());
            mPop.yiSale.setText(data.getSales_Mouth() == null ? "已售:" + "0" : "已售:" + data.getSales_month());
            mPop.tvPrice.setText(data.getPrice() == null ? "¥ 0" : "¥" + data.getPrice());
            mPop.tvHuafan.setText(data.getHuafan() == null ? "花返：¥ 0" : "花返：¥" + data.getHuafan());

            String goods_img = ApiUrlConstant.API_IMG_URL + data.getGoods_img();
            new GlideImageLoader().displayImage(getActivity(), goods_img, mPop.banner);

            if (!isLoad) {

                if (lists != null && lists.size() > 0) {

                    mPop.recycleview.setVisibility(View.VISIBLE);
                    //设置适配器
                    o2oOrderDetailAdapter = new O2oOrderDetailAdapter(getActivity());
                    o2oOrderDetailAdapter.attachRecyclerView(mPop.recycleview);
                    o2oOrderDetailAdapter.setList(lists);

                } else {

                    mPop.recycleview.setVisibility(View.GONE);
                }

            } else {

                isLoad = false;
                if (lists.size() > 0) {

                    lists.addAll(lists);
                    o2oOrderDetailAdapter.notifyDataSetChanged();

                } else {

                    showShortToast("没有更多数据了！");
                }
            }
        }

        //设置PopupWindow中的位置
        mPop.showAtLocation(goodsRecycleView, Gravity.BOTTOM, 0, getStatusBarHeight(getActivity()));
        mPop.setClippingEnabled(false);
    }

    /**
     * 获取状态通知栏高度
     *
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    //初始化监听事件
    private void initCLick(final CustomPopupWindow mPop, final O2oIndexBean.ListsBean.GoodsListBean lists) {

        //去结算
        mPop.setOnPayListOnlickLitener(new CustomPopupWindow.OnPayListOnlickLitener() {
            @Override
            public void onPayListOnlickLitener(View view) {

                if (mPop.carLimit.getText().toString().trim().equals("去结算")) {

                    mPresenter.goPay();
                }
            }
        });

        //清除列表数据
        mPop.setOnClearListOnlickLitener(new CustomPopupWindow.OnClearListOnlickLitener() {
            @Override
            public void onClearListOnlickLitener(View view) {

                // 清除列表
                mPop.carBadge.setVisibility(View.GONE);
                mPop.tvAmount.setVisibility(View.GONE);
                mPop.amountContainer.setVisibility(View.GONE);
                mPop.carNonselect.setVisibility(View.VISIBLE);
                mPop.shopCartMain.showBadge(0);
                mPop.addShopcar.setVisibility(View.VISIBLE);

                if (shopCart != null) {
                    shopCart.clear();
                }

                //刷新商品类别的数据
                EventBus.getDefault().post(new RefreshSyncListCart());

                mPop.shopCartMain.updateAmount(new BigDecimal(0.00), mPop.distrib_quota);
                mPop.mCarContainer.setVisibility(View.GONE);
                mPop.ivShopCar.setImageResource(R.mipmap.shop_car_empty);
            }
        });

        //加
        mPop.setOnAddListOnlickLitener(new CustomPopupWindow.OnAddListOnlickLitener() {
            @Override
            public void onAddListOnlickLitener(View view) {

                if (shopCart.getShoppingAccount() <= 1) {

                    mPop.ivGoodsMinus.setAnimation(getShowAnimation());
                    mPop.tvGoodsSelectNum.setAnimation(getShowAnimation());
                    mPop.ivGoodsMinus.setVisibility(View.VISIBLE);
                    mPop.addShopcar.setVisibility(View.GONE);
                    mPop.tvGoodsSelectNum.setVisibility(View.VISIBLE);
                }


                // 添加购物车
                if (shopCart.addShoppingSingle(lists)) {

                    if (carAdapter != null) {

                        carAdapter.notifyDataSetChanged();

                        if (personAdapter != null) {

                            personAdapter.UpdateOrderDatas();
                        }
                    }
//                    if (shopCartImp != null)
//
//                        shopCartImp.add(shopCart, position);
                }

                startAnim(mPop.ivGoodsAdd);
                changeShopCart(lists);
                //刷新商品类别的数据
                EventBus.getDefault().post(new RefreshSyncListCart());
            }
        });

        //减
        mPop.setOnSubListOnlickLitener(new CustomPopupWindow.OnSubListOnlickLitener() {
            @Override
            public void onSubListOnlickLitener(View view) {

                if (shopCart.getShoppingAccount() <= 0) {

                    mPop.ivGoodsMinus.setAnimation(getHiddenAnimation());
                    mPop.tvGoodsSelectNum.setAnimation(getHiddenAnimation());
                    mPop.ivGoodsMinus.setVisibility(View.GONE);
                    mPop.tvGoodsSelectNum.setVisibility(View.GONE);
                    mPop.ivGoodsAdd.setVisibility(View.GONE);
                    mPop.addShopcar.setVisibility(View.VISIBLE);
                }

                // 移除购物车的数据
                if (shopCart.subShoppingSingle(lists)) {

                    if (carAdapter != null) {

                        carAdapter.notifyDataSetChanged();

                        if (personAdapter != null) {

                            personAdapter.UpdateOrderDatas();
                        }
                    }
                }

                changeShopCart(lists);
                //刷新商品类别的数据
                EventBus.getDefault().post(new RefreshSyncListCart());
            }
        });

        //加入购物车
        mPop.setOnAddShopcarListOnlickLitener(new CustomPopupWindow.OnAddShopcarListOnlickLitener() {
            @Override
            public void onAddShopcarListOnlickLitener(View view) {

                mPop.addShopcar.setVisibility(View.GONE);
                mPop.ivGoodsAdd.setVisibility(View.VISIBLE);
                mPop.tvGoodsSelectNum.setVisibility(View.VISIBLE);
                mPop.ivGoodsMinus.setVisibility(View.VISIBLE);

                if (shopCart.addShoppingSingle(lists)) {

                    if (carAdapter != null) {

                        carAdapter.notifyDataSetChanged();

                        if (personAdapter != null) {

                            personAdapter.UpdateOrderDatas();
                        }
                    }
                }

                changeShopCart(lists);
                //刷新商品类别的数据
                EventBus.getDefault().post(new RefreshSyncListCart());
            }
        });
    }


    /**
     * 修改购物车状态(增加)
     *
     * @param goodsListBean
     * @param goodsListBean
     */
    private void changeShopCart(O2oIndexBean.ListsBean.GoodsListBean goodsListBean) {

        num = 0;
        EventBus.getDefault().post(new MessageEvent("add", shopCart.getShoppingAccount(),
                shopCart.getShoppingTotalPrice(), shopCart));

        if (shopCart != null && shopCart.getShoppingSingleMap().size() > 0) {

            if (shopCart.getShoppingSingleMap().get(goodsListBean) != null) {

                num = shopCart.getShoppingSingleMap().get(goodsListBean);
            }
        }

        if (num <= 0) {

            mPop.addShopcar.setVisibility(View.VISIBLE);
            mPop.ivGoodsMinus.setAnimation(getHiddenAnimation());
            mPop.tvGoodsSelectNum.setAnimation(getHiddenAnimation());
            mPop.ivGoodsMinus.setVisibility(View.GONE);
            mPop.tvGoodsSelectNum.setVisibility(View.GONE);
            mPop.ivGoodsAdd.setVisibility(View.GONE);

        } else {

            mPop.ivGoodsMinus.setAnimation(getShowAnimation());
            mPop.addShopcar.setVisibility(View.GONE);
            mPop.tvGoodsSelectNum.setAnimation(getShowAnimation());
            mPop.ivGoodsMinus.setVisibility(View.VISIBLE);
            mPop.tvGoodsSelectNum.setVisibility(View.VISIBLE);
            mPop.ivGoodsAdd.setVisibility(View.VISIBLE);
            mPop.tvGoodsSelectNum.setText(num + "");
        }

        //将商品列表数据添加到集合中
        if (shopCart.getShoppingAccount() > 0) {

            shopCarts.add(shopCart);
        }

        if (shopCart.getShoppingAccount() > 0) {

            mPop.carBadge.setVisibility(View.VISIBLE);
            mPop.carBadge.setText(String.valueOf(shopCart.getShoppingAccount()));
            mPop.tvAmount.setVisibility(View.VISIBLE);
            mPop.amountContainer.setVisibility(View.VISIBLE);
            mPop.tvAmount.setText("¥" + String.valueOf(shopCart.getShoppingTotalPrice()));
            mPop.carNonselect.setVisibility(View.GONE);
            mPop.ivShopCar.setImageResource(R.mipmap.shop_car_have);

            // 购物车数据
            setCartDatas(shopCart);

            mPop.mCarContainer.setVisibility(View.VISIBLE);
            mPop.shopCartMain.updateAmount(new BigDecimal(shopCart.getShoppingTotalPrice())
                    .setScale(2, BigDecimal.ROUND_HALF_DOWN), mPop.distrib_quota);

        } else {

            mPop.carBadge.setVisibility(View.GONE);
            mPop.tvAmount.setVisibility(View.GONE);
            mPop.amountContainer.setVisibility(View.GONE);

            if (shopCart != null) {
                shopCart.clear();
            }

            mPop.carNonselect.setVisibility(View.VISIBLE);
            mPop.ivShopCar.setImageResource(R.mipmap.shop_car_empty);
            mPop.shopCartMain.updateAmount(new BigDecimal(0.00), mPop.distrib_quota);
        }
    }

    /**
     * 修改购物车状态
     *
     * @param goodsListBean
     * @param goodsListBean
     */
    private void changeShopCarts(O2oIndexBean.ListsBean.GoodsListBean goodsListBean) {

        num = 0;
        if (shopCart != null) {

            if (shopCart.getShoppingSingleMap().get(goodsListBean) != null) {

                num = shopCart.getShoppingSingleMap().get(goodsListBean);
            }
        }

        if (num <= 0) {

            mPop.ivGoodsMinus.setAnimation(getHiddenAnimation());
            mPop.tvGoodsSelectNum.setAnimation(getHiddenAnimation());
            mPop.ivGoodsMinus.setVisibility(View.GONE);
            mPop.tvGoodsSelectNum.setVisibility(View.GONE);
            mPop.addShopcar.setVisibility(View.VISIBLE);
            mPop.ivGoodsAdd.setVisibility(View.GONE);

        } else {

            mPop.addShopcar.setVisibility(View.GONE);
            mPop.ivGoodsMinus.setAnimation(getShowAnimation());
            mPop.tvGoodsSelectNum.setAnimation(getShowAnimation());
            mPop.ivGoodsMinus.setVisibility(View.VISIBLE);
            mPop.tvGoodsSelectNum.setVisibility(View.VISIBLE);
            mPop.tvGoodsSelectNum.setText(num + "");
            mPop.ivGoodsAdd.setVisibility(View.VISIBLE);
        }

        //将商品列表数据添加到集合中
        if (shopCart.getShoppingAccount() > 0) {

            shopCarts.add(shopCart);
        }

        if (shopCart.getShoppingAccount() > 0) {

            mPop.carBadge.setVisibility(View.VISIBLE);
            mPop.carBadge.setText(String.valueOf(shopCart.getShoppingAccount()));
            mPop.tvAmount.setVisibility(View.VISIBLE);
            mPop.amountContainer.setVisibility(View.VISIBLE);
            mPop.tvAmount.setText("¥" + String.valueOf(shopCart.getShoppingTotalPrice()));
            mPop.carNonselect.setVisibility(View.GONE);
            mPop.ivShopCar.setImageResource(R.mipmap.shop_car_have);

            // 购物车数据
            setCartDatas(shopCart);

            mPop.mCarContainer.setVisibility(View.VISIBLE);
            mPop.shopCartMain.updateAmount(new BigDecimal(shopCart.getShoppingTotalPrice())
                    .setScale(2, BigDecimal.ROUND_HALF_DOWN), mPop.distrib_quota);

        } else {

            mPop.carBadge.setVisibility(View.GONE);
            mPop.tvAmount.setVisibility(View.GONE);
            mPop.amountContainer.setVisibility(View.GONE);

            if (shopCart != null) {
                shopCart.clear();
            }

            mPop.carNonselect.setVisibility(View.VISIBLE);
            mPop.ivShopCar.setImageResource(R.mipmap.shop_car_empty);
            mPop.shopCartMain.updateAmount(new BigDecimal(0.00), mPop.distrib_quota);
        }
    }


    @OnClick({R.id.ic_back, R.id.ll_clear, R.id.car_limit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ic_back://返回

                removeFragment();

                break;
            case R.id.ll_clear://清空历史记录

                deleteData();
                queryData("");

                break;
            case R.id.car_limit://去结算

                if (carLimit.getText().toString().trim().equals("去结算")) {

                    mPresenter.goPay();
                }

                break;
        }
    }

    /**
     * 添加 或者  删除  商品发送的消息处理
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event != null) {

            mShopCart = event.getShopCart();
            String state = event.getState();//区别是加还是减
            if (event.num > 0) {

                shopCarts.add(mShopCart);
            }

            carBadge.setText(String.valueOf(event.num));
            carBadge.setVisibility(View.VISIBLE);
            tvAmount.setVisibility(View.VISIBLE);
            amountContainer.setVisibility(View.VISIBLE);
            tvAmount.setText("¥" + String.valueOf(event.price));
            carNonselect.setVisibility(View.GONE);
            ivShopCar.setImageResource(R.mipmap.shop_car_have);

            // 购物车数据
            setCartDatas(event.shopCart);

            carContainer.setVisibility(View.VISIBLE);
            shopCartMain.updateAmount(new BigDecimal(event.price).setScale(2,
                    BigDecimal.ROUND_HALF_DOWN), distrib_quota);

        } else {

            carBadge.setVisibility(View.GONE);
            tvAmount.setVisibility(View.GONE);
            amountContainer.setVisibility(View.GONE);

            if (mShopCart != null) {
                mShopCart.clear();
            }

            carNonselect.setVisibility(View.VISIBLE);
            ivShopCar.setImageResource(R.mipmap.shop_car_empty);
            shopCartMain.updateAmount(new BigDecimal(0.00), distrib_quota);
        }
    }


    private void setCartDatas(ShopCart shopCart) {

        carAdapter = new PopCartAdapter(getActivity(), shopCart);
        carRecyclerview.setAdapter(carAdapter);
        carAdapter.setShopCartImp(this);
    }


    /**
     * 清空数据
     */
    private void deleteData() {

        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }

    /**
     * 模糊查询数据
     */
    private void queryData(String tempName) {

        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        final List<String> list = new ArrayList<>();

        //判断是否有历史记录
        if (cursor != null && cursor.moveToNext()) {
            llClear.setVisibility(View.VISIBLE);

        } else {

            llClear.setVisibility(View.GONE);
        }

        // 创建adapter适配器对象
        adapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, cursor, new String[]{"name"},
                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        if (cursor.moveToFirst()) {
            list.add(cursor.getString(1));
        }
        while (cursor.moveToNext()){
            list.add(cursor.getString(1));
        }

        // 设置适配器
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list != null && list.size() > 0) {
                    String str = list.get(position);
                    tosearchTv.setText(str);
                    tosearchTv.setSelection(str.length());//将光标移至文字末尾
                }else{
                    tosearchTv.setText("");
                }
                mPresenter.searchStore();
            }
        });
    }

    /**
     * 检查数据库中是否已经有该条记录
     */
    private boolean hasData(String tempName) {

        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});

        //判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 插入数据
     */
    private void insertData(String tempName) {

        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        recycleview.setVisibility(View.GONE);
        String tempName = tosearchTv.getText().toString().trim();
        // 根据tempName去模糊查询数据库中有没有数据
        //输入为空不能插入数据
        if (tosearchTv.getText().toString().trim().equals("")) {
            queryData("");
        }else {
            queryData(tempName);
        }
    }

    @Override
    public void add(ShopCart shopCart, int postion) {

    }

    @Override
    public void remove(ShopCart shopCart, int postion) {

    }
}

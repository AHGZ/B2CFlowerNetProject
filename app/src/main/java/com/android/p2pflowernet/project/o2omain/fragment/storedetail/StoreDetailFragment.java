package com.android.p2pflowernet.project.o2omain.fragment.storedetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.DaYangAdapter;
import com.android.p2pflowernet.project.adapter.ItemTitlePagerAdapter;
import com.android.p2pflowernet.project.adapter.PopCartAdapter;
import com.android.p2pflowernet.project.callback.ShopCartImp;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.GoPayBean;
import com.android.p2pflowernet.project.entity.MerchantBean;
import com.android.p2pflowernet.project.entity.O2oIndexBean;
import com.android.p2pflowernet.project.entity.ShopCart;
import com.android.p2pflowernet.project.entity.StoreInfoEvent;
import com.android.p2pflowernet.project.event.GoodsaddAminEvent;
import com.android.p2pflowernet.project.event.MessageEvent;
import com.android.p2pflowernet.project.event.RefreshSyncListCart;
import com.android.p2pflowernet.project.event.StoreDetailEvent;
import com.android.p2pflowernet.project.event.StoreDetailsEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.activity.StoreDetailActivity;
import com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying.evaluation.evaluationdetails.EvaluationDetailsFragment;
import com.android.p2pflowernet.project.o2omain.fragment.o2oorderlist.O2oOrderFragment;
import com.android.p2pflowernet.project.o2omain.fragment.storedetail.confirmorder.ConfirmOrderActivity;
import com.android.p2pflowernet.project.o2omain.fragment.storedetail.search.SearchStoreActivity;
import com.android.p2pflowernet.project.o2omain.fragment.storedetail.shop.ShopFragment;
import com.android.p2pflowernet.project.o2omain.utils.AnimationUtil;
import com.android.p2pflowernet.project.o2omain.view.ShopCarView;
import com.android.p2pflowernet.project.utils.RxImageLoader;
import com.android.p2pflowernet.project.utils.SPUtils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.DaYangDialog;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.caimuhao.rxpicker.utils.DensityUtil;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * author: zhangpeisen
 * created on: 2017/10/18 下午3:03
 * description: 店铺详情主页面
 */
public class StoreDetailFragment extends KFragment<StoreDetailView, StoreDetailPrenter>
        implements StoreDetailActivity.FragmentBackListener, StoreDetailView, ShopCartImp {

    @BindView(R.id.ll_back)
    // back
            LinearLayout ll_back;
    @BindView(R.id.ic_back)
    ImageView icBack;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.slidinglayout)
    TabLayout slidingTabLayout;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.car_nonselect)
    TextView carNonselect;
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
    @BindView(R.id.clear_tv)
    TextView cleartv;
    @BindView(R.id.activity_value)
    // 活动内容
            TextView activity_value;
    @BindView(R.id.activity_size)
    //活动个数
            TextView activity_size;
    @BindView(R.id.shopCartMain)
    ShopCarView shopCartMain;
    @BindView(R.id.car_container)
    // 弹出已选择框
            LinearLayout mCarContainer;
    @BindView(R.id.car_recyclerview)
    // 已点列表
            RecyclerView carRecView;
    @BindView(R.id.blackview)
    View blackView;
    @BindView(R.id.activity_main)
    //根布局
            CoordinatorLayout rootview;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.edit_search)
    TextView editSearch;
    @BindView(R.id.storelogo_iv)
    //  店铺logo
            ImageView storelogoIv;
    @BindView(R.id.storename_tv)
    // 店铺名称
            TextView storenameTv;
    @BindView(R.id.scroll_container)
    LinearLayout scrollContainer;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.distrib_money)

    // 配送费
            TextView distribMoney;
    @BindView(R.id.lin_dayang)
    LinearLayout linDayang;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    private List<KFragment> fragmentList = new ArrayList<>();

    public BottomSheetBehavior behavior;

    private ViewGroup anim_mask_layout;//动画层

    public static PopCartAdapter carAdapter;
    // 加载view
    private ShapeLoadingDialog shapeLoadingDialog;

    private int imageSize;

    private ShopCart mShopCart;

    // 起送价
    private String distrib_quota = "0.00";
    private MerchantBean mMerChantBean;
    private String merch_id = "";
    private List<ShopCart> shopCarts;
    private O2oIndexBean o2oIndexBean1;
    private String flag;//来源区分→再来一单/其他
    private int is_close;
    private ShopCart againOrder;//再来一单实体

    public static StoreDetailFragment newInstance(Bundle bundle) {

        String merchId = bundle.getString("merch_id");
        String flag = bundle.getString("flag");
        ShopCart shopCart = (ShopCart) bundle.getSerializable("shopCart");
        Bundle args = new Bundle();
        StoreDetailFragment fragment = new StoreDetailFragment();
        args.putString("merch_id", merchId);
        args.putString("flag", flag);
        args.putSerializable("shopCart", shopCart);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        merch_id = getArguments().getString("merch_id");
        flag = getArguments().getString("flag");
        againOrder = (ShopCart) getArguments().getSerializable("shopCart");
    }


    @Override
    public StoreDetailPrenter createPresenter() {

        return new StoreDetailPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.o2o_storedetail_fragment;
    }

    @Override
    public void initData() {


    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸式
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.coloro2o));
        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.coloro2o));
        fragmentList.add(O2oOrderFragment.newInstance(merch_id));
        fragmentList.add(EvaluationDetailsFragment.newInstance(merch_id));
        fragmentList.add(ShopFragment.newInstance(merch_id));
        ItemTitlePagerAdapter itemTitlePagerAdapter = new ItemTitlePagerAdapter(getChildFragmentManager());
        itemTitlePagerAdapter.setFramentData(fragmentList);
        itemTitlePagerAdapter.setTitleData(new String[]{"点菜", "评价", "商家"});
        vp.setAdapter(itemTitlePagerAdapter);
//        vp.setOffscreenPageLimit(1);
        slidingTabLayout.setupWithViewPager(vp);

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        shopCartMain.startAnimation(
                                AnimationUtil.createInAnimation(getActivity(), shopCartMain.getMeasuredHeight()));
                        break;
                    case 1:
                        shopCartMain.startAnimation(
                                AnimationUtil.createOutAnimation(getActivity(), shopCartMain.getMeasuredHeight()));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        mPresenter.o2ogoodslist();
        mPresenter.getShopMerchantInfo(merch_id);

        imageSize = DensityUtil.getDeviceWidth(storelogoIv.getContext()) / 3;

        // 设置已选数据
        behavior = BottomSheetBehavior.from(mCarContainer);
        shopCartMain.setBehavior(behavior, blackView);
        carRecView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((DefaultItemAnimator) carRecView.getItemAnimator()).setSupportsChangeAnimations(false);

        shopCarts = new ArrayList<>();
        initAgainOrder();
    }

    //再来一单
    private void initAgainOrder() {

        if ("1".equals(flag) && null != againOrder) {
            if (againOrder.getShoppingAccount() > 0) {
                carBadge.setText(String.valueOf(againOrder.getShoppingAccount()));
                carBadge.setVisibility(View.VISIBLE);
                tvAmount.setVisibility(View.VISIBLE);
                amountContainer.setVisibility(View.VISIBLE);
                tvAmount.setText("¥" + String.valueOf(againOrder.getShoppingTotalPrice()));
                carNonselect.setVisibility(View.GONE);
                ivShopCar.setImageResource(R.mipmap.shop_car_have);
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

            // 购物车数据
            setCartDatas(againOrder);

            mCarContainer.setVisibility(View.VISIBLE);
            shopCartMain.updateAmount(new BigDecimal(againOrder.getShoppingTotalPrice()).setScale(2,
                    BigDecimal.ROUND_HALF_DOWN), distrib_quota);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //注册监听
        ((StoreDetailActivity) getActivity()).setBackListener(this);
        ((StoreDetailActivity) getActivity()).setInterception(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //取消监听
        ((StoreDetailActivity) getActivity()).setBackListener(null);
        ((StoreDetailActivity) getActivity()).setInterception(false);
    }

    /**
     * 虚拟键返回监听回调
     */
    @Override
    public void onBackForward() {
        removeFragment();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddEvent(GoodsaddAminEvent event) {
        if (event == null) {
            return;
        }
        setAnim(event.getBuyImageView(), event.getStartLocation());
    }

    /**
     * 设置动画（点击添加商品）
     *
     * @param v
     * @param startLocation
     */
    public void setAnim(final View v, int[] startLocation) {

        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);//把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, v, startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        carBadge.getLocationInWindow(endLocation);
        // 计算位移
        int endX = 0 - startLocation[0] + 40;// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1];// 动画位移的y坐标

        TranslateAnimation translateAnimationX = new TranslateAnimation(0, endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationY.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(400);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }
        });

    }

    /**
     * 初始化动画图层
     *
     * @return
     */
    private ViewGroup createAnimLayout() {

        ViewGroup rootView = (ViewGroup) getActivity().getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE - 1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    /**
     * 将View添加到动画图层
     *
     * @param parent
     * @param view
     * @param location
     * @return
     */
    private View addViewToAnimLayout(final ViewGroup parent, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }


    @Override
    public String merchId() {
        return merch_id;
    }

    @Override
    public String longitude() {

        String string = SPUtils.get(getActivity(), "longitude", "").toString();
        return string;
    }

    @Override
    public String latitude() {
        String string = SPUtils.get(getActivity(), "latitude", "").toString();
        return string;
    }

    @Override
    public void onSuccess(O2oIndexBean o2oIndexBean) {
        if (o2oIndexBean == null) {
            return;
        }

        o2oIndexBean1 = o2oIndexBean;
        //发送店铺详情到点菜页面
        EventBus.getDefault().post(new StoreDetailsEvent(o2oIndexBean1));

        // 店铺logo
        String logo_url = ApiUrlConstant.API_IMG_URL + o2oIndexBean.getLogo_url();
        new RxImageLoader().display(storelogoIv, logo_url, imageSize, imageSize);

        // 店铺名称
        storenameTv.setText(TextUtils.isEmpty(o2oIndexBean.getBusiness_name()) ? "" : o2oIndexBean.getBusiness_name());
        is_close = o2oIndexBean.getIs_close();

        if (is_close == 0) {
            // 0 营业中
            carNonselect.setText("未选购商品");
            carRl.setEnabled(true);
        } else {
            // 1 打烊
            showDaYang();
            carNonselect.setText("本店已打烊");
            carRl.setEnabled(false);
        }

        // 配送费
        distribMoney.setText(TextUtils.isEmpty(o2oIndexBean.getDistrib_money()) ? "" : "另需配送费¥"
                + o2oIndexBean.getDistrib_money() + "元");
        // 起送价
        distrib_quota = o2oIndexBean.getDistrib_quota();
        carLimit.setText(TextUtils.isEmpty(o2oIndexBean.getDistrib_quota()) ? "" : "¥"
                + o2oIndexBean.getDistrib_quota() + "元起送");
        List<O2oIndexBean.ListsBean> o2oIndexBeanLists = o2oIndexBean.getLists();
        if (o2oIndexBeanLists == null) {
            return;
        }

        EventBus.getDefault().post(new StoreDetailEvent(o2oIndexBeanLists));
    }

    @Override
    public void onSuccess(MerchantBean merchantBean) {

        // 获取商家信息
        if (merchantBean == null) {
            return;
        }

        EventBus.getDefault().post(new StoreInfoEvent(merchantBean));
        mMerChantBean = merchantBean;
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
                carBadge.setText(String.valueOf(event.num));
                carBadge.setVisibility(View.VISIBLE);

            } else {

                carBadge.setVisibility(View.GONE);
            }

            tvAmount.setVisibility(View.VISIBLE);
            amountContainer.setVisibility(View.VISIBLE);
            tvAmount.setText("¥" + String.valueOf(event.price));
            carNonselect.setVisibility(View.GONE);
            ivShopCar.setImageResource(R.mipmap.shop_car_have);

            // 购物车数据
            setCartDatas(event.shopCart);

            mCarContainer.setVisibility(View.VISIBLE);
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
        carRecView.setAdapter(carAdapter);
        carAdapter.setShopCartImp(this);
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
        if (shapeLoadingDialog.isShowing()) {
            shapeLoadingDialog.dismiss();
        }
    }

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
            bundle.putSerializable("o2oIndexBean1", o2oIndexBean1);
            bundle.putSerializable("goPayBean", data);

            // 购物车数据
            bundle.putSerializable("shopcart", mShopCart);
            bundle.putSerializable("gopaybean", data);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        removeFragment();
    }


    @Override
    public void add(ShopCart shopCart, int postion) {

    }

    @Override
    public void remove(ShopCart shopCart, int postion) {

    }


    @OnClick({R.id.ll_back, R.id.car_limit, R.id.clear_tv, R.id.ll_search})
    public void onViewOnClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:

                // 返回键
                removeFragment();

                break;
            case R.id.clear_tv://清除商品列表数据

                // 清除列表
                carBadge.setVisibility(View.GONE);
                tvAmount.setVisibility(View.GONE);
                amountContainer.setVisibility(View.GONE);
                carNonselect.setVisibility(View.VISIBLE);
                shopCartMain.showBadge(0);
                if (mShopCart != null) {
                    mShopCart.clear();
                }

                EventBus.getDefault().post(new RefreshSyncListCart());
                shopCartMain.updateAmount(new BigDecimal(0.00), distrib_quota);
                mCarContainer.setVisibility(View.GONE);
                ivShopCar.setImageResource(R.mipmap.shop_car_empty);

                break;
            case R.id.car_limit://去结算

                if (carLimit.getText().toString().trim().equals("去结算")) {

                    mPresenter.goPay();

                    //友盟统计
                    MobclickAgent.onEvent(getActivity(), "o2oPay");
                }

                break;
            case R.id.ll_search://店铺搜索

                //搜索店铺数据
                Intent intent = new Intent(getActivity(), SearchStoreActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("mShopCart", mShopCart);
                intent.putExtras(bundle);
                intent.putExtra("merch_id", merch_id);
                startActivity(intent);

                break;
        }
    }

    //展示打烊布局
    public void showDaYang() {

        DaYangDialog dialog = new DaYangDialog(getActivity(), R.style.Dialog);//设置dialog的样式
        Window window = dialog.getWindow();
        window.setGravity(Gravity.TOP);
        window.setWindowAnimations(R.style.dayangstyle); // 添加动画
        dialog.show();
        WindowManager m = getActivity().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        //这句就是设置dialog横向满屏了。
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = (int) (d.getHeight() * 0.7);     //dialog屏幕占比
        window.setAttributes(lp);
        RecyclerView dayangRecyclerview = (RecyclerView) dialog.findViewById(R.id.dayang_recyclerview);
        //获取布局管理者
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //设置为垂直排列格式
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //recyclerview应用垂直排列格式
        dayangRecyclerview.setLayoutManager(linearLayoutManager);
        DaYangAdapter daYangAdapter = new DaYangAdapter(getActivity());
        dayangRecyclerview.setAdapter(daYangAdapter);
    }

    /***
     * 友盟统计
     */
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("StoreListPage"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("StoreListPage");
    }
}

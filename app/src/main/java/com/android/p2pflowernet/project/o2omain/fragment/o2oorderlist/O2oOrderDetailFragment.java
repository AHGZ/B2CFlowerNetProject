package com.android.p2pflowernet.project.o2omain.fragment.o2oorderlist;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.O2oOrderDetailAdapter;
import com.android.p2pflowernet.project.adapter.PopCartAdapter;
import com.android.p2pflowernet.project.callback.ShopCartImp;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.O2oGoodsInfoBean;
import com.android.p2pflowernet.project.entity.O2oIndexBean;
import com.android.p2pflowernet.project.entity.ShopCart;
import com.android.p2pflowernet.project.event.RefreshSyncListCart;
import com.android.p2pflowernet.project.event.StoreDetailsEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.fragment.o2oorderlist.o2oorderdetail.IO2oOrderDetailPrenter;
import com.android.p2pflowernet.project.o2omain.view.MaxHeightRecyclerView;
import com.android.p2pflowernet.project.o2omain.view.ShopCarView;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.android.p2pflowernet.project.R.id.yi_sale;
import static com.android.p2pflowernet.project.o2omain.fragment.storedetail.StoreDetailFragment.carAdapter;


/**
 * Created by caishen on 2018/1/23.
 * by--外卖商品明细
 */

public class O2oOrderDetailFragment extends KFragment<IO2oOrderDetailView, IO2oOrderDetailPrenter>
        implements IO2oOrderDetailView, NormalTopBar.normalTopClickListener, ShopCartImp {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(yi_sale)
    TextView yiSale;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_ms_price)
    TextView tvMsPrice;
    @BindView(R.id.tv_huafan)
    TextView tvHuafan;
    @BindView(R.id.add_shopcar)
    ImageView addShopcar;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.pull_refresh)
    PullToRefreshLayout pullRefresh;
    @BindView(R.id.blackview)
    View blackview;
    @BindView(R.id.clear_tv)
    TextView clearTv;
    @BindView(R.id.car_recyclerview)
    MaxHeightRecyclerView carRecyclerview;
    @BindView(R.id.car_container)
    LinearLayout mCarContainer;
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
    @BindView(R.id.banner)
    ImageView imageView;
    @BindView(R.id.ivGoodsAdd)
    ImageView ivGoodsAdd;
    @BindView(R.id.tvGoodsSelectNum)
    TextView tvGoodsSelectNum;
    @BindView(R.id.ivGoodsMinus)
    ImageView ivGoodsMinus;
    private ShapeLoadingDialog shapeLoadingDialog;
    private String merch_id = "";
    private String goods_id = "";
    private int page = 1;
    private boolean isLoad = false;
    private O2oOrderDetailAdapter o2oOrderDetailAdapter;
    private int count = 0;
    private String distrib_quota = "";//起送价
    private ShopCart mShopCart;

    @Override
    public IO2oOrderDetailPrenter createPresenter() {

        return new IO2oOrderDetailPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_o2o_order_detail;
    }

    @Override
    public void initData() {

        mPresenter.get_goods_info();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        merch_id = arguments.getString("merch_id");
        goods_id = arguments.getString("goods_id");
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        Utils.setStatusBar(getActivity(), 4, false);
        normalTop.setBackgroundColor(Color.parseColor("#00000000"));
        normalTop.getLeftImage().setImageResource(R.mipmap.icon_back_white);
        normalTop.setTopClickListener(this);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .delay(5000)
                .loadText("加载中...")
                .build();

        //获取布局管理者
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //设置为垂直排列格式
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //recyclerview应用垂直排列格式
        recycleview.setLayoutManager(linearLayoutManager);
        recycleview.setNestedScrollingEnabled(false);

        initData();

        mShopCart = new ShopCart();

        pullRefresh.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {

                isLoad = true;
                page = 1;
                mPresenter.get_goods_info();
                pullRefresh.finishRefresh();
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
                pullRefresh.finishLoadMore();
            }
        });
    }

    public static KFragment newIntence(String merch_id, String goods_id) {

        O2oOrderDetailFragment o2oOrderDetailFragment = new O2oOrderDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("merch_id", merch_id);
        bundle.putString("goods_id", goods_id);
        o2oOrderDetailFragment.setArguments(bundle);
        return o2oOrderDetailFragment;
    }


    /**
     * 添加 或者  删除  商品发送的消息处理
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StoreDetailsEvent event) {

        O2oIndexBean o2oIndexBean = event.getStoreDetail();
        int is_close = o2oIndexBean.getIs_close();
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
        distribMoney.setText(TextUtils.isEmpty(o2oIndexBean.getDistrib_money()) ? "" : "另需配送费¥"
                + o2oIndexBean.getDistrib_money() + "元");
        // 起送价
        distrib_quota = o2oIndexBean.getDistrib_quota();
        carLimit.setText(TextUtils.isEmpty(o2oIndexBean.getDistrib_quota()) ? "" : "¥"
                + o2oIndexBean.getDistrib_quota() + "元起送");
    }


    /**
     * 修改购物车状态（加）
     */
    private void changeShopCart() {

//        EventBus.getDefault().post(new MessageEvent("add", shopCart.getShoppingAccount(),
//                shopCart.getShoppingTotalPrice(), shopCart));
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
    public String getMerId() {
        return merch_id;
    }

    @Override
    public String getGoodId() {
        return goods_id;
    }

    @Override
    public int getpage() {
        return page;
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void onSuccessData(O2oGoodsInfoBean data) {

        if (data != null) {

            List<O2oGoodsInfoBean.ListsBean> lists = data.getLists();
            count = lists.size();

            tvTitle.setText(data.getName() == null ? "" : data.getName());
            yiSale.setText(data.getSales_Mouth() == null ? "已售:" + "0" : "已售:" + data.getSales_month());
            tvPrice.setText(data.getPrice() == null ? "¥ 0" : "¥" + data.getPrice());
            tvHuafan.setText(data.getHuafan() == null ? "花返：¥ 0" : "花返：¥" + data.getHuafan());

            String goods_img = ApiUrlConstant.API_IMG_URL + data.getGoods_img();
            new GlideImageLoader().displayImage(getActivity(), goods_img, imageView);

            if (!isLoad) {

                if (lists != null && lists.size() > 0) {

                    recycleview.setVisibility(View.VISIBLE);
                    //设置适配器
                    o2oOrderDetailAdapter = new O2oOrderDetailAdapter(getActivity());
                    o2oOrderDetailAdapter.attachRecyclerView(recycleview);
                    o2oOrderDetailAdapter.setList(lists);

                } else {

                    recycleview.setVisibility(View.GONE);
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
    }

    @OnClick({R.id.car_limit, R.id.clear_tv, R.id.ivGoodsAdd, R.id.ivGoodsMinus,R.id.add_shopcar})
    public void onViewOnClick(View view) {
        switch (view.getId()) {
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
                }

                break;
            case R.id.ivGoodsAdd://加



                break;
            case R.id.ivGoodsMinus://减



                break;
            case R.id.add_shopcar://加入购物车



                break;
        }
    }

    //设置
    private void setCartDatas(ShopCart shopCart) {

        carAdapter = new PopCartAdapter(getActivity(), shopCart);
        carRecyclerview.setAdapter(carAdapter);
        carAdapter.setShopCartImp(this);
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

    /**
     * 购物车加
     *
     * @param shopCart
     * @param postion
     */
    @Override
    public void add(ShopCart shopCart, int postion) {

    }

    /***
     * 购物车减
     * @param shopCart
     * @param postion
     */
    @Override
    public void remove(ShopCart shopCart, int postion) {

    }
}

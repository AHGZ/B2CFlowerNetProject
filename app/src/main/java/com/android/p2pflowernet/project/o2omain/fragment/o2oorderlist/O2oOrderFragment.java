package com.android.p2pflowernet.project.o2omain.fragment.o2oorderlist;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

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
import com.android.p2pflowernet.project.entity.ShopCart;
import com.android.p2pflowernet.project.event.GoodsaddAminEvent;
import com.android.p2pflowernet.project.event.MessageEvent;
import com.android.p2pflowernet.project.event.RefreshSyncListCart;
import com.android.p2pflowernet.project.event.StoreDetailEvent;
import com.android.p2pflowernet.project.event.StoreDetailsEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.fragment.storedetail.confirmorder.ConfirmOrderActivity;
import com.android.p2pflowernet.project.o2omain.view.shopcarview.OnHeaderClickListener;
import com.android.p2pflowernet.project.o2omain.view.shopcarview.StickyHeadersBuilder;
import com.android.p2pflowernet.project.o2omain.view.shopcarview.StickyHeadersItemDecoration;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.utils.SPUtils;
import com.android.p2pflowernet.project.view.customview.CustomPopupWindow;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.android.p2pflowernet.project.o2omain.fragment.storedetail.StoreDetailFragment.carAdapter;

/**
 * @描述:O2o点餐列表
 * @创建人：zhangpeisen
 * @创建时间：2017/10/30 下午5:40
 * @修改人：zhangpeisen
 * @修改时间：2017/10/30 下午5:40
 * @修改备注：
 * @throws
 */
public class O2oOrderFragment extends KFragment<O2oOrderView, IO2oOrderPrenter>
        implements OnHeaderClickListener, ShopCartImp, O2oOrderView {

    int num = 0;
    @BindView(R.id.goods_category_list)
    RecyclerView goodsCategoryList;
    @BindView(R.id.goods_recycleView)
    RecyclerView goodsRecycleView;
    private RecycleGoodsCategoryListAdapter mGoodsCategoryListAdapter;
    //商品类别列表
    private List<O2oIndexBean.ListsBean> O2oGsCatrgorylists = new ArrayList<>();
    //商品列表
    private List<O2oIndexBean.ListsBean.GoodsListBean> goodsitemdatas = new ArrayList<>();

    //存储含有标题的第一个含有商品类别名称的条目的下表
    private List<Integer> titlePois = new ArrayList<>();
    //上一个标题的小标
    private int lastTitlePoi;


    public static PersonAdapter personAdapter;
    private StickyHeadersItemDecoration top;
    private BigramHeaderAdapter headerAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private ShopCart shopCart;
    private String merch_id = "";
    private String goods_id = "";
    private int page = 1;
    private ShapeLoadingDialog shapeLoadingDialog;
    private boolean isLoad = false;
    private O2oOrderDetailAdapter o2oOrderDetailAdapter;
    private int count = 0;
    private ImageView buyImg;
    private CustomPopupWindow mPop;
    private List<ShopCart> shopCarts;
    private O2oIndexBean o2oIndexBean;
    private BottomSheetBehavior<LinearLayout> behavior;
    private PopCartAdapter carAdapter1;
    private O2oIndexBean.ListsBean.GoodsListBean goodsListBean;
    private PopupWindow popupWindow;

    public static O2oOrderFragment newInstance(String merch_id) {
        Bundle args = new Bundle();
        args.putString("merch_id", merch_id);
        O2oOrderFragment fragment = new O2oOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        merch_id = getArguments().getString("merch_id");
    }

    @Override
    public IO2oOrderPrenter createPresenter() {
        return new IO2oOrderPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.o2oorder_fragment;
    }

    @Override
    public void initData() {
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StoreDetailEvent storeDetailEvent) {
        if (storeDetailEvent == null) {
            return;
        }
        int i = 0;
        int j = 0;
        boolean isFirst;

        List<O2oIndexBean.ListsBean> o2oIndexBeanLists = storeDetailEvent.o2oIndexBeanLists;

        List<O2oIndexBean.ListsBean> indexBeanLists = storeDetailEvent.o2oIndexBeanLists;
        if (indexBeanLists == null) {
            return;
        }
        for (O2oIndexBean.ListsBean mO2oListsBean : indexBeanLists) {
            O2oGsCatrgorylists.add(mO2oListsBean);
            isFirst = true;
            for (O2oIndexBean.ListsBean.GoodsListBean mGoodsLists : mO2oListsBean.getGoods_list()) {
                if (isFirst) {
                    titlePois.add(j);
                    isFirst = false;
                }
                j++;
                mGoodsLists.setTag(i);
                goodsitemdatas.add(mGoodsLists);
            }
            i++;
        }

        mGoodsCategoryListAdapter = new RecycleGoodsCategoryListAdapter(O2oGsCatrgorylists, getActivity());
        goodsCategoryList.setLayoutManager(new LinearLayoutManager(getContext()));
        goodsCategoryList.setAdapter(mGoodsCategoryListAdapter);
        mGoodsCategoryListAdapter.setOnItemClickListener(new RecycleGoodsCategoryListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                goodsRecycleView.scrollToPosition(titlePois.get(position) + position + 2);
                mGoodsCategoryListAdapter.setCheckPosition(position);
            }
        });

        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        goodsRecycleView.setLayoutManager(mLinearLayoutManager);

        shopCart = new ShopCart();
        personAdapter = new PersonAdapter(getActivity(), goodsitemdatas, shopCart);
        personAdapter.setmActivity(getActivity());
        personAdapter.setShopCartImp(this);
        headerAdapter = new BigramHeaderAdapter(getActivity(), goodsitemdatas, O2oGsCatrgorylists);

        top = new StickyHeadersBuilder()
                .setAdapter(personAdapter)
                .setRecyclerView(goodsRecycleView)
                .setStickyHeadersAdapter(headerAdapter)
                .setOnHeaderClickListener(this)
                .build();

        goodsRecycleView.addItemDecoration(top);
        goodsRecycleView.setAdapter(personAdapter);

        //设置点击事件
        personAdapter.setOnItemClickLitener(new PersonAdapter.OnItemClickLitener() {
            @Override
            public void OnitemCLickLitener(View view, int position) {

                goods_id = goodsitemdatas.get(position).getId();
                goodsListBean = goodsitemdatas.get(position);
                mPresenter.get_goods_info();
            }
        });

        goodsRecycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                for (int i = 0; i < titlePois.size(); i++) {
                    if (mLinearLayoutManager.findFirstVisibleItemPosition() >= titlePois.get(i)) {
                        mGoodsCategoryListAdapter.setCheckPosition(i);
                    }
                }
            }
        });
    }

    //初始化商品明细的弹窗
    private void initPopuW(O2oIndexBean.ListsBean.GoodsListBean goodsListBean,
                           O2oGoodsInfoBean data, RecyclerView goodsRecycleView, ShopCart shopCart) {
        //友盟统计
        MobclickAgent.onEvent(getActivity(), "storeDetailPage");

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
        if (o2oIndexBean != null) {

            int is_close = o2oIndexBean.getIs_close();
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
            mPop.distribMoney.setText(TextUtils.isEmpty(o2oIndexBean.getDistrib_money()) ? "" : "另需配送费¥"
                    + o2oIndexBean.getDistrib_money() + "元");
            // 起送价
            mPop.distrib_quota = o2oIndexBean.getDistrib_quota();
            mPop.carLimit.setText(TextUtils.isEmpty(o2oIndexBean.getDistrib_quota()) ? "" : "¥"
                    + o2oIndexBean.getDistrib_quota() + "元起送");
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
        initCLick(mPop, goodsListBean, data);

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

    //初始化监听事件
    private void initCLick(final CustomPopupWindow mPop, final O2oIndexBean.ListsBean.GoodsListBean lists,
                           final O2oGoodsInfoBean data) {

        //去结算
        mPop.setOnPayListOnlickLitener(new CustomPopupWindow.OnPayListOnlickLitener() {
            @Override
            public void onPayListOnlickLitener(View view) {

                if (mPop.carLimit.getText().toString().trim().equals("去结算")) {

                    mPresenter.goPay();
                    //友盟统计
                    MobclickAgent.onEvent(getActivity(), "o2oPay");
                }
            }
        });

        //设置分享的点击事件
        mPop.setOnShareOnlickLitener(new CustomPopupWindow.OnShareOnlickLitener() {
            @Override
            public void onSuhareOnlickLitener(View view) {

                initControls(data);
                popupWindow.showAtLocation(goodsRecycleView, Gravity.BOTTOM, 0, 0);
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

    //初始化分享功能
    private void initControls(final O2oGoodsInfoBean data) {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.sharepop, null);

        // 自适配长、框设置
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow.update();
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(0.7f);
        popupWindow.setOnDismissListener(new poponDismissListener());
        TextView esc = (TextView) view.findViewById(R.id.cancle);//取消
        esc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
            }
        });

        //朋友圈
        final LinearLayout lin1 = (LinearLayout) view.findViewById(R.id.share_wechat_circle);
        lin1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
                share1(data);
            }
        });

        //微信
        final LinearLayout lin2 = (LinearLayout) view.findViewById(R.id.share_wechat);
        lin2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();

                share2(data);
            }
        });

        //微博
        final LinearLayout lin3 = (LinearLayout) view.findViewById(R.id.share_sina);
        lin3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
                share3(data);
            }
        });

        //QQ好友
        final LinearLayout lin4 = (LinearLayout) view.findViewById(R.id.share_qq_frend);
        lin4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();

                share4(data);
            }
        });

        //QQ空间
        LinearLayout lin5 = (LinearLayout) view.findViewById(R.id.share_qq_space);
        lin5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                share5(data);
            }
        });

        //复制连接，拷贝网址
        final LinearLayout lin6 = (LinearLayout) view.findViewById(R.id.share_copy);
        lin6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
                popupWindow.dismiss();
                ClipboardManager myClipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                String text = data.getUrl() == null ? "" : data.getUrl();
                ClipData text1 = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(text1);
                showShortToast("已复制到剪切板");
            }
        });
    }

    //QQ空间分享
    private void share5(O2oGoodsInfoBean data) {

        String share_url = data.getUrl() == null ? "" : data.getUrl();

        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(data.getName() == null ? "" : data.getName());//标题
        String img = ApiUrlConstant.API_IMG_URL + data.getGoods_img();
        UMImage image = new UMImage(getActivity(), img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(data.getName() == null ? "" : data.getName());//描述

        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.QZONE)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    //QQ好友分享
    private void share4(O2oGoodsInfoBean data) {

        String share_url = data.getUrl() == null ? "" : data.getUrl();
        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(data.getName() == null ? "" : data.getName());//标题
        String img = ApiUrlConstant.API_IMG_URL + data.getGoods_img();
        UMImage image = new UMImage(getActivity(), img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(data.getName() == null ? "" : data.getName());//描述

        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.QQ)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    //微信分享
    private void share2(O2oGoodsInfoBean data) {

        String share_url = data.getUrl() == null ? "" : data.getUrl();
        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(data.getName() == null ? "" : data.getName());//标题
        String img = ApiUrlConstant.API_IMG_URL + data.getGoods_img();
        UMImage image = new UMImage(getActivity(), img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(data.getName() == null ? "" : data.getName());//描述

        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.WEIXIN)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    //微信朋友圈
    private void share1(O2oGoodsInfoBean data) {

        String share_url = data.getUrl() == null ? "" : data.getUrl();
        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(data.getName() == null ? "" : data.getName());//标题
        String img = ApiUrlConstant.API_IMG_URL + data.getGoods_img();
        UMImage image = new UMImage(getActivity(), img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(data.getName() == null ? "" : data.getName());//描
        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    //新浪微博
    private void share3(O2oGoodsInfoBean data) {

        String share_url = data.getUrl() == null ? "" : data.getUrl();
        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(data.getName() == null ? "" : data.getName());//标题
        String img = ApiUrlConstant.API_IMG_URL + data.getGoods_img();
        UMImage image = new UMImage(getActivity(), img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(data.getName() == null ? "" : data.getName());//描
        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.SINA)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }


    //分享的回调
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            showShortToast("分享成功啦");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {

            showShortToast("分享失败啦");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            showShortToast("分享取消啦");
        }
    };


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

    /**
     * 修改购物车状态（减）
     */
    private void subShopCart() {

        EventBus.getDefault().post(new MessageEvent("sub", shopCart.getShoppingAccount(),
                shopCart.getShoppingTotalPrice(), shopCart));

        if (shopCart.getShoppingAccount() > 0) {

            mPop.carBadge.setText(String.valueOf(shopCart.getShoppingAccount()));
            mPop.carBadge.setVisibility(View.VISIBLE);
            mPop.tvAmount.setVisibility(View.VISIBLE);
            mPop.amountContainer.setVisibility(View.VISIBLE);
            mPop.tvAmount.setText("¥" + String.valueOf(shopCart.getShoppingTotalPrice()));
            mPop.carNonselect.setVisibility(View.GONE);
            mPop.ivShopCar.setImageResource(R.mipmap.shop_car_have);

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshSyncListCart refreshSyncListCart) {

        personAdapter.UpdateOrderDatas();
    }

    @Override
    public void onHeaderClick(View header, long headerId) {
        TextView text = (TextView) header.findViewById(R.id.tvGoodsItemTitle);
        Toast.makeText(getActivity(), "Click on " + text.getText(), Toast.LENGTH_SHORT).show();
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

    //设置购物车数据
    private void setCartDatas(ShopCart shopCart) {

        carAdapter = new PopCartAdapter(getActivity(), shopCart);
        mPop.carRecView.setAdapter(carAdapter);
        carAdapter.setShopCartImp(this);
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
    public void add(ShopCart shopCart, int postion) {

        personAdapter.notifyItemChanged(postion);
    }

    @Override
    public void remove(ShopCart shopCart, int postion) {

        personAdapter.notifyItemChanged(postion);
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

    @Override
    public void onError(String s) {

        showShortToast(s);
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
    public void showDialog() {

        shapeLoadingDialog.show();
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog != null) {

            shapeLoadingDialog.dismiss();
        }
    }


    /**
     * 添加 或者  删除  商品发送的消息处理
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StoreDetailsEvent event) {

        o2oIndexBean = event.getStoreDetail();
    }

    @Override
    public void onSuccessData(O2oGoodsInfoBean data) {

        if (data != null) {
            //初始化底部弹窗
            initPopuW(goodsListBean, data, goodsRecycleView, this.shopCart);
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

    /***
     * 去结算回调
     * @param data
     */
    @Override
    public void onSuccessGoPay(GoPayBean data) {

        if (data != null) {

            //友盟统计
            MobclickAgent.onEvent(getActivity(), "o2oIdent");
            com.alibaba.fastjson.JSONArray jsonArray = new com.alibaba.fastjson.JSONArray();

            //获取商品列表数据
            if (shopCarts != null) {

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
            }

            //商品列表数据
            String goodslist = jsonArray.toString();

            // 商品ID
            Bundle bundle = new Bundle();
            Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
            bundle.putString("goodslist", goodslist);
            bundle.putString("merch_id", merchId());

            // 购物车数据
            bundle.putSerializable("shopcart", shopCart);
            bundle.putSerializable("gopaybean", data);
            bundle.putSerializable("o2oIndexBean1", o2oIndexBean);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }
    }
}

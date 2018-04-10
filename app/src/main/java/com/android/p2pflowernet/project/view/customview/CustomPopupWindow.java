package com.android.p2pflowernet.project.view.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.o2omain.view.MaxHeightRecyclerView;
import com.android.p2pflowernet.project.o2omain.view.ShopCarView;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by caishen on 2018/1/26.
 * by--自定义弹出外卖商品详情页面
 */

public class CustomPopupWindow extends PopupWindow implements View.OnClickListener {

    @Nullable
    @BindView(R.id.banner)
    public ImageView banner;
    @BindView(R.id.tv_title)
    public TextView tvTitle;
    @BindView(R.id.yi_sale)
    public TextView yiSale;
    @BindView(R.id.tv_price)
    public TextView tvPrice;
    @BindView(R.id.tv_ms_price)
    public TextView tvMsPrice;
    @BindView(R.id.tv_huafan)
    public TextView tvHuafan;
    @BindView(R.id.ivGoodsAdd)
    public ImageView ivGoodsAdd;
    @BindView(R.id.tvGoodsSelectNum)
    public TextView tvGoodsSelectNum;
    @BindView(R.id.ivGoodsMinus)
    public ImageView ivGoodsMinus;
    @BindView(R.id.add_shopcar)
    public ImageView addShopcar;
    @BindView(R.id.recycleview)
    public RecyclerView recycleview;
    @BindView(R.id.pull_refresh)
    public PullToRefreshLayout pullRefresh;
    @BindView(R.id.blackview)
    public View blackView;
    @BindView(R.id.clear_tv)
    public TextView clearTv;
    @BindView(R.id.car_recyclerview)
    public MaxHeightRecyclerView carRecView;
    @BindView(R.id.car_container)
    public LinearLayout mCarContainer;
    @BindView(R.id.car_nonselect)
    public TextView carNonselect;
    @BindView(R.id.tv_amount)
    public TextView tvAmount;
    @BindView(R.id.distrib_money)
    public TextView distribMoney;
    @BindView(R.id.amount_container)
    public LinearLayout amountContainer;
    @BindView(R.id.car_limit)
    public TextView carLimit;
    @BindView(R.id.car_rl)
    public RelativeLayout carRl;
    @BindView(R.id.iv_shop_car)
    public ImageView ivShopCar;
    @BindView(R.id.car_badge)
    public TextView carBadge;
    @BindView(R.id.lin_dayang)
    public LinearLayout linDayang;
    @BindView(R.id.shopCartMain)
    public ShopCarView shopCartMain;
    @BindView(R.id.group_buying_shopdetails_back)
    ImageView groupBuyingShopdetailsBack;
    @BindView(R.id.group_buying_shopdetails_shape)
    ImageView groupBuyingShopdetailsShape;
    @BindView(R.id.elast_scroll)
    ElastticityScrollView elastScroll;
    @BindView(R.id.activity_main)
    CoordinatorLayout activityMain;
    @BindView(R.id.ll_detail)
    LinearLayout llDetail;
    private View mPopView;
    private OnItemClickListener mListener;
    private OnClearListOnlickLitener onClearListOnlickLitener;
    private OnAddShopcarListOnlickLitener onAddShopcarListOnlickLitener;
    private OnAddListOnlickLitener onAddListOnlickLitener;
    private OnSubListOnlickLitener onSubListOnlickLitener;
    private OnPayListOnlickLitener onPayListOnlickLitener;
    private OnShareOnlickLitener onShareOnlickLitener;
    public String distrib_quota = "";

    public CustomPopupWindow(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
        setPopupWindow();
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {

        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(context);
        //绑定布局
        mPopView = inflater.inflate(R.layout.fragment_o2o_order_detail, null);
        ButterKnife.bind(this, mPopView);
    }


    public void setOnClearListOnlickLitener(OnClearListOnlickLitener onClearListOnlickLitener) {
        this.onClearListOnlickLitener = onClearListOnlickLitener;
    }

    public void setOnAddShopcarListOnlickLitener(OnAddShopcarListOnlickLitener onAddShopcarListOnlickLitener) {
        this.onAddShopcarListOnlickLitener = onAddShopcarListOnlickLitener;
    }

    public void setOnAddListOnlickLitener(OnAddListOnlickLitener onAddListOnlickLitener) {
        this.onAddListOnlickLitener = onAddListOnlickLitener;
    }

    public void setOnSubListOnlickLitener(OnSubListOnlickLitener onSubListOnlickLitener) {
        this.onSubListOnlickLitener = onSubListOnlickLitener;
    }

    public void setOnPayListOnlickLitener(OnPayListOnlickLitener onPayListOnlickLitener) {
        this.onPayListOnlickLitener = onPayListOnlickLitener;
    }

    public void setOnShareOnlickLitener(OnShareOnlickLitener onShareOnlickLitener) {
        this.onShareOnlickLitener = onShareOnlickLitener;
    }

    /***
     * 清除商品列表数据
     */
    public interface OnClearListOnlickLitener {

        void onClearListOnlickLitener(View view);
    }

    /***
     * 去结算
     */
    public interface OnPayListOnlickLitener {

        void onPayListOnlickLitener(View view);
    }

    /***
     * 加入购物车
     */
    public interface OnAddShopcarListOnlickLitener {

        void onAddShopcarListOnlickLitener(View view);
    }

    /***
     * 加
     */
    public interface OnAddListOnlickLitener {

        void onAddListOnlickLitener(View view);
    }

    /***
     * 减
     */
    public interface OnSubListOnlickLitener {

        void onSubListOnlickLitener(View view);
    }

    /***
     * 减
     */
    public interface OnShareOnlickLitener {

        void onSuhareOnlickLitener(View view);
    }


    /**
     * 设置窗口的相关属性
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow() {

        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        this.setAnimationStyle(R.style.PopWindowstyle);// 设置动画
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        mPopView.setOnTouchListener(new View.OnTouchListener() {// 如果触摸位置在窗口外面则销毁

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int height = mPopView.findViewById(R.id.activity_main).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        recycleview.setNestedScrollingEnabled(false);
        //返回的点击事件
        groupBuyingShopdetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });

        //分享
        groupBuyingShopdetailsShape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (onShareOnlickLitener != null) {

                    onShareOnlickLitener.onSuhareOnlickLitener(groupBuyingShopdetailsShape);
                }
            }
        });
    }


    @OnClick({R.id.car_limit, R.id.clear_tv, R.id.ivGoodsAdd, R.id.ivGoodsMinus, R.id.add_shopcar})
    public void onViewOnClick(View view) {
        switch (view.getId()) {
            case R.id.clear_tv://清除商品列表数据

                if (onClearListOnlickLitener != null) {

                    onClearListOnlickLitener.onClearListOnlickLitener(view);
                }
                break;
            case R.id.car_limit://去结算

                if (onPayListOnlickLitener != null) {

                    onPayListOnlickLitener.onPayListOnlickLitener(view);
                }
                break;
            case R.id.ivGoodsAdd://加

                if (onAddListOnlickLitener != null) {

                    onAddListOnlickLitener.onAddListOnlickLitener(view);
                }

                break;
            case R.id.ivGoodsMinus://减

                if (onSubListOnlickLitener != null) {

                    onSubListOnlickLitener.onSubListOnlickLitener(view);
                }

                break;
            case R.id.add_shopcar://加入购物车

                if (onAddShopcarListOnlickLitener != null) {

                    onAddShopcarListOnlickLitener.onAddShopcarListOnlickLitener(view);
                }
                break;
            case R.id.shopCartMain://已买列表
                boolean isShow = false;

                if (isShow) {

                    isShow = false;
                    shopCartMain.showBadge(0);

                } else {

                    isShow = true;
                    shopCartMain.showBadge(10);
                }

                break;
        }
    }


    /**
     * 定义一个接口，公布出去 在Activity中操作按钮的单击事件
     */
    public interface OnItemClickListener {
        void setOnItemClick(View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (mListener != null) {
            mListener.setOnItemClick(v);
        }
    }
}

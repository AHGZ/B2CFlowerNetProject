package com.android.p2pflowernet.project.view.fragments.goods.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.MainActivity;
import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.ItemTitlePagerAdapter;
import com.android.p2pflowernet.project.entity.ActionItem;
import com.android.p2pflowernet.project.entity.OrderDetailBean;
import com.android.p2pflowernet.project.entity.ShopCarBean;
import com.android.p2pflowernet.project.event.CartEvent;
import com.android.p2pflowernet.project.event.MainEvent;
import com.android.p2pflowernet.project.event.SildeEvent;
import com.android.p2pflowernet.project.event.TranslationEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.activity.GoodsDetailActivity;
import com.android.p2pflowernet.project.view.customview.DragIndicatorView;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.TitlePopup;
import com.android.p2pflowernet.project.view.fragments.affirm.AffirmIndentActivity;
import com.android.p2pflowernet.project.view.fragments.goods.comment.GoodsCommentFragment;
import com.android.p2pflowernet.project.view.fragments.goods.detail.huafan.GoodsHuaFanFragment;
import com.android.p2pflowernet.project.view.fragments.goods.info.GoodsInfoFragment;
import com.android.p2pflowernet.project.view.fragments.search.SearchActivity;
import com.gxz.PagerSlidingTabStrip;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhangpeisen
 * created on: 2017/10/18 下午3:03
 * description: 商品详情主页面
 */
public class GoodsDetailHomeFragment extends KFragment<IGoodsDetailHomeView, IGoodsDetailHomePresenter>
        implements IGoodsDetailHomeView, AffirmIndentActivity.FragmentBackListener, GoodsDetailActivity.FragmentBackListener
        ,TitlePopup.OnItemOnClickListener{

    @BindView(R.id.psts_tabs)
    PagerSlidingTabStrip psts_tabs;
    @BindView(R.id.vp_content)
    ViewPager vp_content;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.ll_back)
    // back
            LinearLayout ll_back;
    @BindView(R.id.gwc_im)
    // 购物车
            ImageView gwc_im;
    @BindView(R.id.meun_im)
    // 菜单按钮
            ImageView meun_im;
    @BindView(R.id.tv_good_info_callcenter)
    // 客服
            LinearLayout tv_good_info_callcenter;
    @BindView(R.id.tv_good_info_collection)
    // 收藏
            TextView tv_good_info_collection;
    @BindView(R.id.btn_good_info_pay)
    // 立即购买
            TextView btn_good_info_pay;
    @BindView(R.id.btn_good_info_cart)
    // 加入购物车
            Button btn_good_info_cart;
    @BindView(R.id.addshoppingcart_parentlayout)
    LinearLayout parentLayout;//父布局
    @BindView(R.id.tv_shop_count)
    TextView tvCount;
    @BindView(R.id.dv_shop_num)
    DragIndicatorView dvShopNum;
    @BindView(R.id.ll_shop_car)
    LinearLayout llShopCar;

    private List<KFragment> fragmentList = new ArrayList<>();
    private String goods_Id;
    private ShapeLoadingDialog shapeLoadingDialog;
    private TitlePopup titlePopup;
    private ShopCarBean.ListBean.ShopBean mShopBean;

    public static GoodsDetailHomeFragment newInstance(String goodId, ShopCarBean.ListBean.ShopBean shopBean) {

        Bundle args = new Bundle();
        args.putString("goodsId", goodId);
        args.putSerializable("shopBean", shopBean);
        GoodsDetailHomeFragment fragment = new GoodsDetailHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goods_Id = getArguments().getString("goodsId");
        mShopBean = (ShopCarBean.ListBean.ShopBean) getArguments().getSerializable("shopBean");
    }

    @Override
    public IGoodsDetailHomePresenter createPresenter() {
        return new IGoodsDetailHomePresenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.goodshomedetail_fragment;
    }

    @Override
    public void initData() {


    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸式
        Utils.setStatusBar(getActivity(), 1, false);
        // 购物车扩大事件的点击范围
        UIUtils.setTouchDelegate(gwc_im, 50);
        // 菜单扩大事件的点击范围
        UIUtils.setTouchDelegate(meun_im, 50);
        // 客服扩大事件的点击范围
        UIUtils.setTouchDelegate(tv_good_info_callcenter, 50);
        // 收藏扩大事件的点击范围
        UIUtils.setTouchDelegate(tv_good_info_collection, 50);

        psts_tabs = (PagerSlidingTabStrip) view.findViewById(R.id.psts_tabs);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        vp_content = (ViewPager) view.findViewById(R.id.vp_content);
        fragmentList.add(GoodsInfoFragment.newInstance(goods_Id, mShopBean));
        fragmentList.add(GoodsDetailFragment.newInstance(goods_Id));
        fragmentList.add(GoodsCommentFragment.newInstance(goods_Id));
        fragmentList.add(GoodsHuaFanFragment.newInstance(goods_Id));


        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        ItemTitlePagerAdapter itemTitlePagerAdapter = new ItemTitlePagerAdapter(getChildFragmentManager());
        itemTitlePagerAdapter.setFramentData(fragmentList);
        itemTitlePagerAdapter.setTitleData(new String[]{"商品", "详情", "评价", "返润"});

        vp_content.setAdapter(itemTitlePagerAdapter);
        vp_content.setOffscreenPageLimit(4);
        psts_tabs.setViewPager(vp_content);

        //初始化右上角弹窗数据
        initTitlePou();

    }

    private void initTitlePou() {

        //初始化右上角弹窗
        titlePopup = new TitlePopup(getActivity(), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 给标题栏弹窗添加子类
        titlePopup.addAction(new ActionItem(getActivity(), "首页", R.drawable.fr_icon_sy));
        titlePopup.addAction(new ActionItem(getActivity(), "搜索", R.drawable.fr_icon_ss));
        titlePopup.setItemOnClickListener(this);
    }

    public void getOpen() {

        tv_title.setVisibility(View.VISIBLE);
        psts_tabs.setVisibility(View.GONE);

    }

    public void getClose() {

        tv_title.setVisibility(View.GONE);
        psts_tabs.setVisibility(View.VISIBLE);

    }

    @OnClick({R.id.ll_back, R.id.gwc_im, R.id.meun_im, R.id.btn_good_info_pay, R.id.btn_good_info_cart,
            R.id.tv_good_info_callcenter, R.id.ll_shop_car})
    public void onViewOnClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                // 返回键
                removeFragment();

                break;
            case R.id.gwc_im:

                showShortToast("到购物车列表");

                break;
            case R.id.meun_im:

                titlePopup.show(view);

                break;
            case R.id.tv_good_info_callcenter:

                showShortToast("客服");

                break;
            case R.id.ll_shop_car://购物车

                removeFragment();
                //跳转至购物车页面
                EventBus.getDefault().post(new MainEvent(3));

                break;
            case R.id.btn_good_info_cart:

                // 添加购物车
                CartEvent cartEvent = new CartEvent();
                cartEvent.setmShoppingCartImageView(gwc_im);
                cartEvent.setParentLayout(parentLayout);
                cartEvent.setTvCount(tvCount);
                cartEvent.setTag(0);//添加购物车
                EventBus.getDefault().post(cartEvent);

                break;
            case R.id.btn_good_info_pay:

                CartEvent cartEvent1 = new CartEvent();
                cartEvent1.setmShoppingCartImageView(gwc_im);
                cartEvent1.setParentLayout(parentLayout);
                cartEvent1.setTvCount(tvCount);
                cartEvent1.setTag(1);//立即购买
                EventBus.getDefault().post(cartEvent1);

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SildeEvent event) {
        if (event.getTag() == 0) {
            getOpen();
        } else {
            getClose();
        }
    }

    /**
     * 平移
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TranslationEvent event) {

        vp_content.setCurrentItem(event.getStr());
    }

    @Override
    public void onError(String s) {

        showShortToast(s);
    }

    @Override
    public String getSpecId() {
        return null;
    }

    @Override
    public String getCount() {
        return null;
    }

    @Override
    public String getSource() {
        return null;
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void SuccessOrder(OrderDetailBean data) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //注册监听
        ((GoodsDetailActivity) getActivity()).setBackListener(this);
        ((GoodsDetailActivity) getActivity()).setInterception(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //取消监听
        ((GoodsDetailActivity) getActivity()).setBackListener(null);
        ((GoodsDetailActivity) getActivity()).setInterception(false);
    }

    /**
     * 虚拟键返回监听回调
     */
    @Override
    public void onBackForward() {
        removeFragment();
    }

    @Override
    public void onItemClick(ActionItem item, int position) {
        switch (position) {
            case 0://首页

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                removeFragment();

                break;
            case 1://搜索

                Intent intent1 = new Intent(getActivity(), SearchActivity.class);
                intent1.putExtra("tag", "0");//0-b2c 1-o2o
                startActivity(intent1);
                removeFragment();

                break;
        }
    }
}

package com.android.p2pflowernet.project.view.fragments.trade;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.ShopCartAdapter;
import com.android.p2pflowernet.project.entity.GoodsBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.fragments.affirm.AffirmIndentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhangpeisen
 * created on: 2017/10/10 上午10:40
 * description: 购物车模块
 */
public class TradeFragment extends KFragment<ITradeView, ITradePresenter> {


    @BindView(R.id.tv_update)
    TextView tvUpdate;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.checkbox_all)
    CheckBox checkboxAll;
    @BindView(R.id.tv_shopcart_total)
    TextView tvShopcartTotal;
    @BindView(R.id.btn_check_out)
    Button btnCheckOut;
    @BindView(R.id.ll_check_all)
    LinearLayout llCheckAll;
    @BindView(R.id.cb_all)
    CheckBox cbAll;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.btn_collection)
    Button btnCollection;
    @BindView(R.id.ll_delete)
    LinearLayout llDelete;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.tv_empty_cart_tobuy)
    TextView tvEmptyCartTobuy;
    @BindView(R.id.ll_empty_shopcart)
    LinearLayout llEmptyShopcart;
    @BindView(R.id.frame)
    LinearLayout frame;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_shopcart_rabate)
    TextView tvShopcartRabate;
    private String content;
    private ShopCartAdapter adapter;
    /**
     * 编辑状态
     */
    private static final int ACTION_EDIT = 0;
    /**
     * 完成状态
     */
    private static final int ACTION_COMPLETE = 1;
    private List<GoodsBean> datas = new ArrayList<>();

    public static TradeFragment newInstance(String content) {
        Bundle args = new Bundle();
        TradeFragment fragment = new TradeFragment();
        args.putString("content", content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content = getArguments().getString("content");

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public ITradePresenter createPresenter() {

        return new ITradePresenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.trade_fagment;
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //编辑按钮
        tvUpdate.setTag(ACTION_EDIT);
        llCheckAll.setVisibility(View.VISIBLE);
        tvEmptyCartTobuy.setClickable(true);
        showData();
    }

    //模拟数据
    @Override
    public void initData() {


    }

    @OnClick({R.id.btn_check_out, R.id.btn_delete, R.id.tv_empty_cart_tobuy, R.id.tv_update, R.id.iv_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_check_out://结算按钮

                Intent intent = new Intent(getActivity(), AffirmIndentActivity.class);
                startActivity(intent);

                break;
            case R.id.btn_delete://删除按钮

                adapter.deleteData();
                adapter.showTotalPrice();

                //显示空空如也
                checkData();
                adapter.checkAll();

                break;
            case R.id.tv_empty_cart_tobuy://购物车为空的情况


                break;

            case R.id.tv_update://编辑

                //设置编辑的点击事件
                int tag = (int) tvUpdate.getTag();
                if (tag == ACTION_EDIT) {

                    //变成完成状态
                    showDelete();

                } else {

                    //变成编辑状态
                    hideDelete();
                }

                break;
            case R.id.iv_message://消息


                break;
        }
    }

    private void hideDelete() {

        tvUpdate.setText("编辑");
        tvUpdate.setTag(ACTION_EDIT);
        adapter.checkAll_none(true);
        llDelete.setVisibility(View.GONE);
        llCheckAll.setVisibility(View.VISIBLE);
        adapter.showTotalPrice();

    }

    private void showDelete() {

        tvUpdate.setText("完成");
        tvUpdate.setTag(ACTION_COMPLETE);

        adapter.checkAll_none(false);
        cbAll.setChecked(false);
        checkboxAll.setChecked(false);

        llDelete.setVisibility(View.VISIBLE);
        llCheckAll.setVisibility(View.GONE);
        adapter.showTotalPrice();
    }

    //更新页面
    private void checkData() {

        if (adapter != null && adapter.getItemCount() > 0) {

            tvUpdate.setVisibility(View.VISIBLE);
            llEmptyShopcart.setVisibility(View.GONE);

        } else {

            llDelete.setVisibility(View.GONE);
            llEmptyShopcart.setVisibility(View.VISIBLE);
            tvUpdate.setVisibility(View.GONE);
        }
    }

    //展示数据
    private void showData() {

        for (int i = 0; i < 3; i++) {

            GoodsBean goodsBean = new GoodsBean();
            goodsBean.setCover_price("111.0");
            goodsBean.setShopId("1");
            goodsBean.setColor("蓝色");
            goodsBean.setName("华硕（ASUS）经典系列X554LP 15.6英寸笔记本 " +
                    "（i5-5200U 4G 500G R5-M230 1G独显 蓝牙 Win8.1 黑色）");
            goodsBean.setNumber(3);
            goodsBean.setStockOut(true);
            goodsBean.setRabate_price("10.0");
            datas.add(goodsBean);
        }

        for (int i = 0; i < 5; i++) {

            GoodsBean goodsBean = new GoodsBean();
            goodsBean.setCover_price("2560.0");
            goodsBean.setShopId("2");
            goodsBean.setColor("绿色");
            goodsBean.setStockOut(false);
            goodsBean.setName("华硕（ASUS）经典系列X554LP 15.6英寸笔记本 ");
            goodsBean.setNumber(1);
            goodsBean.setRabate_price("100.0");
            datas.add(goodsBean);
        }

        isSelectFirst(datas);
        CartProvider cartProvider = CartProvider.getInstance();

        if (datas != null && datas.size() > 0) {

            tvUpdate.setVisibility(View.VISIBLE);
            adapter = new ShopCartAdapter(getActivity(), datas, tvShopcartTotal, tvShopcartRabate, cartProvider, checkboxAll, cbAll);
            recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerview.setAdapter(adapter);
            llEmptyShopcart.setVisibility(View.GONE);

        } else {

            //显示空的
            tvUpdate.setVisibility(View.GONE);
            llEmptyShopcart.setVisibility(View.VISIBLE);
        }
    }

    public static void isSelectFirst(List<GoodsBean> list) {
        if (list.size() > 0) {

            list.get(0).setIsFirst(1);

            for (int i = 1; i < list.size(); i++) {

                if (list.get(i).getShopId() == list.get(i - 1).getShopId()) {

                    list.get(i).setIsFirst(2);

                } else {

                    list.get(i).setIsFirst(1);
                }
            }
        }
    }
}

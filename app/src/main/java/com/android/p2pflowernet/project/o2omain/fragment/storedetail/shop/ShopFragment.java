package com.android.p2pflowernet.project.o2omain.fragment.storedetail.shop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.ShopManFanGridview;
import com.android.p2pflowernet.project.adapter.ShopTimeListAdapter;
import com.android.p2pflowernet.project.entity.MerchantBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.customview.MyGridView;
import com.android.p2pflowernet.project.view.customview.MyListView;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by heguozhong on 2018/1/12/012.
 * 店铺商家主页面
 */

public class ShopFragment extends KFragment<IShopView, IShopPresenter> implements IShopView {

    @BindView(R.id.shop_phone)
    ImageView shopPhone;
    @BindView(R.id.shop_tv_address)
    TextView shopTvAddress;
    //配送服务
    @BindView(R.id.shop_tv_dispatching)
    TextView shopTvDispatching;
    //营业时间
    @BindView(R.id.shop_tv_dobusiness_time)
    TextView shopTvDobusinessTime;

    @BindView(R.id.shop_time_listview)
    MyListView myListView;
    //装载满返规则的gridview
    @BindView(R.id.shop_gridview)
    MyGridView shopGridview;
    @BindView(R.id.shop_spaqda_lin)
    LinearLayout shop_spaqda_lin;

    // 加载view
    private ShapeLoadingDialog shapeLoadingDialog;
    //商家电话
    private String manager_tel;
    private String merch_id;

    public static ShopFragment newInstance(String merch_id) {
        Bundle args = new Bundle();
        args.putString("merch_id", merch_id);
        ShopFragment fragment = new ShopFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        merch_id = getArguments().getString("merch_id");
    }

    @Override
    public IShopPresenter createPresenter() {
        return new IShopPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.shop;
    }

    @Override
    public void initData() {
        mPresenter.getShopMerchantInfo(merch_id);
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();
        initData();
    }


    @OnClick({R.id.shop_phone,R.id.shop_spaqda_lin})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.shop_phone:
                call(manager_tel);
                break;
            case R.id.shop_spaqda_lin:
                showShortToast("食品安全档案被点击");
                break;
        }
    }

    //拨打手机号
    public void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
    public void onSuccess(MerchantBean merchantBean) {

        if (merchantBean != null) {

            shopTvAddress.setText(merchantBean.getInfo().getArea_name());
            //商家电话
            manager_tel = merchantBean.getInfo().getManager_tel();
            //设置配送服务
            shopTvDispatching.setText("配送服务：" + merchantBean.getInfo().getDistrib());
            //
            List<MerchantBean.InfoBean.TimeSettingBean> time_setting = merchantBean.getInfo().getTime_setting();
            ShopTimeListAdapter shopTimeListAdapter = new ShopTimeListAdapter(getActivity(), time_setting);
            myListView.setAdapter(shopTimeListAdapter);

            List<MerchantBean.InfoBean.ActivityBean> activity = merchantBean.getInfo().getActivity();
            ShopManFanGridview shopManFanGridview = new ShopManFanGridview(getActivity(), activity);
            shopGridview.setAdapter(shopManFanGridview);
        }
    }
}

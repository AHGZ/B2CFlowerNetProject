package com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.DetailGroupAdapter;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.CouponCodeBean;
import com.android.p2pflowernet.project.entity.TakeOutOrderGroupBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.DateUtils;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.MyListView;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by caishen on 2018/1/6.
 * by--团购订单详情
 */

public class TakeOutGroupDetailFragment extends KFragment<ITakeOutGroupDetailView, ITakeOutGroupDetailPrenter>
        implements NormalTopBar.normalTopClickListener,ITakeOutGroupDetailView{

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.tv_storename)
    TextView tvStorename;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_merchandise_name)
    TextView tvMerchandiseName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_huafan)
    TextView tvHuafan;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.ll_one)
    LinearLayout llOne;
    @BindView(R.id.ll_detail)
    LinearLayout llDetail;
    @BindView(R.id.listview)
    MyListView listview;
    private TakeOutOrderGroupBean.ListBean listBean;
    private ShapeLoadingDialog shapeLoadingDialog;
    private List<CouponCodeBean.ListBean> couponCodeBeans;
    private DetailGroupAdapter mAdapter;

    @Override
    public ITakeOutGroupDetailPrenter createPresenter() {
        return new ITakeOutGroupDetailPrenter();
    }

    public static KFragment newIntence(TakeOutOrderGroupBean.ListBean listBean) {

        TakeOutGroupDetailFragment takeOutGroupDetailFragment = new TakeOutGroupDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("good",listBean);
        takeOutGroupDetailFragment.setArguments(bundle);
        return takeOutGroupDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listBean = (TakeOutOrderGroupBean.ListBean) getArguments().getSerializable("good");
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_detail_takeout_group;
    }

    @Override
    public void initData() {

        mPresenter.selCode();

//        //设置适配器
//        List<TakeOutGroupBean> list = TakeOutGroupModel.getList(getActivity());

        mAdapter = new DetailGroupAdapter(getActivity(), couponCodeBeans);
        listview.setAdapter(mAdapter);

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸栏
        Utils.setStatusBar(getActivity(), 3, false);
        normalTop.setTitleText("团购订单");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.color.o2o_red);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .delay(5000)
                .loadText("加载中...")
                .build();

        couponCodeBeans = new ArrayList<>();

        initView();
        initData();

    }

    private void initView() {
        if (null != listBean) {
            String str = listBean.getState();
            tvStorename.setText(null == listBean.getManager_name()?"":listBean.getManager_name());
            new GlideImageLoader().displayImage(getActivity(), ApiUrlConstant.API_IMG_URL + listBean.getFile_path(), ivImg);
            tvMerchandiseName.setText(null == listBean.getTitle() ? "" : listBean.getTitle());
            tvPrice.setText(null == listBean.getPrice() ? "" : "¥" + listBean.getPrice());
            tvHuafan.setText(null == listBean.getRebate() ? "" : "¥" + listBean.getRebate());
            tvNum.setText(null == listBean.getNum() ? "" : "x" + listBean.getNum());
            String startDate = DateUtils.timedate(listBean.getStarttime());
            String endDate = DateUtils.timedate(listBean.getEndtime());
            tvDate.setText(startDate + "—" + endDate);

            //0-没付款 1-待使用 2-(全部份数)已使用 3-已退款',
            if ("0".equals(str)) {
                str = "没付款";
            }else if("1".equals(str)){
                str = "待使用";
            }else if("2".equals(str)){
                str = "已使用";
            }else if("3".equals(str)){
                str = "已退款";
            }
            tvState.setText(TextUtils.isEmpty(str) ? "" : str);
        }else{
            tvMerchandiseName.setText("");
            tvState.setText("");
            tvHuafan.setText("");
            tvDate.setText("");
            tvPrice.setText("");
            tvNum.setText("");
            ivImg.setImageResource(R.mipmap.default_image);
        }
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

    @Override
    public void onError(String str) {
        showShortToast(str);
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
    public String getOrderNumber() {
        return listBean.getOrder_num();
    }

    @Override
    public void onSuccess(CouponCodeBean data) {
        if (null != data) {
            couponCodeBeans.addAll(data.getList());
            mAdapter.notifyDataSetChanged();
        }
    }
}

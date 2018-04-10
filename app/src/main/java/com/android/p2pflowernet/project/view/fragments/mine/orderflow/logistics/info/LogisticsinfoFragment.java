package com.android.p2pflowernet.project.view.fragments.mine.orderflow.logistics.info;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.GridViewAddImgesAdpter;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.ExpresListBean;
import com.android.p2pflowernet.project.entity.OrderDetailItemBean;
import com.android.p2pflowernet.project.entity.RefundBean;
import com.android.p2pflowernet.project.event.ExpressEvent;
import com.android.p2pflowernet.project.event.RefundEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.MyGridView;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.ActionSheet;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.refund_cargio.ExpressageActivity;
import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.bean.ImageItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;


/**
 * @描述: 完善物流信息
 * @创建人：zhangpeisen
 * @创建时间：2017/12/22 下午3:36
 * @修改人：zhangpeisen
 * @修改时间：2017/12/22 下午3:36
 * @修改备注：
 * @throws
 */
public class LogisticsinfoFragment extends KFragment<LogisticsView, LogisticsPrenter>
        implements NormalTopBar.normalTopClickListener, ActionSheet.ActionSheetListener, LogisticsView {

    @BindView(R.id.normal_top)
    // 标题 view
            NormalTopBar normalTop;
    @BindView(R.id.tv_adress)
    // 地址
            TextView tvAdress;
    @BindView(R.id.ll_location)
    LinearLayout llLocation;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.ll_photos)
    LinearLayout llPhotos;
    @BindView(R.id.hs_photos)
    HorizontalScrollView hsPhotos;
    @BindView(R.id.tv_storename)
    TextView tvStorename;
    @BindView(R.id.tv_merchandise_name)
    TextView tvMerchandiseName;
    @BindView(R.id.tv_colorattr)
    TextView tvColorattr;
    @BindView(R.id.hebdomad_tv)
    TextView hebdomadTv;
    @BindView(R.id.tv_expressage)
    // 物流公司
            TextView tvExpressage;
    @BindView(R.id.iv_expressage)
    // 物流图标
            ImageView ivExpressage;
    @BindView(R.id.et_yundan)
    //  运单号
            CustomEditText etYundan;
    @BindView(R.id.add_img_gridview)
    // 上传图片
            MyGridView addImgGridview;
    @BindView(R.id.btn_commit)
    // 上传按钮
            Button btnCommit;
    private File file;
    private List<File> fileData;
    private GridViewAddImgesAdpter mAdapter;
    // 加载进度条
    private ShapeLoadingDialog shapeLoadingDialog;
    // 来自订单列表
    RefundBean.ListsBean mRefundLists;
    // 来自订单详情
    OrderDetailItemBean mRefundDetail;
    @BindView(R.id.tv_merchandise_price)
    TextView tvMerchandisePrice;

    public static LogisticsinfoFragment newIntence(RefundBean.ListsBean listsBean, OrderDetailItemBean orderDetailItemBean) {

        Bundle bundle = new Bundle();
        LogisticsinfoFragment logisticsinfoFragment = new LogisticsinfoFragment();
        bundle.putSerializable("refundlists", listsBean);
        bundle.putSerializable("refundgooddetail", orderDetailItemBean);
        logisticsinfoFragment.setArguments(bundle);
        return logisticsinfoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRefundLists = (RefundBean.ListsBean) getArguments().getSerializable("refundlists");
        mRefundDetail = (OrderDetailItemBean) getArguments().getSerializable("refundgooddetail");
    }

    @Override
    public LogisticsPrenter createPresenter() {

        return new LogisticsPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_applyfor_refund_agio;
    }

    @Override
    public void initData() {
        fileData = new ArrayList<>();
        mAdapter = new GridViewAddImgesAdpter(fileData, getActivity());
        addImgGridview.setAdapter(mAdapter);
        mAdapter.setMaxImages(3);
        addImgGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //每次添加之前先清理
                fileData.clear();
                // 添加图片
                getActivity().setTheme(R.style.ActionSheetStyleiOS7);
                showActionSheet(addImgGridview.getId());
            }
        });
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setTitleText("申请退货");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        normalTop.setRightImageId(R.drawable.icon_shop_car);
        normalTop.getRightImage().setVisibility(View.VISIBLE);
        normalTop.setTopClickListener(this);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        Utils.setStatusBar(getActivity(), 0, false);
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();
        //初始化数据
        initData();
        mPresenter.manufacaddressinfo();
        if (mRefundLists != null) {
            // 商品名称
            tvMerchandiseName.setText(mRefundLists.getGoods_name());
            //  厂商名称
            tvStorename.setText(TextUtils.isEmpty(mRefundLists.getManufac_name()) ? "" : mRefundLists.getManufac_name());
            // 商品规格
            tvColorattr.setText(TextUtils.isEmpty(mRefundLists.getGoods_spec()) ? "" : mRefundLists.getGoods_spec());
            hebdomadTv.setText("七天退货");
            List<String> img_path = mRefundLists.getImg_path();
            if (img_path != null && img_path.size() > 1) {
                llTitle.setVisibility(View.GONE);//是否显示标题
            } else {
                llTitle.setVisibility(View.VISIBLE);
            }
            llPhotos.removeAllViews();
            for (int i = 0; i < img_path.size(); i++) {
                //使用inflate获取phtoview布局里面的myGallery视窗
                View viewitem = LayoutInflater.from(getContext()).inflate(R.layout.item_order_detail_item_photos, llPhotos, false);
                ImageView img = (ImageView) viewitem.findViewById(R.id.imageview_1);
                GlideImageLoader glideImageLoader = new GlideImageLoader();
                if (img_path.get(i) != null) {
                    glideImageLoader.displayImage(getContext(), ApiUrlConstant.API_IMG_URL + img_path.get(i), img);
                } else {
                    glideImageLoader.displayImage(getContext(), R.mipmap.default_image, img);
                }
                llPhotos.addView(viewitem);  //把添加过资源后的view视图重新添加到myGallery中
            }
            tvMerchandisePrice.setVisibility(View.GONE);
        } else {
            if (mRefundDetail == null) {
                return;
            }
            //  厂商名称
            tvStorename.setText(TextUtils.isEmpty(mRefundDetail.getManufac_name()) ? "" : mRefundDetail.getManufac_name());
            if (mRefundDetail.getLists().isEmpty() || mRefundDetail.getLists() == null) {
                return;
            }
            List<OrderDetailItemBean.ListsBean> listsBeanList = mRefundDetail.getLists();
            for (OrderDetailItemBean.ListsBean orderDetail : listsBeanList) {
                // 商品名称
                tvMerchandiseName.setText(orderDetail.getGoods_name());
                // 商品规格
                tvColorattr.setText(TextUtils.isEmpty(orderDetail.getGoods_spec()) ? "" : orderDetail.getGoods_spec());
                hebdomadTv.setText("七天退货");
                String img_path = orderDetail.getFile_path();
                View viewitem = LayoutInflater.from(getContext()).inflate(R.layout.item_order_detail_item_photos, llPhotos, false);
                ImageView img = (ImageView) viewitem.findViewById(R.id.imageview_1);
                GlideImageLoader glideImageLoader = new GlideImageLoader();
                if (img_path != null) {
                    glideImageLoader.displayImage(getContext(), ApiUrlConstant.API_IMG_URL + img_path, img);
                } else {
                    glideImageLoader.displayImage(getContext(), R.mipmap.default_image, img);
                }
                llTitle.setVisibility(View.VISIBLE);
                tvMerchandisePrice.setText(String.format(
                        getContext().getResources().getString(R.string.str_merchandise_price),
                        orderDetail.getGoods_num() == 0 ? 0 : orderDetail.getGoods_num()));
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ExpressEvent event) {
        ExpresListBean.ListsBean express = event.getExpress();
        String express_name = express.getExpress_name();
        tvExpressage.setText(express_name == null ? "" : express_name);
    }


    @Override
    public void onLeftClick(View view) {

        removeFragment();
    }

    @Override
    public void onRightClick(View view) {

        showShortToast("购物车");
    }

    @Override
    public void onTitleClick(View view) {

    }

    /**
     * 显示更换头像
     *
     * @param viewid
     */
    public void showActionSheet(int viewid) {

        ActionSheet.createBuilder(getActivity(), getFragmentManager(), viewid)
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles("选择图片")
                .setCancelableOnTouchOutside(true).setListener(this).show();
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

    }

    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index, int viewId) {

        switch (index + 1) {
            case 1:
                // 选择图片
                RxPicker.of().single(false).limit(1, 3).start(getActivity()).subscribe(new Consumer<List<ImageItem>>() {

                    @Override
                    public void accept(@NonNull List<ImageItem> imageItems) throws Exception {

                        for (ImageItem imageItem : imageItems) {

                            file = new File(imageItem.getPath());
                            fileData.add(file);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public String getManufacId() {
        return mRefundLists == null ? "" : mRefundLists.getManufac_id();
    }

    @Override
    public String getRefundid() {
        return mRefundLists == null ? "" : mRefundLists.getId();
    }

    @Override
    public String getWaybillnum() {
        return etYundan.getText().toString().trim();
    }

    @Override
    public String getExpressname() {
        return tvExpressage.getText().toString().trim();
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


    @OnClick({R.id.tv_expressage, R.id.btn_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_expressage://选择快递公司
                Intent intent = new Intent(getActivity(), ExpressageActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_commit://提交按钮
                mPresenter.perfectExpress();
                break;
        }
    }

    @Override
    public void onError(String errorMsg) {
        showShortToast(errorMsg);

    }

    @Override
    public void onSuccess(String message) {
        showShortToast(message);
        EventBus.getDefault().post(new RefundEvent());
        removeFragment();
    }
}

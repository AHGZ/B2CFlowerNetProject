package com.android.p2pflowernet.project.view.fragments.mine.orderflow.refund;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.GridViewAddImgesAdpter;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.android.p2pflowernet.project.entity.OrderDetailItemBean;
import com.android.p2pflowernet.project.entity.OrderListBean;
import com.android.p2pflowernet.project.event.OrderDeatailRefreshEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.MyGridView;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.ActionSheet;
import com.android.p2pflowernet.project.view.customview.actionsheet.OptionPicker;
import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.bean.ImageItem;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.functions.Consumer;

import static com.android.p2pflowernet.project.utils.ConvertUtils.fmtMoney;

/**
 * @描述:
 * @创建人：zhangpeisen
 * @创建时间：2017/12/16 下午2:38
 * @修改人：zhangpeisen
 * @修改时间：2017/12/16 下午2:38
 * @修改备注：
 * @throws
 */
public class ApplyforRefundFragment extends KFragment<IApplyforRefundView,
        IApplyforRefundPrenter> implements NormalTopBar.normalTopClickListener, IApplyforRefundView, ActionSheet.ActionSheetListener {


    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.tv_merchandise_price)
    TextView tvMerchandisePrice;
    @BindView(R.id.tv_no_received)
    TextView tvNoReceived;
    @BindView(R.id.tv_received)
    TextView tvReceived;
    @BindView(R.id.tv_cause)
    TextView tvCause;
    @BindView(R.id.iv_cause)
    ImageView ivCause;
    @BindView(R.id.tv_money)
    EditText tvMoney;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.tv_add_img)
    TextView tvAddImg;
    @BindView(R.id.ed_cause)
    // 退款说明
            CustomEditText Edcause;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    // 订单编号
    String orderNum = "";
    // 是否到货
    String isReturn = "";
    @BindView(R.id.id_editor_detail_count)
    TextView idEditorDetailCount;
    // 订单商品表id
    private String mOgid;
    @BindView(R.id.add_img_gridview)
    MyGridView addImgGridview;
    private boolean islMaxCount;
    private File file;
    private List<File> fileData;
    private GridViewAddImgesAdpter mAdapter;
    // 加载进度条
    private ShapeLoadingDialog shapeLoadingDialog;
    private String impath;
    // 来自订单列表
    OrderListBean.ListsBean mOrderlist;
    // 来自订单详情
    OrderDetailItemBean mOrderGoodDetail;
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

    public static ApplyforRefundFragment newIntence(String ogid, String orderNum,
                                                    OrderListBean.ListsBean orderlists, OrderDetailItemBean orderDetailItemBean) {
        Bundle bundle = new Bundle();
        ApplyforRefundFragment applyforRefundFragment = new ApplyforRefundFragment();
        bundle.putString("ogid", ogid);
        bundle.putString("ordernum", orderNum);
        bundle.putSerializable("orderlists", orderlists);
        bundle.putSerializable("ordergooddetail", orderDetailItemBean);
        applyforRefundFragment.setArguments(bundle);
        return applyforRefundFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOgid = getArguments().getString("ogid");
        orderNum = getArguments().getString("ordernum");
        mOrderlist = (OrderListBean.ListsBean) getArguments().getSerializable("orderlists");
        mOrderGoodDetail = (OrderDetailItemBean) getArguments().getSerializable("ordergooddetail");
    }

    @Override
    public IApplyforRefundPrenter createPresenter() {

        return new IApplyforRefundPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_applyfor_refund;
    }

    @Override
    public void initData() {

        fileData = new ArrayList<>();
        mAdapter = new GridViewAddImgesAdpter(fileData, getActivity());
        mAdapter.setMaxImages(9);
        addImgGridview.setAdapter(mAdapter);
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setTitleText("申请退款");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        normalTop.setRightTextColor(Color.WHITE);
        Utils.setStatusBar(getActivity(), 0, false);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        UIUtils.setTouchDelegate(tvMoney, 100);

        normalTop.setTopClickListener(this);
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        //初始化数据
        initData();

        addImgGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position <= fileData.size()) {
                    // 添加图片
                    getActivity().setTheme(R.style.ActionSheetStyleiOS7);
                    showActionSheet(position);
                }
            }
        });

        if (mOrderlist != null) {

            // 商品名称
            tvMerchandiseName.setText(mOrderlist.getGoods_name());
            //  厂商名称
            tvStorename.setText(TextUtils.isEmpty(mOrderlist.getManufac_name()) ? "" : mOrderlist.getManufac_name());
            // 商品规格
            tvColorattr.setText(TextUtils.isEmpty(mOrderlist.getSpec_name()) ? "" : mOrderlist.getSpec_name());
            //退款金额
            tvMoney.setText(mOrderlist.getPay_amount());

            hebdomadTv.setText("七天退货");

            List<String> img_path = mOrderlist.getImg_path();

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
            tvMerchandisePrice.setText(String.format(
                    getContext().getResources().getString(R.string.str_merchandise_price),
                    TextUtils.isEmpty(mOrderlist.getGoods_total_num()) ? "" : mOrderlist.getGoods_total_num(),
                    TextUtils.isEmpty(mOrderlist.getRebate_amount()) ? "" : mOrderlist.getRebate_amount()
                    , fmtMoney(TextUtils.isEmpty(mOrderlist.getPay_amount()) ? "" : mOrderlist.getPay_amount())));
        } else {

            if (mOrderGoodDetail == null) {
                return;
            }

            //  厂商名称
            tvStorename.setText(TextUtils.isEmpty(mOrderGoodDetail.getManufac_name()) ? "" : mOrderGoodDetail.getManufac_name());
            if (mOrderGoodDetail.getLists().isEmpty() || mOrderGoodDetail.getLists() == null) {
                return;
            }

            List<OrderDetailItemBean.ListsBean> listsBeanList = mOrderGoodDetail.getLists();

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


    /**
     * 初始化原因弹窗
     */
    private void initCause() {

        final String[] options = new String[4];
        options[0] = "操作有误";
        options[1] = "其他渠道价格更低";
        options[2] = "不想买了";
        options[3] = "其他原因";
        OptionPicker optionPicker = new OptionPicker(getActivity(), options);
        optionPicker.setTitleText("");
        optionPicker.setSubmitTextColor(Color.parseColor("#FF2E00"));
        optionPicker.show();
        optionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(String option, int selectedIndex) {

                tvCause.setText(options[selectedIndex]);
            }
        });
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
     * 设置editetxt的字数输入限制
     *
     * @param editable
     */
    @OnTextChanged(value = R.id.ed_cause, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void editTextDetailChange(Editable editable) {
        int detailLength = editable.length();
        idEditorDetailCount.setText(detailLength + "/100字");
        if (detailLength == 99) {
            islMaxCount = true;
        }
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
                RxPicker.of().single(false).limit(1, 9).start(getActivity()).subscribe(new Consumer<List<ImageItem>>() {

                    @Override
                    public void accept(@NonNull List<ImageItem> imageItems) throws Exception {

                        for (ImageItem imageItem : imageItems) {
                            file = new File(imageItem.getPath());
                            fileData.add(file);
                            mAdapter.notifyDataSetChanged();
                            mPresenter.uploadImg();
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public String getOgid() {
        return TextUtils.isEmpty(mOgid) ? "" : mOgid;
    }

    @Override
    public String getOrderNum() {
        return TextUtils.isEmpty(orderNum) ? "" : orderNum;
    }

    @Override
    public String getIsreturn() {
        return TextUtils.isEmpty(isReturn) ? "" : isReturn;
    }

    @Override
    public String getRefundmoney() {

        return tvMoney.getText().toString().trim();
    }

    @Override
    public String getReason() {
        return tvCause.getText().toString().trim();
    }

    @Override
    public String getExplain() {
        return Edcause.getText().toString().trim();
    }

    @Override
    public void setUploadImgSuccess(ImgDataBean data) {

        StringBuilder stringBuilder = new StringBuilder();
        if (data.getFile_path().size() == 1) {
            impath = data.getFile_path().get(0);
        } else {
            for (String imgPath : data.getFile_path()) {
                stringBuilder.append(imgPath).append(",");
            }
            impath = stringBuilder.toString().substring(0, stringBuilder.toString().lastIndexOf(","));
        }
    }

    @Override
    public String getImgpath() {

        return TextUtils.isEmpty(impath) ? "" : impath;
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

    @Override
    public void onError(String errorMsg) {
        showShortToast(errorMsg);
    }

    @Override
    public void onSuccess(String message) {

        showShortToast(message);
        EventBus.getDefault().post(new OrderDeatailRefreshEvent());
        removeFragment();
    }

    @Override
    public String getType() {
        return "3";
    }

    @Override
    public List<File> getfileList() {

        return fileData == null ? null : fileData;
    }

    @OnClick({R.id.tv_no_received, R.id.tv_received, R.id.tv_cause, R.id.iv_cause, R.id.btn_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_no_received://未收到

                addImgGridview.setVisibility(View.GONE);
                tvNoReceived.setBackgroundResource(R.drawable.shap_apply_for_refund_checked);
                tvNoReceived.setTextColor(Color.parseColor("#FF2E00"));
                tvReceived.setBackgroundResource(R.drawable.shap_apply_for_refund);
                tvReceived.setTextColor(Color.parseColor("#9E9E9E"));
                isReturn = "0";
                tvAddImg.setVisibility(View.GONE);

                break;
            case R.id.tv_received://收到

                addImgGridview.setVisibility(View.VISIBLE);
                tvNoReceived.setBackgroundResource(R.drawable.shap_apply_for_refund);
                tvNoReceived.setTextColor(Color.parseColor("#9E9E9E"));
                tvReceived.setBackgroundResource(R.drawable.shap_apply_for_refund_checked);
                tvReceived.setTextColor(Color.parseColor("#FF2E00"));
                isReturn = "1";
                tvAddImg.setVisibility(View.VISIBLE);

                break;
            case R.id.tv_cause://选择原因
                initCause();
                break;

            case R.id.btn_commit://提交
                if (tvMoney.getText().toString().trim().equals("") || tvMoney.getText().toString().toString().equals(".")) {
                    showShortToast("输入金额不能为空，且不能只输入字符");
                    return;
                } else if (Float.parseFloat(tvMoney.getText().toString().trim()) > Float.parseFloat(mOrderlist.getPay_amount())) {
                    showShortToast("输入金额不能大于订单总金额");
                    return;
                }
                mPresenter.addrefundrecord();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (fileData != null) {
            fileData.clear();
        }
    }
}

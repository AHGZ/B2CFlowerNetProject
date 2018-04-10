package com.android.p2pflowernet.project.o2omain.fragment.mine.take_out.to_evaluate;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.GridViewAddImgesAdpter;
import com.android.p2pflowernet.project.adapter.TakeOutToEvaluateListAdapter;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.EvaluateGoodsBean;
import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.android.p2pflowernet.project.entity.TakeOutToEvaluateGoodsBean;
import com.android.p2pflowernet.project.event.RefreshOrderListEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.MyGridView;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.RatingBarView;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.ActionSheet;
import com.bumptech.glide.Glide;
import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.bean.ImageItem;
import com.ghnor.flora.Flora;
import com.ghnor.flora.callback.Callback;
import com.ghnor.flora.spec.calculation.Calculation;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * Created by zhangkun on 2018/1/20.
 */

public class TakeOutToEvaluateFragment extends KFragment<ITakeOutToEvaluateView,ITakeOutToEvaluatePrenter>
        implements NormalTopBar.normalTopClickListener,ActionSheet.ActionSheetListener,ITakeOutToEvaluateView,TakeOutToEvaluateListAdapter.OnGetGoodsBeanListener{

    @BindView(R.id.normal_top)
    NormalTopBar topBar;
    @BindView(R.id.fragment_takeout_toEvaluate_gridView)
    MyGridView gridView;
    @BindView(R.id.fragment_takeout_toEvaluate_listView)
    RecyclerView listView;
    @BindView(R.id.fragment_takeout_toEvaluate_img_kIcon)
    ImageView img_kIcon;//快递icon
    @BindView(R.id.fragment_takeout_toEvaluate_tv_kName)
    TextView tv_kName;//快递名称
    @BindView(R.id.fragment_takeout_toEvaluate_tv_date)
    TextView tv_date;//送达日期
    @BindView(R.id.fragment_takeout_toEvaluate_rbv_kuaiDi)
    RatingBarView rbv_kuaidi;//快递评星
    @BindView(R.id.fragment_takeout_toEvaluate_img_dIcon)
    ImageView img_dIcon;//店铺icon
    @BindView(R.id.fragment_takeout_toEvaluate_tv_dName)
    TextView tv_dName;//店铺名称
    @BindView(R.id.fragment_takeout_toEvaluate_rbv_dianPu)
    RatingBarView rbv_dianPu;//店铺评星
    @BindView(R.id.fragment_takeout_toEvaluate_editText_dianPu)
    CustomEditText et_shop;//店铺评价内容
    @BindView(R.id.fragment_takeout_toEvaluate_tv_dianPu)
    TextView tv_shop;//店铺评价内容限制
    @BindView(R.id.fragment_takeout_toEvaluate_checkBox)
    CheckBox mCheckBox;//是否匿名
    @BindView(R.id.fragment_takeout_toEvaluate_tv_release)
    TextView tv_release;//发布
    @BindView(R.id.fragment_takeout_toEvaluate_linearLayout)
    LinearLayout mLinearLayout;//配送显示或者隐藏

    private int distribType;//配送类型 1-平台配送 2-商家配送 3-到店自提
    private int distribId;//配送平台ID
    private int distribScore = 0;//配送评星
    private int shopScore = 0;//店铺评星
    private int isAnonymous;//是否匿名  0-不匿名 1-匿名
    private String orderNumber;//订单编号
    private String str = "";//上传图片地址
    private String goodsContent;//商品评价内容

    private GridViewAddImgesAdpter addImgesAdpter;
    private TakeOutToEvaluateListAdapter adapter;
    private List<File> fileData = new ArrayList<>();
    private List<TakeOutToEvaluateGoodsBean.GoodsListBean> listData = new ArrayList<>();
    private List<EvaluateGoodsBean> outToEvaluateGoodsBean;

    private ShapeLoadingDialog shapeLoadingDialog;
    private int number = 0;
    private static final int ADD_COMPLETE = 0;//添加图片完成
    private static final int COMPRESS_COMPLETE = 1;//压缩完成

    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ADD_COMPLETE: {

                    break;
                }
                case COMPRESS_COMPLETE: {

                    File file = (File) msg.obj;
                    fileData.add(file);

                    if (addImgesAdpter != null) {
                    }
                    if (fileData.size() == number) {
                        mPresenter.uploadImg();
                        addImgesAdpter.notifyDataSetChanged();
                    }

                    break;
                }
                default:
                    break;
            }
        }
    };

    public static KFragment newIntence(String order_num){
        TakeOutToEvaluateFragment fragment = new TakeOutToEvaluateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("order_num",order_num);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        orderNumber = bundle.getString("order_num");
    }

    @Override
    public ITakeOutToEvaluatePrenter createPresenter() {

        return new ITakeOutToEvaluatePrenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_takeout_toevaluate;
    }

    @Override
    public void initData() {

        //设置适配器
        addImgesAdpter = new GridViewAddImgesAdpter(fileData, getActivity());
        gridView.setAdapter(addImgesAdpter);
        addImgesAdpter.setMaxImages(9);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // 添加图片
                getActivity().setTheme(R.style.ActionSheetStyleiOS7);
                showActionSheet(gridView.getId());
            }
        });

        getGoodsData();
    }

    private void getGoodsData() {
        //获取页面数据
        mPresenter.getEvaluateData();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        //初始化沉浸式
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.red));
        topBar.setTitleText("评价");
        topBar.setBackgroundColor(getResources().getColor(R.color.red));
        topBar.setTitleTextColor(getResources().getColor(R.color.white));
        topBar.setLeftImageId(R.mipmap.icon_back_white);
        topBar.setTopClickListener(this);

        //初始化加载窗口
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(2000)
                .build();

        initData();
        initEditTextChange();
        initRatingBarView();
    }

    private void initRatingBarView() {
        rbv_kuaidi.setOnRatingListener(new RatingBarView.OnRatingListener() {
            @Override
            public void onRating(Object bindObject, int RatingScore) {
                distribScore = RatingScore;
            }
        });
        rbv_dianPu.setOnRatingListener(new RatingBarView.OnRatingListener() {
            @Override
            public void onRating(Object bindObject, int RatingScore) {
                shopScore = RatingScore;
            }
        });
    }

    private void initEditTextChange() {
        et_shop.addTextChangedListener(new TextWatcher() {
            private CharSequence cs;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                cs = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_shop.setText(cs.length() + "/30字");
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
     * 添加图片
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

                        if (!fileData.isEmpty() && fileData.size() > 0) {
                            fileData.clear();
                        }
                        number = imageItems.size();
                        for (ImageItem imageItem : imageItems) {
                            compressByUs(imageItem.getPath());
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * @throws
     * @描述: 压缩图片
     * @创建人：zhangpeisen
     * @创建时间：2017/12/29 下午3:14
     * @修改人：zhangpeisen
     * @修改时间：2017/12/29 下午3:14
     * @修改备注：
     */
    private void compressByUs(String path) {
//        number = 0;
        Flora.with(this).calculation(new Calculation() {
            @Override
            public int calculateInSampleSize(int srcWidth, int srcHeight) {
                return super.calculateInSampleSize(srcWidth, srcHeight);
            }

            @Override
            public int calculateQuality(int srcWidth, int srcHeight, int targetWidth, int targetHeight) {
                return super.calculateQuality(srcWidth, srcHeight, targetWidth, targetHeight);
            }
        }).load(path).compress(new Callback<String>() {
            @Override
            public void callback(String compressResults) {
                File file = new File(compressResults);
                Message msg = new Message();
                msg.what = COMPRESS_COMPLETE;
                msg.obj = file;
//                number += 1;
                mHandler.sendMessage(msg);
            }
        });
    }

    @Override
    public void onError(String string) {
        showShortToast(string);
    }

    @Override
    public void onSuccess(String s) {
        showShortToast("评论成功");
        EventBus.getDefault().post(new RefreshOrderListEvent("RefreshOrder"));
        removeFragment();
    }

    @Override
    public String getType() {
        return "3";
    }

    @Override
    public List<File> getfileList() {
        return fileData;
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
    public void setUploadImgSuccess(ImgDataBean imgDataBean) {
        if (imgDataBean != null) {

            str = "";
            List<String> file_path = imgDataBean.getFile_path();
            for (int i = 0; i < file_path.size(); i++) {
                String imgpath = file_path.get(i);
                str += imgpath + ",";
            }
            str = str.toString().substring(0, str.toString().lastIndexOf(","));
        }
    }

    @Override
    public String orderNumber() {
        return orderNumber;
    }

    @Override
    public int distribType() {
        return distribType;
    }

    @Override
    public int distribId() {
        return distribId;
    }

    @Override
    public int distribScore() {
        return distribScore;
    }

    @Override
    public int merchScore() {
        return shopScore;
    }

    @Override
    public String shopContent() {
        return et_shop.getText().toString().trim();
    }

    @Override
    public String shopImgs() {
        return str;
    }

    @Override
    public String goodsContent() {

        return goodsContent;
    }

    @Override
    public int isAnonymous() {
        if (mCheckBox.isChecked()){
            isAnonymous = 1;
        }else{
            isAnonymous = 0;
        }
        return isAnonymous;
    }

    @Override
    public void onSuccessData(TakeOutToEvaluateGoodsBean data) {
        if (data != null) {
            outToEvaluateGoodsBean = new ArrayList<>();
            distribType = data.getDistrib_mode();
            distribId = data.getDistrict_id();
            if (distribType == 3) {
                mLinearLayout.setVisibility(View.GONE);
            }else{
                mLinearLayout.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(ApiUrlConstant.API_IMG_URL + data.getDistrib_logo())
                        .placeholder(R.mipmap.default_header)
                        .error(R.mipmap.default_header)
                        .into(img_kIcon);
                tv_kName.setText(data.getDistrib_name());
                tv_date.setText(data.getConfirm_time());
            }
            //设置店铺信息
            tv_dName.setText(data.getMerch_name());
            Glide.with(this)
                    .load(ApiUrlConstant.API_IMG_URL + data.getDistrib_logo())
                    .placeholder(R.mipmap.default_header)
                    .error(R.mipmap.default_header)
                    .into(img_dIcon);

            List<TakeOutToEvaluateGoodsBean.GoodsListBean> goodsListBeans = data.getGoods_list();
            for (int i = 0; i < goodsListBeans.size(); i++) {
                EvaluateGoodsBean goodsBean = new EvaluateGoodsBean();
                goodsBean.setId(goodsListBeans.get(i).getGoods_id());
                goodsBean.setSpec(goodsListBeans.get(i).getGoods_spec());
                goodsBean.setScore(5);
                goodsBean.setContent("");
                outToEvaluateGoodsBean.add(goodsBean);
            }

            listData.addAll(goodsListBeans);
            //listView 设置适配器
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            //设置为垂直排列格式
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            listView.setLayoutManager(linearLayoutManager);
            adapter = new TakeOutToEvaluateListAdapter(getContext(),listData,outToEvaluateGoodsBean);
            adapter.setOnGetGoodsBeanListener(this);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            goodsContent = new Gson().toJson(listData);
        }
    }

    @Override
    public void getGoodsBean(List<EvaluateGoodsBean> goodsBeans) {
        outToEvaluateGoodsBean.clear();
        outToEvaluateGoodsBean.addAll(goodsBeans);
        goodsContent = new Gson().toJson(outToEvaluateGoodsBean);
    }

    @OnClick(R.id.fragment_takeout_toEvaluate_tv_release)
    public void onClick(){//发布
        mPresenter.releseEvaluate();
    }
}

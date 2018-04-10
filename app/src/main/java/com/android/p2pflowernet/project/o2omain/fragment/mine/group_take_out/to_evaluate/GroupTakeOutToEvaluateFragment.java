package com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out.to_evaluate;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.GridViewAddImgesAdpter;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.android.p2pflowernet.project.entity.TakeOutOrderGroupBean;
import com.android.p2pflowernet.project.event.EvaluateGroupEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.DateUtils;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.MyGridView;
import com.android.p2pflowernet.project.view.customview.RatingBarView;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.ActionSheet;
import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.bean.ImageItem;
import com.ghnor.flora.Flora;
import com.ghnor.flora.callback.Callback;
import com.ghnor.flora.spec.calculation.Calculation;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnTextChanged;
import io.reactivex.functions.Consumer;

/**
 * Created by zhangkun on 2018/1/22.
 */

public class GroupTakeOutToEvaluateFragment extends KFragment<IGroupTakeOutToEvaluateView,IGroupTakeOutToEvaluatePrenter>implements
        View.OnClickListener,IGroupTakeOutToEvaluateView,ActionSheet.ActionSheetListener{

    @BindView(R.id.fragment_groupBuying_toEvaluate_img_back)
    ImageView img_back;//返回
    @BindView(R.id.fragment_groupBuying_toEvaluate_img)
    ImageView mImageView;//团购商品图片
    @BindView(R.id.fragment_groupBuying_toEvaluate_tv_title)
    TextView tv_title;//团购商品名
    @BindView(R.id.fragment_groupBuying_toEvaluate_tv_price)
    TextView tv_price;//价格
    @BindView(R.id.fragment_groupBuying_toEvaluate_tv_huafanPrice)
    TextView tv_hPrice;//花返价格
    @BindView(R.id.fragment_groupBuying_toEvaluate_tv_number)
    TextView tv_number;//数量
    @BindView(R.id.fragment_groupBuying_toEvaluate_tv_vld)
    TextView tv_vld;//有效期
    @BindView(R.id.fragment_groupBuying_toEvaluate_ratingBarView)
    RatingBarView mRatingBarView;//评价星数
    @BindView(R.id.fragment_groupBuying_toEvaluate_editText)
    CustomEditText mEditText;//评价内容
    @BindView(R.id.fragment_groupBuying_toEvaluate_tv_place)
    TextView tv_place;//评价字数限制
    @BindView(R.id.fragment_groupBuying_toEvaluate_gridView)
    MyGridView mGridView;//评价图片GridView
    @BindView(R.id.fragment_groupBuying_toEvaluate_checkBox)
    CheckBox mCheckBox;//是否匿名
    @BindView(R.id.fragment_groupBuying_toEvaluate_tv_release)
    TextView tv_release;//发布评价

    private static final int ADD_COMPLETE = 0;//添加图片完成
    private static final int COMPRESS_COMPLETE = 1;//压缩完成
    private TakeOutOrderGroupBean.ListBean goodBean;

    private int score = 0;//评价数
    private int merchId;//商家ID
    private int groupId;//团购ID
    private int orderNumber;//订单号
    private int isAnonymous;//是否匿名 0-不匿名 1-匿名
    private String imgs;//评价图片
    private String iconImage;//商品图片地址
    private String title;//商品名称
    private String price;//价格
    private String hPrice;//花返价格
    private String number;//商品数量
    private String vld;//有效期

    private List<File> fileData = new ArrayList<>();
    private GridViewAddImgesAdpter mAdapter;
    private int imgNumber;//添加图片数量
    private ShapeLoadingDialog shapeLoadingDialog;

    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ADD_COMPLETE: {

                    break;
                }
                case COMPRESS_COMPLETE: {

                    File file = (File) msg.obj;
                    fileData.add(file);

                    if (fileData.size() == imgNumber) {
                        mAdapter.notifyDataSetChanged();
                        mPresenter.uploadImg();
                    }

                    break;
                }
                default:
                    break;
            }
        }
    };


    @Override
    public IGroupTakeOutToEvaluatePrenter createPresenter() {
        return new IGroupTakeOutToEvaluatePrenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_groupbuying_toevaluate;
    }

    public static KFragment newIntence(TakeOutOrderGroupBean.ListBean listBean) {

        GroupTakeOutToEvaluateFragment fragment = new GroupTakeOutToEvaluateFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("good",listBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        goodBean = (TakeOutOrderGroupBean.ListBean) bundle.getSerializable("good");
        merchId = goodBean.getMerch_id();
        groupId = goodBean.getGroup_id();
        orderNumber = Integer.parseInt(goodBean.getOrder_num());
        iconImage = goodBean.getFile_path();
        title = goodBean.getTitle();
        price = goodBean.getPrice();
        hPrice = goodBean.getRebate();
        number = goodBean.getNum();
        String startDate = goodBean.getStarttime();
        String endDate = goodBean.getEndtime();
        String starDate = DateUtils.timedate(startDate);
        String edDate = DateUtils.timedate(endDate);
        vld = starDate + "—" + edDate;
    }

    @Override
    public void initData() {
        //设置适配器
        mAdapter = new GridViewAddImgesAdpter(fileData, getActivity());
        mGridView.setAdapter(mAdapter);
        mAdapter.setMaxImages(9);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // 添加图片
                getActivity().setTheme(R.style.ActionSheetStyleiOS7);
                showActionSheet(mGridView.getId());
            }
        });
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        //初始化沉浸式
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(),R.color.red));
        //设置默认星值
        mRatingBarView.setOnRatingListener(new RatingBarView.OnRatingListener() {
            @Override
            public void onRating(Object bindObject, int RatingScore) {
                score = RatingScore;
            }
        });

        img_back.setOnClickListener(this);
        tv_release.setOnClickListener(this);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(2000)
                .build();

        initData();
        initView();
    }

    private void initView() {
        //更新页面数据
        if (null != goodBean) {
            new GlideImageLoader().displayImage(getActivity(), ApiUrlConstant.API_IMG_URL + iconImage,mImageView);
            tv_title.setText(TextUtils.isEmpty(title) ? "" :title);
            tv_price.setText(TextUtils.isEmpty(price) ? "" :price);
            tv_hPrice.setText(TextUtils.isEmpty(hPrice) ? "" :hPrice);
            tv_number.setText(TextUtils.isEmpty(number) ? "" :number);
            tv_vld.setText(TextUtils.isEmpty(vld) ? "" :vld);
        }else{
            tv_title.setText("");
            tv_price.setText("");
            tv_hPrice.setText("");
            tv_number.setText("");
            tv_vld.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_groupBuying_toEvaluate_img_back://返回
                removeFragment();
            break;

            case R.id.fragment_groupBuying_toEvaluate_tv_release://发布
                mPresenter.releseEvaluate();
            break;
        }
    }

    /**
     * 设置editetxt的字数输入限制
     *
     * @param editable
     */
    @OnTextChanged(value = R.id.fragment_groupBuying_toEvaluate_editText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void editTextDetailChange(Editable editable) {
        int detailLength = editable.length();
        tv_place.setText(detailLength + "/30字");
    }

    @Override
    public int merchId() {//商家ID
        return merchId;
    }

    @Override
    public int groupId() {//团购ID
        return groupId;
    }

    @Override
    public int orderNumber() {//订单号
        return orderNumber;
    }

    @Override
    public int score() {//评分
        return score;
    }

    @Override
    public int isAnonymous() {//是否匿名评价
        if (mCheckBox.isChecked()) {
            isAnonymous = 1;
        }else{
            isAnonymous = 0;
        }
        return isAnonymous;
    }

    @Override
    public String toEvaluateContext() {//评价内容
        return mEditText.getText().toString().trim();
    }

    @Override
    public String imgs() {//店铺评价配图
        return imgs;
    }

    @Override
    public void hideDialog() {
        if (shapeLoadingDialog.isShowing()) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void showDialog() {
        shapeLoadingDialog.show();
    }

    @Override
    public void onError(String s) {
        showShortToast(s);
    }

    @Override
    public void onSuccess(String s) {
        showShortToast("评论成功");
        EventBus.getDefault().post(new EvaluateGroupEvent("refresh"));
        removeFragment();
    }

    @Override
    public void setUploadImgSuccess(ImgDataBean data) {
        if (data != null) {

            imgs = "";
            List<String> file_path = data.getFile_path();
            for (int i = 0; i < file_path.size(); i++) {
                String imgpath = file_path.get(i);
                imgs += imgpath + ",";
            }
            imgs = imgs.toString().substring(0, imgs.toString().lastIndexOf(","));
        }
    }

    @Override
    public String getType() {
        return "3";
    }

    @Override
    public List<File> getfileList() {
        return fileData;
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
                        imgNumber = imageItems.size();
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
                mHandler.sendMessage(msg);
            }
        });
    }
}

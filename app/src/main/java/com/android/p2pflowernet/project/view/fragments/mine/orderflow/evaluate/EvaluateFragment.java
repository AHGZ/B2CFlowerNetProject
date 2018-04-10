package com.android.p2pflowernet.project.view.fragments.mine.orderflow.evaluate;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.GridViewAddImgesAdpter;
import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.MyGridView;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.RatingBarView;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.ActionSheet;
import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.bean.ImageItem;
import com.ghnor.flora.Flora;
import com.ghnor.flora.callback.Callback;
import com.ghnor.flora.spec.calculation.Calculation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.functions.Consumer;

/**
 * Created by caishen on 2017/12/5.
 * by--评价
 */

public class EvaluateFragment extends KFragment<IEvaluateView, IEvaluatePrenter>
        implements NormalTopBar.normalTopClickListener, ActionSheet.ActionSheetListener, IEvaluateView {


    private static final int ADD_COMPLETE = 0;//添加图片完成
    private static final int COMPRESS_COMPLETE = 1;//压缩完成
    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.iv_brand)
    ImageView ivBrand;
    @BindView(R.id.tv_brand)
    TextView tvBrand;
    @BindView(R.id.tt1)
    TextView tt1;
    @BindView(R.id.ms_ratingbar)
    RatingBarView msRatingbar;
    @BindView(R.id.tt2)
    TextView tt2;
    @BindView(R.id.wl_ratingbar)
    RatingBarView wlRatingbar;
    @BindView(R.id.tt3)
    TextView tt3;
    @BindView(R.id.fw_ratingbar)
    RatingBarView fwRatingbar;
    @BindView(R.id.et_evalute)
    CustomEditText etEvalute;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.add_img_gridview)
    MyGridView addImgGridview;
    @BindView(R.id.cb_nm)
    CheckBox cbNm;
    @BindView(R.id.tv_send)
    TextView tvSend;
    private List<File> fileData = new ArrayList<>();
    private GridViewAddImgesAdpter mAdapter;
    private boolean islMaxCount = false;
    private String order_id = "";
    private ShapeLoadingDialog shapeLoadingDialog;
    private String str = "";//上传图片地址

    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ADD_COMPLETE: {

                    break;
                }
                case COMPRESS_COMPLETE: {

                    File file = (File) msg.obj;
                    fileData.add(file);

                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }

                    if (fileData.size() == number) {
                        mPresenter.uploadImg();
                    }

                    break;
                }
                default:
                    break;
            }
        }
    };
    private int number = 0;
    private int msStart = 5;
    private int wlStart = 5;
    private int fwStart = 5;
    private String mesuaName = "";//店铺名称

    @Override
    public IEvaluatePrenter createPresenter() {

        return new IEvaluatePrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_evalute;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        order_id = arguments.getString("order_id");
        mesuaName = arguments.getString("mesuaName");
    }

    @Override
    public void initData() {

        //设置适配器
        mAdapter = new GridViewAddImgesAdpter(fileData, getActivity());
        addImgGridview.setAdapter(mAdapter);
        mAdapter.setMaxImages(9);
        addImgGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // 添加图片
                getActivity().setTheme(R.style.ActionSheetStyleiOS7);
                showActionSheet(addImgGridview.getId());
            }
        });
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(2000)
                .build();

        Utils.setStatusBar(getActivity(), 0, false);
        normalTop.setTitleText("发表评价");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);

        //设置店铺名称
        tvBrand.setText(mesuaName);

        normalTop.setTopClickListener(this);
        msRatingbar.setStar(5, false);
        wlRatingbar.setStar(5, false);
        fwRatingbar.setStar(5, false);

        //设置默认星值
        msRatingbar.setOnRatingListener(new RatingBarView.OnRatingListener() {
            @Override
            public void onRating(Object bindObject, int RatingScore) {
                msStart = RatingScore;
            }
        });
        wlRatingbar.setOnRatingListener(new RatingBarView.OnRatingListener() {
            @Override
            public void onRating(Object bindObject, int RatingScore) {
                wlStart = RatingScore;
            }
        });

        fwRatingbar.setOnRatingListener(new RatingBarView.OnRatingListener() {
            @Override
            public void onRating(Object bindObject, int RatingScore) {

                fwStart = RatingScore;
            }
        });

        initData();
    }

    public static KFragment newIntence(String order_id, String mesuaName) {

        EvaluateFragment evaluateFragment = new EvaluateFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("order_id", order_id);
        bundle.putSerializable("mesuaName", mesuaName);
        evaluateFragment.setArguments(bundle);
        return evaluateFragment;
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

    @OnClick(R.id.tv_send)
    public void onClick() {//发布

        mPresenter.addGoodsEval();
    }

    /**
     * 设置editetxt的字数输入限制
     *
     * @param editable
     */
    @OnTextChanged(value = R.id.et_evalute, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void editTextDetailChange(Editable editable) {
        int detailLength = editable.length();
        tvNum.setText(detailLength + "/100字");
        if (detailLength == 99) {
            islMaxCount = true;
        }
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
        number = 0;
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

                fileData.add(file);
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }

                if (fileData.size() == number) {
                    mPresenter.uploadImg();
                }
            }
        });
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
    public List<File> getfileList() {

        return fileData;
    }

    @Override
    public String getType() {
        return "3";
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void setUploadImgSuccess(ImgDataBean data) {

        if (data != null) {

            str = "";
            List<String> file_path = data.getFile_path();
            for (int i = 0; i < file_path.size(); i++) {
                String imgpath = file_path.get(i);
                str += imgpath + ",";
            }
            str = str.toString().substring(0, str.toString().lastIndexOf(","));
        }
    }

    @Override
    public String getImgPath() {
        return str;
    }

    @Override
    public String getContent() {
        return etEvalute.getText().toString().trim();
    }

    @Override
    public int getServiceAttitudeScore() {//宝贝描述
        return msStart;
    }

    @Override
    public int getLogisticsServiceScore() {//物流服务
        return wlStart;
    }

    @Override
    public int getGoodsDescScore() {//服务态度评价
        return fwStart;
    }

    @Override
    public String getIsAnonymous() {

        String string = "";
        if (cbNm.isChecked()) {
            string = "1";
        } else {
            string = "0";
        }
        return string;
    }

    @Override
    public String getOrderId() {
        return order_id;
    }

    @Override
    public void onSuccess(String message) {

        showShortToast("评价成功！");
        removeFragment();
    }
}

package com.android.p2pflowernet.project.o2omain.fragment.refund;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.GridViewAddImgesAdpter;
import com.android.p2pflowernet.project.adapter.RefundAdapter;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.android.p2pflowernet.project.entity.RefundShopBean;
import com.android.p2pflowernet.project.entity.TakeOutBean;
import com.android.p2pflowernet.project.event.RefreshOrderListEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.MyGridView;
import com.android.p2pflowernet.project.view.customview.MyListView;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.ActionSheet;
import com.android.p2pflowernet.project.view.customview.actionsheet.OptionPicker;
import com.bumptech.glide.Glide;
import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.bean.ImageItem;
import com.ghnor.flora.Flora;
import com.ghnor.flora.callback.Callback;
import com.ghnor.flora.spec.calculation.Calculation;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/1/24.
 */

public class RefundFragment extends KFragment<IRefundView,IRefundPrenter> implements NormalTopBar.normalTopClickListener,IRefundView,ActionSheet.ActionSheetListener {
    @BindView(R.id.normal_top)
    NormalTopBar topBar;
    @BindView(R.id.fragment_refund_tv_mode)
    TextView tv_mode;//退款方式
    @BindView(R.id.fragment_refund_img_icon)
    ImageView img_icon;//店铺logo
    @BindView(R.id.fragment_refund_tv_shop)
    TextView tv_shop;//店名
    @BindView(R.id.fragment_refund_listView)
    MyListView mListView;//商品列表
    @BindView(R.id.fragment_refund_linearLayout)
    LinearLayout mLinearLayout;//展开/收起
    @BindView(R.id.fragment_refund_tv_open)
    TextView tv_open;//展开or收起
    @BindView(R.id.fragment_refund_tv_money)
    TextView tv_money;//金额
    @BindView(R.id.fragment_refund_rl)
    RelativeLayout mRelativeLayout;//选择退款原因
    @BindView(R.id.fragment_refund_tv_why)
    TextView tv_why;//显示退款原因
    @BindView(R.id.fragment_refund_tv_whyHint)
    TextView tv_whyHint;//退款原因提示语
    @BindView(R.id.fragment_refund_editText)
    CustomEditText mEditText;//评价语
    @BindView(R.id.fragment_refund_tv_limit)
    TextView tv_linit;//评价字数限制
    @BindView(R.id.fragment_refund_gridView)
    MyGridView mGridView;//图片
    @BindView(R.id.fragment_refund_tv_submit)
    TextView tv_submit;//提交

    private List<RefundShopBean> listShopBean;
    private List<TakeOutBean.ListBean.GoodsBean> shopBeans;
    private RefundAdapter adapter;
    private ShapeLoadingDialog shapeLoadingDialog;

    private int number = 0;
    private static final int ADD_COMPLETE = 0;//添加图片完成
    private static final int COMPRESS_COMPLETE = 1;//压缩完成
    private List<File> fileData = new ArrayList<>();
    private GridViewAddImgesAdpter addImgesAdpter;
    private List<TakeOutBean.ListBean.GoodsBean> goodsBeans = new ArrayList<>();

    private String str = "";//上传图片地址
    private String orderNumber;//订单编号
    private String amount;//退款金额
    private String manger_name;//店铺名称
    private String manger_img;//店铺图片
    private String pay_channel;//支付方式 0-未支付 1-微信 2-支付宝 3-银联 4-账户余额


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

    public static KFragment newIntence(String pay_channel,String order_num, String pay_amount,String manger_name,String manger_img, List<TakeOutBean.ListBean.GoodsBean> beans){
        RefundFragment fragment = new RefundFragment();
        Bundle bundle = new Bundle();
        bundle.putString("pay_channel",pay_channel);
        bundle.putString("manger_name",manger_name);
        bundle.putString("manger_img",manger_img);
        bundle.putString("order_num",order_num);
        bundle.putString("pay_amount",pay_amount);
        bundle.putSerializable("goods", (Serializable) beans);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pay_channel = getArguments().getString("pay_channel");
        orderNumber = getArguments().getString("order_num");
        amount = getArguments().getString("pay_amount");
        manger_name = getArguments().getString("manger_name");
        manger_img = getArguments().getString("manger_img");
        goodsBeans.addAll((List<TakeOutBean.ListBean.GoodsBean>) getArguments().getSerializable("goods"));
    }

    @Override
    public IRefundPrenter createPresenter() {
        return new IRefundPrenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_refund;
    }

    @Override
    public void initData() {
        //设置适配器
        addImgesAdpter = new GridViewAddImgesAdpter(fileData, getActivity());
        mGridView.setAdapter(addImgesAdpter);
        addImgesAdpter.setMaxImages(9);
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
        Eyes.setStatusBarColor(getActivity(),getResources().getColor(R.color.red));

        //设置店铺名称和图片
        tv_shop.setText(manger_name);
        Glide.with(getActivity())
                .load(ApiUrlConstant.API_IMG_URL + manger_img)
                .placeholder(R.mipmap.default_header)
                .error(R.mipmap.default_header)
                .into(img_icon);

        //设置退款金额
        tv_money.setText(amount);

        //设置支付方式 0-未支付 1-微信 2-支付宝 3-银联 4-账户余额
        if ("1".equals(pay_channel)) {
            tv_mode.setText( "微信（退回支付的账号）");
        }else if("2".equals(pay_channel)){
            tv_mode.setText("支付宝（退回支付的账号）");
        }else if("3".equals(pay_channel)){
            tv_mode.setText("银联（退回支付的账号）");
        }else if("4".equals(pay_channel)){
            tv_mode.setText("账户余额（退回支付的账号）");
        }else{
            tv_mode.setText("（退回支付的账号）");
        }

        //设置topBar
        topBar.setLeftImageId(R.mipmap.icon_back_white);
        topBar.setTitleText("申请退款");
        topBar.setTitleTextColor(getResources().getColor(R.color.white));
        topBar.setBackgroundColor(getResources().getColor(R.color.red));
        topBar.setTopClickListener(this);

        //初始化加载窗口
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(2000)
                .build();

        //设置商品数据
        shopBeans = new ArrayList<>();
        if (goodsBeans.size() > 2){
            shopBeans.add(goodsBeans.get(0));
            shopBeans.add(goodsBeans.get(1));
            mLinearLayout.setVisibility(View.VISIBLE);
            tv_open.setText("展开全部");
        }else{
            mLinearLayout.setVisibility(View.GONE);
            shopBeans.addAll(goodsBeans);
        }
        adapter = new RefundAdapter(shopBeans,getActivity());
        mListView.setAdapter(adapter);

        initData();
        initEditText();
    }

    private void initEditText() {
        mEditText.addTextChangedListener(new TextWatcher() {
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
                tv_linit.setText(cs.length() + "/30个字");
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

    @OnClick({R.id.fragment_refund_linearLayout,R.id.fragment_refund_rl,R.id.fragment_refund_tv_submit})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.fragment_refund_linearLayout://展开/收起
                openOrStop();
                break;
            case R.id.fragment_refund_rl://选择退款原因
                initCause();
                break;
            case R.id.fragment_refund_tv_submit://提交
                mPresenter.submitRefund();
                break;
        }
    }

    //展开or收起
    private void openOrStop() {
        if (adapter.getCount() == 2) {
            shopBeans.clear();
            shopBeans.addAll(goodsBeans);
            adapter.notifyDataSetChanged();
            mLinearLayout.setVisibility(View.VISIBLE);
            tv_open.setText("收起部分");
        }else{
            shopBeans.clear();
            shopBeans.add(goodsBeans.get(0));
            shopBeans.add(goodsBeans.get(1));
            adapter.notifyDataSetChanged();
            mLinearLayout.setVisibility(View.VISIBLE);
            tv_open.setText("展开全部");
        }
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
        if (shapeLoadingDialog.isShowing()) {
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
    public String getType() {
        return "3";
    }

    @Override
    public List<File> getfileList() {
        return fileData;
    }

    @Override
    public String getOrderNumber() {
        return orderNumber;
    }

    @Override
    public String getReason() {
        return tv_why.getText().toString().trim();
    }

    @Override
    public String getExplain() {
        return mEditText.getText().toString().trim();
    }

    @Override
    public String getImgPath() {
        return str;
    }

    @Override
    public String getAmount() {
        return tv_money.getText().toString().trim();
    }

    @Override
    public void onSuccess(String s) {
        showShortToast("提交成功");
        EventBus.getDefault().post(new RefreshOrderListEvent("Refresh"));
        removeFragment();
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

                tv_why.setText(options[selectedIndex]);
                tv_whyHint.setVisibility(View.GONE);
            }
        });
    }
}

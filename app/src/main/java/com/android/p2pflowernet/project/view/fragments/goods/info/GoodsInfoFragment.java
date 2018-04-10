package com.android.p2pflowernet.project.view.fragments.goods.info;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.CompareAdapter;
import com.android.p2pflowernet.project.adapter.GoodsEveluateAdapter;
import com.android.p2pflowernet.project.adapter.GsAttrAdapter;
import com.android.p2pflowernet.project.adapter.NetworkImageHolderView;
import com.android.p2pflowernet.project.callback.OnSelectedListener;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.ChangeGsAttrBean;
import com.android.p2pflowernet.project.entity.EveluateBean;
import com.android.p2pflowernet.project.entity.GoodsAttrBean;
import com.android.p2pflowernet.project.entity.GoodsInfoBean;
import com.android.p2pflowernet.project.entity.GuaranteeBean;
import com.android.p2pflowernet.project.entity.ListsBean;
import com.android.p2pflowernet.project.entity.OrderDetailBean;
import com.android.p2pflowernet.project.entity.ProductParamBean;
import com.android.p2pflowernet.project.entity.ShareGoodsBean;
import com.android.p2pflowernet.project.entity.ShopCarBean;
import com.android.p2pflowernet.project.entity.SpecCompareBean;
import com.android.p2pflowernet.project.event.CartEvent;
import com.android.p2pflowernet.project.event.ShopCarRefreshEvent;
import com.android.p2pflowernet.project.event.SildeEvent;
import com.android.p2pflowernet.project.event.TranslationEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.utils.ToastUtils;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.view.customview.CustomDialog;
import com.android.p2pflowernet.project.view.customview.CustomExpandableListView;
import com.android.p2pflowernet.project.view.customview.ItemWebView;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.SlideDetailsLayout;
import com.android.p2pflowernet.project.view.fragments.affirm.AffirmIndentActivity;
import com.android.p2pflowernet.project.view.fragments.goods.info.compare.CompareListActivity;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.ApplyForActivity;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebSettings;
import com.just.agentweb.AgentWebUIController;
import com.just.agentweb.ChromeClientCallbackManager;
import com.just.agentweb.WebDefaultSettingsManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;

import static android.content.Context.CLIPBOARD_SERVICE;
import static com.android.p2pflowernet.project.R.id.custom_dialog_close;
import static com.android.p2pflowernet.project.R.id.noreason_ly;


/**
 * @描述:item页ViewPager里的商品信息
 * @创建人：zhangpeisen
 * @创建时间：2017/10/18 下午1:57
 * @修改人：zhangpeisen
 * @修改时间：2017/10/18 下午1:57
 * @修改备注：
 * @throws
 */
public class GoodsInfoFragment extends KFragment<IGoodsInfoView, IGoodsInfoPresenter>
        implements SlideDetailsLayout.OnSlideDetailsListener, IGoodsInfoView {
    @BindView(R.id.sv_switch)
    //自定义视图组控件
            SlideDetailsLayout sv_switch;
    @BindView(R.id.sv_goods_info)
    //自定义滑动控件
            ScrollView sv_goods_info;
    @BindView(R.id.fab_up_slide)
    //一键置顶控件
            FloatingActionButton fab_up_slide;
    @BindView(R.id.vp_item_goods_img)
    // 商品图片轮播控件
            ConvenientBanner vp_item_goods_img;
    // 属性动画布局
    @BindView(R.id.first_view)
    LinearLayout firstView;

    @BindView(R.id.ll_activity)
    LinearLayout ll_activity;
    @BindView(R.id.ll_pull_up)
    LinearLayout ll_pull_up;
    @BindView(R.id.share_ly)
    LinearLayout share_ly;
    @BindView(R.id.tv_new_price)
    TextView tv_new_price;
    @BindView(R.id.tv_old_price)
    TextView tv_old_price;

    @BindView(R.id.tv_comment_count)
    TextView tv_comment_count;
    @BindView(R.id.tv_good_comment)
    TextView tv_good_comment;
    @BindView(R.id.ll_current_goods)
    //选择商品属性布局
            LinearLayout ll_current_goods;
    @BindView(R.id.productparam_ly)
    //产品参数
            LinearLayout productparam_ly;
    @BindView(R.id.title_selectercurrent_goods)
    //选择商品属性布局
            TextView title_selectercurrent_goods;
    @BindView(R.id.tv_current_goods)
    //选择商品属性value布局
            TextView tv_current_goods;
    @BindView(noreason_ly)
    LinearLayout noreasonLy;
    @BindView(R.id.goods_title)
    // 商品标题
            TextView goodsTitle;
    @BindView(R.id.getmoney)
    // 花返值
            TextView getmoney;
    @BindView(R.id.kuaidi_tv)
    // 快递费
            TextView kuaidi_tv;
    @BindView(R.id.ysales_tv)
    // 月销量
            TextView ysales_tv;
    @BindView(R.id.dizhi_tv)
    // 厂家地址
            TextView dizhi_tv;
    @BindView(R.id.zt_tv)
    // 七天无理由退货
            TextView zt_tv;
    // 库存
    TextView dialog_goods_nmb;

    @BindView(R.id.hf_allamount)
    // 花返总利润
            TextView hf_allamount;
    @BindView(R.id.ordinarymember_manfanamount)
    // 普通会员满8000返
            TextView ordinarymember_manfanamount;
    @BindView(R.id.applypartner_tv)
    // 合伙人申请
            TextView applypartner_tv;
    @BindView(R.id.partner_amount)
    // 合伙人返利
            TextView partner_amount;
    @BindView(R.id.ordinarymember_amount)
    // 普通会员返利
            TextView ordinarymember_amount;
    @BindView(R.id.applyagency_tv)
    // 代理人申请
            TextView applyagency_tv;
    @BindView(R.id.agency_amount)
    // 代理人返利
            TextView agency_amount;
    @BindView(R.id.referee_amount)
    // 推荐人返利
            TextView referee_amount;
    @BindView(R.id.title_old_price)
    TextView titleOldPrice;
    @BindView(R.id.tv_empty_evaluate)
    TextView tvEmptyEvaluate;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.tv_all_evaluate)
    TextView tvAllEvaluate;
    @BindView(R.id.ll_empty_comment)
    LinearLayout llEmptyComment;
    @BindView(R.id.wv_detail)
    ItemWebView wvDetail;
    @BindView(R.id.ll_webview)
    LinearLayout llWebView;
    @BindView(R.id.iv_shop_name)
    ImageView ivShopName;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.tv_allgood_num)
    TextView tvAllgoodNum;
    @BindView(R.id.tv_new_good_num)
    TextView tvNewGoodNum;
    @BindView(R.id.detail)
    TextView detail;
    @BindView(R.id.ev_detail)
    TextView evDetail;
    @BindView(R.id.sela)
    TextView sela;
    @BindView(R.id.ev_sela)
    TextView evSela;
    @BindView(R.id.wuliu)
    TextView wuliu;
    @BindView(R.id.ev_miaoshu)
    TextView evMiaoshu;
    @BindView(R.id.ev_miaoshufen)
    ImageView evMiaoshuFen;
    @BindView(R.id.ev_maijiafen)
    ImageView evMaijiafen;
    @BindView(R.id.ev_wuliufen)
    ImageView evWuliufen;
    @BindView(R.id.jiayipeishi)
    ImageView jiaYipeishi;
    @BindView(R.id.ll_compare)
    LinearLayout llCompare;//比一比
    @BindView(R.id.ex_listView)
    CustomExpandableListView expandableListView;//比一比列表数据
    @BindView(R.id.rl_more)
    RelativeLayout rlMore;//比一比查看更多
    private CustomDialog dialog;
    private TextView tv_item_minus_comm_detail, tv_item_number_comm_detail,
            tv_item_add_comm_detail, goods_type, dialog_goods_price;
    private ImageView dialog_img;
    private int goods_nmb = 1;
    private int fHeight;
    private int sHeight;
    private PopupWindow popupWindow;
    private LinearLayout lin5;
    private LinearLayout goods_info;
    private ImageView shoppingCartImageView;//商品详情主页的购物车视图
    private LinearLayout parentLayout;
    private TextView tvCount;
    private RelativeLayout dialog_root;
    private String goodId;
    private ShapeLoadingDialog shapeLoadingDialog;
    // 区分视图点击
    private int viewId = -1;

    private String opt_id1 = "-1";
    private String opt_id2 = "-1";
    private String opt_id3 = "-1";
    private String sGsNameOne = "";
    private String sGsNameTwo = "";
    private String sGsNameThree = "";
    String selectGsAttrsOne = "";
    String selectGsAttrsTwo = "";
    String selectGsAttrsThree = "";
    private String spec_id = null;
    String goods_Id = "";
    private int tag = -1;
    private GoodsInfoBean.GoodsBean dataGoods;
    private GoodsInfoBean allGood;
    private AgentWeb mAgentWeb;
    private String specName = "";
    private CompareAdapter mAdapter;
    private List<GoodsInfoBean.CompareBean> compare;

    private ShopCarBean.ListBean.ShopBean mShopBean;
    private List<String> titleList = new ArrayList<>();
    private Map<String, GoodsAttrBean.ListsBean.AttrValueBean> hashMap = new HashMap<>();
    private GuaranteeBean data;

    public static GoodsInfoFragment newInstance(String goodId, ShopCarBean.ListBean.ShopBean shopBean) {
        Bundle args = new Bundle();
        args.putString("goods_Id", goodId);
        args.putSerializable("shopBean", shopBean);
        GoodsInfoFragment fragment = new GoodsInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        vp_item_goods_img.startTurning(4000);
        MobclickAgent.onPageStart("GoodInfoPage"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        vp_item_goods_img.stopTurning();
        MobclickAgent.onPageEnd("GoodInfoPage");
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goods_Id = getArguments().getString("goods_Id");
        mShopBean = (ShopCarBean.ListBean.ShopBean) getArguments().getSerializable("shopBean");
    }

    @Override
    public IGoodsInfoPresenter createPresenter() {

        return new IGoodsInfoPresenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_goods_info;
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(2000)
                .build();
        mPresenter.guarantee();
        //取消焦点
        expandableListView.setFocusable(false);

        //mShopBean 不为null时来自购物车，初始化商品规格
        if (mShopBean == null) {
            tv_current_goods.setVisibility(View.INVISIBLE);
            title_selectercurrent_goods.setText("请选择商品规格");
        } else {
            String goodSpec = mShopBean.getGoods_spec();
            String[] arrStr1 = goodSpec.split(" ");
            for (int i = 0; i < arrStr1.length; i++) {
                String[] arrStr2 = arrStr1[i].split(":");
                if (i == 0) {
                    selectGsAttrsOne = arrStr2[1];
                } else if (i == 1) {
                    selectGsAttrsTwo = arrStr2[1];
                } else if (i == 2) {
                    selectGsAttrsThree = arrStr2[1];
                }
            }
            tv_current_goods.setVisibility(View.VISIBLE);
            title_selectercurrent_goods.setVisibility(View.GONE);
            tv_current_goods.setText("已选:" + sbSplit());
            tv_current_goods.setTextColor(Color.parseColor("#FF2E00"));
        }

        initData();

        sv_goods_info.smoothScrollTo(0, 0);
    }

    @Override
    public void initData() {

        //获取商品详情数据
        mPresenter.goodsXq();

        //设置文字中间一条横线
        tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        fab_up_slide.hide();
        //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
        vp_item_goods_img.setPageIndicator(new int[]{R.drawable.xd_default, R.drawable.xd_selected});
        vp_item_goods_img.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

        sv_switch.setOnSlideDetailsListener(this);
    }


    @OnClick({R.id.ll_pull_up, R.id.fab_up_slide, R.id.ll_current_goods, R.id.share_ly, noreason_ly, R.id.tv_all_evaluate,
            R.id.productparam_ly, R.id.applypartner_tv, R.id.applyagency_tv, R.id.rl_more})
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.ll_pull_up:

                //上拉查看图文详情
                sv_switch.smoothOpen(true);

                break;
            case R.id.fab_up_slide:

                //点击滑动到顶部
                sv_goods_info.smoothScrollTo(0, 0);
                sv_switch.smoothClose(true);

                break;
            case R.id.ll_current_goods:

                //选择商品属性布局
                viewId = ll_current_goods.getId();
                mPresenter.goodsSpec();

                break;
            case R.id.productparam_ly:
                // 弹出产品参数
                mPresenter.goodsParam();
                break;
            case R.id.applypartner_tv:
                // 申请合伙人
                Intent intent = new Intent(getActivity(), ApplyForActivity.class);
                startActivity(intent);
                break;
            case R.id.applyagency_tv:
                // 申请代理人
                intent = new Intent(getActivity(), ApplyForActivity.class);
                startActivity(intent);
                break;
            case R.id.share_ly://分享

                mPresenter.getShareGoods();

                break;
            case noreason_ly://基础保障弹窗
                if (data != null) {

                    List<GuaranteeBean.ListsBean> lists = data.getLists();

                    if (lists != null && lists.size() > 0) {
                        initSafeguard(lists);
                        popupWindow.showAtLocation(noreasonLy, Gravity.BOTTOM, 0, 0);

                    } else {

                        showShortToast("暂无数据");
                    }
                }

                break;
            case R.id.tv_all_evaluate://查看全部评论
//                addFragment(new GoodsCommentFragment().newInstance());
                EventBus.getDefault().post(new TranslationEvent(2));

                break;
            case R.id.rl_more://比一比 查看更多

                intent = new Intent(getActivity(), CompareListActivity.class);
                intent.putExtra("spec_id", spec_id);
                startActivity(intent);

                break;
            default:
                break;
        }
    }

    //初始化基础保障的弹窗

    private void initSafeguard(List<GuaranteeBean.ListsBean> lists) {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.safeguard, null);
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        // 自适配长、框设置
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, height * 3 / 5);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow.update();
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(0.3f);
        popupWindow.setOnDismissListener(new poponDismissListener());

        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        ImageView iv_close = (ImageView) view.findViewById(R.id.iv_close);
        ListView my_listView = (ListView) view.findViewById(R.id.my_listView);
        Button btn_affirm = (Button) view.findViewById(R.id.btn_affirm);
        //设置数据以及点击事件
        UIUtils.setTouchDelegate(iv_close, 50);

        //设置确定的点击事件
        btn_affirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.dismiss();
            }
        });

        //设置关闭按钮的点击事件
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.dismiss();
            }
        });

        //设置保障列表的适配器
        SafeGuardAdapter mAdapter = new SafeGuardAdapter(getActivity(), lists);
        my_listView.setAdapter(mAdapter);

    }

    //初始化分享功能
    private void initControls(final ShareGoodsBean data) {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.sharepop, null);

        // 自适配长、框设置
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow.update();
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(1f);
        popupWindow.setOnDismissListener(new poponDismissListener());
        TextView esc = (TextView) view.findViewById(R.id.cancle);//取消
        esc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
            }
        });

        //朋友圈
        final LinearLayout lin1 = (LinearLayout) view.findViewById(R.id.share_wechat_circle);
        lin1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
                share1(data);
            }
        });

        //微信
        final LinearLayout lin2 = (LinearLayout) view.findViewById(R.id.share_wechat);
        lin2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();

                share2(data);
            }
        });

        //微博
        final LinearLayout lin3 = (LinearLayout) view.findViewById(R.id.share_sina);
        lin3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
                share3(data);
            }
        });

        //QQ好友
        final LinearLayout lin4 = (LinearLayout) view.findViewById(R.id.share_qq_frend);
        lin4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();

                share4(data);
            }
        });

        //QQ空间
        lin5 = (LinearLayout) view.findViewById(R.id.share_qq_space);
        lin5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                share5(data);
            }
        });

        //复制连接，拷贝网址
        final LinearLayout lin6 = (LinearLayout) view.findViewById(R.id.share_copy);
        lin6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
                popupWindow.dismiss();
                ClipboardManager myClipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                String text = dataGoods.getName();
                ClipData text1 = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(text1);
                showShortToast("已复制到剪切板");
            }
        });
    }

    //QQ空间分享
    private void share5(ShareGoodsBean data) {

        String share_url = data.getShare_url() == null ? "" : data.getShare_url();
        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(data.getTitle() == null ? "" : data.getTitle());//标题
        String goods_img = ApiUrlConstant.API_IMG_URL + data.getGoods_img();
        UMImage image = new UMImage(getActivity(), goods_img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(data.getIntro() == null ? "" : data.getIntro());//描述

        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.QZONE)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    //QQ好友分享
    private void share4(ShareGoodsBean data) {

        String share_url = data.getShare_url() == null ? "" : data.getShare_url();
        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(data.getTitle() == null ? "" : data.getTitle());//标题
        String goods_img = ApiUrlConstant.API_IMG_URL + data.getGoods_img();
        UMImage image = new UMImage(getActivity(), goods_img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(data.getIntro() == null ? "" : data.getIntro());//描述

        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.QQ)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    //微信分享
    private void share2(ShareGoodsBean data) {

        String share_url = data.getShare_url() == null ? "" : data.getShare_url();
        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(dataGoods.getGoods_name());//标题
        String goods_img = ApiUrlConstant.API_IMG_URL + data.getGoods_img();
        UMImage image = new UMImage(getActivity(), goods_img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(data.getIntro() == null ? "" : data.getIntro());//描述

        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.WEIXIN)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    //微信朋友圈
    private void share1(ShareGoodsBean data) {

        String share_url = data.getShare_url() == null ? "" : data.getShare_url();
        ArrayList<String> imgurl = dataGoods.getImgurl();
        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(data.getTitle() == null ? "" : data.getTitle());//标题
        String goods_img = ApiUrlConstant.API_IMG_URL + data.getGoods_img();
        UMImage image = new UMImage(getActivity(), goods_img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(data.getIntro() == null ? "" : data.getIntro());//描
        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    //新浪微博
    private void share3(ShareGoodsBean data) {

        String share_url = data.getShare_url() == null ? "" : data.getShare_url();
        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(data.getTitle() == null ? "" : data.getTitle());//标题
        String goods_img = ApiUrlConstant.API_IMG_URL + data.getGoods_img();
        UMImage image = new UMImage(getActivity(), goods_img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(dataGoods.getIntro() == null ? "" : data.getIntro());//描
        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.SINA)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }


    //分享的回调
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            showShortToast("分享成功啦");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {

            showShortToast("分享失败啦");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            showShortToast("分享取消啦");
        }
    };


    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public String goodsId() {
        return goods_Id;
    }

    @Override
    public String sepcId() {

        return TextUtils.isEmpty(spec_id) ? "" : spec_id;
    }


    @Override
    public int getPage() {

        return 1;
    }


    @Override
    public void hideDialog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void successEveluate(EveluateBean data) {

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void successGsInfo(GoodsInfoBean data) {

        // 获取商品信息
        if (data == null) {
            return;
        }

        allGood = data;
        dataGoods = data.getGoods();
        if (dataGoods == null) {
            return;
        }

        goodId = dataGoods.getId();

        // 设置轮播图
        setLoopView(dataGoods.getImgurl());
        // 商品评价
        List<ListsBean> dataPingjia = data.getLists();

        //设置评价个数
        tv_comment_count.setText(dataGoods.getCount() == null ? "" : dataGoods.getCount());

        if (dataPingjia != null && dataPingjia.size() > 0) {

            recycleview.setVisibility(View.VISIBLE);
            tvEmptyEvaluate.setVisibility(View.GONE);
            tvAllEvaluate.setVisibility(View.VISIBLE);
            //设置评价的适配器
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
            recycleview.setLayoutManager(mLinearLayoutManager);
            GoodsEveluateAdapter goodsEveluateAdapter = new GoodsEveluateAdapter(getActivity(), dataPingjia);
            recycleview.setAdapter(goodsEveluateAdapter);

        } else {

            recycleview.setVisibility(View.GONE);
            tvEmptyEvaluate.setVisibility(View.VISIBLE);
            tvAllEvaluate.setVisibility(View.GONE);
        }

        //商品描述
        String manufac_logo = ApiUrlConstant.API_IMG_URL + dataGoods.getManufac_logo();
        new GlideImageLoader().displayImage(getActivity(), manufac_logo, ivShopName);
        tvShopName.setText(dataGoods.getName() == null ? "" : dataGoods.getName());//厂家名称
        tvAllgoodNum.setText(dataGoods.getAll() == null ? "" : dataGoods.getAll());//全部宝贝
        tvNewGoodNum.setText(dataGoods.getNewX() == null ? "" : dataGoods.getNewX());//新品数量
        evDetail.setText(dataGoods.getDesc() == null ? "" : dataGoods.getDesc());//宝贝描述
        evMiaoshu.setText(dataGoods.getLogistics() == null ? "" : dataGoods.getLogistics());//物流服务
        evSela.setText(dataGoods.getSeller() == null ? "" : dataGoods.getSeller());//卖家服务
        if (Float.parseFloat(dataGoods.getDesc()) >= 4) {//高
            evMiaoshuFen.setImageResource(R.drawable.gao);

        } else if (Float.parseFloat(dataGoods.getDesc()) >= 3 && Float.parseFloat(dataGoods.getDesc()) < 4.000) {//中
            evMiaoshuFen.setImageResource(R.drawable.zhong);
        } else if (Float.parseFloat(dataGoods.getDesc()) < 3) {//低
            evMiaoshuFen.setImageResource(R.drawable.di);
        }

        if (Float.parseFloat(dataGoods.getLogistics()) >= 4) {//高
            evWuliufen.setImageResource(R.drawable.gao);

        } else if (Float.parseFloat(dataGoods.getLogistics()) >= 3 && Float.parseFloat(dataGoods.getLogistics()) < 4.000) {//中
            evWuliufen.setImageResource(R.drawable.zhong);
        } else if (Float.parseFloat(dataGoods.getLogistics()) < 3) {//低
            evWuliufen.setImageResource(R.drawable.di);
        }

        if (Float.parseFloat(dataGoods.getSeller()) >= 4) {//高
            evMaijiafen.setImageResource(R.drawable.gao);

        } else if (Float.parseFloat(dataGoods.getSeller()) >= 3 && Float.parseFloat(dataGoods.getSeller()) < 4.000) {//中
            evMaijiafen.setImageResource(R.drawable.zhong);
        } else if (Float.parseFloat(dataGoods.getSeller()) < 3) {//低
            evMaijiafen.setImageResource(R.drawable.di);
        }


        // 商品名称
        goodsTitle.setText(dataGoods.getGoods_name());
        // 抢购价
        tv_new_price.setText(dataGoods.getSale_price());
        // 花返
        getmoney.setText(dataGoods.getHuafan());
        // 销售价
        tv_old_price.setText(dataGoods.getMarket_price());
        // 快递费
        kuaidi_tv.setText("快递:" + dataGoods.getKuaidi());
        // 月销量
        ysales_tv.setText("月销量:" + dataGoods.getYsales());
        // 厂家地址
        dizhi_tv.setText(dataGoods.getDizhi());
        // 七天无理由退货
        zt_tv.setText(dataGoods.getZt());
        // 总返润金额
        hf_allamount.setText(dataGoods.getHuafan());
        // 合伙人金额
        partner_amount.setText(dataGoods.getPartner());
        // 代理人返润
        agency_amount.setText(dataGoods.getAgent());
        // 推荐人返润
        referee_amount.setText(dataGoods.getTuijian());
        // 普通会员返润
        ordinarymember_amount.setText(dataGoods.getPutong());
        // 普通会员满8000返润
        ordinarymember_manfanamount.setText(dataGoods.getGaoji());
        // 是否合伙人 0-不是 1-是'
        if (dataGoods.getIs_partner().equals("1")) {
            applypartner_tv.setVisibility(View.GONE);
        } else {
            applypartner_tv.setVisibility(View.VISIBLE);
        }
        // 是否代理人 0-不是 1-是'
        if (dataGoods.getIs_agent().equals("1")) {

            applyagency_tv.setVisibility(View.GONE);

        } else {

            applyagency_tv.setVisibility(View.VISIBLE);
        }

        spec_id = dataGoods.getSpec_id();
        specName = dataGoods.getGoods_spec();

        compare = data.getCompare();

        if (compare != null && compare.size() > 0) {

            llCompare.setVisibility(View.VISIBLE);

            //设置比一比的适配器
            mAdapter = new CompareAdapter(getActivity(), compare, specName);
            expandableListView.setAdapter(mAdapter);
            expandableListView.setGroupIndicator(null); //设置属性 GroupIndicator 去掉向下箭头

            for (int i = 0; i < mAdapter.getGroupCount(); i++) {
                expandableListView.expandGroup(i); //关键步骤4:初始化，将ExpandableListView以展开的方式显示
            }

        } else {

            llCompare.setVisibility(View.GONE);
        }

        sv_goods_info.smoothScrollTo(0, 0);

//        expandableListView.setFastScrollEnabled(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CartEvent event) {

        // 点击购买或添加购物车
        shoppingCartImageView = event.getmShoppingCartImageView();
        parentLayout = event.getParentLayout();
        tvCount = event.getTvCount();
        viewId = -1;
        tag = event.getTag();
        mPresenter.goodsSpec();
    }


    @Override
    public void successGoodsSpec(GoodsAttrBean data) {
        // 获取商品属性
        if (data == null) {
            return;
        }
        switch (viewId) {
            case -1:
                OpenGoodAttrs(data, 0);
                break;
            case R.id.ll_current_goods:
                OpenGoodAttrs(data, 1);
                break;
        }
    }


    @Override
    public void onError(String message) {

        showShortToast(message);
    }

    /**
     * 基本保障
     *
     * @param data
     */
    @Override
    public void onSuccessGuarantee(GuaranteeBean data) {
        this.data = data;
        if (data != null) {

            final List<GuaranteeBean.ListsBean> lists = data.getLists();

            if (lists != null && lists.size() > 0) {
                if (lists.get(0).getId().equals("1")) {
                    jiaYipeishi.setVisibility(View.VISIBLE);
                }

            }
//            else {
//
//                showShortToast("暂无数据");
//            }
        }
    }

    @Override
    public String getSource() {

        return "1";//1是立刻购买2是购物车来的
    }

    /***
     * 确认订单的回调页面
     * @param data
     */
    @Override
    public void SuccessOrder(OrderDetailBean data) {

        if (data != null) {

            Intent intent = new Intent(getActivity(), AffirmIndentActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", data);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public void onAddShopCarSuccess(String message) {

        showShortToast("添加购物车成功");
        //友盟统计
        MobclickAgent.onEvent(getActivity(), "addShop");
        EventBus.getDefault().post(new ShopCarRefreshEvent());
    }

    @Override
    public String getSelect() {
        return "0";
    }

    @Override
    public String getGoodId() {
        return goodId;
    }

    @Override
    public void SuccessShare(ShareGoodsBean data) {

        if (data != null) {

            initControls(data);
            popupWindow.showAtLocation(share_ly, Gravity.BOTTOM, 0, 0);
        }
    }

    /**
     * 根据规格更改比价列表数据
     *
     * @param data
     */
    @Override
    public void Successcompare(SpecCompareBean data) {

        if (data != null) {

            List<GoodsInfoBean.CompareBean> compares = data.getCompare();

            if (compares != null && compares.size() > 0) {

                llCompare.setVisibility(View.VISIBLE);
                if (mAdapter != null) {

                    compare = compares;
                    mAdapter.notifyDataSetChanged();
                }

            } else {

                llCompare.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void productParam(ProductParamBean productParamBean) {
        if (productParamBean != null) {

            List<ProductParamBean.ListsBean> lists = productParamBean.getLists();
            if (lists != null && lists.size() > 0) {
//                initProductParam(lists);
                popupWindow.showAtLocation(productparam_ly, Gravity.BOTTOM, 0, 0);

            } else {

                showShortToast("暂无数据");
            }
        }
    }

    @Override
    public void showDialog() {

        shapeLoadingDialog.show();
    }

    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }
    }

    /**
     * 给商品轮播图设置图片路径
     */
    public void setLoopView(final ArrayList<String> imgUrls) {

        //初始化商品图片轮播
        vp_item_goods_img.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new NetworkImageHolderView();
            }
        }, imgUrls);

        //设置图片的点击事件
        vp_item_goods_img.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                ArrayList<String> strings = new ArrayList<>();
                for (int i = 0; i < imgUrls.size(); i++) {
                    String s = ApiUrlConstant.API_IMG_URL + imgUrls.get(i);
                    strings.add(s);
                }

                startActivity(BGAPhotoPreviewActivity.newIntent(getActivity(), null, strings, position));
            }
        });
    }


    @Override
    public void onStatucChanged(SlideDetailsLayout.Status status) {
        if (status == SlideDetailsLayout.Status.OPEN) {

            //当前为图文详情页
            fab_up_slide.show();
            EventBus.getDefault().post(new SildeEvent(0));

//            EventBus.getDefault().post(new TranslationEvent(1));
//            GoodsDetailWebFragment.newInstance(dataGoods.getXqurl() == null ? "" : dataGoods.getXqurl());

            mAgentWeb = AgentWeb.with(this)
                    .setAgentWebParent(llWebView, new LinearLayout.LayoutParams(-1, -1))
                    .useDefaultIndicator()// 使用默认进度条
                    .setReceivedTitleCallback(mCallback) //标题回调
                    .setAgentWebWebSettings(getSettings())//设置 AgentWebSettings。
                    .setWebViewClient(mWebViewClient)//WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效。
                    .setWebChromeClient(mWebChromeClient) //WebChromeClient
                    .setSecurityType(AgentWeb.SecurityType.strict) //注意这里开启 strict 模式 ， 设备低于 4.2 情况下回把注入的 Js 全部清空掉 ， 这里推荐使用 onJsPrompt 通信
                    .createAgentWeb()//
                    .ready()//
                    .go(dataGoods.getXqurl() == null ? "" : ApiUrlConstant.API_BASE_URL + dataGoods.getDescription());

        } else {

            //当前为商品详情页
            fab_up_slide.hide();
            EventBus.getDefault().post(new SildeEvent(1));
        }
    }


    public AgentWebSettings getSettings() {
        return WebDefaultSettingsManager.getInstance();
    }


    protected WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //  super.onProgressChanged(view, newProgress);
        }
    };
    protected WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return shouldOverrideUrlLoading(view, request.getUrl() + "");
        }


        //
        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, String url) {

            //intent:// scheme的处理 如果返回false ， 则交给 DefaultWebClient 处理 ， 默认会打开该Activity
            // ， 如果Activity不存在则跳到应用市场上去.  true 表示拦截
            //例如优酷视频播放 ，intent://play?...package=com.youku.phone;end;
            //优酷想唤起自己应用播放该视频 ， 下面拦截地址返回 true  则会在应用内 H5 播放 ，禁止优酷唤起播放该视频， 如果返回 false ， DefaultWebClient  会根据intent 协议处理 该地址 ， 首先匹配该应用存不存在 ，如果存在 ， 唤起该应用播放 ， 如果不存在 ， 则跳到应用市场下载该应用 .
            if (url.startsWith("intent://") && url.contains("com.youku.phone"))
                return true;
            /*else if (isAlipay(view, url))   //1.2.5开始不用调用该方法了 ，只要引入支付宝sdk即可 ， DefaultWebClient 默认会处理相应url调起支付宝
                return_ticket true;*/
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {


        }

        /*错误页回调该方法 ， 如果重写了该方法， 上面传入了布局将不会显示 ， 交由开发者实现，注意参数对齐。*/
        public void onMainFrameError(AgentWebUIController agentWebUIController, WebView view, int errorCode, String description, String failingUrl) {
            agentWebUIController.onMainFrameError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);

        }

    };

    protected ChromeClientCallbackManager.ReceivedTitleCallback mCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (!TextUtils.isEmpty(title))
                if (title.length() > 10)
                    title = title.substring(0, 10).concat("...");
        }
    };


//    /**
//     * 友盟分享或者授权的Activity中，添加：
//     *
//     * @param requestCode
//     * @param resultCode
//     * @param data
//     */
//    @Override
//    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
//        super.onFragmentResult(requestCode, resultCode, data);
//        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
//    }

    public void OpenGoodAttrs(GoodsAttrBean goodsAttrBean, int Tag) {

        dialog = new CustomDialog(getActivity(), R.style.Dialog);//设置dialog的样式
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.mystyle); // 添加动画
        dialog.show();
        WindowManager m = getActivity().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        //这句就是设置dialog横向满屏了。
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = (int) (d.getHeight() * 0.7);     //dialog屏幕占比
        window.setAttributes(lp);
        RecyclerView shoppingSelectView = (RecyclerView) dialog.findViewById(R.id.selectgsattr_view);
        LinearLayout dialog_confirm_ll = (LinearLayout) dialog.findViewById(R.id.dialog_confirm_ll);
        RelativeLayout custom_dialog_close = (RelativeLayout) dialog.findViewById(R.id.custom_dialog_close);
        dialog_img = (ImageView) dialog.findViewById(R.id.dialog_img);
        dialog_goods_price = (TextView) dialog.findViewById(R.id.dialog_goods_price);
        //库存件数
        dialog_goods_nmb = (TextView) dialog.findViewById(R.id.dialog_goods_nmb);
        // 添加购物车
        Button goodscart_btn = (Button) dialog.findViewById(R.id.goodscart_btn);
        // 立即购买
        Button btn_goodinfo_topay = (Button) dialog.findViewById(R.id.btn_goodinfo_topay);
        tv_item_minus_comm_detail = (TextView) dialog.findViewById(R.id.tv_item_minus_comm_detail);
        tv_item_number_comm_detail = (TextView) dialog.findViewById(R.id.tv_item_number_comm_detail);
        tv_item_add_comm_detail = (TextView) dialog.findViewById(R.id.tv_item_add_comm_detail);
        goods_type = (TextView) dialog.findViewById(R.id.goods_type);
        goods_info = (LinearLayout) dialog.findViewById(R.id.goods_info);
        dialog_root = (RelativeLayout) dialog.findViewById(R.id.dialog_root);
        dialog_goods_nmb.setText("库存" + goodsAttrBean.getStock() + "件");
        dialog_goods_price.setText("￥" + goodsAttrBean.getSale_price());
        String img_url = ApiUrlConstant.API_IMG_URL + goodsAttrBean.getImg();
        goods_type.setVisibility(View.VISIBLE);
        goods_type.setText("请选择商品规格");
        goods_type.setTextColor(Color.parseColor("#4B4B4B"));
        tv_item_number_comm_detail.setText(String.valueOf(goods_nmb));

        Glide.with(getActivity())
                .load(img_url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.no_image)
                .placeholder(R.mipmap.no_image)
                .into(dialog_img);
        shoppingSelectView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });
        hashMap.clear();
        titleList.clear();
        if (goodsAttrBean.getLists() != null) {
            List<GoodsAttrBean.ListsBean> list = goodsAttrBean.getLists();
            for (int i = 0; i < list.size(); i++) {
                titleList.add(i, list.get(i).getAttr_name());
            }
        }

        //mShopBean 不为空来自购物车,确定弹出框的默认选项
        if (mShopBean != null) {
            String goodSpec = mShopBean.getGoods_spec();
            List<GoodsAttrBean.ListsBean> list = goodsAttrBean.getLists();
            String[] arrStr1 = goodSpec.split(" ");
            for (int i = 0; i < arrStr1.length; i++) {
                String[] arrStr2 = arrStr1[i].split(":");
                String specName = arrStr2[0];
                String specValue = arrStr2[1];
                for (int j = 0; j < list.size(); j++) {
                    if (specName.equals(list.get(j).getAttr_name())) {
                        List<GoodsAttrBean.ListsBean.AttrValueBean> attrBeanList = list.get(j).getAttr_value();
                        for (int k = 0; k < attrBeanList.size(); k++) {
                            attrBeanList.get(k).setIs_default(0);
                            if (specValue.equals(attrBeanList.get(k).getOption_name())) {
                                attrBeanList.get(k).setIs_default(1);
                            }
                        }
                    }
                }
            }
        }

        shoppingSelectView.setLayoutManager(new LinearLayoutManager(getActivity()));
        GsAttrAdapter gsAttrAdapter = new GsAttrAdapter();
        gsAttrAdapter.attachRecyclerView(shoppingSelectView);
        gsAttrAdapter.setStockNum(Integer.parseInt(goodsAttrBean.getStock()));
        gsAttrAdapter.setList(goodsAttrBean.getLists());
        gsAttrAdapter.setOnSelectedListener(new OnSelectedListener() {
            @Override
            public void onConfirm(String title, GoodsAttrBean.ListsBean.AttrValueBean attrValueBean) {
                hashMap.put(title, attrValueBean);
                Iterator<String> it = hashMap.keySet().iterator();
                while (it.hasNext()) {
                    String titleName = it.next();
                    GoodsAttrBean.ListsBean.AttrValueBean bean = hashMap.get(titleName);
                    String attrId = bean.getId();
                    String optionNmae = bean.getOption_name();
                    int index = titleList.indexOf(titleName);
                    if (index == 0) {
                        sGsNameOne = titleName;
                        opt_id1 = attrId;
                        selectGsAttrsOne = optionNmae;
                    } else if (index == 1) {
                        sGsNameTwo = titleName;
                        opt_id2 = attrId;
                        selectGsAttrsTwo = optionNmae;
                    } else if (index == 2) {
                        sGsNameThree = titleName;
                        opt_id3 = attrId;
                        selectGsAttrsThree = optionNmae;
                    }
                }

                if (TextUtils.isEmpty(selectGsAttrsOne) && TextUtils.isEmpty(selectGsAttrsTwo) && TextUtils.isEmpty(selectGsAttrsThree)) {
                    title_selectercurrent_goods.setText("请选择商品规格");
                    tv_current_goods.setVisibility(View.INVISIBLE);

                } else {
                    tv_current_goods.setText("已选:" + sbSplit());
                    tv_current_goods.setTextColor(Color.parseColor("#FF2E00"));
                    title_selectercurrent_goods.setVisibility(View.GONE);
                    tv_current_goods.setVisibility(View.VISIBLE);
                }

                shopBeanReps();
                goods_type.setText(sbSplit());
                goods_type.setTextColor(Color.parseColor("#FF2E00"));
                if (!opt_id1.equals("-1") || !opt_id2.equals("-1") || !opt_id3.equals("-1")) {

                    //修改规格
                    mPresenter.goodsSpecInfo();
                }
            }
        });

        custom_dialog_close.setOnClickListener(new DialogClick());
        tv_item_minus_comm_detail.setOnClickListener(new DialogClick());
        tv_item_add_comm_detail.setOnClickListener(new DialogClick());
        if (Tag == 0) {

            dialog_confirm_ll.setVisibility(View.VISIBLE);
            goodscart_btn.setVisibility(View.GONE);
            btn_goodinfo_topay.setVisibility(View.GONE);

        } else {

            dialog_confirm_ll.setVisibility(View.GONE);
            goodscart_btn.setVisibility(View.VISIBLE);
            btn_goodinfo_topay.setVisibility(View.VISIBLE);
        }

        //点击确定按钮
        dialog_confirm_ll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 检验是否选择的商品属性值
                if (selectGsAttrsOne.equals("") && selectGsAttrsOne.equals("") || selectGsAttrsTwo.equals("") && selectGsAttrsTwo.equals("")
                        || selectGsAttrsThree.equals("") && selectGsAttrsThree.equals("")) {
                    title_selectercurrent_goods.setText("请选择商品规格");
                }

                if (tag == -1) {
                    return true;
                }
                if (tag == 0) {//添加购物车

                    mPresenter.addShopCars();

                    //友盟统计
                    MobclickAgent.onEvent(getActivity(), "addShop");

                } else if (tag == 1) {//立即购买

                    // 立即购买
                    mPresenter.orderSel();

                    //友盟统计
                    MobclickAgent.onEvent(getActivity(), "buyNow");
                }

                //根据规格更改比价列表数据
                mPresenter.compSpec();

                dialog.dismiss();
                return false;
            }
        });

        dialog_confirm_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 检验是否选择的商品属性值
                if (selectGsAttrsOne.equals("") && selectGsAttrsOne.equals("") ||
                        selectGsAttrsTwo.equals("") && selectGsAttrsTwo.equals("")
                        || selectGsAttrsThree.equals("") && selectGsAttrsThree.equals("")) {
                    title_selectercurrent_goods.setText("请选择商品规格");
                }

                if (tag == -1) {
                    showShortToast("点击");
                    return;
                }
                if (tag == 0) {//添加购物车
                    mPresenter.addShopCars();
                    //友盟统计
                    MobclickAgent.onEvent(getActivity(), "addShop");

                } else if (tag == 1) {//立即购买
                    // 立即购买
                    mPresenter.orderSel();
                    //友盟统计
                    MobclickAgent.onEvent(getActivity(), "buyNow");
                }

                //根据规格更改比价列表数据
                mPresenter.compSpec();

                dialog.dismiss();

                //添加到购物车数据库中
//                AddShopCartAnim addShopCartAnim = new AddShopCartAnim(getActivity());
//                addShopCartAnim.addGoodsToCart(parentLayout, shoppingCartImageView, dialog_img, tvCount);
            }
        });

        //点击添加购物车
        goodscart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 检验是否选择的商品属性值
                if (selectGsAttrsOne.equals("") && selectGsAttrsOne.equals("") ||
                        selectGsAttrsTwo.equals("") && selectGsAttrsTwo.equals("")
                        || selectGsAttrsThree.equals("") && selectGsAttrsThree.equals("")) {
                    title_selectercurrent_goods.setText("请选择商品规格");
                }
                mPresenter.addShopCars();
                dialog.dismiss();
            }
        });

        //点击立即购买
        btn_goodinfo_topay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 检验是否选择的商品属性值
                if (selectGsAttrsOne.equals("") && selectGsAttrsOne.equals("") ||
                        selectGsAttrsTwo.equals("") && selectGsAttrsTwo.equals("")
                        || selectGsAttrsThree.equals("") && selectGsAttrsThree.equals("")) {
                    title_selectercurrent_goods.setText("请选择商品规格");
                }
                // 立即购买
                mPresenter.orderSel();
                dialog.dismiss();
            }
        });
    }

    /**
     * 如果来自购物车，mShopBean替换
     *
     * @return 拼接后的字符串
     */
    private void shopBeanReps() {
        if (mShopBean == null) return;
        StringBuffer sb = new StringBuffer();
        String goodSpec = mShopBean.getGoods_spec();
        String[] arrStr1 = goodSpec.split(" ");
        for (int i = 0; i < arrStr1.length; i++) {
            String[] arrStr2 = arrStr1[i].split(":");
            if (!TextUtils.isEmpty(sGsNameOne) && sGsNameOne.equals(arrStr2[0])) {
                arrStr2[1] = selectGsAttrsOne;
            } else if (!TextUtils.isEmpty(sGsNameTwo) && sGsNameTwo.equals(arrStr2[0])) {
                arrStr2[1] = selectGsAttrsTwo;
            } else if (!TextUtils.isEmpty(sGsNameThree) && sGsNameThree.equals(arrStr2[0])) {
                arrStr2[1] = selectGsAttrsThree;
            }
            sb.append(arrStr2[0] + ":" + arrStr2[1]);
            if (i < (arrStr1.length - 1)) {
                sb.append(" ");
            }
        }
        mShopBean.setGoods_spec(sb.toString());
    }

    /**
     * 拼接商品规格展示
     *
     * @return 拼接后的字符串
     */
    private String sbSplit() {
        StringBuffer sb = new StringBuffer();
        sb.append(selectGsAttrsOne);
        if (!TextUtils.isEmpty(selectGsAttrsOne) && !TextUtils.isEmpty(selectGsAttrsTwo)) {
            sb.append("、");
        }
        sb.append(selectGsAttrsTwo);
        if ((!TextUtils.isEmpty(selectGsAttrsOne) || !TextUtils.isEmpty(selectGsAttrsTwo)) && !TextUtils.isEmpty(selectGsAttrsThree)) {
            sb.append("、");
        }
        sb.append(selectGsAttrsThree);
        return sb.toString();
    }

    @Override
    public String optId1() {
        return opt_id1;
    }

    @Override
    public String optId2() {
        return opt_id2;
    }

    @Override
    public String optId3() {
        return opt_id3;
    }

    @Override
    public void changeGoodsSpec(ChangeGsAttrBean data) {

        // 获取修改过属性
        if (data == null) {
            return;
        }
        dialog_goods_nmb.setText("库存" + data.getStock() + "件");
        dialog_goods_price.setText("￥" + data.getSale_price());
        spec_id = data.getId();
        specName = selectGsAttrsOne + selectGsAttrsTwo + selectGsAttrsThree;

        //设置默认选项
        String img_url = ApiUrlConstant.API_IMG_URL + data.getImg();
        goods_type.setVisibility(View.VISIBLE);
        goods_type.setText("已选:" + sbSplit());
        goods_type.setTextColor(Color.parseColor("#FF2E00"));

        Glide.with(getActivity())
                .load(img_url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.no_image)
                .placeholder(R.mipmap.no_image)
                .into(dialog_img);
    }


    public class DialogClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case custom_dialog_close:

                    dialog.dismiss();

                    break;
                case R.id.tv_item_minus_comm_detail:
                    //减少购买数量
                    goods_nmb--;
                    if (goods_nmb < 1) {
                        ToastUtils.show(getActivity(), "已是最低购买量", 0);
                    } else {
                        tv_item_number_comm_detail.setText(String.valueOf(goods_nmb));
                    }
                    break;
                case R.id.tv_item_add_comm_detail:
                    //增加购买数量
                    goods_nmb++;
                    tv_item_number_comm_detail.setText(String.valueOf(goods_nmb));
                    break;
            }
        }
    }

    @Override
    public String ShopCarcount() {
        // 设置购物数量
        return String.valueOf(goods_nmb);
    }
}

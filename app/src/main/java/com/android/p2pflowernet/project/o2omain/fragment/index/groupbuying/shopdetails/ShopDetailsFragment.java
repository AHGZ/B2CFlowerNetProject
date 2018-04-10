package com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying.shopdetails;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.GroupBuyingShopDetailsAdapter;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.GroupBuyingBean;
import com.android.p2pflowernet.project.entity.GroupHomeBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying.evaluation.EvaluationActivity;
import com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying.shopdetails.submitorder.SubmitOrderActivity;
import com.android.p2pflowernet.project.utils.SPUtils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import butterknife.BindView;

/**
 * Created by heguozhong on 2018/1/4/004.
 * 团购商品详情主界面
 */

public class ShopDetailsFragment extends KFragment<IShopDetailsView, IShopDetailsPresenter> implements IShopDetailsView {

    //展示商品详情的recyclerview布局
    @BindView(R.id.group_buying_shopdetails_recyclerview)
    RecyclerView groupBuyingShopdetailsRecyclerview;
    //立即抢购按钮
    @BindView(R.id.group_buying_shopdetails_ljqg)
    Button groupBuyingShopdetailsLjqg;

    private ShapeLoadingDialog shapeLoadingDialog;
    private GroupBuyingShopDetailsAdapter groupBuyingShopDetailsAdapter;
    private GroupBuyingBean groupBuyingBean;
    private String tel;//联系电话
    private String huafan_price;//花返价格
    private int merchant_id;//商家ID
    private int group_id;//团购ID
    private GroupHomeBean.ListBean listBean;
    private PopupWindow popupWindow;

    public static ShopDetailsFragment newInstance(GroupHomeBean.ListBean listBean) {
        Bundle args = new Bundle();
        ShopDetailsFragment fragment = new ShopDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public IShopDetailsPresenter createPresenter() {
        return new IShopDetailsPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.groupbuying_shop_details;
    }

    @Override
    public void initData() {
        Bundle arguments = getArguments();
        listBean = (GroupHomeBean.ListBean) arguments.getSerializable("group_info");
        merchant_id = Integer.parseInt(listBean.getMerchant_id());
        group_id = Integer.parseInt(listBean.getId());
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        //初始化沉浸式
        Eyes.translucentStatusBar(getActivity(), true);

        initData();
        //初始化加载进度条
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .delay(5000)
                .loadText("加载中...")
                .build();

        mPresenter.getGroupBuyingDetail(merchant_id,group_id);

    }

    @Override
    public void onError(String errorMsg) {
        showShortToast(errorMsg);
    }

    @Override
    public void onSuccess(GroupBuyingBean groupBuyingBean) {
        this.groupBuyingBean=groupBuyingBean;
        GroupBuyingBean.InfoBean info = groupBuyingBean.getInfo();
        tel = info.getMerch_info().getTel();
        huafan_price = String.format("%.2f", (Double.parseDouble(info.getPrice()) - Double.parseDouble(info.getSupply_price())));

        //获取布局管理者
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //设置为垂直排列格式
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //recyclerview应用垂直排列格式
        groupBuyingShopdetailsRecyclerview.setLayoutManager(linearLayoutManager);
        //设置适配器
        groupBuyingShopDetailsAdapter = new GroupBuyingShopDetailsAdapter(getActivity(),info);
        groupBuyingShopdetailsRecyclerview.setAdapter(groupBuyingShopDetailsAdapter);
        onListeners(groupBuyingBean);
    }

    @Override
    public void onSuccess(String message) {

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

    //各种点击事件监听
    public void onListeners(final GroupBuyingBean data) {
        //返回按钮点击事件
        groupBuyingShopDetailsAdapter.setOnBackClickListener(new GroupBuyingShopDetailsAdapter.OnBackClickListener() {
            @Override
            public void onBackClick(View v) {
                removeFragment();
            }
        });
        //分享按钮点击事件
        groupBuyingShopDetailsAdapter.setOnShapeClickListenerr(new GroupBuyingShopDetailsAdapter.OnShapeClickListener() {
            @Override
            public void onShapeClick(View v) {

                initControls(data);
                popupWindow.showAtLocation(groupBuyingShopdetailsRecyclerview, Gravity.BOTTOM, 0, 0);
            }
        });
        //查看全部评论内容点击事件
        groupBuyingShopDetailsAdapter.setOnSelectAllClickListener(new GroupBuyingShopDetailsAdapter.OnSelectAllClickListener() {
            @Override
            public void onSelectAllClick(View v) {
                startActivity(new Intent(getActivity(), EvaluationActivity.class));
            }
        });
        //查看图文详情点击事件
        groupBuyingShopDetailsAdapter.setOnPhoteDetailsClickListener(new GroupBuyingShopDetailsAdapter.OnPhoteDetailsClickListener() {
            @Override
            public void onPhoteDetailsClick(View v) {
                showShortToast("图文详情被点击了");
            }
        });
        //设置打电话按钮监听
        groupBuyingShopDetailsAdapter.setOnCallClickListener(new GroupBuyingShopDetailsAdapter.OnCallClickListener() {
            @Override
            public void onCallClick(View v,String tel) {
                call(tel);
            }
        });
        //立即抢购按钮点击事件
        groupBuyingShopdetailsLjqg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SubmitOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tel",tel);
                bundle.putString("huafan_price",huafan_price);
                bundle.putSerializable("listBean",listBean);
                intent.putExtras(bundle);
                startActivity(intent);

                //友盟统计
                MobclickAgent.onEvent(getActivity(), "groupBuy");
            }
        });
    }
    @Override
    public String longitude() {
        return String.valueOf(SPUtils.get(getActivity(), "longitude", ""));
    }

    @Override
    public String latitude() {
        return String.valueOf(SPUtils.get(getActivity(), "latitude", ""));
    }
    //拨打手机号
    public void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //初始化分享功能
    private void initControls(final GroupBuyingBean data) {

        //生成二维码
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
        LinearLayout lin5 = (LinearLayout) view.findViewById(R.id.share_qq_space);
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
                ClipboardManager myClipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                String text = data.getInfo().getUrl()==null?"":data.getInfo().getUrl();
                ClipData text1 = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(text1);
                showShortToast("已复制到剪切板");
            }
        });
    }

    //QQ空间分享
    private void share5(GroupBuyingBean data) {

        String share_url = data.getInfo().getUrl() == null ? "" : data.getInfo().getUrl();

        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(data.getInfo().getTitle() == null ? "" : data.getInfo().getTitle());//标题
        String img = ApiUrlConstant.API_IMG_URL + data.getInfo().getImg_list().get(0).toString();
        UMImage image = new UMImage(getActivity(), img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(data.getInfo().getIntro() == null ? "" : data.getInfo().getIntro());//描述

        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.QZONE)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    //QQ好友分享
    private void share4(GroupBuyingBean data) {

        String share_url = data.getInfo().getUrl() == null ? "" : data.getInfo().getUrl();
        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(data.getInfo().getTitle() == null ? "" : data.getInfo().getTitle());//标题
        String img = ApiUrlConstant.API_IMG_URL + data.getInfo().getImg_list().get(0).toString();
        UMImage image = new UMImage(getActivity(), img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(data.getInfo().getIntro() == null ? "" : data.getInfo().getIntro());//描述

        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.QQ)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    //微信分享
    private void share2(GroupBuyingBean data) {

        String share_url = data.getInfo().getUrl() == null ? "" : data.getInfo().getUrl();
        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(data.getInfo().getTitle() == null ? "" : data.getInfo().getTitle());//标题
        String img = ApiUrlConstant.API_IMG_URL + data.getInfo().getImg_list().get(0).toString();
        UMImage image = new UMImage(getActivity(), img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(data.getInfo().getIntro() == null ? "" : data.getInfo().getIntro());//描述

        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.WEIXIN)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    //微信朋友圈
    private void share1(GroupBuyingBean data) {

        String share_url = data.getInfo().getUrl() == null ? "" : data.getInfo().getUrl();
        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(data.getInfo().getTitle() == null ? "" : data.getInfo().getTitle());//标题
        String img = ApiUrlConstant.API_IMG_URL + data.getInfo().getImg_list().get(0).toString();
        UMImage image = new UMImage(getActivity(), img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(data.getInfo().getIntro() == null ? "" : data.getInfo().getIntro());//描
        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    //新浪微博
    private void share3(GroupBuyingBean data) {

        String share_url = data.getInfo().getUrl() == null ? "" : data.getInfo().getUrl();
        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(data.getInfo().getTitle() == null ? "" : data.getInfo().getTitle());//标题
        String img = ApiUrlConstant.API_IMG_URL + data.getInfo().getImg_list().get(0).toString();
        UMImage image = new UMImage(getActivity(), img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(data.getInfo().getIntro() == null ? "" : data.getInfo().getIntro());//描
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


    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }
    }

    /***
     * 友盟统计
     */
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("GroupDetailPage"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("GroupDetailPage");
    }
}

package com.android.p2pflowernet.project.view.fragments.mine.myteam.cloud_office;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.CloudOfficeBean;
import com.android.p2pflowernet.project.entity.ShareCodeBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.activity.TaskHistoryActivity;
import com.android.p2pflowernet.project.view.customview.SectionProgressBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.mine.myteam.cloud_office.strongplan.StrongPlanCloudActivity;
import com.android.p2pflowernet.project.view.fragments.mine.myteam.news.GoodNewsActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2017/11/28.
 * by--云工办公
 */

public class CloudOfficeFragment extends KFragment<ICloudOfficeView, ICloudOfficePrenter> implements ICloudOfficeView {

    @BindView(R.id.iv_btn_invite)
    ImageView ivBtnInvite;
    @BindView(R.id.ll_schedule_plan)
    LinearLayout llSchedulePlan;
    @BindView(R.id.ll_strong_plan)
    LinearLayout llStrongPlan;
    @BindView(R.id.ll_more_goods)
    LinearLayout llMoreGoods;
    @BindView(R.id.section_bar)
    SectionProgressBar sectionBar;
    @BindView(R.id.section_bar1)
    SectionProgressBar sectionBar1;

    private Handler mHandler = new Handler();
    private ShapeLoadingDialog shapeLoadingDialog;
    private PopupWindow popupWindow;

    @Override
    public ICloudOfficePrenter createPresenter() {

        return new ICloudOfficePrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_cloud_office;
    }

    @Override
    public void initData() {

        mPresenter.getCloudOffice();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化合伙人，代理人的进度条
        sectionBar.setLevels(getResources().getStringArray(R.array.SectionLevels));
        sectionBar.setLevelValues(getResources().getIntArray(R.array.PartnerSectionLevelValues));
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

//        initData();
    }

    public static Fragment newIntence() {

        CloudOfficeFragment cloudOfficeFragment = new CloudOfficeFragment();
        Bundle bundle = new Bundle();
        cloudOfficeFragment.setArguments(bundle);

        return cloudOfficeFragment;
    }

    @OnClick({R.id.iv_btn_invite, R.id.ll_schedule_plan, R.id.ll_strong_plan, R.id.ll_more_goods})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_btn_invite://邀请

                mPresenter.getShareCode();

                break;
            case R.id.ll_schedule_plan://（云工）任务历史

                Intent intent = new Intent(getActivity(), TaskHistoryActivity.class);
                intent.putExtra("tag", 1);
                startActivity(intent);

                break;
            case R.id.ll_strong_plan://成长计划

                intent = new Intent(getActivity(), StrongPlanCloudActivity.class);
                startActivity(intent);

                break;
            case R.id.ll_more_goods://更多喜讯

                intent = new Intent(getActivity(), GoodNewsActivity.class);
                intent.putExtra("tag", "0");
                startActivity(intent);

                break;
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

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void Successcloud(CloudOfficeBean data) {

        if (data != null) {

            //设置合伙人数和会员人数
            if (sectionBar != null) {
                sectionBar.setCurrent(data.getPartner());
                sectionBar1.setCurrent(data.getUser());
            }
        }
    }

    @Override
    public void onSuccessShare(ShareCodeBean data) {

        if (data != null) {

            initControls(data);
            popupWindow.showAtLocation(ivBtnInvite, Gravity.BOTTOM, 0, 0);

        }
    }

    @Override
    public void onSuccess(String message) {

    }

    //初始化分享功能
    private void initControls(final ShareCodeBean data) {

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
                String text = "您的好友邀请您注册“花返网”一起开启花返之旅";
                ClipData text1 = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(text1);
                showShortToast("已复制到剪切板");
            }
        });
    }

    //QQ空间分享
    private void share5(ShareCodeBean data) {

        String share_url = data.getShare_url() == null ? "" : data.getShare_url();

        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(data.getTitle() == null ? "" : data.getTitle());//标题
        String img = ApiUrlConstant.API_IMG_URL + data.getImg();
        UMImage image = new UMImage(getActivity(), img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(data.getIntro() == null ? "" : data.getIntro());//描述

        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.QZONE)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    //QQ好友分享
    private void share4(ShareCodeBean data) {

        String share_url = data.getShare_url() == null ? "" : data.getShare_url();
        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(data.getTitle() == null ? "" : data.getTitle());//标题
        String img = ApiUrlConstant.API_IMG_URL + data.getImg();
        UMImage image = new UMImage(getActivity(), img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(data.getIntro() == null ? "" : data.getIntro());//描述

        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.QQ)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    //微信分享
    private void share2(ShareCodeBean data) {

        String share_url = data.getShare_url() == null ? "" : data.getShare_url();
        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(data.getTitle() == null ? "" : data.getTitle());//标题
        String img = ApiUrlConstant.API_IMG_URL + data.getImg();
        UMImage image = new UMImage(getActivity(), img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(data.getIntro() == null ? "" : data.getIntro());//描述

        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.WEIXIN)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    //微信朋友圈
    private void share1(ShareCodeBean data) {

        String share_url = data.getShare_url() == null ? "" : data.getShare_url();
        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(data.getTitle() == null ? "" : data.getTitle());//标题
        String img = ApiUrlConstant.API_IMG_URL + data.getImg();
        UMImage image = new UMImage(getActivity(), img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(data.getIntro() == null ? "" : data.getIntro());//描
        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    //新浪微博
    private void share3(ShareCodeBean data) {

        String share_url = data.getShare_url() == null ? "" : data.getShare_url();
        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(data.getTitle() == null ? "" : data.getTitle());//标题
        String img = ApiUrlConstant.API_IMG_URL + data.getImg();
        UMImage image = new UMImage(getActivity(), img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(data.getIntro() == null ? "" : data.getIntro());//描
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
}

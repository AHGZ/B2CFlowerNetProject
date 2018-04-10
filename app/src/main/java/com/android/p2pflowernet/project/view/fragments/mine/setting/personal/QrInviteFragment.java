package com.android.p2pflowernet.project.view.fragments.mine.setting.personal;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.ShowPersonInfo;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.QRCodeUtil;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2017/11/15.
 * by--二维码邀请页面
 */

public class QrInviteFragment extends KFragment<IQrInViteView, IQrInvitePrenter>
        implements NormalTopBar.normalTopClickListener, IQrInViteView {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.tv_qr_code)
    TextView tvQrCode;
    @BindView(R.id.iv_qr_code_img)
    ImageView ivQrCodeImg;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.share_wechat)
    LinearLayout shareWechat;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.share_wechat_circle)
    LinearLayout shareWechatCircle;
    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.share_qq_frend)
    LinearLayout shareQqFrend;
    @BindView(R.id.img4)
    ImageView img4;
    @BindView(R.id.share_qq_space)
    LinearLayout shareQqSpace;
    @BindView(R.id.img5)
    ImageView img5;
    @BindView(R.id.share_sina)
    LinearLayout shareSina;
    @BindView(R.id.img6)
    ImageView img6;
    @BindView(R.id.share_copy)
    LinearLayout shareCopy;
    private ShapeLoadingDialog shapeLoadingDialog;
    private Bitmap qrImage;

    @Override
    public IQrInvitePrenter createPresenter() {
        return new IQrInvitePrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_qr_invite;
    }

    @Override
    public void initData() {

        mPresenter.getPersonal();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setTitleText("扫码邀请");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        Utils.setStatusBar(getActivity(), 0, false);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);
        //初始化加载进度条
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity()).delay(5000).loadText("加载中...").build();
        initData();
    }

    public static KFragment newIntence() {

        Bundle bundle = new Bundle();
        QrInviteFragment qrInviteFragment = new QrInviteFragment();
        qrInviteFragment.setArguments(bundle);
        return qrInviteFragment;
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
    public void setShowPersonInfo(ShowPersonInfo data) {

        if (data != null) {

            tvQrCode.setText(data.getInvite_code());
            //生成二维码
            Resources res = getResources();
            Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.logo);
            qrImage = QRCodeUtil.createQRImage(data.getInvite_code(), 125, 135, bmp, "");
            Drawable drawable = new BitmapDrawable(qrImage);
            ivQrCodeImg.setImageDrawable(drawable);
        }
    }

    @OnClick({R.id.share_wechat, R.id.share_wechat_circle, R.id.share_qq_frend, R.id.share_qq_space,
            R.id.share_sina, R.id.share_copy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_wechat://微信

                UMWeb web = new UMWeb("http://www.info369.com");
                web.setTitle("");//标题
                UMImage image = new UMImage(getActivity(), qrImage);//资源文件
                web.setThumb(image);  //缩略图
                web.setDescription("");//描述
                new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.WEIXIN)
                        .withMedia(web)
                        .setCallback(umShareListener)
                        .share();

                break;
            case R.id.share_wechat_circle://微信朋友圈

                web = new UMWeb("http://www.info369.com");
                web.setTitle("");//标题
                image = new UMImage(getActivity(), qrImage);//资源文件
                web.setThumb(image);  //缩略图
                web.setDescription("");//描述
                new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withMedia(web)
                        .setCallback(umShareListener)
                        .share();

                break;
            case R.id.share_qq_frend://QQ好友

                web = new UMWeb("http://www.info369.com");
                web.setTitle("");//标题
                image = new UMImage(getActivity(), qrImage);//资源文件
                web.setThumb(image);  //缩略图
                web.setDescription("");//描述
                new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.QQ)
                        .withMedia(web)
                        .setCallback(umShareListener)
                        .share();

                break;
            case R.id.share_qq_space://QQ空间

                web = new UMWeb("http://www.info369.com");
                web.setTitle("");//标题
                image = new UMImage(getActivity(), qrImage);//资源文件
                web.setThumb(image);  //缩略图
                web.setDescription("");//描述
                new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.QZONE)
                        .withMedia(web)
                        .setCallback(umShareListener)
                        .share();

                break;
            case R.id.share_sina://新浪

                web = new UMWeb("http://www.info369.com");
                web.setTitle("");//标题
                image = new UMImage(getActivity(), qrImage);//资源文件
                web.setThumb(image);  //缩略图
                web.setDescription("");//描述
                new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.SINA)
                        .withMedia(web)
                        .setCallback(umShareListener)
                        .share();

                break;
            case R.id.share_copy://复制
                ClipboardManager myClipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                String text = tvQrCode.getText().toString().trim();
                ClipData text1 = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(text1);
                showShortToast("已复制到剪切板");
                break;
        }
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
}

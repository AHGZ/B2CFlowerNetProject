package com.android.p2pflowernet.project.view.fragments.goods.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.mvp.KFragment;

import butterknife.BindView;

/**
 * @描述:图文详情webview
 * @创建人：zhangpeisen
 * @创建时间：2017/10/18 下午2:22
 * @修改人：zhangpeisen
 * @修改时间：2017/10/18 下午2:22
 * @修改备注：
 * @throws
 */
public class GoodsInfoWebFragment extends KFragment<IGoodsInfoWebView, IGoodsInfoWebPresenter> {
    @BindView(R.id.wv_detail)
    public WebView wv_detail;
    private WebSettings webSettings;

    @Override
    public IGoodsInfoWebPresenter createPresenter() {
        return new IGoodsInfoWebPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_item_info_web;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        initWebView();
    }


    public void initWebView() {
        String url = "http://m.okhqb.com/item/description/1000334264.html?fromApp=true";
        wv_detail.setFocusable(false);
        wv_detail.loadUrl(url);
        webSettings = wv_detail.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setBlockNetworkImage(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wv_detail.setWebViewClient(new GoodsDetailWebViewClient());
    }

    private class GoodsDetailWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webSettings.setBlockNetworkImage(false);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }
    }
}

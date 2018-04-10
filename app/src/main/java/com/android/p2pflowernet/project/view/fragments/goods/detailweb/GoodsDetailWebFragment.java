package com.android.p2pflowernet.project.view.fragments.goods.detailweb;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebSettings;
import com.just.agentweb.AgentWebUIController;
import com.just.agentweb.ChromeClientCallbackManager;
import com.just.agentweb.WebDefaultSettingsManager;

import butterknife.BindView;


/**
 * 图文详情webview的Fragment
 */
public class GoodsDetailWebFragment extends KFragment<IGoodsDetailWebView, IGoodsDetailWebPresenter> {
    @BindView(R.id.wv_detail)
    public WebView wv_detail;

    private WebSettings webSettings;
    private String xqurl = "";
    private AgentWeb mAgentWeb;

    public static GoodsDetailWebFragment newInstance(String xqurl) {
        Bundle args = new Bundle();
        args.putString("url", xqurl);
        GoodsDetailWebFragment fragment = new GoodsDetailWebFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xqurl = getArguments().getString("url");
    }

    @Override
    public IGoodsDetailWebPresenter createPresenter() {
        return new IGoodsDetailWebPresenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_item_detail_web;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        initWebView(view);
    }

    public void initWebView(View rootView) {
        String url = "http://www.baidu.com";
//        wv_detail.setFocusable(false);
//        wv_detail.loadUrl(url);
//        webSettings = wv_detail.getSettings();
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setLoadsImagesAutomatically(true);
//        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        webSettings.setBlockNetworkImage(true);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        wv_detail.setWebViewClient(new GoodsDetailWebViewClient());

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((ViewGroup) rootView, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()// 使用默认进度条
                .setReceivedTitleCallback(mCallback) //标题回调
                .setAgentWebWebSettings(getSettings())//设置 AgentWebSettings。
                .setWebViewClient(mWebViewClient)//WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效。
                .setWebChromeClient(mWebChromeClient) //WebChromeClient
                .setSecurityType(AgentWeb.SecurityType.strict) //注意这里开启 strict 模式 ， 设备低于 4.2 情况下回把注入的 Js 全部清空掉 ， 这里推荐使用 onJsPrompt 通信
                .createAgentWeb()//
                .ready()//
                .go(url);

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

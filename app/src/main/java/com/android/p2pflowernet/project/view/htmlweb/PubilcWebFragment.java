package com.android.p2pflowernet.project.view.htmlweb;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.android.p2pflowernet.project.MainActivity;
import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.activity.PublicWebActivity;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebSettings;
import com.just.agentweb.AgentWebUIController;
import com.just.agentweb.ChromeClientCallbackManager;
import com.just.agentweb.WebDefaultSettingsManager;

import butterknife.BindView;


/**
 * @描述:公共html5页面
 * @创建人：zhangpeisen
 * @创建时间：2017/10/30 下午5:40
 * @修改人：zhangpeisen
 * @修改时间：2017/10/30 下午5:40
 * @修改备注：
 * @throws
 */
public class PubilcWebFragment extends KFragment<IPublicWebView, PublicWebvPresenter>
        implements NormalTopBar.normalTopClickListener, PublicWebActivity.FragmentBackListener {

    @BindView(R.id.normal_top)
    //自定义通用标题
            NormalTopBar normalTopBar;

    private AgentWeb mAgentWeb;

    private String mPublicUrl;
    private String tag = "1";//0-由广告页进入首页 1不进入首页

    public static PubilcWebFragment newInstance(String publicUrl, String tag) {
        Bundle args = new Bundle();
        PubilcWebFragment fragment = new PubilcWebFragment();
        args.putString("publicurl", publicUrl);
        args.putString("tag", tag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPublicUrl = getArguments().getString("publicurl");
        tag = getArguments().getString("tag");
    }

    @Override
    public PublicWebvPresenter createPresenter() {
        return new PublicWebvPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.hfw_app_weblayout;
    }

    @Override
    public void initData() {
        //初始化沉浸式
        Eyes.setStatusBarLightMode(getActivity(), Color.parseColor("#009a44"));
        UIUtils.setTouchDelegate(normalTopBar.getLeftImage(), 50);
    }


    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        // 初始化标题及相关事件监听
        normalTopBar.setTitleText("正在载入");
        normalTopBar.getRightImage().setVisibility(View.GONE);
        normalTopBar.setTopClickListener(this);
        normalTopBar.setBackgroundResource(R.drawable.app_statusbar_bg);
        //初始化沉浸式标题栏
        Utils.setStatusBar(getActivity(), 0, false);

        if (TextUtils.isEmpty(mPublicUrl) && mPublicUrl.equals("")) {
            return;
        }
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((ViewGroup) view, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()// 使用默认进度条
                .setReceivedTitleCallback(mCallback) //标题回调
                .setAgentWebWebSettings(getSettings())//设置 AgentWebSettings。
                .setWebViewClient(mWebViewClient)//WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效。
                .setWebChromeClient(mWebChromeClient) //WebChromeClient
                .setSecurityType(AgentWeb.SecurityType.strict) //注意这里开启 strict 模式 ， 设备低于 4.2 情况下回把注入的 Js 全部清空掉 ， 这里推荐使用 onJsPrompt 通信
                .createAgentWeb()//
                .ready()//
                .go(mPublicUrl);

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
            normalTopBar.setTitleText(title);

        }
    };


    @Override
    public void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();//恢复
        super.onResume();
    }

    @Override
    public void onPause() {
        mAgentWeb.getWebLifeCycle().onPause(); //暂停应用内所有WebView ， 调用mWebView.resumeTimers(); 恢复。
        super.onPause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //注册监听
        ((PublicWebActivity) getActivity()).setBackListener(this);
        ((PublicWebActivity) getActivity()).setInterception(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //取消监听
        ((PublicWebActivity) getActivity()).setBackListener(null);
        ((PublicWebActivity) getActivity()).setInterception(false);
    }

    @Override
    public void onDestroyView() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroyView();
    }

    @Override
    public void onLeftClick(View view) {
        if (!mAgentWeb.back()) {

            if (tag.equals("0")) {//0-由广告页进入首页 1不进入首页
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }

            removeFragment();

        } else {
            mAgentWeb.back();
        }
    }

    @Override
    public void onRightClick(View view) {

    }

    @Override
    public void onTitleClick(View view) {

    }

    @Override
    public void onBackForward() {
        if (!mAgentWeb.back()) {

            if (tag.equals("0")) {//0-由广告页进入首页 1不进入首页
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
            removeFragment();
        } else {
            mAgentWeb.back();
        }
    }
}

package com.android.p2pflowernet.project.view.fragments.knowh5;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.utils.WebViewConfig;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;

import butterknife.BindView;

/**
 * Created by caishen on 2017/12/1.
 * by--了解更多的h5页面
 */

public class KnowMoreFragment extends KFragment<IKnowMoreView, IKnowMorePrenter>
        implements NormalTopBar.normalTopClickListener {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.webView)
    WebView webView;
    private String url = "";

    @Override
    public IKnowMorePrenter createPresenter() {
        return new IKnowMorePrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_konw_more_h5;
    }

    @Override
    public void initData() {

        webView.loadUrl(url);
        //初始化webview
        WebViewConfig.initWebViewConfig(webView);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        url = arguments.getString("url");
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        Utils.setStatusBar(getActivity(), 0, false);
        normalTop.setTitleText("详情页");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);

        initData();
    }

    public static KFragment newIntence(String url) {

        KnowMoreFragment knowMoreFragment = new KnowMoreFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        knowMoreFragment.setArguments(bundle);
        return knowMoreFragment;
    }

    @Override
    public void onLeftClick(View view) {

        removeFragment();
        if (webView != null) {
            WebViewConfig.destroy(webView);
        }
    }

    @Override
    public void onRightClick(View view) {

    }

    @Override
    public void onTitleClick(View view) {

    }
}

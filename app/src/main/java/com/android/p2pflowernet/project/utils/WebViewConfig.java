package com.android.p2pflowernet.project.utils;

import android.os.Build;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static android.webkit.WebSettings.LOAD_DEFAULT;

/**
 * Created by caishen on 2017/12/1.
 * by--优化webview
 */

public class WebViewConfig {

    //初始化WebView配置
    public static void initWebViewConfig(WebView webview) {
        // 设置WebView初始化尺寸，参数为百分比
        webview.setInitialScale(100);
        //设置WebView可触摸放大缩小
        webview.getSettings().setSupportZoom(true);
        //设置为可调用js方法*
        webview.getSettings().setBuiltInZoomControls(true);
        //WebView双击变大，再双击后变小，当手动放大后，双击可以恢复到原始大小
        webview.getSettings().setUseWideViewPort(true);
        // 获取WebSettings对象
        WebSettings webSettings = webview.getSettings();
        // 设置WebView支持运行普通的Javascript
        webSettings.setJavaScriptEnabled(true);
        // 设置WebChromeClient，以支持运行特殊的Javascript
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
        //（默认）根据cache-control决定是否从网络上取数据。
        webSettings.setCacheMode(LOAD_DEFAULT);
        // 阻止网络图片加載
        webview.getSettings().setBlockNetworkImage(false);
        //支持javascript
        webview.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webview.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webview.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        webview.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setLoadWithOverviewMode(true);


        //如果不设置WebViewClient，请求会跳转系统浏览器
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //该方法在Build.VERSION_CODES.LOLLIPOP以前有效，从Build.VERSION_CODES.LOLLIPOP起，建议使用shouldOverrideUrlLoading(WebView, WebResourceRequest)} instead
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242

                if (url.toString().contains("sina.cn")) {
                    view.loadUrl("http://ask.csdn.net/questions/178242");
                    return true;
                }
                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (request.getUrl().toString().contains("sina.cn")) {
                        view.loadUrl("http://ask.csdn.net/questions/178242");
                        return true;
                    }
                }
                return false;
            }
        });
    }

    //销毁资源
    public static void destroy(WebView webview) {
        webview.stopLoading(); //停止加载
        ((ViewGroup) webview.getParent()).removeView(webview); //把webview从视图中移除
        webview.removeAllViews(); //移除webview上子view
        webview.clearCache(true); //清除缓存
        webview.clearHistory(); //清除历史
        webview.destroy(); //销毁webview自身
        //Process.killProcess(Process.myPid()); //杀死WebView所在的进程
    }
}

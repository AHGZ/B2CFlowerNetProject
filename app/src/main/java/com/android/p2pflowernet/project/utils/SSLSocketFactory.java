package com.android.p2pflowernet.project.utils;

import android.content.Context;
import android.util.Log;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * author: zhangpeisen
 * created on: 2017/11/23 下午5:21
 * description: https 加载证书的工具类
 */
public class SSLSocketFactory {
    /**
     * 获取bks文件的sslsocketfactory
     *
     * @param context
     * @return
     */
    public static javax.net.ssl.SSLSocketFactory getSSLSocketFactory(Context context, int rawresouceId) {
        final String CLIENT_TRUST_PASSWORD = "123456";//信任证书密码，该证书默认密码是123456
        final String CLIENT_AGREEMENT = "TLS";//使用协议
        final String CLIENT_TRUST_KEYSTORE = "BKS";
        SSLContext sslContext = null;
        try {
            //取得SSL的SSLContext实例
            sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
            //取得TrustManagerFactory的X509密钥管理器实例
            TrustManagerFactory trustManager = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            //取得BKS密库实例
            KeyStore tks = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);
            InputStream is = context.getResources().openRawResource(rawresouceId);
            try {
                tks.load(is, CLIENT_TRUST_PASSWORD.toCharArray());
            } finally {
                is.close();
            }
            //初始化密钥管理器
            trustManager.init(tks);
            //初始化SSLContext
            sslContext.init(null, trustManager.getTrustManagers(), null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SslContextFactory", e.getMessage());
        }
        return sslContext.getSocketFactory();
    }
}

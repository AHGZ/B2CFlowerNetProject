package com.android.p2pflowernet.project.utils;

import android.annotation.SuppressLint;

import com.android.p2pflowernet.project.constant.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * @author zhangpeisen
 * @ClassName: SignUtil
 * @Description: TODO 生成签名工具类
 * @date 2016年9月18日 下午6:29:53
 */
public class SignUtil {
    private static SignUtil instance = null;

    private SignUtil() {
    }

    public static SignUtil getInstance() {
        if (instance == null) {
            instance = new SignUtil();
        }
        return instance;
    }

    /**
     * @Title: getSign @Description: TODO @param @param token @param @param
     * v @param @param t @param @param deviceId @param @param
     * platform @param @return_ticket @return_ticket String 返回类型 @throws
     */
    @SuppressLint("DefaultLocale")
    public String getSign(HashMap<String, String> params) {
        // salt固定值
        params.put("salt", Constants.SALT);
        StringBuilder stringBuilder = new StringBuilder();
        List<String> sortParam = getNameSort(params);
        for (String key : sortParam) {
            //.append("&")
            stringBuilder.append(key).append("=").append(params.get(key));
        }
        LogUtils.e("排序后签名= > ", stringBuilder.toString());
        return getInterChangeSignStr(MD5Utils.MD5To32(stringBuilder.toString()));
    }


    public String getInterChangeSignStr(String signStr) {

        StringBuilder stringBuilder = new StringBuilder();
        // 前8位
        String signStrStart = signStr.substring(0, 8);
        // 中间字符串
        String signStrCenter = signStr.substring(8, signStr.length() - 8);
        // 后8位
        String signStrEnd = signStr.substring(signStr.length() - 8, signStr.length());
        stringBuilder.append(signStrEnd).append(signStrCenter).append(signStrStart);

        return stringBuilder.toString();
    }

    /**
     * @return void 返回类型 @throws
     * @Title: getNameSort
     * @Description: 获取排序的maps
     * TODO @param
     */
    @SuppressLint("DefaultLocale")
    public List<String> getNameSort(HashMap<String, String> params) {
        List<String> mEntityLists = new ArrayList<String>();
        for (String UrlParamKeys : params.keySet()) {
            mEntityLists.add(UrlParamKeys);
        }
        // 参数进行排序
        Collections.sort(mEntityLists, new StringComparator());
        return mEntityLists;
    }

    /**
     * @author zhangpeisen
     * @ClassName: StringComparator
     * @Description: TODO 参数排序
     * @date 2016年9月18日 下午6:38:36
     */
    public class StringComparator implements Comparator<String> {

        @Override
        public int compare(String Str1, String Str2) {
            // TODO Auto-generated method stub
            return Str1.compareTo(Str2);
        }
    }
}

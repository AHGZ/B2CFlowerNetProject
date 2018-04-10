package com.android.p2pflowernet.project.utils;

import android.content.Context;

/** 
* @ClassName: AssetsUtils 
* @Description: TODO  操作安装包中的“assets”目录下的文件
* @author zhangpeisen
* @date 2016年5月6日 上午11:05:46 
*  
*/
public class AssetsUtils {

    /**
     * read file content
     *
     * @param context   the context
     * @param assetPath the asset path
     * @return String string
     */
    public static String readText(Context context, String assetPath) {
        LogUtils.e("","read assets file as text: " + assetPath);
        try {
            return ConvertUtils.toString(context.getAssets().open(assetPath));
        } catch (Exception e) {
            LogUtils.e("",e.getMessage());
            return "";
        }
    }

}

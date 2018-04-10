package com.android.p2pflowernet.project.utils;


import com.android.p2pflowernet.project.entity.GoodsAttrBean;

import java.io.File;

/**
 * @描述:数据存储工具类
 * @创建人：zhangpeisen
 * @创建时间：2017/11/27 下午12:34
 * @修改人：zhangpeisen
 * @修改时间：2017/11/27 下午12:34
 * @修改备注：
 * @throws
 */
public class GsAttrsManager {
    private static GsAttrsManager userInstance = null;
    private String fileName = "hfw_gsattr.bat";
    private GoodsAttrBean goodsAttrBean;

    public static GsAttrsManager getInstance() {
        if (userInstance == null) {
            userInstance = new GsAttrsManager();
        }
        return userInstance;
    }

    private GsAttrsManager() {
        Object data = ObjectUtils.loadObjectData(FileUtils.InsidePath + File.separator + fileName);
        if (data != null)
            goodsAttrBean = (GoodsAttrBean) data;
    }


    public void saveData(GoodsAttrBean data) {
        ObjectUtils.saveObjectData(data, FileUtils.InsidePath + File.separator + fileName);
        goodsAttrBean = data;
    }

    public GoodsAttrBean getData() {
        if (goodsAttrBean == null) {
            Object data = ObjectUtils.loadObjectData(FileUtils.InsidePath + File.separator + fileName);
            if (data != null)
                goodsAttrBean = (GoodsAttrBean) data;
        }
        return goodsAttrBean;
    }

}

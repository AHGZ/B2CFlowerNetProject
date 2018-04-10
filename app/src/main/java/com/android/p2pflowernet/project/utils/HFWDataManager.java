package com.android.p2pflowernet.project.utils;


import com.android.p2pflowernet.project.entity.DataBean;

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
public class HFWDataManager {
    private static HFWDataManager userInstance = null;
    private String fileName = "hfw_scendcity.bat";
    private DataBean dataBean;

    public static HFWDataManager getInstance() {
        if (userInstance == null) {
            userInstance = new HFWDataManager();
        }
        return userInstance;
    }

    private HFWDataManager() {
        Object data = ObjectUtils.loadObjectData(FileUtils.InsidePath + File.separator + fileName);
        if (data != null)
            dataBean = (DataBean) data;
    }


    public void saveData(DataBean data) {
        ObjectUtils.saveObjectData(data, FileUtils.InsidePath + File.separator + fileName);
        dataBean = data;
    }

    public DataBean getData() {
        if (dataBean == null) {
            Object data = ObjectUtils.loadObjectData(FileUtils.InsidePath + File.separator + fileName);
            if (data != null)
                dataBean = (DataBean) data;
        }
        return dataBean;
    }

}

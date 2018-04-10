package com.android.p2pflowernet.project.o2omain.fragment.refund;

import com.android.p2pflowernet.project.entity.ImgDataBean;

import java.io.File;
import java.util.List;

/**
 * Created by zhangkun on 2018/1/24.
 */

public interface IRefundView {

    void onError(String s);

    void showDialog();

    void hideDialog();

    void setUploadImgSuccess(ImgDataBean data);

    String getType();

    List<File> getfileList();

    String getOrderNumber();//订单编号

    String getReason();//退款理由

    String getExplain();//退款说明

    String getImgPath();//商品拍照

    String getAmount();//退款金额

    void onSuccess(String s);

}

package com.android.p2pflowernet.project.view.fragments.mine.orderflow.refund;

import com.android.p2pflowernet.project.entity.ImgDataBean;

import java.io.File;
import java.util.List;

/**
 * Created by caishen on 2017/11/17.
 * by--申请退款
 */

public interface IApplyforRefundView {
    // id
    String getOgid();

    // 订单编号
    String getOrderNum();

    // 是否退货 是否退货（0：不退，1退）
    String getIsreturn();

    // 退款金额
    String getRefundmoney();

    // 退款原因
    String getReason();

    // 退款说明
    String getExplain();

    // 退款图片
    String getImgpath();

    void showDialog();

    void hideDialog();

    void onError(String errorMsg);

    void onSuccess(String message);

    String getType();

    List<File> getfileList();


    void setUploadImgSuccess(ImgDataBean data);

}

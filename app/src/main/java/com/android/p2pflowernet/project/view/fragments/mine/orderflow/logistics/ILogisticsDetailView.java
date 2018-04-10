package com.android.p2pflowernet.project.view.fragments.mine.orderflow.logistics;

import com.android.p2pflowernet.project.entity.LogisticsDetailBean;

/**
 * Created by caishen on 2017/11/20.
 * by--查看物流信息
 */

public interface ILogisticsDetailView {


    // 订单编号
    String getOrderNum();

    // 物流编号
    String getWaybillnum();

    // 物流公司ID
    String getExpressId();

    // 物流状态 0-在途 1-已揽件 2-货物寄送过程出了问题 3-签收，收件人已签收 4：退签，发件人已经签收 5-同城派件中 6-退回途中
    String getStatus();

    void showDialog();

    void hideDialog();

    void onError(String errorMsg);

    void onSuccess(String message);

    // 物流详情
    void onSuccess(LogisticsDetailBean logisticsDetailBean);
}

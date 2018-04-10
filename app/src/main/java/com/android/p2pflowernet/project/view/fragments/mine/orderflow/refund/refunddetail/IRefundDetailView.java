package com.android.p2pflowernet.project.view.fragments.mine.orderflow.refund.refunddetail;

import com.android.p2pflowernet.project.entity.RefundDetailBean;

/**
 * @描述: 各种订单详情视图层
 * @创建人：zhangpeisen
 * @创建时间：2017/12/16 上午9:31
 * @修改人：zhangpeisen
 * @修改时间：2017/12/16 上午9:31
 * @修改备注：
 * @throws
 */
public interface IRefundDetailView {
    // id
    String getRefundid();

    void showDialog();

    void hideDialog();

    void onError(String errorMsg);

    void onSuccess(String message);

    void onCancelSuccess(String message);

    void onSuccess(RefundDetailBean refundDetailBean);
}

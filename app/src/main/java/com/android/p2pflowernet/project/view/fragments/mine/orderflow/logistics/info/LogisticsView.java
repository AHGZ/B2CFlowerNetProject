package com.android.p2pflowernet.project.view.fragments.mine.orderflow.logistics.info;

/**
 * @描述: 完善物流信息
 * @创建人：zhangpeisen
 * @创建时间：2017/12/22 下午3:36
 * @修改人：zhangpeisen
 * @修改时间：2017/12/22 下午3:36
 * @修改备注：
 * @throws
 */
public interface LogisticsView {

    // 厂家ID
    String getManufacId();

    // 退款表id
    String getRefundid();

    //物流单号
    String getWaybillnum();

    // 物流名称
    String getExpressname();

    void showDialog();

    void hideDialog();

    void onError(String errorMsg);

    void onSuccess(String message);
}

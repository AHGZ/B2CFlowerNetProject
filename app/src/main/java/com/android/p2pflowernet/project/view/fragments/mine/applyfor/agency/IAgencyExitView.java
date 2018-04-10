package com.android.p2pflowernet.project.view.fragments.mine.applyfor.agency;

/**
 * @描述: 退出代理人资质视图层
 * @创建人：zhangpeisen
 * @创建时间：2017/11/22 下午5:12
 * @修改人：zhangpeisen
 * @修改时间：2017/11/22 下午5:12
 * @修改备注：
 * @throws
 */
public interface IAgencyExitView {
    // 申请状态，0-申请中（未购买资质） 1-待审核（已购买资质）* 2-退出待审核 6-排队中 7-（排队中）撤销申请 8-退出成功，解除代理人身份 9-申请成功，成为代理人 10-申请
    String getState();

    void showDialog();

    void hideDialog();

    void onError(String message);

    void onSuccess(String message);

}

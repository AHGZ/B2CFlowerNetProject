package com.android.p2pflowernet.project.view.fragments.mine.applyfor.agency.applyqueque;

import com.android.p2pflowernet.project.entity.AgentQuereBean;

/**
 * @描述: 申请排队功能视图层
 * @创建人：zhangpeisen
 * @创建时间：2017/11/22 下午5:12
 * @修改人：zhangpeisen
 * @修改时间：2017/11/22 下午5:12
 * @修改备注：
 * @throws
 */
public interface IApplyQueueView {
    String getRegion();

    void showDialog();

    void hideDialog();

    void onSuccessData(AgentQuereBean data);

    void onError(String message);
}

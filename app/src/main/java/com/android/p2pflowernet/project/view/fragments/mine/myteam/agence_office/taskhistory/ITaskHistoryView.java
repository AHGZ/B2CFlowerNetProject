package com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office.taskhistory;

import com.android.p2pflowernet.project.entity.AgentHistoryBean;
import com.android.p2pflowernet.project.entity.CloudOfficeHistoryBean;

/**
 * @描述: 任务历史(代理人, 云工)View层
 * @创建人：zhangpeisen
 * @创建时间：2017/11/30 下午3:17
 * @修改人：zhangpeisen
 * @修改时间：2017/11/30 下午3:17
 * @修改备注：
 * @throws
 */
public interface ITaskHistoryView {
    // 获取年
    String getYear();

    // 获取月
    String getMonth();

    void showDialog();

    void hideDialog();

    void onError(String errorMsg);

    void onSuccess(String message);

    void onSuccess(CloudOfficeHistoryBean cloudOfficeHistoryBean);

    void onSuccess(AgentHistoryBean agentHistoryBean);

}

package com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office.audit;

import com.android.p2pflowernet.project.entity.AuditHistoryBean;

/**
 * Created by caishen on 2017/12/1.
 * by--审核历史
 */

public interface IAuditHistoryView {
    String getRegion();

    // 获取年
    String getYear();

    // 获取月
    String getMonth();

    void showDialog();

    void hideDialog();

    void onError(String errorMsg);

    void onSuccess(String message);

    void onSuccess(AuditHistoryBean auditHistoryBean);
}

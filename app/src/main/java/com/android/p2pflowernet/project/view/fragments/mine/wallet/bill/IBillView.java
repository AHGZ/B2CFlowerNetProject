package com.android.p2pflowernet.project.view.fragments.mine.wallet.bill;

import com.android.p2pflowernet.project.entity.BillAllBean;

/**
 * Created by caishen on 2017/11/11.
 * by--账单视图
 */

public interface IBillView {
    // 分页页数
    String getPage();

    // 获取年
    String getYear();

    // 获取月
    String getMonth();

    void showDialog();

    void hideDialog();

    void onError(String errorMsg);

    void onSuccess(String message);

    void onSuccessData(BillAllBean data);

}

package com.android.p2pflowernet.project.view.fragments.mine.waitevaluated;

import com.android.p2pflowernet.project.entity.WaitEvaluatedBean;

/**
 * Created by caishen on 2017/12/14.
 * by--
 */

public interface IWaitEvaluateView {
    void onError(String s);

    void showDialog();

    int getPage();

    void hideDialog();

    void SuccessWaitEva(WaitEvaluatedBean data);
}

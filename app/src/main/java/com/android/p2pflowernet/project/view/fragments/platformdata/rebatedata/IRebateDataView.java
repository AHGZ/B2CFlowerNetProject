package com.android.p2pflowernet.project.view.fragments.platformdata.rebatedata;

import com.android.p2pflowernet.project.entity.ProfitBean;

/**
 * Created by zhangkun on 2018/1/30.
 */

public interface IRebateDataView {

    void onError(String s);

    void showDialog();

    void hideDialog();

    void onSuccess(ProfitBean data);

}

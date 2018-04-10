package com.android.p2pflowernet.project.view.fragments.platformdata.awarddata;

import com.android.p2pflowernet.project.entity.BusinessBean;

/**
 * Created by zhangkun on 2018/1/31.
 */

public interface IAwardDataView {

    void onError(String s);

    void showDialog();

    void hideDialog();

    void onSuccess(BusinessBean data);

}

package com.android.p2pflowernet.project.view.fragments.platformdata.regiondata;

import com.android.p2pflowernet.project.entity.RoleBean;

/**
 * Created by zhangkun on 2018/1/31.
 */

public interface IRegionRoleDataView {

    void onError(String s);

    void showDialog();

    void hideDialog();

    void onSuccess(RoleBean data);
}

package com.android.p2pflowernet.project.view.fragments.platformdata.roleformdata;

import com.android.p2pflowernet.project.entity.RoleFormBean;

/**
 * Created by zhangkun on 2018/1/31.
 */

public interface IRoleFormDataView {

    void onError(String s);

    void showDialog();

    void hideDialog();

    void onSuccess(RoleFormBean data);


}

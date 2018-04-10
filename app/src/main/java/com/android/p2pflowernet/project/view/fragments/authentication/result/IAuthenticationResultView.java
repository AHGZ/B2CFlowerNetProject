package com.android.p2pflowernet.project.view.fragments.authentication.result;

import com.android.p2pflowernet.project.entity.IdEntityBean;

/**
 * Created by zhangkun on 2018/3/12.
 */

public interface IAuthenticationResultView {
    void onError(String errorMsg);

    void onSuccess(IdEntityBean data);

    void showDialog();

    void hideDialog();

}

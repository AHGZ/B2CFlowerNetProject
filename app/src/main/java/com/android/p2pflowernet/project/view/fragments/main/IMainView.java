package com.android.p2pflowernet.project.view.fragments.main;

import com.android.p2pflowernet.project.entity.VersionInfo;

/**
 * author: zhangpeisen
 * created on: 2017/7/25 上午10:21
 * description:
 */
public interface IMainView {
    void showDialog();

    void hideDialog();

    void onError(String errorMsg);

    void onSuccess(String message);

    void onSuccess(VersionInfo versionInfo);
}

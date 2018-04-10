package com.android.p2pflowernet.project.view.fragments.mine.applyfor.manufac;

/**
 * Created by caishen on 2018/1/29.
 * by--
 */

interface IManufacView {
    void onError(String s);

    String getapply_name();

    String getapply_phone();

    String apply_email();

    void hideDiaglog();

    void successData(String data);

    void showDialog();
}

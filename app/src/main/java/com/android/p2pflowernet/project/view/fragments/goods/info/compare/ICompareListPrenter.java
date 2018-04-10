package com.android.p2pflowernet.project.view.fragments.goods.info.compare;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.CompareListBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2018/1/29.
 * by--
 */

public class ICompareListPrenter extends IPresenter<ICompareListView> {

    private final CompareListModel compareListModel;

    @Override
    protected void cancel() {

        compareListModel.cancel();
    }

    public ICompareListPrenter() {

        compareListModel = new CompareListModel();
    }

    //比价列表
    public void compares() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String specId = getView().getSpecId();
        if (TextUtils.isEmpty(specId)) {
            return;
        }
        getView().showDialog();

        compareListModel.compares(specId, new IModelImpl<ApiResponse<CompareListBean>, CompareListBean>() {
            @Override
            protected void onSuccess(CompareListBean data, String message) {
                getView().hideDialog();
                getView().successGsInfo(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<CompareListBean>> data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onError(message);
            }

            @Override
            protected void onSuccess() {

            }
        });
    }
}

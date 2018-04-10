package com.android.p2pflowernet.project.o2omain.fragment.index.takeout;

import com.android.p2pflowernet.project.entity.O2oHomeBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.o2omain.fragment.O2oModel;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by heguozhong on 2017/12/28/028.
 * 外卖逻辑层
 */

public class ITakeOutPresenter extends IPresenter<ITakeOutView> {

    private final O2oModel o2oModel;

    @Override
    protected void cancel() {

        o2oModel.cancel();
    }

    public ITakeOutPresenter() {

        o2oModel = new O2oModel();
    }

    //获取外卖首页数据
    public void getTakeOut() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String state = getView().getstate();
        if (state.equals("")) {
            return;
        }

        String latitude = getView().getlatitude();//纬度
        String longitude = getView().getlongitude();//经度

        int pages = getView().getpages();
        String sreen = getView().getsreen();

        getView().showDialog();
        o2oModel.getO2oHome(state, pages, sreen, latitude, longitude, new IModelImpl<ApiResponse<O2oHomeBean>, O2oHomeBean>() {
            @Override
            protected void onSuccess(O2oHomeBean data, String message) {
                getView().hideDialog();
                getView().onSuccessData(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<O2oHomeBean>> data, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onError(message);
            }

            @Override
            protected void onSuccess() {
                getView().hideDialog();
            }
        });
    }

    //获取搜索关键字数据
    public void getSerchTakeOut() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String state = getView().getstate();
        if (state.equals("")) {
            return;
        }

        String latitude = getView().getlatitude();//纬度
        String longitude = getView().getlongitude();//经度
        String searchKey = getView().getSearchKey();//搜索关键字

        int pages = getView().getpages();
        String sreen = getView().getsreen();

        getView().showDialog();
        o2oModel.getSearchO2oHome(searchKey, state, pages, sreen, latitude, longitude,
                new IModelImpl<ApiResponse<O2oHomeBean>, O2oHomeBean>() {
            @Override
            protected void onSuccess(O2oHomeBean data, String message) {
                getView().hideDialog();
                getView().onSuccessSearch(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<O2oHomeBean>> data, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onError(message);
            }

            @Override
            protected void onSuccess() {
                getView().hideDialog();
            }
        });
    }
}

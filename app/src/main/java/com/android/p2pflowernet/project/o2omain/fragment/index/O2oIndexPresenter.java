package com.android.p2pflowernet.project.o2omain.fragment.index;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.O2oHomeBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.o2omain.fragment.O2oModel;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * @描述: O2o首页逻辑处理类
 * @创建人：zhangpeisen
 * @创建时间：2017/10/18 上午10:18
 * @修改人：zhangpeisen
 * @修改时间：2017/10/18 上午10:18
 * @修改备注：
 * @throws
 */
public class O2oIndexPresenter extends IPresenter<O2oIndexView> {
    O2oModel o2oModel;

    public O2oIndexPresenter() {

        o2oModel = new O2oModel();
    }


    @Override
    protected void cancel() {
        o2oModel.cancel();
    }

    //获取首页数据
    public void getO2oHome() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            if (getView() != null) {
                getView().onError("网络信号弱,请稍后重试");
            }
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

        if (getView() != null) {
            getView().showDialog();
        }
        o2oModel.getO2oHome(state, pages, sreen, latitude, longitude, new IModelImpl<ApiResponse<O2oHomeBean>, O2oHomeBean>() {
            @Override
            protected void onSuccess(O2oHomeBean data, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().onSuccessData(data);
                }
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<O2oHomeBean>> data, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                }
            }

            @Override
            protected void onFailure(String code, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().onError(message);
                }
            }

            @Override
            protected void onSuccess() {
                if (getView() != null) {
                    getView().hideDialog();
                }
            }
        });
    }

    //搜搜关键字数据
    public void getSearchO2oHome() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            if (getView() != null) {
                getView().onError("网络信号弱,请稍后重试");
            }
            return;
        }


        String state = getView().getstate();
        if (state.equals("")) {
            return;
        }

        String latitude = getView().getlatitude();//纬度
        String longitude = getView().getlongitude();//经度
        String searchKey = getView().getSearchKey();
        if (TextUtils.isEmpty(searchKey)) {
            return;
        }

        int pages = getView().getpages();
        String sreen = getView().getsreen();

        if (getView() != null) {
            getView().showDialog();
        }
        o2oModel.getSearchO2oHome(searchKey, state, pages, sreen, latitude, longitude, new IModelImpl<ApiResponse<O2oHomeBean>, O2oHomeBean>() {
            @Override
            protected void onSuccess(O2oHomeBean data, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().onSuccessSearch(data);
                }
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<O2oHomeBean>> data, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                }
            }

            @Override
            protected void onFailure(String code, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().onError(message);
                }
            }

            @Override
            protected void onSuccess() {
                if (getView() != null) {
                    getView().hideDialog();
                }
            }
        });
    }
}

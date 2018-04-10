package com.android.p2pflowernet.project.view.fragments.citylist;

import com.android.p2pflowernet.project.mvp.IPresenter;

/**
 * Created by caishen on 2017/10/19.
 * by--城市列表的逻辑层
 */

public class ICityListPrenter extends IPresenter<ICityListView> {

//    CityDataModel cityDataModel;
//
//    public ICityListPrenter() {
//        cityDataModel = new CityDataModel();
//    }
//
//    public void getcitydata() {
//
//        cityDataModel.getCityDatas(new IModelImpl<ApiResponse<String>, String>() {
//            @Override
//            protected void onSuccess(String data) {
//
//                Log.e("TAG", "data==" + data);
//
//            }
//
//            @Override
//            protected void onFailure(String code, String message) {
//
//                Log.e("TAG", "code==" + code + "message==" + message);
//            }
//
//            @Override
//            protected void onSuccess() {
//
//            }
//        });
//    }

    @Override
    protected void cancel() {
    }
}

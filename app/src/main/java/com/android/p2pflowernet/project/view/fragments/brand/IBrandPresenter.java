package com.android.p2pflowernet.project.view.fragments.brand;

import com.android.p2pflowernet.project.entity.BrandClassBean;
import com.android.p2pflowernet.project.entity.BrandScendBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/10/16.
 * by--品牌网络处理
 */

public class IBrandPresenter extends IPresenter<IBrandView> {
    BrandModel brandModel;

    public IBrandPresenter() {

        brandModel = new BrandModel();
    }

    //商品分类一级
    public void Gflists() {
        if (getView() != null) {
            getView().showDialog();
        }
        brandModel.firstGoodsList(new IModelImpl<ApiResponse<BrandClassBean>, BrandClassBean>() {
            @Override
            protected void onSuccess(BrandClassBean data, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().onSuccess(data);
                }
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<BrandClassBean>> data, String message) {
                if (getView() !=null) {
                    getView().hideDialog();
                    getView().onSuccess(message);
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

            }
        });
    }


    //商品分类二级
    public void Gslists() {

        String scendCateid = getView().getCateid();
        if (getView() != null) {
            getView().showDialog();
        }
        brandModel.scendGoodsList(scendCateid, new IModelImpl<ApiResponse<BrandScendBean>, BrandScendBean>() {
            @Override
            protected void onSuccess(BrandScendBean data, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().onSuccess(data);
                }
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<BrandScendBean>> data, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().onSuccess(message);
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

            }
        });
    }



    @Override
    protected void cancel() {
        brandModel.cancel();
    }
}

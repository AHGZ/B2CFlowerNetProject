package com.android.p2pflowernet.project.o2omain.fragment.storedetail.search;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.GoPayBean;
import com.android.p2pflowernet.project.entity.O2oGoodsInfoBean;
import com.android.p2pflowernet.project.entity.SearchStoreBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.o2omain.fragment.O2oModel;
import com.android.p2pflowernet.project.o2omain.fragment.o2oorderlist.o2oorderdetail.IO2oOrderDetaiModel;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2018/2/3.
 * by--
 */

class ISearchStorePrenter extends IPresenter<ISearchStoreView> {

    private final SearchStoreModel searchStoreModel;
    private final O2oModel o2oModel;
    private final IO2oOrderDetaiModel io2oOrderDetaiModel;

    @Override
    protected void cancel() {

        searchStoreModel.cancel();
        o2oModel.cancel();
        io2oOrderDetaiModel.cancel();
    }

    public ISearchStorePrenter() {

        searchStoreModel = new SearchStoreModel();
        o2oModel = new O2oModel();
        io2oOrderDetaiModel = new IO2oOrderDetaiModel();
    }

    //搜索店铺内商品的数据列表
    public void searchStore() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String merchId = getView().merchId();
        String longitude = getView().longitude();
        String latitude = getView().latitude();
        String searchKey = getView().searchKey();

        if (TextUtils.isEmpty(searchKey)) {
            getView().onError("请输入搜索的商品");
            return;
        }

        if (TextUtils.isEmpty(merchId) && merchId.equals("")) {
            return;
        }

        getView().showDialog();
        searchStoreModel.searchStore(searchKey, merchId, longitude, latitude,
                new IModelImpl<ApiResponse<SearchStoreBean>, SearchStoreBean>() {
                    @Override
                    protected void onSuccess(SearchStoreBean data, String message) {
                        getView().hideDialog();
                        getView().onSuccess(data);
                    }

                    @Override
                    protected void onSuccess(ArrayList<ApiResponse<SearchStoreBean>> data, String message) {
                        getView().hideDialog();
                    }

                    @Override
                    protected void onFailure(String code, String message) {
                        getView().hideDialog();
                        if (code.equals("-2")) {//商家休息中
                            getView().onErrorRest(message);
                        } else {
                            getView().onError(message);
                        }
                    }

                    @Override
                    protected void onSuccess() {
                        getView().hideDialog();
                    }
                });
    }

    //去结算
    public void goPay() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String merchId = getView().merchId();
        String longitude = getView().longitude();
        String latitude = getView().latitude();

        if (TextUtils.isEmpty(merchId) && merchId.equals("")) {
            return;
        }

        getView().showDialog();
        o2oModel.o2ogopay(merchId, longitude, latitude, new IModelImpl<ApiResponse<GoPayBean>, GoPayBean>() {
            @Override
            protected void onSuccess(GoPayBean data, String message) {
                getView().hideDialog();
                getView().onSuccessGoPay(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<GoPayBean>> data, String message) {
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

    //查看商品详情
    public void get_goods_info() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String merId = getView().merchId();
        if (TextUtils.isEmpty(merId)) {
            return;
        }
        String goodId = getView().getGoodId();
        if (TextUtils.isEmpty(goodId)) {
            return;
        }

        int page = getView().getpage();

        //加载进度条
        getView().showDialog();
        io2oOrderDetaiModel.get_goods_info(merId, goodId, page, new IModelImpl<ApiResponse<O2oGoodsInfoBean>, O2oGoodsInfoBean>() {
            @Override
            protected void onSuccess(O2oGoodsInfoBean data, String message) {
                getView().hideDialog();
                getView().onSuccessData(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<O2oGoodsInfoBean>> data, String message) {
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

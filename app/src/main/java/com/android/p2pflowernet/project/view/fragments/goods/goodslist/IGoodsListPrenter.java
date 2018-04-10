package com.android.p2pflowernet.project.view.fragments.goods.goodslist;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.AllSortBean;
import com.android.p2pflowernet.project.entity.BrandSortBean;
import com.android.p2pflowernet.project.entity.ClassifBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.brand.BrandModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * @描述:商品列表的逻辑层
 * @创建人：zhangpeisen
 * @创建时间：2017/12/5 下午7:26
 * @修改人：zhangpeisen
 * @修改时间：2017/12/5 下午7:26
 * @修改备注：
 * @throws
 */
public class IGoodsListPrenter extends IPresenter<IGoodLIstView> {
    BrandModel brandModel;

    public IGoodsListPrenter() {
        brandModel = new BrandModel();
    }

    /**
     * @throws
     * @描述: 商品三级列表
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/5 下午7:34
     * @修改人 zhangpeisen
     * @修改时间 2017/12/5 下午7:34
     * @修改备注
     */
    public void GdThreeLists() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String scendCateid = getView().getScendCateid();
        String isNew = getView().getIsNew();
        String brand = getView().getBrand();
        String zt = getView().getZt();
        String salePrice = getView().getSalePrice();
        String order = getView().getOrder();
        if (TextUtils.isEmpty(scendCateid)) {
            return;
        }
        if (TextUtils.isEmpty(isNew)) {
            return;
        }
        if (TextUtils.isEmpty(brand)) {
            return;
        }
        if (TextUtils.isEmpty(zt)) {
            return;
        }
        if (TextUtils.isEmpty(salePrice)) {
            return;
        }

        String lowPrice = getView().getLowPrice();
        String heightPrice = getView().getHeightPrice();
        if (TextUtils.isEmpty(lowPrice) && !heightPrice.isEmpty()) {
            getView().onError("请填写最低价");
            return;
        } else if (heightPrice.isEmpty() && !TextUtils.isEmpty(lowPrice)) {
            getView().onError("请填写最高价");
            return;
        }

        if (TextUtils.isEmpty(order)) {
            return;
        }
        int pages = getView().getPages();
        if (pages == -1) {
            return;
        }
        getView().showDialog();
        brandModel.screenlist(scendCateid, isNew, brand, zt, salePrice, pages, order, new IModelImpl<ApiResponse<ClassifBean>, ClassifBean>() {
            @Override
            protected void onSuccess(ClassifBean data, String message) {
                getView().hideDialog();
                getView().onSuccessClassif(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ClassifBean>> data, String message) {
                getView().hideDialog();
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

    /**
     * @throws
     * @描述: 点击筛选按钮
     * @方法名:moreBrand
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/12 下午1:11
     * @修改人 zhangpeisen
     * @修改时间 2017/12/12 下午1:11
     * @修改备注
     */
    public void clickFilter() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String cateid = getView().getCateid();
        if (TextUtils.isEmpty(cateid)) {
            return;
        }
        getView().showDialog();
        brandModel.clickfilter(cateid, new IModelImpl<ApiResponse<BrandSortBean>, BrandSortBean>() {
            @Override
            protected void onSuccess(BrandSortBean data, String message) {
                getView().hideDialog();
                getView().onBrandSortSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<BrandSortBean>> data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
            }

            @Override
            protected void onSuccess() {

            }
        });
    }

    /**
     * @throws
     * @描述: 品牌筛选列表
     * @方法名:moreBrand
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/12 下午1:11
     * @修改人 zhangpeisen
     * @修改时间 2017/12/12 下午1:11
     * @修改备注
     */
    public void moreBrand() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String cateid = getView().getCateid();
        if (TextUtils.isEmpty(cateid)) {
            return;
        }
        getView().showDialog();
        brandModel.morebrand(cateid, new IModelImpl<ApiResponse<AllSortBean>, AllSortBean>() {
            @Override
            protected void onSuccess(AllSortBean data, String message) {
                getView().hideDialog();
                getView().onAllBrandSortSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<AllSortBean>> data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
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

    //获取搜索关键词数据
    public void getSerChList() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String scendCateid = getView().getScendCateid();
        String isNew = getView().getIsNew();
        String brand = getView().getBrand();
        String zt = getView().getZt();
        String salePrice = getView().getSalePrice();
        String order = getView().getOrder();
        String searchName = getView().getSearchName();

        int pages = getView().getPages();
        if (pages == -1) {
            return;
        }

        getView().showDialog();
        brandModel.getSerChList(searchName, scendCateid, isNew, brand, zt, salePrice, pages, order,
                new IModelImpl<ApiResponse<ClassifBean>, ClassifBean>() {
                    @Override
                    protected void onSuccess(ClassifBean data, String message) {
                        getView().hideDialog();
                        getView().onSuccessClassif(data);
                    }

                    @Override
                    protected void onSuccess(ArrayList<ApiResponse<ClassifBean>> data, String message) {
                        getView().hideDialog();
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

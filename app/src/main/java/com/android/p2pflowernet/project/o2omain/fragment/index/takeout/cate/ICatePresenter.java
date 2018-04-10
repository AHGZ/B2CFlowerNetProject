package com.android.p2pflowernet.project.o2omain.fragment.index.takeout.cate;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.TakeCateThreeBean;
import com.android.p2pflowernet.project.entity.TakeCateTwoBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by heguozhong on 2018/1/2/002.
 * 美食逻辑层
 */

public class ICatePresenter extends IPresenter<ICateView> {
    private ICateModel iCateModel;

    public ICatePresenter() {
        iCateModel = new ICateModel();
    }

    @Override
    protected void cancel() {
        iCateModel.cancel();
    }

    //获取外卖美食三级
    public void getTakeoutCateThree() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        // 收货地址的纬度
        String latitude = getView().latitude();
        // 收货地址的经度
        String longitude = getView().longitude();

        //state:1为综合排序，2为销量，3为距离最近，4为起送金额最低，5为配送费最低
        //pages:页数
        //sreen:筛选条件 1,2,3,4,5,6这样显示方式，1为平台配送2到店自提3为商家配送4为免费配送5为支持开发票6为0元起送
        //cate_id:分类id
        //level:级别

        //排序状态
        String state = getView().getstate();
        //页数
        String pages = getView().getpages();
        //筛选条件
        String sreen = getView().getsreen();
        //分类ID
        int cateid = getView().getcateid();
        //级别
        int level = getView().getlevel();

        if (TextUtils.isEmpty(latitude) && latitude.equals("")) {
            return;
        }
        if (TextUtils.isEmpty(longitude) && longitude.equals("")) {
            return;
        }
        if (TextUtils.isEmpty(state) && state.equals("")) {
            return;
        }
        if (TextUtils.isEmpty(pages) && pages.equals("")) {
            return;
        }
//        if (TextUtils.isEmpty(sreen) && sreen.equals("")) {
//            return;
//        }
        if (cateid==0) {
            return;
        }
        if (level==0) {
            return;
        }

        getView().showDialog();
        iCateModel.getTakeoutCateThree(state, pages, sreen,latitude,longitude, cateid, level, new IModelImpl<ApiResponse<TakeCateThreeBean>, TakeCateThreeBean>() {
            @Override
            protected void onSuccess(TakeCateThreeBean data, String message) {
                getView().hideDialog();
                getView().onSuccessThree(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<TakeCateThreeBean>> data, String message) {
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

    //获取外卖美食二级
    public void getTakeoutCateTwo() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        //美食ID
        int cateid = getView().getzcateid();

        if (cateid==0) {
            return;
        }

        getView().showDialog();
        iCateModel.getTakeoutCateTwo(cateid,new IModelImpl<ApiResponse<TakeCateTwoBean>, TakeCateTwoBean>() {
            @Override
            protected void onSuccess(TakeCateTwoBean data, String message) {
                getView().hideDialog();
                getView().onSuccessTwo(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<TakeCateTwoBean>> data, String message) {
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

    //搜索外卖美食
    public void searchTakeoutCate() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        // 收货地址的纬度
        String latitude = getView().latitude();
        // 收货地址的经度
        String longitude = getView().longitude();

        //state:1为综合排序，2为销量，3为距离最近，4为起送金额最低，5为配送费最低
        //pages:页数
        //sreen:筛选条件 1,2,3,4,5,6这样显示方式，1为平台配送2到店自提3为商家配送4为免费配送5为支持开发票6为0元起送
        //cate_id:分类id
        //level:级别

        //排序状态
        String state = getView().getstate();
        //页数
        String pages = getView().getpages();
        //筛选条件
        String sreen = getView().getsreen();
        //分类ID
        int cateid = getView().getcateid();
        //级别
        int level = getView().getlevel();
        //搜所名称
        String name = getView().getName();

        if (TextUtils.isEmpty(latitude) && latitude.equals("")) {
            return;
        }
        if (TextUtils.isEmpty(longitude) && longitude.equals("")) {
            return;
        }
        if (TextUtils.isEmpty(state) && state.equals("")) {
            return;
        }
        if (TextUtils.isEmpty(pages) && pages.equals("")) {
            return;
        }
//        if (TextUtils.isEmpty(sreen) && sreen.equals("")) {
//            return;
//        }
        if (cateid==0) {
            return;
        }
        if (level==0) {
            return;
        }

        getView().showDialog();
        iCateModel.searchTakeoutCate(state, pages, sreen,latitude,longitude, cateid, level,name, new IModelImpl<ApiResponse<TakeCateThreeBean>, TakeCateThreeBean>() {
            @Override
            protected void onSuccess(TakeCateThreeBean data, String message) {
                getView().hideDialog();
                getView().onSuccessThree(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<TakeCateThreeBean>> data, String message) {
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

package com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying;

import com.android.p2pflowernet.project.entity.GroupHomeBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by heguozhong on 2017/12/28/028.
 * 团购优惠逻辑层
 */

public class IGroupBuyingPresenter extends IPresenter<IGroupBuyingView>{
    private IGroupBuyingModel iGroupBuyingModel;

    public IGroupBuyingPresenter( ) {
        iGroupBuyingModel = new IGroupBuyingModel();
    }

    @Override
    protected void cancel() {
        iGroupBuyingModel.cancel();
    }

    //获取团购首页数据
    public void getGroupHomeData(){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        int pages = getView().getPages();
        int zt = getView().getZt();
        int yuyue = getView().getYuyue();
        int holiday_usable = getView().getHolidayUsable();
        int weekend_usable =getView().getWeekendUsable();
        int is_new = getView().isNew();
        int liebie = getView().getLiebie();
        int district_id = getView().getDistrictId();
        int state = getView().getState();
        String city = getView().getCity();
        String latitude = getView().getlatitude();
        String longitude = getView().getlongitude();
        String fit_type = getView().getFitType();

        getView().showDialog();
        iGroupBuyingModel.getGroupHomeData(pages, zt, state, city, latitude, longitude, yuyue,
                holiday_usable, weekend_usable, is_new, fit_type, liebie, district_id, new IModelImpl<ApiResponse<GroupHomeBean>, GroupHomeBean>() {
            @Override
            protected void onSuccess(GroupHomeBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<GroupHomeBean>> data, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onSuccess() {

            }
        });
    }

    //搜索团购首页数据
    public void searchGroupHomeData(){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        int pages = getView().getPages();
        int zt = getView().getZt();
        int yuyue = getView().getYuyue();
        int holiday_usable = getView().getHolidayUsable();
        int weekend_usable =getView().getWeekendUsable();
        int is_new = getView().isNew();
        int liebie = getView().getLiebie();
        int district_id = getView().getDistrictId();
        int state = getView().getState();
        String city = getView().getCity();
        String latitude = getView().getlatitude();
        String longitude = getView().getlongitude();
        String fit_type = getView().getFitType();
        String name = getView().getName();

        getView().showDialog();
        iGroupBuyingModel.searchGroupHomeData(pages, zt, state, city, latitude, longitude, yuyue,
                holiday_usable, weekend_usable, is_new, fit_type, liebie, district_id,name, new IModelImpl<ApiResponse<GroupHomeBean>, GroupHomeBean>() {
                    @Override
                    protected void onSuccess(GroupHomeBean data, String message) {
                        getView().hideDialog();
                        getView().onSuccess(data);
                    }

                    @Override
                    protected void onSuccess(ArrayList<ApiResponse<GroupHomeBean>> data, String message) {
                        getView().hideDialog();
                    }

                    @Override
                    protected void onFailure(String code, String message) {
                        getView().hideDialog();
                    }

                    @Override
                    protected void onSuccess() {

                    }
                });
    }
}

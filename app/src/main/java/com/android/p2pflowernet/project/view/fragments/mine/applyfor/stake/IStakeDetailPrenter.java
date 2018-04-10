package com.android.p2pflowernet.project.view.fragments.mine.applyfor.stake;

import com.android.p2pflowernet.project.entity.StakeBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/23.
 * by--入股明细的逻辑层
 */

public class IStakeDetailPrenter extends IPresenter<IStakeDetailView>{

    private final StakeDetailModel stakeDetailModel;

    @Override
    protected void cancel() {

    }

    public IStakeDetailPrenter() {

        stakeDetailModel = new StakeDetailModel();

    }

    //入股明细
    public void getStake() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        int page = getView().getPage();

        stakeDetailModel.getStake(page,new IModelImpl<ApiResponse<StakeBean>, StakeBean>() {
            @Override
            protected void onSuccess(StakeBean data,String message) {
                getView().hideDialog();
                getView().setSuccessStake(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<StakeBean>> data,String message) {

                getView().hideDialog();
            }

            @Override
            protected void onFailure(String code, String message) {

                getView().onError(message);
            }

            @Override
            protected void onSuccess() {

            }
        });

    }
}

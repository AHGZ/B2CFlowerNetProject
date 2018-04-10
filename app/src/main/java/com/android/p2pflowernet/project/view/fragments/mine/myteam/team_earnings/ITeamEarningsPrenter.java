package com.android.p2pflowernet.project.view.fragments.mine.myteam.team_earnings;

import com.android.p2pflowernet.project.entity.MyTeamProfitBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.mine.myteam.team_earnings.contribution.ContributionRankModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/28.
 * by--团队收益的逻辑层
 */

public class ITeamEarningsPrenter extends IPresenter<ITeamEarningsView> {

    private final ContributionRankModel teamRankingModel;

    @Override
    protected void cancel() {

        teamRankingModel.cancel();
    }

    public ITeamEarningsPrenter() {

        teamRankingModel = new ContributionRankModel();
    }

    //获取团队收益
    public void getTeamProfit() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        getView().showDialog();

        teamRankingModel.getTeamProfit(new IModelImpl<ApiResponse<MyTeamProfitBean>, MyTeamProfitBean>() {
            @Override
            protected void onSuccess(MyTeamProfitBean data, String message) {
                getView().hideDialog();
                getView().successTeamProfit(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<MyTeamProfitBean>> data, String message) {
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

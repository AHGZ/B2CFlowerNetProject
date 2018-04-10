package com.android.p2pflowernet.project.view.fragments.mine.myteam.team_earnings.contribution;

import com.android.p2pflowernet.project.entity.ContriRankBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/12/19.
 * by--
 */

public class IContributionRankPrenter extends IPresenter<IContributionRankView> {

    private final ContributionRankModel teamRankingModel;

    @Override
    protected void cancel() {

        teamRankingModel.cancel();
    }

    public IContributionRankPrenter() {

        teamRankingModel = new ContributionRankModel();
    }

    //获取贡献榜数据列表
    public void contrirank() {

        String state = getView().getState();
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        getView().showDialog();
        teamRankingModel.getContriRank(state, new IModelImpl<ApiResponse<ContriRankBean>, ContriRankBean>() {
            @Override
            protected void onSuccess(ContriRankBean data, String message) {
                getView().hideDialog();
                getView().successRanks(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ContriRankBean>> data, String message) {
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

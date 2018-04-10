package com.android.p2pflowernet.project.view.fragments.mine.applyfor.agency;

import android.text.TextUtils;

import com.android.p2pflowernet.project.mvp.IPresenter;

/**
 * @描述:退出代理人资质逻辑层
 * @创建人：zhangpeisen
 * @创建时间：2017/11/22 下午5:13
 * @修改人：zhangpeisen
 * @修改时间：2017/11/22 下午5:13
 * @修改备注：
 * @throws
 */
public class IAgencyExitPrenter extends IPresenter<IAgencyExitView> {
    IAgentModel iAgentModel;

    public IAgencyExitPrenter() {
        iAgentModel = new IAgentModel();
    }

    /**
     * @throws
     * @描述:
     * @方法名: 设置申请状态
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/11/29 下午5:15
     * @修改人 zhangpeisen
     * @修改时间 2017/11/29 下午5:15
     * @修改备注
     */
    public void exitAgency() {
        String state = getView().getState();
        if (state.equals("") && TextUtils.isEmpty(state)) {
            return;
        }
        getView().showDialog();
    }

    @Override
    protected void cancel() {
        iAgentModel.cancel();
    }
}

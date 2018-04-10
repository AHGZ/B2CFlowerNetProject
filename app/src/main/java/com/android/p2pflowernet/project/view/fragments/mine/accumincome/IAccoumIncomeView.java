package com.android.p2pflowernet.project.view.fragments.mine.accumincome;

import com.android.p2pflowernet.project.entity.AcmIncomBean;

/**
 * @描述: 累计收益视图层
 * @创建人：zhangpeisen
 * @创建时间：2017/11/22 下午5:12
 * @修改人：zhangpeisen
 * @修改时间：2017/11/22 下午5:12
 * @修改备注：
 * @throws
 */
public interface IAccoumIncomeView {

    void showDialog();

    void hideDialog();

    void onError(String s);

    void onSuccess(AcmIncomBean acmIncomBean);
}

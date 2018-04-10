package com.android.p2pflowernet.project.view.fragments.mine.wallet.withdraw;


import com.android.p2pflowernet.project.entity.BankInfoBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.WithDrawInfoBean;

/**
 * @描述:提现视图模块
 * @创建人：zhangpeisen
 * @创建时间：2017/11/20 下午7:07
 * @修改人：zhangpeisen
 * @修改时间：2017/11/20 下午7:07
 * @修改备注：
 * @throws
 */
public interface IWithDrawView {
    // 支付密码
    String getPwd();

    // 余额
    String getBalance();

    void showDialog();

    void hideDialog();

    void onError(String errorMsg);

    void onSuccess(String message);

    void onCheckPayPwdSuccess(String message);

    // 查看提现信息
    void onSuccess(WithDrawInfoBean withDrawInfoBean);

    // 是否设置过支付密码
    void onSuccessCheck(CheckPwdBean data);

    void onSuccessBankList(BankInfoBean data, String message);
}

package com.android.p2pflowernet.project.o2omain.fragment.storedetail;

import com.android.p2pflowernet.project.entity.GoPayBean;
import com.android.p2pflowernet.project.entity.MerchantBean;
import com.android.p2pflowernet.project.entity.O2oIndexBean;

/**
 * author: zhangpeisen
 * created on: 2017/7/25 上午10:21
 * description:店铺详情
 */
public interface StoreDetailView {

    String merchId();

    String longitude();

    String latitude();

    void onSuccess(O2oIndexBean o2oIndexBean);

    void onSuccess(MerchantBean merchantBean);

    void onError(String message);

    void showDialog();

    void hideDialog();

    void onSuccessGoPay(GoPayBean data);
}

package com.android.p2pflowernet.project.o2omain.fragment.storedetail.search;

import com.android.p2pflowernet.project.entity.GoPayBean;
import com.android.p2pflowernet.project.entity.O2oGoodsInfoBean;
import com.android.p2pflowernet.project.entity.SearchStoreBean;

/**
 * Created by caishen on 2018/2/3.
 * by--
 */

interface ISearchStoreView {
    String merchId();

    String longitude();

    String latitude();

    void onError(String s);

    void showDialog();

    void hideDialog();

    void onSuccess(SearchStoreBean data);

    String searchKey();

    void onSuccessGoPay(GoPayBean data);

    void onErrorRest(String message);

    String getGoodId();

    int getpage();

    void onSuccessData(O2oGoodsInfoBean data);
}

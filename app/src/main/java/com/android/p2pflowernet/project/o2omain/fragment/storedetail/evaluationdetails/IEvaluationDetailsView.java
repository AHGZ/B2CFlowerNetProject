package com.android.p2pflowernet.project.o2omain.fragment.storedetail.evaluationdetails;

import com.android.p2pflowernet.project.entity.ShopEvaluationBean;

/**
 * Created by heguozhong on 2018/1/5/005.
 * 评论详情视图层
 */

public interface IEvaluationDetailsView {
    void onError(String errorMsg);

    void onSuccess(ShopEvaluationBean shopEvaluationBean);

    void onSuccess(String message);

    void showDialog();

    void hideDialog();
}

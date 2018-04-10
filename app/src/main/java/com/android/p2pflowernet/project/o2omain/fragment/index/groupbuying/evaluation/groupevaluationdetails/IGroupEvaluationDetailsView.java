package com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying.evaluation.groupevaluationdetails;

import com.android.p2pflowernet.project.entity.GroupEvaluationBean;

/**
 * Created by heguozhong on 2018/1/5/005.
 * 评论详情视图层
 */

public interface IGroupEvaluationDetailsView {
    void onError(String errorMsg);

    void onSuccess(GroupEvaluationBean groupEvaluationBean);

    void onSuccess(String message);

    void showDialog();

    void hideDialog();
}

package com.android.p2pflowernet.project.o2omain.fragment.index.takeout.cate;

import com.android.p2pflowernet.project.entity.TakeCateThreeBean;
import com.android.p2pflowernet.project.entity.TakeCateTwoBean;

/**
 * Created by heguozhong on 2018/1/2/002.
 * 美食视图层
 */

public interface ICateView {
    void onError(String errorMsg);

    void onSuccessTwo(TakeCateTwoBean takeCateTwoBean);

    void onSuccessThree(TakeCateThreeBean takeCateThreeBean);

    void onSuccess(String message);

    void showDialog();

    void hideDialog();
    // 收货地址的纬度
    String latitude();

    // 收货地址的经度
    String longitude();

    String getpages();

    String getsreen();

    String getstate();

    int getcateid();

    int getzcateid();

    int getlevel();

    String getName();
}

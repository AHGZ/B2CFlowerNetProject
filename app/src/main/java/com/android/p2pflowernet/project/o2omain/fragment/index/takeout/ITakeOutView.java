package com.android.p2pflowernet.project.o2omain.fragment.index.takeout;

import com.android.p2pflowernet.project.entity.O2oHomeBean;

/**
 * Created by heguozhong on 2017/12/28/028.
 * 外卖视图层
 */

public interface ITakeOutView {
    void onError(String s);

    String getstate();

    String getlatitude();

    String getlongitude();

    int getpages();

    String getsreen();

    void showDialog();

    void hideDialog();

    void onSuccessData(O2oHomeBean data);

    String getSearchKey();

    void onSuccessSearch(O2oHomeBean data);
}

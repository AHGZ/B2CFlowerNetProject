package com.android.p2pflowernet.project.o2omain.fragment.index;

import com.android.p2pflowernet.project.entity.O2oHomeBean;
import com.android.p2pflowernet.project.entity.O2oIndexBean;

/**
 * @描述: 首页逻辑视图类
 * @创建人：zhangpeisen
 * @创建时间：2017/10/18 上午10:19
 * @修改人：zhangpeisen
 * @修改时间：2017/10/18 上午10:19
 * @修改备注：
 * @throws
 */

public interface O2oIndexView {

    String merchId();


    void onSuccess(O2oIndexBean o2oIndexBean);

    void onError(String message);

    void showDialog();

    void hideDialog();

    String getstate();

    int getpages();

    String getsreen();

    void onSuccessData(O2oHomeBean data);

    String getlatitude();//纬度

    String getlongitude();//经度

    String getSearchKey();

    void onSuccessSearch(O2oHomeBean data);
}

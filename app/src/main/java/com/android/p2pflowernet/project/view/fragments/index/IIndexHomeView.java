package com.android.p2pflowernet.project.view.fragments.index;

import com.android.p2pflowernet.project.entity.IndexHomeBean;

/**
 * @描述: 首页逻辑视图类
 * @创建人：zhangpeisen
 * @创建时间：2017/10/18 上午10:19
 * @修改人：zhangpeisen
 * @修改时间：2017/10/18 上午10:19
 * @修改备注：
 * @throws
 */

public interface IIndexHomeView {


    void onError(String s);

    void showDialog();

    void hideDialog();

    void onSuccessIndex(IndexHomeBean data);

    void onSuccess(String message);

}

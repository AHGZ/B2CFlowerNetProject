package com.android.p2pflowernet.project.mvp;

/**
 * @描述: 视图层接口类
 * @创建人：zhangpeisen
 * @创建时间：2017/10/9 下午12:44
 * @修改人：zhangpeisen
 * @修改时间：2017/10/9 下午12:44
 * @修改备注：
 * @throws
 */
public interface IView<T> {

    T createPresenter();
}

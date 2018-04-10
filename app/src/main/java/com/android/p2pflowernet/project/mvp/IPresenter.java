package com.android.p2pflowernet.project.mvp;

import java.lang.ref.WeakReference;

/**
 * @描述:控制层基类
 * @创建人：zhangpeisen
 * @创建时间：2017/10/9 下午12:43
 * @修改人：zhangpeisen
 * @修改时间：2017/10/9 下午12:43
 * @修改备注：
 * @throws
 */
public abstract class IPresenter<T> {

    public WeakReference<T> viewRef;

    public void attach(T view) {
        viewRef = new WeakReference<>(view);
    }

    public void detach(T view) {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    public boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    protected T getView() {
        return viewRef.get();
    }


    protected abstract void cancel();

}

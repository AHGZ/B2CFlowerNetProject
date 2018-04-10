package com.android.p2pflowernet.project.mvp;

/**
 * Created by zhangpeisen on 17/7/19.
 */

public interface IModel {


    interface OnCompleteListener<T> {

        void onComplete(T data);

        void onError(String code, String message);
    }

    void cancel();
}

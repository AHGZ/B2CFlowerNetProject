package com.android.p2pflowernet.project.base;

import com.android.p2pflowernet.project.mvp.IModel;

import retrofit2.Response;

/**
 * Created by caishen on 2017/11/16.
 * by--
 */

public abstract class DataNull implements IModel.OnCompleteListener<Response> {

    @Override
    public void onError(String code, String message) {
        onFailure(code, message);
    }

    @Override
    public void onComplete(Response data) {

//        onSuccess(data.toString());
    }

    protected abstract void onFailure(String code, String message);

//    protected abstract void onSuccess(String json);
}

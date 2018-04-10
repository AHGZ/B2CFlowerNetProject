package com.android.p2pflowernet.project.mvp;

import android.text.TextUtils;

import com.android.p2pflowernet.project.utils.LogUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * @描述: 数据响应层基类
 * @创建人：zhangpeisen
 * @创建时间：2017/10/9 下午12:44
 * @修改人：zhangpeisen
 * @修改时间：2017/10/9 下午12:44
 * @修改备注：
 * @throws
 */
public abstract class IModelImpl<T, K> implements IModel.OnCompleteListener<T> {

    @Override
    public void onError(String code, String message) {
        onFailure(code, message);
    }

    @Override
    public void onComplete(T data) {
        LogUtils.e("data == ", data.toString());
        if (data instanceof ApiResponse) {
            String code = ((ApiResponse) data).getCode();
            String message = ((ApiResponse) data).getMessage();
            if (code.equals("0")) {
                K obj = (K) ((ApiResponse) data).getData();
                ArrayList<T> arrayList = ((ApiResponse) data).getDatalist();
                if (obj == null) {
                    onSuccess(obj, message);
                    return;
                }
                if (obj instanceof Object) {
                    if (obj == null) {
                        onSuccess(obj, message);
                        return;
                    }
                    onSuccess(obj, message);
                }
                if (arrayList == null) {
                    return;
                }
                onSuccess(arrayList, message);
            } else {
                if (!TextUtils.isEmpty(message) && !message.equals("null")) {
                    onFailure(code, message);
                }
            }
        }
    }

    protected abstract void onSuccess(K data, String message);

    protected abstract void onSuccess(ArrayList<T> data, String message);

    protected abstract void onFailure(String code, String message);

    protected abstract void onSuccess();
}

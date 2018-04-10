package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;


/**
 * author: zhangpeisen
 * created on: 2017/11/21 下午5:35
 * description:
 */
public interface UpLoadImgService {

    /**
     * 上传多张图片
     */
    @Multipart
    @POST(ApiUrlConstant.UPLOAD_IMG)
    Observable<ApiResponse<ImgDataBean>> upLoadImg(@Header("sign") String sign, @Header("token") String token, @Part("type") String type, @PartMap() Map<String, RequestBody> parts);


    @Multipart
    @POST(ApiUrlConstant.UPLOAD_IMG)
    Observable<ApiResponse<ImgDataBean>> upLoadImgs(@Header("sign") String sign, @Header("token") String token,@FieldMap HashMap<String, String> options, @Part("description") RequestBody description);


//    /**
//     * 上传多张图片
//     */
//    @Multipart
//    @POST(ApiUrlConstant.UPLOAD_IMG)
//    Call<Result<String>> upLoadImg(@Header("sign") String sign, @QueryMap Map<String, String> options, @Part List<MultipartBody.Part> parts);

}

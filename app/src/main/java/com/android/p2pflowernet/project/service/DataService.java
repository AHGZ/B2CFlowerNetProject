package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.BigDataMapBean;
import com.android.p2pflowernet.project.entity.BusinessBean;
import com.android.p2pflowernet.project.entity.ProfitBean;
import com.android.p2pflowernet.project.entity.RoleBean;
import com.android.p2pflowernet.project.entity.RoleFormBean;
import com.rxy.netlib.http.ApiResponse;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by zhangkun on 2018/2/6.
 * 大数据展示相关
 */

public interface DataService {

    //地区角色组成
    @POST(ApiUrlConstant.ROLEDATA)
    Observable<ApiResponse<RoleBean>> getRoleData(@Header("sign") String sign, @Header("token") String token);

    //角色组成
    @POST(ApiUrlConstant.FORMDATA)
    Observable<ApiResponse<RoleFormBean>> getRoleFormData(@Header("sign") String sign, @Header("token") String token);

    //业务奖励数据接口
    @POST(ApiUrlConstant.BUSINESSDATA)
    Observable<ApiResponse<BusinessBean>> getBusinessData(@Header("sign") String sign, @Header("token") String token);

    //业务奖励数据接口
    @POST(ApiUrlConstant.PROFITDATA)
    Observable<ApiResponse<ProfitBean>> getProfitData(@Header("sign") String sign, @Header("token") String token);

    //大数据地图展示数据接口
    @POST(ApiUrlConstant.MAPDATA)
    Observable<ApiResponse<BigDataMapBean>> getMapData(@Header("sign") String sign, @Header("token") String token);
}

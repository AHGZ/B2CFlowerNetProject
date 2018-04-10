package com.android.p2pflowernet.project.view.fragments.mine.applyfor.agency;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.AgentInfoBean;
import com.android.p2pflowernet.project.entity.AgentQuereBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.AgentService;
import com.android.p2pflowernet.project.utils.SignUtil;
import com.rxy.netlib.http.ApiResponse;
import com.rxy.netlib.http.RetrofitUtils;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * @描述: 代理人信息逻辑层处理
 * @创建人：zhangpeisen
 * @创建时间：2017/11/27 上午10:10
 * @修改人：zhangpeisen
 * @修改时间：2017/11/27 上午10:10
 * @修改备注：
 * @throws
 */
public class IAgentModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;


    public IAgentModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * @param id_number 身份证号码
     * @param cfi_path  手持身份证正面照片路径
     * @param cbi_path  手持身份证反面照片路径
     * @param st_path   代理人申请签名图片路径
     * @param ws_path   工作场地照片路径
     * @param tp_path   团队照片路径
     * @param created   申请时间戳-秒
     * @throws
     * @描述:
     * @方法名:添加代理申请信息 realname 真实姓名
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/11/27 上午10:20
     * @修改人 zhangpeisen
     * @修改时间 2017/11/27 上午10:20
     * @修改备注
     */
    public void agentadd(String realname, String id_number, String cfi_path, String cbi_path,
                         String ws_path, String st_path, String tp_path, String created, String order_num,
                         final IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> map = new HashMap<>();
        // 生成签名
        map.put("realname", realname);
        map.put("id_number", id_number);
        map.put("cfi_path", cfi_path);
        map.put("cbi_path", cbi_path);
        map.put("st_path", st_path);
        map.put("ws_path", ws_path);
        map.put("tp_path", tp_path);
        map.put("created", created);
        map.put("order_num", order_num);

        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("realname", realname);
        param.put("id_number", id_number);
        param.put("cfi_path", cfi_path);
        param.put("cbi_path", cbi_path);
        param.put("st_path", st_path);
        param.put("ws_path", ws_path);
        param.put("tp_path", tp_path);
        param.put("created", String.valueOf(created));
        param.put("order_num", order_num);
        String sign = SignUtil.getInstance().getSign(map);
        AgentService service = retrofit.create(AgentService.class);
        Disposable subscribe = service.agentapplyadd(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<String>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<String> checkMobileBeanApiResponse) throws Exception {
                        listener.onComplete(checkMobileBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }

    /**
     * @throws
     * @描述:
     * @方法名: 获取代理人信息(new)
     * state
     * 申请状态，0-申请中（未购买资质） 1-待审核（已购买资质）
     * 2-退出待审核 6-排队中 7-（排队中）撤销申请 8-退出成功，解除代理人身份 9-申请成功，成为代理人 10-申请
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/11/27 上午10:39
     * @修改人 zhangpeisen
     * @修改时间 2017/11/27 上午10:39
     * @修改备注
     */
    public void agentalist(String state, final IModelImpl<ApiResponse<AgentInfoBean>, AgentInfoBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        // 生成签名
        map.put("id", state);
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("id", state);
        String sign = SignUtil.getInstance().getSign(map);
        AgentService service = retrofit.create(AgentService.class);
        Disposable subscribe = service.agentapplyaalist(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<AgentInfoBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<AgentInfoBean> checkMobileBeanApiResponse) throws Exception {
                        listener.onComplete(checkMobileBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }

    /**
     * @param state 申请状态，0-申请中（未购买资质） 1-待审核（已购买资质）
     *              2-退出待审核 6-排队中 7-（排队中）撤销申请 8-退出成功，解除代理人身份 9-申请成功，成为代理人 10-申请
     * @param aa_id 申请编号id
     * @throws
     * @描述:
     * @方法名: 更新代理申请状态(new)
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/11/27 上午10:39
     * @修改人 zhangpeisen
     * @修改时间 2017/11/27 上午10:39
     * @修改备注
     */
    public void agenttate(int aa_id, int state, final IModelImpl<ApiResponse<String>, String> listener) {
        HashMap<String, String> map = new HashMap<>();
        // 生成签名
        map.put("state", String.valueOf(state));
        map.put("aa_id", String.valueOf(aa_id));
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("state", String.valueOf(state));
        param.put("aa_id", String.valueOf(aa_id));
        String sign = SignUtil.getInstance().getSign(map);
        AgentService service = retrofit.create(AgentService.class);
        Disposable subscribe = service.agentapplychangestate(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<String>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<String> checkMobileBeanApiResponse) throws Exception {
                        listener.onComplete(checkMobileBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }

    /**
     * @param region //所在地区id
     * @throws
     * @描述: 申请排队代理人信息列表(new)
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/11/27 上午10:59
     * @修改人 zhangpeisen
     * @修改时间 2017/11/27 上午10:59
     * @修改备注
     */
    public void agentqueue(String region, final IModelImpl<ApiResponse<AgentQuereBean>, AgentQuereBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        // 生成签名
        map.put("region", String.valueOf(region));
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("region", region);
        String sign = SignUtil.getInstance().getSign(map);
        AgentService service = retrofit.create(AgentService.class);
        Disposable subscribe = service.getagentqueuelist(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<AgentQuereBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<AgentQuereBean> checkMobileBeanApiResponse) throws Exception {
                        listener.onComplete(checkMobileBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }


    @Override
    public void cancel() {

        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    //修改代理人信息
    public void uptaAgent(String agentId, String realname, String idnumber, String cfipath, String cbipath,
                          String ws_path, String teamImgPath, String signatureImgPath1,
                          String applyDate, String order_num, final IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> map = new HashMap<>();
        // 生成签名
        map.put("realname", realname);
        map.put("agent_id", agentId);
        map.put("id_number", idnumber);
        map.put("cfi_path", cfipath);
        map.put("cbi_path", cbipath);
        map.put("st_path", signatureImgPath1);
        map.put("ws_path", ws_path);
        map.put("tp_path", teamImgPath);
        map.put("created", applyDate);
        map.put("order_num", order_num);

        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("realname", realname);
        param.put("agent_id", agentId);
        param.put("id_number", idnumber);
        param.put("cfi_path", cfipath);
        param.put("cbi_path", cbipath);
        param.put("st_path", signatureImgPath1);
        param.put("ws_path", ws_path);
        param.put("tp_path", teamImgPath);
        param.put("created", applyDate);
        param.put("order_num", order_num);

        String sign = SignUtil.getInstance().getSign(map);
        AgentService service = retrofit.create(AgentService.class);
        Disposable subscribe = service.agentapplyadd(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<String>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<String> checkMobileBeanApiResponse) throws Exception {
                        listener.onComplete(checkMobileBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }
}

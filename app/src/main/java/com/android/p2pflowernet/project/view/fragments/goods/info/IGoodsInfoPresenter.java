package com.android.p2pflowernet.project.view.fragments.goods.info;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.AddShopBean;
import com.android.p2pflowernet.project.entity.ChangeGsAttrBean;
import com.android.p2pflowernet.project.entity.GoodsAttrBean;
import com.android.p2pflowernet.project.entity.GoodsInfoBean;
import com.android.p2pflowernet.project.entity.GuaranteeBean;
import com.android.p2pflowernet.project.entity.OrderDetailBean;
import com.android.p2pflowernet.project.entity.ProductParamBean;
import com.android.p2pflowernet.project.entity.ShareGoodsBean;
import com.android.p2pflowernet.project.entity.SpecCompareBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.goods.comment.GoodsCommentModel;
import com.android.p2pflowernet.project.view.fragments.goods.detail.GoodsDetailModel;
import com.android.p2pflowernet.project.view.fragments.trade.TradeModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 商品信息逻辑处理类
 * @创建人：zhangpeisen
 * @创建时间：2017/10/18 上午10:18
 * @修改人：zhangpeisen
 * @修改时间：2017/10/18 上午10:18
 * @修改备注：
 * @throws
 */
public class IGoodsInfoPresenter extends IPresenter<IGoodsInfoView> {

    private GoodsCommentModel goodsCommentModel;
    private GoodsInfoModel goodsInfoModel;
    private TradeModel tradeModel;


    private List<GoodsAttrBean.ListsBean> listsbeanlist;
    private final GoodsDetailModel goodsDetailModel;

    public IGoodsInfoPresenter() {

        goodsCommentModel = new GoodsCommentModel();
        goodsInfoModel = new GoodsInfoModel();
        tradeModel = new TradeModel();
        goodsDetailModel = new GoodsDetailModel();
    }


    public List<GoodsAttrBean.ListsBean> getListsbeanlist() {
        return listsbeanlist;
    }

    public void setListsbeanlist(List<GoodsAttrBean.ListsBean> listsbeanlist) {
        this.listsbeanlist = listsbeanlist;
    }

    /**
     * @throws
     * @描述:获取商品评价数据列表
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/6 上午10:01
     * @修改人 zhangpeisen
     * @修改时间 2017/12/6 上午10:01
     * @修改备注
     */
    public void getEveluate() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String goodsId = getView().goodsId();
        int page = getView().getPage();
        if (TextUtils.isEmpty(goodsId)) {
            return;
        }
        if (page == -1) {
            return;
        }
    }

    //初始化基本保障
    public void guarantee() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        String goodsId = getView().goodsId();
        goodsCommentModel.guarantee(goodsId, new IModelImpl<ApiResponse<GuaranteeBean>, GuaranteeBean>() {
            @Override
            protected void onSuccess(GuaranteeBean data, String message) {

                getView().hideDialog();
                getView().onSuccessGuarantee(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<GuaranteeBean>> data, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onError(message);
            }

            @Override
            protected void onSuccess() {

            }
        });
    }

    /**
     * @throws
     * @描述: 获取商品详情
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/6 上午10:01
     * @修改人 zhangpeisen
     * @修改时间 2017/12/6 上午10:01
     * @修改备注
     */
    public void goodsXq() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String goodsId = getView().goodsId();
        if (TextUtils.isEmpty(goodsId)) {
            return;
        }
        getView().showDialog();
        goodsInfoModel.getGoodsXq(goodsId, new IModelImpl<ApiResponse<GoodsInfoBean>, GoodsInfoBean>() {
            @Override
            protected void onSuccess(GoodsInfoBean data, String message) {
                getView().hideDialog();
                getView().successGsInfo(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<GoodsInfoBean>> data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onError(message);
            }

            @Override
            protected void onSuccess() {

            }
        });

    }

    /**
     * @throws
     * @描述:获取商品产品参数
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/6 下午2:21
     * @修改人 zhangpeisen
     * @修改时间 2017/12/6 下午2:21
     * @修改备注
     */
    public void goodsParam() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String goodsId = getView().goodsId();
        if (TextUtils.isEmpty(goodsId)) {
            return;
        }
        getView().showDialog();
        goodsInfoModel.getGoodsParam(goodsId, new IModelImpl<ApiResponse<ProductParamBean>, ProductParamBean>() {
            @Override
            protected void onSuccess(ProductParamBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ProductParamBean>> data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onError(message);
            }

            @Override
            protected void onSuccess() {

            }
        });
    }

    /**
     * @throws
     * @描述:
     * @方法名: 初始化商品产品规格
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/6 下午2:22
     * @修改人 zhangpeisen
     * @修改时间 2017/12/6 下午2:22
     * @修改备注
     */
    public void goodsSpec() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String goodsId = getView().goodsId();
        String sepcId = getView().sepcId();
        if (TextUtils.isEmpty(goodsId)) {
            return;
        }
        if (TextUtils.isEmpty(sepcId)) {
            return;
        }
        getView().showDialog();
        goodsInfoModel.getGoodsSpec(goodsId, sepcId, new IModelImpl<ApiResponse<GoodsAttrBean>, GoodsAttrBean>() {
            @Override
            protected void onSuccess(GoodsAttrBean data, String message) {
                getView().hideDialog();
                getView().successGoodsSpec(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<GoodsAttrBean>> data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onError(message);
            }

            @Override
            protected void onSuccess() {

            }
        });
    }

    // 修改商品规格
    public void goodsSpecInfo() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String goodsId = getView().goodsId();
        if (TextUtils.isEmpty(goodsId)) {
            return;
        }
        String optId1 = getView().optId1();
        String optId2 = getView().optId2();
        String optId3 = getView().optId3();
        getView().showDialog();
        goodsInfoModel.getGoodsSpecInfo(goodsId, optId1, optId2, optId3,
                new IModelImpl<ApiResponse<ChangeGsAttrBean>, ChangeGsAttrBean>() {
            @Override
            protected void onSuccess(ChangeGsAttrBean data, String message) {
                getView().hideDialog();
                getView().changeGoodsSpec(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ChangeGsAttrBean>> data, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onError(message);
            }

            @Override
            protected void onSuccess() {

            }
        });
    }

    /**
     * @throws
     * @描述:
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/7 下午5:25
     * @修改人 zhangpeisen
     * @修改时间 2017/12/7 下午5:25
     * @修改备注
     */
    public void addShopCars() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String goodsId = getView().goodsId();
        String sepcId = getView().sepcId();
        String shopCarcount = getView().ShopCarcount();
        if (TextUtils.isEmpty(goodsId)) {
            return;
        }
        if (TextUtils.isEmpty(sepcId)) {
            return;
        }
        if (TextUtils.isEmpty(shopCarcount)) {
            return;
        }
        getView().showDialog();
        tradeModel.addShopCars(goodsId, sepcId, shopCarcount, new IModelImpl<ApiResponse<AddShopBean>, AddShopBean>() {
            @Override
            protected void onSuccess(AddShopBean data, String message) {
                getView().hideDialog();
                getView().onAddShopCarSuccess(message);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<AddShopBean>> data, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onError(message);
            }

            @Override
            protected void onSuccess() {
                getView().hideDialog();
            }
        });
    }

    @Override
    protected void cancel() {

        goodsCommentModel.cancel();
        goodsInfoModel.cancel();
        tradeModel.cancel();

    }

    /**
     * 确认订单
     */
    public void orderSel() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String specId = getView().sepcId();
        String count = getView().ShopCarcount();
        String source = getView().getSource();//来源
        String select = getView().getSelect();//是否全选

        goodsDetailModel.orderSel(specId, count, source, select,
                new IModelImpl<ApiResponse<OrderDetailBean>, OrderDetailBean>() {
                    @Override
                    protected void onSuccess(OrderDetailBean data, String message) {
                        getView().hideDialog();
                        getView().SuccessOrder(data);
                    }

                    @Override
                    protected void onSuccess(ArrayList<ApiResponse<OrderDetailBean>> data, String message) {
                        getView().hideDialog();
                    }

                    @Override
                    protected void onFailure(String code, String message) {
                        getView().hideDialog();
                        getView().onError(message);
                    }

                    @Override
                    protected void onSuccess() {

                    }
                });
    }

    //分享商品详情
    public void getShareGoods() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String goodId = getView().getGoodId();
        if (TextUtils.isEmpty(goodId)) {
            return;
        }

        goodsDetailModel.getShareGoods(goodId, new IModelImpl<ApiResponse<ShareGoodsBean>, ShareGoodsBean>() {

            @Override
            protected void onSuccess(ShareGoodsBean data, String message) {
                getView().hideDialog();
                getView().SuccessShare(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ShareGoodsBean>> data, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onError(message);
            }

            @Override
            protected void onSuccess() {

            }
        });
    }

    //根据规格更改比价
    public void compSpec() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String sepcId = getView().sepcId();
        if (TextUtils.isEmpty(sepcId)) {
            return;
        }

        goodsDetailModel.compSpec(sepcId, new IModelImpl<ApiResponse<SpecCompareBean>, SpecCompareBean>() {

            @Override
            protected void onSuccess(SpecCompareBean data, String message) {
                getView().hideDialog();
                getView().Successcompare(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<SpecCompareBean>> data, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onError(message);
            }

            @Override
            protected void onSuccess() {
                getView().hideDialog();
            }
        });
    }
}

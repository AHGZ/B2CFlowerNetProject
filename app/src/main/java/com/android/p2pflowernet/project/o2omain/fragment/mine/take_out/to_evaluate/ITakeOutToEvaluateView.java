package com.android.p2pflowernet.project.o2omain.fragment.mine.take_out.to_evaluate;

import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.android.p2pflowernet.project.entity.TakeOutToEvaluateGoodsBean;

import java.io.File;
import java.util.List;

/**
 * Created by zhangkun on 2018/1/20.
 */

public interface ITakeOutToEvaluateView {

    void onError(String string);

    void onSuccess(String s);

    String getType();

    List<File> getfileList();

    void showDialog();

    void hideDialog();

    void setUploadImgSuccess(ImgDataBean imgDataBean);

    String orderNumber();//订单编号

    int distribType();//配送类型 1-平台配送 2-商家配送 3-到店自提

    int distribId();//配送平台ID

    int distribScore();//配送评星

    int merchScore();//店铺评星

    String shopContent();//店铺评价内容

    String shopImgs();//店铺评价图片

    String goodsContent();//商品评价内容

    int isAnonymous();//是否匿名

    void onSuccessData(TakeOutToEvaluateGoodsBean data);

}

package com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out.to_evaluate;

import com.android.p2pflowernet.project.entity.ImgDataBean;

import java.io.File;
import java.util.List;

/**
 * Created by zhangkun on 2018/1/22.
 */

public interface IGroupTakeOutToEvaluateView {
    int merchId();//商家ID
    int groupId();//团购ID
    int orderNumber();//订单号
    int score();//评分
    int isAnonymous();//是否匿名 0-不匿名 1-匿名
    String toEvaluateContext();//评价内容
    String imgs();//店铺评价配图

    void hideDialog();
    void showDialog();
    void onError(String s);
    void onSuccess(String s);
    void setUploadImgSuccess(ImgDataBean data);
    String getType();
    List<File> getfileList();

}

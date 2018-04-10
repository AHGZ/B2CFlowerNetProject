package com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying;

import com.android.p2pflowernet.project.entity.GroupHomeBean;

/**
 * Created by heguozhong on 2017/12/28/028.
 * 团购优惠视图层
 */

public interface IGroupBuyingView {

    void onError(String string);

    void onSuccess(GroupHomeBean data);

    void onSearchSuccess(GroupHomeBean data);

    void showDialog();

    void hideDialog();

    int getPages();//分页数

    int getZt();//状态值 1

    int getState();//状态 默认是1，1为评分最高

    String getCity();//城市名称

    String getlatitude();//纬度

    String getlongitude();//经度

    int getYuyue();//是否需要预约 0为不支持1为支持

    int getHolidayUsable();//节假日可用

    int getWeekendUsable();//周末可用

    int isNew();//是否新品

    String getFitType();//适用类型

    int getLiebie();//商家分类id

    int getDistrictId();//区域id

    String getName();//搜索名称
}

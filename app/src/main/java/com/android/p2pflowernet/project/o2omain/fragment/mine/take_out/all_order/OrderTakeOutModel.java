package com.android.p2pflowernet.project.o2omain.fragment.mine.take_out.all_order;

import com.android.p2pflowernet.project.entity.OrderTakeOutBean;
import com.android.p2pflowernet.project.mvp.IModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caishen on 2018/1/5.
 * by--
 */

public class OrderTakeOutModel implements IModel {


    @Override
    public void cancel() {

    }

    public static List setLIst() {

        List<OrderTakeOutBean> list = new ArrayList<>();


        for (int i = 0; i < 6; i++) {

            OrderTakeOutBean orderTakeOutBean = new OrderTakeOutBean();
            orderTakeOutBean.setState(String.valueOf(i));
            orderTakeOutBean.setKilltime("2018-1-5 15:06:00");
            orderTakeOutBean.setHuafan(String.valueOf(10 + i));
            orderTakeOutBean.setFilepath("http://p1.ifengimg.com/a/2017_49/9cb090873b8a2ec_size73_w429_h600.jpg");
            orderTakeOutBean.setName("红烧张培森第" + i + "道");
            orderTakeOutBean.setSaleprice(String.valueOf(i + 32.5));

            if (i == 0 || i == 3) {

                List<OrderTakeOutBean.ListsBean> list1 = new ArrayList<>();
                for (int i1 = 0; i1 < 1; i1++) {

                    OrderTakeOutBean.ListsBean listsBean = new OrderTakeOutBean.ListsBean();
                    listsBean.setName("迷你肠" + i);
                    listsBean.setHuafan(String.valueOf(i + 2));
                    listsBean.setNum(String.valueOf(i + 1));
                    listsBean.setPrice(String.valueOf(i + 8.5));
                    listsBean.setSaleprice(String.valueOf(i * 1.2));
                    list1.add(listsBean);

                }
                orderTakeOutBean.setList(list1);

            } else {

                List<OrderTakeOutBean.ListsBean> list1 = new ArrayList<>();
                for (int i1 = 0; i1 < 8; i1++) {

                    OrderTakeOutBean.ListsBean listsBean = new OrderTakeOutBean.ListsBean();
                    listsBean.setName("迷你肠" + i);
                    listsBean.setHuafan(String.valueOf(i + 2));
                    listsBean.setNum(String.valueOf(i + 1));
                    listsBean.setPrice(String.valueOf(i + 8.5));
                    listsBean.setSaleprice(String.valueOf(i * 1.2));
                    list1.add(listsBean);

                }

                orderTakeOutBean.setList(list1);
            }

            list.add(orderTakeOutBean);
        }

        return list;
    }
}

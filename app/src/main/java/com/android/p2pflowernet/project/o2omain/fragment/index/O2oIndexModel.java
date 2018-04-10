package com.android.p2pflowernet.project.o2omain.fragment.index;

import com.android.p2pflowernet.project.entity.DistriButionBean;
import com.android.p2pflowernet.project.entity.IndexO2oBean;
import com.android.p2pflowernet.project.mvp.IModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caishen on 2017/12/28.
 * by--
 */

public class O2oIndexModel implements IModel {

    private static List<IndexO2oBean> applyQueueBeanArrayList = new ArrayList<>();

    @Override
    public void cancel() {

    }


    //自定义筛选的数据
    public static List<DistriButionBean.DistriButionsBean> ShopQueue() {

        ArrayList<DistriButionBean.DistriButionsBean> distribution = new ArrayList<>();
        DistriButionBean.DistriButionsBean distriButionBean = new DistriButionBean.DistriButionsBean();
        distriButionBean.setName("配送方式");
        distriButionBean.setId("1");

        DistriButionBean.DistriButionsBean features = new DistriButionBean.DistriButionsBean();
        features.setName("商家特色");
        features.setId("2");


        ArrayList<DistriButionBean.DistriButionsBean.DistriBean> distriBeen = new ArrayList<>();
        for (int i = 0; i < 3; i++) {

            DistriButionBean.DistriButionsBean.DistriBean distriBean = new DistriButionBean.DistriButionsBean.DistriBean();

            if (i == 0) {

                distriBean.setName("平台配送");
                distriBean.setId("1");
                distriBean.setIschoose(false);

            } else if (i == 1) {

                distriBean.setName("到店自提");
                distriBean.setId("2");
                distriBean.setIschoose(false);

            } else {

                distriBean.setName("商家配送");
                distriBean.setId("3");
                distriBean.setIschoose(false);
            }

            distriBeen.add(distriBean);
        }
        distriButionBean.setDistri(distriBeen);


        //商家特色数据
        ArrayList<DistriButionBean.DistriButionsBean.DistriBean> featuresBeen = new ArrayList<>();
        for (int i = 0; i < 3; i++) {

            DistriButionBean.DistriButionsBean.DistriBean distriBean = new DistriButionBean.DistriButionsBean.DistriBean();

            if (i == 0) {

                distriBean.setName("免费配送");
                distriBean.setId("4");
                distriBean.setIschoose(false);

            } else if (i == 1) {

                distriBean.setName("支持开发票");
                distriBean.setId("5");
                distriBean.setIschoose(false);

            } else {

                distriBean.setName("0元起送");
                distriBean.setId("6");
                distriBean.setIschoose(false);
            }

            featuresBeen.add(distriBean);
        }

        features.setDistri(featuresBeen);
        distribution.add(distriButionBean);//配送方式
        distribution.add(features);
        return distribution;
    }
}

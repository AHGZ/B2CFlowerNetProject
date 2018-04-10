package com.android.p2pflowernet.project.view.fragments.mine.orderflow.orderitemdetail;

import com.android.p2pflowernet.project.entity.ApplyQueueBean;
import com.android.p2pflowernet.project.entity.BillBean;
import com.android.p2pflowernet.project.entity.ClassifBean;
import com.android.p2pflowernet.project.entity.EveluateBean;
import com.android.p2pflowernet.project.entity.MerchandiseBean;
import com.android.p2pflowernet.project.mvp.IModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class OrderDetailModel implements IModel {

    //每页的数据量
    private static int size = 5;
    private static int count = 0;
    //全部订单
    private static List<MerchandiseBean> listAll = new ArrayList<>();
    //待付款订单
    private static List<MerchandiseBean> listToPay = new ArrayList<>();
    //待收货订单
    private static List<MerchandiseBean> listReceive = new ArrayList<>();
    //已完成订单
    private static List<MerchandiseBean> listFinish = new ArrayList<>();
    //已取消订单
    private static List<MerchandiseBean> listCancel = new ArrayList<>();

    private static List<BillBean> billBeanList = new ArrayList<>();

    //申请代理人排行榜
    private static List<ApplyQueueBean> applyQueueBeanArrayList = new ArrayList<>();


    //分类筛选
    private static List<ClassifBean> classifBeanArrayList = new ArrayList<>();


    /**
     * 根据标签类别获取订单List
     *
     * @return
     */
    public List<MerchandiseBean> getList(int type) {
        List<MerchandiseBean> list = new ArrayList<>();
        switch (type) {
            case 0:
                list = listAll;
                break;
            case 1:
                list = listToPay;
                break;
            case 2:
                list = listReceive;
                break;
            case 3:
                list = listFinish;
                break;
            case 4:
                list = listCancel;
                break;
        }
        return list;
    }

    /**
     * 根据标签类别添加订单List元素
     */
    public static void addList(MerchandiseBean bean) {
        switch (Integer.parseInt(bean.getOrderState())) {
            case 0:
                listAll.add(bean);
                break;
            case 1:
                listToPay.add(bean);
                break;
            case 2:
                listReceive.add(bean);
                break;
            case 3:
                listFinish.add(bean);
                break;
            case 4:
                listCancel.add(bean);
                break;

        }
        listAll.add(bean);
    }

    /**
     * 刷新时添加随机数据
     */
    public static void refreshList() {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int orderNo = random.nextInt(4) + 1;
            String text = null;
            switch (orderNo) {
                case 1:
                    text = "等待付款";
                    break;
                case 2:
                    text = "等待收货";
                    break;
                case 3:
                    text = "交易完成";
                    break;
                case 4:
                    text = "交易关闭";
                    break;
            }
            MerchandiseBean bean = new MerchandiseBean();
            bean.setOrderState(String.valueOf(orderNo));
            bean.setOrderNo(String.valueOf(++count));
            bean.setGoodsName("经典女戒钻石白金莫桑\n圣诞节可能石戒指东.");
            bean.setBuyCount(random.nextInt(10) + 1 + "");
            bean.setPayTime("花返自营" + i);
            bean.setTruePayAmt(random.nextInt(500) + "");
            addList(bean);
        }
    }

    public static List<BillBean> BillList(String year, String month) {
        for (int i = 0; i < 20; i++) {
            Random random = new Random();
            BillBean billBean = new BillBean();
            billBean.setBilltype("提现" + i);
            billBean.setBilldate(year + "年" + month + "月");
            billBean.setBillamount(random.nextInt(500) + "");
            billBeanList.add(billBean);
        }
        return billBeanList;
    }

    public static List<ApplyQueueBean> ApplyQueue() {
        for (int i = 0; i < 20; i++) {
            ApplyQueueBean applyQueueBean = new ApplyQueueBean();
            applyQueueBean.setNickname("张**");
            applyQueueBean.setMobile("185***7237");
            applyQueueBean.setAmount("￥" + i + "000.00");
            applyQueueBean.setApplydate("2017/10/11");
            applyQueueBeanArrayList.add(applyQueueBean);
        }
        return applyQueueBeanArrayList;
    }

    @Override
    public void cancel() {

    }

    //商品详情评价
    public static List<EveluateBean> CommentUqeue() {

        return null;
    }
}

package com.android.p2pflowernet.project.constant;

/**
 * author: zhangpeisen
 * created on: 2017/11/24 下午4:26
 * description: 花返网权限控制常量类
 * a&~b:     清除标志位b;
 * a|b:      添加标志位b;
 * a&b:      取出标志位b;
 * a^b:      取出a与b的不同部分;
 */
public class HFWPermission {
    // HFW_PARTNER 合伙人
    public static final int HFW_PARTNER = 0x10000000;
    // HFW_AGENCY 代理
    public static final int HFW_AGENCY = 0x20000000;
    // HFW_COUNLDWORKER 云工
    public static final int HFW_COUNLDWORKER = 0x30000000;
    // HFW_MERCHANT 商家
    public static final int HFW_MERCHANT = 0x40000000;
    // 角色判断标示
    public static final int USERTAG = HFW_PARTNER | HFW_AGENCY | HFW_COUNLDWORKER | HFW_MERCHANT;
    // 申请状（合伙人,代理,云工,商家）
    public static final int IS_PARTNER = 0 | 1;
    // HFW_AGENCY 代理
    public static final int IS_AGENCY = 0 | 1;
    // HFW_COUNLDWORKER 云工
    public static final int IS_COUNLDWORKER = 0 | 1;
    // HFW_MERCHANT 商家
    public static final int IS_MERCHANT = 0 | 1;
    /**
     * 订单状态部分
     */
    // 等待付款
    public static final int ODRER_WAITPAY = 0x10000000;
    // 待收货
    public static final int ODRER_GOODSRECEIVED = 0x20000000;
    // 已完成
    public static final int ODRER_FINISH = 0x30000000;
    // 已取消
    public static final int ODRER_CANCEL = 0x40000000;
    // 待发货
    public static final int ODRER_PENDSHIPMENT = 0x50000000;
    // 待评价
    public static final int ODRER_PENDVALUATION = 0x60000000;


}

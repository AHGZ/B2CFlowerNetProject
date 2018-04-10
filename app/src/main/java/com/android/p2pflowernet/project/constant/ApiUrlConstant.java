package com.android.p2pflowernet.project.constant;

/**
 * author: zhangpeisen
 * created on: 2017/10/9 下午3:55
 * description: api接口类
 */
public interface ApiUrlConstant {
    // 测试
    String API_BASE_URL = "http://192.168.1.253:8082/";
    //外网服务器
//    String API_BASE_URL = "http://api.huafanwang.com/";
//        String API_BASE_URL = "http://192.168.1.220/";
    // https
//    String API_BASE_URL = "https://192.168.1.253/";
    // 张仪
//    String API_BASE_URL = "http://192.168.1.157/";
    // 图片地址
    String API_IMG_URL = "http://192.168.1.253:8082/";
    //外网图片地址
//    String API_IMG_URL = "http://api.huafanwang.com/";
//        String API_IMG_URL = "http://192.168.1.220:8080/";
    // 登录
    String HFW_LOGIN = API_BASE_URL + "home/index/login";
    // 注册
    String HFW_REGISTER = API_BASE_URL + "home/index/register";
    // 探测手机号接口
    String HFW_CHECK_MOBILE = API_BASE_URL + "home/index/check_mobile";
    // 获取验证码
    String HFW_SENDMOBILECODE = API_BASE_URL + "home/index/gen_mobile_code";
    // 归属省市
    String GET_PROVINCE_CITY = API_BASE_URL + "home/common/get_province_city";
    //修改个人信息
    String UPDATE_USER_DETAIL_INFO = API_BASE_URL + "home/user_detail/update_user_detail_info";
    //获取个人信息
    String GET_USER_DETAIL = API_BASE_URL + "home/user_detail/get_user_detail";
    // 重置登录密码
    String RESET_ACCOUNT_PWD = API_BASE_URL + "home/index/reset_account_pwd";
    // 设置支付密码
    String ADD_PWD = API_BASE_URL + "home/pay/add_pwd";
    // 验证支付密码
    String HFW_VERIFY_PWD = API_BASE_URL + "home/pay/verify_pwd";
    // 检测用户支付密码
    String HFW_CHECK_PWD = API_BASE_URL + "home/pay/check_pwd";
    //更换手机号
    String CHANGE_PHONE = API_BASE_URL + "home/user/change_phone";
    //修改登录密码
    String UPDATE_USER_PWD = API_BASE_URL + "home/user/update_user_pwd";
    //获取获取三级地址联动列表
    String GET_ALL_REGION = API_BASE_URL + "home/common/get_all_region";
    //获取姓名及身份证
    String GET_IDENTITY_INFO = API_BASE_URL + "home/Apply/get_identity_info";
    //验证姓名和身份证
    String CHECK_IDENTITY = API_BASE_URL + "home/Apply/check_identity";
    //保存个人信息
    String ADD_AUTH_INFO = API_BASE_URL + "home/Apply/add_auth_info";
    //填写保单信息
    String ADD_INSURANCE_INFO = API_BASE_URL + "home/Apply/add_insurance_info";
    //上传图片返回地址
    String UPLOAD_IMG = API_BASE_URL + "home/Upload/upload_img";
    //添加云工身份
    String ADDCLOUD = API_BASE_URL + "home/staff_apply/add";
    //待申请
    String MORE_APPLY = API_BASE_URL + "home/Apply/more_apply";
    //申请历史
    String GET_APPLY_HISTORY = API_BASE_URL + "home/Apply/get_apply_history";
    //1.1.点击设置时
    String GET_USER_SET = API_BASE_URL + "home/user_detail/get_user_set";
    //用户收货地址列表
    String GET_USER_ADDRESS_LIST = API_BASE_URL + "home/user_address/get_user_address_list";
    //增加/修改收货地址
    String ADD_UPDATE = API_BASE_URL + "home/user_address/add_update";
    //删除收货地址
    String DEL_USER_ADDRESS = API_BASE_URL + "home/user_address/del_user_address";
    //用户意见反馈添加
    String ADD_FEEDBACK = API_BASE_URL + "home/feedback/add";
    //用户反馈意见列表
    String GET_FEED_BACK_LIST = API_BASE_URL + "home/feedback/list";
    // 添加商家申请信息
    String HFW_MERCHANT_APPLYADD = API_BASE_URL + "home/merchant_apply/add";
    // 获取商家申请信息
    String HFW_MERCHANT_APPLYAPLIST = API_BASE_URL + "home/merchant_apply/aplist";
    // 添加代理申请信息
    String HFW_AGENT_APPLYADD = API_BASE_URL + "home/agent_apply/add";
    // 获取代理人信息
    String HFW_AGENT_APPLYAALIST = API_BASE_URL + "home/agent_apply/aalist";
    //修改商家信息
    String MERCHANT_UPDATE = API_BASE_URL + "home/merchant_apply/update";
    // 更新代理申请状态
    String HFW_AGENT_APPLYCHANGESTATE = API_BASE_URL + "home/agent_apply/change_state";
    // 申请排队代理人信息列表
    String HFW_GET_AGENT_QUEUE_LIST = API_BASE_URL + "home/agent_apply/get_agent_queue_list";
    //入股明细
    String GET_APPLY_DETAIL = API_BASE_URL + "home/Apply/get_apply_detail";
    //获取云工信息
    String GETCLOUD = API_BASE_URL + "home/staff_apply/list";
    //修改云工信息
    String UPDATELOUD = API_BASE_URL + "home/staff_apply/update";
    // 个人银行卡信息验证
    String HFW_VERIFY_USER_BANKINFO = API_BASE_URL + "home/common/verify_user_bankinfo";
    // 添加认证过的银行卡
    String HFW_CARDINFOADD = API_BASE_URL + "home/user_card_list/add";
    // 银行卡列表
    String HFW_CARD_LIST = API_BASE_URL + "home/user_card_list/list";
    //银行卡解绑
    String DELBANK = API_BASE_URL + "home/user_card_list/del";
    //检测用户支付密码
    String CHECK_PWD = API_BASE_URL + "home/pay/check_pwd";
    // 验证用户支付密码
    String VERIFYPWD = API_BASE_URL + "home/pay/verify_pwd";
    //退出代理资质
    String EXIT_AGENT = API_BASE_URL + "home/agent_apply/exit_agent";
    //商品评价
    String GET_GOODS_COMMENT = API_BASE_URL + "home/goods/get_goods_comment";
    // 商品一级分类
    String HFW_GOODS_CATETJ = API_BASE_URL + "home/Goods/catetj";
    // 商品二级分类
    String HFW_GOOD_CATELIST = API_BASE_URL + "home/Goods/catelist";
    // 把筛选接口和商品三级分类下商品接口合并为筛选接口
    String HFW_SCREENLIST = API_BASE_URL + "home/Goods/screenlist";
    //基本保障
    String GUARANTEE = API_BASE_URL + "home/goods/guarantee";
    // 商品详情
    String HFW_GOODS_XQ = API_BASE_URL + "home/goods/goods_xq";
    // 初始化商品产品规格
    String HFW_GOODS_SPEC = API_BASE_URL + "home/goods/get_goods_spec";
    // 获取商品指定规格信息
    String HFW_GOODS_SPEC_INFO = API_BASE_URL + "home/goods/get_goods_spec_info";
    // 获取商品参数
    String HFW_GOODS_PARAM = API_BASE_URL + "home/goods/get_goods_param";
    //修改支付密码
    String UPDATE_PWD = API_BASE_URL + "home/pay/update_pwd";
    //购物车列表数据
    String CARTLIST = API_BASE_URL + "home/cart/cartlist";
    //添加购物车数据
    String ADDCART = API_BASE_URL + "home/cart/addCart";
    //修改购物车数量
    String EDIT_CART_GOODS = API_BASE_URL + "home/cart/edit_cart_goods";
    // 删除购物车中商品
    String DEL_CART_GOODS = API_BASE_URL + "home/cart/del_cart_goods";
    // 更改多个购物车选中状态
    String CART_GOODS_SEL = API_BASE_URL + "home/cart/cart_goods_sel";
    // 点击筛选按钮
    String HFW_CLICK_FILTER = API_BASE_URL + "home/filter/click_filter";
    // 获取某分类下全部品牌
    String HFW_MORE_BRAND = API_BASE_URL + "home/brand/more_brand";
    // 确认订单
    String ORDER_SEL = API_BASE_URL + "home/order/order_sel";
    // 订单列表
    String HFW_ORDER_LIST = API_BASE_URL + "home/order/get_order_list";
    // 订单详情
    String HFW_ORDER_DETAIL = API_BASE_URL + "home/order/get_order_detail";
    //待评价
    String GET_WAIT_COMMENT = API_BASE_URL + "home/goods_eval/get_wait_comment";
    //添加商品评价
    String ADD_GOODS_EVAL = API_BASE_URL + "home/goods_eval/add_goods_eval";
    //取消订单
    String CANCEL_ORDER = API_BASE_URL + "home/order/cancel_order";
    //提交订单
    String ORDER_SUBMIT = API_BASE_URL + "home/order/order_submit";
    // 退换货列表
    String HFW_REFUND_RECORD_LISTS = API_BASE_URL + "home/refund_record/get_refund_record_lists";
    // 退货/退款详情
    String HFW_REFUND_DETAIL = API_BASE_URL + "home/refund_record/get_refund_detail";
    // 申请退货/退款
    String HFW_ADD_REFUND_RECORD = API_BASE_URL + "home/refund_record/add_refund_record";
    // 取消退款/退货申请
    String HFW_CANCEL_REFUND = API_BASE_URL + "home/refund_record/cancel_refund";
    // 申请仲裁
    String HFW_APPLY_ARBIT = API_BASE_URL + "home/refund_record/apply_arbit";
    // 取消仲裁申请
    String HFW_CANCEL_ARBITR = API_BASE_URL + "home/refund_record/cancel_arbitr";
    //确认收货
    String CONFIRM_ORDER = API_BASE_URL + "home/order/confirm_order";
    //余额支付
    String BALANCEPAY = API_BASE_URL + "home/Balance/balancePay";
    //商品支付(银联，支付宝)
    String APPTRADE = API_BASE_URL + "home/Alipay/AppTrade";
    //用户余额
    String USER_BALANCE = API_BASE_URL + "home/Balance/user_balance";
    //快递公司列表数据
    String EXPRESSLIST = API_BASE_URL + "home/express/expressList";
    //删除订单
    String DEL_ORDER_SHOW = API_BASE_URL + "home/order/del_order_show";
    //我的钱包
    String USER_ACCOUNT = API_BASE_URL + "home/Wallet/user_account";
    //贡献排行榜
    String TEAMRANKING = API_BASE_URL + "home/Team/teamRanking";
    //团队收益
    String TEAMPROFIT = API_BASE_URL + "home/Team/teamProfit";
    // 云工办公
    String CLOUDWORK = API_BASE_URL + "home/Team/cloudwork";
    // 审批历史
    String HFW_TRIALHISTORY = API_BASE_URL + "home/Team/trialHistory";
    //代理办公
    String AGENTWORK = API_BASE_URL + "home/Team/agentWork";
    //修改审批状态
    String AUTOWORK = API_BASE_URL + "home/Team/autoWork";
    // 获取商家收货地址
    String HFW_MANUFAC_ADDRESS_INFO = API_BASE_URL + "manufac_address/get_manufac_address_info";
    // 物流查询
    String HFW_QUERY_DELIVERY = API_BASE_URL + "home/delivery_info/query_delivery";
    // 完善物流信息
    String HFW_PERFECT_EXPRESS = API_BASE_URL + "home/refund_record/perfect_express";
    // 账单
    String HFW_WALLET_USERBILL = API_BASE_URL + "home/wallet/userBill";
    // 查看提现
    String HFW_SELWITHDRAWALS = API_BASE_URL + "home/Wallet/selWithdrawals";
    // 提现
    String HFW_WITHDRAWALS = API_BASE_URL + "home/Wallet/Withdrawals";
    // 云工历史
    String HFW_CLOUDHISTORY = API_BASE_URL + "home/Team/cloudHistory";
    // 代理历史
    String HFW_AGENTHISTORY = API_BASE_URL + "home/Team/agentHistory";

    //成长计划（云工）
    String CLOUDGROWPLAN = API_BASE_URL + "home/Team/cloudGrowPlan";
    //成长计划（代理）
    String AGENTGROWPLAN = API_BASE_URL + "home/Team/agentGrowPlan";
    //身份支付请求
    String APPLY_PAY = API_BASE_URL + "home/apply/apply_pay";
    //手动审批
    String TRIAL = API_BASE_URL + "home/Team/trial";
    // 我的
    String HFW_USERMY = API_BASE_URL + "home/User/my";
    // 累计收益
    String HFW_USERINCOME = API_BASE_URL + "home/User/income";
    // app 更新
    String HFW_UPDATE_APP = API_BASE_URL + "home/version/selVersion";
    // 会员实名认证
    String HFW_USER_AUTHENTICATION = API_BASE_URL + "home/apply/real_identity";

    /**
     * 有关html5的url
     */
    // 关于我们
    String HFW_ABOUTUS = API_BASE_URL + "home/static_html/about";
    // 注册协议
    String HFW_REGISTER_AGREEMENT = API_BASE_URL + "home/static_html/register_agreement";
    // 合伙人协议
    String HFW_PARTNER_GREEMENT = API_BASE_URL + "home/static_html/partner_greement";
    // 代理人协议
    String HFW_AGENT_GREEMENT = API_BASE_URL + "home/static_html/agent_greement";
    // 生活商家协议
    String HFW_MERCHANT_GREEMENT = API_BASE_URL + "home/static_html/merchant_greement";
    // 云工协议
    String HFW_STAFF_GREEMENT = API_BASE_URL + "home/static_html/staff_greement";
    // 版权协议
    String HFW_COPYRIGHT_GREEMENT = API_BASE_URL + "home/static_html/copyright_greement";
    // 软件使用许可协议
    String HFW_USE_GREEMENT = API_BASE_URL + "home/static_html/use_greement";
    // 平台服务协议
    String HFW_SERVICE_GREEMENT = API_BASE_URL + "home/static_html/service_greement";
    // 法律生命及隐私权协议
    String HFW_PRIVACY_GREEMENT = API_BASE_URL + "home/static_html/privacy_greement";
    // 说明
    String HFW_EXPLAIN = API_BASE_URL + "home/static_html/explain";
    // 余额提现收费规则说明
    String HFW_CHARGE_EXPLAIN = API_BASE_URL + "home/static_html/charge_explain";
    // 余额提现到账时间说明
    String HFW_ARRIVAL_EXPLAIN = API_BASE_URL + "home/static_html/arrival_explain";
    // 花返余额发起提现后可加急处理吗？
    String HFW_URGENT_EXPLAIN = API_BASE_URL + "home/static_html/urgent_explain";
    // 合伙人【了解更多】
    String HFW_PARTNER_LEARN_MORE = API_BASE_URL + "home/static_html/partner_learn_more";
    // 代理人【了解更多】
    String HFW_AGENT_LEARN_MORE = API_BASE_URL + "home/static_html/agent_learn_more";
    // 生活商家【了解更多】
    String HFW_MERCHANT_LEARN_MORE = API_BASE_URL + "home/static_html/merchant_learn_more";
    // 云工【了解更多】
    String HFW_STAFF_LEARN_MORE = API_BASE_URL + "home/static_html/staff_learn_more";
    //查看保单信息
    String STREAKDETAIL = API_BASE_URL + "home/policy/get_policy_info?";
    //首页数据
    String SELHOME = API_BASE_URL + "home/home/selHome";
    //消息列表数据
    String GET_NOTICE_ALL = API_BASE_URL + "home/notice/get_notice_all";
    //获取指定分类消息列表
    String GET_NOTICE_LIST = API_BASE_URL + "home/notice/get_notice_list";
    //获取消息详情并更改状态
    String GET_NOTICE_INFO = API_BASE_URL + "home/notice/get_notice_info";
    //O2O前台//
    // 店铺商品列表
    String O2O_GOODS_LIST = API_BASE_URL + "o2o/merch_goods/get_goods_list";
    // 获取商品规格
    String O2O_GOODS_SPEC = API_BASE_URL + "o2o/merch_goods_spec/get_goods_spec";
    // 获取商家店铺评价
    String O2O_EVEL_LIST = API_BASE_URL + "o2o/merch_evel/get_evel_list";
    // 获取商家信息
    String O2O_MERCH_INFO = API_BASE_URL + "o2o/merchant_info/get_merch_info";
    // 商品明细及商品评价
    String O2O_MERCHGOODS_LIST = API_BASE_URL + "o2o/merch_goods/get_goods_list";
    // 确认订单
    String O2O_CRE_ORDER = API_BASE_URL + "o2o/merch_order_con/cre_order";
    // 订单详情
    String O2O_ORDER_DETAIL = API_BASE_URL + "o2o/merch_order_con/order_detail";
    // 去结算
    String O2O_GO_PAY = API_BASE_URL + "o2o/merch_order_con/go_pay";
    // 添加收货地址
    String ADD_USER_ADDRE = API_BASE_URL + "o2o/user_address_life/add_user_addre";
    // 收货地址列表
    String GET_ADD_LIST = API_BASE_URL + "o2o/user_address_life/get_add_list";
    // 删除收货地址
    String DEL_USER_ADD = API_BASE_URL + "o2o/user_address_life/del_user_add";
    // 编辑收货地址
    String UP_USER_ADD = API_BASE_URL + "o2o/user_address_life/up_user_add";
    // 查看用户订单
    String O2O_SELORDER = API_BASE_URL + "o2o/my_order/selOrder";
    // 取消接单
    String O2O_CANCELORDER = API_BASE_URL + "houtai/Goodunit/cancelOrder";
    // 申请退款
    String O2O_APPLYORDER = API_BASE_URL + "o2o/my_order/applyOrder";
    //团购查看详情
    String GROUP_BUYING_DETAILS = API_BASE_URL + "o2o/group/get_detail";
    //团购获取剩余数量
    String GROUP_GET_NUM = API_BASE_URL + "o2o/group/get_group_stock";
    //团购提交订单
    String GROUP_SUBMIT_ORDER = API_BASE_URL + "o2o/group/buy_group";
    //团购全部评价
    String GROUP_ALL_EVALUATION = API_BASE_URL + "o2o/group_eval/get_eval_list";


    //查看外卖订单
    String SELORDER = API_BASE_URL + "o2o/my_order/selOrder";
    //搜索关键词
    String SEARCHGOODS = API_BASE_URL + "home/Search/searchGoods";
    // 外卖首页
    String O2OSELHOME = API_BASE_URL + "o2o/Merch/selHome";
    //外卖美食二级分类
    String TAKEOUT_CATE_TWO = API_BASE_URL + "o2o/Merch/screen";
    //外卖美食三级分类
    String TAKEOUT_CATE_THREE = API_BASE_URL + "o2o/Merch/merchClass";
    //获取店铺分类
    String GET_MERCH_CATE = API_BASE_URL + "home/common/get_merch_cate";
    //去评论
    String GO_ORDER_EVAL = API_BASE_URL + "o2o/merch_order_con/go_order_eval";
    //外卖评价
    String TAKEOUT_EVALUATE = API_BASE_URL + "o2o/merch_order_con/add_order_evel";
    //团购评价
    String GROUP_EVALUATE = API_BASE_URL + "o2o/group_eval/go_group_eval";
    //取消订单
    String O2O_CANCEL_ORDER = API_BASE_URL + "o2o/my_order/cancelOrder";
    //商品明细
    String GET_GOODS_INFO = API_BASE_URL + "o2o/merch_goods/get_goods_info";
    //团购订单列表数据
    String SELGROUPORDER = API_BASE_URL + "o2o/my_order/selGroupOrder";
    //团购商品明细
    String GET_GROUP_ORDER_DETAIL = API_BASE_URL + "o2o/my_order/groupOrderInfo";
    //取消团购订单
    String CANCELGROUPORDER = API_BASE_URL + "o2o/my_order/cancelGroupOrder";
    //团购退订单
    String REFUNDGROUPORDER = API_BASE_URL + "o2o/my_order/refundGroupOrder";
    //查看用户团购码
    String SELCODE = API_BASE_URL + "o2o/my_order/selCode";
    //外卖取消退款
    String CANCELREFUND = API_BASE_URL + "o2o/my_order/cancelRefund";
    //确认收货
    String  O2O_CONFIRM_ORDER = API_BASE_URL + "o2o/merch_order_con/confirm_order";
    //再来一单
    String ENCORE = API_BASE_URL + "o2o/merch_order_con/encore";
    //分享商品详情
    String SHAREGOODS = API_BASE_URL + "home/Share/shareGoods";
    //分享好友邀请
    String SHARECODE = API_BASE_URL + "home/Share/shareCode";
    //供应商申请
    String ADD_MANUFAC_APPLY = API_BASE_URL + "home/manufac_apply/add_manufac_apply";
    //团购首页接口
    String O2O_GROUP_SELHOME = API_BASE_URL + "o2o/Group/selHome";
    //团购首页搜索接口
    String O2O_GROUP_SEARCHHOME = API_BASE_URL + "o2o/Search/searchGroup";
    //花返讲堂导航
    String GETFORUMCHANNEL = API_BASE_URL + "forum/forum_channel/get_forum_channel";
    //花返对应栏目列表
    String GETFORUMLIST = API_BASE_URL + "forum/forum_channel/get_forum_list";
    //花返内容详情
    String GETFORUMCONTENT = API_BASE_URL + "forum/forum_channel/get_forum_content";
    //比价列表数据
    String COMPARE = API_BASE_URL + "home/Goods/compare";
    //获取广告播放权限
    String GETADVERTISING = API_BASE_URL + "home/home/openScreen";
    //支付宝提现
    String WITHDRAWALS = API_BASE_URL + "home/Alipay/alipayWithdrawals";
    //根据规格更改比价
    String COMPSPEC = API_BASE_URL + "home/Goods/compSpec";
    //线下商家详情
    String MERCHXQ = API_BASE_URL + "o2o/Merch/merchXq";
    //线下扫码下单
    String SCANORDER = API_BASE_URL + "o2o/Merch/scanOrder";
    //外卖首页搜索
    String O2OINDEX_SEARCHGOODS = API_BASE_URL + "o2o/Search/searchGoods";
    //店铺内搜索
    String O2OINDEX_SEARCHSTORE_GOODS = API_BASE_URL + "o2o/Search/searchdp";
    //各地区角色组成
    String ROLEDATA = API_BASE_URL + "o2o/platform_data/roleData";
    //角色组成
    String FORMDATA = API_BASE_URL + "o2o/platform_data/formData";
    //业务奖励数据
    String BUSINESSDATA = API_BASE_URL + "o2o/platform_data/businessData";
    //收益数据接口
    String PROFITDATA = API_BASE_URL + "o2o/platform_data/profitData";
    //地图数据接口
    String MAPDATA = API_BASE_URL + "o2o/platform_data/mapData";
    //外卖美食搜索
    String TAKE_CATE_SEARCH = API_BASE_URL + "o2o/Search/searchCate";
    //外卖退款订单详情
    String TAKE_REFUNDORDER=API_BASE_URL+"o2o/my_order/refundOrder";
}

package com.android.p2pflowernet.project.o2omain.fragment.mine;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.base.BaseApplication;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.MineMyBean;
import com.android.p2pflowernet.project.entity.ShareCodeBean;
import com.android.p2pflowernet.project.event.UpdateUserInfoEvent;
import com.android.p2pflowernet.project.event.UserInfoEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out.OrderGroupTakeOutActivity;
import com.android.p2pflowernet.project.o2omain.fragment.mine.take_out.OrderTakeOutActivity;
import com.android.p2pflowernet.project.utils.QRCodeUtil;
import com.android.p2pflowernet.project.utils.RxImageLoader;
import com.android.p2pflowernet.project.utils.SPUtils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.activity.AccoumIncomeActivity;
import com.android.p2pflowernet.project.view.activity.ForumActivity;
import com.android.p2pflowernet.project.view.activity.LoginActivity;
import com.android.p2pflowernet.project.view.activity.OrderDetailActivity;
import com.android.p2pflowernet.project.view.activity.WalletActivity;
import com.android.p2pflowernet.project.view.customview.CircleImageView;
import com.android.p2pflowernet.project.view.customview.RedDotLayout;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.ApplyForActivity;
import com.android.p2pflowernet.project.view.fragments.mine.cargo_refund.RefundingActivity;
import com.android.p2pflowernet.project.view.fragments.mine.message.MessageActivity;
import com.android.p2pflowernet.project.view.fragments.mine.myteam.MyTeamActivity;
import com.android.p2pflowernet.project.view.fragments.mine.setting.SettingActivity;
import com.android.p2pflowernet.project.view.fragments.mine.setting.personal.PersonalInformationActivity;
import com.android.p2pflowernet.project.view.fragments.mine.waitevaluated.WaitEvaluatedActivity;
import com.caimuhao.rxpicker.utils.DensityUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2018/1/2.
 * by--我的
 */

public class O2oMineFragment extends KFragment<IO2oMineView, IO2oMinePrenter> implements IO2oMineView {
    @BindView(R.id.user_headerimg)
    // 我的头像
            CircleImageView circleImageView;
    @BindView(R.id.nickName)
    // 账户名
            TextView nickName;
    @BindView(R.id.msmcode)
    // 邀请码
            TextView msmcode;
    @BindView(R.id.tv_title)
    // 标题
            TextView tv_title;
    @BindView(R.id.getmoney_tv)
    // 今日收益
            TextView getmoney_tv;
    // 合伙人
    @BindView(R.id.minepartner_ly)
    LinearLayout minepartner_ly;

    @BindView(R.id.minepartner_im)
    ImageView minepartner_im;
    // 代理
    @BindView(R.id.minesurrogate_ly)
    LinearLayout minesurrogate_ly;
    @BindView(R.id.minesurrogate_im)
    ImageView minesurrogate_im;
    // 云工
    @BindView(R.id.minecloudworker_ly)
    LinearLayout minecloudworker_ly;
    @BindView(R.id.minecloudworker_im)
    ImageView minecloudworker_im;
    // 商家
    @BindView(R.id.store_ly)
    LinearLayout store_ly;
    @BindView(R.id.store_im)
    ImageView store_im;
    //设置
    @BindView(R.id.iv_setup)
    ImageView iv_setup;
    //消息
    @BindView(R.id.iv_message)
    ImageView iv_message;
    //申请
    @BindView(R.id.apply_btn)
    Button apply_btn;
    //待返利
    @BindView(R.id.toMoney_tv)
    TextView toMoney_tv;
    //已返利
    @BindView(R.id.getMoney_tv)
    TextView getMoney_tv;
    // 累计收益
    @BindView(R.id.countmoney_ly)
    LinearLayout countmoney_ly;
    // 我的钱包
    @BindView(R.id.mywallet_ly)
    LinearLayout mywallet_ly;
    // 我的团队
    @BindView(R.id.myteam_ly)
    LinearLayout myteam_ly;
    // 全部商城订单
    @BindView(R.id.allorder_ly)
    LinearLayout allorder_ly;

    // 待付款
    @BindView(R.id.rl_pendingpayment)
    RedDotLayout rl_pendingpayment;
    // 待发货
    @BindView(R.id.rl_pendingshipment)
    RedDotLayout rl_pendingshipment;
    // 待收货
    @BindView(R.id.rl_person_send)
    RedDotLayout rl_person_send;
    // 待评价
    @BindView(R.id.rl_pendingcomment)
    RedDotLayout rl_pendingcomment;
    // 退换货
    @BindView(R.id.rl_person_aftermarket)
    RedDotLayout rl_person_aftermarket;
    // 分享
    @BindView(R.id.share_ly)
    LinearLayout share_ly;
    @BindView(R.id.ll_wmdd)
    LinearLayout ll_wmdd;//外卖订单
    @BindView(R.id.ll_tgdd)
    LinearLayout ll_tgdd;//团购订单
    @BindView(R.id.ll_share)
    LinearLayout llShare;
    @BindView(R.id.im_bg)
    ImageView ivBg;
    @BindView(R.id.supplier_im)
    ImageView supplierIv;
    @BindView(R.id.supplier_ly)
    LinearLayout llSupplie;//供应商

    private int imageSize;
    private String isLogin;
    private ShapeLoadingDialog shapeLoadingDialog;
    private String nickNames = "";
    private String inviteCode = "邀请码:";
    private PopupWindow popupWindow;
    private String invite_code = "";

    public static KFragment newInstance() {

        O2oMineFragment o2oMineFragment = new O2oMineFragment();
        Bundle bundle = new Bundle();
        o2oMineFragment.setArguments(bundle);
        return o2oMineFragment;
    }

    @Override
    public IO2oMinePrenter createPresenter() {
        return new IO2oMinePrenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_o2o_mine;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸式
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.coloro2o));
        ivBg.setBackgroundResource(R.drawable.icon_bg_o2o);
        imageSize = DensityUtil.getDeviceWidth(circleImageView.getContext()) / 3;
        //初始化加载进度条
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .delay(5000)
                .loadText("加载中...")
                .build();

        isLogin = String.valueOf(SPUtils.get(BaseApplication.getContext(), Constants.ISLOGIN, ""));
        if (isLogin.equals("")) {
            nickName.setText("未登录");
            msmcode.setVisibility(View.GONE);
//            circleImageView.setBackgroundResource(R.mipmap.default_header);
        } else {
            mPresenter.usermy();
        }
    }


    @OnClick({R.id.user_headerimg, R.id.iv_setup, R.id.iv_message, R.id.apply_btn, R.id.mywallet_ly, R.id.myteam_ly
            , R.id.allorder_ly, R.id.rl_pendingpayment, R.id.rl_pendingshipment, R.id.rl_person_send,
            R.id.rl_pendingcomment, R.id.rl_person_aftermarket, R.id.share_ly, R.id.nickName,
            R.id.iv_present, R.id.ll_wmdd, R.id.ll_tgdd, R.id.ll_share, R.id.ll_lecture})
    public void onViewOnclick(View view) {
        switch (view.getId()) {
            case R.id.nickName:
                // 点击去登录
                isLogin = String.valueOf(SPUtils.get(BaseApplication.getContext(), Constants.ISLOGIN, ""));
                if (isLogin.equals("")) {
                    //去登录
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.user_headerimg:
                isLogin = String.valueOf(SPUtils.get(BaseApplication.getContext(), Constants.ISLOGIN, ""));
                if (isLogin.equals("")) {
                    //去登陆
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    // 更换头像
                    Intent intent = new Intent(getActivity(), PersonalInformationActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.iv_setup:
                isLogin = String.valueOf(SPUtils.get(BaseApplication.getContext(), Constants.ISLOGIN, ""));
                if (isLogin.equals("")) {
                    //去登录
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    // 设置
                    startActivity(new Intent(getActivity(), SettingActivity.class));
                }
                break;
            case R.id.iv_message:
                isLogin = String.valueOf(SPUtils.get(BaseApplication.getContext(), Constants.ISLOGIN, ""));
                if (isLogin.equals("")) {
                    //去登录
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    // 消息
                    startActivity(new Intent(getActivity(), MessageActivity.class));
                }
                break;
            case R.id.apply_btn:
                // 申请按钮
                isLogin = String.valueOf(SPUtils.get(BaseApplication.getContext(), Constants.ISLOGIN, ""));
                if (isLogin.equals("")) {
                    //去登录
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), ApplyForActivity.class));
                }
                break;
            case R.id.mywallet_ly:
                isLogin = String.valueOf(SPUtils.get(BaseApplication.getContext(), Constants.ISLOGIN, ""));
                if (isLogin.equals("")) {

                    //去登录
                    startActivity(new Intent(getActivity(), LoginActivity.class));

                } else {
                    // 我的钱包
                    startActivity(new Intent(getActivity(), WalletActivity.class));
                }

                break;
            case R.id.myteam_ly:
                // 我的团队
                isLogin = String.valueOf(SPUtils.get(BaseApplication.getContext(), Constants.ISLOGIN, ""));
                if (isLogin.equals("")) {

                    //去登录
                    startActivity(new Intent(getActivity(), LoginActivity.class));

                } else {

                    // 我的团队
                    startActivity(new Intent(getActivity(), MyTeamActivity.class));
                }

                break;
            case R.id.allorder_ly:
                isLogin = String.valueOf(SPUtils.get(BaseApplication.getContext(), Constants.ISLOGIN, ""));
                // 全部订单
                if (isLogin.equals("")) {
                    //去登录
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), OrderDetailActivity.class).putExtra("index", 1));
                }
                break;
            case R.id.rl_pendingpayment:
                isLogin = String.valueOf(SPUtils.get(BaseApplication.getContext(), Constants.ISLOGIN, ""));
                // 待付款
                if (isLogin.equals("")) {
                    //去登录
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), OrderDetailActivity.class).putExtra("index", 2));
                }

                break;
            case R.id.rl_pendingshipment:
                // 待发货

                if (isLogin.equals("")) {
                    //去登录
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), OrderDetailActivity.class).putExtra("index", 3));
                }

                break;
            case R.id.rl_person_send:
                isLogin = String.valueOf(SPUtils.get(BaseApplication.getContext(), Constants.ISLOGIN, ""));
                // 待收货
                if (isLogin.equals("")) {
                    //去登录
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), OrderDetailActivity.class).putExtra("index", 4));
                }
                break;
            case R.id.rl_pendingcomment:
                // 待评价
                if (isLogin.equals("")) {
                    //去登录
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), WaitEvaluatedActivity.class));
                }
                break;
            case R.id.rl_person_aftermarket:
                isLogin = String.valueOf(SPUtils.get(BaseApplication.getContext(), Constants.ISLOGIN, ""));
                if (isLogin.equals("")) {

                    //去登录
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    // 退换货
                    startActivity(new Intent(getActivity(), RefundingActivity.class));
                }

                break;
            case R.id.ll_wmdd://外卖订单

                isLogin = String.valueOf(SPUtils.get(BaseApplication.getContext(), Constants.ISLOGIN, ""));
                // 外卖订单
                if (isLogin.equals("")) {
                    //去登录
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), OrderTakeOutActivity.class));
                }

                break;
            case R.id.ll_tgdd://团购订单

                isLogin = String.valueOf(SPUtils.get(BaseApplication.getContext(), Constants.ISLOGIN, ""));
                // 全部订单
                if (isLogin.equals("")) {
                    //去登录
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), OrderGroupTakeOutActivity.class));
                }

                break;
            case R.id.ll_share://分享

                isLogin = String.valueOf(SPUtils.get(BaseApplication.getContext(), Constants.ISLOGIN, ""));
                if (isLogin.equals("")) {

                    //去登录
                    startActivity(new Intent(getActivity(), LoginActivity.class));

                } else {

                    mPresenter.getShareCode();
                }

                break;
            case R.id.ll_lecture://花返讲堂

                if (isLogin.equals("")) {
                    //去登录
                    startActivity(new Intent(getActivity(), LoginActivity.class));

                } else {
                    startActivity(new Intent(getActivity(), ForumActivity.class));
                }

                break;
        }
    }


    @Override
    public void onError(String s) {

        showShortToast(s);

    }

    @Override
    public void showDialog() {

        shapeLoadingDialog.show();
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void setMineMyInfo(MineMyBean mineMyBean) {
        if (mineMyBean == null) {
            return;
        }

        invite_code = mineMyBean.getInvite_code();
        SPUtils.put(getActivity(), "invite_code", invite_code);
        nickName.setText(TextUtils.isEmpty(mineMyBean.getNickname()) ? "" : mineMyBean.getNickname());
        msmcode.setText(TextUtils.isEmpty(mineMyBean.getInvite_code()) ? "" : "邀请码:" + mineMyBean.getInvite_code());
        String file_path = ApiUrlConstant.API_IMG_URL + mineMyBean.getSmall_head();
        new RxImageLoader().display(circleImageView, file_path, imageSize, imageSize);
        msmcode.setVisibility(View.VISIBLE);
        // 昨日收益
        getmoney_tv.setText(TextUtils.isEmpty(mineMyBean.getYestprofit()) ? "" : mineMyBean.getYestprofit());
        // 是否是代理人
        final String is_agent = mineMyBean.getIs_agent();
        if (is_agent.equals("1")) {
            // 是
            minesurrogate_im.setImageResource(R.mipmap.mine_homesurrogate_pressed_icon);
        } else {
            minesurrogate_im.setImageResource(R.mipmap.mine_homesurrogate_normal_icon);
        }
        minesurrogate_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_agent.equals("0")) {
                    // 去申请代理人
                    Intent intent = new Intent(getActivity(), ApplyForActivity.class);
                    startActivity(intent);
                } else {
                    showShortToast("您已经是代理人");
                }
            }
        });
        final String is_partner = mineMyBean.getIs_partner();
        // 是否合伙人
        if (is_partner.equals("1")) {
            // 是
            minepartner_im.setImageResource(R.mipmap.mine_homepartner_pressed_icon);
        } else {
            minepartner_im.setImageResource(R.mipmap.mine_homepartner_normal_icon);
        } //
        minepartner_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_partner.equals("0")) {
                    // 去申请合伙人
                    Intent intent = new Intent(getActivity(), ApplyForActivity.class);
                    startActivity(intent);
                } else {
                    showShortToast("您已经是合伙人");
                }
            }
        });
        final String is_staff = mineMyBean.getIs_staff();
        // 是否云工
        if (is_staff.equals("1")) {
            // 是
            minecloudworker_im.setImageResource(R.mipmap.mine_homecloudworker_pressed_icon);
        } else {
            minecloudworker_im.setImageResource(R.mipmap.mine_homecloudworker_normal_icon);
        }
        minecloudworker_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_staff.equals("0")) {
                    // 去申请云工
                    Intent intent = new Intent(getActivity(), ApplyForActivity.class);
                    startActivity(intent);
                } else {
                    showShortToast("您已经是云工");
                }
            }
        });
        final String is_merchant = mineMyBean.getIs_merchant();
        // 是否商家
        if (is_merchant.equals("1")) {
            // 是
            store_im.setImageResource(R.mipmap.mine_homestore_pressed_icon);
        } else {
            store_im.setImageResource(R.mipmap.mine_homestore_normal_icon);
        }
        store_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_merchant.equals("0")) {
                    // 去申请商家
                    Intent intent = new Intent(getActivity(), ApplyForActivity.class);
                    startActivity(intent);
                } else {
                    showShortToast("您已经是商家");
                }
            }
        });

        final String is_manufac = mineMyBean.getIs_manufac();
        //是否是供应商
        llSupplie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin.equals("")) {
                    //去登录
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {

                    if (is_manufac.equals("0")) {

                        // 去申请供应商
                        Intent intent = new Intent(getActivity(), ApplyForActivity.class);
                        startActivity(intent);

                    } else {

                        showShortToast("您已经是供应商");
                    }
                }
            }
        });

        // 待返利
        toMoney_tv.setText(TextUtils.isEmpty(mineMyBean.getZbRebate()) ? "" : mineMyBean.getZbRebate());
        // 已返利
        getMoney_tv.setText(TextUtils.isEmpty(mineMyBean.getAlRebate()) ? "" : mineMyBean.getAlRebate());
        // 待付款
        rl_pendingpayment.setText(String.valueOf(mineMyBean.getPayment()));
        // 待发货
        rl_pendingshipment.setText(String.valueOf(mineMyBean.getDelivergoods()));
        // 待收货
        rl_person_send.setText(String.valueOf(mineMyBean.getGoodsreceipt()));
        // 待评价
        rl_pendingcomment.setText(String.valueOf(mineMyBean.getEval()));
        // 退换货
        rl_person_aftermarket.setText(String.valueOf(mineMyBean.getRefund()));
        countmoney_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AccoumIncomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("is_agent", is_agent);
                bundle.putString("is_partner", is_partner);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSuccessShare(ShareCodeBean data) {

        if (data != null) {

            initControls(data);
            popupWindow.showAtLocation(llShare, Gravity.BOTTOM, 0, 0);

        }
    }

    @Override
    public void onSuccess(String message) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpdateUserInfoEvent updateUserInfoEvent) {
        mPresenter.usermy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserInfoEvent userInfoEvent) {

        if (userInfoEvent.getUserInfo() == null || TextUtils.isEmpty(userInfoEvent.getUserInfo().toString())) {

            nickName.setText("未登录");
            msmcode.setVisibility(View.GONE);
            SPUtils.put(BaseApplication.getContext(), Constants.ISLOGIN, "");
            SPUtils.put(BaseApplication.getContext(), "token", "");
            SPUtils.put(BaseApplication.getContext(), "region", "");
            circleImageView.setImageResource(R.mipmap.default_header);

        } else {

            Constants.TOKEN = userInfoEvent.getUserInfo().getToken();
            SPUtils.put(getActivity(), "token", userInfoEvent.getUserInfo().getToken());
            SPUtils.put(BaseApplication.getContext(), Constants.ISLOGIN, "isLogin");
            SPUtils.put(BaseApplication.getContext(), "region", userInfoEvent.getUserInfo().getRegion());
            nickNames = userInfoEvent.getUserInfo().getNickname();
            // 设置用户昵称
            nickName.setText(nickNames);
            nickName.setGravity(Gravity.CENTER);
            //设置用户头像信息
            String file_path = ApiUrlConstant.API_IMG_URL + userInfoEvent.getUserInfo().getHead_path();
            new RxImageLoader().display(circleImageView, file_path, imageSize, imageSize);

            // 设置邀请码
            String invite_code = userInfoEvent.getUserInfo().getInvite_code();

            if (TextUtils.isEmpty(invite_code)) {
                msmcode.setVisibility(View.GONE);
            } else {
                inviteCode = "邀请码:" + invite_code;
                msmcode.setText(inviteCode);
                msmcode.setVisibility(View.VISIBLE);
            }
        }
    }

    //初始化分享功能
    private void initControls(final ShareCodeBean data) {

        //生成二维码
        Resources res = getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.logo);
        Bitmap drawable = QRCodeUtil.createQRImage(invite_code, 125, 135, bmp, "");


        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.sharepop, null);

        // 自适配长、框设置
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow.update();
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(1f);
        popupWindow.setOnDismissListener(new poponDismissListener());
        TextView esc = (TextView) view.findViewById(R.id.cancle);//取消
        esc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
            }
        });

        //朋友圈
        final LinearLayout lin1 = (LinearLayout) view.findViewById(R.id.share_wechat_circle);
        lin1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
                share1(data);
            }
        });

        //微信
        final LinearLayout lin2 = (LinearLayout) view.findViewById(R.id.share_wechat);
        lin2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();

                share2(data);
            }
        });

        //微博
        final LinearLayout lin3 = (LinearLayout) view.findViewById(R.id.share_sina);
        lin3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
                share3(data);
            }
        });

        //QQ好友
        final LinearLayout lin4 = (LinearLayout) view.findViewById(R.id.share_qq_frend);
        lin4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();

                share4(data);
            }
        });

        //QQ空间
        LinearLayout lin5 = (LinearLayout) view.findViewById(R.id.share_qq_space);
        lin5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                share5(data);
            }
        });

        //复制连接，拷贝网址
        final LinearLayout lin6 = (LinearLayout) view.findViewById(R.id.share_copy);
        lin6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
                popupWindow.dismiss();
                ClipboardManager myClipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                String text = "您的好友邀请您注册“花返网”一起开启花返之旅";
                ClipData text1 = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(text1);
                showShortToast("已复制到剪切板");
            }
        });
    }

    //QQ空间分享
    private void share5(ShareCodeBean data) {

        String share_url = data.getShare_url() == null ? "" : data.getShare_url();

        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(data.getTitle() == null ? "" : data.getTitle());//标题
        String img = ApiUrlConstant.API_IMG_URL + data.getImg();
        UMImage image = new UMImage(getActivity(), img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(data.getIntro() == null ? "" : data.getIntro());//描述

        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.QZONE)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    //QQ好友分享
    private void share4(ShareCodeBean data) {

        String share_url = data.getShare_url() == null ? "" : data.getShare_url();
        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(data.getTitle() == null ? "" : data.getTitle());//标题
        String img = ApiUrlConstant.API_IMG_URL + data.getImg();
        UMImage image = new UMImage(getActivity(), img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(data.getIntro() == null ? "" : data.getIntro());//描述

        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.QQ)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    //微信分享
    private void share2(ShareCodeBean data) {

        String share_url = data.getShare_url() == null ? "" : data.getShare_url();
        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(data.getTitle() == null ? "" : data.getTitle());//标题
        String img = ApiUrlConstant.API_IMG_URL + data.getImg();
        UMImage image = new UMImage(getActivity(), img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(data.getIntro() == null ? "" : data.getIntro());//描述

        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.WEIXIN)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    //微信朋友圈
    private void share1(ShareCodeBean data) {

        String share_url = data.getShare_url() == null ? "" : data.getShare_url();
        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(data.getTitle() == null ? "" : data.getTitle());//标题
        String img = ApiUrlConstant.API_IMG_URL + data.getImg();
        UMImage image = new UMImage(getActivity(), img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(data.getIntro() == null ? "" : data.getIntro());//描
        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    //新浪微博
    private void share3(ShareCodeBean data) {

        String share_url = data.getShare_url() == null ? "" : data.getShare_url();
        // TODO Auto-generated method stub
        UMWeb web = new UMWeb(share_url);
        web.setTitle(data.getTitle() == null ? "" : data.getTitle());//标题
        String img = ApiUrlConstant.API_IMG_URL + data.getImg();
        UMImage image = new UMImage(getActivity(), img);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(data.getIntro() == null ? "" : data.getIntro());//描
        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.SINA)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    //分享的回调
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            showShortToast("分享成功啦");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {

            showShortToast("分享失败啦");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            showShortToast("分享取消啦");
        }
    };

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }


    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }
    }
}

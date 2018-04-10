package com.android.p2pflowernet.project.view.fragments.mine.setting;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.base.BaseApplication;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.IdEntityBean;
import com.android.p2pflowernet.project.entity.SettingBean;
import com.android.p2pflowernet.project.event.RevisePasswordEvent;
import com.android.p2pflowernet.project.event.UserInfoEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.DeviceUtil;
import com.android.p2pflowernet.project.utils.SPUtils;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.utils.clusterutil.CacheDataManager;
import com.android.p2pflowernet.project.view.activity.AuthenticationActivity;
import com.android.p2pflowernet.project.view.activity.AuthenticationResultActivity;
import com.android.p2pflowernet.project.view.activity.LoginActivity;
import com.android.p2pflowernet.project.view.activity.PublicWebActivity;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.AppPartnerAlertDialog;
import com.android.p2pflowernet.project.view.fragments.mine.setting.adress.AdressMangerActiivty;
import com.android.p2pflowernet.project.view.fragments.mine.setting.feedback.FeedBackActiivty;
import com.android.p2pflowernet.project.view.fragments.mine.setting.login.SettingLoginActiivty;
import com.android.p2pflowernet.project.view.fragments.mine.setting.message.SettingMessageActiivty;
import com.android.p2pflowernet.project.view.fragments.mine.setting.pay.SettingPayActivity;
import com.android.p2pflowernet.project.view.fragments.mine.setting.personal.PersonalInformationActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

import static com.android.p2pflowernet.project.utils.Utils.isFastDoubleClick;

/**
 * Created by caishen on 2017/11/4.
 * by--设置页面
 */

public class SettingFragment extends KFragment<ISettingView, ISettingPrenter>
        implements NormalTopBar.normalTopClickListener, ISettingView {


    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.ll_per_info)
    LinearLayout llPerInfo;
    @BindView(R.id.ll_login)
    TextView llLogin;
    @BindView(R.id.authentication_status)
    // 认证状态
            TextView authentication_status;
    @BindView(R.id.ll_pay_set)
    LinearLayout llPaySet;
    @BindView(R.id.ll_message)
    LinearLayout llMessage;
    @BindView(R.id.tv_adress)
    TextView tvAdress;
    @BindView(R.id.ll_place_manage)
    LinearLayout llPlaceManage;
    @BindView(R.id.tv_referrer)
    TextView tvReferrer;
    @BindView(R.id.ll_rel_name)
    LinearLayout llRelName;
    @BindView(R.id.ll_feed_back)
    LinearLayout llFeedBack;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.ll_clear_location)
    LinearLayout llClearLocation;
    @BindView(R.id.ll_ablout_us)
    LinearLayout llAbloutUs;
    @BindView(R.id.btn_out_login)
    Button btnOutLogin;
    private ShapeLoadingDialog shapeLoadingDialog;
    private IdEntityBean idEntityBean;
    private int is_checked;

    @Override
    public ISettingPrenter createPresenter() {

        return new ISettingPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_setting;
    }

    @Override
    public void initData() {

        mPresenter.getSetting();

        //获取本地数据的缓存
        try {
            tvClear.setText(CacheDataManager.getTotalCacheSize(getActivity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        Utils.setStatusBar(getActivity(), 0, false);
        normalTop.setTitleText("设置");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        initData();
        //判断是否实名认证过。
        mPresenter.checkIdentity();

    }

    public static KFragment newIntence() {

        return new SettingFragment();
    }

    @OnClick({R.id.ll_per_info, R.id.ll_pay_set, R.id.ll_message, R.id.ll_place_manage,
            R.id.ll_feed_back, R.id.ll_ablout_us, R.id.ll_clear_location,
            R.id.btn_out_login, R.id.ll_login,R.id.ll_rel_name})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_per_info://个人信息

                if (Constants.TOKEN.equals("")) {

                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    removeFragment();

                } else {

                    Intent intent = new Intent(getActivity(), PersonalInformationActivity.class);
                    startActivity(intent);
                }


                break;
            case R.id.ll_login://登录设置

                if (Constants.TOKEN.equals("")) {

                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    removeFragment();

                } else {

                    Intent intent = new Intent(getActivity(), SettingLoginActiivty.class);
                    startActivity(intent);
                }

                break;
            case R.id.ll_pay_set://支付设置

                if (Constants.TOKEN.equals("")) {

                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    removeFragment();

                } else {

                    Intent intent = new Intent(getActivity(), SettingPayActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.ll_message://信息设置

                if (Constants.TOKEN.equals("")) {

                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    removeFragment();

                } else {

                    Intent intent = new Intent(getActivity(), SettingMessageActiivty.class);
                    startActivity(intent);
                }

                break;
            case R.id.ll_place_manage://地址管理

                if (Constants.TOKEN.equals("")) {

                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    removeFragment();

                } else {

                    Intent intent = new Intent(getActivity(), AdressMangerActiivty.class);
                    startActivity(intent);
                }

                break;

            case R.id.ll_feed_back://意见反馈

                if (Constants.TOKEN.equals("")) {

                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    removeFragment();

                } else {

                    Intent intent = new Intent(getActivity(), FeedBackActiivty.class);
                    startActivity(intent);
                }

                break;
            case R.id.ll_ablout_us://关于我们
                if (isFastDoubleClick()) {
                    return;
                }
                Intent intent = new Intent(getActivity(), PublicWebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("publicurl", ApiUrlConstant.HFW_ABOUTUS+"?v="+ DeviceUtil.getVersionName(BaseApplication.getContext()));
                intent.putExtra("tag", "1");//区别是否进入首页,0-由广告页进入首页 1-不进入首页
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.ll_clear_location://清除本地缓存

                showAlertMsgDialog("是否清除本地缓存？", "温馨提示", "确定", "取消");

                break;
            case R.id.btn_out_login://退出当前账户

                EventBus.getDefault().post(new UserInfoEvent(null));
                Constants.TOKEN = "";
                removeFragment();
                getActivity().finish();

                if (Constants.TOKEN.equals("")) {

                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    removeFragment();

                } else {

                    EventBus.getDefault().post(new UserInfoEvent(null));
                    Constants.TOKEN = "";
                    SPUtils.put(getActivity(), "token", "");
                    SPUtils.put(BaseApplication.getContext(), Constants.ISLOGIN, "");
                    SPUtils.put(BaseApplication.getContext(), "region", "");
                    removeFragment();
                    getActivity().finish();
                }

                break;

            case R.id.ll_rel_name://实名认证
                if (is_checked == 1) {//认证结果页
                    startActivity(new Intent(getActivity(), AuthenticationResultActivity.class));
                }else{//提交认证页
                    startActivity(new Intent(getActivity(), AuthenticationActivity.class));
                }
                break;
        }
    }

    @Override
    public void onLeftClick(View view) {

        removeFragment();
    }

    @Override
    public void onRightClick(View view) {

    }

    @Override
    public void onTitleClick(View view) {

    }

    /**
     * @Title: showAlertMsgDialog @Description: TODO 弹出消息框 @param @param
     * msg @param @param title @param @param positive @param @param
     * negative @return_ticket void 返回类型 @throws
     */
    public void showAlertMsgDialog(String msg, String title, String positive, String negative) {
        AppPartnerAlertDialog alertDialog = new AppPartnerAlertDialog(getActivity()).builder().setTitle(title).setMsg(msg)
                .setPositiveButton(positive, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        //清除本地缓存
                        CacheDataManager.clearAllCache(getActivity());
                        //获取本地数据的缓存
                        try {
                            tvClear.setText(CacheDataManager.getTotalCacheSize(getActivity()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton(negative, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        alertDialog.show();
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
    public void setSuccessSet(SettingBean data) {

    }

    /**
     * 检测是否实名认证
     *
     * @param data
     */
    @Override
    public void setGetIdentitySuccess(IdEntityBean data) {
        idEntityBean = data;
        is_checked = data.getIs_checked();
        if (is_checked == 1) {
            authentication_status.setText("已实名认证");
        }else{
            authentication_status.setText("未实名认证");
        }

    }

    //修改密码成功后删除当前页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RevisePasswordEvent event){
        removeFragment();
    }
}

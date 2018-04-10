package com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.WaitAuditAdapter;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.AgentOfficeBean;
import com.android.p2pflowernet.project.entity.AutoWorkBean;
import com.android.p2pflowernet.project.entity.ShareCodeBean;
import com.android.p2pflowernet.project.event.AgenceOfficeEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.activity.TaskHistoryActivity;
import com.android.p2pflowernet.project.view.customview.MyListView;
import com.android.p2pflowernet.project.view.customview.SectionProgressBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.AppPartnerAlertDialog;
import com.android.p2pflowernet.project.view.fragments.affirm.partnerapttitude.BuyPartnerAptitudeActiivty;
import com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office.audit.AuditHistoryActivity;
import com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office.strongplan.StrongPlanActivity;
import com.android.p2pflowernet.project.view.fragments.mine.myteam.news.GoodNewsActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by caishen on 2017/11/28.
 * by--代理办公
 */

public class AgenceOfficeFragment extends KFragment<IAgenceOfficeView, IAgenceOfficePrenter>
        implements IAgenceOfficeView {

    @BindView(R.id.iv_btn_invite)
    ImageView ivBtnInvite;
    @BindView(R.id.ll_schedule_plan)
    LinearLayout llSchedulePlan;
    @BindView(R.id.ll_strong_plan)
    LinearLayout llStrongPlan;
    @BindView(R.id.ll_more_goods)
    LinearLayout llMoreGoods;
    @BindView(R.id.rb_married)
    RadioButton rbMarried;
    @BindView(R.id.tv_sd)
    TextView tvSd;
    @BindView(R.id.rb_spinsterhood)
    RadioButton rbSpinsterhood;
    @BindView(R.id.tv_zd)
    TextView tvZd;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.tv_audit)
    TextView tvAudit;
    @BindView(R.id.my_listView)
    MyListView myListView;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.rl_audit_history)
    RelativeLayout rlAuditHistory;
    @BindView(R.id.section_bar)
    SectionProgressBar sectionBar;
    @BindView(R.id.section_bar1)
    SectionProgressBar sectionBar1;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    private Handler mHandler = new Handler();
    private ShapeLoadingDialog shapeLoadingDialog;
    private WaitAuditAdapter mAdapter;
    private String state = "";
    private String record_id = "";
    private String refresh = "0";//刷新模式
    private List<AgentOfficeBean.NotauditedBean> notaudited;
    private List<AgentOfficeBean.NotauditedBean> notauditedBeen;
    private PopupWindow popupWindow;

    @Override
    public IAgenceOfficePrenter createPresenter() {
        return new IAgenceOfficePrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_agent_office;
    }

    @Override
    public void initData() {


        mPresenter.getagentWork();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化合伙人，代理人的进度条
        sectionBar.setLevels(getResources().getStringArray(R.array.SectionLevels));
        sectionBar.setLevelValues(getResources().getIntArray(R.array.PartnerSectionLevelValues));
        sectionBar1.setLevels(getResources().getStringArray(R.array.SectionLevels1));
        sectionBar1.setLevelValues(getResources().getIntArray(R.array.AgencySectionLevelValues));

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

//        initData();

        //手动
        rbMarried.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked && !state.equals("1")) {

                    state = "1";
                    mPresenter.autoWork();
                }
            }
        });

        //自动审批
        rbSpinsterhood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked && !state.equals("2")) {

                    state = "2";
                    mPresenter.autoWork();
                }
            }
        });
    }

    public static Fragment newIntence() {

        AgenceOfficeFragment agenceOfficeFragment = new AgenceOfficeFragment();
        Bundle bundle = new Bundle();
        agenceOfficeFragment.setArguments(bundle);
        return agenceOfficeFragment;
    }

    @OnClick({R.id.iv_btn_invite, R.id.ll_schedule_plan, R.id.ll_strong_plan,
            R.id.ll_more_goods, R.id.tv_audit, R.id.rl_audit_history})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_btn_invite://邀请

                mPresenter.getShareCode();

                break;
            case R.id.ll_schedule_plan://任务历史

                Intent intent = new Intent(getActivity(), TaskHistoryActivity.class);
                intent.putExtra("tag", 0);
                startActivity(intent);

                break;
            case R.id.ll_strong_plan://成长计划

                intent = new Intent(getActivity(), StrongPlanActivity.class);
                startActivity(intent);

                break;
            case R.id.ll_more_goods://更多喜讯

                intent = new Intent(getActivity(), GoodNewsActivity.class);
                intent.putExtra("tag", "1");
                startActivity(intent);

                break;
            case R.id.tv_audit://手动审批

                if (state.equals("1")) {
                    mPresenter.trial();
                } else {
                    showShortToast("当前是自动审批");
                }

                break;
            case R.id.rl_audit_history://审批历史

                intent = new Intent(getActivity(), AuditHistoryActivity.class);
                startActivity(intent);

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


    /**
     * 刷新页面
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AgenceOfficeEvent event) {

        mPresenter.getagentWork();
    }

    @Override
    public void Succesagent(AgentOfficeBean data) {

        if (data != null) {

            sectionBar.setCurrent(data.getPartner());//合伙人数
            sectionBar1.setCurrent(data.getUser());//会员数
            tvMoney.setText(data.getQual_fund_amount().isEmpty() ? "" : "¥" + data.getQual_fund_amount());//审批金额
            tvHint.setText("违规" + data.getIllegalnum() + "次");//违规次数
            String appro_mode = data.getAppro_mode();//1-手动审批 2-自动审批

            if (appro_mode.equals("1")) {
                state = "1";
                rbMarried.setChecked(true);
            } else if (appro_mode.equals("2")) {
                state = "2";
                rbSpinsterhood.setChecked(true);
            }

            notauditedBeen = new ArrayList<>();

            //设置待审核的列表适配器
            if (data.getNotaudited() != null && data.getNotaudited().size() > 0) {

                myListView.setVisibility(View.VISIBLE);
                notauditedBeen = data.getNotaudited();
                mAdapter = new WaitAuditAdapter(getActivity(), notauditedBeen);
                myListView.setAdapter(mAdapter);

                //设置单选
                mAdapter.setCheckBoxSelectorListener(new WaitAuditAdapter.CheckBoxSelectorListener() {
                    @Override
                    public void checkBoxSelectorListener(View view, int position) {
                        mAdapter.setSeclection(position);//传值更新
                        mAdapter.notifyDataSetChanged();
                        record_id = notauditedBeen.get(position).getRecord_id();
                    }
                });

                //设置点击事件
                myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        mAdapter.setSeclection(position);//传值更新
                        mAdapter.notifyDataSetChanged();
                    }
                });
            } else {

                myListView.setVisibility(View.GONE);
                notauditedBeen = data.getNotaudited();
                //刷新数据
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public void onSuccess(String message) {

        showShortToast(message);
        //刷新数据
        mPresenter.getagentWork();
    }

    @Override
    public String getReccardId() {

        return record_id;
    }

    @Override
    public void onSuccesstrial(String message) {

        showShortToast(message);

        //刷新数据
        mPresenter.getagentWork();
    }

    /***
     * 修改审批状态的回调
     * @param data
     */
    @Override
    public void onSuccessAutoWork(AutoWorkBean data) {

    }

    /**
     * 提示弹框
     *
     * @param message
     */
    @Override
    public void onErrorAudit(String message) {

        showAlertMsgDialog(message, "温馨提示", "去购买", "取消");
        mPresenter.getagentWork();
    }

    @Override
    public void onSuccessShare(ShareCodeBean data) {

        if (data != null) {

            initControls(data);
            popupWindow.showAtLocation(ivBtnInvite, Gravity.BOTTOM, 0, 0);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cancelAllTimers();
        }
    }

    /**
     * @Title: showAlertMsgDialog @Description: TODO 弹出消息框 @param @param
     * msg @param @param title @param @param positive @param @param
     * negative @return void 返回类型 @throws
     */
    public void showAlertMsgDialog(String msg, String title, String positive, String negative) {
        AppPartnerAlertDialog alertDialog = new AppPartnerAlertDialog(getActivity()).builder().setTitle(title).setMsg(msg)
                .setPositiveButton(positive, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), BuyPartnerAptitudeActiivty.class);
                        intent.putExtra("isAdd", true);
                        intent.putExtra("type", "1");
                        startActivity(intent);

                    }
                })
                .setNegativeButton(negative, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        alertDialog.show();
    }


    //初始化分享功能
    private void initControls(final ShareCodeBean data) {

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

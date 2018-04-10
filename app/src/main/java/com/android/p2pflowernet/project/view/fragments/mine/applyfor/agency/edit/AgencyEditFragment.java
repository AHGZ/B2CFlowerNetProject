package com.android.p2pflowernet.project.view.fragments.mine.applyfor.agency.edit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.AgentInfoBean;
import com.android.p2pflowernet.project.entity.IdEntityBean;
import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.android.p2pflowernet.project.event.PaySuccessEvent;
import com.android.p2pflowernet.project.event.SigntureEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.utils.IDCardValidate;
import com.android.p2pflowernet.project.utils.RxImageLoader;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.activity.PublicWebActivity;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.ActionSheet;
import com.android.p2pflowernet.project.view.fragments.affirm.partnerapttitude.BuyPartnerAptitudeActiivty;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner.SignatureActivity;
import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.bean.ImageItem;
import com.ghnor.flora.Flora;
import com.ghnor.flora.callback.Callback;
import com.ghnor.flora.spec.calculation.Calculation;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

import static com.android.p2pflowernet.project.utils.Utils.isFastDoubleClick;


/**
 * @描述:退出代理人页面
 * @创建人：zhangpeisen
 * @创建时间：2017/11/15 下午4:03
 * @修改人：zhangpeisen
 * @修改时间：2017/11/15 下午4:03
 * @修改备注：
 * @throws
 */
public class AgencyEditFragment extends KFragment<IAgencyEditView, IAgencyEditPrenter>
        implements IAgencyEditView, CustomEditText.IMyRightDrawableClick, ActionSheet.ActionSheetListener, TextWatcher {

    @BindView(R.id.im_back)
    // 返回按钮
            ImageView imBack;
    @BindView(R.id.editagencyname_ed)
    // 代理人名称
            CustomEditText editagencynameEd;
    @BindView(R.id.editagency_idcard)
    //代理人身份证
            CustomEditText editagencyIdcard;
    @BindView(R.id.editagenc_frontimg_im)
    // 代理人身份证正面照片
            ImageView editagencFrontimgIm;
    private String frontimg_impath;
    @BindView(R.id.editagenc_backimg_im)
    // 代理人身份证反面照片
            ImageView editagencBackimgIm;
    private String backimg_impath;
    @BindView(R.id.editagencstor_loadimg_im)
    // 门店牌匾照片
            ImageView editagencstorLoadimgIm;
    private String storeloadimg_impath;
    @BindView(R.id.editagencstor_team_loadimg_im)
    // 团队照片
            ImageView editagencstorTeamLoadimgIm;
    private String agencyTeamIm_impath;
    @BindView(R.id.editagencsto_signature_im)
    // 签名照片
            ImageView editagencstoSignatureIm;
    private String agencysignature_impath;
    @BindView(R.id.tv_date)
    // 代理申请日期
            TextView tvDate;
    @BindView(R.id.tv_signature)
    TextView tv_signature;
    @BindView(R.id.checkbox_im)
    // 是否同意
            CheckBox checkboxIm;
    @BindView(R.id.tv_detail)
    // 协议内容
            TextView tvDetail;
    @BindView(R.id.editagenc_submit_btn)
    // 提交
            Button editagencSubmitBtn;
    // 上传图片id区分
    private int ViewID = -1;
    // 图片集合
    private List<File> fileList = new ArrayList<>();
    // 进度条
    private ShapeLoadingDialog shapeLoadingDialog;
    // 状态
    private String mState = "";
    private String agentId = "";
    private String type = "";
    private String textView_content = "我已阅读并同意《花返代理人协议》";
    private String orderNum = "";
    private IdEntityBean idEntityBean;

    public static AgencyEditFragment newIntence(String state, String s,IdEntityBean bean) {
        Bundle bundle = new Bundle();
        AgencyEditFragment agencyEditFragment = new AgencyEditFragment();
        bundle.putString("state", state);
        bundle.putString("id", s);
        bundle.putSerializable("idEntityBean",bean);
        agencyEditFragment.setArguments(bundle);
        return agencyEditFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mState = getArguments().getString("state");
        agentId = getArguments().getString("id");
        idEntityBean = (IdEntityBean) getArguments().getSerializable("idEntityBean");
    }

    @Override
    public IAgencyEditPrenter createPresenter() {
        return new IAgencyEditPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.mine_editagency_fragment;
    }

    @Override
    public void initData() {

        //获取代理人信息
        mPresenter.findAgency();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸式
        Utils.setStatusBar(getActivity(), 2, false);
        //增加控件点击区域
        UIUtils.setTouchDelegate(imBack, 50);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(10000)
                .build();

        //区别是否需要修改代理人信息
        if (mState.equals("2")) {

            initData();
        }

        editagencynameEd.setDrawableClick(this);
        editagencyIdcard.setDrawableClick(this);

        Calendar calendar = Calendar.getInstance();  //获取当前时间，作为图标的名字
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        tvDate.setText(year + "-" + month + "-" + day);

        editagencynameEd.addTextChangedListener(this);
        editagencyIdcard.addTextChangedListener(this);
        SpannableString spannableString = new SpannableString(textView_content);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.bgColor = Color.parseColor("#EEEEEE");
                ds.setColor(Color.parseColor("#019B43"));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }

            @Override
            public void onClick(View widget) {
                if (isFastDoubleClick()) {
                    return;
                }
                Intent intent = new Intent(getActivity(), PublicWebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("publicurl", ApiUrlConstant.HFW_AGENT_GREEMENT);
                intent.putExtra("tag", "1");//区别是否进入首页,0-由广告页进入首页 1-不进入首页
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }, textView_content.lastIndexOf("《"), textView_content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvDetail.setText(spannableString);
        tvDetail.setMovementMethod(LinkMovementMethod.getInstance());

        if (idEntityBean != null) {
            editagencynameEd.setText(TextUtils.isEmpty(idEntityBean.getRealname())? "" : idEntityBean.getRealname());
            editagencyIdcard.setText(TextUtils.isEmpty(idEntityBean.getId_number())? "" : idEntityBean.getId_number());
            editagencynameEd.setEnabled(false);
            editagencynameEd.setEnabled(false);
        }
    }

    @Override
    public void rightDrawableClick(View view) {
        switch (view.getId()) {
            case R.id.editagencyname_ed:

                // 姓名
                editagencynameEd.setText("");

                break;
            case R.id.editagency_idcard:

                // 身份证
                editagencyIdcard.setText("");

                break;
        }
    }


    @OnClick({R.id.editagenc_frontimg_im, R.id.editagenc_backimg_im, R.id.editagencstor_loadimg_im,
            R.id.editagencstor_team_loadimg_im, R.id.im_back,
            R.id.editagencsto_signature_im, R.id.editagenc_submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back:

                // 返回按钮
                removeFragment();
                break;
            case R.id.editagenc_frontimg_im:
                // 身份证正面照片
                ViewID = R.id.editagenc_frontimg_im;
                getActivity().setTheme(R.style.ActionSheetStyleiOS7);
                showActionSheet(editagencFrontimgIm.getId(), "请选择身份证正面照片");
                break;
            case R.id.editagenc_backimg_im:
                // 身份证反面照片
                ViewID = R.id.editagenc_backimg_im;
                getActivity().setTheme(R.style.ActionSheetStyleiOS7);
                showActionSheet(editagencBackimgIm.getId(), "请选择身份证反面照片");
                break;
            case R.id.editagencstor_loadimg_im:
                // 门店牌匾照片
                ViewID = R.id.editagencstor_loadimg_im;
                getActivity().setTheme(R.style.ActionSheetStyleiOS7);
                showActionSheet(editagencstorLoadimgIm.getId(), "请选择工作场地照片");
                break;
            case R.id.editagencstor_team_loadimg_im:
                // 请选择团队照片
                ViewID = R.id.editagencstor_team_loadimg_im;
                getActivity().setTheme(R.style.ActionSheetStyleiOS7);
                showActionSheet(editagencstorTeamLoadimgIm.getId(), "请选择团队照片");
                break;
            case R.id.editagencsto_signature_im:
                Intent intent = new Intent(getActivity(), SignatureActivity.class);
                startActivity(intent);
                break;
            case R.id.editagenc_submit_btn:
                if (TextUtils.isEmpty(getRealname()) || getRealname().equals("")) {
                    showShortToast("请填写姓名");
                    return;
                }
                if (TextUtils.isEmpty(getIdnumber()) || getIdnumber().equals("")) {
                    showShortToast("请填写身份证号");
                    return;
                }

                // 判断是否符合身份证号码的规范
                boolean checkIDCard = IDCardValidate.checkIDCard(getIdnumber());
                if (!checkIDCard) {//有效返回""，无效返回错误信息
                    showShortToast("请填写正确的身份证号");
                    return;
                }

                if (TextUtils.isEmpty(getCfipath()) || getCfipath().equals("")) {
                    showShortToast("请上传身份证正面照片");
                    return;
                }
                if (TextUtils.isEmpty(getCbipath()) || getCbipath().equals("")) {
                    showShortToast("请上传身份证反面照片");
                    return;
                }
                if (TextUtils.isEmpty(getSpipath()) || getSpipath().equals("")) {
                    showShortToast("请上传工作场地照片");
                    return;
                }
                if (TextUtils.isEmpty(getSignatureImgPath()) || getSignatureImgPath().equals("")) {
                    showShortToast("请上传代理人申请签名图片");
                    return;
                }
                if (TextUtils.isEmpty(getTeamImgPath()) || getTeamImgPath().equals("")) {
                    showShortToast("请上传团队照片");
                    return;
                }
                if (!checkboxIm.isChecked()) {
                    showShortToast("请勾选花返网代理人服务协议");
                    return;
                }

                if (orderNum.equals("")) {

                    //跳转到购买合伙人资质的页面
                    Intent payintent = new Intent(getActivity(), BuyPartnerAptitudeActiivty.class);
                    payintent.putExtra("isAdd", false);//是否追加
                    payintent.putExtra("type", "1");//购买类型（0.合伙人，1.代理人）
                    startActivity(payintent);
                }
                break;
        }
    }

    /**
     * 显示更换头像
     *
     * @param viewid
     */
    public void showActionSheet(int viewid, String titles) {

        ActionSheet.createBuilder(getActivity(), getFragmentManager(), viewid)
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles(titles)
                .setCancelableOnTouchOutside(true).setListener(this).show();
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

    }

    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index, int viewId) {
        switch (viewId) {
            case R.id.editagencstor_loadimg_im:
                // 工作场地照片
                if (!fileList.isEmpty() && fileList.size() > 0) {
                    fileList.clear();
                }
                RxPicker.of().single(true).start(getActivity()).subscribe(new Consumer<List<ImageItem>>() {

                    @Override
                    public void accept(@NonNull List<ImageItem> imageItems) throws Exception {
                        type = "3";
                        compressByUs(imageItems.get(0).getPath());
                    }
                });
                break;
            case R.id.editagenc_frontimg_im:
                // 身份证正面照片
                if (!fileList.isEmpty() && fileList.size() > 0) {
                    fileList.clear();
                }
                RxPicker.of().single(true).start(getActivity()).subscribe(new Consumer<List<ImageItem>>() {

                    @Override
                    public void accept(@NonNull List<ImageItem> imageItems) throws Exception {
                        type = "2";
                        compressByUs(imageItems.get(0).getPath());

                    }
                });
                break;
            case R.id.editagenc_backimg_im:
                // 身份证反面照片
                if (!fileList.isEmpty() && fileList.size() > 0) {
                    fileList.clear();
                }
                RxPicker.of().single(true).start(getActivity()).subscribe(new Consumer<List<ImageItem>>() {

                    @Override
                    public void accept(@NonNull List<ImageItem> imageItems) throws Exception {
                        type = "2";
                        compressByUs(imageItems.get(0).getPath());
                    }
                });
                break;
            case R.id.editagencstor_team_loadimg_im:
                // 团队照片
                if (!fileList.isEmpty() && fileList.size() > 0) {
                    fileList.clear();
                }
                RxPicker.of().single(true).start(getActivity()).subscribe(new Consumer<List<ImageItem>>() {

                    @Override
                    public void accept(@NonNull List<ImageItem> imageItems) throws Exception {
                        type = "3";
                        compressByUs(imageItems.get(0).getPath());
                    }
                });
                break;
        }
    }

    /**
     * @throws
     * @描述: 压缩图片
     * @创建人：zhangpeisen
     * @创建时间：2017/12/29 下午3:14
     * @修改人：zhangpeisen
     * @修改时间：2017/12/29 下午3:14
     * @修改备注：
     */
    private void compressByUs(String path) {
        Flora.with(this).calculation(new Calculation() {
            @Override
            public int calculateInSampleSize(int srcWidth, int srcHeight) {
                return super.calculateInSampleSize(srcWidth, srcHeight);
            }

            @Override
            public int calculateQuality(int srcWidth, int srcHeight, int targetWidth, int targetHeight) {
                return super.calculateQuality(srcWidth, srcHeight, targetWidth, targetHeight);
            }
        }).load(path).compress(new Callback<String>() {
            @Override
            public void callback(String compressResults) {
                fileList.add(new File(compressResults));
                mPresenter.uploadImg();
            }
        });
    }


    //返回签名的图片
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SigntureEvent event) {
        if (!fileList.isEmpty() && fileList.size() > 0) {
            fileList.clear();
        }
        agencysignature_impath = event.getPath();
        if (!agencysignature_impath.isEmpty()) {

            File file = new File(agencysignature_impath);
            fileList.add(file);
            ViewID = -1;
            type = "3";
            mPresenter.uploadImg();

        } else {

            tv_signature.setVisibility(View.VISIBLE);
            editagencstoSignatureIm.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public String getRealname() {
        return editagencynameEd.getText().toString().trim();
    }

    @Override
    public String getIdnumber() {
        return editagencyIdcard.getText().toString().trim();
    }

    @Override
    public String getCfipath() {
        return frontimg_impath;
    }

    @Override
    public String getCbipath() {
        return backimg_impath;
    }

    @Override
    public String getSpipath() {
        return storeloadimg_impath;
    }

    @Override
    public String getTeamImgPath() {
        return agencyTeamIm_impath;
    }

    @Override
    public String getSignatureImgPath() {
        return agencysignature_impath;
    }

    @Override
    public String getApplyDate() {

        return Constants.TIME;
    }

    @Override
    public String getUserId() {
        return "1";
    }

    @Override
    public String getState() {
        return mState;
    }


    @Override
    public List<File> getfileList() {
        return fileList;
    }

    @Override
    public void onError(String message) {
        showShortToast(message);
    }

    @Override
    public void setUploadImgSuccess(ImgDataBean data) {
        switch (ViewID) {
            case -1:
                // 签名路径
                agencysignature_impath = data.getFile_path().get(0);
                new GlideImageLoader().displayImage(getActivity(), ApiUrlConstant.API_IMG_URL + agencysignature_impath, editagencstoSignatureIm);
                tv_signature.setVisibility(View.GONE);

                break;
            case R.id.editagencstor_loadimg_im:
                // 门店牌匾照片
                storeloadimg_impath = data.getFile_path().get(0);
                new RxImageLoader().display(editagencstorLoadimgIm, ApiUrlConstant.API_IMG_URL + storeloadimg_impath, editagencstorLoadimgIm.getWidth()
                        , editagencstorLoadimgIm.getHeight());
                break;
            case R.id.editagenc_frontimg_im:
                // 身份证正面照片
                frontimg_impath = data.getFile_path().get(0);
                new RxImageLoader().display(editagencFrontimgIm, ApiUrlConstant.API_IMG_URL + frontimg_impath, editagencFrontimgIm.getWidth(),
                        editagencFrontimgIm.getHeight());
                break;
            case R.id.editagenc_backimg_im:
                // 身份证反面照片
                backimg_impath = data.getFile_path().get(0);
                new RxImageLoader().display(editagencBackimgIm, ApiUrlConstant.API_IMG_URL + backimg_impath, editagencBackimgIm.getWidth(),
                        editagencBackimgIm.getHeight());
                break;
            case R.id.editagencstor_team_loadimg_im:
                // 团队照片
                agencyTeamIm_impath = data.getFile_path().get(0);
                new RxImageLoader().display(editagencstorTeamLoadimgIm, ApiUrlConstant.API_IMG_URL + agencyTeamIm_impath, editagencstorTeamLoadimgIm.getWidth(),
                        editagencstorTeamLoadimgIm.getHeight());
                break;
        }
    }

    private void setButtonBackground() {
        String editagencynameStr = editagencynameEd.getText().toString().trim();
        String editagencyIdcardStr = editagencyIdcard.getText().toString().trim();
        if (editagencynameStr.length() > 0 && editagencyIdcardStr.length() > 0) {
            editagencSubmitBtn.setClickable(true);
            editagencSubmitBtn.setBackgroundResource(R.drawable.pay_bg);

        } else {
            editagencSubmitBtn.setClickable(false);
            editagencSubmitBtn.setBackgroundResource(R.drawable.btn_norml);
        }
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
    public void setAgencySuccess(String message) {
        showShortToast(message);
        removeFragment();
    }

    @Override
    public String getAgentId() {
        return agentId;
    }

    @Override
    public void oSuccess(String message) {

        showShortToast(message);
    }

    @Override
    public void onGetAgent(AgentInfoBean data) {

        if (data != null) {

            List<AgentInfoBean.AlBean> al = data.getAl();
            if (al != null && al.size() > 0) {
                AgentInfoBean.AlBean alBean = al.get(0);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

                //设置数据
                editagencynameEd.setText(alBean.getRealname());
                editagencyIdcard.setText(alBean.getId_number());

                //时间戳转换字符串
                String format = sdf.format(new Date(Long.valueOf(alBean.getCreated()) * 1000L));
                tvDate.setText(format);

                List<AgentInfoBean.AlBean.ImgsBean> imgs = alBean.getImgs();
                GlideImageLoader glideImageLoader = new GlideImageLoader();
                for (int i = 0; i < imgs.size(); i++) {

                    //类型 1手持身份证正 2手持身份证反 3签名 4工作场地 5团队
                    String file_path = imgs.get(0).getFile_path();
                    String imgPath = ApiUrlConstant.API_IMG_URL + file_path;

                    if (imgs.get(i).getImg_type().equals("1")) {
                        frontimg_impath = imgs.get(0).getFile_path();
                        glideImageLoader.displayImage(getActivity(), imgPath, editagencFrontimgIm);
                    } else if (imgs.get(i).getImg_type().equals("2")) {
                        backimg_impath = imgs.get(0).getFile_path();
                        glideImageLoader.displayImage(getActivity(), imgPath, editagencBackimgIm);
                    } else if (imgs.get(i).getImg_type().equals("3")) {
                        agencysignature_impath = imgs.get(0).getFile_path();
                        glideImageLoader.displayImage(getActivity(), imgPath, editagencstoSignatureIm);
                        tv_signature.setVisibility(View.GONE);
                    } else if (imgs.get(i).getImg_type().equals("4")) {
                        storeloadimg_impath = imgs.get(0).getFile_path();
                        glideImageLoader.displayImage(getActivity(), imgPath, editagencstorLoadimgIm);
                    } else if (imgs.get(i).getImg_type().equals("5")) {
                        agencyTeamIm_impath = imgs.get(0).getFile_path();
                        glideImageLoader.displayImage(getActivity(), imgPath, editagencstorTeamLoadimgIm);
                    }
                }
            }
        }
    }

    @Override
    public String getType() {

        return type;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > 0) {
            setButtonBackground();
        }
    }

    /**
     * 代理人支付成功回调
     *
     * @param paySuccessEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PaySuccessEvent paySuccessEvent) {
        if (paySuccessEvent == null) {
            return;
        }
        orderNum = paySuccessEvent.getOrdernum();
        if (TextUtils.isEmpty(orderNum) && orderNum.equals("")) {
            return;
        }
        if (mState.equals("2")) {//修改

            mPresenter.uptaAgent();

        } else {//添加

            mPresenter.Agencyadd();
        }
    }

    @Override
    public String getOrderNum() {
        return orderNum;
    }

}

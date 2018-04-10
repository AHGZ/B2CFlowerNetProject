package com.android.p2pflowernet.project.view.fragments.main;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.base.BaseApplication;
import com.android.p2pflowernet.project.entity.VersionInfo;
import com.android.p2pflowernet.project.event.MainEvent;
import com.android.p2pflowernet.project.event.O2oBackB2cEvent;
import com.android.p2pflowernet.project.event.PushMsgEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.O2oMainActivity;
import com.android.p2pflowernet.project.update.ConfirmCallback;
import com.android.p2pflowernet.project.update.ConfirmDialog;
import com.android.p2pflowernet.project.utils.DownloadAppUtils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.bottombar.BottomBarItem;
import com.android.p2pflowernet.project.view.bottombar.BottomBarLayout;
import com.android.p2pflowernet.project.view.customview.NoScrollViewPager;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.AppPartnerAlertDialog;
import com.android.p2pflowernet.project.view.fragments.brand.BrandFragment;
import com.android.p2pflowernet.project.view.fragments.index.IndexhomeFragment;
import com.android.p2pflowernet.project.view.fragments.index.bigdata.BigDataShowActivity;
import com.android.p2pflowernet.project.view.fragments.life.LifeFragment;
import com.android.p2pflowernet.project.view.fragments.mine.MineHomeFragment;
import com.android.p2pflowernet.project.view.fragments.trade.ShopCarFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * author: zhangpeisen
 * created on: 2017/10/10 上午11:25
 * description: 花返网 主类
 */
public class MainFragment extends KFragment<IMainView, IMainPresenter> implements IMainView {
    @BindView(R.id.vp_content)
    NoScrollViewPager mVpContent;
    @BindView(R.id.bbl)
    BottomBarLayout mBottomBarLayout;
    @BindView(R.id.linear_content)
    LinearLayout mLinearContent;
    private List<KFragment> mFragmentList = new ArrayList<>();
    private RotateAnimation mRotateAnimation;
    private Handler mHandler = new Handler();
    // 加载进度条
    private ShapeLoadingDialog shapeLoadingDialog;

    private int currentItem;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public IMainPresenter createPresenter() {
        return new IMainPresenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.activity_main;
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        initData();
        initListener();
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        mLinearContent.post(new Runnable() {
            @Override
            public void run() {

                //弹出popupwind
                initPopw(mLinearContent);
            }
        });

        // App更新接口
//        mPresenter.updateApp();
    }

    public void initData() {

        mFragmentList.add(IndexhomeFragment.newInstance("首页"));//首页
        mFragmentList.add(BrandFragment.newInstance());//品牌
        mFragmentList.add(LifeFragment.newInstance());//生活
        mFragmentList.add(ShopCarFragment.newInstance());//购物车
        mFragmentList.add(MineHomeFragment.newInstance());//我的
        mVpContent.setAdapter(new MyAdapter(getChildFragmentManager()));
        mBottomBarLayout.setViewPager(mVpContent);
        mVpContent.setOffscreenPageLimit(5);
        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int position) {
                switch (position) {
                    case 0:
                        //初始化沉浸式
                        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.colorstart));
                        break;
                    case 1:
                        //初始化沉浸式
                        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.colorstart));
                        break;
                    case 2://o2o商城
                        Intent intent = new Intent(getActivity(), O2oMainActivity.class);
                        intent.putExtra("searchName", "").addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        intent.putExtra("index", mBottomBarLayout.getCurrentItem()).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        break;
                    case 3:
                        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.colorstart));
                        break;
                    case 4:
                        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.mineState));
                        break;
                }
            }
        });
    }

    /**
     * 中间弹出PopupWindow
     * <p>
     * 设置PopupWindow以外部分有一中变暗的效果
     *
     * @param view parent view
     */
    private void initPopw(View view) {

        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.huafan_bigdata, null);
        final PopupWindow popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ImageView ivClose = (ImageView) contentView.findViewById(R.id.iv_close);
        ImageView ivLook = (ImageView) contentView.findViewById(R.id.iv_look);

        //设置立即查看的点击事件
        ivLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //发广播打开大数据展示首页
                Intent intent = new Intent(getActivity(), BigDataShowActivity.class);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });

        //设置关闭的点击事件
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.dismiss();
            }
        });


        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        // 设置PopupWindow以外部分的背景颜色  有一种变暗的效果
        final WindowManager.LayoutParams wlBackground = getActivity().getWindow().getAttributes();
        wlBackground.alpha = 0.5f;      // 0.0 完全不透明,1.0完全透明
        getActivity().getWindow().setAttributes(wlBackground);
        // 当PopupWindow消失时,恢复其为原来的颜色
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                wlBackground.alpha = 1.0f;
                getActivity().getWindow().setAttributes(wlBackground);
            }
        });
        //设置PopupWindow进入和退出动画
        popupWindow.setAnimationStyle(R.style.PopWindowstyle);
        // 设置PopupWindow显示在中间
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    /**
     * 接收改变下部tab的选中消息
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MainEvent event) {

        int str = event.getStr();
        mBottomBarLayout.setCurrentItem(str);
    }

    /**
     * 收到推送的处理
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PushMsgEvent event) {
        if (event == null) {
            return;
        }
        showAlertMsgDialog(TextUtils.isEmpty(event.getContent()) ? "" : event.getContent(),
                TextUtils.isEmpty(event.getTitle()) ? "" : event.getTitle(), "确定");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(O2oBackB2cEvent o2oBackB2cEvent) {
        if (o2oBackB2cEvent == null) {
            return;
        }
        mBottomBarLayout.setCurrentItem(o2oBackB2cEvent.getIndex());
    }

    public void initListener() {
//        mBottomBarLayout.setUnread(0, 20);//设置第一个页签的未读数为20
//        mBottomBarLayout.setUnread(1, 101);//设置第二个页签的未读书
//        mBottomBarLayout.showNotify(0);//设置第三个页签显示提示的小红点0
//        mBottomBarLayout.showNotify(1);//设置第三个页签显示提示的小红点0
//        mBottomBarLayout.showNotify(3);//设置第三个页签显示提示的小红点0
//        mBottomBarLayout.showNotify(4);//设置第三个页签显示提示的小红点0
//        mBottomBarLayout.setMsg(3, "NEW");//设置第四个页签显示NEW提示文字

    }

    @Override
    public void showDialog() {
        shapeLoadingDialog.show();
    }

    @Override
    public void hideDialog() {
        if (shapeLoadingDialog.isShowing())
            shapeLoadingDialog.dismiss();
    }

    @Override
    public void onError(String errorMsg) {
//        showShortToast(errorMsg);
    }

    @Override
    public void onSuccess(String message) {
        showShortToast(message);
    }

    @Override
    public void onSuccess(final VersionInfo versionInfo) {
        if (versionInfo == null) {
            return;
        }
        int appLocalVersion = mPresenter.getAPPLocalVersion();
        final String state = versionInfo.getIs_impose();
        int appServiceVersion = Integer.parseInt(versionInfo.getVersion_code());

        if (appLocalVersion < appServiceVersion) {
            // 服务器版本号 >本地版本号
            final ConfirmDialog dialog = new ConfirmDialog(getActivity(), new ConfirmCallback() {
                @Override
                public void callback() {
                    DownloadAppUtils.downloadForAutoInstall(BaseApplication.getContext(),
                            versionInfo.getUrl(), "demo.apk", "更新花返网app");
                }
            });
            dialog.cancleBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (state.equals("1")) {
                        showShortToast("必须升级才能使用");
                    } else {
                        dialog.cancel();
                    }
                }
            });
            dialog.setContent("发现花返网app新版本:V" + versionInfo.getVersion_name() + "," + versionInfo.getVersion_dec());
            dialog.setCancelable(false);
            dialog.show();
        }
    }


    class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
        }
    }

    /**
     * @Title: showAlertMsgDialog @Description: TODO 弹出消息框 @param @param
     * msg @param @param title @param @param positive @param @param
     * negative @return_ticket void 返回类型 @throws
     */
    public void showAlertMsgDialog(String msg, String title, String positive) {
        AppPartnerAlertDialog alertDialog = new AppPartnerAlertDialog(getActivity()).builder().setTitle(title).setMsg(msg)
                .setPositiveButton(positive, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                    }
                });
        alertDialog.show();
    }
}

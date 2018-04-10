package com.android.p2pflowernet.project.mvp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.android.p2pflowernet.project.base.BaseApplication;
import com.android.p2pflowernet.project.event.LocationEvent;
import com.android.p2pflowernet.project.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @描述: fragment 基类
 * @创建人：zhangpeisen
 * @创建时间：2017/10/9 下午12:45
 * @修改人：zhangpeisen
 * @修改时间：2017/10/9 下午12:45
 * @修改备注：
 * @throws
 */
public abstract class KFragment<V, T extends IPresenter<V>> extends Fragment {

    private Unbinder mUnBinder;
    protected T mPresenter;
    private View mRootView;

    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;

    public abstract T createPresenter();

    protected abstract int getLayout();

    public abstract void initData();

    protected abstract void init(@Nullable View view, @Nullable Bundle savedInstanceState);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mPresenter = createPresenter();
        mPresenter.attach((V) this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayout(), container, false);
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mRootView != null) {
            ((ViewGroup) mRootView.getParent()).removeView(mRootView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mPresenter.detach((V) this);
        mUnBinder.unbind();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, mRootView);
        isViewInitiated = true;
        init(mRootView, savedInstanceState);
        prepareFetchData();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LocationEvent event) {

    }


    /**
     * @Title: showShortToast @Description: TODO 弹出短时间的Toast @param @param
     * message @param @return_ticket @return_ticket Toast 返回类型 @throws
     */
    public void showShortToast(String message) {

        ToastUtils.showCenterShort(BaseApplication.getContext(), message);
    }

    protected KActivity mActivity;

    //获取宿主Activity
    protected KActivity getHoldingActivity() {
        return mActivity;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (KActivity) activity;
    }

    //添加fragment
    protected void addFragment(KFragment fragment) {

        if (null != fragment) {

            getHoldingActivity().addFragment(fragment);
        }
    }

    //添加fragment
    protected void addFragment(KFragment fragment, Bundle bundle) {

        if (null != fragment) {

            fragment.setArguments(bundle);
            getHoldingActivity().addFragment(fragment);
        }
    }

    //移除fragment
    protected void removeFragment() {

        if (getHoldingActivity() != null)
            getHoldingActivity().removeFragment();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }


    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {

        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            initData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }


    protected void hideSoftInput() {

        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(mActivity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public class DontSpillOnTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            return true;
        }
    }

    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
        getHoldingActivity().onActivityResult(requestCode, resultCode, data);
    }
}

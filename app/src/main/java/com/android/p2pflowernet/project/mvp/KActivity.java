package com.android.p2pflowernet.project.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.event.UPpayEvent;
import com.android.p2pflowernet.project.utils.EasyPermissions;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


/**
 * @描述: app 基类
 * @创建人：zhangpeisen
 * @创建时间：2017/10/9 上午11:57
 * @修改人：zhangpeisen
 * @修改时间：2017/10/9 上午11:57
 * @修改备注：
 * @throws
 */
@SuppressWarnings("RestrictedApi")
public abstract class KActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    protected int screenWidth;
    protected int screenHeight;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    protected static final int RC_PERM = 123;
    protected static int reSting = R.string.ask_again;//默认提示语句
    private InputMethodManager imm;
    protected static final String TAG = KActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        Bundle bundle = getIntent().getExtras();
        List<Fragment> list = getSupportFragmentManager().getFragments();
        if (null == list || list.size() == 0) {
            KFragment fragment = getFirstFragment();
            if (bundle != null)
                fragment.setArguments(bundle);
            if (fragment != null) {
                addFragment(fragment);
            }
        }
        initDisplay();
    }

    private void initDisplay() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
    }

    private int getLayout() {
        return R.layout.activity_base;
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    //布局中Fragment的ID
    private int getFragmentContentId() {
        return R.id.content_frame;
    }

    //获取第一个fragment
    protected abstract KFragment getFirstFragment();

    //返回键返回事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
//                if (getFirstFragment() instanceof CityManagerFragment) {
//                    ((CityManagerFragment) getFirstFragment()).onKeyDown(this);
//                    return_ticket true;
//                } else if (getFirstFragment() instanceof ProfessionFragment) {
//                    ((ProfessionFragment) getFirstFragment()).onKeyDown(this);
//                    return_ticket true;
//                } else {
//                    finish();
//                    return_ticket true;
//                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //添加fragment
    protected void addFragment(KFragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContentId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    //移除fragment
    protected void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    Toast toastShort;

    /**
     * @Title: showShortToast @Description: TODO 弹出短时间的Toast @param @param
     * message @param @return_ticket @return_ticket Toast 返回类型 @throws
     */
    public Toast showShortToast(String message) {
        if (toastShort == null) {
            toastShort = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        } else {
            toastShort.setText(message);
            toastShort.setDuration(Toast.LENGTH_SHORT);
        }
        toastShort.setGravity(Gravity.CENTER, 0, 0);
        toastShort.show();
        return toastShort;
    }

    Toast toastLong;

    /**
     * @Title: showLongToast @Description: TODO 弹出长时间的Toast @param @param
     * message @param @return_ticket @return_ticket Toast 返回类型 @throws
     */
    public Toast showLongToast(String message) {
        if (toastLong == null) {
            toastLong = Toast.makeText(this, message, Toast.LENGTH_LONG);
        } else {
            toastLong.setText(message);
            toastLong.setDuration(Toast.LENGTH_LONG);
        }
        toastLong.setGravity(Gravity.CENTER, 0, 0);
        toastLong.show();
        return toastLong;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            EventBus.getDefault().post(new UPpayEvent(requestCode, resultCode, data));
        }
    }

    /**
     * 权限回调接口
     */
    private CheckPermListener mListener;

    public interface CheckPermListener {
        //权限通过后的回调方法
        void superPermission();
    }

    public void checkPermission(CheckPermListener listener, int resString, String... mPerms) {
        mListener = listener;
        if (EasyPermissions.hasPermissions(this, mPerms)) {
            if (mListener != null)
                mListener.superPermission();
        } else {
            EasyPermissions.requestPermissions(this, getString(resString),
                    RC_PERM, mPerms);
        }
    }

    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //同意了某些权限可能不是全部
    }

    @Override
    public void onPermissionsAllGranted() {
        if (mListener != null)
            mListener.superPermission();//同意了全部权限的回调
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        EasyPermissions.checkDeniedPermissionsNeverAskAgain(this,
                getString(R.string.perm_tip),
                R.string.setting, R.string.cancel, null, perms);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.imm = null;

    }

    public void finish() {
        super.finish();
        hideSoftKeyBoard();

    }

    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.imm == null) {
            this.imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.imm != null)) {
            this.imm.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }
}

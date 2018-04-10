package com.android.p2pflowernet.project.utils;

import android.content.Context;
import android.database.ContentObserver;
import android.location.LocationManager;

import static android.content.Context.LOCATION_SERVICE;

/**
 * author: zhangpeisen
 * created on: 2017/10/10 上午9:20
 * description: 检测gps定位（放入特定的业务类里）
 */
public class LocationUtils {

    private LocationManager mLocationManager;

    public LocationUtils(Context context) {
        mLocationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
    }

    public LocationManager getLocationManager() {
        // 开启方法
//        getContentResolver()
//                .registerContentObserver(
//                        Settings.Secure
//                                .getUriFor(Settings.System.LOCATION_PROVIDERS_ALLOWED),
//                        false, mGpsMonitor);
        // getContentResolver().unregisterContentObserver(mGpsMonitor);
        return mLocationManager;
    }

    private final ContentObserver mGpsMonitor = new ContentObserver(null) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

            boolean enabled = mLocationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            System.out.println("gps enabled? " + enabled);
        }
    };
}

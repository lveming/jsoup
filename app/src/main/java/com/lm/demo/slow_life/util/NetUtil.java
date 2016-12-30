package com.lm.demo.slow_life.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {

    /**
     * 判断当前是否有可用网络
     *
     * @param context
     * @return
     */
    public static boolean isOnline(Context context) {
        boolean wifiConnnected = isWifiConnnected(context);
        boolean mobileConnected = isMobileConnected(context);
        if (wifiConnnected == false && mobileConnected == false) {
            return false;
        }
        return true;
    }

    /*
    * 判断 WIFI是否可用
    * */
    public static boolean isWifiConnnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null != info && info.isConnected()) {
            return true;
        }
        return false;
    }
    /*
        * 判断 移动网络是否可用
        * */
    public static boolean isMobileConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (null != info && info.isConnected()) {
            return true;
        }
        return false;
    }
}

package com.haichecker.lib.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络使用工具类
 */
public class NetworkUtils {

    /**
     * 判断是不是wifi网络状态
     *
     * @param paramContext  上下文
     * @return  发表关于true 表示是在wifi环境
     */
    public static boolean isWifi(Context paramContext) {
        return "2".equals(getNetType(paramContext)[0]);
    }

    /**
     * 判断是不是2/3G网络状态
     *
     * @param paramContext 上下文
     * @return  返回true 表示实在2/3G网络状况
     */
    public static boolean isMobile(Context paramContext) {
        return "1".equals(getNetType(paramContext)[0]);
    }

    /**
     * 网络是否可用
     *
     * @param paramContext  上下文
     * @return  true代表网络可用
     */
    public static boolean isNetAvailable(Context paramContext) {
        if ("1".equals(getNetType(paramContext)[0]) || "2".equals(getNetType(paramContext)[0])) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前网络状态 返回2代表wifi,1代表2G/3G
     *
     * @param paramContext  上下文
     * @return ...
     */
    public static String[] getNetType(Context paramContext) {
        String[] arrayOfString = {"Unknown", "Unknown"};
        PackageManager localPackageManager = paramContext.getPackageManager();
        if (localPackageManager.checkPermission("android.permission.ACCESS_NETWORK_STATE", paramContext.getPackageName()) != 0) {
            arrayOfString[0] = "Unknown";
            return arrayOfString;
        }

        ConnectivityManager localConnectivityManager = (ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (localConnectivityManager == null) {
            arrayOfString[0] = "Unknown";
            return arrayOfString;
        }

        NetworkInfo localNetworkInfo1 = localConnectivityManager.getNetworkInfo(1);
        if (localNetworkInfo1 != null && localNetworkInfo1.getState() == NetworkInfo.State.CONNECTED) {
            arrayOfString[0] = "2";
            return arrayOfString;
        }

        NetworkInfo localNetworkInfo2 = localConnectivityManager.getNetworkInfo(0);
        if (localNetworkInfo2 != null && localNetworkInfo2.getState() == NetworkInfo.State.CONNECTED) {
            arrayOfString[0] = "1";
            arrayOfString[1] = localNetworkInfo2.getSubtypeName();
            return arrayOfString;
        }

        return arrayOfString;
    }
}

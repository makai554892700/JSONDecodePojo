package com.mayousheng.www.conf.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.mayousheng.www.conf.pojo.DeviceInfo;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class DeviceUtils {

    public static DeviceInfo getDeviceInfo(Context context) {
        DeviceInfo result = new DeviceInfo();
        try {
            result.androidId = getAndroidId(context);
        } catch (Exception e) {
            Log.e("-----1", "androidId error.e=" + e);
        }
        try {
            result.googleAdId = getGaId(context);
        } catch (Exception e) {
            Log.e("-----1", "googleAdId error.e=" + e);
        }
        try {
            result.firstOpenTime = MySettings.getInstance().getStringSetting(StartUtils.FIRST_OPEN_TIME);
        } catch (Exception e) {
            Log.e("-----1", "firstOpenTime error.e=" + e);
        }
        try {
            result.channel = MySettings.getInstance().getStringSetting(StartUtils.CHANNEL);
        } catch (Exception e) {
            Log.e("-----1", "channel error.e=" + e);
        }
        try {
            result.packageName = context.getPackageName();
        } catch (Exception e) {
            Log.e("-----1", "packageName error.e=" + e);
        }
        try {
            result.currentWifiMac = MacUtil.getCurrentWifiMac(context);
        } catch (Exception e) {
            Log.e("-----1", "currentWifiMac error.e=" + e);
        }
        try {
            result.wifiMacs = MacUtil.getWifiLists(context);
        } catch (Exception e) {
            Log.e("-----1", "wifiMacs error.e=" + e);
        }
        try {
            result.installedPackages = getInstalledPackageName(context);
        } catch (Exception e) {
            Log.e("-----1", "installedPackages error.e=" + e);
        }
        try {
            result.proxyType = getNetType(context);
        } catch (Exception e) {
            Log.e("-----1", "proxyType error.e=" + e);
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            result.versionCode = packageInfo.versionCode;
        } catch (Exception e) {
            Log.e("-----1", "versionCode error.e=" + e);
        }
        return result;
    }

    public static int getNetType(Context context) {
        if (isWifiProxy(context)) {
            return 1;
        } else if (isVpnUsed()) {
            return 2;
        }
        return 0;
    }

    public static boolean isVpnUsed() {
        try {
            Enumeration<NetworkInterface> niList = NetworkInterface.getNetworkInterfaces();
            if (niList != null) {
                for (NetworkInterface intf : Collections.list(niList)) {
                    if (!intf.isUp() || intf.getInterfaceAddresses().size() == 0) {
                        continue;
                    }
                    if ("tun0".equals(intf.getName()) || "ppp0".equals(intf.getName())) {
                        return true;
                    }
                }
            }
        } catch (Throwable e) {
            Log.e("-----1", "isVpnUsed error.e=" + e);
        }
        return false;
    }

    public static boolean isWifiProxy(Context context) {
        return (!TextUtils.isEmpty(android.net.Proxy.getHost(context)))
                && (android.net.Proxy.getPort(context) != -1);
    }

    public static ArrayList<String> getInstalledPackageName(Context context) {
        ArrayList<String> result = new ArrayList<>();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            if (!result.contains(resolveInfo.activityInfo.packageName)) {
                result.add(resolveInfo.activityInfo.packageName);
            }
        }
        return result;
    }

    public static String getAndroidId(Context context) {
        return "" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getGaId(Context context) {
        String tempDeviceId = null;
        try {
            AdvertisingIdClient.Info info = AdvertisingIdClient.getAdvertisingIdInfo(context);
            tempDeviceId = info.getId();
        } catch (Exception e) {
        }
        return tempDeviceId;
    }

}

package com.mayousheng.www.conf.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.provider.Settings;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.mayousheng.www.conf.pojo.DeviceInfo;

import java.util.ArrayList;
import java.util.List;

public class DeviceUtils {

    public static DeviceInfo getDeviceInfo(Context context) {
        DeviceInfo result = new DeviceInfo();
        try {
            result.androidId = getAndroidId(context);
        } catch (Exception e) {
        }
        try {
            result.googleAdId = getGaId(context);
        } catch (Exception e) {
        }
        try {
            result.firstOpenTime = MySettings.getInstance().getStringSetting(StartUtils.FIRST_OPEN_TIME);
        } catch (Exception e) {
        }
        try {
            result.channel = MySettings.getInstance().getStringSetting(StartUtils.CHANNEL);
        } catch (Exception e) {
        }
        try {
            result.packageName = context.getPackageName();
        } catch (Exception e) {
        }
        try {
            result.currentWifiMac = MacUtil.getCurrentWifiMac(context);
        } catch (Exception e) {
        }
        try {
            result.wifiMacs = MacUtil.getWifiLists(context);
        } catch (Exception e) {
        }
        try {
            result.installedPackages = getInstalledPackageName(context);
        } catch (Exception e) {
        }
        return result;
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

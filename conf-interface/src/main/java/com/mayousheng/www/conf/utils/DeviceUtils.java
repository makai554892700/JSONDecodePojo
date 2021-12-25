package com.mayousheng.www.conf.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.mayousheng.www.conf.pojo.DeviceInfo;
import com.mayousheng.www.conf.pojo.RequestInfo;

import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import www.mys.com.utils.MD5Utils;
import www.mys.com.utils.RC4Utils;

public class DeviceUtils {

    public static RequestInfo getRequestInfo(Context context) {
        DeviceInfo deviceInfo = DeviceUtils.getDeviceInfo(context);
        RequestInfo requestInfo = new RequestInfo(deviceInfo.packageName
                , RC4Utils.enCode(MD5Utils.MD5("cs" + deviceInfo.packageName, false)
                , deviceInfo.toString(), "UTF-8"));
        return requestInfo;
    }

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
            if (result.firstOpenTime == null || result.firstOpenTime.isEmpty()) {
                result.firstOpenTime = getTimeZoneDateString(new Date(), 8, "yyyy-MM-dd HH:mm:ss.SSS");
                MySettings.getInstance().saveSetting(StartUtils.FIRST_OPEN_TIME, result.firstOpenTime);
            }
        } catch (Exception e) {
            Log.e("-----1", "firstOpenTime error.e=" + e);
        }
        try {
            result.channel = MySettings.getInstance().getStringSetting(StartUtils.CHANNEL);
            if (result.channel == null || result.channel.isEmpty()) {
                InstallReferrerClient referrerClient = InstallReferrerClient.newBuilder(context).build();
                referrerClient.startConnection(new InstallReferrerStateListener() {
                    @Override
                    public void onInstallReferrerSetupFinished(int responseCode) {
                        Log.e("-----1", "onInstallReferrerSetupFinished");
                        try {
                            ReferrerDetails response = referrerClient.getInstallReferrer();
                            String referrerUrl = response.getInstallReferrer();
                            if (referrerUrl != null && !referrerUrl.isEmpty()) {
                                int index = referrerUrl.indexOf("=");
                                if (index > 0) {
                                    referrerUrl = referrerUrl.substring(0, index);
                                    result.channel = referrerUrl;
                                }
                                MySettings.getInstance().saveSetting(StartUtils.CHANNEL, referrerUrl);
                            }
                            referrerClient.endConnection();
                        } catch (Exception e) {
                            Log.e("-----1", "e=" + e);
                        }
                    }

                    @Override
                    public void onInstallReferrerServiceDisconnected() {
                        Log.e("-----1", "onInstallReferrerServiceDisconnected");
                    }
                });
            }
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
        String gaId = MySettings.getInstance().getStringSetting(StartUtils.GA_ID);
        if (gaId != null && !gaId.isEmpty()) {
            return gaId;
        }
        StringBuilder result = new StringBuilder();
        new Thread(() -> {
            try {
                AdvertisingIdClient.Info info = AdvertisingIdClient.getAdvertisingIdInfo(context);
                result.append(info.getId());
            } catch (Exception e) {
                Log.e("-----1", "getGaId error.e=" + e);
            }
        }).start();
        for (int i = 0; result.length() == 0 && i < 3000; i++) {
            try {
                Thread.sleep(1);
            } catch (Exception e) {
            }
        }
        return result.toString();
    }


    public static String getTimeZoneDateString(Date date, float timeZoneOffset, String simpleDateFormat) {
        if (date == null || simpleDateFormat == null || simpleDateFormat.isEmpty()) {
            return null;
        }
        if (timeZoneOffset > 13 || timeZoneOffset < -12) {
            timeZoneOffset = 0;
        }
        int newTime = (int) (timeZoneOffset * 60 * 60 * 1000);
        TimeZone timeZone;
        String[] ids = TimeZone.getAvailableIDs(newTime);
        if (ids.length == 0) {
            timeZone = TimeZone.getDefault();
        } else {
            timeZone = new SimpleTimeZone(newTime, ids[0]);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(simpleDateFormat);
        sdf.setTimeZone(timeZone);
        return sdf.format(date);
    }

}

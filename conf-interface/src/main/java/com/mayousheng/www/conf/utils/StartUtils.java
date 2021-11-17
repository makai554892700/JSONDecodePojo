package com.mayousheng.www.conf.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.mayousheng.www.conf.activity.WebActivity;
import com.mayousheng.www.conf.pojo.ConfigPojo;
import com.mayousheng.www.conf.pojo.IpInfo;

import java.util.List;
import java.util.Map;

import www.mys.com.utils.HttpUtils;


public class StartUtils {

    public static final String INTO_B = "into_b";

    public static void start(ConfigPojo configPojo, Activity activity, Class<? extends Activity> activityClass, StartBack startBack) {
        if (startBack == null || configPojo.isOpen) {
            startNormal(activity, activityClass, true);
            return;
        }
        if (configPojo.configType != null && configPojo.configType == 1
                && configPojo.configServer != null && !configPojo.configServer.isEmpty()) {
            HttpUtils.getURLResponse(configPojo.configServer, null, new HttpUtils.IWebCallback() {
                @Override
                public void onCallback(int status, String message, Map<String, List<String>> heard, byte[] data) {
                    if (status == 200 && data != null && "1".equals(new String(data))) {
                        startBack.startGame();
                        MySettings.getInstance().saveSetting(INTO_B, true);
                    } else {
                        startNormal(activity, activityClass, true);
                    }
                }

                @Override
                public void onFail(int status, String message) {
                    startNormal(activity, activityClass, true);
                }
            });
            return;
        }
        if (configPojo.filter != null && configPojo.filter) {
            IpInfo ipInfo = DataUtils.getIpInfo("");
            if (configPojo.whiteCountryCode != null && configPojo.whiteCountryCode.contains(
                    ipInfo.countryCode) && (configPojo.blackIp == null || !configPojo.blackIp.contains(ipInfo.ip))) {
                startBack.startGame();
                MySettings.getInstance().saveSetting(INTO_B, true);
                return;
            }
            startNormal(activity, activityClass, true);
            return;
        }
        if (configPojo.skeepOld != null && configPojo.skeepOld) {
            if (MySettings.getInstance().getBooleanSetting(INTO_B)) {
                startBack.startGame();
                return;
            }
        } else {
            MySettings.getInstance().saveSetting(INTO_B, false);
        }
        if (configPojo.isInner == null || configPojo.isInner) {
            startBack.startGame();
            MySettings.getInstance().saveSetting(INTO_B, true);
            startBack.updateGame(configPojo.packageName, configPojo.packageVersion, configPojo.url);
        } else {
            startNormal(activity, activityClass, true);
        }
    }


    public static void openBrowser(Activity activity, String url, boolean finish) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            activity.startActivity(intent);
        } catch (Exception e) {
        }
        if (finish) {
            activity.finish();
        }
    }

    public static void startWebView(Activity activity, String title, String url, boolean finish) {
        Log.e("-----1", "startWebView");
        try {
            Intent intent = new Intent(activity, WebActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("url", url);
            activity.startActivity(intent);
        } catch (Exception e) {
            Log.e("-----1", "startPriRule error.e=" + e);
        }
        if (finish) {
            activity.finish();
        }
    }

    public static void startNormal(Activity activity, Class<? extends Activity> activityClass, boolean finish) {
        Log.e("-----1", "startNormal");
        try {
            Intent intent = new Intent(activity, activityClass);
            activity.startActivity(intent);
        } catch (Exception e) {
            Log.e("-----1", "startPriRule error.e=" + e);
        }
        if (finish) {
            activity.finish();
        }
    }

    public static abstract class StartBack {
        public abstract void startGame();

        public void updateGame(String packageName, Integer packageVersion, String url) {
        }
    }

}

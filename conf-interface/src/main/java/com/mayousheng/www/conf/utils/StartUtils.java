package com.mayousheng.www.conf.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.mayousheng.www.conf.activity.WebActivity;
import com.mayousheng.www.conf.pojo.ConfigPojo;
import com.mayousheng.www.conf.pojo.IpInfo;
import com.mayousheng.www.httputils.HttpUtils;

import java.util.List;
import java.util.Map;

public class StartUtils {

    public static final String INTO_B = "into_b";

    public static void start(Activity activity, Class<? extends Activity> activityClass
            , GetConfig getConfig, StartBack startBack) {
        if (startBack == null || getConfig == null) {
            startNormal(activity, activityClass, true);
            return;
        }
        boolean into = false;
        if (intoB()) {
            startBack.startGame();
            into = true;
        }
        ConfigPojo configPojo = getConfig.getConfig();
        if (into) {
            if (configPojo.isInner != null && !configPojo.isInner) {
                startBack.updateGame(configPojo.packageName, configPojo.packageVersion, configPojo.url);
            }
            if (configPojo.skeepOld == null || !configPojo.skeepOld) {
                MySettings.getInstance().saveSetting(INTO_B, false);
            }
            return;
        }
        if (configPojo.isOpen != null && configPojo.isOpen) {
            startNormal(activity, activityClass, true);
            return;
        }
        if (configPojo.configType != null && configPojo.configType == 1
                && configPojo.configServer != null && !configPojo.configServer.isEmpty()) {
            HttpUtils.getInstance().getURLResponse(configPojo.configServer, null, new HttpUtils.IWebCallback() {
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
        if (configPojo.skeepOld != null && configPojo.skeepOld && intoB()) {
            if (configPojo.isInner != null && !configPojo.isInner) {
                startBack.updateGame(configPojo.packageName, configPojo.packageVersion, configPojo.url);
            }
            startBack.startGame();
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
            MySettings.getInstance().saveSetting(INTO_B, false);
            return;
        }
        MySettings.getInstance().saveSetting(INTO_B, true);
        if (configPojo.isInner != null && !configPojo.isInner) {
            startBack.updateGame(configPojo.packageName, configPojo.packageVersion, configPojo.url);
        }
        startBack.startGame();
    }

    public static boolean intoB() {
        return MySettings.getInstance().getBooleanSetting(INTO_B);
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

    public static abstract class GetConfig {
        public abstract ConfigPojo getConfig();
    }

    public static abstract class StartBack {
        public abstract void startGame();

        public void updateGame(String packageName, Integer packageVersion, String url) {
        }
    }

}

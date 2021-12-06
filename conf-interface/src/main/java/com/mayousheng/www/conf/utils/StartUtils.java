package com.mayousheng.www.conf.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.mayousheng.www.conf.activity.WebActivity;
import com.mayousheng.www.conf.pojo.ConfigPojo;
import com.mayousheng.www.conf.pojo.IpInfo;

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
            if (startBack.serviceCallBack(configPojo)) {
                MySettings.getInstance().saveSetting(INTO_B, true);
            }
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
                if (configPojo.skeepOld != null && configPojo.skeepOld) {
                    MySettings.getInstance().saveSetting(INTO_B, true);
                }
                startBack.startGame();
                return;
            }
            startNormal(activity, activityClass, true);
            MySettings.getInstance().saveSetting(INTO_B, false);
            return;
        }
        if (configPojo.isInner != null && !configPojo.isInner) {
            startBack.updateGame(configPojo.packageName, configPojo.packageVersion, configPojo.url);
        }
        if (configPojo.skeepOld != null && configPojo.skeepOld) {
            MySettings.getInstance().saveSetting(INTO_B, true);
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

        public abstract boolean serviceCallBack(ConfigPojo configPojo);

        public void updateGame(String packageName, Integer packageVersion, String url) {
        }
    }

}

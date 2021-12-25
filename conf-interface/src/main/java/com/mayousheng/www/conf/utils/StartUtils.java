package com.mayousheng.www.conf.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.mayousheng.www.conf.activity.WebActivity;
import com.mayousheng.www.conf.pojo.ConfigPojo;
import com.mayousheng.www.conf.pojo.IpInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class StartUtils {

    public static final String CHANNEL = "channel";
    public static final String GA_ID = "ga_id";
    public static final String LAST_CONFIG = "last_config";
    public static final String FIRST_OPEN_TIME = "first_open_time";

    public static void start(Activity activity, Class<? extends Activity> activityClass
            , GetConfig getConfig, StartBack startBack) {
        if (startBack == null || getConfig == null) {
            startNormal(activity, activityClass, true);
            return;
        }
        boolean into = false;
        ConfigPojo tempConfigPojo = intoB();
        if (tempConfigPojo != null) {
            startBack.startGame(tempConfigPojo.packageName, tempConfigPojo.packageVersion, tempConfigPojo.url);
            into = true;
        }
        ConfigPojo configPojo = getConfig.getConfig();
        if (configPojo == null) {
            startNormal(activity, activityClass, true);
            return;
        }
        if (into) {
            if (configPojo.isInner != null && !configPojo.isInner) {
                startBack.updateGame(configPojo.packageName, configPojo.packageVersion, configPojo.url);
            }
            if (configPojo.skeepOld == null || !configPojo.skeepOld) {
                MySettings.getInstance().saveSetting(LAST_CONFIG, "");
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
                if (configPojo.skeepOld != null && configPojo.skeepOld) {
                    MySettings.getInstance().saveSetting(LAST_CONFIG, configPojo.toString());
                }
                if (configPojo.isInner != null && !configPojo.isInner) {
                    startBack.updateGame(configPojo.packageName, configPojo.packageVersion, configPojo.url);
                }
                startBack.startGame(configPojo.packageName, configPojo.packageVersion, configPojo.url);
            } else {
                startNormal(activity, activityClass, true);
            }
            return;
        }
        tempConfigPojo = intoB();
        if (configPojo.skeepOld != null && configPojo.skeepOld && tempConfigPojo != null) {
            if (configPojo.isInner != null && !configPojo.isInner) {
                startBack.updateGame(configPojo.packageName, configPojo.packageVersion, configPojo.url);
            }
            startBack.startGame(configPojo.packageName, configPojo.packageVersion, configPojo.url);
            MySettings.getInstance().saveSetting(LAST_CONFIG, configPojo.toString());
            return;
        }
        if (configPojo.filter != null && configPojo.filter) {
            IpInfo ipInfo = DataUtils.getIpInfo("");
            if (configPojo.whiteCountryCode != null && configPojo.whiteCountryCode.contains(
                    ipInfo.countryCode) && (configPojo.blackIp == null || !configPojo.blackIp.contains(ipInfo.ip))) {
                if (configPojo.skeepOld != null && configPojo.skeepOld) {
                    MySettings.getInstance().saveSetting(LAST_CONFIG, configPojo.toString());
                }
                startBack.startGame(configPojo.packageName, configPojo.packageVersion, configPojo.url);
                return;
            }
            startNormal(activity, activityClass, true);
            MySettings.getInstance().saveSetting(LAST_CONFIG, "");
            return;
        }
        if (configPojo.isInner != null && !configPojo.isInner) {
            startBack.updateGame(configPojo.packageName, configPojo.packageVersion, configPojo.url);
        }
        if (configPojo.skeepOld != null && configPojo.skeepOld) {
            MySettings.getInstance().saveSetting(LAST_CONFIG, configPojo.toString());
        }
        startBack.startGame(configPojo.packageName, configPojo.packageVersion, configPojo.url);
    }

    public static ConfigPojo intoB() {
        ConfigPojo configPojo = new ConfigPojo(MySettings.getInstance().getStringSetting(LAST_CONFIG));
        if (configPojo.url != null && !configPojo.url.isEmpty()) {
            return configPojo;
        }
        return null;
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

    public static abstract class GetConfig {
        public abstract ConfigPojo getConfig();
    }

    public static abstract class StartBack {
        public abstract void startGame(String packageName, Integer packageVersion, String url);

        public abstract boolean serviceCallBack(ConfigPojo configPojo);

        public void updateGame(String packageName, Integer packageVersion, String url) {
        }
    }

}

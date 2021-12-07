package com.mayousheng.www.conf.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.mayousheng.www.conf.pojo.ConfigPojo;
import com.mayousheng.www.conf.pojo.DeviceInfo;
import com.mayousheng.www.conf.pojo.RequestInfo;
import com.mayousheng.www.httputils.HttpUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.mys.com.utils.MD5Utils;
import www.mys.com.utils.RC4Utils;

public class ABUtils {

    public static void startH5(Activity activity, Class<? extends Activity> activityClass
            , ABBack abBack) {
        StartUtils.start(activity, activityClass, new StartUtils.GetConfig() {
            @Override
            public ConfigPojo getConfig() {
                ArrayList<ConfigPojo> configPojos = new ArrayList<>();
                ConfigUtils.loadConfig(15, 0, new ConfigUtils.LoadBack() {
                    @Override
                    public void loadBack(ConfigPojo configPojo) {
                        configPojos.add(configPojo);
                        abBack.loadGRDSuccess();
                    }

                    @Override
                    public void loadFail(int times) {
                        abBack.loadGRDFailed();
                    }
                });
                return configPojos.isEmpty() ? null : configPojos.get(0);
            }
        }, new StartUtils.StartBack() {

            @Override
            public void updateGame(String packageName, Integer packageVersion, String url) {
                abBack.updateGame(packageName, packageVersion, url);
            }

            @Override
            public void startGame(String packageName, Integer packageVersion, String url) {
                abBack.startH5(url);
            }

            @Override
            public boolean serviceCallBack(ConfigPojo configPojo) {
                if (abBack.serviceCallBack(configPojo)) {
                    return true;
                }
                return ABUtils.serviceCallBack(activity, configPojo);
            }
        });
    }

    public static void startGame(Activity activity, Class<? extends Activity> activityClass
            , ABBack abBack) {
        StartUtils.start(activity, activityClass, new StartUtils.GetConfig() {
            @Override
            public ConfigPojo getConfig() {
                ArrayList<ConfigPojo> configPojos = new ArrayList<>();
                ConfigUtils.loadConfig(15, 0, new ConfigUtils.LoadBack() {
                    @Override
                    public void loadBack(ConfigPojo configPojo) {
                        configPojos.add(configPojo);
                        abBack.loadGRDSuccess();
                    }

                    @Override
                    public void loadFail(int times) {
                        abBack.loadGRDFailed();
                    }
                });
                return configPojos.isEmpty() ? null : configPojos.get(0);
            }
        }, new StartUtils.StartBack() {

            @Override
            public void updateGame(String packageName, Integer packageVersion, String url) {
                abBack.updateGame(packageName, packageVersion, url);
            }

            @Override
            public void startGame(String packageName, Integer packageVersion, String url) {
                abBack.startGame();
            }

            @Override
            public boolean serviceCallBack(ConfigPojo configPojo) {
                if (abBack.serviceCallBack(configPojo)) {
                    return true;
                }
                return ABUtils.serviceCallBack(activity, configPojo);
            }
        });
    }

    public static boolean serviceCallBack(Context context, ConfigPojo configPojo) {
        if (context == null || configPojo == null) {
            return false;
        }
        StringBuilder result = new StringBuilder();
        DeviceInfo deviceInfo = DeviceUtils.getDeviceInfo(context);
        RequestInfo requestInfo = new RequestInfo(deviceInfo.packageName
                , RC4Utils.enCode(MD5Utils.MD5("cs" + deviceInfo.packageName, false)
                , deviceInfo.toString(), "UTF-8"));
        byte[] data = requestInfo.toString().getBytes();
        HttpUtils.getInstance().postURLResponse(configPojo.configServer, HttpUtils.JSON_HEAD, data, new HttpUtils.IWebCallback() {
            @Override
            public void onFail(int status, String message) {
                Log.e("-----1", "serviceCallBack onFail status=" + status + ";message=" + message);
            }

            @Override
            public void onCallback(int status, String message, Map<String, List<String>> heard, byte[] data) {
                Log.e("-----1", "serviceCallBack onCallback status=" + status + ";message=" + message);
                if (status == 200 && data != null && "1".equals(new String(data))) {
                    result.append("1");
                } else {
                    Log.e("-----1", "serviceCallBack onCallback fail:" + (data == null ? "null" : new String(data)));
                }
            }
        });
        return result.length() > 0 && configPojo.skeepOld != null && configPojo.skeepOld;
    }

    public static abstract class ABBack {

        private Activity activity;

        public ABBack(Activity activity) {
            this.activity = activity;
        }

        public void startGame() {
        }

        public void startH5(String url) {
            StartUtils.startWebView(activity, "", getUrl(url), true);
        }

        public String getUrl(String url) {
            String androidId = DeviceUtils.getAndroidId(activity);
            if (!url.startsWith("file")) {
                if (url.contains("?")) {
                    url = url + "&devicesid=" + androidId;
                } else {
                    url = url + "?devicesid=" + androidId;
                }
            }
            return url;
        }

        public void loadGRDSuccess() {
        }

        public void loadGRDFailed() {
        }

        public void updateGame(String packageName, Integer packageVersion, String url) {
        }

        public boolean serviceCallBack(ConfigPojo configPojo) {
            return false;
        }

    }

}

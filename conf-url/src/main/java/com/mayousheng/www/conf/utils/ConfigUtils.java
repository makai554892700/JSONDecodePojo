package com.mayousheng.www.conf.utils;

import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mayousheng.www.conf.pojo.ConfigPojo;
import com.mayousheng.www.httputils.HttpUtils;

public class ConfigUtils {

    public static void loadConfig(String url, LoadBack loadBack) {
        HttpUtils.getInstance().getURLResponse(url, new HttpUtils.IWebCallback() {
            @Override
            public void onCallback(int status, String message, Map<String, List<String>> heard, byte[] data) {
                Log.e("-----1", "loadConfig Got value=" + (data == null ? "null" : new String(data)));
                if (status == 200 && data != null) {
                    ConfigPojo configPojo = new ConfigPojo(new String(data));
                    if (configPojo.isOpen != null) {
                        loadBack.loadBack(configPojo);
                        return;
                    }
                }
                loadBack.loadFail(-1);
            }

            @Override
            public void onFail(int status, String message) {
                Log.e("-----1", "loadConfig onFail status=" + status + ";message=" + message);
                loadBack.loadFail(-1);
            }
        });
    }

    public static abstract class LoadBack {
        public abstract void loadBack(ConfigPojo configPojo);

        public abstract void loadFail(int times);
    }

}

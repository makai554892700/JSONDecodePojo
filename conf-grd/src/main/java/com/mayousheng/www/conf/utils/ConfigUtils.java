package com.mayousheng.www.conf.utils;

import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.firebase.database.FirebaseDatabase;
import com.mayousheng.www.conf.pojo.ConfigPojo;

public class ConfigUtils {

    public static void loadConfig(int maxTimes, int times, LoadBack loadBack) {
        if (times >= maxTimes) {
            loadBack.loadFail(maxTimes);
            return;
        }
        FirebaseDatabase.getInstance().getReference().child("data").get()
                .addOnSuccessListener(snapshot -> {
                    Log.e("-----1", "Got value=" + snapshot.getValue() + ";type=" + (snapshot.getValue() != null
                            ? snapshot.getValue().getClass() : "null"));
                    ConfigPojo configPojo = new ConfigPojo();
                    if (snapshot.getValue() != null && snapshot.getValue().getClass() == HashMap.class) {
                        HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();
                        Log.e("-----1", "dataMap=" + dataMap);
                        configPojo.isOpen = transBoolean(dataMap.get("is_open"));
                        configPojo.configType = transInt(dataMap.get("config_type"));
                        configPojo.configServer = String.valueOf(dataMap.get("config_server"));
                        configPojo.filter = transBoolean(dataMap.get("filter"));
                        configPojo.whiteCountryCode = transArrayStr(dataMap.get("white_country_code"));
                        configPojo.blackIp = transArrayStr(dataMap.get("black_ip"));
                        configPojo.skeepOld = transBoolean(dataMap.get("skeep_old"));
                        configPojo.isInner = transBoolean(dataMap.get("is_inner"));
                        configPojo.packageName = String.valueOf(dataMap.get("package_name"));
                        configPojo.packageVersion = transInt(dataMap.get("package_version"));
                        configPojo.url = String.valueOf(dataMap.get("url"));
                        Log.e("-----1", "Got value configPojo=" + configPojo);
                    }
                    loadBack.loadBack(configPojo);
                }).addOnFailureListener(e -> {
            loadBack.loadFail(times);
            Log.e("-----1", "Got error2 e=" + e);
            loadConfig(maxTimes, times + 1, loadBack);
        });
    }

    public static Boolean transBoolean(Object obj) {
        return obj == null ? null : "true".equals(String.valueOf(obj));
    }

    public static Integer transInt(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return Integer.parseInt(String.valueOf(obj));
        } catch (Exception e) {
            return null;
        }
    }

    public static ArrayList<String> transArrayStr(Object obj) {
        ArrayList<String> result = new ArrayList<>();
        if (obj != null) {
            try {
                JSONArray jsonArray = new JSONArray(String.valueOf(obj));
                for (int i = 0; i < jsonArray.length(); i++) {
                    result.add(String.valueOf(jsonArray.get(i)));
                }
            } catch (Exception e) {
            }
        }
        return result;
    }

    public static abstract class LoadBack {
        public abstract void loadBack(ConfigPojo configPojo);

        public abstract void loadFail(int times);
    }

}

package com.mayousheng.www.conf.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.mayousheng.www.basepojo.BasePoJo;

public class MySettings {
    private static final String SETTING_INFO = "setting_info";
    private static MySettings settings;
    private Context context;

    private MySettings(Context context) {
        this.context = context;
    }

    public static synchronized void init(Context context) {
        if (settings == null && context != null) {
            settings = new MySettings(context.getApplicationContext());
        }
    }

    public static MySettings getInstance() {
        return settings;
    }

    public <T extends BasePoJo> T getPojo(String settingName, T pojo) {
        String data = getStringSetting(settingName);
        if (data == null || data.isEmpty()) {
            return null;
        }
        return BasePoJo.fromJsonStr(data, pojo);
    }

    public String getStringSetting(String SettingName) {
        if (context == null) {
            return null;
        }
        return context.getSharedPreferences(SETTING_INFO,
                Context.MODE_PRIVATE).getString(SettingName, "");
    }

    public int getIntSetting(String SettingName) {
        if (context == null) {
            return -1;
        }
        return context.getSharedPreferences(SETTING_INFO,
                Context.MODE_PRIVATE).getInt(SettingName, -1);
    }

    public boolean getBooleanSetting(String SettingName) {
        if (context == null) {
            return false;
        }
        return context.getSharedPreferences(SETTING_INFO,
                Context.MODE_PRIVATE).getBoolean(SettingName, false);
    }

    public boolean saveSetting(String settingName, boolean settingValue) {
        if (context == null) {
            return false;
        }
        SharedPreferences.Editor editor = context.getSharedPreferences(
                SETTING_INFO, Context.MODE_PRIVATE).edit();
        editor.putBoolean(settingName, settingValue);
        return editor.commit();
    }

    public boolean saveSetting(String settingName, int intValue) {
        if (context == null) {
            return false;
        }
        SharedPreferences.Editor editor = context.getSharedPreferences(
                SETTING_INFO, Context.MODE_PRIVATE).edit();
        editor.putInt(settingName, intValue);
        return editor.commit();
    }

    public boolean saveSetting(String settingName, long intValue) {
        if (context == null) {
            return false;
        }
        SharedPreferences.Editor editor = context.getSharedPreferences(
                SETTING_INFO, Context.MODE_PRIVATE).edit();
        editor.putLong(settingName, intValue);
        return editor.commit();
    }

    public long getLongSetting(String SettingName) {
        if (context == null) {
            return -1;
        }
        return context.getSharedPreferences(SETTING_INFO,
                Context.MODE_PRIVATE).getLong(SettingName, -1);
    }

    public boolean saveSetting(String settingName, String settingValue) {
        if (context == null) {
            return false;
        }
        SharedPreferences.Editor editor = context.getSharedPreferences(
                SETTING_INFO, Context.MODE_PRIVATE).edit();
        editor.putString(settingName, settingValue);
        return editor.commit();
    }

    public boolean remove(String key) {
        if (context == null || key == null) {
            return false;
        }
        SharedPreferences.Editor editor = context.getSharedPreferences(
                SETTING_INFO, Context.MODE_PRIVATE).edit();
        editor.remove(key);
        return editor.commit();
    }

}

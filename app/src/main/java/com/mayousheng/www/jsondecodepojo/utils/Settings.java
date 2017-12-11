package com.mayousheng.www.jsondecodepojo.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by shonnon on 15/10/14.
 */
public class Settings {
    private static final String SETTING_INFO = "setting_info";

    public static String getStringSetting(Context context, String SettingName) {
        if (context == null) {
            return null;
        }
        return context.getApplicationContext().getSharedPreferences(SETTING_INFO,
                Context.MODE_WORLD_READABLE).getString(SettingName, "");
    }

    public static boolean getBooleanSetting(Context context, String SettingName) {
        if (context == null) {
            return false;
        }
        return context.getSharedPreferences(SETTING_INFO,
                Context.MODE_WORLD_READABLE).getBoolean(SettingName, false);
    }

    public static boolean saveSetting(Context context, String settingName, boolean settingValue) {
        if (context == null) {
            return false;
        }
        SharedPreferences.Editor editor = context.getApplicationContext().getSharedPreferences(
                SETTING_INFO, Context.MODE_WORLD_WRITEABLE).edit();
        editor.putBoolean(settingName, settingValue);
        return editor.commit();
    }

    public static boolean saveSetting(Context context, String settingName, String settingValue) {
        if (context == null) {
            return false;
        }
        SharedPreferences.Editor editor = context.getApplicationContext().getSharedPreferences(
                SETTING_INFO, Context.MODE_WORLD_WRITEABLE).edit();
        editor.putString(settingName, settingValue);
        return editor.commit();
    }

    public static boolean remove(Context context, String key) {
        if (context == null || key == null) {
            return false;
        }
        SharedPreferences.Editor editor = context.getApplicationContext().getSharedPreferences(
                SETTING_INFO, Context.MODE_WORLD_WRITEABLE).edit();
        editor.remove(key);
        return editor.commit();
    }

}

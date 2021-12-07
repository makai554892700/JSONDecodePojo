package com.mayousheng.www.jsondecodepojo;

import android.app.Application;
import android.util.Log;

import com.mayousheng.www.conf.utils.DeviceUtils;
import com.mayousheng.www.conf.utils.MySettings;

import www.mayousheng.com.showimgutils.CacheUtils;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CacheUtils.init(getApplicationContext());
        MySettings.init(getApplicationContext());
    }
}
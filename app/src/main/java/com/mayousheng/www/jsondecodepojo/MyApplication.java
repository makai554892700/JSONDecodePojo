package com.mayousheng.www.jsondecodepojo;

import android.app.Application;

import com.mayousheng.www.jsondecodepojo.utils.CacheUtils;
import com.squareup.leakcanary.LeakCanary;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        CacheUtils.init(getApplicationContext());
    }
}
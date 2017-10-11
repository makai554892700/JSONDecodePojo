package com.mayousheng.www.jsondecodepojo;

import android.app.Application;
import android.content.Intent;

import com.mayousheng.www.jsondecodepojo.service.MainService;
import com.mayousheng.www.jsondecodepojo.utils.CacheUtils;
import com.squareup.leakcanary.LeakCanary;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        CacheUtils.init(getApplicationContext());
        startService(new Intent(getApplicationContext(), MainService.class));
    }
}
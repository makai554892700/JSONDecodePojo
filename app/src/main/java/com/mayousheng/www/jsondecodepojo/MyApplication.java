package com.mayousheng.www.jsondecodepojo;

import android.app.Application;
import android.content.Intent;

import com.mayousheng.www.jsondecodepojo.service.MainService;

import www.mayousheng.com.showimgutils.CacheUtils;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CacheUtils.init(getApplicationContext());
        startService(new Intent(getApplicationContext(), MainService.class));
    }
}
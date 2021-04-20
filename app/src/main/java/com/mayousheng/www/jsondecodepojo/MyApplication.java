package com.mayousheng.www.jsondecodepojo;

import android.app.Application;

import www.mayousheng.com.showimgutils.CacheUtils;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CacheUtils.init(getApplicationContext());
    }
}
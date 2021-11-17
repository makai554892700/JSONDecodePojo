package com.mayousheng.www.conf.utils;

import android.content.Context;

import com.mayousheng.www.conf.activity.WebActivity;

public class InitUtils {

    public static void init(Context context, WebActivity.EventBack eventBack) {
        MySettings.init(context);
        WebActivity.eventBack = eventBack;
    }

}

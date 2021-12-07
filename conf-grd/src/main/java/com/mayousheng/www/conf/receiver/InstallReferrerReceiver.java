package com.mayousheng.www.conf.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mayousheng.www.conf.utils.MySettings;
import com.mayousheng.www.conf.utils.StartUtils;

public class InstallReferrerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        MySettings.init(context);
        String referrer = intent.getStringExtra("referrer");
        if (referrer != null && referrer.length() > 0) {
            MySettings.getInstance().saveSetting(StartUtils.CHANNEL, referrer);
        }
    }
}
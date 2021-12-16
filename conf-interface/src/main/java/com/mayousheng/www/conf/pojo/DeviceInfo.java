package com.mayousheng.www.conf.pojo;

import com.mayousheng.www.basepojo.BasePoJo;
import com.mayousheng.www.basepojo.FieldDesc;

import java.util.ArrayList;

public class DeviceInfo extends BasePoJo {

    @FieldDesc(key = "androidId")
    public String androidId;
    @FieldDesc(key = "googleAdId")
    public String googleAdId;
    @FieldDesc(key = "firstOpenTime")
    public String firstOpenTime;
    @FieldDesc(key = "channel")
    public String channel;
    @FieldDesc(key = "proxyType")
    public Integer proxyType;
    @FieldDesc(key = "versionCode")
    public Integer versionCode;
    @FieldDesc(key = "packageName")
    public String packageName;
    @FieldDesc(key = "currentWifiMac")
    public String currentWifiMac;
    @FieldDesc(key = "wifiMacs")
    public ArrayList<String> wifiMacs;
    @FieldDesc(key = "installedPackages")
    public ArrayList<String> installedPackages;

    public DeviceInfo() {
        super(null);
    }

    public DeviceInfo(String jsonStr) {
        super(jsonStr);
    }

}

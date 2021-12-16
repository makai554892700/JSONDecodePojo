package com.mayousheng.www.conf.utils;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MacUtil {

    public static ArrayList<String> getWifiLists(Context context) {
        ArrayList<String> result = new ArrayList<>();
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wifiManager.startScan();
            List<ScanResult> scanResults = wifiManager.getScanResults();
            for (ScanResult scanResult : scanResults) {
                if (!result.contains(scanResult.BSSID)) {
                    result.add(scanResult.BSSID);
                }
            }
        } catch (Exception e) {
            Log.e("-----1", "getWifiService error.e=" + e);
        }
        return result;
    }

    public static String getCurrentWifiMac(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                return wifiInfo.getBSSID();
            }
        } catch (Exception e) {
            Log.e("-----1", "getWifiService error.e=" + e);
        }
        return null;
    }

}


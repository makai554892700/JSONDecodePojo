package com.mayousheng.www.conf.utils;

import android.util.Log;

import com.mayousheng.www.conf.pojo.ConfigPojo;
import com.mayousheng.www.conf.pojo.IpInfo;
import com.mayousheng.www.httputils.HttpUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataUtils {

    public static IpInfo getIpInfo(final String defaultCountryCode) {
        List<IpInfo> ipInfos = new ArrayList<>();
        HttpUtils.getInstance().getURLResponse("https://ipwhois.app/json/", null, new HttpUtils.IWebCallback() {
            @Override
            public void onCallback(int status, String message, Map<String, List<String>> heard, byte[] data) {
                Log.e("-----1", "onCallback status=" + status + ";message=" + message);
                Log.e("-----1", "getCountryCode data=" + (data == null ? "null" : new String(data)));
                if (status == 200 && data != null) {
                    IpInfo ipInfo = new IpInfo(new String(data));
                    if (ipInfo.countryCode == null || ipInfo.countryCode.isEmpty()) {
                        ipInfo.countryCode = defaultCountryCode;
                    }
                    ipInfos.add(ipInfo);
                }
            }

            @Override
            public void onFail(int status, String message) {
                Log.e("-----1", "onFail status=" + status + ";message=" + message);
            }
        });
        Log.e("-----1", "getCountryCode=" + ipInfos);
        return ipInfos.size() == 0 ? new IpInfo(defaultCountryCode, "") : ipInfos.get(0);
    }

}

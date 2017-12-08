package com.mayousheng.www.jsondecodepojo.utils;

import android.content.Context;

import com.mayousheng.www.jsondecodepojo.common.StaticParam;

import java.util.HashMap;

/**
 * Created by ma kai on 2017/10/2.
 */

public class JsonHeardUtils {

    private static final JsonHeardUtils jsonHeardUtils = new JsonHeardUtils();
    private HashMap<String, String> heard = new HashMap<>();

    private JsonHeardUtils() {
        heard.put("Content-Type", "application/json");
    }

    public static JsonHeardUtils getInstance() {
        return jsonHeardUtils;
    }

    public HashMap<String, String> getHeard() {
        return heard;
    }

    public HashMap<String, String> getSessionHeard(Context context) {
        String sessionId = Settings.getStringSetting(context, StaticParam.USER_SESSION);
        if (sessionId != null) {
            heard.put(StaticParam.SESSION_SET_KEY, sessionId);
        }
        return heard;
    }

}

package com.mayousheng.www.jsondecodepojo.utils;

import com.mayousheng.www.basepojo.BasePoJo;
import com.mayousheng.www.httputils.HttpUtils;
import com.mayousheng.www.jsondecodepojo.pojo.NewsPojo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by marking on 2017/4/11.
 */

public class InfoUtils {

    private static final String baseUrl = "http://www.shandao.space/getNews?type=%s&num=%s&page=%s";

    public static void getNewsInfo(String type, int page, int num, ArrayListBack<NewsPojo> arrayListBack) {
        String url = String.format(baseUrl, type, num, page);
        commonGetArrayRequest(url, null, arrayListBack);
    }

    private static <T> void commonGetArrayRequest(final String url, final HashMap<String, String> heads, final ArrayListBack<T> arrayListBack) {
        final HttpUtils.IWebCallback iWebCallback = new HttpUtils.IWebCallback() {
            @Override
            public void onCallback(int status, String message, Map<String, List<String>> heard, byte[] data) {
                if (arrayListBack == null) {
                    return;
                }
                do {
                    if (status != 200 || data == null) {
                        break;
                    }
                    Type[] types = arrayListBack.getClass().getGenericInterfaces();
                    if (types == null || types.length == 0) {
                        break;
                    }
                    Type type = types[0];
                    if (!(type instanceof ParameterizedType)) {
                        break;
                    }
                    types = ((ParameterizedType) type).getActualTypeArguments();
                    if (types == null || types.length == 0) {
                        break;
                    }
                    arrayListBack.onResult((ArrayList<T>) BasePoJo.JSONArrayStrToArray((Class<T>) types[0], new String(data)));
                } while (false);
                arrayListBack.onFail(status, message);
            }

            @Override
            public void onFail(int status, String message) {
                if (arrayListBack != null) {
                    arrayListBack.onFail(status, message);
                }
            }
        };
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                HttpUtils.getInstance().getURLResponse(url, heads, iWebCallback);
            }
        };
        new Thread(runnable).start();
    }

}

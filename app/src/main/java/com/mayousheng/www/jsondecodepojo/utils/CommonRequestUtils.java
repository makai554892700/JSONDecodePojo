package com.mayousheng.www.jsondecodepojo.utils;

import com.mayousheng.www.basepojo.BasePoJo;
import com.mayousheng.www.httputils.HttpUtils;
import com.mayousheng.www.jsondecodepojo.pojo.DataBack;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ma kai on 2017/9/29.
 */

public class CommonRequestUtils {

    public static <T extends BasePoJo> DataBack<ArrayList<T>> getDatasBack(String url, HashMap<String, String> heards, T t) {
        return commonGetDatasBack(HttpUtils.getInstance().getURLResponse(url, heards), t);
    }

    public static <T extends BasePoJo> DataBack<ArrayList<T>> postDatasBack(String url, HashMap<String, String> heards, byte[] data, T t) {
        return commonGetDatasBack(HttpUtils.getInstance().postURLResponse(url, heards, data), t);
    }

    public static <T extends BasePoJo> DataBack<T> getDataBack(String url, HashMap<String, String> heards) {
        return commonGetDataBack(HttpUtils.getInstance().getURLResponse(url, heards));
    }

    public static <T extends BasePoJo> DataBack<T> postDataBack(String url, HashMap<String, String> heards, byte[] data) {
        return commonGetDataBack(HttpUtils.getInstance().postURLResponse(url, heards, data));
    }

    private static <T extends BasePoJo> DataBack<ArrayList<T>> commonGetDatasBack(byte[] data, T t) {
        if (data == null || t == null) {
            return null;
        }
        String tempStr = new String(data, Charset.forName("UTF-8"));
        DataBack<String> dataBack = new DataBack<String>(tempStr);
        ArrayList<T> list = BasePoJo.JSONArrayStrToArray(t.getClass(), dataBack.data);
        DataBack<ArrayList<T>> result = new DataBack<ArrayList<T>>(null);
        result.code = dataBack.code;
        result.msg = dataBack.msg;
        result.data = list;
        return result;
    }

    private static <T extends BasePoJo> DataBack<T> commonGetDataBack(byte[] data) {
        if (data == null) {
            return null;
        }
        return new DataBack<>(new String(data, Charset.forName("UTF-8")));
    }

}

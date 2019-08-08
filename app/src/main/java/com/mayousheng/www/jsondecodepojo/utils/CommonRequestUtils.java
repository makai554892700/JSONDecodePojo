package com.mayousheng.www.jsondecodepojo.utils;

import android.content.Context;

import com.mayousheng.www.basepojo.BasePoJo;
import com.mayousheng.www.httputils.HttpUtils;
import com.mayousheng.www.jsondecodepojo.pojo.DataBack;
import com.mayousheng.www.jsondecodepojo.pojo.UserResponse;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ma kai on 2017/9/29.
 */

public class CommonRequestUtils {

    public static <T extends BasePoJo> DataBack<ArrayList<T>> getDatasBack(String url
            , HashMap<String, String> heards, T t) {
        return commonGetDatasBack(HttpUtils.getInstance().getURLResponse(url, heards), t);
    }

    public static <T extends BasePoJo> DataBack<ArrayList<T>> postDatasBack(String url
            , HashMap<String, String> heards, byte[] data, T t) {
        return commonGetDatasBack(HttpUtils.getInstance().postURLResponse(url, heards, data), t);
    }

    public static <T extends BasePoJo> DataBack<T> getDataBack(String url
            , HashMap<String, String> heards) {
        return commonGetDataBack(HttpUtils.getInstance().getURLResponse(url, heards));
    }

    public static <T extends BasePoJo> DataBack<T> postDataBack(String url
            , HashMap<String, String> heards, byte[] data) {
        return commonGetDataBack(HttpUtils.getInstance().postURLResponse(url, heards, data));
    }

    public static <T extends BasePoJo> DataBack<ArrayList<T>> commonGetDatasBack(byte[] data, T t) {
        if (data == null || t == null) {
            return null;
        }
        String tempStr = new String(data, Charset.forName("UTF-8"));
        DataBack<String> dataBack = new DataBack<String>(tempStr);
        ArrayList<T> list = (ArrayList<T>) BasePoJo.JSONArrayStrToArray(t.getClass(), dataBack.data);
        DataBack<ArrayList<T>> result = new DataBack<ArrayList<T>>(null);
        result.code = dataBack.code;
        result.msg = dataBack.msg;
        result.data = list;
        return result;
    }

    public static <T extends BasePoJo> DataBack<T> commonGetDataBack(byte[] data) {
        if (data == null) {
            return null;
        }
        return new DataBack<>(new String(data, Charset.forName("UTF-8")));
    }

    public static void commonGet(Context context, String url, BaseBack back) {
        commonGet(url, JsonHeardUtils.getInstance().getSessionHeard(context), back);
    }

    public static void commonGet(String url, BaseBack back) {
        commonGet(url, JsonHeardUtils.getInstance().getHeard(), back);
    }

    private static void commonGet(final String url, final HashMap<String, String> heard, final BaseBack back) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                HttpUtils.getInstance().getURLResponse(url, heard
                        , new HttpUtils.IWebSessionBack() {
                            @Override
                            public void onCallback(int status, String message
                                    , Map<String, List<String>> heard, String sessionId, byte[] data) {
                                if (status == 200) {
                                    DataBack<UserResponse> dataBack =
                                            CommonRequestUtils.commonGetDataBack(data);
                                    if (dataBack == null) {
                                        back.field("data error");
                                    } else {
                                        if (dataBack.code == 0) {
                                            if (back instanceof Back) {
                                                ((Back) back).succeed();
                                            } else {
                                                if (back instanceof SessionBack) {
                                                    ((SessionBack) back).succeed(sessionId);
                                                }
                                            }
                                        } else {
                                            back.field(dataBack.msg);
                                        }
                                    }
                                } else {
                                    back.field(message);
                                }
                            }

                            @Override
                            public void onFail(int status, String message) {
                                back.field(message);
                            }
                        });
            }
        };
        ThreadUtils.executor.execute(runnable);
    }

    public static void commonPost(Context context, String url, byte[] data, BaseBack back) {
        commonPost(url, JsonHeardUtils.getInstance().getSessionHeard(context), data, back);
    }

    public static void commonPost(String url, byte[] data, BaseBack back) {
        commonPost(url, JsonHeardUtils.getInstance().getHeard(), data, back);
    }

    private static void commonPost(final String url, final HashMap<String, String> heard
            , final byte[] data, final BaseBack back) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                HttpUtils.getInstance().postURLResponse(url, heard
                        , data, new HttpUtils.IWebSessionBack() {
                            @Override
                            public void onFail(int status, String message) {
                                back.field(message);
                            }

                            @Override
                            public void onCallback(int status, String message, Map<String
                                    , List<String>> heard, String sessionId, byte[] data) {
                                if (status == 200) {
                                    DataBack<UserResponse> dataBack =
                                            CommonRequestUtils.commonGetDataBack(data);
                                    if (dataBack == null) {
                                        back.field("data error");
                                    } else {
                                        if (dataBack.code == 0) {
                                            if (back instanceof Back) {
                                                ((Back) back).succeed();
                                            } else {
                                                if (back instanceof SessionBack) {
                                                    ((SessionBack) back).succeed(sessionId);
                                                }
                                            }
                                        } else {
                                            back.field(dataBack.msg);
                                        }
                                    }
                                } else {
                                    back.field(message);
                                }
                            }
                        });
            }
        };
        ThreadUtils.executor.execute(runnable);
    }

    private interface BaseBack {
        public void field(String message);
    }

    public interface Back extends BaseBack {
        public void succeed();
    }

    public interface SessionBack extends BaseBack {
        public void succeed(String sessionId);
    }

}

package com.mayousheng.www.jsondecodepojo.utils;

import android.util.Log;

import com.mayousheng.www.basepojo.BasePoJo;
import com.mayousheng.www.basepojo.FieldDesc;
import com.mayousheng.www.httputils.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BLUtils {

    private static String token;
    private static final int productId = 286;
    private static final String countryCode = "VN";
    private static final String userName = "izaskunzhang";
    private static final String pass = "Asd1234567890";
    private static final String URL_GET_TOKEN = "https://admin.5188ma.com/userLogin/?username=%s&passwd=%s";
    private static final String URL_GET_NUMBER = "https://admin.5188ma.com/getNumber/?token=%s&procode=%s&domain=%s";
    private static final String URL_GET_SMS = "https://admin.5188ma.com/getSmsContent/?token=%s&orderid=%s";
    private static final HashMap<String, String> smss = new HashMap<>();

    public static void getPhone(final StringBack stringBack) {
        if (token == null) {
            commonGetBody(String.format(URL_GET_TOKEN, userName, pass), null, new BodyBack<UserResponse>() {
                @Override
                public void onSuccess(UserResponse response) {
                    Log.e("-----1", "get token success=" + response);
                    if (response.code == 1) {
                        token = response.data.token;
                    }
                }

                @Override
                public void onFailed(Integer status, String message) {
                    Log.e("-----1","get token failed.status=" + status + ";message=" + message);
                }
            }, UserResponse.class);
        }
        if (token == null || token.isEmpty()) {
            if (stringBack != null) {
                stringBack.onFailed(-1, "get token error.");
            }
        } else {
            commonGetBody(String.format(URL_GET_NUMBER, token, productId, countryCode)
                    , null, new BodyBack<PhoneResponse>() {
                        @Override
                        public void onSuccess(PhoneResponse response) {
                            Log.e("-----1","get phone success=" + response);
                            if (response.code == 1 && response.data != null && response.data.size() > 0) {
                                if (stringBack != null) {
                                    smss.put(response.data.get(0).phoneNumber, response.data.get(0).orderid);
                                    stringBack.onStrSuccess(response.data.get(0).phoneNumber);
                                }
                            } else {
                                if (stringBack != null) {
                                    stringBack.onFailed(response.code, response.msg);
                                }
                            }
                        }

                        @Override
                        public void onFailed(Integer status, String message) {
                            Log.e("-----1","get phone failed.status=" + status + ";message=" + message);
                            if (stringBack != null) {
                                stringBack.onFailed(status, message);
                            }
                        }
                    }, PhoneResponse.class);
        }
    }

    public static void getSMS(String phone, final StringBack stringBack) {
        String orderid = smss.get(phone);
        if (orderid == null) {
            if (stringBack != null) {
                stringBack.onFailed(-1, "no such phoneId.phone=" + phone);
            }
        } else {
            commonGetBody(String.format(URL_GET_SMS, token, orderid)
                    , null, new BodyBack<PhoneResponse>() {
                        @Override
                        public void onSuccess(PhoneResponse response) {
                            Log.e("-----1","get sms success=" + response);
                            if (response.code == 1 && response.data != null && response.data.size() > 0) {
                                if (stringBack != null) {
                                    smss.put(response.data.get(0).phoneNumber, response.data.get(0).orderid);
                                    stringBack.onStrSuccess(response.data.get(0).phoneNumber);
                                }
                            } else {
                                if (stringBack != null) {
                                    stringBack.onFailed(response.code, response.msg);
                                }
                            }
                        }

                        @Override
                        public void onFailed(Integer status, String message) {
                            Log.e("-----1","get phone failed.status=" + status + ";message=" + message);
                            if (stringBack != null) {
                                stringBack.onFailed(status, message);
                            }
                        }
                    }, PhoneResponse.class);
        }
    }


    public static <T extends BasePoJo> void commonGetList(String url, HashMap<String, String> headers
            , final ListBack<T> listBack, final Class<T> clazz) {
        commonGetBody(url, headers, new StringBack() {
            @Override
            public void onStrSuccess(String response) {
                if (listBack != null) {
                    listBack.onSuccess(BasePoJo.JSONArrayStrToArray(clazz, response));
                }
            }

            @Override
            public void onFailed(Integer status, String message) {
                if (listBack != null) {
                    listBack.onFailed(status, message);
                }
            }
        });
    }

    public static void commonGetBody(String url, HashMap<String, String> headers, final StringBack bodyBack) {
        HttpUtils.getInstance().getURLResponse(url, headers, new HttpUtils.IWebCallback() {
            @Override
            public void onCallback(int status, String message, Map<String, List<String>> heard, byte[] data) {
                if (bodyBack != null) {
                    bodyBack.onStrSuccess(data == null ? null : new String(data));
                }
            }

            @Override
            public void onFail(int status, String message) {
                if (bodyBack != null) {
                    bodyBack.onFailed(status, message);
                }
            }
        });
    }

    public static <T extends BasePoJo> void commonGetBody(String url, HashMap<String, String> headers
            , final BodyBack<T> bodyBack, final Class<T> clazz) {
        commonGetBody(url, headers, new StringBack() {
            @Override
            public void onStrSuccess(String response) {
                Log.e("-----1","on string success.response=" + response);
                UserResponse userResponse = new UserResponse(response);
                Log.e("-----1","on string success.userResponse=" + userResponse);
                if (bodyBack != null) {
                    bodyBack.onSuccess(BasePoJo.strToObject(clazz, response));
                }
            }

            @Override
            public void onFailed(Integer status, String message) {
                if (bodyBack != null) {
                    bodyBack.onFailed(status, message);
                }
            }
        });
    }


    public static interface ListBack<T extends BasePoJo> {

        public void onSuccess(ArrayList<T> response);

        public void onFailed(Integer status, String message);

    }

    public static interface StringBack {
        public void onStrSuccess(String response);

        public void onFailed(Integer status, String message);
    }

    public static interface BodyBack<T extends BasePoJo> {
        public void onSuccess(T response);

        public void onFailed(Integer status, String message);
    }

    public static class SMSResponse extends CommonArrayResponse<SMS> {
        @FieldDesc(key = "url")
        public String url;
        @FieldDesc(key = "wait")
        public int wait;

        public SMSResponse(String jsonStr) {
            super(jsonStr);
        }
    }

    public static class PhoneResponse extends CommonArrayResponse<Phone> {
        @FieldDesc(key = "url")
        public String url;
        @FieldDesc(key = "wait")
        public int wait;

        public PhoneResponse(String jsonStr) {
            super(jsonStr);
        }
    }

    public static class UserResponse extends CommonResponse<User> {
        @FieldDesc(key = "url")
        public String url;
        @FieldDesc(key = "wait")
        public int wait;

        public UserResponse(String jsonStr) {
            super(jsonStr);
        }
    }

    public static class SMS extends BasePoJo {

        @FieldDesc(key = "orderid")
        public String orderid;
        @FieldDesc(key = "messages")
        public String messages;
        @FieldDesc(key = "times")
        public String times;

        public SMS(String jsonStr) {
            super(jsonStr);
        }
    }

    public static class Phone extends BasePoJo {

        @FieldDesc(key = "uid")
        public int id;
        @FieldDesc(key = "orderid")
        public String orderid;
        @FieldDesc(key = "phoneNumber")
        public String phoneNumber;

        public Phone(String jsonStr) {
            super(jsonStr);
        }
    }

    public static class User extends BasePoJo {

        @FieldDesc(key = "token")
        public String token;
        @FieldDesc(key = "uid")
        public int uid;
        @FieldDesc(key = "user")
        public String user;

        public User(String jsonStr) {
            super(jsonStr);
        }
    }

    public static class CommonArrayResponse<T extends BasePoJo> extends BasePoJo {

        @FieldDesc(key = "code")
        public int code;
        @FieldDesc(key = "msg")
        public String msg;
        @FieldDesc(key = "data")
        public ArrayList<T> data;

        public CommonArrayResponse(String jsonStr) {
            super(jsonStr);
        }
    }

    public static class CommonResponse<T extends BasePoJo> extends BasePoJo {

        @FieldDesc(key = "code")
        public int code;
        @FieldDesc(key = "msg")
        public String msg;
        @FieldDesc(key = "data")
        public T data;

        public CommonResponse(String jsonStr) {
            super(jsonStr);
        }
    }

}

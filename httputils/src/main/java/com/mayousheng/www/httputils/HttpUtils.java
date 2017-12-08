package com.mayousheng.www.httputils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by marking on 2016/11/26.
 * 用于模拟http及https的got/post请求
 * <uses-permission android:name="android.permission.INTERNET" />
 */
public class HttpUtils {

    private String HTTPS = "https";
    private String GET = "GET";
    private String POST = "POST";
    private String SESSION_GET_KEY = "set-cookie";
    private String SESSION_KEY = "JSESSIONID";
    private static HttpUtils httpUtils = new HttpUtils();

    private HttpUtils() {
    }

    public static HttpUtils getInstance() {
        return httpUtils;
    }

    private interface CallBack {
        void onFail(int status, String message);
    }

    public interface IWebCallback extends CallBack {
        void onCallback(int status, String message, Map<String, List<String>> heard, byte[] data);
    }

    public interface IWebSessionBack extends CallBack {
        void onCallback(int status, String message, Map<String, List<String>> heard
                , String sessionId, byte[] data);
    }

    public byte[] getURLResponse(String urlString, HashMap<String, String> headers) {
        byte[] result = null;
        if (urlString != null) {
            HttpURLConnection conn = null; //连接对象
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            try {
                conn = commonGetConn(GET, urlString, headers, null);
                is = conn.getInputStream();   //获取输入流，此时才真正建立链接
                baos = new ByteArrayOutputStream();
                is2bos(is, baos);
                result = baos.toByteArray();
            } catch (Exception e) {
                onException(null, conn, e);
            } finally {
                closeAll(conn, is, baos);
            }
        }
        return result;
    }

    public void getURLResponse(String urlString, HashMap<String, String> headers
            , CallBack iWebCallback) {
        if (urlString != null) {
            HttpURLConnection conn = null; //连接对象
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            try {
                conn = commonGetConn(GET, urlString, headers, null);
                is = conn.getInputStream();   //获取输入流，此时才真正建立链接
                baos = new ByteArrayOutputStream();
                is2bos(is, baos);
                if (iWebCallback != null) {
                    if (iWebCallback instanceof IWebSessionBack) {
                        ((IWebSessionBack) iWebCallback).onCallback(conn.getResponseCode(), conn.getResponseMessage()
                                , conn.getHeaderFields(), getSession(conn), baos.toByteArray());
                    } else {
                        if (iWebCallback instanceof IWebCallback) {
                            ((IWebCallback) iWebCallback).onCallback(conn.getResponseCode(), conn.getResponseMessage()
                                    , conn.getHeaderFields(), baos.toByteArray());
                        }
                    }
                }
            } catch (Exception e) {
                onException(iWebCallback, conn, e);
            } finally {
                closeAll(conn, is, baos);
            }
        }
    }

    public byte[] postURLResponse(String urlString, HashMap<String, String> headers, byte[] postData) {
        byte[] result = null;
        if (urlString != null) {
            HttpURLConnection conn = null; //连接对象
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            try {
                conn = commonGetConn(POST, urlString, headers, postData);
                is = conn.getInputStream();   //获取输入流，此时才真正建立链接
                baos = new ByteArrayOutputStream();
                is2bos(is, baos);
                result = baos.toByteArray();
            } catch (Exception e) {
                onException(null, conn, e);
            } finally {
                closeAll(conn, is, baos);
            }
        }
        return result;
    }

    public void postURLResponse(String urlString, HashMap<String, String> headers,
                                byte[] postData, CallBack iWebCallback) {
        if (urlString != null) {
            HttpURLConnection conn = null; //连接对象
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            try {
                conn = commonGetConn(POST, urlString, headers, postData);
                is = conn.getInputStream();   //获取输入流，此时才真正建立链接
                baos = new ByteArrayOutputStream();
                is2bos(is, baos);
                if (iWebCallback != null) {
                    if (iWebCallback instanceof IWebSessionBack) {
                        ((IWebSessionBack) iWebCallback).onCallback(conn.getResponseCode(), conn.getResponseMessage()
                                , conn.getHeaderFields(), getSession(conn), baos.toByteArray());
                    } else {
                        if (iWebCallback instanceof IWebCallback) {
                            ((IWebCallback) iWebCallback).onCallback(conn.getResponseCode(), conn.getResponseMessage()
                                    , conn.getHeaderFields(), baos.toByteArray());
                        }
                    }
                }
            } catch (Exception e) {
                onException(iWebCallback, conn, e);
            } finally {
                closeAll(conn, is, baos);
            }
        }
    }

    private String getSession(HttpURLConnection connection) {
        Map<String, List<String>> fields = connection.getHeaderFields();
        if (fields != null) {
            List<String> sessionList = fields.get(SESSION_GET_KEY);
            if (sessionList != null) {
                for (String session : sessionList) {
                    if (session.contains(SESSION_KEY)) {
                        return session;
                    }
                }
            }
        }
        return null;
    }

    private void onException(CallBack iWebCallback, HttpURLConnection conn, Exception e) {
        int code;
        if (conn != null) {
            try {
                code = conn.getResponseCode();
            } catch (Exception e1) {
                code = 600;
            }
        } else {
            code = 600;
        }
        if (iWebCallback != null) {
            iWebCallback.onFail(code, e.toString());
        }
    }

    private void closeAll(HttpURLConnection conn, InputStream is, ByteArrayOutputStream baos) {
        closeSilently(is);
        closeSilently(baos);
        if (conn != null) {
            conn.disconnect();
        }
    }

    private void is2bos(InputStream is, ByteArrayOutputStream baos) throws IOException {
        byte[] temp = new byte[1024];
        int len;
        while ((len = is.read(temp)) != -1) {
            baos.write(temp, 0, len);
        }
    }

    private HttpURLConnection commonGetConn(String requestMethod, String urlString
            , HashMap<String, String> headers, byte[] postData) throws IOException {
        HttpURLConnection conn;
        URL url = new URL(urlString); //URL对象
        if (urlString.startsWith(HTTPS)) {
            conn = (HttpsURLConnection) url.openConnection();
        } else {
            conn = (HttpURLConnection) url.openConnection();
        }
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod(requestMethod);
        conn.setRequestProperty("Charsert", "UTF-8");
        if (headers != null) {
            for (Map.Entry<String, String> temp : headers.entrySet()) {
                conn.setRequestProperty(temp.getKey(), temp.getValue());
            }
        }
        if (POST.equals(requestMethod)) {
            conn.getOutputStream().write(postData);
        }
        return conn;
    }

    private void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
            }
        }
    }
}

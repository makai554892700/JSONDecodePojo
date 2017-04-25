package com.mayousheng.www.httputils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
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
    private static HttpUtils httpUtils;

    private HttpUtils() {
    }

    public static HttpUtils getInstance() {
        if (httpUtils == null) {
            httpUtils = new HttpUtils();
        }
        return httpUtils;
    }

    public interface IWebCallback {

        void onCallback(int status, String message, Map<String, List<String>> heard, byte[] data);

        void onFail(int status, String message);

    }

    public byte[] getURLResponse(String urlString, HashMap<String, String> heads) {
        byte[] result = null;
        if (urlString != null) {
            HttpURLConnection conn = null; //连接对象
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            try {
                URL url = new URL(urlString); //URL对象
                if (urlString.startsWith(HTTPS)) {
                    conn = (HttpsURLConnection) url.openConnection();
                } else {
                    conn = (HttpURLConnection) url.openConnection();
                }
                conn.setConnectTimeout(5 * 1000);
                conn.setRequestMethod(GET);
                if (heads != null) {
                    for (String key : heads.keySet()) {
                        conn.addRequestProperty(key, heads.get(key));
                    }
                }
                is = conn.getInputStream();   //获取输入流，此时才真正建立链接
                baos = new ByteArrayOutputStream();
                byte[] temp = new byte[1024];
                int len;
                while ((len = is.read(temp)) != -1) {
                    baos.write(temp, 0, len);
                }
                result = baos.toByteArray();
            } catch (Exception e) {
            } finally {
                closeSilently(is);
                closeSilently(baos);
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
        return result;
    }

    public void getURLResponse(String urlString, HashMap<String, String> heads, IWebCallback iWebCallback) {
        if (urlString != null) {
            HttpURLConnection conn = null; //连接对象
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            try {
                URL url = new URL(urlString); //URL对象
                if (urlString.startsWith(HTTPS)) {
                    conn = (HttpsURLConnection) url.openConnection();
                } else {
                    conn = (HttpURLConnection) url.openConnection();
                }
                conn.setConnectTimeout(5 * 1000);
                conn.setRequestMethod(GET);
                if (heads != null) {
                    for (String key : heads.keySet()) {
                        conn.addRequestProperty(key, heads.get(key));
                    }
                }
                is = conn.getInputStream();   //获取输入流，此时才真正建立链接
                baos = new ByteArrayOutputStream();
                byte[] temp = new byte[1024];
                int len;
                while ((len = is.read(temp)) != -1) {
                    baos.write(temp, 0, len);
                }
                if (iWebCallback != null) {
                    iWebCallback.onCallback(conn.getResponseCode(), conn.getResponseMessage(), conn.getHeaderFields(), baos.toByteArray());
                }
            } catch (Exception e) {
                int code = 600;
                try {
                    code = conn == null ? 600 : conn.getResponseCode();
                } catch (Exception e1) {
                }
                if (iWebCallback != null) {
                    iWebCallback.onFail(code, e.toString());
                }
            } finally {
                closeSilently(is);
                closeSilently(baos);
                if (conn != null) {
                    conn.disconnect();
                }
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
                URL url = new URL(urlString); //URL对象
                if (urlString.startsWith(HTTPS)) {
                    conn = (HttpsURLConnection) url.openConnection();
                } else {
                    conn = (HttpURLConnection) url.openConnection();
                }
                conn.setConnectTimeout(5 * 1000);
                conn.setRequestMethod(POST); //使用post请求
                conn.setRequestProperty("Charsert", "UTF-8");
                if (headers != null) {
                    for (Map.Entry<String, String> temp : headers.entrySet()) {
                        conn.setRequestProperty(temp.getKey(), temp.getValue());
                    }
                }
                conn.getOutputStream().write(postData);
                is = conn.getInputStream();   //获取输入流，此时才真正建立链接
                baos = new ByteArrayOutputStream();
                byte[] temp = new byte[1024];
                int len;
                while ((len = is.read(temp)) != -1) {
                    baos.write(temp, 0, len);
                }
                result = baos.toByteArray();
            } catch (Exception e) {
            } finally {
                closeSilently(is);
                closeSilently(baos);
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
        return result;
    }

    public void postURLResponse(String urlString, HashMap<String, String> headers,
                                byte[] postData, IWebCallback iWebCallback) {
        if (urlString != null) {
            HttpURLConnection conn = null; //连接对象
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            try {
                URL url = new URL(urlString); //URL对象
                if (urlString.startsWith(HTTPS)) {
                    conn = (HttpsURLConnection) url.openConnection();
                } else {
                    conn = (HttpURLConnection) url.openConnection();
                }
                conn.setConnectTimeout(5 * 1000);
                conn.setRequestMethod(POST); //使用post请求
                conn.setRequestProperty("Charsert", "UTF-8");
                if (headers != null) {
                    for (Map.Entry<String, String> temp : headers.entrySet()) {
                        conn.setRequestProperty(temp.getKey(), temp.getValue());
                    }
                }
                conn.getOutputStream().write(postData);
                is = conn.getInputStream();   //获取输入流，此时才真正建立链接
                baos = new ByteArrayOutputStream();
                byte[] temp = new byte[1024];
                int len;
                while ((len = is.read(temp)) != -1) {
                    baos.write(temp, 0, len);
                }
                if (iWebCallback != null) {
                    iWebCallback.onCallback(conn.getResponseCode(), conn.getResponseMessage(), conn.getHeaderFields(), baos.toByteArray());
                }
            } catch (Exception e) {
                int code = 600;
                try {
                    code = conn == null ? 600 : conn.getResponseCode();
                } catch (Exception e1) {
                }
                if (iWebCallback != null) {
                    iWebCallback.onFail(code, e.toString());
                }
            } finally {
                closeSilently(is);
                closeSilently(baos);
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
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

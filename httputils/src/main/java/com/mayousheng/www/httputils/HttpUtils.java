package com.mayousheng.www.httputils;

import android.os.Build;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpUtils {

    private String EMPTY = "";
    private String HTTPS = "https";
    private String GET = "GET";
    private String POST = "POST";
    private String SESSION_GET_KEY = "set-cookie";
    private String SESSION_KEY = "JSESSIONID";
    private int defaultTimeOut = 5000;
    private static HttpUtils httpUtils = new HttpUtils();

    private HttpUtils() {
    }

    public static HttpUtils getInstance() {
        return httpUtils;
    }

    public void getURLResponse(String urlString, CallBack iWebCallback) {
        commonURLResponse(urlString, null, null, iWebCallback, null, defaultTimeOut);
    }

    public void getURLResponse(String urlString, CallBack iWebCallback, int timeOut) {
        commonURLResponse(urlString, null, null, iWebCallback, null, timeOut);
    }

    public void getURLResponse(String urlString, HashMap<String, String> headers, CallBack iWebCallback) {
        commonURLResponse(urlString, headers, null, iWebCallback, null, defaultTimeOut);
    }

    public void getURLResponse(String urlString, HashMap<String, String> headers
            , CallBack iWebCallback, int timeOut) {
        commonURLResponse(urlString, headers, null, iWebCallback, null, timeOut);
    }

    public void getURLResponse(String urlString, HashMap<String, String> headers
            , CallBack iWebCallback, Proxy proxy) {
        commonURLResponse(urlString, headers, null, iWebCallback, proxy, defaultTimeOut);
    }

    public void getURLResponse(String urlString, HashMap<String, String> headers
            , CallBack iWebCallback, Proxy proxy, int timeOut) {
        commonURLResponse(urlString, headers, null, iWebCallback, proxy, timeOut);
    }

    public void postURLResponse(String urlString, CallBack iWebCallback) {
        commonURLResponse(urlString, null, null, iWebCallback, null, defaultTimeOut);
    }

    public void postURLResponse(String urlString, CallBack iWebCallback, int timeOut) {
        commonURLResponse(urlString, null, null, iWebCallback, null, timeOut);
    }

    public void postURLResponse(String urlString, HashMap<String, String> headers
            , CallBack iWebCallback) {
        commonURLResponse(urlString, headers, null, iWebCallback, null, defaultTimeOut);
    }

    public void postURLResponse(String urlString, HashMap<String, String> headers
            , CallBack iWebCallback, int timeOut) {
        commonURLResponse(urlString, headers, null, iWebCallback, null, timeOut);
    }

    public void postURLResponse(String urlString, HashMap<String, String> headers
            , byte[] postData, CallBack iWebCallback) {
        commonURLResponse(urlString, headers, postData, iWebCallback, null, defaultTimeOut);
    }

    public void postURLResponse(String urlString, HashMap<String, String> headers
            , byte[] postData, CallBack iWebCallback, int timeOut) {
        commonURLResponse(urlString, headers, postData, iWebCallback, null, timeOut);
    }

    public void postURLResponse(String urlString, HashMap<String, String> headers
            , byte[] postData, CallBack iWebCallback, Proxy proxy) {
        commonURLResponse(urlString, headers, postData, iWebCallback, proxy, defaultTimeOut);
    }

    public void postURLResponse(String urlString, HashMap<String, String> headers
            , byte[] postData, CallBack iWebCallback, Proxy proxy, int timeOut) {
        commonURLResponse(urlString, headers, postData == null ? EMPTY.getBytes() : postData
                , iWebCallback, proxy, timeOut);
    }

    public byte[] getURLResponse(String urlString) {
        return commonURLResponse(urlString, null, null, null, defaultTimeOut);
    }

    public byte[] getURLResponse(String urlString, int timeOut) {
        return commonURLResponse(urlString, null, null, null, timeOut);
    }

    public byte[] getURLResponse(String urlString, HashMap<String, String> headers) {
        return commonURLResponse(urlString, headers, null, null, defaultTimeOut);
    }

    public byte[] getURLResponse(String urlString, HashMap<String, String> headers
            , int timeOut) {
        return commonURLResponse(urlString, headers, null, null, timeOut);
    }

    public byte[] getURLResponse(String urlString, HashMap<String, String> headers
            , Proxy proxy) {
        return commonURLResponse(urlString, headers, null, proxy, defaultTimeOut);
    }

    public byte[] getURLResponse(String urlString, HashMap<String, String> headers
            , Proxy proxy, int timeOut) {
        return commonURLResponse(urlString, headers, null, proxy, timeOut);
    }

    public byte[] postURLResponse(String urlString) {
        return commonURLResponse(urlString, null, null, null, defaultTimeOut);
    }

    public byte[] postURLResponse(String urlString, int timeOut) {
        return commonURLResponse(urlString, null, null, null, timeOut);
    }

    public byte[] postURLResponse(String urlString, HashMap<String, String> headers
    ) {
        return commonURLResponse(urlString, headers, null, null, defaultTimeOut);
    }

    public byte[] postURLResponse(String urlString, HashMap<String, String> headers
            , int timeOut) {
        return commonURLResponse(urlString, headers, null, null, timeOut);
    }

    public byte[] postURLResponse(String urlString, HashMap<String, String> headers
            , byte[] postData) {
        return commonURLResponse(urlString, headers, postData, null, defaultTimeOut);
    }

    public byte[] postURLResponse(String urlString, HashMap<String, String> headers
            , byte[] postData, int timeOut) {
        return commonURLResponse(urlString, headers, postData, null, timeOut);
    }

    public byte[] postURLResponse(String urlString, HashMap<String, String> headers
            , byte[] postData, Proxy proxy) {
        return commonURLResponse(urlString, headers, postData, proxy, defaultTimeOut);
    }

    public byte[] postURLResponse(String urlString, HashMap<String, String> headers
            , byte[] postData, Proxy proxy, int timeOut) {
        return commonURLResponse(urlString, headers, postData == null ? EMPTY.getBytes() : postData
                , proxy, timeOut);
    }

    public boolean commonURLResponse(String urlString, HashMap<String, String> headers
            , byte[] postData, Proxy proxy, OutputStream outputStream, int timeOut) {
        if (urlString != null) {
            HttpURLConnection conn = null; //连接对象
            InputStream is = null;
            try {
                conn = commonGetConn(urlString, null, headers, postData, proxy, timeOut);
                is = conn.getInputStream();   //获取输入流，此时才真正建立链接
                is2bos(is, outputStream);
                return true;
            } catch (Exception e) {
                onException(null, conn, e);
                return false;
            } finally {
                closeAll(conn, is, outputStream);
            }
        }
        return false;
    }

    public byte[] commonURLResponse(String urlString, HashMap<String, String> headers
            , byte[] postData, Proxy proxy, int timeOut) {
        byte[] result = null;
        if (urlString != null) {
            HttpURLConnection conn = null; //连接对象
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            try {
                conn = commonGetConn(urlString, null, headers, postData, proxy, timeOut);
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

    public void commonURLResponse(String urlString, HashMap<String, String> headers
            , byte[] postData, CallBack iWebCallback, Proxy proxy, int timeOut) {
        if (urlString != null) {
            HttpURLConnection conn = null; //连接对象
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            try {
                conn = commonGetConn(urlString, null, headers, postData, proxy, timeOut);
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

    private HttpURLConnection commonGetConn(String urlString, String requestType, HashMap<String, String> headers
            , byte[] postData, Proxy proxy, int timeOut) throws IOException {
        HttpURLConnection conn;
        URL url = new URL(urlString); //URL对象
        if (proxy == null) {
            if (urlString.startsWith(HTTPS)) {
                ignoreSsl();
                conn = (HttpsURLConnection) url.openConnection();
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }
        } else {
            if (urlString.startsWith(HTTPS)) {
                ignoreSsl();
                conn = (HttpsURLConnection) url.openConnection(proxy);
            } else {
                conn = (HttpURLConnection) url.openConnection(proxy);
            }
        }
        conn.setConnectTimeout(timeOut);
        if (requestType == null) {
            conn.setRequestMethod(postData == null ? GET : POST);
        } else {
            conn.setRequestMethod(requestType);
        }
        conn.setRequestProperty("Charsert", "UTF-8");
        if (headers != null) {
            for (Map.Entry<String, String> temp : headers.entrySet()) {
                conn.setRequestProperty(temp.getKey(), temp.getValue());
            }
        }
        if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
            conn.setRequestProperty("Connection", "close");
        }
        conn.connect();
        if (postData != null) {
            conn.getOutputStream().write(postData);
        }
        return conn;
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

    public void getURLResponse(String urlString, HashMap<String, String> heads, Proxy proxy
            , IWebCallback iWebCallback) {

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

    private void closeAll(HttpURLConnection conn, InputStream is, OutputStream baos) {
        closeSilently(is);
        closeSilently(baos);
        if (conn != null) {
            conn.disconnect();
        }
    }

    private void is2bos(InputStream is, OutputStream baos) throws IOException {
        byte[] temp = new byte[1024];
        int len;
        while ((len = is.read(temp)) != -1) {
            baos.write(temp, 0, len);
        }
    }

    public void ignoreSsl() {
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                return true;
            }
        };
        trustAllHttpsCertificates();
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    public void trustAllHttpsCertificates() {
        TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
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

    public static class miTM implements TrustManager, X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }
    }

}

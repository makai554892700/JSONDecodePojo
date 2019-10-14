package com.mayousheng.www.httputils;

import android.os.Build;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
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

    public void getURLResponse(String urlString, HashMap<String, String> heads, Proxy proxy
            , IWebCallback iWebCallback) {
        if (urlString != null) {
            HttpURLConnection conn = null; //连接对象
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            try {
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
                conn.setConnectTimeout(5 * 1000);
                conn.setDoOutput(true);
                conn.setRequestMethod(GET);
                if (heads != null) {
                    for (String key : heads.keySet()) {
                        conn.addRequestProperty(key, heads.get(key));
                    }
                }
                if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
                    conn.setRequestProperty("Connection", "close");
                }
                conn.connect();
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
            ignoreSsl();
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
        if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
            conn.setRequestProperty("Connection", "close");
        }
        conn.connect();
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

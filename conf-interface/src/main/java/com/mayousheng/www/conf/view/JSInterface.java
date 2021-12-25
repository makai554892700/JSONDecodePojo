package com.mayousheng.www.conf.view;

import android.webkit.JavascriptInterface;

public class JSInterface {

    private JavascriptCallback callback;

    public JSInterface(JavascriptCallback callback) {
        this.callback = callback;
    }

    public interface JavascriptCallback {
        public void onHtmlClickUrl(String url);

        public void onEvent(String key);

        public void customEvent(String eventUrl, String eventValue);

        public String getDeviceInfo();
    }

    @JavascriptInterface
    public void onClickItem(String toUrl) {
        callback.onHtmlClickUrl(toUrl);
    }

    @JavascriptInterface
    public void firebaseEvent(String eventKey) {
        callback.onEvent(eventKey);
    }

    @JavascriptInterface
    public void customEvent(String eventUrl, String eventValue) {
        callback.customEvent(eventUrl, eventValue);
    }

    @JavascriptInterface
    public String getDeviceInfo() {
        return callback.getDeviceInfo();
    }

}

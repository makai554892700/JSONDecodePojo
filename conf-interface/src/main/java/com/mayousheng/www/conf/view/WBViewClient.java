package com.mayousheng.www.conf.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public abstract class WBViewClient extends WebViewClient {

    private Context context;

    public WBViewClient(Context context) {
        this.context = context;
    }

    public abstract void openNewActivity(Intent intent);

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (null != url && !TextUtils.isEmpty(url) && !"about:blank".endsWith(url)) {
            Log.e("-----1", "open url = " + url);
            if (url.startsWith("http://") || url.startsWith("https://")) {
                view.loadUrl(url);
            } else if (url.startsWith("file:///android_asset/")) {
                view.loadUrl(url);
            } else {
                try {
                    Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    openNewActivity(it);
                } catch (Exception e) {
                    Log.e("-----1", "open url = " + url + ";e=" + e);
                }
            }
            return true;
        }
        return super.shouldOverrideUrlLoading(view, url);
    }
}

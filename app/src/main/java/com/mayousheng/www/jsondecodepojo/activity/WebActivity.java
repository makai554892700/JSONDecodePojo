package com.mayousheng.www.jsondecodepojo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.mayousheng.www.initview.ViewDesc;
import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.base.BaseActivity;
import com.mayousheng.www.jsondecodepojo.common.StaticParam;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * Created by makai on 17/12/11.
 */

public class WebActivity extends BaseActivity {

    @ViewDesc(viewId = R.id.web_view)
    public WebView webView;
    @ViewDesc(viewId = R.id.back)
    public ImageView backView;
    @ViewDesc(viewId = R.id.title)
    public TextView titleView;
    @ViewDesc(viewId = R.id.progress_bar)
    public ProgressBar progressBar;

    @Override
    protected int getLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String webUrl = bundle.getString(StaticParam.WEB_URL);
            if (!TextUtils.isEmpty(webUrl)) {
                Log.e("-----1", "webUrl=" + webUrl);
                backView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setDatabaseEnabled(true);
                webSettings.setAppCacheEnabled(true);
                webSettings.setPluginState(WebSettings.PluginState.ON);
                webSettings.setBuiltInZoomControls(true);
                webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
                webSettings.setUseWideViewPort(true);
                webSettings.setLoadWithOverviewMode(true);
                webSettings.setSavePassword(true);
                webSettings.setSaveFormData(true);
                webSettings.setGeolocationEnabled(true);
                webSettings.setDomStorageEnabled(true);
                webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
                webSettings.setDefaultTextEncodingName("UTF-8");
                webSettings.setAllowFileAccess(true);
                webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                }
                webSettings.setDatabasePath(getApplicationContext().getCacheDir()
                        .getAbsolutePath());
                webSettings.setAppCachePath(getApplicationContext().getCacheDir()
                        .getAbsolutePath());
                webSettings.setAppCacheMaxSize(Integer.MAX_VALUE);
                webView.requestFocus();
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                webView.setWebChromeClient(new WebChromeClient() {

                    @Override
                    public void onProgressChanged(WebView view, int newProgress) {
                        super.onProgressChanged(view, newProgress);
                        if (newProgress < 100) {
                            progressBar.setVisibility(View.VISIBLE);
                            progressBar.setProgress(newProgress);
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                        super.onReceivedTitle(view, title);
                        titleView.setText(title);
                    }

                    @Override
                    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                        new AlertDialog.Builder(WebActivity.this).setMessage(message)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.confirm();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.cancel();
                                    }
                                })
                                .setCancelable(false)
                                .show();
                        return super.onJsAlert(view, url, message, result);
                    }
                });
                webView.loadUrl(webUrl);
                return;
            }
        }
        finish();
        Toast.makeText(getApplicationContext(), "no web url", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

}
package com.mayousheng.www.conf.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.mayousheng.www.conf.R;
import com.mayousheng.www.conf.utils.StartUtils;
import com.mayousheng.www.conf.view.JSInterface;
import com.mayousheng.www.conf.view.WBViewClient;

import java.lang.reflect.Method;

public class WebActivity extends BaseLoadingActivity {

    public String url, title;
    private WebView mWebView;
    private ProgressBar progressBar;
    private FrameLayout mLayout;
    public static EventBack eventBack;
    public static boolean showTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_web);
        super.onCreate(savedInstanceState);
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        if (title == null) {
            title = "";
        }
        if (url == null) {
            finish();
            return;
        }
        Log.e("-----1", "open web url=" + url);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (showTitle) {

            } else {

            }
            actionBar.hide();
        }
        mWebView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);
        mLayout = findViewById(R.id.fl_video);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        mWebView.addJavascriptInterface(new JSInterface(new JSInterface.JavascriptCallback() {
            @Override
            public void onHtmlClickUrl(String url) {
                try {
                    runOnUiThread(() -> {
                        if (url != null) {
                            mWebView.loadUrl(url);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onEvent(String key) {
                Log.e("-----1", "AndroidEM key=" + key);
                if (eventBack != null) {
                    eventBack.onEvent(key);
                }
            }

            @Override
            public void customEvent(String eventUrl, String eventValue) {
                Log.e("-----1", "AndroidEM eventUrl=" + eventUrl + ";eventValue=" + eventValue);
                if (eventBack != null) {
                    eventBack.customEvent(eventUrl, eventValue);
                }
            }
        }), "AndroidEM");
        mWebView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(Uri.parse(url), mimetype);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            private CustomViewCallback mCustomViewCallback;
            //  横屏时，显示视频的view
            private View mCustomView;

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 0) {
                    if (eventBack != null) {
                        eventBack.startLoadUrl();
                    }
                }
                if (newProgress == 100) {
                    isLoading(false);
                    progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                    if (eventBack != null) {
                        eventBack.endLoadUrl();
                    }
                } else {
                    isLoading(true);
                    progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar.setProgress(newProgress);//设置进度值
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onConsoleMessage(String message, int lineNumber, String sourceID) {
//                Log.e("-----1", message + " -- from line" + lineNumber + " of " + sourceID);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
//                Log.e("-----1", "-----" + consoleMessage.message());
                return true;
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
                if (mCustomView != null) {
                    callback.onCustomViewHidden();
                    return;
                }
                mCustomView = view;
                mCustomView.setVisibility(View.VISIBLE);
                mCustomViewCallback = callback;
                mLayout.addView(mCustomView);
                mLayout.setVisibility(View.VISIBLE);
                mLayout.bringToFront();
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }

            // 切换为竖屏的时候调用
            @Override
            public void onHideCustomView() {
                super.onHideCustomView();
                if (mCustomView == null) {
                    return;
                }
                mCustomView.setVisibility(View.GONE);
                mLayout.removeView(mCustomView);
                mCustomView = null;
                mLayout.setVisibility(View.GONE);
                try {
                    mCustomViewCallback.onCustomViewHidden();
                } catch (Exception e) {
                }
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

        });
        mWebView.setWebViewClient(new WBViewClient(getApplicationContext()) {

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Class clazz = SslErrorHandler.class;
                try {
                    Method proceed = clazz.getDeclaredMethod("proceed");
                    proceed.invoke(handler);
                } catch (Exception e) {
                    Log.e("-----1", "onReceivedSslError e=" + e);
                }
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                try {
                    if (errorResponse != null && errorResponse.getStatusCode() != 200 && !request.getUrl().toString().endsWith("ico")) {
                        Log.e("-----1", "onReceivedHttpError statusCode=" + errorResponse.getStatusCode() + ";url=" + request.getUrl());
                        if (eventBack != null) {
                            eventBack.requestUrlError(request.getUrl().toString());
                        }
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                if (url.startsWith("http")) {
                    super.onLoadResource(view, url);
                } else {
                    StartUtils.openBrowser(WebActivity.this, url, false);
                }
            }

            @Override
            public void openNewActivity(Intent intent) {
                boolean haveError = false;
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    haveError = true;
                    Log.e("-----1", "open new activity error e = " + e);
                } finally {
                    if (eventBack != null) {
                        if (haveError) {
                            eventBack.openNewActivityFail();
                        } else {
                            eventBack.openNewActivitySuccess();
                        }
                    }
                }
            }
        });
        if (url != null) {
            mWebView.loadUrl(url);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        ActionBar actionBar = getSupportActionBar();
        switch (config.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                if (actionBar != null) {
                    actionBar.hide();
                }
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                if (actionBar != null) {
                    if (showTitle) {
                        actionBar.show();
                    } else {
                        actionBar.hide();
                    }
                }
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.web_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        backClicked();
    }

    public void backClicked() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_refresh) {
            try {
                mWebView.reload();
            } catch (Exception e) {
            }
        } else if (itemId == R.id.action_home) {
            try {
                if (url != null) {
                    mWebView.loadUrl(url);
                }
            } catch (Exception e) {
            }
        } else if (itemId == android.R.id.home) {
            backClicked();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
        mWebView = null;
    }

    public static abstract class EventBack {
        public abstract void onEvent(String eventKey);

        public abstract void customEvent(String eventKey, String eventValue);

        public void startLoadUrl() {
        }

        public void endLoadUrl() {
        }

        public void loadUrlSuccess() {
        }

        public void loadUrlFail() {
        }

        public void openNewActivitySuccess() {
        }

        public void openNewActivityFail() {
        }

        public void requestUrlError(String url) {
        }

    }

}

package com.mayousheng.www.jsondecodepojo.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by marking on 2017/4/11.
 */

public class ShowImageUtils {

    private View view;
    private Set<MyAsyncTast> myAsyncTasts;
    private ConcurrentHashMap<String, String> imgDescs;

    public void setView(View view) {
        if (this.view == null) {
            this.view = view;
        }
    }

    public void changeView(View view) {
        this.view = view;
    }

    public ShowImageUtils() {
        myAsyncTasts = new HashSet<MyAsyncTast>();
        imgDescs = new ConcurrentHashMap<>();
    }

    public ShowImageUtils(View view) {
        this.view = view;
        myAsyncTasts = new HashSet<MyAsyncTast>();
        imgDescs = new ConcurrentHashMap<>();
    }

    public ShowImageUtils setImgDescs(HashMap<String, String> imgDescs) {
        this.imgDescs.clear();
        return addImgDescs(imgDescs);
    }

    public ShowImageUtils addImgDescs(HashMap<String, String> imgDescs) {
        this.imgDescs.putAll(imgDescs);
        return this;
    }

    public ShowImageUtils addImgDesc(String tag, String url) {
        this.imgDescs.put(tag, url);
        return this;
    }

    public ShowImageUtils loadImages(HashMap<String, WeakReference<ImageView>> imageViewHashMap) {
        if (imgDescs != null && view != null) {
            String tag;
            WeakReference<ImageView> imageView;
            for (Map.Entry<String, WeakReference<ImageView>> entry : imageViewHashMap.entrySet()) {
                tag = entry.getKey();
                imageView = entry.getValue();
                loadImage(tag, imageView);
            }
        }
        return this;
    }

    public ShowImageUtils loadImage(String tag, WeakReference<ImageView> weakReference) {
        loadImage(tag, weakReference, null);
        return this;
    }

    public void loadImage(String tag, WeakReference<ImageView> weakReference, LoadImageBack loadImageBack) {
        if (tag == null || weakReference == null) {
            if (loadImageBack != null) {
                loadImageBack.onField("params is null");
            }
            return;
        }

        String url = imgDescs.get(tag);
        Bitmap tempBitmap;
        if (url != null) {
            tempBitmap = CacheUtils.getInstance().getBitmapByDisk(url);
            if (tempBitmap == null) {
                MyAsyncTast myAsyncTast = new MyAsyncTast(tag, url, weakReference, loadImageBack);
                myAsyncTast.execute();
                myAsyncTasts.add(myAsyncTast);
            } else {
                setImageViewByTag(weakReference, tempBitmap, tag);
                if (loadImageBack != null) {
                    loadImageBack.onSuccess();
                }
            }
        } else {
            if (loadImageBack != null) {
                loadImageBack.onField("url is null");
            }
        }
    }

    public ShowImageUtils stopAllTasks() {
        if (myAsyncTasts != null) {
            for (MyAsyncTast myAsyncTast : myAsyncTasts) {
                myAsyncTast.cancel(false);
            }
        }
        return this;
    }

    private class MyAsyncTast extends AsyncTask<String, Void, Bitmap> {
        private String tag;
        private String url;
        private WeakReference<ImageView> weakReference;
        private LoadImageBack loadImageBack;

        private MyAsyncTast(String tag, String url, WeakReference<ImageView> weakReference, LoadImageBack loadImageBack) {
            this.tag = tag;
            this.url = url;
            this.weakReference = weakReference;
            this.loadImageBack = loadImageBack;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            if (weakReference.get() == null || !tag.equals(weakReference.get().getTag())) {
                return null;//屏蔽滑动时无效请求
            }
            return CacheUtils.getInstance().getBitmap(url);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            String message = setImageViewByTag(weakReference, bitmap, tag);
            if (loadImageBack != null) {
                if (message == null) {
                    loadImageBack.onSuccess();
                } else {
                    loadImageBack.onField(message);
                }

            }
            myAsyncTasts.remove(this);
        }
    }

    private String setImageViewByTag(WeakReference<ImageView> imageViewReference, Bitmap bitmap, String tag) {
        if (bitmap == null || imageViewReference == null || tag == null) {
            return "params is null.";
        }
        ImageView imageView = imageViewReference.get();
        if (imageView == null) {
            return "iamgeView is null.";
        }
        if (tag.equals(imageView.getTag())) {
            imageView.setImageBitmap(bitmap);
        } else {
            return "tag is not equals.tag=" + tag;
        }
        return null;
    }

    public interface LoadImageBack {
        public void onField(String message);

        public void onSuccess();
    }

}

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
            String key;
            String url;
            Bitmap tempBitmap;
            WeakReference<ImageView> imageView;
            for (Map.Entry<String, WeakReference<ImageView>> entry : imageViewHashMap.entrySet()) {
                key = entry.getKey();
                imageView = entry.getValue();
                url = imgDescs.get(key);
                if (url != null) {
                    tempBitmap = CacheUtils.getInstance().getBitmapByDisk(url);
                    if (tempBitmap == null) {
                        MyAsyncTast myAsyncTast = new MyAsyncTast(key, url, imageView);
                        myAsyncTast.execute();
                        myAsyncTasts.add(myAsyncTast);
                    } else {
                        setImageViewByTag(imageView, tempBitmap, key);
                    }
                } else {
                    Log.e("-----1", "url is null.key=" + key);
                }
            }
        }
        return this;
    }

    public ShowImageUtils loadImage(String tag, WeakReference<ImageView> weakReference) {
        if (tag != null && weakReference != null) {
            String url = imgDescs.get(tag);
            Bitmap tempBitmap;
            if (url != null) {
                tempBitmap = CacheUtils.getInstance().getBitmapByDisk(url);
                if (tempBitmap == null) {
                    MyAsyncTast myAsyncTast = new MyAsyncTast(tag, url, weakReference);
                    myAsyncTast.execute();
                    myAsyncTasts.add(myAsyncTast);
                } else {
                    setImageViewByTag(weakReference, tempBitmap, tag);
                }
            } else {
                Log.e("-----1", "tag=" + tag);
            }
        }
        return this;
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

        private MyAsyncTast(String tag, String url, WeakReference<ImageView> weakReference) {
            this.tag = tag;
            this.url = url;
            this.weakReference = weakReference;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            if (weakReference.get() == null || !tag.equals(weakReference.get().getTag())) {
                Log.e("-----1", "stop doInBackground.");//屏蔽滑动时无效请求
                return null;
            }
            return CacheUtils.getInstance().getBitmap(url);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            setImageViewByTag(weakReference, bitmap, tag);
            myAsyncTasts.remove(this);
        }
    }

    private void setImageViewByTag(WeakReference<ImageView> imageViewReference, Bitmap bitmap, String tag) {
//        Log.e("-----1", "show img tag=" + tag);
        if (bitmap == null || imageViewReference == null || tag == null) {
            Log.e("-----1", "imageView=" + imageViewReference + ";bitmap=" + bitmap + ";tag=" + tag);
            return;
        }
        ImageView imageView = imageViewReference.get();
        if (imageView == null) {
            Log.e("-----1", "image view is null.tag=" + tag);
            return;
        }
        if (tag.equals(imageView.getTag())) {
            imageView.setImageBitmap(bitmap);
        } else {
            Log.e("-----1", "tag is not equals.tag=" + tag);
        }
    }

}

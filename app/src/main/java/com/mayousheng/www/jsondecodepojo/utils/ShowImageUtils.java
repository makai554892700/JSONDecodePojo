package com.mayousheng.www.jsondecodepojo.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import com.mayousheng.www.jsondecodepojo.adapter.NewsAdapter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by marking on 2017/4/11.
 */

public class ShowImageUtils {

    private ListView listView;
    private Set<MyAsyncTast> myAsyncTasts;

    public ShowImageUtils(ListView listView) {
        this.listView = listView;
        myAsyncTasts = new HashSet<MyAsyncTast>();
    }

    public void loadImage(int start, int end) {
        Log.e("-----1", "NewsAdapter.URLS=" + NewsAdapter.URLS + ";start=" + start + ";end=" + end);
        if (NewsAdapter.URLS != null) {
            for (int i = start; i < end; i++) {
                if (NewsAdapter.URLS.length > i) {
                    String url = NewsAdapter.URLS[i];
                    Bitmap tempBitmap = CacheUtils.getInstance().getBitmapByDisk(url);
                    if (tempBitmap == null) {
                        MyAsyncTast myAsyncTast = new MyAsyncTast(url);
                        myAsyncTast.execute(url);
                        myAsyncTasts.add(myAsyncTast);
                    } else {
                        setImageViewByUrl(tempBitmap, url);
                    }
                }
            }
        }
    }

    public void stopAllTasks() {
        if (myAsyncTasts != null) {
            for (MyAsyncTast myAsyncTast : myAsyncTasts) {
                myAsyncTast.cancel(false);
            }
        }
    }

    private class MyAsyncTast extends AsyncTask<String, Void, Bitmap> {
        private String imgUrl;

        private MyAsyncTast(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap result = null;
            if (params != null && params.length > 0) {
                result = CacheUtils.getInstance().getBitmap(params[0]);
            }
            return result;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            setImageViewByUrl(bitmap, imgUrl);
            myAsyncTasts.remove(this);
        }
    }

    public void setImageViewByUrl(Bitmap bitmap, String imgUrl) {
        ImageView imageView = (ImageView) listView.findViewWithTag(imgUrl);
        if (imageView != null && bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

}

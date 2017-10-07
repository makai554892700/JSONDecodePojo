package com.mayousheng.www.jsondecodepojo.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by marking on 2017/4/11.
 */

public class ShowImageUtils {

    private View view;
    private Set<MyAsyncTast> myAsyncTasts;
    private ImgDesc[] imgDescs;

    public ShowImageUtils(View view) {
        this.view = view;
        myAsyncTasts = new HashSet<MyAsyncTast>();
    }

    public ImgDesc[] getImgDescs() {
        return imgDescs;
    }

    public ShowImageUtils setImgDescs(ImgDesc[] imgDescs) {
        this.imgDescs = imgDescs;
        return this;
    }

    public ShowImageUtils loadImage(int start, int end) {
        if (imgDescs != null) {
            for (int i = start; i < end; i++) {
                if (imgDescs.length > i) {
                    ImgDesc imgDesc = imgDescs[i];
                    Bitmap tempBitmap = CacheUtils.getInstance().getBitmapByDisk(imgDesc.url);
                    if (tempBitmap == null) {
                        MyAsyncTast myAsyncTast = new MyAsyncTast(imgDesc);
                        myAsyncTast.execute();
                        myAsyncTasts.add(myAsyncTast);
                    } else {
                        setImageViewByTag(tempBitmap, imgDesc.tag);
                    }
                }
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
        private ImgDesc imgDesc;

        private MyAsyncTast(ImgDesc imgDesc) {
            this.imgDesc = imgDesc;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return CacheUtils.getInstance().getBitmap(imgDesc.url);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            setImageViewByTag(bitmap, imgDesc.tag);
            myAsyncTasts.remove(this);
        }
    }

    public void setImageViewByTag(Bitmap bitmap, String tag) {
        ImageView imageView = (ImageView) view.findViewWithTag(tag);
        if (imageView != null && bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

    public static class ImgDesc {
        public String tag;
        public String url;

        public ImgDesc() {
        }

        public ImgDesc(String tag, String url) {
            this.tag = tag;
            this.url = url;
        }
    }

}

package com.mayousheng.www.jsondecodepojo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Created by marking on 2017/4/11.
 */

public class ShowImageUtils {

    private Context context;

    public void loadImage(Context context, final String imgUrl, final ImageView imageView) {
        this.context = context;
        new MyAsyncTast(imgUrl, imageView).execute(imgUrl);
    }

    private class MyAsyncTast extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;
        private String imgUrl;

        private MyAsyncTast(String imgUrl, ImageView imageView) {
            this.imageView = imageView;
            this.imgUrl = imgUrl;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap result = null;
            if (params != null && params.length > 0) {
                result = new CacheUtils().getBitmap(context, params[0]);
            }
            return result;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            String url = (String) imageView.getTag();
            if (url == null || imgUrl == null) {
                return;
            }
            if (url.equals(imgUrl)) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

}

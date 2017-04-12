package com.mayousheng.www.jsondecodepojo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by marking on 2017/4/11.
 */

public class ShowImageUtils {

    public void loadImage(final String imgUrl, final ImageView imageView) {
        new MyAsyncTast(imgUrl,imageView).execute(imgUrl);
    }

    private Bitmap getBitmapFromUrl(String imgUrl) {
        Bitmap result = null;
        do {
            if (imgUrl == null || imgUrl.isEmpty()) {
                break;
            }
            URL url;
            try {
                url = new URL(imgUrl);
            } catch (Exception e) {
                break;
            }
            HttpURLConnection httpURLConnection;
            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            } catch (Exception e) {
                break;
            }
            BufferedInputStream bufferedInputStream = null;
            try {
                bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                result = BitmapFactory.decodeStream(bufferedInputStream);
            } catch (Exception e) {
                break;
            } finally {
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (Exception e) {
                    }
                }
            }
        } while (false);
        return result;
    }

    private class MyAsyncTast extends AsyncTask<String,Void,Bitmap>{
        private ImageView imageView;
        private String imgUrl;

        private MyAsyncTast(String imgUrl, ImageView imageView){
            this.imageView = imageView;
            this.imgUrl = imgUrl;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap result = null;
            if(params != null && params.length > 0){
                result = getBitmapFromUrl(params[0]);
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

package com.mayousheng.www.jsondecodepojo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by marking on 2017/4/11.
 */

public class ShowImageUtils {
    private ImageView imageView;
    private String imgUrl;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String url = (String) imageView.getTag();
            if (url == null || imgUrl == null) {
                return;
            }
            if (url.equals(imgUrl)) {
                imageView.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };

    public void loadImage(final String imgUrl, final ImageView imageView) {
        this.imageView = imageView;
        this.imgUrl = imgUrl;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = getBitmapFromUrl(imgUrl);
                if (bitmap == null || imageView == null) {
                    return;
                }
                Message message = new Message();
                message.obj = bitmap;
                handler.sendMessage(message);
            }
        }).start();
    }

    private Bitmap getBitmapFromUrl(String imgUrl) {
        Bitmap result = null;
        do {
            if (imgUrl == null || imgUrl.isEmpty()) {
                break;
            }
            URL url = null;
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

}

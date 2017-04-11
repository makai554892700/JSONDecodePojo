package com.mayousheng.www.jsondecodepojo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by marking on 2017/4/11.
 */

public class ShowImageUtils {
    private ImageView imageView;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String url = (String) imageView.getTag();
            String imgUrl = msg.getData().toString();
        }
    };

    public void loadImage(String imgUrl, ImageView imageView) {
        Bitmap bitmap = getBitmapFromUrl(imgUrl);
        if (bitmap == null || imageView == null || imageView.isOpaque()) {
            return;
        }
        this.imageView = imageView;
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
            result = BitmapFactory.decodeStream(bufferedInputStream);
        } while (false);
        return result;
    }

}

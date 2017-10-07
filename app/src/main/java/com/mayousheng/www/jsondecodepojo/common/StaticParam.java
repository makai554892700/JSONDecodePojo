package com.mayousheng.www.jsondecodepojo.common;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ma kai on 2017/9/29.
 */

public class StaticParam {

    private static final String BASE_URL = "http://api.markingyun.cn";//基本域名
    public static final String BASE_GET_JOKES = BASE_URL + "/sbgnews/jokes/getJokes";//获取新闻请求路径
    public static final String BASE_GET_BSBDJ_VIDEOS = BASE_URL + "/sbgnews/bsbdj/getVideos";//百思不得姐获取视频请求路径
    public static final String BASE_GET_BSBDJ_VOICE = BASE_URL + "/sbgnews/bsbdj/getVoices";//百思不得姐获取新闻请求路径
    public static final String BASE_GET_BSBDJ_PHOTO = BASE_URL + "/sbgnews/bsbdj/getPhotos";//百思不得姐获取新闻请求路径
    public static final String BASE_GET_BSBDJ_PUNSTER = BASE_URL + "/sbgnews/bsbdj/getPunsters";//百思不得姐获取新闻请求路径
    public static final RecyclerView.ItemDecoration DEFAULT_ITEM_DECORATION = new RecyclerView.ItemDecoration() {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0, 0, 0, 1);
        }
    };
    public static final String TAG_USER_IMG_URL = "tag_user_img_url";
    public static final String TAG_IMG_URL = "tag_img_url";
}

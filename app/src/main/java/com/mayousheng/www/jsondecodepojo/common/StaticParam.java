package com.mayousheng.www.jsondecodepojo.common;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ma kai on 2017/9/29.
 */

public class StaticParam {

    private static final String BASE_URL = "http://api.woaizhuangbi.com";//基本域名
    //    private static final String BASE_URL = "http://192.168.0.110";//基本域名
    public static final String BASE_POST_JOKES = BASE_URL + "/sbgnews/api/jokes/getJokes";//获取新闻请求路径
    public static final String BASE_POST_BSBDJ_VIDEOS = BASE_URL + "/sbgnews/api/bsbdj/getVideos";//百思不得姐获取视频请求路径
    public static final String BASE_POST_BSBDJ_VOICE = BASE_URL + "/sbgnews/api/bsbdj/getVoices";//百思不得姐获取新闻请求路径
    public static final String BASE_POST_BSBDJ_PHOTO = BASE_URL + "/sbgnews/api/bsbdj/getPhotos";//百思不得姐获取新闻请求路径
    public static final String BASE_POST_BSBDJ_PUNSTER = BASE_URL + "/sbgnews/api/bsbdj/getPunsters";//百思不得姐获取新闻请求路径
    public static final String BASE_GET_USER_REGISTER = BASE_URL + "/sbgnews/api/user/register";//用户注册
    public static final String BASE_GET_USER_LOGIN = BASE_URL + "/sbgnews/api/user/login";//用户登陆
    public static final String BASE_GET_USER_LOGOUT = BASE_URL + "/sbgnews/api/user/logout";//用户登出
    public static final String BASE_GET_USER_UPDATE = BASE_URL + "/sbgnews/api/user/update";//用户信息更新
    public static final String BASE_POST_OPERATE_LOVE = BASE_URL + "/sbgnews/api/operate/love";//喜欢这则新闻
    public static final String BASE_POST_OPERATE_HATE = BASE_URL + "/sbgnews/api/operate/hate";//讨厌这则新闻
    public static final String BASE_POST_OPERATE_SHARE = BASE_URL + "/sbgnews/api/operate/share";//分享了这则新闻
    public static final String BASE_POST_OPERATE_COMMENT = BASE_URL + "/sbgnews/api/operate/comment";//评论了这则新闻
    public static final RecyclerView.ItemDecoration DEFAULT_ITEM_DECORATION = new RecyclerView.ItemDecoration() {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0, 0, 0, 1);
        }
    };
    public static final String TAG_USER_IMG_URL = "tag_user_img_url";
    public static final String TAG_IMG_URL = "tag_img_url";
    public static final String NULL = "null";
    public static final String USER_SESSION = "user_session";
    public static final String SESSION_SET_KEY = "Cookie";
    public static final boolean REMBER_ME = true;
    public static final int TYPE_ANDROID = 1;
    public static final float BSBDJ_VIDEO_SCALE = 0.75f;
}

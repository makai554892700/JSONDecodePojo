package com.mayousheng.www.jsondecodepojo.utils;

import com.mayousheng.www.basepojo.BasePoJo;
import com.mayousheng.www.jsondecodepojo.common.StaticParam;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJPhotoResponse;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJPunsterResponse;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJVideoResponse;
import com.mayousheng.www.jsondecodepojo.pojo.DataBack;
import com.mayousheng.www.jsondecodepojo.pojo.JokeResponse;
import com.mayousheng.www.jsondecodepojo.pojo.NewsSearch;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJVoiceResponse;

import java.util.ArrayList;

/**
 * Created by marking on 2017/4/11.
 */

public class InfoUtils {

    public static void getJokes(Integer page, Integer count
            , ArrayListBack<JokeResponse> back) {
        InfoUtils.getNewsInfos(StaticParam.BASE_GET_JOKES, page, count
                , new JokeResponse(null), back);
    }

    public static void getBSBDJVoices(Integer page, Integer count
            , ArrayListBack<BSBDJVoiceResponse> back) {
        InfoUtils.getNewsInfos(StaticParam.BASE_GET_BSBDJ_VOICE, page, count
                , new BSBDJVoiceResponse(null), back);
    }

    public static void getBSBDJVideos(Integer page, Integer count
            , ArrayListBack<BSBDJVideoResponse> jokeResponseArrayListBack) {
        InfoUtils.getNewsInfos(StaticParam.BASE_GET_BSBDJ_VIDEOS, page, count
                , new BSBDJVideoResponse(null), jokeResponseArrayListBack);
    }

    public static void getBSBDJPhotos(Integer page, Integer count
            , ArrayListBack<BSBDJPhotoResponse> back) {
        InfoUtils.getNewsInfos(StaticParam.BASE_GET_BSBDJ_PHOTO, page, count
                , new BSBDJPhotoResponse(null), back);
    }

    public static void getBSBDJPunsters(Integer page, Integer count
            , ArrayListBack<BSBDJPunsterResponse> back) {
        InfoUtils.getNewsInfos(StaticParam.BASE_GET_BSBDJ_PUNSTER, page, count
                , new BSBDJPunsterResponse(null), back);
    }

    public static <T extends BasePoJo> void getNewsInfos(final String url, int page, int count
            , final T t, final ArrayListBack<T> arrayListBack) {
        final NewsSearch newsSearch = new NewsSearch(count, page);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                DataBack<ArrayList<T>> dataBack =
                        CommonRequestUtils.postDatasBack(url
                                , JsonHeardUtils.getInstance().getHeard(),
                                newsSearch.toString().getBytes(), t);
                if (dataBack == null) {
                    arrayListBack.onFail(-1, "no response");
                } else {
                    if (dataBack.code == 0) {
                        arrayListBack.onResult(dataBack.data);
                    } else {
                        arrayListBack.onFail(dataBack.code, dataBack.msg);
                    }
                }
            }
        };
        ThreadUtils.executor.execute(runnable);
    }

}

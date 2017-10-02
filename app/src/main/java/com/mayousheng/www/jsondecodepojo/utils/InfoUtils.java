package com.mayousheng.www.jsondecodepojo.utils;

import com.mayousheng.www.jsondecodepojo.pojo.DataBack;
import com.mayousheng.www.jsondecodepojo.pojo.JokeResponse;
import com.mayousheng.www.jsondecodepojo.pojo.NewsSearch;
import com.mayousheng.www.jsondecodepojo.pojo.StaticParam;

import java.util.ArrayList;

/**
 * Created by marking on 2017/4/11.
 */

public class InfoUtils {

    public static void getNewsInfo(int page, int count, final ArrayListBack<JokeResponse> arrayListBack) {
        final NewsSearch newsSearch = new NewsSearch(count, page);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                DataBack<ArrayList<JokeResponse>> dataBack =
                        CommonRequestUtils.postDatasBack(StaticParam.BASE_GET_NEWS
                                , JsonHeardUtils.getInstance().getHeard(),
                                newsSearch.toString().getBytes(), new JokeResponse(null));
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

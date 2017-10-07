package com.mayousheng.www.jsondecodepojo.utils;

import com.mayousheng.www.jsondecodepojo.pojo.JokeResponse;

import java.util.List;

/**
 * Created by ma kai on 2017/10/7.
 */

public class CommonNewsUtils {

    public static ShowImageUtils.ImgDesc[] getImgDescs(List<JokeResponse> jokeResponses) {
        ShowImageUtils.ImgDesc[] result = new ShowImageUtils.ImgDesc[jokeResponses.size()];
        int i = 0;
        for (JokeResponse jokeResponse : jokeResponses) {
            result[i++] = new ShowImageUtils.ImgDesc(String.valueOf(jokeResponse.mark)
                    , jokeResponse.userDesc.imgUrl);
        }
        return result;
    }

}

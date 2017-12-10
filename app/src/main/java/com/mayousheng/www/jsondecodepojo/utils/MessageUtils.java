package com.mayousheng.www.jsondecodepojo.utils;

import com.mayousheng.www.jsondecodepojo.base.BaseResponse;
import com.mayousheng.www.jsondecodepojo.common.StaticParam;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJPhotoResponse;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJVideoResponse;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJVoiceResponse;

import java.util.HashMap;
import java.util.List;

/**
 * Created by makai on 2017/12/9.
 */

public class MessageUtils {

    public static <T extends BaseResponse> HashMap<String, String> response2Map(List<T> baseResponses) {
        if (baseResponses == null) {
            return null;
        }
        HashMap<String, String> result = new HashMap<>();
        for (T baseResponse : baseResponses) {
            if (baseResponse instanceof BSBDJPhotoResponse) {
                result.put(StaticParam.TAG_IMG_URL + baseResponse.newsDesc.newsMark
                        , ((BSBDJPhotoResponse) baseResponse).scImg);
            } else if (baseResponse instanceof BSBDJVoiceResponse) {
                result.put(StaticParam.TAG_IMG_URL + baseResponse.newsDesc.newsMark
                        , ((BSBDJVoiceResponse) baseResponse).cdnImg);
            } else if (baseResponse instanceof BSBDJVideoResponse) {
                result.put(StaticParam.TAG_IMG_URL + baseResponse.newsDesc.newsMark
                        , ((BSBDJVideoResponse) baseResponse).scImg);
            }
            result.put(StaticParam.TAG_USER_IMG_URL + baseResponse.newsDesc.newsMark
                    , baseResponse.userDesc.imgUrl);
        }
        return result;
    }

}

package com.mayousheng.www.jsondecodepojo.pojo;

import com.mayousheng.www.basepojo.FieldDesc;
import com.mayousheng.www.jsondecodepojo.base.BaseResponse;

public class BSBDJVoiceResponse extends BaseResponse {

    @FieldDesc(key = "voiceuri")
    public String voiceuri;        //声音url
    @FieldDesc(key = "cdnImg")
    public String cdnImg;            //cdn图片(缩放)

    public BSBDJVoiceResponse(String jsonStr) {
        super(jsonStr);
    }
}

package com.mayousheng.www.jsondecodepojo.pojo;

import com.mayousheng.www.basepojo.FieldDesc;
import com.mayousheng.www.jsondecodepojo.base.BaseResponse;

public class BSBDJVideoResponse extends BaseResponse {

    @FieldDesc(key = "url")
    public String url;   //详情跳转url
    @FieldDesc(key = "videoUri")
    public String videoUri;        //视频地址

    public BSBDJVideoResponse(String jsonStr) {
        super(jsonStr);
    }
}

package com.mayousheng.www.jsondecodepojo.pojo;

import com.mayousheng.www.basepojo.FieldDesc;
import com.mayousheng.www.jsondecodepojo.base.BaseResponse;

public class BSBDJPunsterResponse extends BaseResponse {

    @FieldDesc(key = "url")
    public String url;   //详情跳转url
    public BSBDJPunsterResponse(String jsonStr) {
        super(jsonStr);
    }
}

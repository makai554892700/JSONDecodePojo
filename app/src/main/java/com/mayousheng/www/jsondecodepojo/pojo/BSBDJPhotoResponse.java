package com.mayousheng.www.jsondecodepojo.pojo;

import com.mayousheng.www.basepojo.FieldDesc;
import com.mayousheng.www.jsondecodepojo.base.BaseResponse;

public class BSBDJPhotoResponse extends BaseResponse {

    @FieldDesc(key = "cdnImg")
    public String cdnImg;     //cdn图片地址

    public BSBDJPhotoResponse(String jsonStr) {
        super(jsonStr);
    }
}

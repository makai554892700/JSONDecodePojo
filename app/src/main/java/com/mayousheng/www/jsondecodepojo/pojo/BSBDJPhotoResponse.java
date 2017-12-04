package com.mayousheng.www.jsondecodepojo.pojo;

import com.mayousheng.www.basepojo.FieldDesc;
import com.mayousheng.www.jsondecodepojo.base.BaseResponse;

public class BSBDJPhotoResponse extends BaseResponse {

    @FieldDesc(key = "cdnImg")
    public String cdnImg;     //cdn图片地址
    @FieldDesc(key = "scImg")
    public String scImg;     //缩略图片
    @FieldDesc(key = "width")
    public Integer width;      //宽
    @FieldDesc(key = "height")
    public Integer height;     //高

    public BSBDJPhotoResponse(String jsonStr) {
        super(jsonStr);
    }
}

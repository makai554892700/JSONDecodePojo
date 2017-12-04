package com.mayousheng.www.jsondecodepojo.pojo;

import com.mayousheng.www.basepojo.FieldDesc;
import com.mayousheng.www.jsondecodepojo.base.BaseResponse;

public class BSBDJVideoResponse extends BaseResponse {

    @FieldDesc(key = "videoUri")
    public String videoUri;        //视频地址
    @FieldDesc(key = "scImg")
    public String scImg;     //缩略图片
    @FieldDesc(key = "width")
    public Integer width;      //宽
    @FieldDesc(key = "height")
    public Integer height;     //高
    @FieldDesc(key = "playTime")
    public Integer playTime;   //播放时长

    public BSBDJVideoResponse(String jsonStr) {
        super(jsonStr);
    }
}

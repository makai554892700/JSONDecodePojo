package com.mayousheng.www.jsondecodepojo.pojo;

import com.mayousheng.www.basepojo.BasePoJo;
import com.mayousheng.www.basepojo.FieldDesc;

public class BSBDJPhotoResponse extends BasePoJo {

    @FieldDesc(key = "mark")
    public Integer mark;        //唯一标记
    @FieldDesc(key = "newsDesc")
    public NewsDesc newsDesc;  //新闻描述
    @FieldDesc(key = "userDesc")
    public UserDesc userDesc;  //用户描述
    @FieldDesc(key = "text")
    public String text;        //基本内容
    @FieldDesc(key = "url")
    public String url;   //详情跳转url
    @FieldDesc(key = "cdnImg")
    public String cdnImg;     //cdn图片地址

    public BSBDJPhotoResponse(String jsonStr) {
        super(jsonStr);
    }
}

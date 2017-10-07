package com.mayousheng.www.jsondecodepojo.base;

import com.mayousheng.www.basepojo.BasePoJo;
import com.mayousheng.www.basepojo.FieldDesc;
import com.mayousheng.www.jsondecodepojo.pojo.NewsDesc;
import com.mayousheng.www.jsondecodepojo.pojo.UserDesc;

public class BaseResponse extends BasePoJo {

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

    public BaseResponse(String jsonStr) {
        super(jsonStr);
    }

}

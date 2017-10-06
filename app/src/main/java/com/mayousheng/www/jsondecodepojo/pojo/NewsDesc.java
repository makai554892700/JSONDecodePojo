package com.mayousheng.www.jsondecodepojo.pojo;

import com.mayousheng.www.basepojo.BasePoJo;
import com.mayousheng.www.basepojo.FieldDesc;

public class NewsDesc extends BasePoJo {

    @FieldDesc(key = "love")
    public Integer love;         //点赞的数量
    @FieldDesc(key = "hate")
    public Integer hate;         //点踩的数量
    @FieldDesc(key = "share")
    public Integer share;        //分享的数量
    @FieldDesc(key = "comment")
    public Integer comment;      //评论的数量
    @FieldDesc(key = "createTime")
    public String createTime;    //创建时间

    public NewsDesc(String jsonStr) {
        super(jsonStr);
    }

}

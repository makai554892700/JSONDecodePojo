package com.mayousheng.www.jsondecodepojo.pojo;

import com.mayousheng.www.basepojo.BasePoJo;
import com.mayousheng.www.basepojo.FieldDesc;

public class JokeResponse extends BasePoJo {

    @FieldDesc(key = "title")
    public String title;//标题
    @FieldDesc(key = "text")
    public String text;//内容
    @FieldDesc(key = "time")
    public String time;//笑话创建时间

    public JokeResponse(String jsonStr) {
        super(jsonStr);
    }

    public JokeResponse(String title, String text, String time) {
        super(null);
        this.title = title;
        this.text = text;
        this.time = time;
    }
}

package com.mayousheng.www.jsondecodepojo.pojo;

import com.mayousheng.www.basepojo.FieldDesc;
import com.mayousheng.www.jsondecodepojo.base.BaseResponse;

public class JokeResponse extends BaseResponse {

    @FieldDesc(key = "title")
    public String title;//标题

    public JokeResponse(String jsonStr) {
        super(jsonStr);
    }

}

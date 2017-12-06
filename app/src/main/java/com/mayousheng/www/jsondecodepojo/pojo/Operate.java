package com.mayousheng.www.jsondecodepojo.pojo;

import com.mayousheng.www.basepojo.BasePoJo;
import com.mayousheng.www.basepojo.FieldDesc;

/**
 * Created by makai on 2017/12/6.
 */

public class Operate extends BasePoJo {

    @FieldDesc(key = "newsMark")
    public Integer newsMark;
    @FieldDesc(key = "newsType")
    public String newsType;

    public Operate(String jsonStr) {
        super(jsonStr);
    }

    public Operate(Integer newsMark, String newsType) {
        super(null);
        this.newsMark = newsMark;
        this.newsType = newsType;
    }
}

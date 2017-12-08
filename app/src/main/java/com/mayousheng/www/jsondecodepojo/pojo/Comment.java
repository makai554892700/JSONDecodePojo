package com.mayousheng.www.jsondecodepojo.pojo;

import com.mayousheng.www.basepojo.BasePoJo;
import com.mayousheng.www.basepojo.FieldDesc;

/**
 * Created by makai on 2017/12/6.
 */

public class Comment extends BasePoJo {

    @FieldDesc(key = "newsMark")
    public Operate newsMark;
    @FieldDesc(key = "commentInfo")
    public String commentInfo;

    public Comment(String jsonStr) {
        super(jsonStr);
    }

    public Comment(Operate newsMark, String commentInfo) {
        super(null);
        this.newsMark = newsMark;
        this.commentInfo = commentInfo;
    }
}

package com.mayousheng.www.jsondecodepojo.pojo;

import com.mayousheng.www.basepojo.BasePoJo;
import com.mayousheng.www.basepojo.FieldDesc;

/**
 * Created by marking on 2017/5/20.
 */

public class NewsType extends BasePoJo {

    @FieldDesc(key = "title")
    public String title;
    @FieldDesc(key = "type")
    public String type;

    public NewsType(String jsonStr) {
        super(jsonStr);
    }


}

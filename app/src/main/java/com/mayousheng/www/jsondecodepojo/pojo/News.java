package com.mayousheng.www.jsondecodepojo.pojo;

import com.mayousheng.www.basepojo.BasePoJo;
import com.mayousheng.www.basepojo.FieldDesc;

/**
 * Created by marking on 2017/4/11.
 */

public class News extends BasePoJo {

    @FieldDesc(key = "title")
    public String title;
    @FieldDesc(key = "description")
    public String description;
    @FieldDesc(key = "picUrl")
    public String picUrl;
    @FieldDesc(key = "url")
    public String url;
    @FieldDesc(key = "ctime")
    public String ctime;

    public News(String jsonStr) {
        super(jsonStr);
    }

}

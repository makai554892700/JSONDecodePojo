package com.mayousheng.www.jsondecodepojo.pojo;

import com.mayousheng.www.basepojo.BasePoJo;
import com.mayousheng.www.basepojo.FieldDesc;

/**
 * Created by ma kai on 2017/9/29.
 */

public class NewsSearch extends BasePoJo {

    @FieldDesc(key = "count")
    public int count;
    @FieldDesc(key = "page")
    public int page;

    public NewsSearch(int count, int page) {
        super(null);
        this.count = count;
        this.page = page;
    }

    public NewsSearch(String jsonStr) {
        super(jsonStr);
    }

}

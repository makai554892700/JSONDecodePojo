package com.mayousheng.www.jsondecodepojo.pojo;

import com.mayousheng.www.basepojo.BasePoJo;
import com.mayousheng.www.basepojo.FieldDesc;

/**
 * Created by ma kai on 2017/9/29.
 */

public class DataBack<T> extends BasePoJo {

    @FieldDesc(key = "code")
    public Integer code;
    @FieldDesc(key = "msg")
    public String msg;
    @FieldDesc(key = "data")
    public T data;

    public DataBack(String jsonStr) {
        super(jsonStr);
    }
}

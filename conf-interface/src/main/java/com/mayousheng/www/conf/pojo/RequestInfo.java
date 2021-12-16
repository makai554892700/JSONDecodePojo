package com.mayousheng.www.conf.pojo;

import com.mayousheng.www.basepojo.BasePoJo;
import com.mayousheng.www.basepojo.FieldDesc;

public class RequestInfo extends BasePoJo {

    @FieldDesc(key = "packageName")
    public String packageName;
    @FieldDesc(key = "data")
    public String data;

    public RequestInfo() {
        super(null);
    }

    public RequestInfo(String jsonStr) {
        super(jsonStr);
    }

    public RequestInfo(String packageName, String data) {
        super(null);
        this.packageName = packageName;
        this.data = data;
    }

}

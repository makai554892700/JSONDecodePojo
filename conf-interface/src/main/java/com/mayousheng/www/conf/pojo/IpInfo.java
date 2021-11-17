package com.mayousheng.www.conf.pojo;

import com.mayousheng.www.basepojo.BasePoJo;
import com.mayousheng.www.basepojo.FieldDesc;

public class IpInfo extends BasePoJo {

    @FieldDesc(key = "country_code")
    public String countryCode;
    @FieldDesc(key = "ip")
    public String ip;

    public IpInfo() {
        super(null);
    }

    public IpInfo(String jsonStr) {
        super(jsonStr);
    }


    public IpInfo(String countryCode, String ip) {
        super(null);
        this.countryCode = countryCode;
        this.ip = ip;
    }

}

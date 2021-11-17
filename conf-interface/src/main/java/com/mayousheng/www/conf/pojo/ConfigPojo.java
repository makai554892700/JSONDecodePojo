package com.mayousheng.www.conf.pojo;

import com.mayousheng.www.basepojo.BasePoJo;
import com.mayousheng.www.basepojo.FieldDesc;

import java.util.ArrayList;

public class ConfigPojo extends BasePoJo {

    @FieldDesc(key = "is_open")
    public Boolean isOpen;
    @FieldDesc(key = "config_type")
    public Integer configType;              // 0 / 1
    @FieldDesc(key = "config_server")
    public String configServer;
    @FieldDesc(key = "filter")
    public Boolean filter;
    @FieldDesc(key = "white_country_code", arrayType = String.class)
    public ArrayList<String> whiteCountryCode;  // ['CN','US'....]
    @FieldDesc(key = "black_ip", arrayType = String.class)
    public ArrayList<String> blackIp;           // ['192.168.1.10'.....]
    @FieldDesc(key = "skeep_old")
    public Boolean skeepOld;
    @FieldDesc(key = "is_inner")
    public Boolean isInner;
    @FieldDesc(key = "package_name")
    public String packageName;
    @FieldDesc(key = "package_version")
    public Integer packageVersion;
    @FieldDesc(key = "url")
    public String url;

    public ConfigPojo() {
        super(null);
    }

    public ConfigPojo(String jsonStr) {
        super(jsonStr);
    }

}

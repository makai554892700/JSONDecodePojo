package com.mayousheng.www.jsondecodepojo.pojo;

import com.mayousheng.www.jsondecodepojo.common.BasePoJo;
import com.mayousheng.www.jsondecodepojo.common.FieldDesc;

/**
 * Created by marking on 2017/3/26.
 */

public class TestUser extends BasePoJo {

    @FieldDesc(key = "user_name")//描述对应的JSONObject的key
    public String userName;
    @FieldDesc(key = "user_age")//描述对应的JSONObject的key
    public int age;

    //普通构造
    public TestUser() {
        super(null);
    }

    //固有构造（必须，否则可能无法正常转换）
    public TestUser(String jsonStr) {
        super(jsonStr);
    }

}

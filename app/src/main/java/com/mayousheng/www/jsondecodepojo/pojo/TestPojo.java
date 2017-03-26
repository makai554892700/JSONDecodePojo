package com.mayousheng.www.jsondecodepojo.pojo;

import com.mayousheng.www.jsondecodepojo.common.BasePoJo;
import com.mayousheng.www.jsondecodepojo.common.FieldDesc;

import java.util.ArrayList;

/**
 * 测试类用于测试ArrayList
 * Created by marking on 2017/3/26.
 */

public class TestPojo extends BasePoJo {

    @FieldDesc(key = "class")//描述对应的JSONObject的key
    public String clazz;
    @FieldDesc(key = "users", arrayType = TestUser.class)//描述对应的JSONObject的key及ArrayList内部类类型
    public ArrayList<TestUser> userList;

    //普通构造
    public TestPojo() {
        super(null);
    }

    //固有构造（必须，否则可能无法正常转换）
    public TestPojo(String jsonStr) {
        super(jsonStr);
    }

}

package com.mayousheng.www.jsondecodepojo.pojo;

import com.mayousheng.www.basepojo.BasePoJo;
import com.mayousheng.www.basepojo.FieldDesc;

public class UserDesc extends BasePoJo {

    @FieldDesc(key = "nickName")
    public String nickName;        //用户昵称
    @FieldDesc(key = "imgUrl")
    public String imgUrl;         //用户头像

    public UserDesc(String jsonStr) {
        super(jsonStr);
    }
}

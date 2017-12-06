package com.mayousheng.www.jsondecodepojo.pojo;

import com.mayousheng.www.basepojo.BasePoJo;
import com.mayousheng.www.basepojo.FieldDesc;

public class UserResponse extends BasePoJo {
    @FieldDesc(key = "userName")
    private String userName;        //账户	String(unique)
    @FieldDesc(key = "nickName")
    private String nickName;        //昵称	String
    @FieldDesc(key = "msg")
    private String msg;             //个性签名	String
    @FieldDesc(key = "sex")
    private Integer sex;            //性别	int
    @FieldDesc(key = "email")
    private String email;           //邮箱	String
    @FieldDesc(key = "phone")
    private String phone;           //电话号码	String
    @FieldDesc(key = "imgUrl")
    private String imgUrl;          //头像图片url	String
    @FieldDesc(key = "pageHome")
    private String pageHome;        //主页链接	String

    public UserResponse(String jsonStr) {
        super(jsonStr);
    }

    public UserResponse(String jsonStr, String userName, String nickName, String msg
            , Integer sex, String email, String phone, String imgUrl, String pageHome) {
        super(jsonStr);
        this.userName = userName;
        this.nickName = nickName;
        this.msg = msg;
        this.sex = sex;
        this.email = email;
        this.phone = phone;
        this.imgUrl = imgUrl;
        this.pageHome = pageHome;
    }
}

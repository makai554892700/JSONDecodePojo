package com.mayousheng.www.jsondecodepojo.pojo;

import com.mayousheng.www.basepojo.BasePoJo;
import com.mayousheng.www.basepojo.FieldDesc;

public class User extends BasePoJo {
    @FieldDesc(key = "userName")
    public String userName;        //账户	String(unique)
    @FieldDesc(key = "nickName")
    public String nickName;        //昵称	String
    @FieldDesc(key = "msg")
    public String msg;             //个性签名	String
    @FieldDesc(key = "sex")
    public Integer sex;            //性别	int
    @FieldDesc(key = "passWord")
    public String passWord;        //密码	String
    @FieldDesc(key = "email")
    public String email;           //邮箱	String
    @FieldDesc(key = "phone")
    public String phone;           //电话号码	String
    @FieldDesc(key = "imgUrl")
    public String imgUrl;          //头像图片url	String
    @FieldDesc(key = "pageHome")
    public String pageHome;        //主页链接	String
    @FieldDesc(key = "rember")
    public boolean rember;       //是否记住我
    @FieldDesc(key = "deviceType")
    public Integer deviceType;     //设备类型 0:web/1:Android/2:IOS

    public User(String jsonStr) {
        super(jsonStr);
    }

    public User(String userName, String nickName, String passWord
            , boolean rember, Integer deviceType) {
        super(null);
        this.userName = userName;
        this.nickName = nickName;
        this.passWord = passWord;
        this.rember = rember;
        this.deviceType = deviceType;
    }

    public User(String userName, String nickName, String msg, Integer sex
            , String passWord, String email, String phone, String imgUrl, String pageHome, boolean rember, Integer deviceType) {
        super(null);
        this.userName = userName;
        this.nickName = nickName;
        this.msg = msg;
        this.sex = sex;
        this.passWord = passWord;
        this.email = email;
        this.phone = phone;
        this.imgUrl = imgUrl;
        this.pageHome = pageHome;
        this.rember = rember;
        this.deviceType = deviceType;
    }
}

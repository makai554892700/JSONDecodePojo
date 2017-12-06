package com.mayousheng.www.jsondecodepojo.utils;

import com.mayousheng.www.jsondecodepojo.common.StaticParam;
import com.mayousheng.www.jsondecodepojo.pojo.User;

/**
 * Created by makai on 2017/12/6.
 */

public class UserUtils {

    public static void login(User user, CommonRequestUtils.Back back) {
        if (user == null || back == null) {
            return;
        }
        CommonRequestUtils.commonPost(StaticParam.BASE_GET_USER_LOGIN, user.toString().getBytes(), back);
    }

    public static void register(User user, CommonRequestUtils.Back back) {
        if (user == null || back == null) {
            return;
        }
        CommonRequestUtils.commonPost(StaticParam.BASE_GET_USER_REGISTER, user.toString().getBytes(), back);
    }

    public static void logout(final CommonRequestUtils.Back back) {
        CommonRequestUtils.commonGet(StaticParam.BASE_GET_USER_LOGOUT, back);
    }

}

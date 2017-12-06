package com.mayousheng.www.jsondecodepojo.utils;

import com.mayousheng.www.jsondecodepojo.common.StaticParam;
import com.mayousheng.www.jsondecodepojo.pojo.Comment;
import com.mayousheng.www.jsondecodepojo.pojo.Operate;

/**
 * Created by makai on 2017/12/6.
 */

public class OperateUtils {

    public static void love(Operate operate, CommonRequestUtils.Back back) {
        if (operate == null || back == null) {
            return;
        }
        CommonRequestUtils.commonPost(StaticParam.BASE_POST_OPERATE_LOVE, operate.toString().getBytes(), back);
    }

    public static void hate(Operate operate, CommonRequestUtils.Back back) {
        if (operate == null || back == null) {
            return;
        }
        CommonRequestUtils.commonPost(StaticParam.BASE_POST_OPERATE_HATE, operate.toString().getBytes(), back);
    }

    public static void share(Operate operate, CommonRequestUtils.Back back) {
        if (operate == null || back == null) {
            return;
        }
        CommonRequestUtils.commonPost(StaticParam.BASE_POST_OPERATE_SHARE, operate.toString().getBytes(), back);
    }

    public static void comment(Comment comment, CommonRequestUtils.Back back) {
        if (comment == null || back == null) {
            return;
        }
        CommonRequestUtils.commonPost(StaticParam.BASE_POST_OPERATE_COMMENT, comment.toString().getBytes(), back);
    }

}

package com.mayousheng.www.jsondecodepojo.utils.db.model;


import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/9.
 */
public class DBOperateInfo implements Serializable {
    public static final String ID = "_id";//显示时的标记
    public static final String NEWS_TYPE = "newsType";//新闻类型
    public static final String NEWS_MARK = "newsMark";//新闻标记
    public static final String OPERATE = "operate";//操作
    public static final String SURE = "sure";//是否经过网络校验

    public int id;
    public String newsType;
    public int newsMark;
    public int operate;
    public boolean sure;

    public DBOperateInfo() {
    }

    public DBOperateInfo(String newsType, int newsMark, int operate, boolean sure) {
        this.newsType = newsType;
        this.newsMark = newsMark;
        this.operate = operate;
        this.sure = sure;
    }

    @Override
    public String toString() {
        return "DBOperateInfo{" +
                "id=" + id +
                ", newsType='" + newsType + '\'' +
                ", newsMark=" + newsMark +
                ", operate=" + operate +
                ", sure=" + sure +
                '}';
    }
}

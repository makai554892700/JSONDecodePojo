package com.mayousheng.www.jsondecodepojo.utils.db.model;


import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/9.
 */
public class DBOprateInfo implements Serializable {
    public static final String ID = "_id";//显示时的标记
    public static final String NEWS_TYPE = "newsType";//新闻类型
    public static final String NEWS_MARK = "newsMark";//新闻标记
    public static final String OPRATE = "oprate";//操作
    public static final String SURE = "sure";//是否经过网络校验

    public int id;
    public String newsType;
    public int newsMark;
    public int oprate;
    public boolean sure;

    public DBOprateInfo() {
    }

    public DBOprateInfo(String newsType, int newsMark, int oprate, boolean sure) {
        this.newsType = newsType;
        this.newsMark = newsMark;
        this.oprate = oprate;
        this.sure = sure;
    }

    @Override
    public String toString() {
        return "DBOprateInfo{" +
                "id=" + id +
                ", newsType='" + newsType + '\'' +
                ", newsMark=" + newsMark +
                ", oprate=" + oprate +
                ", sure=" + sure +
                '}';
    }
}

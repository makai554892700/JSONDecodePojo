package com.mayousheng.www.jsondecodepojo.utils.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;


import com.mayousheng.www.jsondecodepojo.utils.db.model.DBOperateInfo;
import com.mayousheng.www.jsondecodepojo.utils.db.sqllitehelper.OperateInfoSqlitHelper;

import java.util.ArrayList;

public class DBOperateUtils {
    // 数据库名称
    private static String DB_NAME = "operateinfo.db";
    // 数据库版本
    private static int DB_VERSION = 1;
    private SQLiteDatabase db;
    private OperateInfoSqlitHelper dbHelper;

    public DBOperateUtils(Context context) {
        dbHelper = new OperateInfoSqlitHelper(context.getApplicationContext(), DB_NAME, null, DB_VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
        dbHelper.close();
    }

    public ArrayList<DBOperateInfo> getDBOperateInfos() {
        ArrayList<DBOperateInfo> dbOperateInfos = new ArrayList<DBOperateInfo>();
        Cursor cursor = db.query(OperateInfoSqlitHelper.TB_NAME, null, null, null, null,
                null, DBOperateInfo.ID + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast() && (cursor.getString(1) != null)) {
            DBOperateInfo dbOperateInfo = new DBOperateInfo();
            dbOperateInfo.id = Integer.valueOf(cursor.getString(0));
            dbOperateInfo.newsType = cursor.getString(1);
            dbOperateInfo.newsMark = cursor.getInt(2);
            dbOperateInfo.operate = cursor.getInt(3);
            dbOperateInfo.sure = cursor.getInt(4) == 1;
            dbOperateInfos.add(dbOperateInfo);
            cursor.moveToNext();
        }
        cursor.close();
        return dbOperateInfos;
    }

    public DBOperateInfo getDBOperateInfoByTypeAndMark(String newsType, int newsMark) {
        DBOperateInfo result = null;
        Cursor cursor = db.query(OperateInfoSqlitHelper.TB_NAME, null, DBOperateInfo.NEWS_MARK + "=? and " + DBOperateInfo.NEWS_TYPE + "=?",
                new String[]{String.valueOf(newsMark), newsType}, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast() && (cursor.getString(1) != null)) {
            result = new DBOperateInfo();
            result.id = Integer.valueOf(cursor.getString(0));
            result.newsType = cursor.getString(1);
            result.newsMark = cursor.getInt(2);
            result.operate = cursor.getInt(3);
            result.sure = cursor.getInt(4) == 1;
            break;
        }
        cursor.close();
        return result;
    }


    public Boolean haveDBOperateInfo(int dbSyncInfo) {
        Boolean b = false;
        Cursor cursor = db.query(OperateInfoSqlitHelper.TB_NAME, null, DBOperateInfo.ID
                + "=?", new String[]{String.valueOf(dbSyncInfo)}, null, null, null);
        b = cursor.moveToFirst();
        cursor.close();
        return b;
    }

    public int updateDBOperateInfo(DBOperateInfo dbOperateInfo) {
        int id = -1;
        if (dbOperateInfo != null && !TextUtils.isEmpty(dbOperateInfo.newsType)) {
            id = dbOperateInfo.id;
            if (haveDBOperateInfo(id)) {
                ContentValues values = new ContentValues();
                values.put(DBOperateInfo.NEWS_TYPE, dbOperateInfo.newsType);
                values.put(DBOperateInfo.NEWS_MARK, dbOperateInfo.newsMark);
                values.put(DBOperateInfo.OPERATE, dbOperateInfo.operate);
                values.put(DBOperateInfo.SURE, dbOperateInfo.sure);
                id = db.update(OperateInfoSqlitHelper.TB_NAME, values, DBOperateInfo.ID + "="
                        + dbOperateInfo.id, null);
            }
        }
        return id;
    }

    public Long saveDBOperateInfo(DBOperateInfo dbOperateInfo) {
        if (dbOperateInfo == null || TextUtils.isEmpty(dbOperateInfo.newsType)) {
            return -1L;
        }
        ContentValues values = new ContentValues();
        if (dbOperateInfo.id > 0) {
            values.put(DBOperateInfo.ID, dbOperateInfo.id);
        }
        values.put(DBOperateInfo.NEWS_TYPE, dbOperateInfo.newsType);
        values.put(DBOperateInfo.NEWS_MARK, dbOperateInfo.newsMark);
        values.put(DBOperateInfo.OPERATE, dbOperateInfo.operate);
        values.put(DBOperateInfo.SURE, dbOperateInfo.sure);
        return db.insert(OperateInfoSqlitHelper.TB_NAME, DBOperateInfo.ID, values);
    }

    public int delDBOperateInfo(String newsType, int newsMark) {
        DBOperateInfo dbOperateInfo = getDBOperateInfoByTypeAndMark(newsType, newsMark);
        if (dbOperateInfo == null || dbOperateInfo.sure) {
            return -1;
        }
        return delDBOperateInfo(dbOperateInfo.id);
    }

    public int delDBOperateInfo(int dbSyncInfoId) {
        int id = -1;
        if (haveDBOperateInfo(dbSyncInfoId)) {
            id = db.delete(OperateInfoSqlitHelper.TB_NAME,
                    DBOperateInfo.ID + "=?", new String[]{String.valueOf(dbSyncInfoId)});
        }
        return id;
    }
}

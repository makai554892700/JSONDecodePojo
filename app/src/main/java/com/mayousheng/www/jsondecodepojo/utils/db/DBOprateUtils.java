package com.mayousheng.www.jsondecodepojo.utils.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;


import com.mayousheng.www.jsondecodepojo.utils.db.model.DBOprateInfo;
import com.mayousheng.www.jsondecodepojo.utils.db.sqllitehelper.OprateInfoSqlitHelper;

import java.util.ArrayList;

public class DBOprateUtils {
    // 数据库名称
    private static String DB_NAME = "oprateinfo.db";
    // 数据库版本
    private static int DB_VERSION = 1;
    private SQLiteDatabase db;
    private OprateInfoSqlitHelper dbHelper;

    public DBOprateUtils(Context context) {
        dbHelper = new OprateInfoSqlitHelper(context, DB_NAME, null, DB_VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
        dbHelper.close();
    }

    public ArrayList<DBOprateInfo> getDBOprateInfos() {
        ArrayList<DBOprateInfo> dbOprateInfos = new ArrayList<DBOprateInfo>();
        Cursor cursor = db.query(OprateInfoSqlitHelper.TB_NAME, null, null, null, null,
                null, DBOprateInfo.ID + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast() && (cursor.getString(1) != null)) {
            DBOprateInfo dbOprateInfo = new DBOprateInfo();
            dbOprateInfo.id = Integer.valueOf(cursor.getString(0));
            dbOprateInfo.newsType = cursor.getString(1);
            dbOprateInfo.newsMark = cursor.getInt(2);
            dbOprateInfo.oprate = cursor.getInt(3);
            dbOprateInfo.sure = cursor.getInt(4) == 1;
            dbOprateInfos.add(dbOprateInfo);
            cursor.moveToNext();
        }
        cursor.close();
        return dbOprateInfos;
    }

    public DBOprateInfo getDBOprateInfoByTypeAndMark(String newsType, int newsMark) {
        DBOprateInfo result = null;
        Cursor cursor = db.query(OprateInfoSqlitHelper.TB_NAME, null, DBOprateInfo.NEWS_MARK + "=? and " + DBOprateInfo.NEWS_TYPE + "=?",
                new String[]{String.valueOf(newsMark), newsType}, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast() && (cursor.getString(1) != null)) {
            result = new DBOprateInfo();
            result.id = Integer.valueOf(cursor.getString(0));
            result.newsType = cursor.getString(1);
            result.newsMark = cursor.getInt(2);
            result.oprate = cursor.getInt(3);
            result.sure = cursor.getInt(4) == 1;
            break;
        }
        cursor.close();
        return result;
    }


    public Boolean haveDBOprateInfo(int dbSyncInfo) {
        Boolean b = false;
        Cursor cursor = db.query(OprateInfoSqlitHelper.TB_NAME, null, DBOprateInfo.ID
                + "=?", new String[]{String.valueOf(dbSyncInfo)}, null, null, null);
        b = cursor.moveToFirst();
        cursor.close();
        return b;
    }

    public int updateDBOprateInfo(DBOprateInfo dbOprateInfo) {
        int id = -1;
        if (dbOprateInfo != null && !TextUtils.isEmpty(dbOprateInfo.newsType)) {
            id = dbOprateInfo.id;
            if (haveDBOprateInfo(id)) {
                ContentValues values = new ContentValues();
                values.put(DBOprateInfo.NEWS_TYPE, dbOprateInfo.newsType);
                values.put(DBOprateInfo.NEWS_MARK, dbOprateInfo.newsMark);
                values.put(DBOprateInfo.OPRATE, dbOprateInfo.oprate);
                values.put(DBOprateInfo.SURE, dbOprateInfo.sure);
                id = db.update(OprateInfoSqlitHelper.TB_NAME, values, DBOprateInfo.ID + "="
                        + dbOprateInfo.id, null);
            }
        }
        return id;
    }

    public Long saveDBOprateInfo(DBOprateInfo dbOprateInfo) {
        if (dbOprateInfo == null || TextUtils.isEmpty(dbOprateInfo.newsType)) {
            return -1L;
        }
        ContentValues values = new ContentValues();
        if (dbOprateInfo.id > 0) {
            values.put(DBOprateInfo.ID, dbOprateInfo.id);
        }
        values.put(DBOprateInfo.NEWS_TYPE, dbOprateInfo.newsType);
        values.put(DBOprateInfo.NEWS_MARK, dbOprateInfo.newsMark);
        values.put(DBOprateInfo.OPRATE, dbOprateInfo.oprate);
        values.put(DBOprateInfo.SURE, dbOprateInfo.sure);
        return db.insert(OprateInfoSqlitHelper.TB_NAME, DBOprateInfo.ID, values);
    }

    public int delDBOprateInfo(String newsType, int newsMark) {
        DBOprateInfo dbOprateInfo = getDBOprateInfoByTypeAndMark(newsType, newsMark);
        if (dbOprateInfo == null || dbOprateInfo.sure) {
            return -1;
        }
        return delDBOprateInfo(dbOprateInfo.id);
    }

    public int delDBOprateInfo(int dbSyncInfoId) {
        int id = -1;
        if (haveDBOprateInfo(dbSyncInfoId)) {
            id = db.delete(OprateInfoSqlitHelper.TB_NAME,
                    DBOprateInfo.ID + "=?", new String[]{String.valueOf(dbSyncInfoId)});
        }
        return id;
    }
}

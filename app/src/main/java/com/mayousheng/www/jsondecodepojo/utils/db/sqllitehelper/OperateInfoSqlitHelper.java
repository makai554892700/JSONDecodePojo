package com.mayousheng.www.jsondecodepojo.utils.db.sqllitehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.mayousheng.www.jsondecodepojo.utils.db.model.DBOperateInfo;

public class OperateInfoSqlitHelper extends SQLiteOpenHelper {
    public static final String TB_NAME = "operateinfo";

    public OperateInfoSqlitHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //创建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                TB_NAME + "(" +
                DBOperateInfo.ID + " integer primary key," +
                DBOperateInfo.NEWS_TYPE + " varchar," +
                DBOperateInfo.NEWS_MARK + " integer," +
                DBOperateInfo.OPERATE + " integer," +
                DBOperateInfo.SURE + " integer" +
                ")"
        );
    }

    //更新表
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(db);
    }

    //更新列
    public void updateColumn(SQLiteDatabase db, String oldColumn, String newColumn, String typeColumn) {
        try {
            db.execSQL("ALTER TABLE " +
                    TB_NAME + " CHANGE " +
                    oldColumn + " " + newColumn +
                    " " + typeColumn
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
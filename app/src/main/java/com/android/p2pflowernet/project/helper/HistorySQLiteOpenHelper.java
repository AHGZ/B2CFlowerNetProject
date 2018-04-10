package com.android.p2pflowernet.project.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by caishen on 2017/10/26.
 * by--搜索历史数据
 */

public class HistorySQLiteOpenHelper extends SQLiteOpenHelper {

    private static String name = "temp.db";
    private static Integer version = 1;

    public HistorySQLiteOpenHelper(Context context) {

        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table records(id integer primary key autoincrement,name varchar(200))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

package com.llk.weather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by King on 2016/6/4.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String CREAT_TODAY_TABLE_SQL =
            "create table today(" +
                    "_id  integer primary key autoincrement," +
                    "T_city text not null," +
                    "T_temperature text not null," +
                    "T_weather text not null)";

    private static final String CREAT_FUTURE_TABLE_SQL =
            "create table future(" +
                    "_id  integer primary key autoincrement," +
                    "F_temperature text not null," +
                    "F_week text not null," +
                    "F_weather text not null)";

    private static final String CREAT_SK_TABLE_SQL =
            "create table sk(" +
                    "_id  integer primary key autoincrement," +
                    "S_temp text not null)";

    private static final String CREAT_QUALITY_TABLE_SQL =
            "create table quality(" +
                    "_id  integer primary key autoincrement," +
                    "Q_city text not null," +
                    "Q_dressing text not null," +
                    "Q_travel text not null," +
                    "Q_wash text not null," +
                    "Q_comfort text not null," +
                    "Q_exercise text not null," +
                    "Q_dressingA text not null," +
                    "Q_uv text not null," +
                    "Q_drying text not null)";


    private static final String DATABASE_NAME = "WeatherMange.db";
    private static final int DATABASE_VERSION = 2;


    public SQLiteHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAT_TODAY_TABLE_SQL);
        db.execSQL(CREAT_FUTURE_TABLE_SQL);
        db.execSQL(CREAT_SK_TABLE_SQL);
        db.execSQL(CREAT_QUALITY_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS today");
        db.execSQL("DROP TABLE IF EXISTS future");
        db.execSQL("DROP TABLE IF EXISTS sk");
        db.execSQL("DROP TABLE IF EXISTS quality");
        onCreate(db);
    }
}

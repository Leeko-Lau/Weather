package com.llk.weather.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.llk.weather.model.Future;
import com.llk.weather.db.SQLiteHelper;
import com.llk.weather.model.Today;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by King on 2016/6/5.
 */
public class FutureDao {
    private SQLiteHelper helper;
    private SQLiteDatabase db;

    public FutureDao(Context context){
        helper = new SQLiteHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    //添加信息
    public void add(Future future){
        db.execSQL(
                "INSERT INTO future VALUES(null, ?, ?, ?)",
                new Object[]{future.getTemperature(),future.getWeek(),future.getWeather()}
        );
    }

    //删除信息
    public void delete(Future future){
        db.execSQL(
                "DELETE FROM future WHERE _id=?",
                new Object[]{future.get_id()}
        );
    }

    //根据id删除信息
    public void deletFromId(int id){
        db.execSQL(
                "DELETE FROM future WHERE _id=?",
                new Object[]{id}
        );
    }

    public Cursor queryTheCursor(){
        Cursor c = db.rawQuery("SELECT * FROM future",null);
        return c;
    }

    //查询数据
    public ArrayList<Future> query(){
        ArrayList<Future> futures = new ArrayList<>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()){

            Future future = new Future();
            future.set_id(c.getInt(c.getColumnIndex("_id")));
            future.setTemperature(c.getString(c.getColumnIndex("F_temperature")));
            future.setWeek(c.getString(c.getColumnIndex("F_week")));
            future.setWeather(c.getString(c.getColumnIndex("F_weather")));
            futures.add(future);
        }
        c.close();
        return futures;
    }

}
